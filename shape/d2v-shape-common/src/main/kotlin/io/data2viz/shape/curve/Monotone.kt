package io.data2viz.shape.curve

import io.data2viz.path.PathAdapter
import io.data2viz.shape.Curve
import kotlin.math.abs
import kotlin.math.min

private class ReflectContext(val context: PathAdapter) : PathAdapter {
    override fun moveTo(x: Double, y: Double) {
        context.moveTo(y, x)
    }

    override fun lineTo(x: Double, y: Double) {
        context.lineTo(y, x)
    }

    override fun closePath() {
        context.closePath()
    }

    override fun bezierCurveTo(x1: Double, y1: Double, x2: Double, y2: Double, x: Double, y: Double) {
        context.bezierCurveTo(y1, x1, y2, x2, y, x)
    }

    override fun quadraticCurveTo(x1: Double, y1: Double, x: Double, y: Double) {}
    override fun arcTo(fromX: Double, fromY: Double, toX: Double, toY: Double, radius: Double) {}
    override fun arc(centerX: Double, centerY: Double, radius: Double, startAngle: Double, endAngle: Double, counterClockWise: Boolean) {}
    override fun rect(x: Double, y: Double, w: Double, h: Double) {}
}

abstract class AbstractMonotone(override val context: PathAdapter) : Curve {

    private var x0 = -1.0
    private var y0 = -1.0
    private var x1 = -1.0
    private var y1 = -1.0
    private var t0 = -1.0

    private var lineStatus = -1
    private var pointStatus = -1

    override fun areaStart() {
        lineStatus = 0
    }

    override fun areaEnd() {
        lineStatus = -1
    }

    override fun lineStart() {
        x0 = -1.0
        y0 = -1.0
        x1 = -1.0
        y1 = -1.0
        t0 = -1.0
        pointStatus = 0
    }

    override fun lineEnd() {
        when (pointStatus) {
            2 -> context.lineTo(x1, y1)
            3 -> curve(t0, slope2(t0))
        }
        if (lineStatus > 0) context.closePath()
        lineStatus = 1 - lineStatus
    }

    override fun point(x: Double, y: Double) {
        var t1 = -1.0
        if (x == x1 && y == y1) return

        when (pointStatus) {
            0 -> {
                pointStatus = 1
                if (lineStatus > 0) context.lineTo(x, y) else context.moveTo(x, y)
            }
            1 -> pointStatus = 2
            2 -> {
                pointStatus = 3
                t1 = slope3(x, y)
                curve(slope2(t1), t1)
            }
            else -> {
                t1 = slope3(x, y)
                curve(t0, t1)
            }
        }
        x0 = x1
        x1 = x
        y0 = y1
        y1 = y
        t0 = t1
    }

    fun sign(x: Double): Double {
        return if (x < 0) -1.0 else 1.0
    }

    /**
     * According to https://en.wikipedia.org/wiki/Cubic_Hermite_spline#Representations
     * "you can express cubic Hermite interpolation in terms of cubic Bézier curves
     * with respect to the four values p0, p0 + m0 / 3, p1 - m1 / 3, p1".
     */
    private fun curve(t0: Double, t1: Double) {
        val dx = (x1 - x0) / 3
        context.bezierCurveTo(x0 + dx, y0 + dx * t0, x1 - dx, y1 - dx * t1, x1, y1)
    }

    /**
     * Calculate the slopes of the tangents (Hermite-type interpolation) based on
     * the following paper: Steffen, M. 1990. A Simple Method for Monotonic
     * Interpolation in One Dimension. Astronomy and Astrophysics, Vol. 239, NO.
     * NOV(II), P. 443, 1990.
     */
    fun slope3(x2: Double, y2: Double): Double {
        val h0 = x1 - x0
        val h1 = x2 - x1
        val divider0 = if (h0 != 0.0) h0 else if (h1 < 0) 0.0 else -0.0
        val divider1 = if (h1 != 0.0) h1 else if (h0 < 0) 0.0 else -0.0
        val s0 = (y1 - y0) / divider0
        val s1 = (y2 - y1) / divider1
        val p = (s0 * h1 + s1 * h0) / (h0 + h1)
        val value = (sign(s0) + sign(s1)) * min(abs(s0), min(abs(s1), 0.5 * abs(p)))
        return if (value.isNaN()) 0.0 else value
    }

    /**
     * Calculate a one-sided slope.
     */
    fun slope2(t: Double): Double {
        val h = x1 - x0;
        return if (h != 0.0) ((3 * (y1 - y0) / h - t) / 2) else t
    }
}

class MonotoneX(context: PathAdapter) : AbstractMonotone(context)
class MonotoneY(context: PathAdapter) : AbstractMonotone(ReflectContext(context)) {

    override fun point(x: Double, y: Double) {
        super.point(y, x)
    }
}
