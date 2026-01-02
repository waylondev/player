package dev.waylon.player.apis.features.home.ranking

import kotlinx.serialization.Serializable

/**
 * Hot Ranking request parameters
 * 
 * @param rid Rank type ID (0: all, 1: animation, 3: music, 4: dance, 5: game, etc.)
 * @param day Rank period (1: daily, 3: 3-day, 7: weekly)
 */
@Serializable
data class HotRankingRequest(
    /** Rank type ID */
    val rid: Int = 0,
    /** Rank period */
    val day: Int = 1
)