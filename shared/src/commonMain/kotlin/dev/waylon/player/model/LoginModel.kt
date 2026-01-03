package dev.waylon.player.model

import kotlinx.serialization.Serializable

/**
 * Login-related model classes
 * Designed to be multi-platform generic, not tied to specific platform API structures
 */

/**
 * QR Code login status
 */
enum class QRCodeLoginStatus {
    /**
     * QR code generated, waiting for scan
     */
    READY,

    /**
     * QR code scanned, waiting for confirmation
     */
    SCANNED,

    /**
     * Login successful
     */
    SUCCESS,

    /**
     * QR code expired
     */
    EXPIRED,

    /**
     * Login failed
     */
    FAILED
}

/**
 * Login QR code information
 */
@Serializable
class QRCodeInfo(
    /**
     * QR code image URL
     */
    val imageUrl: String,

    /**
     * QR code unique identifier for polling status
     */
    val qrCodeKey: String,

    /**
     * QR code expiration time (seconds)
     */
    val expireTime: Int
)

/**
 * QR code generation result
 */
@Serializable
class QRCodeGenerateResult(
    /**
     * Whether QR code generation was successful
     */
    val success: Boolean,

    /**
     * Error message, empty if successful
     */
    val errorMessage: String? = null,

    /**
     * QR code information, returned if successful
     */
    val qrCodeInfo: QRCodeInfo? = null
)

/**
 * QR code polling result
 */
@Serializable
class QRCodePollResult(
    /**
     * Current login status
     */
    val status: QRCodeLoginStatus,

    /**
     * Status description message
     */
    val statusMessage: String,

    /**
     * User information after successful login, returned if successful
     */
    val userInfo: UserInfo? = null,

    /**
     * Login token, returned if successful
     */
    val token: String? = null,

    /**
     * Refresh token, returned if successful
     */
    val refreshToken: String? = null,

    /**
     * Token expiration time (milliseconds)
     */
    val tokenExpireTime: Long? = null
)

/**
 * User information
 */
@Serializable
class UserInfo(
    /**
     * User ID
     */
    val userId: String,

    /**
     * Username
     */
    val userName: String,

    /**
     * User avatar URL
     */
    val avatarUrl: String? = null,

    /**
     * User level
     */
    val level: Int = 0,

    /**
     * User nickname
     */
    val nickname: String? = null,

    /**
     * User bio/description
     */
    val bio: String? = null
)

/**
 * Platform context information
 */
@Serializable
class PlatformContext(
    /**
     * Current logged-in user information, null if not logged in
     */
    val userInfo: UserInfo? = null,

    /**
     * Platform configuration information
     */
    val platformConfig: PlatformConfig? = null
)

/**
 * Platform configuration information
 */
@Serializable
class PlatformConfig(
    /**
     * Platform name
     */
    val platformName: String,

    /**
     * Platform logo URL
     */
    val platformLogo: String? = null,

    /**
     * Platform theme color
     */
    val themeColor: String? = null,

    /**
     * Supported login methods
     */
    val supportedLoginMethods: List<String> = emptyList()
)
