package dev.waylon.player.model

import kotlinx.serialization.Serializable

/**
 * Video Stream Information Model
 *
 * Represents the stream information required for video playback, including stream URLs of different qualities
 */
@Serializable
data class VideoStream(
    /**
     * Video ID
     */
    val videoId: String,

    /**
     * Currently selected video quality ID
     */
    val selectedQualityId: Int,

    /**
     * Video stream list
     */
    val streams: List<StreamItem>,

    /**
     * Audio stream list (if separated from video stream)
     */
    val audioStreams: List<AudioStreamItem>? = null
)

/**
 * Video Stream Item
 */
@Serializable
data class StreamItem(
    /**
     * Stream quality ID
     */
    val qualityId: Int,

    /**
     * Stream quality name
     */
    val qualityName: String,

    /**
     * Video resolution (width x height)
     */
    val resolution: Resolution,

    /**
     * Video bitrate (kbps)
     */
    val bitrate: Int,

    /**
     * Video format
     */
    val format: String,

    /**
     * Video stream URL
     */
    val url: String,

    /**
     * Stream type (e.g., mp4, flv, etc.)
     */
    val type: String,

    /**
     * Stream size (bytes)
     */
    val size: Long? = null
)

/**
 * Audio Stream Item
 */
@Serializable
data class AudioStreamItem(
    /**
     * Audio quality ID
     */
    val qualityId: Int,

    /**
     * Audio quality name
     */
    val qualityName: String,

    /**
     * Audio bitrate (kbps)
     */
    val bitrate: Int,

    /**
     * Audio format
     */
    val format: String,

    /**
     * Audio stream URL
     */
    val url: String,

    /**
     * Audio stream size (bytes)
     */
    val size: Long? = null
)

/**
 * Resolution Information
 */
@Serializable
data class Resolution(
    /**
     * Width
     */
    val width: Int,

    /**
     * Height
     */
    val height: Int
)

/**
 * Stream Type Enum
 */
enum class StreamType {
    MP4,
    FLV,
    DASH,
    HLS,
    WEBM
}