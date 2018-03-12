package io.data2viz.geo.projection

import io.data2viz.geo.ModifiedStream
import io.data2viz.geo.asin
import io.data2viz.geo.cartesian
import io.data2viz.math.EPSILON
import io.data2viz.math.toRadians
import kotlin.math.abs
import kotlin.math.atan2
import kotlin.math.cos
import kotlin.math.sqrt

const val MAX_DEPTH = 16
val COS_MIN_DISTANCE = cos(30.0.toRadians())

fun resample(project: Projectable, delta2: Double) = if(delta2 != .0) _resample(project, delta2) else resampleNone(project)

private fun resampleNone(project: Projectable): (Stream)->Stream {
    return { stream: Stream ->
        object : ModifiedStream(stream) {
            override fun point(x: Double, y: Double, z: Double) {
                val p = project.project(x, y)
                stream.point(p[0], p[1], 0.0)
            }
        }
    }
}

private fun _resample(project: Projectable, delta2: Double): (Stream)->Stream {
    fun resampleLineTo(x0: Double, y0: Double, lambda0: Double, a0: Double, b0: Double, c0: Double,
                               x1: Double, y1: Double, lambda1: Double, a1: Double, b1: Double, c1: Double,
                               depth: Int, stream: Stream) {
        var newDepth = depth

        val dx = x1 - x0
        val dy = y1 - y0
        val d2 = dx * dx + dy * dy
        if(d2 > 4 * delta2 && newDepth != 0) {
            newDepth--
            var a = a0 + a1
            var b = b0 + b1
            var c = c0 + c1
            val m = sqrt(a * a + b * b + c * c)
            c /= m
            val phi2 = c.asin
            val lambda2 = when {
                abs(abs(c) - 1) < EPSILON || abs(lambda0 - lambda1) < EPSILON -> (lambda0 + lambda1) / 2
                else -> atan2(b, a)
            }
            val p = project.project(lambda2, phi2)
            val x2 = p[0]
            val y2 = p[1]
            val dx2 = x2 - x0
            val dy2 = y2 - y0
            val dz = dy * dx2 - dx * dy2
            if(dz * dz / d2 > delta2 // perpendicular projected distance
                    || abs((dx * dx2 + dy * dy2) / d2 - 0.5) > 0.3 // midpoint close to an end
                    || a0 * a1 + b0 * b1 + c0 * c1 < COS_MIN_DISTANCE) { // angular distance
                a /= m
                b /= m
                resampleLineTo(x0, y0, lambda0, a0, b0, c0, x2, y2, lambda2, a, b, c, newDepth, stream)
                stream.point(x2, y2, 0.0)
                resampleLineTo(x2, y2, lambda2, a, b, c, x1, y1, lambda1, a1, b1, c1, newDepth, stream)
            }
        }
    }

    return { stream: Stream ->
        object : Stream {

            // First point
            var lambda00 = Double.NaN
            var x00 = Double.NaN
            var y00 = Double.NaN
            var a00 = Double.NaN
            var b00 = Double.NaN
            var c00 = Double.NaN

            // Previous point
            var lambda0 = Double.NaN
            var x0 = Double.NaN
            var y0 = Double.NaN
            var a0 = Double.NaN
            var b0 = Double.NaN
            var c0 = Double.NaN

            var currentPoint = ::defaultPoint
            var currentLineStart = ::defaultLineStart
            var currentLineEnd = ::defaultLineEnd
            var currentPolygonStart = { stream.polygonStart(); currentLineStart = ::ringStart }
            var currentPolygonEnd = { stream.polygonEnd(); currentLineStart = ::defaultLineStart }

            override fun lineEnd() = currentLineEnd()
            override fun lineStart() = currentLineStart()
            override fun point(x: Double, y: Double, z: Double) = currentPoint(x, y, z)
            override fun polygonEnd() = currentPolygonEnd()
            override fun polygonStart() = currentPolygonStart()

            private fun defaultPoint(x: Double, y: Double, z: Double) {
                val p = project.project(x, y)
                stream.point(p[0], p[1], 0.0)
            }

            private fun defaultLineStart() {
                x0 = Double.NaN
                currentPoint = ::linePoint
                stream.lineStart()
            }

            private fun linePoint(lambda: Double, phi: Double, z: Double) {
                val c = cartesian(doubleArrayOf(lambda, phi))
                val p = project.project(lambda, phi)
                resampleLineTo(x0, y0, lambda0, a0, b0, c0, p[0], p[1], lambda, c[0], c[1], c[2], MAX_DEPTH, stream)
                x0 = p[0]
                y0 = p[1]
                lambda0 = lambda
                a0 = c[0]
                b0 = c[1]
                c0 = c[2]
                stream.point(x0, y0, z)
            }

            private fun defaultLineEnd() {
                currentPoint = ::defaultPoint
                stream.lineEnd()
            }

            private fun ringStart() {
                defaultLineStart()
                currentPoint = ::ringPoint
                currentLineEnd = ::ringEnd
            }

            private fun ringPoint(lambda: Double, phi: Double, z: Double) {
                lambda00 = lambda
                linePoint(lambda, phi, 0.0)
                x00 = x0
                y00 = y0
                a00 = a0
                b00 = b0
                c00 = c0
                currentPoint = ::linePoint
            }

            private fun ringEnd() {
                resampleLineTo(x0, y0, lambda0, a0, b0, c0, x00, y00, lambda00, a00, b00, c00, MAX_DEPTH, stream)
                currentLineEnd = ::defaultLineEnd
                lineEnd()
            }
        }
    }
}