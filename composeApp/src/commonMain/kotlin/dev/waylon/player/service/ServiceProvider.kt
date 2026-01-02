package dev.waylon.player.service

/**
 * Service Provider
 * 
 * Responsible for managing various service instances used in the application
 * Provides a unified service access entry, facilitating the switching of service implementations for different platforms
 */
expect object ServiceProvider {
    /**
     * Currently used video platform service instance
     * 
     * Can be dynamically switched according to configuration or user selection
     * For example: If you need to use YouTube, you can replace it with YoutubeAdapter()
     */
    val videoService: VideoPlatformService
}