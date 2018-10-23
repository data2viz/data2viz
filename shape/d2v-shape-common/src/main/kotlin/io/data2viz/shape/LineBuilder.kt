package io.data2viz.shape

import io.data2viz.geom.Path

fun <T> line(init: LineBuilder<T>.() -> Unit) = LineBuilder<T>().apply(init)

fun <T, D> const(constantValue: T): (D) -> T = { constantValue }

class LineBuilder<T> {

    var curve: (Path) -> Curve = curves.linear
    var x: (T) -> Double = const(.0)
    var y: (T) -> Double = const(.0)
    var defined: (T) -> Boolean = const(true)

    /**
     * Use the data to generate a line on the path
     */
    fun <C : Path> buildLine(data: List<T>, path: C): C {
        val dataSize = data.size

        var defined0 = false
        val output = curve(path)

        for (i in 0..dataSize) {
            if (!(i < dataSize && defined(data[i])) == defined0) {
                defined0 = !defined0
                if (defined0) output.lineStart() else output.lineEnd()
            }

            if (defined0) {
                val d = data[i]
                output.point(x(d), y(d))
            }
        }
        return path
    }
}