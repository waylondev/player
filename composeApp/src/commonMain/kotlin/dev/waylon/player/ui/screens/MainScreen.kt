package dev.waylon.player.ui.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import dev.waylon.player.ui.components.LoginQRCodeDialog
import dev.waylon.player.ui.components.TopAppBar

/**
 * Main screen component with unified state management
 * Contains top app bar, bottom navigation, and content area
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen() {
    var screenState by remember { mutableStateOf(MainScreenState()) }

    val actions = object : MainScreenActions {
        override fun onTabSelected(tabIndex: Int) {
            screenState = screenState.updateSelectedTab(tabIndex)
        }

        override fun onRefreshComplete() {
            screenState = screenState.updateRefreshing(false)
        }

        override fun onLoginClick() {
            screenState = screenState.updateLoginQRCodeVisibility(!screenState.showLoginQRCode)
        }

        override fun onVideoClick(videoId: String) {
            screenState = screenState.updateVideoDetail(videoId, true)
        }

        override fun onBackFromVideoDetail() {
            screenState = screenState.updateVideoDetail("", false)
        }

        override fun onLoginSuccess() {
            screenState = screenState.updateLoginStatus(true)
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                selectedTab = screenState.selectedTab,
                onTabSelected = actions::onTabSelected,
                onLoginClick = actions::onLoginClick
            )
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
        ) {
            if (screenState.showVideoDetail) {
                VideoDetailScreen(
                    videoId = screenState.currentVideoId,
                    onBackClick = actions::onBackFromVideoDetail
                )
            } else {
                when (screenState.selectedTab) {
                    0 -> HomeRecommendScreen(
                        isRefreshing = screenState.isRefreshing,
                        onRefreshComplete = actions::onRefreshComplete,
                        onVideoClick = actions::onVideoClick
                    )

                    1 -> RankingScreen(
                        isRefreshing = screenState.isRefreshing,
                        onRefreshComplete = actions::onRefreshComplete,
                        onVideoClick = actions::onVideoClick
                    )

                    2 -> SearchScreen(
                        isRefreshing = screenState.isRefreshing,
                        onRefreshComplete = actions::onRefreshComplete,
                        onVideoClick = actions::onVideoClick
                    )
                }
            }
        }
    }

    // Login QR code dialog - should be rendered at top level, not nested in other layouts
    if (screenState.showLoginQRCode) {
        LoginQRCodeDialog(
            onDismiss = { screenState = screenState.updateLoginQRCodeVisibility(false) },
            onLoginSuccess = actions::onLoginSuccess
        )
    }
}