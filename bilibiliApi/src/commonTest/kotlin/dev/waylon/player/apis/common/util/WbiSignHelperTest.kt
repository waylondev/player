package dev.waylon.player.apis.common.util

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue
import kotlinx.coroutines.test.runTest

class WbiSignHelperTest {

    @Test
    fun testGenerateWbiSign() = runTest {
        // Test basic WBI signature generation
        val params = mapOf(
            "keyword" to "kotlin",
            "page" to "1"
        )

        val signedParams = WbiSignHelper.generateWbiSign(params)

        // Verify that signature parameters are present
        assertTrue("wts" in signedParams, "wts parameter should be present")
        assertTrue("w_rid" in signedParams, "w_rid parameter should be present")

        // Verify parameter types
        assertEquals(signedParams["wts"]?.isNotEmpty(), true, "wts should not be empty")
        assertEquals(signedParams["w_rid"]?.isNotEmpty(), true, "w_rid should not be empty")

        // Verify that original parameters are preserved
        assertTrue("keyword" in signedParams, "original parameters should be preserved")
        assertTrue("page" in signedParams, "original parameters should be preserved")
    }

    @Test
    fun testWbiSignGeneration() = runTest {
        // Test that WBI signature generates valid parameters
        val params = mapOf("test" to "value")
        val signedParams = WbiSignHelper.generateWbiSign(params)

        // Verify signature parameters
        assertTrue("wts" in signedParams, "wts parameter should be present")
        assertTrue("w_rid" in signedParams, "w_rid parameter should be present")
        assertTrue("test" in signedParams, "original parameters should be preserved")

        // Verify parameter formats
        val wts = signedParams["wts"]
        val wRid = signedParams["w_rid"]

        assertEquals(wts?.isNotEmpty(), true, "wts should not be empty")
        assertEquals(wRid?.isNotEmpty(), true, "w_rid should not be empty")
        assertEquals(wRid?.length, 32, "w_rid should be 32 characters (MD5 hash)")
        assertEquals(wRid?.all { it in "0123456789abcdef" }, true, "w_rid should contain only hex characters")
    }
}