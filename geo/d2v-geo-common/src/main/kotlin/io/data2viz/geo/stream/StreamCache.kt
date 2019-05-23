package io.data2viz.geo.stream
import io.data2viz.geo.projection.common.CachedProjection

/**
 * Cache transformed stream result
 * @see CachedProjection
 */
class StreamCache() {

    fun isCacheValidFor(originalStream: Stream) = originalSourceStream == originalStream

    var cachedResultStream: Stream? = null
        private set
    var originalSourceStream: Stream? = null
        private set


    fun cache(originalStream: Stream, resultStream: Stream) {
        originalSourceStream = originalStream
        cachedResultStream = resultStream
    }

    fun reset() {
        cachedResultStream = null
        originalSourceStream = null
    }
}