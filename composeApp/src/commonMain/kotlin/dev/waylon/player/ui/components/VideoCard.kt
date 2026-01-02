package dev.waylon.player.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraintsScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import dev.waylon.player.model.VideoInfo
import dev.waylon.player.ui.theme.Corners
import io.kamel.image.KamelImage
import io.kamel.image.asyncPainterResource


/**
 * 科技感视频卡片组件
 */
@Composable
fun VideoCard(
    video: VideoInfo,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier,
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant
        ),
        shape = Corners.lg
    ) {
        Column(
            modifier = Modifier.padding(12.dp)
        ) {
            // 视频封面
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(180.dp)
                    .clip(Corners.md)
                    .border(
                        width = 2.dp,
                        brush = Brush.linearGradient(
                            colors = listOf(
                                MaterialTheme.colorScheme.primary,
                                MaterialTheme.colorScheme.secondary,
                                MaterialTheme.colorScheme.primary
                            )
                        ),
                        shape = Corners.md
                    )
            ) {
                // 使用KamelImage加载视频封面，使用示例图片URL测试
                KamelImage(
                    resource = asyncPainterResource(data = video.coverUrl),
                    contentDescription = video.title,
                    modifier = Modifier.fillMaxSize()
                )

                // 视频时长
                Box(
                    modifier = Modifier
                        .align(Alignment.BottomEnd)
                        .padding(12.dp)
                        .background(
                            color = Color.Black.copy(alpha = 0.8f),
                            shape = Corners.sm
                        )
                        .padding(4.dp, 6.dp)
                ) {
                    Text(
                        text = formatDuration(video.duration),
                        color = Color.White,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold,
                        lineHeight = 18.sp
                    )
                }

                // 播放量
                Box(
                    modifier = Modifier
                        .align(Alignment.TopStart)
                        .padding(12.dp)
                        .background(
                            color = Color.Black.copy(alpha = 0.8f),
                            shape = Corners.sm
                        )
                        .padding(6.dp, 4.dp)
                ) {
                    Text(
                        text = formatPlayCount(video.playCount),
                        color = Color.White,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Medium,
                        lineHeight = 16.sp
                    )
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            // 视频标题
            Text(
                text = video.title,
                fontSize = 16.sp,
                fontWeight = FontWeight.SemiBold,
                color = MaterialTheme.colorScheme.onSurface,
                modifier = Modifier.fillMaxWidth(),
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
                lineHeight = 22.sp
            )

            Spacer(modifier = Modifier.height(8.dp))

            // 作者和分类信息
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                // 作者信息
                Text(
                    text = video.author,
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Medium,
                    color = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.weight(1f),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )

                // 分类信息
                if (video.category != null) {
                    Text(
                        text = video.category ?: "",
                        fontSize = 12.sp,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        modifier = Modifier.padding(start = 8.dp),
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }
            }
        }
    }
}

/**
 * 格式化视频时长
 */
private fun formatDuration(seconds: Int): String {
    val hours = seconds / 3600
    val minutes = (seconds % 3600) / 60
    val secs = seconds % 60

    fun pad(num: Int): String {
        return if (num < 10) "0$num" else "$num"
    }

    return when {
        hours > 0 -> "${pad(hours)}:${pad(minutes)}:${pad(secs)}"
        minutes > 0 -> "${pad(minutes)}:${pad(secs)}"
        else -> "00:${pad(secs)}"
    }
}

/**
 * 格式化播放量
 */
private fun formatPlayCount(playCount: Long): String {
    return when {
        playCount >= 100000000 -> "${(playCount / 100000000).toInt()}亿"
        playCount >= 10000 -> "${(playCount / 10000).toInt()}万"
        playCount >= 1000 -> "${(playCount / 1000).toInt()}k"
        else -> playCount.toString()
    }
}