package dev.waylon.player.apis.features.video.detail

import dev.waylon.player.apis.common.ApiService
import dev.waylon.player.apis.common.client.ApiClient
import dev.waylon.player.apis.common.constants.UrlConstants
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.client.statement.HttpResponse

/**
 * Video Detail API Service
 * 
 * Provides Bilibili video detail API:
 * - Video detail information - [UrlConstants.ENDPOINT_VIDEO_DETAIL]
 */
object VideoDetailService : ApiService<VideoDetailRequest> {

    override suspend fun execute(request: VideoDetailRequest): HttpResponse {
        // Make API request with necessary parameters
        val response = ApiClient.client.get(UrlConstants.ENDPOINT_VIDEO_DETAIL) {
            request.aid?.let { parameter("aid", it.toString()) }
            request.bvid?.let { parameter("bvid", it) }
        }
        return response
    }
}
