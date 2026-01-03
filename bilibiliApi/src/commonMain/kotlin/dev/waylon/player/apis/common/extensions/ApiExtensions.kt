package dev.waylon.player.apis.common.extensions

import dev.waylon.player.apis.common.ApiService
import io.ktor.client.statement.HttpResponse
import kotlinx.serialization.json.JsonObject

/**
 * API调用扩展函数
 * 提供通用的API执行和转换功能，避免重复代码
 */

/**
 * 执行API调用并自动转换响应
 * 
 * @param request 请求对象
 * @param transformer 响应转换函数
 * @return 转换后的结果
 */
suspend inline fun <T, R> ApiService<T>.executeAndTransform(
    request: T,
    crossinline transformer: (JsonObject) -> R
): R {
    val response = execute(request)
    val root = response.body<JsonObject>()
    return transformer(root)
}

/**
 * 执行API调用并返回原始响应
 * 
 * @param request 请求对象
 * @return 原始HTTP响应
 */
suspend fun <T> ApiService<T>.executeRaw(request: T): HttpResponse {
    return execute(request)
}

/**
 * 执行API调用并返回JSON对象
 * 
 * @param request 请求对象
 * @return JSON响应对象
 */
suspend fun <T> ApiService<T>.executeToJson(request: T): JsonObject {
    val response = execute(request)
    return response.body<JsonObject>()
}