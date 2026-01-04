package dev.waylon.player.apis.adapter.transformer.user

import dev.waylon.player.apis.adapter.transformer.Transformer
import dev.waylon.player.model.PlatformConfig
import dev.waylon.player.model.PlatformContext
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.booleanOrNull
import kotlinx.serialization.json.contentOrNull
import kotlinx.serialization.json.jsonObject
import kotlinx.serialization.json.jsonPrimitive
import kotlinx.serialization.json.longOrNull

/**
 * Transformer for Bilibili Nav API Response
 * 
 * Converts Bilibili API response JSON to common PlatformContext model
 */
object NavTransformer : Transformer<JsonObject, PlatformContext> {

    override fun transform(input: JsonObject): PlatformContext {
        // Parse JSON directly to get needed fields
        val data = input["data"]?.jsonObject

        // Parse user info from Bilibili response
        val userInfo = if (data != null) parseUserInfo(data) else null

        return PlatformContext(
            userInfo = userInfo,
            platformConfig = PlatformConfig(
                platformName = "Bilibili"
            )
        )
    }

    private fun parseUserInfo(data: JsonObject): dev.waylon.player.model.UserInfo? {
        val isLogin = data["isLogin"]?.jsonPrimitive?.booleanOrNull ?: false
        if (!isLogin) return null

        val mid = data["mid"]?.jsonPrimitive?.longOrNull ?: return null
        val name = data["uname"]?.jsonPrimitive?.contentOrNull ?: return null
        val face = data["face"]?.jsonPrimitive?.contentOrNull ?: return null
        val sign = data["sign"]?.jsonPrimitive?.contentOrNull

        return dev.waylon.player.model.UserInfo(
            userId = mid.toString(),
            userName = name,
            avatarUrl = face,
            bio = sign
        )
    }
}
