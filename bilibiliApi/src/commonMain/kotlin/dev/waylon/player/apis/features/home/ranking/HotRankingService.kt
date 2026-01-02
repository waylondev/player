package dev.waylon.player.apis.features.home.ranking

import dev.waylon.player.apis.common.ApiService
import dev.waylon.player.apis.common.client.ApiClient
import dev.waylon.player.apis.common.constants.UrlConstants
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.client.statement.HttpResponse

/**
 * Hot Ranking API Service
 * 
 * Provides Bilibili hot ranking API: [UrlConstants.ENDPOINT_HOT_RANKING]
 */
object HotRankingService : ApiService<HotRankingRequest> {

    override suspend fun execute(request: HotRankingRequest): HttpResponse {
        return ApiClient.client.get(UrlConstants.ENDPOINT_HOT_RANKING) {
            parameter("rid", request.rid.toString())
            parameter("day", request.day.toString())
            parameter("platform", "web")
        }
    }
}