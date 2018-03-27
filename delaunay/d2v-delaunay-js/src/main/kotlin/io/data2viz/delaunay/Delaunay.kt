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