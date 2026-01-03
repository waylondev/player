package dev.waylon.player.model

import kotlinx.serialization.Serializable

/**
 * Video Detailed Information Model
 *
 * Represents the complete detailed information of a video, used for video detail pages and other scenarios
 */
@Serializable
data class VideoDetail(
    /**
     * Video basic information
     */
    val videoInfo: VideoInfo,

    /**
     * Video full description
     */
    val fullDescription: String? = null,

    /**
     * Like count
     */
    val likeCount: Long = 0,

    /**
     * Coin count (virtual currency for Bilibili)
     */
    val coinCount: Long = 0,

    /**
     * Favorite count
     */
    val favoriteCount: Long = 0,

    /**
     * Comment count
     */
    val commentCount: Long = 0,

    /**
     * Share count
     */
    val shareCount: Long = 0,

    /**
     * UP/Author detailed information
     */
    val authorInfo: AuthorInfo? = null,

    /**
     * Video parts information (for videos with multiple segments)
     */
    val parts: List<VideoPart> = emptyList(),

    /**
     * List of available video qualities
     */
    val availableQualities: List<VideoQuality> = emptyList()
)

/**
 * Author/UP Detailed Information
 */
@Serializable
data class AuthorInfo(
    /**
     * Author ID
     */
    val id: String,

    /**
     * Author name
     */
    val name: String,

    /**
     * Author avatar URL
     */
    val avatarUrl: String,

    /**
     * Author follower count
     */
    val followerCount: Long? = null,

    /**
     * Author biography
     */
    val bio: String? = null
)

/**
 * Video Part Information
 */
@Serializable
data class VideoPart(
    /**
     * Part ID
     */
    val id: String,

    /**
     * Part title
     */
    val title: String,

    /**
     * Part duration (seconds)
     */
    val duration: Int,

    /**
     * Part cover URL
     */
    val coverUrl: String? = null
)

/**
 * Video Quality Option
 */
@Serializable
data class VideoQuality(
    /**
     * Quality ID (e.g., qn value for Bilibili)
     */
    val id: Int,

    /**
     * Quality name (e.g., 1080p, 720p, etc.)
     */
    val name: String,

    /**
     * Whether this is the default quality
     */
    val isDefault: Boolean = false
)