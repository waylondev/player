package dev.waylon.player.apis.features.user.login.generate

import dev.waylon.player.apis.common.ApiService
import dev.waylon.player.apis.common.client.ApiClient
import dev.waylon.player.apis.common.constants.UrlConstants
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.statement.HttpResponse

/**
 * QR Code Generate API Service
 * 
 * Provides Bilibili QR code generation API: [UrlConstants.ENDPOINT_QRCODE_GENERATE]
 * 
 * This service generates a QR code for Bilibili login.
 */
object QRCodeGenerateService : ApiService<QRCodeGenerateRequest> {

    override suspend fun execute(request: QRCodeGenerateRequest): HttpResponse {
        return ApiClient.client.get(UrlConstants.ENDPOINT_QRCODE_GENERATE)
    }

    /**
     * Convenience method to generate QR code and return parsed response
     */
    suspend fun generateQRCode(request: QRCodeGenerateRequest): QRCodeGenerateResponse {
        return execute(request).body()
    }
}