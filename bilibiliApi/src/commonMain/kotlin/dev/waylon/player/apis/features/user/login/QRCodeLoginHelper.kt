package dev.waylon.player.apis.features.user.login

import dev.waylon.player.apis.features.user.login.generate.QRCodeGenerateRequest
import dev.waylon.player.apis.features.user.login.generate.QRCodeGenerateResponse
import dev.waylon.player.apis.features.user.login.generate.QRCodeGenerateService
import dev.waylon.player.apis.features.user.login.poll.QRCodePollRequest
import dev.waylon.player.apis.features.user.login.poll.QRCodePollResponse
import dev.waylon.player.apis.features.user.login.poll.QRCodePollService
import dev.waylon.player.apis.features.user.login.poll.QRCodePollStatus

/**
 * QR Code Login Helper
 * 
 * A convenience class to handle the entire QR code login flow
 */
object QRCodeLoginHelper {

    /**
     * Generate a QR code for Bilibili login
     * 
     * @return The QR code generate response containing the URL and qrcode_key
     */
    suspend fun generateQRCode(): QRCodeGenerateResponse {
        return QRCodeGenerateService.generateQRCode(QRCodeGenerateRequest())
    }

    /**
     * Poll the status of a QR code login
     * 
     * @param qrcodeKey The QR code key obtained from generateQRCode()
     * @return The QR code poll response containing the login status
     */
    suspend fun pollQRCodeStatus(qrcodeKey: String): QRCodePollResponse {
        return QRCodePollService.pollQRCodeStatus(QRCodePollRequest(qrcodeKey))
    }

    /**
     * Get the QR code status from the poll response
     * 
     * @param pollResponse The QR code poll response
     * @return The QR code poll status enum
     */
    fun getQRCodeStatus(pollResponse: QRCodePollResponse): QRCodePollStatus {
        return QRCodePollStatus.values().find { it.code == pollResponse.data.code } ?: QRCodePollStatus.NOT_SCANNED
    }
}