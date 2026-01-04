package dev.waylon.player.apis.features.search.all

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
 * All Search API Test
 *
 * Provides comprehensive search API test:
 * - All search list - https://api.bilibili.com/x/web-interface/wbi/search/all/v2
 */
class AllSearchServiceTest {

    private val tag = "AllSearchApiTest"

    @BeforeTest
    fun setUp() {
    }

    @AfterTest
    fun tearDown() {
        runTest {
        }
    }

    @Test
    fun testSearchApi() = runTest {
        // Log test start
        Logger.i(tag, "=== All Search API Test Started ===")
        Logger.i(tag, "Request URL: https://api.bilibili.com/x/web-interface/wbi/search/all/v2")
        Logger.i(tag, "Request Method: GET")
        Logger.i(tag, "Request Parameters: keyword=Kotlin")
        Logger.i(tag, "")

        // Use full response method to print complete JSON
        val request = AllSearchRequest(keyword = "Kotlin")
        val response = AllSearchService.execute(request).body<JsonObject>()

        // Log response results
        Logger.i(tag, "Response: $response")

        // Verify basic response structure
        assertTrue("API return code should be 0 (success)") {
            response["code"]?.jsonPrimitive?.intOrNull == 0
        }

        Logger.i(tag, "=== All Search API Test Ended ===")
    }

    @Test
    fun testSearchWithChineseKeywordApi() = runTest {
        // Log test start
        Logger.i(tag, "=== All Search API Test (Chinese Keyword) Started ===")
        Logger.i(tag, "Request URL: https://api.bilibili.com/x/web-interface/wbi/search/all/v2")
        Logger.i(tag, "Request Method: GET")
        Logger.i(tag, "Request Parameters: keyword=编程")
        Logger.i(tag, "")

        // Use full response method to print complete JSON
        val request = AllSearchRequest(keyword = "编程")
        val response = AllSearchService.execute(request).body<JsonObject>()

        // Log response results
        Logger.i(tag, "Response: $response")

        // Verify basic response structure
        assertTrue("API return code should be 0 (success)") {
            response["code"]?.jsonPrimitive?.intOrNull == 0
        }

        Logger.i(tag, "=== All Search API Test (Chinese Keyword) Ended ===")
    }

}