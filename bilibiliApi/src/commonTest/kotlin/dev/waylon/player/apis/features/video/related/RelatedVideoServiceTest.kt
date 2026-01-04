package dev.waylon.player.apis.features.video.related

import dev.waylon.player.apis.common.util.Logger
import io.ktor.client.call.body
import kotlin.test.AfterTest
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertTrue
import kotlinx.coroutines.test.runTest
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.intOrNull
import kotlinx.serialization.json.jsonPrimitive

/**
 * Related Video API Test
 *
 * Provides related videos API test:
 * - Related videos list - https://api.bilibili.com/x/web-interface/archive/related
 */
class RelatedVideoServiceTest {

    private val tag = "RelatedVideoApiTest"

    @BeforeTest
    fun setUp() {
    }

    @AfterTest
    fun tearDown() {
        runTest {
        }
    }

    @Test
    fun testGetRelatedVideosApi() = runTest {
        // Log test start
        Logger.i(tag, "=== Related Video API Test Started ===")
        Logger.i(tag, "Request URL: https://api.bilibili.com/x/web-interface/archive/related")
        Logger.i(tag, "Request Method: GET")
        Logger.i(tag, "Request Parameters: bvid=BV1GJ411x7h7")
        Logger.i(tag, "")

        // Use full response method to print complete JSON
        val request = RelatedVideoRequest(bvid = "BV1GJ411x7h7")
        val response = RelatedVideoService.execute(request).body<JsonObject>()

        // Log response results
        // Log response results
        Logger.i(tag, "Response: $response")

        // Verify basic response structure
        assertTrue("API return code should be 0 (success)") {
            response["code"]?.jsonPrimitive?.intOrNull == 0
        }

        Logger.i(tag, "=== Related Video API Test Ended ===")
    }

    @Test
    fun testGetRelatedVideosWithAidApi() = runTest {
        // Log test start
        Logger.i(tag, "=== Related Video API Test (with aid) Started ===")
        Logger.i(tag, "Request URL: https://api.bilibili.com/x/web-interface/archive/related")
        Logger.i(tag, "Request Method: GET")
        Logger.i(tag, "Request Parameters: aid=170001")
        Logger.i(tag, "")

        // Use full response method to print complete JSON
        val request = RelatedVideoRequest(aid = 170001)
        val response = RelatedVideoService.execute(request).body<String>()

        // Log response results
        Logger.i(tag, "Response: $response")

        // Verify basic response structure
        assertTrue("API return code should be 0 (success)") {
            response.isNotBlank()
        }

        Logger.i(tag, "=== Related Video API Test (with aid) Ended ===")
    }
}