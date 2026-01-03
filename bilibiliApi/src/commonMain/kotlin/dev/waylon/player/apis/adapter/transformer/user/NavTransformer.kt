package dev.waylon.player.apis.adapter.transformer.user

import dev.waylon.player.apis.adapter.transformer.Transformer
import dev.waylon.player.model.PlatformConfig
import dev.waylon.player.model.PlatformContext
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.jsonObject

/**
 * Transformer for Bilibili Nav API Response
 * 
 * Converts Bilibili API response JSON to common PlatformContext model
 */
object NavTransformer : Transformer<JsonObject, PlatformContext> {

    override fun transform(input: JsonObject): PlatformContext {
        // Parse JSON directly to get needed fields
        input["data"]?.jsonObject

        // For now, return an empty PlatformContext
        // In a real implementation, we would parse the Bilibili-specific nav data
        // and convert it to the common PlatformContext format
        return PlatformContext(
            userInfo = null, // Parse user info from Bilibili response
            platformConfig = PlatformConfig(
                platformName = "Bilibili"
            )
        )
    }
}
