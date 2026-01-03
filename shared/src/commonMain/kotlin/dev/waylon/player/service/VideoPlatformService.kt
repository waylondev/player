package dev.waylon.player.service

import dev.waylon.player.model.PlatformContext
import dev.waylon.player.model.QRCodeGenerateResult
import dev.waylon.player.model.QRCodePollResult
import dev.waylon.player.model.VideoDetail
import dev.waylon.player.model.VideoInfo
import dev.waylon.player.model.VideoStream

/**
 * Unified Video Platform Service Interface
 * 
 * Defines the core functionality that all video platforms need to implement, ensuring consistent API calls across platforms
 * UI layer calls services through this interface, no need to care about specific platform implementation
 */
interface VideoPlatformService {

    /**
     * Get home page recommended videos
     * 
     * @param pageSize Number of items per page, default 10
     * @param page Page number, default 1
     * @return List of video information
     */
    suspend fun getHomeRecommendations(
        pageSize: Int = 10,
        page: Int = 1
    ): List<VideoInfo>

    /**
     * Get video detailed information
     * 
     * @param videoId Video ID, different platforms may have different formats
     * @return Video detailed information
     */
    suspend fun getVideoDetail(
        videoId: String
    ): VideoDetail

    /**
     * Get video playback stream information
     * 
     * @param videoId Video ID
     * @param qualityId Video quality ID, default 80 (1080p)
     * @return Video stream information
     */
    suspend fun getVideoStream(
        videoId: String,
        qualityId: Int = 80,
        cid: Int = 80
    ): VideoStream

    /**
     * Get hot ranking videos
     * 
     * @param rid Category ID, 0 for all categories
     * @param day Time range, default 3 days
     * @return List of video information
     */
    suspend fun getHotRanking(
        rid: Int = 0,
        day: Int = 3
    ): List<VideoInfo>

    /**
     * Search videos
     * 
     * @param keyword Search keyword
     * @param page Page number, default 1
     * @param pageSize Number of items per page, default 20
     * @return List of video information
     */
    suspend fun searchVideos(
        keyword: String,
        page: Int = 1,
        pageSize: Int = 20
    ): List<VideoInfo>

    /**
     * Get related videos
     * 
     * @param videoId Video ID
     * @param pageSize Number of items per page, default 20
     * @return List of video information
     */
    suspend fun getRelatedVideos(
        videoId: String,
        pageSize: Int = 20
    ): List<VideoInfo>

    /**
     * Get platform context information
     * 
     * @return PlatformContext with user info and platform configuration
     */
    suspend fun getPlatformContext(): PlatformContext

    /**
     * Generate login QR code
     * 
     * @return QR code generate result with URL and key
     */
    suspend fun generateLoginQRCode(): QRCodeGenerateResult

    /**
     * Poll QR code login status
     * 
     * @param qrCodeKey QR code key from generateLoginQRCode
     * @return Login status and result
     */
    suspend fun pollLoginQRCodeStatus(qrCodeKey: String): QRCodePollResult

    /**
     * Get current login status
     * 
     * @return Login status
     */
    suspend fun getLoginStatus(): Boolean

    /**
     * Logout current user
     */
    suspend fun logout(): Boolean
}