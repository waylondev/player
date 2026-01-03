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
 * Ranking video list screen
 */
@Composable
fun RankingScreen(
    isRefreshing: Boolean,
    onRefreshComplete: () -> Unit,
    onVideoClick: (String) -> Unit
) {
    // Video list state
    var videos by remember { mutableStateOf<List<VideoInfo>>(emptyList()) }
    var isLoading by remember { mutableStateOf(true) }
    var errorMessage by remember { mutableStateOf<String?>(null) }

    // Load data (initial load and refresh)
    LaunchedEffect(isRefreshing) {
        try {
            isLoading = true
            // Call unified API to get ranking videos
            val result = ServiceProvider.videoService.getHotRanking(
                rid = 0,
                day = 7 // Use 7-day hot data as ranking
            )
            videos = result
        } catch (e: Exception) {
            errorMessage = "Loading failed: ${e.message}"
        } finally {
            isLoading = false
            onRefreshComplete()
        }
    }

    VideoListScreen(
        title = "Ranking",
        videos = videos,
        isLoading = isLoading,
        isLoadingMore = false, // Ranking doesn't need load more
        errorMessage = errorMessage,
        onLoadMore = null, // Ranking doesn't need load more
        onVideoClick = onVideoClick
    )
}