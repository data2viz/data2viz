package io.data2viz.logging


interface KLogger {

    /**
     * Lazy add a log message if isDebugEnabled is true
     */
    fun debug(msg: () -> Any?)

}