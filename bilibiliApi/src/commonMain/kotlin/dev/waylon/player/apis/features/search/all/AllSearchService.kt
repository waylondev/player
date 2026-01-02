package dev.waylon.player.apis.features.search.all

import dev.waylon.player.apis.common.ApiService
import dev.waylon.player.apis.common.client.ApiClient
import dev.waylon.player.apis.common.constants.UrlConstants
import dev.waylon.player.apis.common.extensions.wbiGet
import io.ktor.client.statement.HttpResponse


/**
 * All Search API Service
 * 
 * Provides comprehensive search API:
 * - All search list - [UrlConstants.ENDPOINT_SEARCH_ALL]
 */
object AllSearchService : ApiService<AllSearchRequest> {

    override suspend fun execute(request: AllSearchRequest): HttpResponse {
        // Build search parameters
        val params = mapOf(
            "keyword" to request.keyword,
            "page" to request.page.toString(),
            "order" to request.order,
            "duration" to request.duration.toString(),
            "tids_1" to request.tids_1.toString(),
            "platform" to "web"
        )

        // Make API request with WBI signature using extension function
        return ApiClient.client.wbiGet(UrlConstants.ENDPOINT_SEARCH_ALL, params)
    }
}