package io.data2viz.logging.internal

import io.data2viz.logging.KLogger
import org.slf4j.Logger
import org.slf4j.Marker

/**
 * A class wrapping a [Logger] instance that is not location aware
 * all methods of [KLogger] has default implementation
 * the rest of the methods are delegated to [Logger]
 * Hence no implemented methods
 */
internal class LocationIgnorantKLogger(val underlyingLogger: Logger)
    : KLogger, Logger by underlyingLogger {


    /**
     * Lazy add a log message if isDebugEnabled is true
     */
    override fun debug(msg: () -> Any?) {
        if (isDebugEnabled) debug(msg.toStringSafe())
    }


}
