package dev.waylon.player.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

/**
 * 视频播放器组件 - 跨平台实现
 */
@Composable
expect fun VideoPlayerComponent(
    modifier: Modifier = Modifier,
    url: String,
    isPlaying: Boolean,
    onPlayStateChange: (Boolean) -> Unit
)

/**
 * 视频播放器组件 - 通用实现（用于不支持视频播放的平台或作为降级方案）
 */
@Composable
fun CommonVideoPlayerComponent(
    modifier: Modifier = Modifier,
    url: String,
    isPlaying: Boolean,
    onPlayStateChange: (Boolean) -> Unit
) {
    Box(modifier = modifier.fillMaxSize()) {
        Box(
            modifier = Modifier.fillMaxSize()
                .background(MaterialTheme.colorScheme.surfaceVariant)
                .clickable {
                    onPlayStateChange(!isPlaying)
                }
        ) {
            Text(
                text = "视频播放器",
                modifier = Modifier.align(Alignment.TopCenter)
                    .padding(16.dp),
                style = MaterialTheme.typography.headlineSmall
            )
            
            Text(
                text = "播放地址: $url",
                modifier = Modifier.align(Alignment.Center),
                style = MaterialTheme.typography.bodyMedium
            )
            
            Text(
                text = if (isPlaying) "播放中" else "已暂停",
                modifier = Modifier.align(Alignment.BottomCenter)
                    .padding(16.dp),
                style = MaterialTheme.typography.bodyMedium
            )
            
            // 播放状态指示器
            if (isPlaying) {
                CircularProgressIndicator(
                    modifier = Modifier.align(Alignment.Center)
                        .padding(16.dp)
                )
            }
        }
    }
}