package dev.waylon.player.apis.adapter

import dev.waylon.player.apis.adapter.transformer.home.HomeRecommendationsTransformer
import dev.waylon.player.apis.adapter.transformer.home.HotRankingTransformer
import dev.waylon.player.apis.adapter.transformer.search.AllSearchTransformer
import dev.waylon.player.apis.adapter.transformer.user.NavTransformer
import dev.waylon.player.apis.adapter.transformer.video.RelatedVideoTransformer
import dev.waylon.player.apis.adapter.transformer.video.VideoDetailTransformer
import dev.waylon.player.apis.adapter.transformer.video.VideoStreamTransformer
import dev.waylon.player.apis.common.extensions.executeAndTransform
import dev.waylon.player.apis.features.home.ranking.HotRankingRequest
import dev.waylon.player.apis.features.home.ranking.HotRankingService
import dev.waylon.player.apis.features.home.recommend.HomeRecommendRequest
import dev.waylon.player.apis.features.home.recommend.HomeRecommendationService
import dev.waylon.player.apis.features.search.all.AllSearchRequest
import dev.waylon.player.apis.features.search.all.AllSearchService
import dev.waylon.player.apis.features.user.login.generate.QRCodeGenerateRequest
import dev.waylon.player.apis.features.user.login.generate.QRCodeGenerateResponse
import dev.waylon.player.apis.features.user.login.generate.QRCodeGenerateService
import dev.waylon.player.apis.features.user.login.poll.QRCodePollRequest
import dev.waylon.player.apis.features.user.login.poll.QRCodePollResponse
import dev.waylon.player.apis.features.user.login.poll.QRCodePollService
import dev.waylon.player.apis.features.user.nav.NavRequest
import dev.waylon.player.apis.features.user.nav.NavService
import dev.waylon.player.apis.features.video.detail.VideoDetailRequest
import dev.waylon.player.apis.features.video.detail.VideoDetailService
import dev.waylon.player.apis.features.video.related.RelatedVideoRequest
import dev.waylon.player.apis.features.video.related.RelatedVideoService
import dev.waylon.player.apis.features.video.stream.VideoStreamRequest
import dev.waylon.player.apis.features.video.stream.VideoStreamService
import dev.waylon.player.model.PlatformContext
import dev.waylon.player.model.QRCodeGenerateResult
import dev.waylon.player.model.QRCodeInfo
import dev.waylon.player.model.QRCodeLoginStatus
import dev.waylon.player.model.QRCodePollResult
import dev.waylon.player.model.VideoDetail
import dev.waylon.player.model.VideoInfo
import dev.waylon.player.model.VideoStream
import dev.waylon.player.service.VideoPlatformService
import kotlinx.serialization.json.JsonObject

/**
 * Bilibili Platform Adapter
 * 
 * Adapts Bilibili API services to the unified VideoPlatformService interface
 * Responsible for converting Bilibili API response models to common models
 */
class BilibiliAdapter : VideoPlatformService {

    override suspend fun getHomeRecommendations(
        pageSize: Int,
        page: Int
    ): List<VideoInfo> {
        val request = HomeRecommendRequest(
            ps = pageSize,
            fresh_idx = page
        )

        return HomeRecommendationService.executeAndTransform(request) { root ->
            HomeRecommendationsTransformer.transform(root)
        }
    }

    override suspend fun getVideoDetail(
        videoId: String
    ): VideoDetail {
        val request = VideoDetailRequest(bvid = videoId)
        return VideoDetailService.executeAndTransform(request) { root ->
            VideoDetailTransformer.transform(root)
        }
    }

    override suspend fun getVideoStream(
        videoId: String,
        qualityId: Int,
        cid: Int
    ): VideoStream {
        val request = VideoStreamRequest(
            bvid = videoId, cid = cid, qn = qualityId
        )
        return VideoStreamService.executeAndTransform(request) { root ->
            VideoStreamTransformer.transform(root)
        }
    }

    // Additional business methods

