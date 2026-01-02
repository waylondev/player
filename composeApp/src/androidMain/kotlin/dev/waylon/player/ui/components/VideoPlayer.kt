package dev.waylon.player.ui.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

/**
 * 视频播放器组件 - Android平台实现
 */
@Composable
actual fun VideoPlayerComponent(
    modifier: Modifier,
    url: String,
    isPlaying: Boolean,
    onPlayStateChange: (Boolean) -> Unit
) {
    // Android平台使用CommonVideoPlayerComponent作为实现
    // 在实际项目中，可以集成ExoPlayer或MediaPlayer
    CommonVideoPlayerComponent(
        modifier = modifier,
        url = url,
        isPlaying = isPlaying,
        onPlayStateChange = onPlayStateChange
    )
}