package dev.waylon.player.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

/**
 * Video player component - JVM platform implementation
 * Simple implementation for desktop platforms using common video player
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
        isPlaying = currentIsPlaying,
        onPlayStateChange = currentOnPlayStateChange
    )
}