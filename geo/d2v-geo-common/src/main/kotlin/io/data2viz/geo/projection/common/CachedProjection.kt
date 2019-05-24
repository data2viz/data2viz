package io.data2viz.geo.projection.common

import io.data2viz.geo.stream.Stream
import io.data2viz.geo.stream.StreamCache


/**
 * Abstract projection implementation with caching stream transformations caching
 *
 * @see Projection
 * @see StreamCache
 * @see ProjectorProjection
 */
abstract class CachedProjection() : Projection {

    val streamCache = StreamCache()


    fun reset() {
        streamCache.reset()
    }

    

    override fun stream(stream: Stream): Stream {

        if (!streamCache.isCacheValidFor(stream)) {
            val resultStream = fullCycleStream(stream)
            streamCache.cache(stream, resultStream)
        }

        return streamCache.cachedResultStream!!
    }

    abstract fun fullCycleStream(stream: Stream): Stream

}


