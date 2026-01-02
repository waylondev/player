package dev.waylon.player.apis.common.constants

/**
 * URL Constants for Bilibili API
 * 
 * This class contains all the base URLs and API endpoints used in the project.
 * Centralizing these constants makes it easier to manage and update URLs, especially when switching between environments.
 */
object UrlConstants {
    /**
     * Base URL for Bilibili API requests
     */
    const val BASE_URL_API = "https://api.bilibili.com/"

    /**
     * Base URL for Bilibili Passport API requests
     */
    const val BASE_URL_PASSPORT = "https://passport.bilibili.com/"

    // API endpoints

    /**
     * Nav API endpoint for getting WBI keys
     */
    const val ENDPOINT_NAV = "${BASE_URL_API}x/web-interface/nav"

    /**
     * QR code generate API endpoint
     */
    const val ENDPOINT_QRCODE_GENERATE = "${BASE_URL_PASSPORT}x/passport-login/web/qrcode/generate"

    /**
     * QR code poll API endpoint
     */
    const val ENDPOINT_QRCODE_POLL = "${BASE_URL_PASSPORT}x/passport-login/web/qrcode/poll"

    /**
     * Video stream API endpoint
     */
    const val ENDPOINT_VIDEO_STREAM = "${BASE_URL_API}x/player/wbi/playurl"

    /**
     * Video detail API endpoint
     */
    const val ENDPOINT_VIDEO_DETAIL = "${BASE_URL_API}x/web-interface/view"

    /**
     * Hot ranking API endpoint
     */
    const val ENDPOINT_HOT_RANKING = "${BASE_URL_API}x/web-interface/ranking/v2"

    /**
     * Search API endpoint
     */
    const val ENDPOINT_SEARCH_ALL = "${BASE_URL_API}x/web-interface/wbi/search/all/v2"

    /**
     * Related video API endpoint
     */
    const val ENDPOINT_RELATED_VIDEO = "${BASE_URL_API}x/web-interface/archive/related"

    /**
     * Home recommendation API endpoint
     */
    const val ENDPOINT_HOME_RECOMMENDATION = "${BASE_URL_API}x/web-interface/wbi/index/top/feed/rcmd"
}