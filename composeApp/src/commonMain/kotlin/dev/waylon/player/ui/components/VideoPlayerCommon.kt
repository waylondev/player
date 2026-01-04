package dev.waylon.player.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

/**
 * Video player component - Cross-platform implementation
 */
@Composable
expect fun VideoPlayerComponent(
    modifier: Modifier = Modifier,
    url: String,
    audioUrl: String? = null,
    isPlaying: Boolean,
    onPlayStateChange: (Boolean) -> Unit
)

/**
 * Video player component - Common implementation (for platforms that don't support video playback or as fallback)
 */
@Composable
fun CommonVideoPlayerComponent(
    modifier: Modifier = Modifier,
    url: String,
    audioUrl: String? = null,
    isPlaying: Boolean,
    onPlayStateChange: (Boolean) -> Unit
) {
    Box(modifier = modifier.fillMaxSize()) {
        Box(
            modifier = Modifier.fillMaxSize()
                .background(MaterialTheme.colorScheme.surfaceVariant)
                .clickable {
                    onPlayStateChange(!isPlaying)
                }
        ) {
            // Video player title
            Text(
                text = "Video Player",
                modifier = Modifier.align(Alignment.TopCenter)
                    .padding(16.dp),
                style = MaterialTheme.typography.headlineSmall
            )

            // Video playback URL (partial display)
            Text(
                text = "Playback URL: ${if (url.length > 50) "${url.substring(0, 50)}..." else url}",
                modifier = Modifier.align(Alignment.Center)
                    .padding(16.dp),
                style = MaterialTheme.typography.bodyMedium,
                maxLines = 2
            )

            // Playback state
            Text(
                text = if (isPlaying) "Playing" else "Paused",
                modifier = Modifier.align(Alignment.BottomCenter)
                    .padding(16.dp),
                style = MaterialTheme.typography.bodyMedium
            )

            // Platform support information
            Text(
                text = "Video playback functionality for current platform is under development...",
                modifier = Modifier.align(Alignment.BottomStart)
                    .padding(16.dp),
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )

            // Playback state indicator
            if (isPlaying) {
                CircularProgressIndicator(
                    modifier = Modifier.align(Alignment.Center)
                        .padding(16.dp),
                    color = MaterialTheme.colorScheme.primary
                )
            }
        }
    }
}