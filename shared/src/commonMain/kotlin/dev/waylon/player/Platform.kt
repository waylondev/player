package dev.waylon.player

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform