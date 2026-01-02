package dev.waylon.player.apis.common

import io.ktor.client.statement.HttpResponse

/**
 * Modern Kotlin API Service Interface
 *
 * This interface defines a standard way to execute API requests and handle responses.
 * Subclasses implement the [execute] method to perform the actual API call and return the HttpResponse.
 *
 * @param REQ The type of request object that this service handles
 */
interface ApiService<REQ> {
    /**
     * Main implementation method to be overridden by subclasses.
     * Handles the API request and returns the raw HttpResponse.
     *
     * @param request The request object containing the necessary parameters
     * @return The raw HttpResponse from the API call
     */
    suspend fun execute(request: REQ): HttpResponse

}


