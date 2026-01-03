package dev.waylon.player.ui.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

/**
 * 视频播放器组件 - JVM平台实现
 */
@Composable
actual fun VideoPlayerComponent(
    modifier: Modifier,
    url: String,
    isPlaying: Boolean,
    onPlayStateChange: (Boolean) -> Unit
) {
    // JVM平台使用CommonVideoPlayerComponent作为实现
    // 在实际项目中，可以集成JavaFX Media或其他JVM视频播放库
    CommonVideoPlayerComponent(
        modifier = modifier,
        url = url,
        isPlaying = isPlaying,
        onPlayStateChange = onPlayStateChange
    )
}