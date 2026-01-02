package dev.waylon.player.ui.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import dev.waylon.player.ui.components.LoginQRCodeDialog
import dev.waylon.player.ui.components.TopAppBar

/**
 * 主屏幕组件
 * 包含顶部导航栏、底部导航栏和内容区域
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen() {
    // 当前选中的底部导航项
    var selectedTab by remember { mutableIntStateOf(0) }

    // 刷新状态
    var isRefreshing by remember { mutableStateOf(false) }

    // 登录状态
    var isLoggedIn by remember { mutableStateOf(false) }
    var showLoginQRCode by remember { mutableStateOf(false) }

    // 导航状态
    var showVideoDetail by remember { mutableStateOf(false) }
    var currentVideoId by remember { mutableStateOf("") }

    Scaffold(
        topBar = {
            TopAppBar(
                selectedTab = selectedTab,
                onTabSelected = {
                    selectedTab = it
                    isRefreshing = true
                },
                onLoginClick = { showLoginQRCode = !showLoginQRCode },
                onRefreshClick = {
                    // 触发当前页面刷新
                    isRefreshing = true
                    // 这里可以添加刷新逻辑，通过回调传递给各个屏幕
                }
            )
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
        ) {
            if (showVideoDetail) {
                // 显示视频详情页面
                VideoDetailScreen(
                    videoId = currentVideoId,
                    onBackClick = { showVideoDetail = false }
                )
            } else {
                // 根据选中的标签显示不同的页面，并传递刷新状态和视频点击回调
                when (selectedTab) {
                    0 -> HomeRecommendScreen(
                        isRefreshing = isRefreshing,
                        onRefreshComplete = { isRefreshing = false },
                        onVideoClick = { videoId ->
                            currentVideoId = videoId
                            showVideoDetail = true
                        }
                    )

                    1 -> RankingScreen(
                        isRefreshing = isRefreshing,
                        onRefreshComplete = { isRefreshing = false },
                        onVideoClick = { videoId ->
                            currentVideoId = videoId
                            showVideoDetail = true
                        }
                    )

                    2 -> SearchScreen(
                        isRefreshing = isRefreshing,
                        onRefreshComplete = { isRefreshing = false },
                        onVideoClick = { videoId ->
                            currentVideoId = videoId
                            showVideoDetail = true
                        }
                    )
                }
            }

            // 登录二维码弹窗
            if (showLoginQRCode) {
                LoginQRCodeDialog(
                    onDismiss = { showLoginQRCode = false },
                    onLoginSuccess = { isLoggedIn = true; showLoginQRCode = false }
                )
            }
        }
    }
}