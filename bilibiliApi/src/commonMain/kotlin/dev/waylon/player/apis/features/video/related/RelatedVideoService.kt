package dev.waylon.player.apis.features.video.related

import dev.waylon.player.apis.common.ApiService
import dev.waylon.player.apis.common.client.ApiClient
import dev.waylon.player.apis.common.constants.UrlConstants
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.client.statement.HttpResponse

/**
 * Related Video Recommendation API Service
 * 
 * Provides related videos recommendation API: [UrlConstants.ENDPOINT_RELATED_VIDEO]
 */
object RelatedVideoService : ApiService<RelatedVideoRequest> {

    override suspend fun execute(request: RelatedVideoRequest): HttpResponse {
        return ApiClient.client.get(UrlConstants.ENDPOINT_RELATED_VIDEO) {
            request.aid?.let { parameter("aid", it) }
            request.bvid?.let { parameter("bvid", it) }
        }
    }
}