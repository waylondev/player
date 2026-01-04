package dev.waylon.player.apis.features.home.ranking

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
 * Hot Ranking API Test
 *
 * Provides hot ranking API test:
 * - Hot ranking list - https://api.bilibili.com/x/web-interface/wbi/ranking/v2
 */
class HotRankingServiceTest {

    private val tag = "HotRankingApiTest"

    @BeforeTest
    fun setUp() {
    }

    @AfterTest
    fun tearDown() {
        runTest {
        }
    }

    @Test
    fun testHotRankingApi() = runTest {
        // Log test start
        Logger.i(tag, "=== Hot Ranking API Test Started ===")
        Logger.i(tag, "Request URL: https://api.bilibili.com/x/web-interface/ranking/v2")
        Logger.i(tag, "Request Method: GET")
        Logger.i(tag, "Request Parameters: rid=0, day=1")
        Logger.i(tag, "")

        // Use full response method to print complete JSON
        val request = HotRankingRequest(rid = 0, day = 1)
        val response = HotRankingService.execute(request).body<JsonObject>()

        // Log response results
        Logger.i(tag, "Response: $response")

        // Verify basic response structure
        assertTrue("API return code should be 0 (success)") {
            response["code"]?.jsonPrimitive?.intOrNull == 0
        }

        Logger.i(tag, "=== Hot Ranking API Test Ended ===")
    }

    @Test
    fun testAnimeRankingApi() = runTest {
        // Log test start
        Logger.i(tag, "=== Anime Ranking API Test Started ===")
        Logger.i(tag, "Request URL: https://api.bilibili.com/x/web-interface/wbi/ranking/v2")
        Logger.i(tag, "Request Method: GET")
        Logger.i(tag, "Request Parameters: rid=1, day=3")
        Logger.i(tag, "")

        // Use full response method to print complete JSON
        val request = HotRankingRequest(rid = 1, day = 3)
        val response = HotRankingService.execute(request).body<JsonObject>()

        // Log response results
        Logger.i(tag, "Response: $response")

        // Verify basic response structure
        assertTrue("API return code should be 0 (success)") {
            response["code"]?.jsonPrimitive?.intOrNull == 0
        }

        Logger.i(tag, "=== Anime Ranking API Test Ended ===")
    }

}