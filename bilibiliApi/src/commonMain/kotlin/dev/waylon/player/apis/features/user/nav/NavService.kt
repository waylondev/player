package dev.waylon.player.apis.features.user.nav

import dev.waylon.player.apis.common.ApiService
import dev.waylon.player.apis.common.client.ApiClient
import dev.waylon.player.apis.common.constants.UrlConstants
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.statement.HttpResponse

/**
 * Nav API Service
 * 
 * Provides Bilibili nav API to get user navigation information and WBI keys:
 * - Nav API - [UrlConstants.ENDPOINT_NAV]
 * 
 * This service is used to fetch user navigation data and WBI keys for generating WBI signatures.
 */
object NavService : ApiService<NavRequest> {

    override suspend fun execute(request: NavRequest): HttpResponse {
        // Make API request and return raw bytes
        return ApiClient.client.get(UrlConstants.ENDPOINT_NAV) {
            // HTTP headers are configured globally in ApiClient's defaultRequest
            // Optional: Add cookie if available for authenticated requests
            // header("Cookie", "your_bilibili_cookie_here")
        }
    }

    /**
     * Convenience method to get nav data and return parsed response
     * Reuses the callByteArray method to avoid duplicate API requests
     */
    suspend fun getNavData(request: NavRequest): NavResponse {
        // Get raw bytes from callByteArray and parse to NavResponse
        return execute(request).body()
    }
}