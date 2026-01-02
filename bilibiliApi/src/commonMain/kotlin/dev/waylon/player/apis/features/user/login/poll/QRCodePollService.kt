package dev.waylon.player.apis.features.user.login.poll

import dev.waylon.player.apis.common.ApiService
import dev.waylon.player.apis.common.client.ApiClient
import dev.waylon.player.apis.common.constants.UrlConstants
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.client.statement.HttpResponse

/**
 * QR Code Poll API Service
 * 
 * Provides Bilibili QR code status polling API: [UrlConstants.ENDPOINT_QRCODE_POLL]
 * 
 * This service polls the status of a Bilibili login QR code.
 */
object QRCodePollService : ApiService<QRCodePollRequest> {

    override suspend fun execute(request: QRCodePollRequest): HttpResponse {
        return ApiClient.client.get(UrlConstants.ENDPOINT_QRCODE_POLL) {
            parameter("qrcode_key", request.qrcodeKey)
        }
    }

    /**
     * Convenience method to poll QR code status and return parsed response
     */
    suspend fun pollQRCodeStatus(request: QRCodePollRequest): QRCodePollResponse {
        return execute(request).body()
    }
}