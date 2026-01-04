package dev.waylon.player.apis.features.user.login.generate

import dev.waylon.player.apis.common.util.Logger
import io.ktor.client.call.body
import kotlin.test.AfterTest
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertNotNull
import kotlin.test.assertTrue
import kotlinx.coroutines.test.runTest
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.intOrNull
import kotlinx.serialization.json.jsonPrimitive

/**
 * QR Code Generate API Test
 *
 * Provides QR code generation API test:
 * - QR code generation - https://passport.bilibili.com/x/passport-login/web/qrcode/generate
 */
class QRCodeGenerateServiceTest {

    private val tag = "QRCodeGenerateApiTest"

    @BeforeTest
    fun setUp() {
    }

    @AfterTest
    fun tearDown() {
        runTest {
        }
    }

    @Test
    fun testGenerateQRCodeApi() = runTest {
        // Log test start
        Logger.i(tag, "=== QR Code Generate API Test Started ===")
        Logger.i(tag, "Request URL: https://passport.bilibili.com/x/passport-login/web/qrcode/generate")
        Logger.i(tag, "Request Method: GET")
        Logger.i(tag, "Request Parameters: None")
        Logger.i(tag, "")

        // Use full response method to print complete JSON
        val request = QRCodeGenerateRequest()
        val response = QRCodeGenerateService.execute(request).body<JsonObject>()

        // Log response results
        Logger.i(tag, "Response: $response")

        // Verify basic response structure
        assertTrue("API return code should be 0 (success)") {
            response["code"]?.jsonPrimitive?.intOrNull == 0
        }

        // Verify response contains QR code data
        assertNotNull(response["data"], "Response should contain QR code data")

        Logger.i(tag, "=== QR Code Generate API Test Ended ===")
    }


}