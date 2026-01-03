package dev.waylon.player.ui.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Modifier
import androidx.compose.ui.awt.SwingPanel
import uk.co.caprica.vlcj.player.component.EmbeddedMediaPlayerComponent

/**
 * Video player component - JVM platform implementation
 * Uses VLCJ library for video playback on desktop platforms
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

    // Create media player component
    val mediaPlayerComponent = remember {
        EmbeddedMediaPlayerComponent()
    }

    // Update playback state
    DisposableEffect(currentIsPlaying) {
        if (currentIsPlaying) {
            mediaPlayerComponent.mediaPlayer().controls().play()
        } else {
            mediaPlayerComponent.mediaPlayer().controls().pause()
        }
        onDispose {}
    }

    // Update video URL
    DisposableEffect(currentUrl) {
        if (currentUrl.isNotBlank()) {
            mediaPlayerComponent.mediaPlayer().media().play(currentUrl)
        }
        onDispose {}
    }

    // Clean up resources
    DisposableEffect(Unit) {
        onDispose {
            mediaPlayerComponent.mediaPlayer().controls().stop()
            mediaPlayerComponent.mediaPlayer().release()
        }
    }

    Box(modifier = modifier.fillMaxSize()) {
        // Use SwingPanel to embed VLCJ media player
        SwingPanel(
            modifier = Modifier.fillMaxSize(),
            factory = { mediaPlayerComponent }
        )
    }
}