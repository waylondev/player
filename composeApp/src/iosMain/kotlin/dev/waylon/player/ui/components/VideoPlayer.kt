package dev.waylon.player.ui.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.UIKitView
import kotlinx.cinterop.ExperimentalForeignApi
import platform.AVFoundation.AVPlayer
import platform.AVFoundation.AVPlayerItem
import platform.AVFoundation.AVPlayerLayer
import platform.AVFoundation.AVURLAsset
import platform.CoreMedia.CMTimeGetSeconds
import platform.CoreMedia.kCMTimeZero
import platform.Foundation.NSURL
import platform.QuartzCore.CALayer
import platform.UIKit.UIView

/**
 * 视频播放器组件 - iOS平台实现
 */
@OptIn(ExperimentalForeignApi::class)
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

    // 创建一个记住的播放器引用
    val playerRef = remember { mutableListOf<AVPlayer?>(null) }

    // 用于处理AVPlayerLayer的UIView工厂
    val playerViewFactory = remember { {
        val view = UIView()
        val player = AVPlayer()
        playerRef[0] = player

        // 创建AVPlayerLayer并添加到视图层
        val playerLayer = AVPlayerLayer(player = player)
        playerLayer.frame = view.bounds
        playerLayer.videoGravity = AVPlayerLayerGravityResizeAspect
        view.layer.addSublayer(playerLayer)

        // 存储layer引用以便后续更新
        view.layer.setValue(playerLayer, forKey = "playerLayer")
        view
    } }

    // 处理视图更新
    val onViewUpdate = remember { {
        view: UIView ->
        val playerLayer = view.layer.valueForKey("playerLayer") as? AVPlayerLayer
        playerLayer?.frame = view.bounds
    } }

    Box(modifier = modifier.fillMaxSize()) {
        // UIKitView用于嵌入iOS原生视图
        UIKitView(
            factory = playerViewFactory,
            modifier = Modifier.fillMaxSize(),
            onResize = onViewUpdate
        )
    }

    // 更新视频地址
    DisposableEffect(currentUrl) {
        val player = playerRef[0]
        val nsUrl = NSURL(string = currentUrl)
        val asset = AVURLAsset(URL = nsUrl)
        val playerItem = AVPlayerItem(asset = asset)
        
        player?.replaceCurrentItemWithPlayerItem(playerItem)
        player?.play()
        
        onDispose {}
    }

    // 更新播放状态
    DisposableEffect(currentIsPlaying) {
        val player = playerRef[0]
        if (currentIsPlaying) {
            player?.play()
        } else {
            player?.pause()
        }
        onDispose {}
    }

    // 清理资源
    DisposableEffect(Unit) {
        onDispose {
            val player = playerRef[0]
            player?.pause()
            player?.replaceCurrentItemWithPlayerItem(null)
        }
    }
}

// iOS常量定义
private const val AVPlayerLayerGravityResizeAspect = "AVLayerVideoGravityResizeAspect"
