package dev.waylon.player.ui.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

/**
 * Video player component - JS platform implementation
 * Uses CommonVideoPlayerComponent as implementation to ensure proper compilation
 */
@Composable
actual fun VideoPlayerComponent(
    modifier: Modifier,
    url: String,
    isPlaying: Boolean,
    onPlayStateChange: (Boolean) -> Unit
) {
    // JS platform uses CommonVideoPlayerComponent as implementation
    CommonVideoPlayerComponent(
        modifier = modifier,
        url = url,
        isPlaying = isPlaying,
        onPlayStateChange = onPlayStateChange
    )
}