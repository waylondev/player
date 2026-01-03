package dev.waylon.player.ui.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import dev.waylon.player.model.VideoDetail
import dev.waylon.player.service.ServiceProvider
import dev.waylon.player.service.VideoPlatformService
import dev.waylon.player.ui.components.VideoPlayerComponent

/**
 * Video detail screen
 * Displays video detail information and plays video
 */
@Composable
fun VideoDetailScreen(
    videoId: String,
    onBackClick: () -> Unit
) {
    val videoService: VideoPlatformService = ServiceProvider.videoService
    var videoDetail by remember { mutableStateOf<VideoDetail?>(null) }
    var isLoading by remember { mutableStateOf(true) }
    var error by remember { mutableStateOf<String?>(null) }

    // Load video details
    LaunchedEffect(videoId) {
        isLoading = true
        error = null
        try {
            videoDetail = videoService.getVideoDetail(videoId)
        } catch (e: Exception) {
            error = e.message
        } finally {
            isLoading = false
        }
    }

    Column(modifier = Modifier.fillMaxSize()) {
        // Back button
        IconButton(
            onClick = onBackClick,
            modifier = Modifier.padding(8.dp)
        ) {
            Icon(
                imageVector = Icons.Default.ArrowBack,
                contentDescription = "Back"
            )
        }

        if (isLoading) {
            // Center the loading indicator
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        } else if (error != null) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(text = "Loading failed: $error")
            }
        } else if (videoDetail != null) {
            LazyColumn(modifier = Modifier.fillMaxSize()) {
                item {
                    // Video playback area
                    var isPlaying by remember { mutableStateOf(false) }

                    VideoPlayerComponent(
                        modifier = Modifier.height(300.dp).fillMaxWidth(),
                        // Use sample video URL for testing
                        url = "https://commondatastorage.googleapis.com/gtv-videos-bucket/sample/BigBuckBunny.mp4",
                        isPlaying = isPlaying,
                        onPlayStateChange = { newState ->
                            isPlaying = newState
                        }
                    )
                }

                item {
                    // Video detail information
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(
                            text = videoDetail!!.videoInfo.title,
                            style = MaterialTheme.typography.headlineSmall,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(bottom = 8.dp)
                        )

                        Row(modifier = Modifier.padding(bottom = 16.dp)) {
                            Text(
                                text = "Author: ${videoDetail!!.videoInfo.author}",
                                modifier = Modifier.padding(end = 16.dp)
                            )
                            Text(text = "Play count: ${videoDetail!!.videoInfo.playCount}")
                        }

                        Text(
                            text = "Video description:",
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(bottom = 8.dp)
                        )
                        Text(text = videoDetail!!.fullDescription ?: "")
                    }
                }
            }
        }
    }
}
