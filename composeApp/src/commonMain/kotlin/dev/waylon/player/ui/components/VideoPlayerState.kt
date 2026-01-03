package dev.waylon.player.ui.components

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf

/**
 * Video player state management class
 */
class VideoPlayerState(
    initialUrl: String = "",
    initialIsPlaying: Boolean = false
) {
    // Video playback URL
    private val _url = mutableStateOf(initialUrl)
    val url: State<String> = _url

    // Playback state
    private val _isPlaying = mutableStateOf(initialIsPlaying)
    val isPlaying: State<Boolean> = _isPlaying

    // Loading state
    private val _isLoading = mutableStateOf(false)
    val isLoading: State<Boolean> = _isLoading

    // Error message
    private val _error = mutableStateOf<String?>(null)
    val error: State<String?> = _error

    // Video duration (milliseconds)
    private val _duration = mutableStateOf(0L)
    val duration: State<Long> = _duration

    // Current playback position (milliseconds)
    private val _currentPosition = mutableStateOf(0L)
    val currentPosition: State<Long> = _currentPosition

    // Update video URL
    fun setUrl(newUrl: String) {
        _url.value = newUrl
        // Reset other states
        _isPlaying.value = false
        _isLoading.value = true
        _error.value = null
        _duration.value = 0L
        _currentPosition.value = 0L
    }

    // Toggle playback state
    fun togglePlay() {
        _isPlaying.value = !_isPlaying.value
    }

    // Set playback state
    fun setPlaying(newIsPlaying: Boolean) {
        _isPlaying.value = newIsPlaying
    }

    // Set loading state
    fun setLoading(newIsLoading: Boolean) {
        _isLoading.value = newIsLoading
    }

    // Set error message
    fun setError(newError: String?) {
        _error.value = newError
        if (newError != null) {
            _isLoading.value = false
            _isPlaying.value = false
        }
    }

    // Set video duration
    fun setDuration(newDuration: Long) {
        _duration.value = newDuration
    }

    // Set current playback position
    fun setCurrentPosition(newPosition: Long) {
        _currentPosition.value = newPosition
    }

    // Reset state
    fun reset() {
        _isPlaying.value = false
        _isLoading.value = false
        _error.value = null
        _duration.value = 0L
        _currentPosition.value = 0L
    }
}
