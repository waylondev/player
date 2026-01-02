package dev.waylon.player.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

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
    // 在实际项目中，可以集成JavaFX MediaPlayer或其他JVM视频播放库
    CommonVideoPlayerComponent(
        modifier = modifier,
        url = url,
        isPlaying = isPlaying,
        onPlayStateChange = onPlayStateChange
    )
}