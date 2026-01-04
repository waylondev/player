package dev.waylon.player.apis.features.video.detail

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
 * Video Detail API Test
 *
 * Provides video detail API test:
 * - Video detail - https://api.bilibili.com/x/web-interface/view
 */
class VideoDetailServiceTest {


    private val tag = "VideoDetailApiTest"

    @BeforeTest
    fun setUp() {

    }

    @AfterTest
    fun tearDown() {
        runTest {
        }
    }

    @Test
    fun testVideoDetailByBvid() = runTest {
        // Log test start
        Logger.i(tag, "=== Video Detail API Test Started (By BVID) ===")
        Logger.i(tag, "Request URL: https://api.bilibili.com/x/web-interface/view")
        Logger.i(tag, "Request Method: GET")
        Logger.i(tag, "Request Parameters: bvid=BV1y7411Q7Eq")
        Logger.i(tag, "")

        // Test with a known BVID
        val request = VideoDetailRequest(bvid = "BV1y7411Q7Eq")
        val response = VideoDetailService.execute(request).body<JsonObject>()

        // Log response results
        Logger.i(tag, "Response: $response")

        // Verify basic response structure
        assertTrue("API return code should be 0 (success)") {
            response["code"]?.jsonPrimitive?.intOrNull == 0
        }

        Logger.i(tag, "=== Video Detail API Test Ended (By BVID) ===")
    }

    @Test
    fun testVideoDetailByAid() = runTest {
        // Log test start
        Logger.i(tag, "=== Video Detail API Test Started (By AID) ===")
        Logger.i(tag, "Request URL: https://api.bilibili.com/x/web-interface/view")
        Logger.i(tag, "Request Method: GET")
        Logger.i(tag, "Request Parameters: aid=99999999")
        Logger.i(tag, "")

        // Test with a known AID
        val request = VideoDetailRequest(aid = 99999999)
        val response = VideoDetailService.execute(request).body<JsonObject>()

        // Log response results
        Logger.i(tag, "Response: $response")

        // Verify basic response structure
        assertTrue("API return code should be 0 (success)") {
            response["code"]?.jsonPrimitive?.intOrNull == 0
        }

        Logger.i(tag, "=== Video Detail API Test Ended (By AID) ===")
    }
}
