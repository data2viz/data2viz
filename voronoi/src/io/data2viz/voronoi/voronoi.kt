package io.data2viz.voronoi

import kotlin.js.Math


val epsilon = 1e-6

data class Point(val x: Double, val y: Double) {
    fun round(): Point =
            Point(
                    Math.round(x / epsilon) * epsilon,
                    Math.round(y / epsilon) * epsilon
            )
}

//fun voronoi(points: Array<Point>): Diagram {
//    val diagram = Diagram(points)
//    return diagram
//}


data class Site(
        val point: Point,
        val index: Int
)

class Stack<T> {
    val internal: Array<T> = emptyArray()

    fun popOrCreate(create: () -> T): T {
        return internal.asDynamic().pop() ?: create()
    }

    fun push(item: T) {
        internal.asDynamic().push(item)
    }
}
