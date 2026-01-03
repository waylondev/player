package dev.waylon.player.model

import kotlinx.serialization.Serializable

/**
 * Video Basic Information Model
 *
 * Represents the core basic information of a video, used for list display and other scenarios
 */
@Serializable
data class VideoInfo(
    /**
     * Video ID, different platforms may have different formats
     * e.g., Bilibili's bvid (BV1xx411c7mX), YouTube's videoId
     */
    val id: String,

    /**
     * Video title
     */
    val title: String,

    /**
     * Video cover URL
     */
    val coverUrl: String,

    /**
     * Video author/UP name
     */
    val author: String,

    /**
     * Play count
     */
    val playCount: Long,

    /**
     * Video duration (seconds)
     */
    val duration: Int,

    /**
     * Video category/partition
     */
    val category: String? = null,

    /**
     * Video publish timestamp (seconds)
     */
    val publishTime: Long? = null,

    /**
     * Video description (short version)
     */
    val description: String? = null,

    /**
     * Video CID (Content ID) - required for video playback
     */
    val cid: Int? = null
)