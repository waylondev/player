package dev.waylon.player.apis.features.video.stream

import dev.waylon.player.apis.common.ApiService
import dev.waylon.player.apis.common.client.ApiClient
import dev.waylon.player.apis.common.constants.UrlConstants
import dev.waylon.player.apis.common.extensions.wbiGet
import io.ktor.client.statement.HttpResponse

/**
 * Video Stream API Service
 * 
 * Provides Bilibili video stream API: 
 * - Video stream URL - [UrlConstants.ENDPOINT_VIDEO_STREAM]
 * 
 * This service requires WBI signature for API requests.
 */
object VideoStreamService : ApiService<VideoStreamRequest> {

    override suspend fun execute(request: VideoStreamRequest): HttpResponse {
        // Prepare base parameters
        val params = mutableMapOf<String, Any?>(
            "cid" to request.cid.toString(),
            "qn" to request.qn.toString(),
            "fnval" to request.fnval.toString(),
            "fnver" to request.fnver.toString(),
            "fourk" to request.fourk.toString(),
            "platform" to "web",
            "otype" to "json"
        )

        // Add either aid or bvid (one is required)
        request.aid?.let { params["avid"] = it.toString() }
        request.bvid?.let { params["bvid"] = it }

        // Make API request with WBI signature using extension function
        return ApiClient.client.wbiGet(UrlConstants.ENDPOINT_VIDEO_STREAM, params)
    }
}