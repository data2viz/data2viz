package io.data2viz.shape.order

interface StackOrder<T> {
    fun sort(values: Array<T>): Array<T>
}

/*class StackOrderAscending<T> : StackOrder<T> {
    override fun sort(values: Array<T>): Array<T> {

    }
}*/