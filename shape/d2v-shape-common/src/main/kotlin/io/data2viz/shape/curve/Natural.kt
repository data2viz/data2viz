package io.data2viz.shape.curve

import io.data2viz.geom.Path
import io.data2viz.shape.Curve

class Natural(override val path: Path) : Curve {

    private var x = arrayListOf<Double>()
    private var y = arrayListOf<Double>()

    private var lineStatus = -1

    override fun areaStart() {
        lineStatus = 0
    }

    override fun areaEnd() {
        lineStatus = -1
    }

    override fun lineStart() {
        x.clear()
        y.clear()
    }

    override fun lineEnd() {
        val n = x.size
        if (n > 0) {
            if (lineStatus > 0) path.lineTo(x[0], y[0]) else path.moveTo(x[0], y[0])

            if (n > 1) {
                if (n == 2) path.lineTo(x[1], y[1])
                else {
                    val px = controlPoints(x)
                    val py = controlPoints(y)
                    var i0 = 0
                    var i1 = 1
                    (1 until n).forEach {
                        path.bezierCurveTo(px[0][i0], py[0][i0], px[1][i0], py[1][i0], x[i1], y[i1])
                        i0++
                        i1++
                    }
                }
            }
        }

        if (lineStatus > -1) {
            if (lineStatus > 0) {
                path.closePath()
            }
            lineStatus = 1 - lineStatus
        }
        x.clear()
        y.clear()
    }

    /**
     * See https://www.particleincell.com/2012/bezier-splines/ for derivation.
     * Note : points.size > 2 (in other case we just draw a line between points)
     */
    private fun controlPoints(points: ArrayList<Double>): Array<Array<Double>> {
        val n = points.size - 1
        var m: Double

        val a = Array(n, { 0.0 })
        val b = Array(n, { 0.0 })
        val r = Array(n, { 0.0 })

        a[0] = 0.0
        b[0] = 2.0
        r[0] = points[0] + 2 * points[1]
        (1 until n - 1).forEach { i ->
            a[i] = 1.0
            b[i] = 4.0
            r[i] = 4 * points[i] + 2 * points[i + 1]
        }
        a[n - 1] = 2.0
        b[n - 1] = 7.0
        r[n - 1] = 8 * points[n - 1] + points[n]

        (1 until n).forEach { i ->
            m = a[i] / b[i - 1]
            b[i] -= m
            r[i] -= m * r[i - 1]
        }
        a[n - 1] = r[n - 1] / b[n - 1]

        ((n - 2) downTo 0).forEach { i ->
            a[i] = (r[i] - a[i + 1]) / b[i]
        }
        b[n - 1] = (points[n] + a[n - 1]) / 2

        (0 until n - 1).forEach { i ->
            b[i] = 2 * points[i + 1] - a[i + 1]
        }

        return arrayOf(a, b)
    }

    override fun point(x: Double, y: Double) {
        this.x.add(x)
        this.y.add(y)
    }
}