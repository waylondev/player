package dev.waylon.player.apis.common.extensions

import dev.waylon.player.apis.common.util.WbiSignHelper
import io.ktor.client.HttpClient
import io.ktor.client.request.HttpRequestBuilder
import io.ktor.client.request.parameter
import io.ktor.client.request.request
import io.ktor.client.request.setBody
import io.ktor.client.statement.HttpResponse
import io.ktor.http.HttpMethod

/**
 * WBI Request Extensions
 * 
 * Provides extension functions for handling WBI signed requests
 */

/**
 * Makes a request with WBI signature
 * 
 * @param method The HTTP method to use
 * @param url The URL to request
 * @param params The parameters to sign
 * @param body Optional request body (for POST/PUT/PATCH requests)
 * @param builder Additional request configuration
 * @return The HTTP response
 */
suspend fun HttpClient.wbiRequest(
    method: HttpMethod,
    url: String,
    params: Map<String, Any?>,
    body: Any? = null,
    builder: HttpRequestBuilder.() -> Unit = {}
): HttpResponse {
    // Generate WBI signed parameters
    val signedParams = WbiSignHelper.generateWbiSign(params)

    // Make request with appropriate method and signed parameters
    return request(url) {
        this.method = method

        // Add WBI signed parameters as query parameters
        // According to bilibili-API-collect documentation (https://github.com/SocialSisterYi/bilibili-API-collect):
        // 1. WBI signature only processes query parameters, not request body
        // 2. Signed parameters include w_rid (signature) and wts (timestamp)
        // 3. Always add signed parameters as query parameters for all HTTP methods
        signedParams.forEach { (key, value) ->
            parameter(key, value)
        }

        // Set body if provided (WBI signature doesn't process request body)
        if (body != null) {
            setBody(body)
        }

        // Apply additional configuration
        builder()
    }
}

/**
 * Convenience method for WBI GET requests
 */
suspend fun HttpClient.wbiGet(
    url: String,
    params: Map<String, Any?>,
    builder: HttpRequestBuilder.() -> Unit = {}
): HttpResponse {
    return wbiRequest(HttpMethod.Get, url, params, null, builder)
}

/**
 * Convenience method for WBI POST requests
 */
suspend fun HttpClient.wbiPost(
    url: String,
    params: Map<String, Any?>,
    body: Any? = null,
    builder: HttpRequestBuilder.() -> Unit = {}
): HttpResponse {
    return wbiRequest(HttpMethod.Post, url, params, body, builder)
}

/**
 * Convenience method for WBI PUT requests
 */
suspend fun HttpClient.wbiPut(
    url: String,
    params: Map<String, Any?>,
    body: Any? = null,
    builder: HttpRequestBuilder.() -> Unit = {}
): HttpResponse {
    return wbiRequest(HttpMethod.Put, url, params, body, builder)
}

/**
 * Convenience method for WBI DELETE requests
 */
suspend fun HttpClient.wbiDelete(
    url: String,
    params: Map<String, Any?>,
    builder: HttpRequestBuilder.() -> Unit = {}
): HttpResponse {
    return wbiRequest(HttpMethod.Delete, url, params, null, builder)
}

/**
 * Convenience method for WBI PATCH requests
 */
suspend fun HttpClient.wbiPatch(
    url: String,
    params: Map<String, Any?>,
    body: Any? = null,
    builder: HttpRequestBuilder.() -> Unit = {}
): HttpResponse {
    return wbiRequest(HttpMethod.Patch, url, params, body, builder)
}

/**
 * Convenience method for WBI OPTIONS requests
 */
suspend fun HttpClient.wbiOptions(
    url: String,
    params: Map<String, Any?>,
    builder: HttpRequestBuilder.() -> Unit = {}
): HttpResponse {
    return wbiRequest(HttpMethod.Options, url, params, null, builder)
}

/**
 * Convenience method for WBI HEAD requests
 */
suspend fun HttpClient.wbiHead(
    url: String,
    params: Map<String, Any?>,
    builder: HttpRequestBuilder.() -> Unit = {}
): HttpResponse {
    return wbiRequest(HttpMethod.Head, url, params, null, builder)
}
