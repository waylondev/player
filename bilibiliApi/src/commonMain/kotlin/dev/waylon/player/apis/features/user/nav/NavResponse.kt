package dev.waylon.player.apis.features.user.nav

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * Nav API Response
 */
@Serializable
data class NavResponse(
    val code: Int,
    val message: String,
    val data: NavData?
)

@Serializable
data class NavData(
    @SerialName("wbi_img")
    val wbiImg: WbiImg?
)

@Serializable
data class WbiImg(
    @SerialName("img_url")
    val imgUrl: String,
    @SerialName("sub_url")
    val subUrl: String
)