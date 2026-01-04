package dev.waylon.player.apis.adapter.transformer.search

import dev.waylon.player.apis.adapter.transformer.Transformer
import dev.waylon.player.model.VideoInfo
import kotlin.math.pow
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.contentOrNull
import kotlinx.serialization.json.intOrNull
import kotlinx.serialization.json.jsonArray
import kotlinx.serialization.json.jsonObject
import kotlinx.serialization.json.jsonPrimitive
import kotlinx.serialization.json.longOrNull

/**
 * Transformer for Bilibili All Search API Response
 * 
 * Converts Bilibili API response JSON to List<VideoInfo>
 */
object AllSearchTransformer : Transformer<JsonObject, List<VideoInfo>> {

    override fun transform(input: JsonObject): List<VideoInfo> {
        // Parse JSON directly to get needed fields
        val data = input["data"]?.jsonObject ?: return emptyList()
        val resultArray = data["result"]?.jsonArray ?: return emptyList()

        // Find the video result in the result array
        val videoResultItem = resultArray.find { item ->
            val itemObj = item.jsonObject
            itemObj["result_type"]?.jsonPrimitive?.contentOrNull == "video"
        }?.jsonObject

        val videoData = videoResultItem?.get("data")?.jsonArray ?: return emptyList()

        // Extract needed fields from JSON
        return videoData.mapNotNull { itemJson ->
            val item = itemJson.jsonObject

            // Extract only the fields we need
            val bvid = item["bvid"]?.jsonPrimitive?.contentOrNull ?: return@mapNotNull null
            val title = item["title"]?.jsonPrimitive?.contentOrNull ?: return@mapNotNull null
            val cover = item["pic"]?.jsonPrimitive?.contentOrNull ?: return@mapNotNull null

            val authorName = item["author"]?.jsonPrimitive?.contentOrNull ?: return@mapNotNull null
            val viewCount = item["play"]?.jsonPrimitive?.longOrNull ?: 0
            val durationStr = item["duration"]?.jsonPrimitive?.contentOrNull ?: "0:0"
            val tname = item["typename"]?.jsonPrimitive?.contentOrNull
            val pubdate = item["pubdate"]?.jsonPrimitive?.longOrNull ?: 0
            val desc = item["description"]?.jsonPrimitive?.contentOrNull ?: ""
            
            // Handle cid which might be a large Long value, int, or string in API response
            val cid = item["cid"]?.jsonPrimitive?.longOrNull ?: 
                      item["cid"]?.jsonPrimitive?.contentOrNull?.toLongOrNull()

            // Convert duration string (e.g. "41:22") to seconds
            val duration = durationStr.split(":").reversed().foldIndexed(0) { index, acc, part ->
                acc + (part.toIntOrNull() ?: 0) * 60.0.pow(index.toDouble()).toInt()
            }

            VideoInfo(
                id = bvid,
                title = title,
                coverUrl = cover,
                author = authorName,
                playCount = viewCount,
                duration = duration,
                category = tname,
                publishTime = pubdate,
                description = desc.take(100), // Take first 100 characters as short description
                cid = cid
            )
        }
    }
}
