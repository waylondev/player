package dev.waylon.player.apis.features.user.nav

import dev.waylon.player.apis.common.util.Logger
import io.ktor.client.call.body
import kotlin.test.AfterTest
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertNotNull
import kotlinx.coroutines.test.runTest
import kotlinx.serialization.json.JsonObject

/**
 * Nav API Test
 *
 * Provides nav API test:
 * - Nav API - https://api.bilibili.com/x/web-interface/nav
 */
class NavServiceTest {

    private val tag = "NavApiTest"

    @BeforeTest
    fun setUp() {
    }

    @AfterTest
    fun tearDown() {
        runTest {
        }
    }

    @Test
    fun testNavApi() = runTest {
        // Log test start
        Logger.i(tag, "=== Nav API Test Started ===")
        Logger.i(tag, "Request URL: https://api.bilibili.com/x/web-interface/nav")
        Logger.i(tag, "Request Method: GET")
        Logger.i(tag, "Request Parameters: None")
        Logger.i(tag, "")

        // Use full response method to print complete JSON
        val request = NavRequest()
        val response = NavService.execute(request).body<JsonObject>()

        // Log response results
        Logger.i(tag, "Response: $response")

        // Verify basic response structure
        // Note: Nav API might return code -101 (unauthorized) if not logged in, which is expected
        assertNotNull(response["code"], "Response should contain code field")
        assertNotNull(response["message"], "Response should contain message field")
        assertNotNull(response["data"], "Response should contain data field")

        Logger.i(tag, "=== Nav API Test Ended ===")
    }

    @Test
    fun testGetNavDataWithConvenienceMethod() = runTest {
        // Log test start
        Logger.i(tag, "=== Nav Convenience Method Test Started ===")
        Logger.i(tag, "")

        // Use convenience method to get nav data
        val request = NavRequest()
        val response = NavService.getNavData(request)

        // Log response results
        Logger.i(tag, "Response: $response")

        // Verify basic response structure
        assertNotNull(response, "Response should not be null")
        assertNotNull(response.code, "Response should contain code field")
        assertNotNull(response.message, "Response should contain message field")

        Logger.i(tag, "=== Nav Convenience Method Test Ended ===")
    }
}