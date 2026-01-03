package dev.waylon.player.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import dev.waylon.player.model.VideoInfo
import dev.waylon.player.ui.theme.Corners
import io.kamel.image.KamelImage
import io.kamel.image.asyncPainterResource


/**
 * Tech-style video card component
 */
@Composable
fun VideoCard(
    video: VideoInfo,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier,
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant
        ),
        shape = Corners.lg
    ) {
        Column(
            modifier = Modifier.padding(12.dp)
        ) {
            // Video cover
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(180.dp)
                    .clip(Corners.md)
                    .border(
                        width = 2.dp,
                        brush = Brush.linearGradient(
                            colors = listOf(
                                MaterialTheme.colorScheme.primary,
                                MaterialTheme.colorScheme.secondary,
                                MaterialTheme.colorScheme.primary
                            )
                        ),
                        shape = Corners.md
                    )
            ) {
                // Load video cover using KamelImage with error handling
                KamelImage(
                    resource = asyncPainterResource(data = video.coverUrl),
                    contentDescription = video.title,
                    modifier = Modifier.fillMaxSize(),
                    onLoading = { painter, _ ->
                        // Show loading state
                        Box(
                            modifier = Modifier.fillMaxSize()
                                .background(MaterialTheme.colorScheme.surfaceVariant)
                        )
                    },
                    onFailure = { exception ->
                        // Show error state with fallback
                        Box(
                            modifier = Modifier.fillMaxSize()
                                .background(MaterialTheme.colorScheme.errorContainer),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = "Failed to load image",
                                color = MaterialTheme.colorScheme.onErrorContainer,
                                fontSize = 12.sp
                            )
                        }
                    }
                )

                // Video duration
                Box(
                    modifier = Modifier
                        .align(Alignment.BottomEnd)
                        .padding(12.dp)
                        .background(
                            color = Color.Black.copy(alpha = 0.8f),
                            shape = Corners.sm
                        )
                        .padding(4.dp, 6.dp)
                ) {
                    Text(
                        text = formatDuration(video.duration),
                        color = Color.White,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold,
                        lineHeight = 18.sp
                    )
                }

                // Play count
                Box(
                    modifier = Modifier
                        .align(Alignment.TopStart)
                        .padding(12.dp)
                        .background(
                            color = Color.Black.copy(alpha = 0.8f),
                            shape = Corners.sm
                        )
                        .padding(6.dp, 4.dp)
                ) {
                    Text(
                        text = formatPlayCount(video.playCount),
                        color = Color.White,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Medium,
                        lineHeight = 16.sp
                    )
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Video title
            Text(
                text = video.title,
                fontSize = 16.sp,
                fontWeight = FontWeight.SemiBold,
                color = MaterialTheme.colorScheme.onSurface,
                modifier = Modifier.fillMaxWidth(),
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
                lineHeight = 22.sp
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Author and category information
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Author information
                Text(
                    text = video.author,
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Medium,
                    color = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.weight(1f),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )

                // Category information
                if (video.category != null) {
                    Text(
                        text = video.category ?: "",
                        fontSize = 12.sp,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        modifier = Modifier.padding(start = 8.dp),
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }
            }
        }
    }
}

/**
 * Format video duration
 */
private fun formatDuration(seconds: Int): String {
    val hours = seconds / 3600
    val minutes = (seconds % 3600) / 60
    val secs = seconds % 60

    fun pad(num: Int): String {
        return if (num < 10) "0$num" else "$num"
    }

    return when {
        hours > 0 -> "${pad(hours)}:${pad(minutes)}:${pad(secs)}"
        minutes > 0 -> "${pad(minutes)}:${pad(secs)}"
        else -> "00:${pad(secs)}"
    }
}

/**
 * Format play count
 */
private fun formatPlayCount(playCount: Long): String {
    return when {
        playCount >= 100000000 -> "${(playCount / 100000000).toInt()}亿"
        playCount >= 10000 -> "${(playCount / 10000).toInt()}万"
        playCount >= 1000 -> "${(playCount / 1000).toInt()}k"
        else -> playCount.toString()
    }
}