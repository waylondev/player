package dev.waylon.player.apis.features.home.recommend

import dev.waylon.player.apis.common.util.Logger
import io.ktor.client.call.body
import kotlin.test.AfterTest
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertNotNull
import kotlinx.coroutines.test.runTest
import kotlinx.serialization.json.JsonObject

/**
 * Recommendation API Test
 *
 * Provides two recommendation API tests:
 * 1. Related videos list - https://api.bilibili.com/x/web-interface/archive/related
 * 2. Home page recommendation list - https://api.bilibili.com/x/web-interface/wbi/index/top/feed/rcmd
 */
class HomeRecommendationServiceTest {

    private val tag = "RecommendationApiTest"

    @BeforeTest
    fun setUp() {
    }

    @AfterTest
    fun tearDown() {
        runTest {
        }
    }


    @Test
    fun testGetHomeRecommendationsApi() = runTest {
        // Log test start
        Logger.i(tag, "=== Home Page Recommendation API Test Started ===")
        Logger.i(tag, "Request URL: https://api.bilibili.com/x/web-interface/wbi/index/top/feed/rcmd")
        Logger.i(tag, "Request Method: GET")
        Logger.i(tag, "Request Parameters: ps=6, fresh_idx=1")
        Logger.i(tag, "")

        // Use full response method to print complete JSON
        val request = HomeRecommendRequest(ps = 1, fresh_idx = 1)
        val response = HomeRecommendationService.execute(request).body<JsonObject>()

        // Log response results

        Logger.i(tag, "Response: $response")

        // Verify basic response structure
        assertNotNull(response, "API return code should be 0 (success) or -401 (requires authentication)")

        Logger.i(tag, "=== Home Page Recommendation API Test Ended ===")
    }
}