package dev.waylon.player.ui.theme

import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

/**
 * 科技感主题配置
 */

// 深色主题配色方案（默认）
val DarkColorScheme = darkColorScheme(
    // 主要颜色
    primary = Color(0xFF00C8FF),       // 亮蓝色，科技感主色调
    primaryContainer = Color(0xFF001E2D), // 深青色背景
    onPrimary = Color(0xFF000000),        // 黑色文字在主色调上

    // 次要颜色
    secondary = Color(0xFF00FFA3),       // 亮绿色，科技感辅助色
    secondaryContainer = Color(0xFF00291C), // 深绿色背景
    onSecondary = Color(0xFF000000),      // 黑色文字在辅助色上

    // 背景和表面
    background = Color(0xFF00141E),       // 深蓝色背景
    surface = Color(0xFF001A27),         // 深青色表面
    surfaceVariant = Color(0xFF002232),   // 更深的青色变体
    onBackground = Color(0xFFFFFFFF),     // 白色文字在背景上
    onSurface = Color(0xFFFFFFFF),       // 白色文字在表面上

    // 错误颜色
    error = Color(0xFFFF5252),           // 亮红色错误提示
    errorContainer = Color(0xFF3D0000),   // 深红色错误背景
    onError = Color(0xFFFFFFFF),         // 白色文字在错误色上

    // 其他
    outline = Color(0xFF00C8FF),          // 亮蓝色边框
    inverseSurface = Color(0xFF00C8FF),   // 亮蓝色反色表面
    inverseOnSurface = Color(0xFF000000),  // 黑色文字在反色表面上
    inversePrimary = Color(0xFF0099CC),    // 深蓝色反色主色调

    // 亮色方案（在浅色模式下使用）
    surfaceTint = Color(0xFF00C8FF),      // 亮蓝色表面着色
    outlineVariant = Color(0xFF006680),    // 深蓝色边框变体
    scrim = Color(0xFF000000),            // 黑色遮罩
)

// 浅色主题配色方案（备选）
val LightColorScheme = lightColorScheme(
    primary = Color(0xFF006680),       // 深蓝色主色调
    primaryContainer = Color(0xFFB3E5FC), // 亮青色背景
    onPrimary = Color(0xFFFFFFFF),      // 白色文字在主色调上

    secondary = Color(0xFF00796B),       // 深绿色辅助色
    secondaryContainer = Color(0xFFB2DFDB), // 亮绿色背景
    onSecondary = Color(0xFFFFFFFF),      // 白色文字在辅助色上

    background = Color(0xFFF5F5F5),       // 浅灰色背景
    surface = Color(0xFFFFFFFF),         // 白色表面
    onBackground = Color(0xFF000000),     // 黑色文字在背景上
    onSurface = Color(0xFF000000),       // 黑色文字在表面上

    error = Color(0xFFD32F2F),           // 深红色错误提示
    errorContainer = Color(0xFFFFEBEE),   // 浅红色错误背景
    onError = Color(0xFFFFFFFF),         // 白色文字在错误色上

    outline = Color(0xFF006680),          // 深蓝色边框
    inverseSurface = Color(0xFF006680),   // 深蓝色反色表面
    inverseOnSurface = Color(0xFFFFFFFF),  // 白色文字在反色表面上
    inversePrimary = Color(0xFF00C8FF),    // 亮蓝色反色主色调

    surfaceTint = Color(0xFF006680),      // 深蓝色表面着色
    outlineVariant = Color(0xFFB3E5FC),    // 亮青色边框变体
    scrim = Color(0xFF000000),            // 黑色遮罩
)

// 科技感排版配置
object Typography {
    // 标题大标题
    val largeTitle = androidx.compose.ui.text.TextStyle(
        fontSize = 32.sp,
        fontWeight = androidx.compose.ui.text.font.FontWeight.Bold,
        letterSpacing = 0.5.sp
    )

    // 标题
    val title = androidx.compose.ui.text.TextStyle(
        fontSize = 24.sp,
        fontWeight = androidx.compose.ui.text.font.FontWeight.SemiBold,
        letterSpacing = 0.sp
    )

    // 副标题
    val subtitle = androidx.compose.ui.text.TextStyle(
        fontSize = 18.sp,
        fontWeight = androidx.compose.ui.text.font.FontWeight.Medium,
        letterSpacing = 0.15.sp
    )

    // 正文
    val body = androidx.compose.ui.text.TextStyle(
        fontSize = 16.sp,
        fontWeight = androidx.compose.ui.text.font.FontWeight.Normal,
        letterSpacing = 0.5.sp
    )

    // 小正文
    val smallBody = androidx.compose.ui.text.TextStyle(
        fontSize = 14.sp,
        fontWeight = androidx.compose.ui.text.font.FontWeight.Normal,
        letterSpacing = 0.25.sp
    )

    // 按钮文字
    val button = androidx.compose.ui.text.TextStyle(
        fontSize = 14.sp,
        fontWeight = androidx.compose.ui.text.font.FontWeight.Medium,
        letterSpacing = 1.25.sp
    )

    // 标题小
    val smallTitle = androidx.compose.ui.text.TextStyle(
        fontSize = 14.sp,
        fontWeight = androidx.compose.ui.text.font.FontWeight.Medium,
        letterSpacing = 0.1.sp
    )
}

// 常用间距配置
object Spacing {
    val xs = 4.dp
    val sm = 8.dp
    val md = 16.dp
    val lg = 24.dp
    val xl = 32.dp
    val xxl = 48.dp
}

// 圆角配置
object Corners {
    val sm = androidx.compose.foundation.shape.RoundedCornerShape(4.dp)
    val md = androidx.compose.foundation.shape.RoundedCornerShape(8.dp)
    val lg = androidx.compose.foundation.shape.RoundedCornerShape(16.dp)
    val xl = androidx.compose.foundation.shape.RoundedCornerShape(24.dp)
    val full = androidx.compose.foundation.shape.CircleShape
}

// 阴影配置
object Elevation {
    val sm = 2.dp
    val md = 4.dp
    val lg = 8.dp
    val xl = 16.dp
}
