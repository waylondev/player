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
import platform.Foundation.NSURL
import platform.UIKit.UIView

/**
 * Video player component - iOS platform implementation
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

    // Create a remembered player reference
    val playerRef = remember { mutableListOf<AVPlayer?>(null) }

    // UIView factory for handling AVPlayerLayer
    val playerViewFactory = remember {
        {
            val view = UIView()
            val player = AVPlayer()
            playerRef[0] = player

            // Create AVPlayerLayer and add to view layer
            val playerLayer = AVPlayerLayer(player = player)
            playerLayer.frame = view.bounds
            playerLayer.videoGravity = AVPlayerLayerGravityResizeAspect
            view.layer.addSublayer(playerLayer)

            // Store layer reference for future updates
            view.layer.setValue(playerLayer, forKey = "playerLayer")
            view
        }
    }

    // Handle view updates
    val onViewUpdate = remember {
        { view: UIView ->
            val playerLayer = view.layer.valueForKey("playerLayer") as? AVPlayerLayer
            playerLayer?.frame = view.bounds
        }
    }

    Box(modifier = modifier.fillMaxSize()) {
        // UIKitView for embedding iOS native views
        UIKitView(
            factory = playerViewFactory,
            modifier = Modifier.fillMaxSize(),
            onResize = onViewUpdate
        )
    }

    // Update video URL
    DisposableEffect(currentUrl) {
        val player = playerRef[0]
        val nsUrl = NSURL(string = currentUrl)
        val asset = AVURLAsset(URL = nsUrl)
        val playerItem = AVPlayerItem(asset = asset)

        player?.replaceCurrentItemWithPlayerItem(playerItem)
        player?.play()

        onDispose {}
    }

    // Update playback state
    DisposableEffect(currentIsPlaying) {
        val player = playerRef[0]
        if (currentIsPlaying) {
            player?.play()
        } else {
            player?.pause()
        }
        onDispose {}
    }

    // Clean up resources
    DisposableEffect(Unit) {
        onDispose {
            val player = playerRef[0]
            player?.pause()
            player?.replaceCurrentItemWithPlayerItem(null)
        }
    }
}

// iOS constant definitions
private const val AVPlayerLayerGravityResizeAspect = "AVLayerVideoGravityResizeAspect"
