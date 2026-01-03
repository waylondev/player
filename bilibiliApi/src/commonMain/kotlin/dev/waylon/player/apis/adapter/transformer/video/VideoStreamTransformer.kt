package dev.waylon.player.apis.adapter.transformer.video

import dev.waylon.player.apis.adapter.transformer.Transformer
import dev.waylon.player.model.AudioStreamItem
import dev.waylon.player.model.Resolution
import dev.waylon.player.model.StreamItem
import dev.waylon.player.model.VideoStream
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.contentOrNull
import kotlinx.serialization.json.intOrNull
import kotlinx.serialization.json.jsonArray
import kotlinx.serialization.json.jsonObject
import kotlinx.serialization.json.jsonPrimitive
import kotlinx.serialization.json.longOrNull

/**
 * Bilibili Video Stream API Response Transformer
 * 
 * Converts Bilibili API response JSON to VideoStream model based on actual API response structure.
 * Supports both MP4/FLV format (durl) and DASH format streams.
 * 
 * Reference: https://github.com/SocialSisterYi/bilibili-API-collect/blob/master/docs/video/videostream_url.md
 */
object VideoStreamTransformer : Transformer<JsonObject, VideoStream> {

    override fun transform(input: JsonObject): VideoStream {
        // Check API response code
        val code = input["code"]?.jsonPrimitive?.intOrNull
        if (code != 0) {
            val message = input["message"]?.jsonPrimitive?.contentOrNull ?: "Unknown error"
            throw VideoStreamTransformException("API returned error: $message (code: $code)")
        }

        val data = input["data"]?.jsonObject
            ?: throw VideoStreamTransformException("Missing data field in API response")

        // Parse basic video information
        val videoId = data["bvid"]?.jsonPrimitive?.contentOrNull ?: ""
        val quality = data["quality"]?.jsonPrimitive?.intOrNull ?: 64
        val format = data["format"]?.jsonPrimitive?.contentOrNull ?: ""
        val timelength = data["timelength"]?.jsonPrimitive?.longOrNull ?: 0

        // Parse available formats and qualities
        val acceptFormats = data["accept_format"]?.jsonPrimitive?.contentOrNull ?: ""
        val acceptDescriptions =
            data["accept_description"]?.jsonArray?.mapNotNull { it.jsonPrimitive.contentOrNull } ?: emptyList()
        val acceptQualities =
            data["accept_quality"]?.jsonArray?.mapNotNull { it.jsonPrimitive.intOrNull } ?: emptyList()

        // Parse video streams based on format (MP4/FLV or DASH)
        val streams = parseVideoStreams(data)
        val audioStreams = parseAudioStreams(data)

        return VideoStream(
            videoId = videoId,
            selectedQualityId = quality,
            streams = streams,
            audioStreams = audioStreams
        )
    }

    private fun parseVideoStreams(data: JsonObject): List<StreamItem> {
        // Try DASH format first (new format)
        val dashStreams = parseDashVideoStreams(data)
        if (dashStreams.isNotEmpty()) {
            return dashStreams
        }

        // Fallback to MP4/FLV format (old format)
        return parseDurlVideoStreams(data)
    }

    private fun parseDashVideoStreams(data: JsonObject): List<StreamItem> {
        val dash = data["dash"]?.jsonObject ?: return emptyList()
        val videoStreams = dash["video"]?.jsonArray ?: return emptyList()

        return videoStreams.mapNotNull { videoItem ->
            val video = videoItem.jsonObject
            parseDashVideoStream(video)
        }
    }

    private fun parseDashVideoStream(video: JsonObject): StreamItem? {
        val baseUrl = video["baseUrl"]?.jsonPrimitive?.contentOrNull ?: return null
        val backupUrls = video["backupUrl"]?.jsonArray?.mapNotNull { it.jsonPrimitive.contentOrNull } ?: emptyList()

        // Use primary URL, fallback to first backup URL if primary is unavailable
        val url = baseUrl.ifEmpty { backupUrls.firstOrNull() ?: return null }

        val qualityId = video["id"]?.jsonPrimitive?.intOrNull ?: return null
        val codecs = video["codecs"]?.jsonPrimitive?.contentOrNull ?: ""
        val mimeType = video["mimeType"]?.jsonPrimitive?.contentOrNull ?: ""

        val width = video["width"]?.jsonPrimitive?.intOrNull ?: return null
        val height = video["height"]?.jsonPrimitive?.intOrNull ?: return null
        val frameRate = video["frameRate"]?.jsonPrimitive?.contentOrNull ?: ""

        val bandwidth = video["bandwidth"]?.jsonPrimitive?.intOrNull ?: 0
        val codecid = video["codecid"]?.jsonPrimitive?.intOrNull ?: 7 // Default to AVC (H.264)

        val qualityName = getQualityDisplayName(qualityId, codecs, mimeType)
        val (format, codec) = parseFormatAndCodec(codecs, mimeType, codecid)

        return StreamItem(
            qualityId = qualityId,
            qualityName = qualityName,
            resolution = Resolution(width, height),
            bitrate = bandwidth / 1000, // Convert to kbps
            format = format,
            url = url,
            type = codec
        )
    }

