package dev.waylon.player.ui.components

import androidx.compose.foundation.layout.Box
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

/**
 * iOS平台图片加载实现
 */
@Composable
actual fun ImageLoader(
    modifier: Modifier,
    url: String,
    contentDescription: String?
) {
    Box(modifier = modifier) {
        // 简单实现，使用占位符
        CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
    }
}