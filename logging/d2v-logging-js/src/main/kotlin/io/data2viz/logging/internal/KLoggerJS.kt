package io.data2viz.logging.internal

import io.data2viz.logging.KLogger
import io.data2viz.logging.KotlinLoggingLevel
import io.data2viz.logging.Marker
import io.data2viz.logging.isLoggingEnabled

internal class KLoggerJS(private val loggerName: String) : KLogger {


    override fun debug(msg: () -> Any?) {
        if (KotlinLoggingLevel.TRACE.isLoggingEnabled()) {
            console.log("DEBUG: [$loggerName] ${msg.toStringSafe()}")
        }
    }


    private fun Throwable?.throwableToString(): String {
        if (this == null) {
            return ""
        }
        var msg = ""
        var current = this
        while (current != null && current.cause != current) {
            msg += ", Caused by: '${current.message}'"
            current = current.cause
        }
        return msg
    }
}
