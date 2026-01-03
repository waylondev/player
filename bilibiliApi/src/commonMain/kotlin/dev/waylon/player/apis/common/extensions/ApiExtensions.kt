package dev.waylon.player.apis.common.extensions

import dev.waylon.player.apis.common.ApiService
import io.ktor.client.call.body
import io.ktor.client.statement.HttpResponse
import kotlinx.serialization.json.JsonObject

/**
 * Modern API call extension functions with DSL-style syntax
 * Provides clean, concise API execution and transformation functionality
 */

/**
 * DSL-style API execution with request builder
 */
suspend inline fun <T> ApiService<T>.executeRaw(
    request: T
): HttpResponse = execute(request)

/**
 * DSL-style API execution with transformation
 */
suspend inline fun <T, R> ApiService<T>.executeAndTransform(
    request: T,
    crossinline transformer: suspend (HttpResponse) -> R
): R = transformer(executeRaw(request))

/**
 * Type-safe JSON parsing extension
 */
suspend inline fun <reified R> HttpResponse.parseAs(): R = body()

/**
 * JSON object conversion extension
 */
suspend inline fun HttpResponse.toJsonObject(): JsonObject = body()