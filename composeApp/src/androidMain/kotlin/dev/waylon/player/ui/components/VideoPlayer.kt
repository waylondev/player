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
 * Video player component - Android platform implementation
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

    // Create ExoPlayer instance
    val exoPlayer = remember(context) {
        ExoPlayer.Builder(context)
            .build()
            .apply {
                // Set media item
                setMediaItem(MediaItem.fromUri(Uri.parse(currentUrl)))
                // Prepare for playback
                prepare()
            }
    }

    // Listen for playback state changes
    DisposableEffect(exoPlayer) {
        val listener = object : Player.Listener {
            override fun onPlaybackStateChanged(playbackState: Int) {
                // Handle playback state changes
            }

            override fun onIsPlayingChanged(isPlaying: Boolean) {
                currentOnPlayStateChange(isPlaying)
            }
        }
        exoPlayer.addListener(listener)

        // Clean up resources
        onDispose {
            exoPlayer.removeListener(listener)
            exoPlayer.release()
        }
    }

    // Update playback state
    DisposableEffect(currentIsPlaying) {
        if (currentIsPlaying) {
            exoPlayer.play()
        } else {
            exoPlayer.pause()
        }
        onDispose {}
    }

    // Update video URL
    DisposableEffect(currentUrl) {
        if (currentUrl.isNotBlank()) {
            try {
                exoPlayer.setMediaItem(MediaItem.fromUri(Uri.parse(currentUrl)))
                exoPlayer.prepare()
                // Don't auto-play when URL changes, respect the current playback state
                if (currentIsPlaying) {
                    exoPlayer.play()
                }
            } catch (e: Exception) {
                // Handle URL parsing or media loading errors
                e.printStackTrace()
            }
        }
        onDispose {}
    }

    Box(modifier = modifier.fillMaxSize()) {
        // AndroidView for embedding Android native views
        AndroidView(
            modifier = Modifier.fillMaxSize(),
            factory = { context ->
                PlayerView(context).apply {
                    player = exoPlayer
                    useController = true // Show playback controls
                }
            }
        )
    }
}