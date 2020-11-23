/*
 * Copyright (c) 2018-2019. data2viz sàrl.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 *
 */

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
public abstract class CachedProjection : Projection {

    public val streamCache: StreamCache = StreamCache()


    /**
     * Reset cache
     */
    public fun reset() {
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


