package dev.waylon.player.ui.components

import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

/**
 * 跨平台图片加载组件接口
 */
@Composable
expect fun ImageLoader(
    modifier: Modifier = Modifier,
    url: String,
    contentDescription: String? = null
)