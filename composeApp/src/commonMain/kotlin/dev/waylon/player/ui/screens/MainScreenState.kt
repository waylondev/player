package dev.waylon.player.ui.screens

import androidx.compose.runtime.Stable

/**
 * MainScreen state data class for unified state management
 * 
 * @property selectedTab Currently selected bottom navigation tab index
 * @property isRefreshing Whether the screen is in refreshing state
 * @property isLoggedIn User login status
 * @property showLoginQRCode Whether to show login QR code dialog
 * @property showVideoDetail Whether to show video detail screen
 * @property currentVideoId Currently selected video ID
 */
@Stable
data class MainScreenState(
    val selectedTab: Int = 0,
    val isRefreshing: Boolean = false,
    val isLoggedIn: Boolean = false,
    val showLoginQRCode: Boolean = false,
    val showVideoDetail: Boolean = false,
    val currentVideoId: String = ""
) {
    /**
     * Updates the selected tab and resets related states
     */
    fun updateSelectedTab(newTab: Int): MainScreenState {
        return copy(
            selectedTab = newTab,
            isRefreshing = true,
            showVideoDetail = false
        )
    }

    /**
     * Updates the refreshing state
     */
    fun updateRefreshing(refreshing: Boolean): MainScreenState {
        return copy(isRefreshing = refreshing)
    }

    /**
     * Updates login QR code dialog visibility
     */
    fun updateLoginQRCodeVisibility(visible: Boolean): MainScreenState {
        return copy(showLoginQRCode = visible)
    }

    /**
     * Updates video detail screen state
     */
    fun updateVideoDetail(videoId: String, showDetail: Boolean): MainScreenState {
        return copy(
            currentVideoId = videoId,
            showVideoDetail = showDetail
        )
    }

    /**
     * Updates login status
     */
    fun updateLoginStatus(loggedIn: Boolean): MainScreenState {
        return copy(
            isLoggedIn = loggedIn,
            showLoginQRCode = false
        )
    }
}

/**
 * MainScreen actions interface for handling user interactions
 */
interface MainScreenActions {
    fun onTabSelected(tabIndex: Int)
    fun onRefreshComplete()
    fun onLoginClick()
    fun onVideoClick(videoId: String)
    fun onBackFromVideoDetail()
    fun onLoginSuccess()
}