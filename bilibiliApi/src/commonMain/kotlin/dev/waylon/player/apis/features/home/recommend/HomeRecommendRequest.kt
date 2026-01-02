package dev.waylon.player.apis.features.home.recommend

import kotlinx.serialization.Serializable

/**
 * Parameters for Home Recommendation List Request
 * 
 * API: https://api.bilibili.com/x/web-interface/wbi/index/top/feed/rcmd
 * Method: GET
 * Authentication: Cookie (SESSDATA)
 */
@Serializable
data class HomeRecommendRequest(
    /** Refresh type */
    val fresh_type: Int = 3,
    /** Page size, default 12, max 60 */
    val ps: Int = 12,
    /** Refresh index, default 1 */
    val fresh_idx: Int = 1,
    /** Refresh index (hourly) */
    val fresh_idx_1h: Int? = null,
    /** Refresh load count */
    val brush: Int? = null,
    /** Feed version */
    val feed_version: String = "V8",
    /** Homepage version */
    val homepage_ver: Int = 1,
    /** Web location */
    val web_location: Int = 1345469,
    /** SEO info */
    val seo_info: Int = 1,
    /** Fetch row count */
    val fetch_row: Int? = null,
    /** Latest video count */
    val y_num: Int? = null,
    /** Last latest video count */
    val last_y_num: Int? = null,
    /** Screen size */
    val screen: String? = null,
    /** Last show list */
    val last_showlist: String? = null,
    /** Unique ID */
    val uniq_id: String? = null,
    /** Signature ID */
    val w_rid: String? = null,
    /** Timestamp */
    val wts: Long? = null
)