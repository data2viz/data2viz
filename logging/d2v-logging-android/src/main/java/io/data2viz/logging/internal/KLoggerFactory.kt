package io.data2viz.logging.internal

import io.data2viz.logging.KLoggable
import io.data2viz.logging.KLogger

/**
 * factory methods to obtain a [Logger]
 */
@Suppress("NOTHING_TO_INLINE")
internal object KLoggerFactory {

    /**
     * get logger for the class
     */
    internal inline fun logger(loggable: KLoggable): KLogger =
            logger(KLoggerNameResolver.name(loggable.javaClass))

    /**
     * get logger by explicit name
     */
    internal inline fun logger(name: String): KLogger = KLoggerAndroid(name)

    /**
     * get logger for the method, assuming it was declared at the logger file/class
     */
    inline internal fun logger(noinline func: () -> Unit): KLogger =
            logger(KLoggerNameResolver.name(func))


}
