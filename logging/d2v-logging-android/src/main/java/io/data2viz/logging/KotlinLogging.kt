package io.data2viz.logging

import io.data2viz.logging.internal.KLoggerAndroid
import io.data2viz.logging.internal.KLoggerNameResolver

actual object KotlinLogging {
    /**
     * This method allow defining the logger in a file in the following way:
     * val logger = KotlinLogging.logger {}
     */
    actual fun logger(func: () -> Unit): KLogger {
        return KLoggerAndroid(KLoggerNameResolver.name(func))
    }

    actual fun logger(name: String): KLogger {
        return KLoggerAndroid(name)
    }

}