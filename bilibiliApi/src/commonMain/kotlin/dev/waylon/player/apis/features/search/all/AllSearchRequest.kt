package dev.waylon.player.apis.features.search.all

import kotlinx.serialization.Serializable

/**
 * All Search request parameters
 */
@Serializable
data class AllSearchRequest(
    /** Search keyword */
    val keyword: String,
    /** Page number */
    val page: Int = 1,
    /** Sort order */
    val order: String = "totalrank",
    /** Duration filter */
    val duration: Int = 0,
    /** Category ID */
    val tids_1: Int = 0
)