package dev.waylon.player.ui.components

import androidx.compose.foundation.layout.Box
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import coil.compose.AsyncImage
import coil.request.ImageRequest
import coil.request.CachePolicy

/**
 * Android平台图片加载实现
 */
@Composable
actual fun ImageLoader(
    modifier: Modifier,
    url: String,
    contentDescription: String?
) {
    Box(modifier = modifier) {
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(url)
                .setHeader("Referer", "https://www.bilibili.com/")
                .diskCachePolicy(CachePolicy.ENABLED)
                .memoryCachePolicy(CachePolicy.ENABLED)
                .build(),
            contentDescription = contentDescription,
            modifier = modifier,
            placeholder = { 
                CircularProgressIndicator(
                    modifier = Modifier.align(Alignment.Center)
                ) 
            }
        )
    }
}