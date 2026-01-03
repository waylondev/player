package dev.waylon.player.model

import kotlinx.serialization.Serializable

/**
 * 登录相关模型类
 * 设计为多平台通用，不绑定到特定平台的API结构
 */

/**
 * 二维码登录状态
 */
enum class QRCodeLoginStatus {
    /**
     * 二维码已生成，等待扫描
     */
    READY,

    /**
     * 二维码已被扫描，等待确认
     */
    SCANNED,

    /**
     * 登录成功
     */
    SUCCESS,

    /**
     * 二维码已过期
     */
    EXPIRED,

    /**
     * 登录失败
     */
    FAILED
}

/**
 * 登录二维码信息
 */
@Serializable
class QRCodeInfo(
    /**
     * 二维码图片URL
     */
    val imageUrl: String,

    /**
     * 二维码唯一标识，用于后续轮询状态
     */
    val qrCodeKey: String,

    /**
     * 二维码过期时间（秒）
     */
    val expireTime: Int
)

/**
 * 登录二维码生成结果
 */
@Serializable
class QRCodeGenerateResult(
    /**
     * 是否成功生成二维码
     */
    val success: Boolean,

    /**
     * 错误信息，成功时为空
     */
    val errorMessage: String? = null,

    /**
     * 二维码信息，成功时返回
     */
    val qrCodeInfo: QRCodeInfo? = null
)

/**
 * 登录二维码轮询结果
 */
@Serializable
class QRCodePollResult(
    /**
     * 当前登录状态
     */
    val status: QRCodeLoginStatus,

    /**
     * 状态描述信息
     */
    val statusMessage: String,

    /**
     * 登录成功后的用户信息，成功时返回
     */
    val userInfo: UserInfo? = null,

    /**
     * 登录令牌，成功时返回
     */
    val token: String? = null,

    /**
     * 刷新令牌，成功时返回
     */
    val refreshToken: String? = null,

    /**
     * 令牌过期时间（毫秒）
     */
    val tokenExpireTime: Long? = null
)

/**
 * 用户信息
 */
@Serializable
class UserInfo(
    /**
     * 用户ID
     */
    val userId: String,

    /**
     * 用户名
     */
    val userName: String,

    /**
     * 用户头像URL
     */
    val avatarUrl: String? = null,

    /**
     * 用户等级
     */
    val level: Int = 0,

    /**
     * 用户昵称
     */
    val nickname: String? = null,

    /**
     * 签名/简介
     */
    val bio: String? = null
)

/**
 * 平台上下文信息
 */
@Serializable
class PlatformContext(
    /**
     * 当前登录用户信息，未登录时为null
     */
    val userInfo: UserInfo? = null,

    /**
     * 平台配置信息
     */
    val platformConfig: PlatformConfig? = null
)

/**
 * 平台配置信息
 */
@Serializable
class PlatformConfig(
    /**
     * 平台名称
     */
    val platformName: String,

    /**
     * 平台Logo URL
     */
    val platformLogo: String? = null,

    /**
     * 平台主题色
     */
    val themeColor: String? = null,

    /**
     * 支持的登录方式
     */
    val supportedLoginMethods: List<String> = emptyList()
)
