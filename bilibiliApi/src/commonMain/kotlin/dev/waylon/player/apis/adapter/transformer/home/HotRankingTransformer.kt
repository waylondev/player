package dev.waylon.player.apis.adapter.transformer.home

import dev.waylon.player.apis.adapter.transformer.Transformer
import dev.waylon.player.model.VideoInfo
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.contentOrNull
import kotlinx.serialization.json.intOrNull
import kotlinx.serialization.json.jsonArray
import kotlinx.serialization.json.jsonObject
import kotlinx.serialization.json.jsonPrimitive
import kotlinx.serialization.json.longOrNull

/**
 * Transformer for Bilibili Hot Ranking API Response
 * 
 * Converts Bilibili API response JSON to List<VideoInfo>
 */
object HotRankingTransformer : Transformer<JsonObject, List<VideoInfo>> {

    override fun transform(input: JsonObject): List<VideoInfo> {
        // Parse JSON directly to get needed fields
        val data = input["data"]?.jsonObject ?: return emptyList()
        val list = data["list"]?.jsonArray ?: return emptyList()

        // Extract needed fields from JSON
        return list.mapNotNull { itemJson ->
            val item = itemJson.jsonObject
            val owner = item["owner"]?.jsonObject ?: return@mapNotNull null
            val stat = item["stat"]?.jsonObject ?: return@mapNotNull null

            // Extract only the fields we need
            val bvid = item["bvid"]?.jsonPrimitive?.contentOrNull ?: return@mapNotNull null
            val title = item["title"]?.jsonPrimitive?.contentOrNull ?: return@mapNotNull null
            val cover = item["pic"]?.jsonPrimitive?.contentOrNull ?: return@mapNotNull null
            val authorName = owner["name"]?.jsonPrimitive?.contentOrNull ?: return@mapNotNull null
            val viewCount = stat["view"]?.jsonPrimitive?.longOrNull ?: 0
            val duration = item["duration"]?.jsonPrimitive?.intOrNull ?: 0
            val tname = item["tname"]?.jsonPrimitive?.contentOrNull
            val pubdate = item["pubdate"]?.jsonPrimitive?.longOrNull ?: 0
            val desc = item["desc"]?.jsonPrimitive?.contentOrNull ?: ""

            VideoInfo(
                id = bvid,
                title = title,
                coverUrl = cover,
                author = authorName,
                playCount = viewCount,
                duration = duration,
                category = tname,
                publishTime = pubdate,
                description = desc.take(100) // Take first 100 characters as short description
            )
        }
    }
}
