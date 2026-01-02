package dev.waylon.player.apis.features.user.login.poll

/**
 * QR Code Poll Request
 * 
 * Request for polling Bilibili login QR code status
 */
data class QRCodePollRequest(
    /**
     * The QR code key obtained from generate QR code API
     */
    val qrcodeKey: String
)