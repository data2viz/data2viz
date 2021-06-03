/*
 * Copyright (c) 2018-2021. data2viz sàrl.
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

package io.data2viz.geo.stream
import io.data2viz.geo.projection.common.CachedProjection

/**
 * Cache transformed stream result
 * @see CachedProjection
 */
public class StreamCache {

    public fun isCacheValidFor(originalStream: Stream): Boolean = originalSourceStream == originalStream

    internal var cachedResultStream: Stream? = null
        private set

    internal var originalSourceStream: Stream? = null
        private set


    internal fun cache(originalStream: Stream, resultStream: Stream) {
        originalSourceStream = originalStream
        cachedResultStream = resultStream
    }

    public fun reset() {
        cachedResultStream = null
        originalSourceStream = null
    }
}
