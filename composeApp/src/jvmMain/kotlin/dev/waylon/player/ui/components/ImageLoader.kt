package dev.waylon.player.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import java.awt.image.BufferedImage
import java.io.ByteArrayOutputStream
import java.net.HttpURLConnection
import java.net.URL
import javax.imageio.ImageIO
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

/**
 * JVM平台图片加载实现
 */
@Composable
actual fun ImageLoader(
    modifier: Modifier,
    url: String,
    contentDescription: String?
) {
    var isLoading by remember { mutableStateOf(true) }
    
    // 在协程中加载图片（实际项目中应该实现完整的图片加载和缓存机制）
    remember(url) {
        GlobalScope.launch(Dispatchers.IO) {
            try {
                val connection = URL(url).openConnection() as HttpURLConnection
                connection.setRequestProperty("Referer", "https://www.bilibili.com/")
                connection.connectTimeout = 5000
                connection.readTimeout = 5000
                
                if (connection.responseCode == HttpURLConnection.HTTP_OK) {
                    // 这里只是验证连接，实际项目中应该实现完整的图片加载
                    val inputStream = connection.inputStream
                    val bufferedImage = ImageIO.read(inputStream)
                    inputStream.close()
                }
            } catch (e: Exception) {
                e.printStackTrace()
            } finally {
                withContext(Dispatchers.Main) {
                    isLoading = false
                }
            }
        }
    }
    
    Box(modifier = modifier) {
        if (isLoading) {
            CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
        }
    }
}