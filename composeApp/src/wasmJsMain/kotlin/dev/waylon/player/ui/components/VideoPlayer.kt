package dev.waylon.player.ui.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

/**
 * Video player component - wasmJs platform implementation
 */
@Composable
actual fun VideoPlayerComponent(
    modifier: Modifier,
    url: String,
    isPlaying: Boolean,
    onPlayStateChange: (Boolean) -> Unit
) {
    // wasmJs platform uses CommonVideoPlayerComponent as implementation
    // In real projects, HTML5 Video element or other WebAssembly video playback libraries can be used
    CommonVideoPlayerComponent(
        modifier = modifier,
        url = url,
        isPlaying = isPlaying,
        onPlayStateChange = onPlayStateChange
    )
}