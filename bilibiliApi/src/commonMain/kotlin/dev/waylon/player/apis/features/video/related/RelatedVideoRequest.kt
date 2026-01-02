package dev.waylon.player.apis.features.video.related

import kotlinx.serialization.Serializable

/**
 * Related Video Request
 *
 * @property aid Video aid, optional (either aid or bvid is required)
 * @property bvid Video bvid, optional (either aid or bvid is required)
 */
@Serializable
data class RelatedVideoRequest(
    val aid: Long? = null,
    val bvid: String? = null
)