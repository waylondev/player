package dev.waylon.player.apis.adapter.transformer.user.login

import dev.waylon.player.apis.adapter.transformer.Transformer
import dev.waylon.player.apis.features.user.login.poll.QRCodePollData
import dev.waylon.player.apis.features.user.login.poll.QRCodePollResponse
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.contentOrNull
import kotlinx.serialization.json.intOrNull
import kotlinx.serialization.json.jsonObject
import kotlinx.serialization.json.jsonPrimitive
import kotlinx.serialization.json.longOrNull

/**
 * Transformer for Bilibili QR Code Poll API Response
 * 
 * Converts Bilibili API response JSON to QRCodePollResponse model
 */
object QRCodePollTransformer : Transformer<JsonObject, QRCodePollResponse> {

    override fun transform(input: JsonObject): QRCodePollResponse {
        // Parse JSON directly to get needed fields
        val code = input["code"]?.jsonPrimitive?.intOrNull ?: -1
        val message = input["message"]?.jsonPrimitive?.contentOrNull ?: ""
        val ttl = input["ttl"]?.jsonPrimitive?.intOrNull ?: 0
        val data = input["data"]?.jsonObject
            ?: throw QRCodePollTransformException("Missing data field in API response")

        // Parse QR code poll data
        val url = data["url"]?.jsonPrimitive?.contentOrNull ?: ""
        val refreshToken = data["refresh_token"]?.jsonPrimitive?.contentOrNull
        val timestamp = data["timestamp"]?.jsonPrimitive?.longOrNull ?: 0L

        return QRCodePollResponse(code, message, ttl, QRCodePollData(url, refreshToken, timestamp, code, message))
    }
}