    /**
     * Get hot ranking videos
     * 
     * @param rid Category ID, 0 for all categories
     * @param day Time range, default 3 days
     * @return List of video information
     */
    override suspend fun getHotRanking(
        rid: Int,
        day: Int
    ): List<VideoInfo> {
        val request = HotRankingRequest(
            rid = rid,
            day = day
        )

        return HotRankingService.executeAndTransform(request) { root ->
            HotRankingTransformer.transform(root)
        }
    }

    /**
     * Search videos
     * 
     * @param keyword Search keyword
     * @param page Page number, default 1
     * @param pageSize Number of items per page, default 20
     * @return List of video information
     */
    override suspend fun searchVideos(
        keyword: String,
        page: Int,
        pageSize: Int
    ): List<VideoInfo> {
        val request = AllSearchRequest(
            keyword = keyword,
            page = page
        )
        return AllSearchService.executeAndTransform(request) { root ->
            AllSearchTransformer.transform(root)
        }
    }

    /**
     * Get related videos
     * 
     * @param videoId Video ID
     * @param pageSize Number of items per page, default 20
     * @return List of video information
     */
    override suspend fun getRelatedVideos(
        videoId: String,
        pageSize: Int
    ): List<VideoInfo> {
        val request = RelatedVideoRequest(
            bvid = videoId
        )

        return RelatedVideoService.executeAndTransform(request) { root ->
            RelatedVideoTransformer.transform(root)
        }
    }

    /**
     * Get platform context information
     * 
     * @return PlatformContext with user info and platform configuration
     */
    override suspend fun getPlatformContext(): PlatformContext {
        val request = NavRequest()

        return NavService.executeAndTransform(request) { root ->
            NavTransformer.transform(root)
        }
    }

    /**
     * Generate login QR code
     * 
     * @return QR code generate result with URL and key
     */
    override suspend fun generateLoginQRCode(): QRCodeGenerateResult {
        // Create request with parameters
        val request = QRCodeGenerateRequest()

        // Call Bilibili API using extension function
        val bilibiliResponse = QRCodeGenerateService.executeAndTransform(request) { root ->
            root.body<QRCodeGenerateResponse>()
        }

        // Convert to common QRCodeGenerateResult
        return if (bilibiliResponse.code == 0) {
            QRCodeGenerateResult(
                success = true,
                qrCodeInfo = QRCodeInfo(
                    imageUrl = bilibiliResponse.data.url,
                    qrCodeKey = bilibiliResponse.data.qrcodeKey,
                    expireTime = bilibiliResponse.ttl
                )
            )
        } else {
            QRCodeGenerateResult(
                success = false,
                errorMessage = bilibiliResponse.message
            )
        }
    }

    /**
     * Poll QR code login status
     * 
     * @param qrCodeKey QR code key from generateLoginQRCode
     * @return Login status and result
     */
    override suspend fun pollLoginQRCodeStatus(qrCodeKey: String): QRCodePollResult {
        // Create request with parameters
        val request = QRCodePollRequest(qrcodeKey = qrCodeKey)

        // Call Bilibili API using extension function
        val bilibiliResponse = QRCodePollService.executeAndTransform(request) { root ->
            root.body<QRCodePollResponse>()
        }

        // Map Bilibili status code to common QRCodeLoginStatus
        val status = when (bilibiliResponse.code) {
            0 -> QRCodeLoginStatus.SUCCESS
            86038 -> QRCodeLoginStatus.EXPIRED
            86090 -> QRCodeLoginStatus.SCANNED
            86101 -> QRCodeLoginStatus.READY
            else -> QRCodeLoginStatus.FAILED
        }

        // Convert to common QRCodePollResult
        return QRCodePollResult(
            status = status,
            statusMessage = bilibiliResponse.message,
            refreshToken = bilibiliResponse.data.refreshToken
        )
    }

    /**
     * Get current login status
     * 
     * @return Login status
     */
    override suspend fun getLoginStatus(): Boolean {
        // 实现获取登录状态的逻辑
        // 这里简单实现，后续可以根据实际情况完善
        return false
    }

    /**
     * Logout current user
     */
    override suspend fun logout(): Boolean {
        // 实现登出逻辑
        // 这里简单实现，后续可以根据实际情况完善
        return true
    }
}
