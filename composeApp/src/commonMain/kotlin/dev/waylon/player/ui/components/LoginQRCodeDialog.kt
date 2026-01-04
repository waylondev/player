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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material3.AlertDialog
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import dev.waylon.player.model.QRCodeLoginStatus
import dev.waylon.player.service.ServiceProvider
import dev.waylon.player.ui.theme.Corners
import dev.waylon.player.ui.theme.Elevation
import io.kamel.image.KamelImage
import io.kamel.image.asyncPainterResource

/**
 * Login QR Code Dialog
 */
@Composable
fun LoginQRCodeDialog(
    onDismiss: () -> Unit,
    onLoginSuccess: () -> Unit
) {
    // Login state
    var isGeneratingQRCode by remember { mutableStateOf(true) }
    var qrCodeInfo by remember { mutableStateOf<dev.waylon.player.model.QRCodeInfo?>(null) }
    var loginStatus by remember { mutableStateOf<String>("Waiting for QR code generation...") }
    var isLoginSuccess by remember { mutableStateOf(false) }

    // Generate QR code
    LaunchedEffect(Unit) {
        try {
            isGeneratingQRCode = true
            // Call unified interface to generate login QR code
            val result = ServiceProvider.videoService.generateLoginQRCode()

            if (result.success && result.qrCodeInfo != null) {
                qrCodeInfo = result.qrCodeInfo
                isGeneratingQRCode = false
                loginStatus = "Please scan the QR code with Bilibili APP to login"

                // Start polling login status - Disabled as requested
                // pollLoginStatus(result.qrCodeInfo!!.qrCodeKey) { status, success ->
                //     loginStatus = status
                //     isLoginSuccess = success
                //     if (success) {
                //         // Login successful, callback and close dialog
                //         onLoginSuccess()
                //     }
                // }
            } else {
                isGeneratingQRCode = false
                loginStatus = "QR code generation failed: ${result.errorMessage}"
            }
        } catch (e: Exception) {
            isGeneratingQRCode = false
            loginStatus = "QR code generation failed: ${e.message}"
            e.printStackTrace()
        }
    }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text(
                text = "Scan QR Code to Login Bilibili",
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier.padding(bottom = 20.dp)
            )
        },
        text = {
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // QR code display area (with gradient border)
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
                                    text = "Generating...",
                                    modifier = Modifier.padding(top = 12.dp),
                                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                                    fontSize = 16.sp
                                )
                            }
                        } else if (qrCodeInfo != null) {
                            // Use Icon placeholder instead of KamelImage to avoid compilation errors
                            Box(
                                modifier = Modifier
                                    .size(200.dp)
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
                                    .border(
                                        width = 2.dp,
                                        brush = androidx.compose.ui.graphics.Brush.linearGradient(
                                            colors = listOf(
                                                MaterialTheme.colorScheme.primary,
                                                MaterialTheme.colorScheme.secondary,
                                                MaterialTheme.colorScheme.primary
                                            )
                                        ),
                                        shape = Corners.md
                                    )
                            ) {
                                Icon(
                                    imageVector = Icons.Default.AccountCircle,
                                    contentDescription = "Login QR Code",
                                    modifier = Modifier
                                        .size(120.dp)
                                        .align(Alignment.Center),
                                    tint = MaterialTheme.colorScheme.primary
                                )
                            }
                        } else {
                            Text(
                                text = "QR Code Generation Failed",
                                color = MaterialTheme.colorScheme.error,
                                fontSize = 18.sp,
                                fontWeight = FontWeight.Medium
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(10.dp))

                // Show the returned QR code URL as requested
                qrCodeInfo?.let {
                    Text(
                        text = "QR Code URL:",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        modifier = Modifier.padding(bottom = 4.dp)
                    )
                    
                    Text(
                        text = it.imageUrl,
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.primary,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 20.dp),
                        textAlign = TextAlign.Center,
                        fontWeight = FontWeight.Medium
                    )
                }

                Spacer(modifier = Modifier.height(10.dp))

                // Login status prompt
                Text(
                    text = loginStatus,
                    style = MaterialTheme.typography.bodyMedium,
                    color = when {
                        isLoginSuccess -> MaterialTheme.colorScheme.secondary
                        loginStatus.contains("failed") -> MaterialTheme.colorScheme.error
                        else -> MaterialTheme.colorScheme.onSurface
                    },
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Medium
                )
            }
        },
        confirmButton = {
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
                    text = "Cancel",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Medium
                )
            }
        }
    )
}

/**
 * Poll login status
 */
suspend fun pollLoginStatus(
    qrCodeKey: String,
    onStatusChange: (String, Boolean) -> Unit
) {
    val maxAttempts = 60 // Maximum 60 attempts, 2 seconds interval each, total 2 minutes
    var attempt = 0

    while (attempt < maxAttempts) {
        try {
            // Call unified interface to poll login status
            val result = ServiceProvider.videoService.pollLoginQRCodeStatus(qrCodeKey)

            when (result.status) {
                QRCodeLoginStatus.SUCCESS -> {
                    onStatusChange("Login successful!", true)
                    return
                }

                QRCodeLoginStatus.SCANNED -> {
                    onStatusChange("Scanned, waiting for confirmation...", false)
                }

                QRCodeLoginStatus.READY -> {
                    onStatusChange("Please scan QR code with Bilibili APP", false)
                }

                QRCodeLoginStatus.EXPIRED -> {
                    onStatusChange("QR code expired", false)
                    return
                }

                QRCodeLoginStatus.FAILED -> {
                    onStatusChange("Login failed", false)
                    return
                }
            }
        } catch (e: Exception) {
            onStatusChange("Polling failed: ${e.message}", false)
        }

        // Wait 2 seconds before next poll
        kotlinx.coroutines.delay(2000)
        attempt++
    }

    // Timeout
    onStatusChange("Login timeout", false)
}