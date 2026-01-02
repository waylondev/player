package dev.waylon.player.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import dev.waylon.player.model.QRCodeLoginStatus
import dev.waylon.player.service.ServiceProvider
import dev.waylon.player.ui.theme.Corners
import dev.waylon.player.ui.theme.Elevation

/**
 * 登录二维码弹窗
 */
@Composable
fun LoginQRCodeDialog(
    onDismiss: () -> Unit,
    onLoginSuccess: () -> Unit
) {
    // 登录状态
    var isGeneratingQRCode by remember { mutableStateOf(true) }
    var qrCodeInfo by remember { mutableStateOf<dev.waylon.player.model.QRCodeInfo?>(null) }
    var loginStatus by remember { mutableStateOf<String>("等待生成二维码...") }
    var isLoginSuccess by remember { mutableStateOf(false) }

    // 生成二维码
    LaunchedEffect(Unit) {
        try {
            isGeneratingQRCode = true
            // 调用统一接口生成登录二维码
            val result = ServiceProvider.videoService.generateLoginQRCode()

            if (result.success && result.qrCodeInfo != null) {
                qrCodeInfo = result.qrCodeInfo
                isGeneratingQRCode = false
                loginStatus = "请使用B站APP扫描二维码登录"

                // 开始轮询登录状态
                pollLoginStatus(result.qrCodeInfo!!.qrCodeKey) { status, success ->
                    loginStatus = status
                    isLoginSuccess = success
                    if (success) {
                        // 登录成功，回调并关闭弹窗
                        onLoginSuccess()
                    }
                }
            } else {
                isGeneratingQRCode = false
                loginStatus = "二维码生成失败: ${result.errorMessage}"
            }
        } catch (e: Exception) {
            isGeneratingQRCode = false
            loginStatus = "二维码生成失败: ${e.message}"
            e.printStackTrace()
        }
    }

    // 背景遮罩
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black.copy(alpha = 0.7f))
            .padding(32.dp),
        contentAlignment = Alignment.Center
    ) {
        // 弹窗内容（带科技感边框和阴影）
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            shape = Corners.lg,
            elevation = CardDefaults.cardElevation(Elevation.xl),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surfaceVariant
            )
        ) {
            Column(
                modifier = Modifier.padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "扫码登录B站",
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.padding(bottom = 20.dp)
                )

                // 二维码显示区域（带渐变边框）
                Card(
                    modifier = Modifier
                        .size(220.dp)
                        .border(
                            width = 2.dp,
                            brush = androidx.compose.ui.graphics.Brush.linearGradient(
                                colors = listOf(
                                    MaterialTheme.colorScheme.primary,
                                    MaterialTheme.colorScheme.secondary,
                                    MaterialTheme.colorScheme.primary
                                )
                            ),
                            shape = Corners.lg
                        ),
                    elevation = CardDefaults.cardElevation(Elevation.lg),
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.surface
                    ),
                    shape = Corners.lg
                ) {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        if (isGeneratingQRCode) {
                            Column(
                                horizontalAlignment = Alignment.CenterHorizontally,
                                verticalArrangement = androidx.compose.foundation.layout.Arrangement.Center
                            ) {
                                CircularProgressIndicator(
                                    color = MaterialTheme.colorScheme.primary,
                                    modifier = Modifier.size(60.dp)
                                )
                                Text(
                                    text = "生成中...",
                                    modifier = Modifier.padding(top = 12.dp),
                                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                                    fontSize = 16.sp
                                )
                            }
                        } else if (qrCodeInfo != null) {
                            // 这里应该显示实际的二维码图片，暂时用占位符代替
                            Box(
                                modifier = Modifier
                                    .size(180.dp)
                                    .background(
                                        brush = androidx.compose.ui.graphics.Brush.linearGradient(
                                            colors = listOf(
                                                MaterialTheme.colorScheme.primary.copy(alpha = 0.2f),
                                                MaterialTheme.colorScheme.secondary.copy(alpha = 0.2f),
                                                MaterialTheme.colorScheme.primary.copy(alpha = 0.2f)
                                            )
                                        ),
                                        shape = Corners.md
                                    )
                            ) {
                                Icon(
                                    imageVector = Icons.Default.AccountCircle,
                                    contentDescription = "登录二维码",
                                    modifier = Modifier
                                        .size(120.dp)
                                        .align(Alignment.Center),
                                    tint = MaterialTheme.colorScheme.primary
                                )
                            }
                        } else {
                            Text(
                                text = "二维码生成失败",
                                color = MaterialTheme.colorScheme.error,
                                fontSize = 18.sp,
                                fontWeight = FontWeight.Medium
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(20.dp))

                // 登录状态提示
                Text(
                    text = loginStatus,
                    style = MaterialTheme.typography.bodyMedium,
                    color = when {
                        isLoginSuccess -> MaterialTheme.colorScheme.secondary
                        loginStatus.contains("失败") -> MaterialTheme.colorScheme.error
                        else -> MaterialTheme.colorScheme.onSurface
                    },
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Medium
                )

                Spacer(modifier = Modifier.height(24.dp))

                // 按钮区域
                Box(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Button(
                        onClick = onDismiss,
                        modifier = Modifier.fillMaxWidth(),
                        shape = Corners.full,
                        elevation = ButtonDefaults.buttonElevation(Elevation.md),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = MaterialTheme.colorScheme.primaryContainer,
                            contentColor = MaterialTheme.colorScheme.onPrimaryContainer
                        )
                    ) {
                        Text(
                            text = "取消",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Medium
                        )
                    }
                }
            }
        }
    }
}

/**
 * 轮询登录状态
 */
suspend fun pollLoginStatus(
    qrCodeKey: String,
    onStatusChange: (String, Boolean) -> Unit
) {
    val maxAttempts = 60 // 最多轮询60次，每次间隔2秒，总共2分钟
    var attempt = 0

    while (attempt < maxAttempts) {
        try {
            // 调用统一接口轮询登录状态
            val result = ServiceProvider.videoService.pollLoginQRCodeStatus(qrCodeKey)

            when (result.status) {
                QRCodeLoginStatus.SUCCESS -> {
                    onStatusChange("登录成功！", true)
                    return
                }

                QRCodeLoginStatus.SCANNED -> {
                    onStatusChange("已扫描，等待确认...", false)
                }

                QRCodeLoginStatus.READY -> {
                    onStatusChange("请使用B站APP扫描二维码", false)
                }

                QRCodeLoginStatus.EXPIRED -> {
                    onStatusChange("二维码已过期", false)
                    return
                }

                QRCodeLoginStatus.FAILED -> {
                    onStatusChange("登录失败", false)
                    return
                }
            }
        } catch (e: Exception) {
            onStatusChange("轮询失败: ${e.message}", false)
        }

        // 间隔2秒再次轮询
        kotlinx.coroutines.delay(2000)
        attempt++
    }

    // 超时
    onStatusChange("登录超时", false)
}