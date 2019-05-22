package io.data2viz.geo.projection.common

import io.data2viz.geo.stream.Stream
import io.data2viz.geo.stream.StreamCache


/**
 * todo What is it?
 */
abstract class CachedProjection() : Projection {

    val streamCache = StreamCache()


    fun reset() {
        streamCache.reset()
    }

    

    override fun stream(originalStream: Stream): Stream {

        if (!streamCache.isCacheValidFor(originalStream)) {
            val resultStream = fullCycleStream(originalStream)
            streamCache.cache(originalStream, resultStream)
        }

        return streamCache.cachedResultStream!!
    }

    abstract fun fullCycleStream(originalStream: Stream): Stream

}


