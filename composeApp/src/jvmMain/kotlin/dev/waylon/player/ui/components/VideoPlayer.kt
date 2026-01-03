package dev.waylon.player.ui.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Modifier

/**
 * Video player component - JVM platform implementation
 * Uses Compose Desktop native support implementation
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

    Box(modifier = modifier.fillMaxSize()) {
        // Use Compose Desktop native support for video player implementation
        // Using platform-specific implementation to ensure proper compilation
        CommonVideoPlayerComponent(
            modifier = modifier,
            url = currentUrl,
            isPlaying = currentIsPlaying,
            onPlayStateChange = currentOnPlayStateChange
        )
    }
}