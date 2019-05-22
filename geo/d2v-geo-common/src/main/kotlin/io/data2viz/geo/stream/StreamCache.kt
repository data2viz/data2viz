package io.data2viz.geo.stream

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