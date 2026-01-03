package dev.waylon.player.ui.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Modifier
import kotlinx.browser.document
import org.w3c.dom.HTMLVideoElement

/**
 * Video player component - wasmJs platform implementation
 * Uses HTML5 Video element for WebAssembly video playback
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

    // Create and manage video element for WASM platform
    DisposableEffect(Unit) {
        val videoContainer = document.createElement("div") as org.w3c.dom.HTMLDivElement
        videoContainer.style.width = "100%"
        videoContainer.style.height = "100%"

        val video = document.createElement("video") as HTMLVideoElement
        video.id = "wasm-video-player"
        video.style.width = "100%"
        video.style.height = "100%"
        video.controls = true
        video.autoplay = false

        videoContainer.appendChild(video)
        document.body?.appendChild(videoContainer)

        // Set up event listeners
        val playListener = { _: dynamic ->
            currentOnPlayStateChange(true)
        }

        val pauseListener = { _: dynamic ->
            currentOnPlayStateChange(false)
        }

        video.addEventListener("play", playListener)
        video.addEventListener("pause", pauseListener)

        onDispose {
            video.removeEventListener("play", playListener)
            video.removeEventListener("pause", pauseListener)
            document.body?.removeChild(videoContainer)
        }
    }

    // Update video URL
    DisposableEffect(currentUrl) {
        if (currentUrl.isNotBlank()) {
            val video = document.getElementById("wasm-video-player") as? HTMLVideoElement
            video?.src = currentUrl
        }
        onDispose {}
    }

    // Update playback state
    DisposableEffect(currentIsPlaying) {
        val video = document.getElementById("wasm-video-player") as? HTMLVideoElement
        if (currentIsPlaying) {
            video?.play()
        } else {
            video?.pause()
        }
        onDispose {}
    }

    // For WASM platform, we use a placeholder since the video is rendered directly to DOM
    Box(modifier = modifier.fillMaxSize()) {
        // The actual video element is managed separately in the DOM
        CommonVideoPlayerComponent(
            modifier = modifier,
            url = currentUrl,
            isPlaying = currentIsPlaying,
            onPlayStateChange = currentOnPlayStateChange
        )
    }
}