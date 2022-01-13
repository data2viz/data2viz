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



/**
 * This stream delegates all calls to the delegate, allowing to override only what should
 * be treated another way.
 */
public open class DelegateStreamAdapter(public val delegate: Stream) : Stream {
    override fun point(x: Double, y: Double, z: Double)   :Unit  = delegate.point(x, y, z)
    override fun lineStart()                              :Unit  = delegate.lineStart()
    override fun lineEnd()                                :Unit  = delegate.lineEnd()
    override fun polygonStart()                           :Unit  = delegate.polygonStart()
    override fun polygonEnd()                             :Unit  = delegate.polygonEnd()
    override fun sphere()                                 :Unit  = delegate.sphere()
}


