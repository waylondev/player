package dev.waylon.player.apis.common.util

import kotlinx.serialization.json.Json

/**
 * JSON utilities for network communication
 */
object JsonUtils {
    /**
     * JSON configuration for network requests/responses
     */
    val networkJson = Json {
        ignoreUnknownKeys = true
        encodeDefaults = true
    }

    /**
     * JSON configuration for pretty printing
     */
    val prettyPrintJson = Json {
        ignoreUnknownKeys = true
        encodeDefaults = true
        prettyPrint = true
    }
}