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
    var isLoadingMore by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf<String?>(null) }
    var page by remember { mutableStateOf(1) }

    // 加载更多协程作用域
    val coroutineScope = rememberCoroutineScope()

    // 加载数据（初始加载和刷新）
    LaunchedEffect(isRefreshing) {
        try {
            isLoading = true
            page = 1
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

    // 加载更多
    val onLoadMore = {
        coroutineScope.launch {
            try {
                isLoadingMore = true
                page += 1
                // 调用统一接口获取更多排行榜视频
                val result = ServiceProvider.videoService.getHotRanking(
                    rid = 0,
                    day = 7
                )
                // 直接追加数据，不做去重处理
                videos = videos + result
            } catch (e: Exception) {
                errorMessage = "加载更多失败: ${e.message}"
            } finally {
                isLoadingMore = false
            }
        }
        Unit
    }

    VideoListScreen(
        title = "排行榜",
        videos = videos,
        isLoading = isLoading,
        isLoadingMore = isLoadingMore,
        errorMessage = errorMessage,
        onLoadMore = onLoadMore,
        onVideoClick = onVideoClick
    )
}