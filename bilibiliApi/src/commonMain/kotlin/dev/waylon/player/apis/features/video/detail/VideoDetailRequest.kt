package dev.waylon.player.apis.features.video.detail

import kotlinx.serialization.Serializable

/**
 * Video Detail request parameters
 * 
 * @param aid Video AV number (optional, use either aid or bvid)
 * @param bvid Video BV number (optional, use either aid or bvid)
 */
@Serializable
data class VideoDetailRequest(
    /** Video AV number */
    val aid: Int? = null,
    /** Video BV number */
    val bvid: String? = null
)
