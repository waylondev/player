package dev.waylon.player.apis.common.extensions

import dev.waylon.player.apis.common.ApiService
import io.ktor.client.call.body
import io.ktor.client.statement.HttpResponse
import kotlinx.serialization.json.JsonObject

/**
 * API call extension functions
 * Provides common API execution and transformation functionality to avoid code duplication
 */

/**
 * Execute API call and return raw response
 *
 * @param request Request object
 * @return Raw HTTP response
 */
suspend inline fun <T> ApiService<T>.executeRaw(request: T): HttpResponse = execute(request)


/**
 * Execute API call and automatically transform response
 *
 * @param request Request object
 * @param transformer Response transformation function
 * @return Transformed result
 */

suspend inline fun <T, R> ApiService<T>.executeAndTransform(
    request: T,
    crossinline transformer: suspend (HttpResponse) -> R
): R {
    val response = executeRaw(request)
    return transformer(response)
}


/**
 * Convenience extension for parsing JSON response using Ktor's built-in body<T>()
 */
suspend inline fun <reified R> HttpResponse.parseAs(): R = this.body()

/**
 * Convenience extension for converting to JSON object
 */
suspend inline fun HttpResponse.toJsonObject(): JsonObject = this.body()