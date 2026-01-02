package dev.waylon.player.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import dev.waylon.player.model.VideoInfo
import dev.waylon.player.service.ServiceProvider
import dev.waylon.player.ui.components.VideoCard

/**
 * 搜索视频页面
 */
@Composable
fun SearchScreen(
    isRefreshing: Boolean,
    onRefreshComplete: () -> Unit,
    onVideoClick: (String) -> Unit
) {
    // 搜索状态
    var searchKeyword by remember { mutableStateOf("") }
    var searchResults by remember { mutableStateOf<List<VideoInfo>>(emptyList()) }
    var isSearching by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf<String?>(null) }

    // 执行搜索
    LaunchedEffect(searchKeyword, isRefreshing) {
        // 如果没有搜索关键词，只在刷新时不做处理
        if (searchKeyword.isBlank() && !isRefreshing) {
            searchResults = emptyList()
            errorMessage = null
            onRefreshComplete()
            return@LaunchedEffect
        }

        // 如果是刷新状态但没有搜索关键词，直接完成刷新
        if (isRefreshing && searchKeyword.isBlank()) {
            onRefreshComplete()
            return@LaunchedEffect
        }

        // 如果有搜索关键词，执行搜索
        if (searchKeyword.isNotBlank()) {
            try {
                isSearching = true
                errorMessage = null
                // 调用统一接口搜索视频
                val result = ServiceProvider.videoService.searchVideos(
                    keyword = searchKeyword,
                    pageSize = 20
                )
                searchResults = result
            } catch (e: Exception) {
                errorMessage = "搜索失败: ${e.message}"
                searchResults = emptyList()
            } finally {
                isSearching = false
                onRefreshComplete()
            }
        }
    }

    Column(modifier = Modifier.fillMaxWidth()) {
        // 搜索栏
        OutlinedTextField(
            value = searchKeyword,
            onValueChange = { searchKeyword = it },
            label = { Text(text = "搜索视频") },
            placeholder = { Text(text = "输入视频标题、UP主名称等") },
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = "搜索"
                )
            },
            trailingIcon = {
                if (searchKeyword.isNotBlank()) {
                    IconButton(onClick = { searchKeyword = "" }) {
                        Icon(
                            imageVector = Icons.Default.Clear,
                            contentDescription = "清除"
                        )
                    }
                }
            },
            singleLine = true,
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Text
            ),
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        )

        Spacer(modifier = Modifier.height(8.dp))

        // 搜索结果或状态显示
        if (isSearching) {
            // 搜索中状态
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(modifier = Modifier.height(64.dp))
                CircularProgressIndicator(
                    color = MaterialTheme.colorScheme.primary
                )
                Text(
                    text = "搜索中...",
                    modifier = Modifier.padding(top = 16.dp),
                    color = MaterialTheme.colorScheme.primary
                )
            }
        } else if (searchKeyword.isBlank()) {
            // 搜索提示
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(modifier = Modifier.height(64.dp))
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = "搜索",
                    tint = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Text(
                    text = "请输入搜索关键词",
                    modifier = Modifier.padding(top = 16.dp),
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        } else if (errorMessage != null) {
            // 错误信息
            Text(
                text = errorMessage!!,
                color = MaterialTheme.colorScheme.error,
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth()
            )
        } else if (searchResults.isEmpty()) {
            // 无结果提示
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(modifier = Modifier.height(64.dp))
                Text(
                    text = "未找到相关视频",
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        } else {
            // 搜索结果列表 - 自适应布局，每个卡片最小宽度200dp
            LazyVerticalGrid(
                columns = GridCells.Adaptive(minSize = 200.dp),
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(8.dp)
            ) {
                items(searchResults) {
                    VideoCard(
                        video = it,
                        modifier = Modifier.padding(8.dp).clickable {
                            onVideoClick(it.id)
                        }
                    )
                }
            }
        }
    }
}
