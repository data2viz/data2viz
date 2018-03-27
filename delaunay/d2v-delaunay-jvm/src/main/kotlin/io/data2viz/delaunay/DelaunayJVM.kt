package io.data2viz.delaunay


actual fun typedIntArray(size: Int): TypedIntArray = TypedIntArrayDelegate(IntArray(size))

class TypedIntArrayDelegate(val array:IntArray): TypedIntArray {
    override fun set(i: Int, value: Int) {
        array[i] = value
    }

    override fun subarray(start: Int, end: Int): TypedIntArray = TypedIntArrayDelegate(array.sliceArray(start..end))

    override fun get(ar: Int): Int = array[ar]

    override val length: Int = array.size

}
