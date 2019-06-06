package io.data2viz.geo.projection.common

import io.data2viz.geo.geometry.limitedAsin
import io.data2viz.geo.stream.DelegateStreamAdapter
import io.data2viz.geo.stream.Stream
import io.data2viz.math.EPSILON
import io.data2viz.math.deg
import kotlin.math.*

/**
 * maximum depth of subdivision
 */
const val MAX_DEPTH = 16

/**
 * cos(minimum angular distance)
 */
val COS_MIN_DISTANCE = 30.deg.cos

/**
 * if deltaPrecision > .0
 *    adds a Resample stream in the chain to split the lines representing curves into smaller segments, to have
 *    a smooth line.
 * else
 *    just perform the projection of points, transforming spheric coordinates into cartesian ones.
 */
fun resample(projector: Projector, delta2Precision: Double): (Stream) -> Stream =
    if (delta2Precision != .0) //todo > .0 ?
        { stream: Stream -> ResampleStream(stream, projector, delta2Precision) }
    else
        resampleNone(projector)


private class ResampleStream(
    val stream: Stream,
    val projector: Projector,
    val delta2Precision: Double = .5
) : Stream {

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


    internal fun resampleLineTo(
        x0: Double, y0: Double, lambda0: Double, a0: Double, b0: Double, c0: Double,
        x1: Double, y1: Double, lambda1: Double, a1: Double, b1: Double, c1: Double,
        depth: Int, stream: Stream
    ) {
        val dx = x1 - x0
        val dy = y1 - y0
        val d2 = dx * dx + dy * dy
        if (d2 > 4 * delta2Precision && depth > 0) {
            val newDepth = depth - 1

            var a = a0 + a1
            var b = b0 + b1
            var c = c0 + c1
            val m = sqrt(a * a + b * b + c * c)
            c /= m
            val phi2 = c.limitedAsin //todo why? d3js use asin
            val lambda2 = when {
                abs(abs(c) - 1) < EPSILON || abs(lambda0 - lambda1) < EPSILON -> (lambda0 + lambda1) / 2
                else -> atan2(b, a)
            }
            val x2 = projector.projectLambda(lambda2, phi2)
            val y2 = projector.projectPhi(lambda2, phi2)

            val dx2 = x2 - x0
            val dy2 = y2 - y0
            val dz = dy * dx2 - dx * dy2
            if (dz * dz / d2 > delta2Precision // perpendicular projected distance
                || abs((dx * dx2 + dy * dy2) / d2 - 0.5) > 0.3 // midpoint close to an end
                || a0 * a1 + b0 * b1 + c0 * c1 < COS_MIN_DISTANCE
            ) { // angular distance
                a /= m
                b /= m
                resampleLineTo(x0, y0, lambda0, a0, b0, c0, x2, y2, lambda2, a, b, c, newDepth, stream)
                stream.point(x2, y2, 0.0)
                resampleLineTo(x2, y2, lambda2, a, b, c, x1, y1, lambda1, a1, b1, c1, newDepth, stream)
            }
        }
    }

    enum class PointContext      { DEFAULT, RING, LINE }
    enum class LineStartContext  { DEFAULT, RING}
    enum class LineEndContext    { DEFAULT, RING}

    var pointContext         = PointContext.DEFAULT
    var lineStartContext     = LineStartContext.DEFAULT
    var lineEndContext       = LineEndContext.DEFAULT

    override fun lineStart() {
        when (lineStartContext) {
            LineStartContext.DEFAULT -> lineStartDefault()
            LineStartContext.RING -> lineStartRing()
        }
    }

    override fun lineEnd() {
        when (lineEndContext) {
            LineEndContext.DEFAULT -> lineEndDefault()
            LineEndContext.RING -> lineEndRing()
        }
    }

    override fun point(x: Double, y: Double, z: Double) {
        when (pointContext) {
            PointContext.DEFAULT -> pointDefault(x,y,z)
            PointContext.LINE -> pointLine(x, y, z)
            PointContext.RING -> pointRing(x,y,z)
        }
    }

    override fun polygonStart() {
        stream.polygonStart()
        lineStartContext = LineStartContext.RING
    }

    override fun polygonEnd() {
        stream.polygonEnd()
        lineStartContext = LineStartContext.DEFAULT
    }



    /**
     * By default, just delegate to next stream after projection.
     */
    fun pointDefault(x: Double, y: Double, z: Double) {
        stream.point(
            projector.projectLambda(x, y),
            projector.projectPhi(x, y),
            z
        )
    }

    fun pointLine(lambda: Double, phi: Double, alt: Double) {
        val cosPhi = cos(phi)
        val cart0 = cosPhi * cos(lambda)
        val cart1 = cosPhi * sin(lambda)
        val cart2 = sin(phi)

        val p0 = projector.projectLambda(lambda, phi)
        val p1 = projector.projectPhi(lambda, phi)

        resampleLineTo(
            x0, y0, lambda0, a0, b0, c0,
            p0, p1, lambda, cart0, cart1, cart2,
            MAX_DEPTH, stream
        )

        x0 = p0
        y0 = p1
        lambda0 = lambda
        a0 = cart0
        b0 = cart1
        c0 = cart2
        stream.point(x0, y0, alt)
    }


    fun pointRing(x: Double, y: Double, z: Double) {
        lambda00 = x
        pointLine(x, y, z)
        x00 = x0
        y00 = y0
        a00 = a0
        b00 = b0
        c00 = c0
        pointContext = PointContext.LINE
    }

    fun lineStartDefault() {
        x0 = Double.NaN
        pointContext = PointContext.LINE
        stream.lineStart()
    }

    fun lineStartRing() {
        lineStartDefault()
        pointContext = PointContext.RING
        lineEndContext = LineEndContext.RING
    }

    fun lineEndDefault() {
        pointContext = PointContext.DEFAULT
        stream.lineEnd()
    }

    fun lineEndRing() {
        resampleLineTo(x0, y0, lambda0, a0, b0, c0, x00, y00, lambda00, a00, b00, c00, MAX_DEPTH, stream)
        lineEndContext = LineEndContext.DEFAULT
        lineEnd()
    }
}

/**
 * No resampling, just project points before passing to next stream.
 */
private fun resampleNone(projector: Projector): (Stream) -> Stream {
    return { stream: Stream ->
        object : DelegateStreamAdapter(stream) {
            override fun point(x: Double, y: Double, z: Double) {
                stream.point(
                    projector.projectLambda(x, y),
                    projector.projectPhi(x, y),
                    z
                )
            }
        }
    }
}
