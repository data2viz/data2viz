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

package io.data2viz.geo.stream

/**
 * Delegates the stream calls to a collection of streams. It simplifies the creation of
 * projection that are composed of few projections.
 * @see io.data2viz.geo.projection.AlbersUSAProjection
 */
class MultiplexStream<T>(private val streams: Collection<Stream<T>>) : Stream<T>() {
    override fun point(point: T)                        = streams.forEach { it.point(point) }
    override fun lineStart()                            = streams.forEach { it.lineStart() }
    override fun lineEnd()                              = streams.forEach { it.lineEnd() }
    override fun polygonStart()                         = streams.forEach { it.polygonStart() }
    override fun polygonEnd()                           = streams.forEach { it.polygonEnd() }
    override fun sphere()                               = streams.forEach { it.sphere() }
}