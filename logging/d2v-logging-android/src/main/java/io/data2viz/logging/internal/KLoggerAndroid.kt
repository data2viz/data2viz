package io.data2viz.logging.internal

import android.util.Log
import io.data2viz.logging.KLogger
import io.data2viz.logging.Marker


class KLoggerAndroid(private val loggerName: String) : KLogger {


    override fun debug(msg: () -> Any?) { Log.d(loggerName, "TRACE: [$loggerName] ${msg.toStringSafe()}") }



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
