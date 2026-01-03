package dev.waylon.player.ui.components

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf

/**
 * 视频播放器状态管理类
 */
class VideoPlayerState(
    initialUrl: String = "",
    initialIsPlaying: Boolean = false
) {
    // 视频播放地址
    private val _url = mutableStateOf(initialUrl)
    val url: State<String> = _url

    // 播放状态
    private val _isPlaying = mutableStateOf(initialIsPlaying)
    val isPlaying: State<Boolean> = _isPlaying

    // 加载状态
    private val _isLoading = mutableStateOf(false)
    val isLoading: State<Boolean> = _isLoading

    // 错误信息
    private val _error = mutableStateOf<String?>(null)
    val error: State<String?> = _error

    // 视频时长（毫秒）
    private val _duration = mutableStateOf(0L)
    val duration: State<Long> = _duration

    // 当前播放位置（毫秒）
    private val _currentPosition = mutableStateOf(0L)
    val currentPosition: State<Long> = _currentPosition

    // 更新视频地址
    fun setUrl(newUrl: String) {
        _url.value = newUrl
        // 重置其他状态
        _isPlaying.value = false
        _isLoading.value = true
        _error.value = null
        _duration.value = 0L
        _currentPosition.value = 0L
    }

    // 切换播放状态
    fun togglePlay() {
        _isPlaying.value = !_isPlaying.value
    }

    // 设置播放状态
    fun setPlaying(newIsPlaying: Boolean) {
        _isPlaying.value = newIsPlaying
    }

    // 设置加载状态
    fun setLoading(newIsLoading: Boolean) {
        _isLoading.value = newIsLoading
    }

    // 设置错误信息
    fun setError(newError: String?) {
        _error.value = newError
        if (newError != null) {
            _isLoading.value = false
            _isPlaying.value = false
        }
    }

    // 设置视频时长
    fun setDuration(newDuration: Long) {
        _duration.value = newDuration
    }

    // 设置当前播放位置
    fun setCurrentPosition(newPosition: Long) {
        _currentPosition.value = newPosition
    }

    // 重置状态
    fun reset() {
        _isPlaying.value = false
        _isLoading.value = false
        _error.value = null
        _duration.value = 0L
        _currentPosition.value = 0L
    }
}
