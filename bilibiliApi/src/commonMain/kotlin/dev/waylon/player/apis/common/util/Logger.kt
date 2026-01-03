package dev.waylon.player.apis.common.util

import co.touchlab.kermit.Severity
import co.touchlab.kermit.StaticConfig

/**
 * Logger utility class for cross-platform logging using Kermit
 */
object Logger {


    private val logger = Logger(StaticConfig())

    fun v(tag: String, message: String, throwable: Throwable? = null) {
        logger.log(Severity.Verbose, tag, throwable, message)
    }

    fun d(tag: String, message: String, throwable: Throwable? = null) {
        logger.log(Severity.Debug, tag, throwable, message)
    }

    fun i(tag: String, message: String, throwable: Throwable? = null) {
        logger.log(Severity.Info, tag, throwable, message)
    }

    fun w(tag: String, message: String, throwable: Throwable? = null) {
        logger.log(Severity.Warn, tag, throwable, message)
    }

    fun e(tag: String, message: String, throwable: Throwable? = null) {
        logger.log(Severity.Error, tag, throwable, message)
    }
}