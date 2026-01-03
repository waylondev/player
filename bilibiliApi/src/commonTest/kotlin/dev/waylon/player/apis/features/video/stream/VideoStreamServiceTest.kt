package dev.waylon.player.apis.features.video.stream

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
 * Video Stream API Test
 *
 * Provides video stream API test:
 * - Video stream URL - https://api.bilibili.com/x/player/wbi/playurl
 */
class VideoStreamServiceTest {


    private val tag = "VideoStreamApiTest"

    @BeforeTest
    fun setUp() {

    }

    @AfterTest
    fun tearDown() {
        runTest {
        }
    }

    @Test
    fun testVideoStreamByBvid() = runTest {
        // Log test start
        Logger.i(tag, "=== Video Stream API Test Started (By BVID) ===")
        Logger.i(tag, "Request URL: https://api.bilibili.com/x/player/wbi/playurl")
        Logger.i(tag, "Request Method: GET")
        Logger.i(tag, "Request Parameters: bvid=BV1y7411Q7Eq, cid=171776208")
        Logger.i(tag, "")

        // Test with a known BVID and CID
        val request = VideoStreamRequest(
            bvid = "BV1y7411Q7Eq",
            cid = 171776208,
            qn = 32,  // 480P
            fnval = 1 // MP4 format
        )
        val response = VideoStreamService.execute(request).body<JsonObject>()

        // Log response results
        Logger.i(tag, "Response: $response")

        // Verify basic response structure
        assertTrue("API return code should be 0 (success)") {
            response["code"]?.jsonPrimitive?.intOrNull == 0
        }

        Logger.i(tag, "=== Video Stream API Test Ended (By BVID) ===")
    }

    @Test
    fun testVideoStreamByAid() = runTest {
        // Log test start
        Logger.i(tag, "=== Video Stream API Test Started (By AID) ===")
        Logger.i(tag, "Request URL: https://api.bilibili.com/x/player/wbi/playurl")
        Logger.i(tag, "Request Method: GET")
        Logger.i(tag, "Request Parameters: aid=99999999, cid=171776208")
        Logger.i(tag, "")

        // Test with a known AID and CID
        val request = VideoStreamRequest(
            aid = 99999999,
            cid = 171776208,
            qn = 32,  // 480P
            fnval = 1 // MP4 format
        )
        val response = VideoStreamService.execute(request).body<JsonObject>()

        // Log response results
        Logger.i(tag, "Response: $response")

        // Verify basic response structure
        assertTrue("API return code should be 0 (success)") {
            response["code"]?.jsonPrimitive?.intOrNull == 0
        }

        Logger.i(tag, "=== Video Stream API Test Ended (By AID) ===")
    }
}
