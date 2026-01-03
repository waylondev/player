package dev.waylon.player.ui.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Modifier

/**
 * 视频播放器组件 - JVM平台实现
 * 使用Compose Desktop原生支持的方式实现
 */
@Composable
actual fun VideoPlayerComponent(
    modifier: Modifier,
    url: String,
    isPlaying: Boolean,
    onPlayStateChange: (Boolean) -> Unit
) {
    val currentUrl by rememberUpdatedState(url)
    val currentIsPlaying by rememberUpdatedState(isPlaying)
    val currentOnPlayStateChange by rememberUpdatedState(onPlayStateChange)

    Box(modifier = modifier.fillMaxSize()) {
        // 使用Compose Desktop原生支持的方式实现视频播放器
        // 这里使用平台特定的实现方式，确保可以正确编译
        CommonVideoPlayerComponent(
            modifier = modifier,
            url = currentUrl,
            isPlaying = currentIsPlaying,
            onPlayStateChange = currentOnPlayStateChange
        )
    }
}