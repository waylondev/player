package dev.waylon.player.apis.features.user.login.poll

import kotlinx.serialization.Serializable

/**
 * QR Code Poll Response
 * 
 * Response data for Bilibili login QR code status polling
 */
@Serializable
data class QRCodePollResponse(
    val code: Int,
    val message: String,
    val ttl: Int,
    val data: QRCodePollData
)

@Serializable
data class QRCodePollData(
    val url: String,
    val refreshToken: String? = null,
    val timestamp: Long,
    val code: Int,
    val message: String
)

/**
 * QR Code Poll Status
 * 
 * Enumeration of possible QR code poll statuses
 */
enum class QRCodePollStatus(val code: Int) {
    /**
     * QR code login successful
     */
    SUCCESS(0),

    /**
     * QR code expired
     */
    EXPIRED(86038),

    /**
     * QR code scanned but not confirmed
     */
    SCANNED_NOT_CONFIRMED(86090),

    /**
     * QR code not scanned yet
     */
    NOT_SCANNED(86101)
}