package dev.waylon.player.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
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
import androidx.compose.runtime.Stable
import dev.waylon.player.model.VideoDetail
import dev.waylon.player.model.VideoInfo
import dev.waylon.player.service.ServiceProvider
import dev.waylon.player.service.VideoPlatformService
import dev.waylon.player.ui.components.VideoCard
import dev.waylon.player.ui.components.VideoPlayerComponent

/**
 * VideoStreamState data class for managing video stream loading state
 */
data class VideoStreamState(
    val isLoading: Boolean = false,
    val streamUrl: String? = null,
    val error: String? = null
)

/**
 * VideoDetailScreen state data class for unified state management
 */
@Stable
data class VideoDetailScreenState(
    val currentVideoId: String,
    val videoDetail: VideoDetail? = null,
    val relatedVideos: List<VideoInfo> = emptyList(),
    val isLoading: Boolean = true,
    val isLoadingRelated: Boolean = false,
    val error: String? = null,
    val relatedError: String? = null
) {
    /**
     * Updates the current video ID and resets related states
     */
    fun updateCurrentVideoId(newVideoId: String): VideoDetailScreenState {
        return copy(
            currentVideoId = newVideoId,
            videoDetail = null,
            isLoading = true,
            error = null
        )
    }

    /**
     * Updates the video detail and loading state
     */
    fun updateVideoDetail(detail: VideoDetail?, loading: Boolean = false, errorMessage: String? = null): VideoDetailScreenState {
        return copy(
            videoDetail = detail,
            isLoading = loading,
            error = errorMessage
        )
    }

    /**
     * Updates the related videos and loading state
     */
    fun updateRelatedVideos(videos: List<VideoInfo>, loading: Boolean = false, errorMessage: String? = null): VideoDetailScreenState {
        return copy(
            relatedVideos = videos,
            isLoadingRelated = loading,
            relatedError = errorMessage
        )
    }
}

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
    var screenState by remember {
        mutableStateOf(
            VideoDetailScreenState(
                currentVideoId = videoId
            )
        )
    }

    // Destructure state for easier access
    val currentVideoId = screenState.currentVideoId
    val videoDetail = screenState.videoDetail
    val relatedVideos = screenState.relatedVideos
    val isLoading = screenState.isLoading
    val isLoadingRelated = screenState.isLoadingRelated
    val error = screenState.error
    val relatedError = screenState.relatedError

    // Load video details
    LaunchedEffect(currentVideoId) {
        screenState = screenState.updateVideoDetail(null, true)
        try {
            val result = videoService.getVideoDetail(currentVideoId)
            screenState = screenState.updateVideoDetail(result)
        } catch (e: Exception) {
            screenState = screenState.updateVideoDetail(null, false, "Load failed: ${e.message}")
        }
    }

    // Load related videos
    LaunchedEffect(videoDetail) {
        if (videoDetail != null) {
            screenState = screenState.updateRelatedVideos(emptyList(), true)
            try {
                val result = videoService.getRelatedVideos(
                    videoId = videoDetail.videoInfo.id,
                    pageSize = 20
                )
                screenState = screenState.updateRelatedVideos(result)
            } catch (e: Exception) {
                screenState = screenState.updateRelatedVideos(emptyList(), false, "Failed to load related videos: ${e.message}")
            }
        }
    }

    Column(modifier = Modifier.fillMaxSize()) {
        // Back button
        IconButton(
            onClick = onBackClick,
            modifier = Modifier.padding(8.dp)
        ) {
            Icon(
                imageVector = Icons.Filled.ArrowBack,
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
                    val videoStreamState = remember(videoDetail) {
                        mutableStateOf<VideoStreamState?>(null)
                    }

                    // Load video stream URL
                    LaunchedEffect(videoDetail) {
                        if (videoStreamState.value == null && videoDetail != null) {
                            videoStreamState.value = VideoStreamState(isLoading = true)
                            try {
                                // Get video stream using the video service
                                val videoStream = videoService.getVideoStream(
                                    videoId = videoDetail.videoInfo.id,
                                    cid = videoDetail.videoInfo.cid ?: 0
                                )
                                videoStreamState.value = VideoStreamState(
                                    isLoading = false,
                                    streamUrl = videoStream.streams.firstOrNull()?.url
                                )
                            } catch (e: Exception) {
                                videoStreamState.value = VideoStreamState(
                                    isLoading = false,
                                    error = "Failed to load video stream: ${e.message}"
                                )
                            }
                        }
                    }

                    // Local playback state
                    var isPlaying by remember { mutableStateOf(false) }

                    // Destructure stream state
                    val streamState = videoStreamState.value ?: VideoStreamState(isLoading = true)
                    val videoStreamUrl = streamState.streamUrl
                    val streamLoading = streamState.isLoading
                    val streamError = streamState.error

                    if (streamLoading) {
                        Box(
                            modifier = Modifier.height(300.dp).fillMaxWidth(),
                            contentAlignment = Alignment.Center
                        ) {
                            CircularProgressIndicator()
                        }
                    } else if (streamError != null) {
                        Box(
                            modifier = Modifier.height(300.dp).fillMaxWidth(),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(text = streamError ?: "Unknown error")
                        }
                    } else if (videoStreamUrl != null) {
                        VideoPlayerComponent(
                            modifier = Modifier.height(300.dp).fillMaxWidth(),
                            url = videoStreamUrl!!,
                            isPlaying = isPlaying,
                            onPlayStateChange = { newState ->
                                isPlaying = newState
                            }
                        )
                    } else {
                        Box(
                            modifier = Modifier.height(300.dp).fillMaxWidth(),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(text = "No video stream available")
                        }
                    }
                }

                item {
                    // Video detail information
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(
                            text = videoDetail.videoInfo.title,
                            style = MaterialTheme.typography.headlineSmall,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(bottom = 8.dp)
                        )

                        Row(modifier = Modifier.padding(bottom = 16.dp)) {
                            Text(
                                text = "Author: ${videoDetail.videoInfo.author}",
                                modifier = Modifier.padding(end = 16.dp)
                            )
                            Text(text = "Play count: ${videoDetail.videoInfo.playCount}")
                        }

                        Text(
                            text = "Video description:",
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(bottom = 8.dp)
                        )
                        Text(text = videoDetail.fullDescription ?: "")
                    }
                }

                // Related videos section
                item {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(
                            text = "Related Videos",
                            style = MaterialTheme.typography.titleLarge,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(bottom = 16.dp)
                        )

                        // Related videos loading state
                        if (isLoadingRelated) {
                            Box(
                                modifier = Modifier.fillMaxWidth(),
                                contentAlignment = Alignment.Center
                            ) {
                                CircularProgressIndicator()
                            }
                        } else if (relatedError != null) {
                            Text(
                                text = relatedError,
                                color = MaterialTheme.colorScheme.error,
                                modifier = Modifier.padding(bottom = 16.dp)
                            )
                        } else if (relatedVideos.isEmpty()) {
                            Text(
                                text = "No related videos available",
                                modifier = Modifier.padding(bottom = 16.dp)
                            )
                        } else {
                            // Related videos list layout
                            Column(modifier = Modifier.fillMaxWidth()) {
                                for (video in relatedVideos.take(6)) {
                                    VideoCard(
                                        video = video,
                                        modifier = Modifier.padding(8.dp).clickable {
                                            // Handle related video click
                                            screenState = screenState.updateCurrentVideoId(video.id)
                                        }
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
