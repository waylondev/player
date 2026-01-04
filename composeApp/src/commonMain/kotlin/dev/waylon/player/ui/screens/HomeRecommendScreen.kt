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
 * HomeRecommendScreen state data class for unified state management
 */
data class HomeRecommendScreenState(
    val baseState: BaseListScreenState<VideoInfo> = BaseListScreenState(),
    val page: Int = 1
) {
    // Delegate properties
    val videos: List<VideoInfo> get() = baseState.items
    val isLoading: Boolean get() = baseState.isLoading
    val isLoadingMore: Boolean get() = baseState.isLoadingMore
    val errorMessage: String? get() = baseState.errorMessage

    /**
     * Updates the loading state
     */
    fun updateLoading(loading: Boolean): HomeRecommendScreenState {
        return copy(baseState = baseState.updateLoading(loading))
    }

    /**
     * Updates the videos list and error message
     */
    fun updateVideos(videos: List<VideoInfo>, errorMessage: String? = null): HomeRecommendScreenState {
        return copy(baseState = baseState.updateItems(videos, errorMessage))
    }

    /**
     * Updates the loading more state
     */
    fun updateLoadingMore(loadingMore: Boolean): HomeRecommendScreenState {
        return copy(baseState = baseState.updateLoadingMore(loadingMore))
    }

    /**
     * Updates the page number
     */
    fun updatePage(page: Int): HomeRecommendScreenState {
        return copy(page = page)
    }

    /**
     * Appends new videos to the list
     */
    fun appendVideos(newVideos: List<VideoInfo>): HomeRecommendScreenState {
        return copy(baseState = baseState.appendItems(newVideos))
    }
}

/**
 * Home recommendation video list
 */
@Composable
fun HomeRecommendScreen(
    isRefreshing: Boolean,
    onRefreshComplete: () -> Unit,
    onVideoClick: (String) -> Unit
) {
    var screenState by remember { mutableStateOf(HomeRecommendScreenState()) }

    // Load more coroutine scope
    val coroutineScope = rememberCoroutineScope()

    // Load data (initial load and refresh)
    LaunchedEffect(isRefreshing) {
        if (isRefreshing || screenState.videos.isEmpty()) {
            try {
                screenState = screenState.updateLoading(true)
                screenState = screenState.updatePage(1)
                // Call unified API to get home recommendation videos
                val result = ServiceProvider.videoService.getHomeRecommendations(
                    pageSize = 20,
                    page = screenState.page
                )
                screenState = screenState.updateVideos(result)
            } catch (e: Exception) {
                screenState = screenState.updateVideos(emptyList(), "Load failed: ${e.message}")
            } finally {
                screenState = screenState.updateLoading(false)
                onRefreshComplete()
            }
        }
    }

    // Load more
    val onLoadMore = {
        coroutineScope.launch {
            try {
                screenState = screenState.updateLoadingMore(true)
                screenState = screenState.updatePage(screenState.page + 1)
                // Call unified API to get more home recommendation videos
                val result = ServiceProvider.videoService.getHomeRecommendations(
                    pageSize = 20,
                    page = screenState.page
                )
                // Directly append data, no deduplication
                screenState = screenState.appendVideos(result)
            } catch (e: Exception) {
                screenState = screenState.updateVideos(screenState.videos, "Load more failed: ${e.message}")
            } finally {
                screenState = screenState.updateLoadingMore(false)
            }
        }
        Unit
    }

    VideoListScreen(
        title = "Home Recommendations",
        videos = screenState.videos,
        isLoading = screenState.isLoading,
        isLoadingMore = screenState.isLoadingMore,
        errorMessage = screenState.errorMessage,
        onLoadMore = onLoadMore,
        onVideoClick = onVideoClick
    )
}