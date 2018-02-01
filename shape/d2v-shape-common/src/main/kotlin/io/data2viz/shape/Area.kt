package io.data2viz.shape

import io.data2viz.path.PathAdapter

fun <T> area(init: AreaGenerator<T>.() -> Unit) = AreaGenerator<T>().apply(init)

/**
 * The area generator produces an area, as in an area chart. An area is defined by two bounding lines, either splines
 * or polylines. Typically, the two lines share the same x-values (x0 = x1), differing only in y-value (y0 and y1);
 * most commonly, y0 is defined as a constant representing zero. The first line (the topline) is defined by x1 and y1
 * and is rendered first; the second line (the baseline) is defined by x0 and y0 and is rendered second, with the
 * points in reverse order. With a curveLinear curve, this produces a clockwise polygon.
 */
class AreaGenerator<T> {

    var curve: (PathAdapter) -> Curve = curves.linear
    var x0: (T) -> Double = const(.0)
    var x1: ((T) -> Double)? = null
    var y0: (T) -> Double = const(.0)
    var y1: ((T) -> Double)? = const(.0)
    var defined: (T) -> Boolean = const(true)

    // TODO : keep these ?
    fun x(x: (T) -> Double) {
        x0 = x
        x1 = null
    }

    // TODO : keep these ?
    fun y(y: (T) -> Double) {
        y0 = y
        y1 = null
    }

    // TODO : implements ?
    /*fun arealine() {
        return line().defined(defined).curve(curve).context(context);
    }

    fun lineX0() { return lineY0()}

    fun lineY0() {
        return arealine().x(x0).y(y0)
    }

    fun lineY1() {
        return arealine().x(x0).y(y1)
    }

    fun lineX1() {
        return arealine().x(x1).y(y0)
    }*/

    /**
     * Use the data to generate an area on the context
     */
    fun <C : PathAdapter> render(data: Array<T>, context: C): C {
        val n = data.size

        val x0z = Array(n, { it -> 0.0 })
        val y0z = Array(n, { it -> 0.0 })

        var j = 0
        var defined0 = false
        val output = curve(context)

        for (i in 0 .. n) {
            val areaNotEnded = i < n
            val undefined = !(areaNotEnded && defined(data[i]))
            if (undefined == defined0) {
                defined0 = !defined0
                if (defined0) {
                    j = i
                    output.areaStart()
                    output.lineStart()
                } else {
                    output.lineEnd()
                    output.lineStart()
                    for (k in i - 1 downTo j) {
                        output.point(x0z[k], y0z[k])
                    }
                    output.lineEnd()
                    output.areaEnd()
                }
            }
            if (defined0) {
                val d = data[i]
                x0z[i] = x0(d)
                y0z[i] = y0(d)
                val outputX = if (x1 != null) x1!!(d) else x0z[i]
                val outputY = if (y1 != null) y1!!(d) else y0z[i]
                output.point(outputX, outputY)
            }
        }
        return context
    }
}