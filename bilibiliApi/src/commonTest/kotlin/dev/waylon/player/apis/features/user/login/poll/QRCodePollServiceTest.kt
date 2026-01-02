package dev.waylon.player.apis.features.user.login.poll

import dev.waylon.player.apis.common.util.Logger
import dev.waylon.player.apis.features.user.login.generate.QRCodeGenerateRequest
import dev.waylon.player.apis.features.user.login.generate.QRCodeGenerateService
import io.ktor.client.call.body
import kotlin.test.AfterTest
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertNotNull
import kotlinx.coroutines.test.runTest
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.jsonObject
import kotlinx.serialization.json.jsonPrimitive

/**
 * QR Code Poll API Test
 *
 * Provides QR code status polling API test:
 * - QR code status polling - https://passport.bilibili.com/x/passport-login/web/qrcode/poll
 */
class QRCodePollServiceTest {

    private val tag = "QRCodePollApiTest"

    @BeforeTest
    fun setUp() {
    }

    @AfterTest
    fun tearDown() {
        runTest {
        }
    }

    @Test
    fun testPollQRCodeStatusApi() = runTest {
        // First generate a QR code to get a valid qrcodeKey
        val generateRequest = QRCodeGenerateRequest()
        val generateResponse = QRCodeGenerateService.execute(generateRequest).body<JsonObject>()
        val qrcodeKey = generateResponse["data"]?.jsonObject?.get("qrcode_key")?.jsonPrimitive?.contentOrNull

        assertNotNull(qrcodeKey, "Should get a valid qrcodeKey from generate API")

        // Log test start
        Logger.i(tag, "=== QR Code Poll API Test Started ===")
        Logger.i(tag, "Request URL: https://passport.bilibili.com/x/passport-login/web/qrcode/poll")
        Logger.i(tag, "Request Method: GET")
        Logger.i(tag, "Request Parameters: qrcode_key=$qrcodeKey")
        Logger.i(tag, "")

        // Use full response method to print complete JSON
        val request = QRCodePollRequest(qrcodeKey = qrcodeKey)
        val response = QRCodePollService.execute(request).body<JsonObject>()

        // Log response results
        Logger.i(tag, "Response: $response")

        // Verify basic response structure
        // Note: For QR code poll, the code might not be 0 immediately, but it should be a valid response
        assertNotNull(response["code"], "Response should contain code field")
        assertNotNull(response["message"], "Response should contain message field")

        Logger.i(tag, "=== QR Code Poll API Test Ended ===")
    }

    @Test
    fun testPollQRCodeWithConvenienceMethod() = runTest {
        // First generate a QR code to get a valid qrcodeKey
        val generateRequest = QRCodeGenerateRequest()
        val generateResponse = QRCodeGenerateService.execute(generateRequest).body<JsonObject>()
        val qrcodeKey = generateResponse["data"]?.jsonObject?.get("qrcode_key")?.jsonPrimitive?.contentOrNull

        assertNotNull(qrcodeKey, "Should get a valid qrcodeKey from generate API")

        // Log test start
        Logger.i(tag, "=== QR Code Poll Convenience Method Test Started ===")
        Logger.i(tag, "")

        // Use convenience method to poll QR code status
        val request = QRCodePollRequest(qrcodeKey = qrcodeKey)
        val response = QRCodePollService.pollQRCodeStatus(request)

        // Log response results
        Logger.i(tag, "Response: $response")

        // Verify basic response structure
        assertNotNull(response, "Response should not be null")
        assertNotNull(response.code, "Response should contain code field")
        assertNotNull(response.message, "Response should contain message field")

        Logger.i(tag, "=== QR Code Poll Convenience Method Test Ended ===")
    }
}