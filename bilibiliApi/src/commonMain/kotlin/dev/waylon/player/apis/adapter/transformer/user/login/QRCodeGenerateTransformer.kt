package dev.waylon.player.apis.adapter.transformer.user.login

import dev.waylon.player.apis.adapter.transformer.Transformer
import dev.waylon.player.apis.features.user.login.generate.QRCodeGenerateData
import dev.waylon.player.apis.features.user.login.generate.QRCodeGenerateResponse
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.contentOrNull
import kotlinx.serialization.json.intOrNull
import kotlinx.serialization.json.jsonObject
import kotlinx.serialization.json.jsonPrimitive

/**
 * Transformer for Bilibili QR Code Generate API Response
 * 
 * Converts Bilibili API response JSON to QRCodeGenerateResponse model
 */
object QRCodeGenerateTransformer : Transformer<JsonObject, QRCodeGenerateResponse> {

    override fun transform(input: JsonObject): QRCodeGenerateResponse {
        // Parse JSON directly to get needed fields
        val code = input["code"]?.jsonPrimitive?.intOrNull ?: -1
        val message = input["message"]?.jsonPrimitive?.contentOrNull ?: ""
        val ttl = input["ttl"]?.jsonPrimitive?.intOrNull ?: 0
        val data = input["data"]?.jsonObject
            ?: throw QRCodeGenerateTransformException("Missing data field in API response")

        // Parse QR code generate data
        val url = data["url"]?.jsonPrimitive?.contentOrNull ?: ""
        val qrcodeKey = data["qrcode_key"]?.jsonPrimitive?.contentOrNull ?: ""
        val generateData = QRCodeGenerateData(url, qrcodeKey)

        return QRCodeGenerateResponse(code, message, ttl, generateData)
    }
}
