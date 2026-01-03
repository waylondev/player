package dev.waylon.player.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import dev.waylon.player.model.VideoInfo
import dev.waylon.player.ui.components.VideoCard
import kotlinx.coroutines.flow.distinctUntilChanged

/**
 * 通用视频列表页面
 */
@Composable
fun VideoListScreen(
    title: String,
    videos: List<VideoInfo>,
    isLoading: Boolean,
    isLoadingMore: Boolean,
    errorMessage: String?,
    onLoadMore: (() -> Unit)?,
    onVideoClick: (String) -> Unit
) {
    Column(modifier = Modifier.fillMaxSize()) {
        // 错误信息
        errorMessage?.let {
            Text(
                text = it,
                color = androidx.compose.material3.MaterialTheme.colorScheme.error,
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth()
            )
        }

        // 加载状态
        if (isLoading) {
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                CircularProgressIndicator(
                    color = androidx.compose.material3.MaterialTheme.colorScheme.primary
                )
                Text(
                    text = "加载中...",
                    modifier = Modifier.padding(top = 16.dp),
                    color = androidx.compose.material3.MaterialTheme.colorScheme.primary
                )
            }
        } else if (videos.isEmpty()) {
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "暂无视频数据",
                    style = androidx.compose.material3.MaterialTheme.typography.bodyLarge,
                    color = androidx.compose.material3.MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        } else {
            // 视频列表 - 自适应布局，每个卡片最小宽度200dp
            val gridState = rememberLazyGridState()

            // 检测滚动到底部，触发加载更多
            if (onLoadMore != null) {
                LaunchedEffect(gridState) {
                    snapshotFlow {
                        val visibleItems = gridState.layoutInfo.visibleItemsInfo
                        if (visibleItems.isNotEmpty()) visibleItems.last().index else -1
                    }
                        .distinctUntilChanged()
                        .collect {
                            if (it >= gridState.layoutInfo.totalItemsCount - 5 && !isLoadingMore) {
                                onLoadMore()
                            }
                        }
                }
            }

            LazyVerticalGrid(
                state = gridState,
                columns = GridCells.Adaptive(minSize = 200.dp),
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(8.dp)
            ) {
                items(videos) {
                    VideoCard(
                        video = it,
                        modifier = Modifier.padding(8.dp).clickable {
                            onVideoClick(it.id)
                        }
                    )
                }

                // 加载更多指示器
                if (isLoadingMore) {
                    item {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            CircularProgressIndicator(
                                color = androidx.compose.material3.MaterialTheme.colorScheme.primary
                            )
                            Text(
                                text = "加载更多...",
                                modifier = Modifier.padding(top = 8.dp),
                                color = androidx.compose.material3.MaterialTheme.colorScheme.primary
                            )
                        }
                    }
                }
            }
        }
    }
}