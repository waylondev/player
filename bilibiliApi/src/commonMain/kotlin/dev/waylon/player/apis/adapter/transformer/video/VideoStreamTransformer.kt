package dev.waylon.player.apis.adapter.transformer.video

import dev.waylon.player.apis.adapter.transformer.Transformer
import dev.waylon.player.model.Resolution
import dev.waylon.player.model.StreamItem
import dev.waylon.player.model.VideoStream
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.contentOrNull
import kotlinx.serialization.json.intOrNull
import kotlinx.serialization.json.jsonObject
import kotlinx.serialization.json.jsonPrimitive

/**
 * Transformer for Bilibili Video Stream API Response
 * 
 * Converts Bilibili API response JSON to VideoStream model
 */
object VideoStreamTransformer : Transformer<JsonObject, VideoStream> {

    override fun transform(input: JsonObject): VideoStream {
        // Parse JSON directly to get needed fields
        val data = input["data"]?.jsonObject ?: return defaultVideoStream()
        val videoId = data["bvid"]?.jsonPrimitive?.contentOrNull ?: ""
        val qualityId = data["quality"]?.jsonPrimitive?.intOrNull ?: 80

        // Create a simple stream item for now
        val streamItem = StreamItem(
            qualityId = qualityId,
            qualityName = "1080p HD",
            resolution = Resolution(1920, 1080),
            bitrate = 1000,
            format = "mp4",
            url = "https://example.com/video.mp4",
            type = "mp4",
            size = 1000000000
        )

        return VideoStream(
            videoId = videoId,
            selectedQualityId = qualityId,
            streams = listOf(streamItem),
            audioStreams = null
        )
    }

    private fun defaultVideoStream(): VideoStream {
        return VideoStream(
            videoId = "",
            selectedQualityId = 80,
            streams = listOf(
                StreamItem(
                    qualityId = 80,
                    qualityName = "1080p HD",
                    resolution = Resolution(1920, 1080),
                    bitrate = 1000,
                    format = "mp4",
                    url = "https://example.com/video.mp4",
                    type = "mp4"
                )
            )
        )
    }
}
