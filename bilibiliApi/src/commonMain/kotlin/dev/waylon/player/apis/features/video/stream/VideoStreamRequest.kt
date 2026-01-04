package dev.waylon.player.apis.features.video.stream

import kotlinx.serialization.Serializable

/**
 * Video stream request parameters
 * 
 * @param aid Video avid
 * @param bvid Video bvid
 * @param cid Video cid
 * @param qn Video quality (16: 360P, 32: 480P, 64: 720P, 80: 1080P, etc.)
 * @param fnval Video format (1: MP4, 16: DASH, 64: HDR, 128: 4K, etc.)
 * @param fnver Video version (always 0)
 * @param fourk Whether to allow 4K video (0: no, 1: yes)
 */
@Serializable
data class VideoStreamRequest(
    /** Video avid (optional, use either avid or bvid) */
    val aid: Int? = null,
    /** Video bvid (optional, use either avid or bvid) */
    val bvid: String? = null,
    /** Video cid (required) */
    val cid: Long,
    /** Video quality */
    val qn: Int? = 32,
    /** Video format */
    val fnval: Int? = 4048,
    /** Video version (always 0) */
    val fnver: Int? = 0,
    /** Whether to allow 4K video */
    val fourk: Int? = 0
)