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
 * 排行榜视频列表
 */
@Composable
fun RankingScreen(
    isRefreshing: Boolean,
    onRefreshComplete: () -> Unit,
    onVideoClick: (String) -> Unit
) {
    // 视频列表状态
    var videos by remember { mutableStateOf<List<VideoInfo>>(emptyList()) }
    var isLoading by remember { mutableStateOf(true) }
    var errorMessage by remember { mutableStateOf<String?>(null) }

    // 加载数据（初始加载和刷新）
    LaunchedEffect(isRefreshing) {
        try {
            isLoading = true
            // 调用统一接口获取排行榜视频
            val result = ServiceProvider.videoService.getHotRanking(
                rid = 0,
                day = 7 // 使用7天热门数据作为排行榜
            )
            videos = result
        } catch (e: Exception) {
            errorMessage = "加载失败: ${e.message}"
        } finally {
            isLoading = false
            onRefreshComplete()
        }
    }

    VideoListScreen(
        title = "排行榜",
        videos = videos,
        isLoading = isLoading,
        isLoadingMore = false, // 排行榜不需要加载更多
        errorMessage = errorMessage,
        onLoadMore = null, // 排行榜不需要加载更多
        onVideoClick = onVideoClick
    )
}