    private fun parseDurlVideoStreams(data: JsonObject): List<StreamItem> {
        val durl = data["durl"]?.jsonArray ?: return emptyList()

        return durl.mapNotNull { durlItem ->
            val durlObject = durlItem.jsonObject
            val url = durlObject["url"]?.jsonPrimitive?.contentOrNull ?: return@mapNotNull null
            val backupUrls =
                durlObject["backup_url"]?.jsonArray?.mapNotNull { it.jsonPrimitive.contentOrNull } ?: emptyList()

            val length = durlObject["length"]?.jsonPrimitive?.longOrNull ?: 0
            val size = durlObject["size"]?.jsonPrimitive?.longOrNull ?: 0

            // Use actual quality information from the API response
            val qualityId = data["quality"]?.jsonPrimitive?.intOrNull ?: 64
            val formatStr = data["format"]?.jsonPrimitive?.contentOrNull ?: ""

            // Get actual resolution from API if available, otherwise estimate
            val width = data["width"]?.jsonPrimitive?.intOrNull
            val height = data["height"]?.jsonPrimitive?.intOrNull
            val resolution = if (width != null && height != null) {
                Resolution(width, height)
            } else {
                estimateResolution(qualityId)
            }

            val qualityName = getQualityDisplayName(qualityId, "", formatStr)
            val (format, codec) = parseFormatAndCodec("", formatStr, 7) // Default to AVC for MP4/FLV

            StreamItem(
                qualityId = qualityId,
                qualityName = qualityName,
                resolution = resolution,
                bitrate = if (length > 0) (size * 8 / length).toInt() else 1000, // Calculate bitrate
                format = format,
                url = url,
                type = codec
            )
        }
    }

    private fun parseAudioStreams(data: JsonObject): List<AudioStreamItem> {
        val dash = data["dash"]?.jsonObject ?: return emptyList()
        val audioStreams = dash["audio"]?.jsonArray ?: return emptyList()

        return audioStreams.mapNotNull { audioItem ->
            val audio = audioItem.jsonObject
            val baseUrl = audio["baseUrl"]?.jsonPrimitive?.contentOrNull ?: return@mapNotNull null
            val backupUrls = audio["backupUrl"]?.jsonArray?.mapNotNull { it.jsonPrimitive.contentOrNull } ?: emptyList()

            val url = baseUrl.ifEmpty { backupUrls.firstOrNull() ?: return@mapNotNull null }
            val bandwidth = audio["bandwidth"]?.jsonPrimitive?.intOrNull ?: 0
            val size = audio["size"]?.jsonPrimitive?.longOrNull
            val audioId = audio["id"]?.jsonPrimitive?.intOrNull ?: return@mapNotNull null

            AudioStreamItem(
                qualityId = audioId,
                qualityName = getAudioQualityDisplayName(audioId, bandwidth),
                bitrate = bandwidth / 1000,
                format = audio["mimeType"]?.jsonPrimitive?.contentOrNull ?: "audio/mp4",
                url = url,
                size = size
            )
        }
    }

    private fun getQualityDisplayName(qualityId: Int, codecs: String, mimeType: String): String {
        return when (qualityId) {
            6 -> "240P"
            16 -> "360P"
            32 -> "480P"
            64 -> "720P"
            74 -> "720P60"
            80 -> "1080P"
            100 -> "智能修复"
            112 -> "1080P+"
            116 -> "1080P60"
            120 -> "4K"
            125 -> "HDR"
            126 -> "杜比视界"
            127 -> "8K"
            129 -> "HDR Vivid"
            else -> "未知 ($qualityId)"
        }
    }

    private fun getAudioQualityDisplayName(audioId: Int, bandwidth: Int): String {
        return when (audioId) {
            30216 -> "64K"
            30232 -> "132K"
            30280 -> "192K"
            30250 -> "杜比全景声"
            30251 -> "Hi-Res"
            30255 -> "高音质"
            else -> when {
                bandwidth >= 320000 -> "高码率音频"
                bandwidth >= 192000 -> "标准音频"
                bandwidth >= 128000 -> "普通音频"
                else -> "低码率音频 ($audioId)"
            }
        }
    }

    private fun parseFormatAndCodec(codecs: String, mimeType: String, codecid: Int): Pair<String, String> {
        // Parse from codecs string first
        if (codecs.isNotEmpty()) {
            return when {
                codecs.contains("avc", ignoreCase = true) -> Pair("mp4", "h264")
                codecs.contains("hev", ignoreCase = true) -> Pair("mp4", "h265")
                codecs.contains("av01", ignoreCase = true) -> Pair("mp4", "av1")
                codecs.contains("vp9", ignoreCase = true) -> Pair("webm", "vp9")
                else -> Pair("mp4", "unknown")
            }
        }

        // Parse from codecid
        return when (codecid) {
            7 -> Pair("mp4", "h264")  // AVC
            12 -> Pair("mp4", "h265") // HEVC
            13 -> Pair("mp4", "av1")  // AV1
            else -> Pair("mp4", "h264") // Default to h264
        }
    }

    private fun estimateResolution(qualityId: Int): Resolution {
        return when (qualityId) {
            6 -> Resolution(426, 240)   // 240P
            16 -> Resolution(640, 360)  // 360P
            32 -> Resolution(854, 480)  // 480P
            64 -> Resolution(1280, 720) // 720P
            74 -> Resolution(1280, 720) // 720P60
            80 -> Resolution(1920, 1080) // 1080P
            112 -> Resolution(1920, 1080) // 1080P+
            116 -> Resolution(1920, 1080) // 1080P60
            120 -> Resolution(3840, 2160) // 4K
            127 -> Resolution(7680, 4320) // 8K
            else -> Resolution(1280, 720) // Default to 720P
        }
    }
}

/**
 * Custom exception for video stream transformation errors
 */
class VideoStreamTransformException(message: String, cause: Throwable? = null) : Exception(message, cause)