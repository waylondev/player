package dev.waylon.player.apis.common.client

import dev.waylon.player.apis.common.util.JsonUtils
import io.ktor.client.HttpClient
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.cookies.HttpCookies
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.request.header
import io.ktor.serialization.kotlinx.json.json

/**
 * Bilibili API Client Base (Singleton)
 */
object ApiClient {
    val client = HttpClient {
        install(ContentNegotiation) {
            json(JsonUtils.networkJson)
        }
        install(HttpCookies) {}
        install(Logging) {
            logger = object : Logger {
                override fun log(message: String) {
                    dev.waylon.player.apis.common.util.Logger.d("Bilibili API", message)
                }
            }
            level = LogLevel.ALL
        }

        // Common HTTP headers for Bilibili API requests
        defaultRequest {
            header(
                "User-Agent",
                "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/91.0.4472.124 Safari/537.36"
            )
            header("Referer", "https://www.bilibili.com/")
            header("Origin", "https://www.bilibili.com")
            header("Accept-Encoding", "identity") // Request uncompressed responses to avoid gzip issues in logs
        }
    }

}