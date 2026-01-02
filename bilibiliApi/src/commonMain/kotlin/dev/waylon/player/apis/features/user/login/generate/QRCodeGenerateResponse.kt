package dev.waylon.player.apis.features.user.login.generate

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * QR Code Generate Response
 * 
 * Response data for Bilibili login QR code generation
 */
@Serializable
data class QRCodeGenerateResponse(
    val code: Int,
    val message: String,
    val ttl: Int,
    val data: QRCodeGenerateData
)

@Serializable
data class QRCodeGenerateData(
    val url: String,
    @SerialName("qrcode_key")
    val qrcodeKey: String
)