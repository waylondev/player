package dev.waylon.player.ui.screens

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import dev.waylon.player.model.VideoInfo
import dev.waylon.player.service.ServiceProvider

/**
 * RankingScreen state data class for unified state management
 */
typealias RankingScreenState = BaseListScreenState<VideoInfo>

/**
 * Ranking video list screen
 */
@Composable
fun RankingScreen(
    isRefreshing: Boolean,
    onRefreshComplete: () -> Unit,
    onVideoClick: (String) -> Unit
) {
    var screenState by remember { mutableStateOf(RankingScreenState()) }

    // Load data (initial load and refresh)
    LaunchedEffect(isRefreshing) {
        if (isRefreshing || screenState.items.isEmpty()) {
            try {
                screenState = screenState.updateLoading(true)
                // Call unified API to get ranking videos
                val result = ServiceProvider.videoService.getHotRanking(
                    rid = 0,
                    day = 7 // Use 7-day hot data as ranking
                )
                screenState = screenState.updateItems(result)
            } catch (e: Exception) {
                screenState = screenState.updateItems(emptyList(), "Loading failed: ${e.message}")
            } finally {
                screenState = screenState.updateLoading(false)
                onRefreshComplete()
            }
        }
    }

    VideoListScreen(
        title = "Ranking",
        videos = screenState.items,
        isLoading = screenState.isLoading,
        isLoadingMore = false, // Ranking doesn't need load more
        errorMessage = screenState.errorMessage,
        onLoadMore = null, // Ranking doesn't need load more
        onVideoClick = onVideoClick
    )
}