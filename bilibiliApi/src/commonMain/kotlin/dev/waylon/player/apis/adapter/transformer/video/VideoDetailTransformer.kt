package dev.waylon.player.apis.adapter.transformer.video

import dev.waylon.player.apis.adapter.transformer.Transformer
import dev.waylon.player.model.AuthorInfo
import dev.waylon.player.model.VideoDetail
import dev.waylon.player.model.VideoInfo
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.contentOrNull
import kotlinx.serialization.json.intOrNull
import kotlinx.serialization.json.jsonObject
import kotlinx.serialization.json.jsonPrimitive
import kotlinx.serialization.json.longOrNull

/**
 * Transformer for Bilibili Video Detail API Response
 * 
 * Converts Bilibili API response JSON to VideoDetail model
 */
object VideoDetailTransformer : Transformer<JsonObject, VideoDetail> {

    override fun transform(input: JsonObject): VideoDetail {
        // Parse JSON directly to get needed fields
        val data = input["data"]?.jsonObject
            ?: throw VideoDetailTransformException("Missing data field in API response")

        // Parse nested objects properly
        val stat = data["stat"]?.jsonObject
        val owner = data["owner"]?.jsonObject

        // Get video stats from stat object
        val viewCount = stat?.get("view")?.jsonPrimitive?.longOrNull ?: 0
        val likeCount = stat?.get("like")?.jsonPrimitive?.longOrNull ?: 0
        val coinCount = stat?.get("coin")?.jsonPrimitive?.longOrNull ?: 0
        val favoriteCount = stat?.get("favorite")?.jsonPrimitive?.longOrNull ?: 0
        val shareCount = stat?.get("share")?.jsonPrimitive?.longOrNull ?: 0
        val commentCount = stat?.get("danmaku")?.jsonPrimitive?.longOrNull ?: 0

        // Get basic video info
        val duration = data["duration"]?.jsonPrimitive?.intOrNull ?: 0
        val tname = data["tname"]?.jsonPrimitive?.contentOrNull
        val pubdate = data["pubdate"]?.jsonPrimitive?.longOrNull ?: 0
        
        // Handle cid which might be a large Long value
        val cid = data["cid"]?.jsonPrimitive?.longOrNull ?: 
                  data["cid"]?.jsonPrimitive?.contentOrNull?.toLongOrNull() ?: 0

        // Create VideoInfo first
        val videoInfo = VideoInfo(
            id = data["bvid"]?.jsonPrimitive?.contentOrNull ?: "",
            title = data["title"]?.jsonPrimitive?.contentOrNull ?: "",
            coverUrl = data["pic"]?.jsonPrimitive?.contentOrNull ?: "",
            author = owner?.get("name")?.jsonPrimitive?.contentOrNull ?: "",
            playCount = viewCount,
            duration = duration,
            category = tname,
            publishTime = pubdate,
            description = data["desc"]?.jsonPrimitive?.contentOrNull,
            cid = cid
        )

        // Create AuthorInfo from owner object
        val authorInfo = AuthorInfo(
            id = owner?.get("mid")?.jsonPrimitive?.contentOrNull ?: "",
            name = owner?.get("name")?.jsonPrimitive?.contentOrNull ?: "",
            avatarUrl = owner?.get("face")?.jsonPrimitive?.contentOrNull ?: "",
            followerCount = 0L,
            bio = null
        )

        // Create VideoDetail with all required fields
        return VideoDetail(
            videoInfo = videoInfo,
            fullDescription = data["desc"]?.jsonPrimitive?.contentOrNull,
            likeCount = likeCount,
            coinCount = coinCount,
            favoriteCount = favoriteCount,
            commentCount = commentCount,
            shareCount = shareCount,
            authorInfo = authorInfo,
            parts = emptyList(),
            availableQualities = emptyList()
        )
    }
}

/**
 * Custom exception for video detail transformation errors
 */
class VideoDetailTransformException(message: String, cause: Throwable? = null) : Exception(message, cause)
