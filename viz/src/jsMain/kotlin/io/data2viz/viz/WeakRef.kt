package io.data2viz.viz

internal external class WeakRef<T>(element: T) {
    fun deref(): T?
}
