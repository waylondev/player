package dev.waylon.player.ui.components

import android.net.Uri
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import androidx.media3.common.MediaItem
import androidx.media3.common.Player
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.ui.PlayerView

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
    val context = LocalContext.current
    val currentUrl by rememberUpdatedState(url)
    val currentIsPlaying by rememberUpdatedState(isPlaying)
    val currentOnPlayStateChange by rememberUpdatedState(onPlayStateChange)

    // 创建ExoPlayer实例
    val exoPlayer = remember(context) {
        ExoPlayer.Builder(context)
            .build()
            .apply {
                // 设置媒体项
                setMediaItem(MediaItem.fromUri(Uri.parse(currentUrl)))
                // 准备播放
                prepare()
            }
    }

    // 监听播放状态变化
    DisposableEffect(exoPlayer) {
        val listener = object : Player.Listener {
            override fun onPlaybackStateChanged(playbackState: Int) {
                // 播放状态变化时的处理
            }

            override fun onIsPlayingChanged(isPlaying: Boolean) {
                currentOnPlayStateChange(isPlaying)
            }
        }
        exoPlayer.addListener(listener)

        // 清理资源
        onDispose {
            exoPlayer.removeListener(listener)
            exoPlayer.release()
        }
    }

    // 更新播放状态
    DisposableEffect(currentIsPlaying) {
        if (currentIsPlaying) {
            exoPlayer.play()
        } else {
            exoPlayer.pause()
        }
        onDispose {}
    }

    // 更新视频地址
    DisposableEffect(currentUrl) {
        exoPlayer.setMediaItem(MediaItem.fromUri(Uri.parse(currentUrl)))
        exoPlayer.prepare()
        exoPlayer.play()
        onDispose {}
    }

    Box(modifier = modifier.fillMaxSize()) {
        // AndroidView用于嵌入Android原生视图
        AndroidView(
            modifier = Modifier.fillMaxSize(),
            factory = { context ->
                PlayerView(context).apply {
                    player = exoPlayer
                    useController = true // 显示播放控制栏
                }
            }
        )
    }
}