package dev.waylon.player.service

import dev.waylon.player.apis.adapter.BilibiliAdapter

actual object ServiceProvider {
    actual val videoService: VideoPlatformService by lazy {
        BilibiliAdapter()
    }
}