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

@file:Suppress("NOTHING_TO_INLINE")

package io.data2viz.delaunay

import org.khronos.webgl.Int32Array
import org.khronos.webgl.Uint32Array
import org.khronos.webgl.get
import org.khronos.webgl.set



@JsName("delaunator")
fun delaunautor(points: Array<Array<Double>>) = Delaunator(points)


//actual fun typedUIntArray(size:Int):TypedUIntArray = TypedUIntArrayDelegate(Uint32Array(size))
actual fun typedIntArray(size:Int): TypedIntArray = TypedIntArrayDelegate(Int32Array(size))

class TypedUIntArrayDelegate(val delegate: Uint32Array):TypedUIntArray {
    override val length: Int
        get() = delegate.length

    override fun set(i: Int, value: Int) {
        delegate[i] = value
    }
    override fun subarray(start: Int, end: Int): TypedUIntArray = TypedUIntArrayDelegate(delegate.subarray(start, end))
    override fun get(ar: Int): Int = delegate[ar]

}

class TypedIntArrayDelegate(val delegate: Int32Array):TypedIntArray {
    override val length: Int
        get() = delegate.length

    override fun set(i: Int, value: Int) {
        delegate[i] = value
    }
    override fun subarray(start: Int, end: Int): TypedIntArray = TypedIntArrayDelegate(delegate.subarray(start, end))
    override fun get(ar: Int): Int = delegate[ar]

}