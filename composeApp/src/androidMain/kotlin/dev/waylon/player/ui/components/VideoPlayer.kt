package dev.waylon.player.ui.components

import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import androidx.media3.common.MediaItem
import androidx.media3.common.Player
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.ui.PlayerView
import dev.waylon.player.apis.common.util.Logger

/**
 * Video player component - Android platform implementation
 */
@Composable
actual fun VideoPlayerComponent(
    modifier: Modifier,
    url: String,
    audioUrl: String?,
    isPlaying: Boolean,
    onPlayStateChange: (Boolean) -> Unit
) {
    val context = LocalContext.current
    val currentUrl by rememberUpdatedState(url)
    val currentIsPlaying by rememberUpdatedState(isPlaying)
    val currentOnPlayStateChange by rememberUpdatedState(onPlayStateChange)

    // 使用统一的状态管理类，保持状态管理风格一致
    val playerState = remember { VideoPlayerState(url, isPlaying) }

    // Create ExoPlayer instance
    val exoPlayer = remember(context) {
        Logger.i("VideoPlayer", "Creating ExoPlayer instance for URL: $url")
        ExoPlayer.Builder(context)
            .build()
    }

    // Listen for playback state changes
    DisposableEffect(exoPlayer) {
        val listener = object : Player.Listener {
            override fun onPlaybackStateChanged(playbackState: Int) {
                when (playbackState) {
                    Player.STATE_BUFFERING -> playerState.setLoading(true)
                    Player.STATE_READY -> playerState.setLoading(false)
                    Player.STATE_ENDED -> {
                        currentOnPlayStateChange(false)
                        playerState.setLoading(false)
                    }

                    Player.STATE_IDLE -> playerState.setLoading(false)
                }
            }

            override fun onIsPlayingChanged(isPlaying: Boolean) {
                currentOnPlayStateChange(isPlaying)
            }

            override fun onPlayerError(error: androidx.media3.common.PlaybackException) {
                Logger.e("VideoPlayer", "Playback error: ${error.message}", error)
                playerState.setError("Video playback error: ${error.message}")
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

    // Update video URL and audio URL
    LaunchedEffect(currentUrl, audioUrl) {
        if (currentUrl.isNotBlank()) {
            try {
                playerState.setError(null)
                playerState.setLoading(true)

                Logger.i("VideoPlayer", "Loading video with URL: $currentUrl, Audio URL: $audioUrl")
                
                // Create media source based on whether we have separate audio and video streams
                val mediaSource = if (audioUrl != null) {
                    // For DASH format with separate audio and video streams,
                    // we need to use a different approach. ExoPlayer requires a manifest file
                    // that describes both audio and video streams.
                    // In this case, we'll use the video URL which should be the DASH manifest
                    Logger.i("VideoPlayer", "Using DASH format with combined manifest")
                    androidx.media3.exoplayer.source.DashMediaSource.Factory(
                        androidx.media3.datasource.DefaultDataSource.Factory(context)
                    )
                        .createMediaSource(MediaItem.fromUri(Uri.parse(currentUrl)))
                } else {
                    // Fallback to progressive media source for single stream URLs
                    Logger.i("VideoPlayer", "Using progressive media source for single stream")
                    androidx.media3.exoplayer.source.ProgressiveMediaSource.Factory(
                        androidx.media3.datasource.DefaultDataSource.Factory(context)
                    )
                        .createMediaSource(MediaItem.fromUri(Uri.parse(currentUrl)))
                }
                
                // Set the media source and prepare the player
                exoPlayer.setMediaSource(mediaSource)
                exoPlayer.prepare()

                // Don't auto-play when URL changes, respect the current playback state
                if (currentIsPlaying) {
                    exoPlayer.play()
                }
            } catch (e: Exception) {
                playerState.setError("Failed to load video: ${e.message}")
                Logger.e("VideoPlayer", "Error loading video: ${e.message}", e)
            }
        }
    }

    Box(modifier = modifier.fillMaxSize()) {
        if (playerState.error.value != null) {
            // Show error message
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(MaterialTheme.colorScheme.errorContainer),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = playerState.error.value ?: "Unknown error",
                    color = MaterialTheme.colorScheme.onErrorContainer
                )
            }
        } else if (playerState.isLoading.value) {
            // Show loading indicator
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(MaterialTheme.colorScheme.surfaceVariant),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "Loading video...",
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        } else {
            // Show video player
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
}