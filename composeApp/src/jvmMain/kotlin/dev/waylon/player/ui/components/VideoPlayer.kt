package dev.waylon.player.ui.components

import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Modifier

/**
 * Video player component - JVM platform implementation
 * Simple implementation for desktop platforms using common video player
 */
@Composable
actual fun VideoPlayerComponent(
    modifier: Modifier,
    url: String,
    audioUrl: String?,
    isPlaying: Boolean,
    onPlayStateChange: (Boolean) -> Unit
) {
    val currentUrl by rememberUpdatedState(url)
    val currentAudioUrl by rememberUpdatedState(audioUrl)
    val currentIsPlaying by rememberUpdatedState(isPlaying)
    val currentOnPlayStateChange by rememberUpdatedState(onPlayStateChange)

    // Clean up resources
    DisposableEffect(Unit) {
        onDispose {
            // Cleanup logic for JVM platform
        }
    }

    // Use common video player implementation for JVM platform
    CommonVideoPlayerComponent(
        modifier = modifier,
        url = currentUrl,
        audioUrl = currentAudioUrl,
        isPlaying = currentIsPlaying,
        onPlayStateChange = currentOnPlayStateChange
    )
}