package io.data2viz.shape

import io.data2viz.path.PathAdapter

fun <D> area(init: AreaGenerator<D>.() -> Unit) = AreaGenerator<D>().apply(init)

/**
 * The area generator produces an area, as in an area chart. An area is defined by two bounding lines, either splines
 * or polylines. Typically, the two lines share the same x-values (x0 = x1), differing only in y-value (y0 and y1);
 * most commonly, y0 is defined as a constant representing zero. The first line (the topline) is defined by x1 and y1
 * and is rendered first; the second line (the baseline) is defined by x0 and y0 and is rendered second, with the
 * points in reverse order. With a curveLinear curve, this produces a clockwise polygon.
 */
class AreaGenerator<D> {

    /**
     * The type of curve used to draw the bounding lines of the area.
     */
    var curve: (PathAdapter) -> Curve = curves.linear

    /**
     * X-value for the base-line, should take a Domain object and return a Double.
     */
    var xBaseline: (D) -> Double = const(.0)

    /**
     * Y-value for the base-line, should take a Domain object and return a Double.
     */
    var yBaseline: (D) -> Double = const(.0)

    /**
     * X-value for the top-line, should take a Domain object and return a Double.
     * If xTopline is the same as xBaseline you may let it null.
     */
    var xTopline: ((D) -> Double)? = null

    /**
     * Y-value for the top-line, should take a Domain object and return a Double.
     * If yTopline is the same as yBaseline you may let it null.
     */
    var yTopline: ((D) -> Double)? = null

    /**
     * This will indicate if a Domain value should be displayed.
     * If defined(d) returns true, the point is on the line.
     * If defined(d) returns false, the point is omitted.
     */
    var defined: (D) -> Boolean = const(true)


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
    fun <C : PathAdapter> render(data: List<D>, context: C): C {
        val n = data.size

        val x0z = Array(n, { 0.0 })
        val y0z = Array(n, { 0.0 })

        var j = 0
        var defined0 = false
        val output = curve(context)

        for (i in 0..n) {
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
                x0z[i] = xBaseline(d)
                y0z[i] = yBaseline(d)
                val outputX = if (xTopline != null) xTopline!!(d) else x0z[i]
                val outputY = if (yTopline != null) yTopline!!(d) else y0z[i]
                output.point(outputX, outputY)
            }
        }
        return context
    }
}