package io.data2viz.logging.internal

@Suppress("NOTHING_TO_INLINE")
internal inline fun (() -> Any?).toStringSafe(): String {
    return try {
        invoke().toString()
    } catch (e: Exception) {
        "Log message invocation failed: $e"
    }
}