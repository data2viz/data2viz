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
abstract class CachedProjection : Projection {

    val streamCache = StreamCache()


    /**
     * Reset cache
     */
    fun reset() {
        streamCache.reset()
    }

    

    override fun bindTo(downstream: Stream): Stream {

        if (!streamCache.isCacheValidFor(downstream)) {
            val resultStream = fullCycleStream(downstream)
            streamCache.cache(downstream, resultStream)
        }

        return streamCache.cachedResultStream!!
    }

    /**
     * Provides full cycle of transformations
     *
     * @param stream original source stream
     * @return result stream with applied transformations
     */
    protected abstract fun fullCycleStream(stream: Stream): Stream

}


