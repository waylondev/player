package dev.waylon.player.ui.screens

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import dev.waylon.player.model.VideoInfo
import dev.waylon.player.service.ServiceProvider
import kotlinx.coroutines.launch

/**
 * Home recommendation video list
 */
@Composable
fun HomeRecommendScreen(
    isRefreshing: Boolean,
    onRefreshComplete: () -> Unit,
    onVideoClick: (String) -> Unit
) {
    // Video list state
    var videos by remember { mutableStateOf<List<VideoInfo>>(emptyList()) }
    var isLoading by remember { mutableStateOf(true) }
    var isLoadingMore by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf<String?>(null) }
    var page by remember { mutableStateOf(1) }

    // Load more coroutine scope
    val coroutineScope = rememberCoroutineScope()

    // Load data (initial load and refresh)
    LaunchedEffect(isRefreshing) {
        try {
            isLoading = true
            page = 1
            // Call unified API to get home recommendation videos
            val result = ServiceProvider.videoService.getHomeRecommendations(
                pageSize = 20,
                page = page
            )
            videos = result
        } catch (e: Exception) {
            errorMessage = "Load failed: ${e.message}"
        } finally {
            isLoading = false
            onRefreshComplete()
        }
    }

    // Load more
    val onLoadMore = {
        coroutineScope.launch {
            try {
                isLoadingMore = true
                page += 1
                // Call unified API to get more home recommendation videos
                val result = ServiceProvider.videoService.getHomeRecommendations(
                    pageSize = 20,
                    page = page
                )
                // Directly append data, no deduplication
                videos = videos + result
            } catch (e: Exception) {
                errorMessage = "Load more failed: ${e.message}"
            } finally {
                isLoadingMore = false
            }
        }
        Unit
    }

    VideoListScreen(
        title = "Home Recommendations",
        videos = videos,
        isLoading = isLoading,
        isLoadingMore = isLoadingMore,
        errorMessage = errorMessage,
        onLoadMore = onLoadMore,
        onVideoClick = onVideoClick
    )
}