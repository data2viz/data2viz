package io.data2viz.geo.projection.common

import io.data2viz.geo.stream.DelegateStreamAdapter
import io.data2viz.geo.stream.Stream
import io.data2viz.geo.geometry.limitedAsin
import io.data2viz.math.EPSILON
import io.data2viz.math.toRadians
import kotlin.math.*

const val MAX_DEPTH = 16
val COS_MIN_DISTANCE = cos(30.0.toRadians())

fun resample(project: Projector, delta2: Double) =
    if (delta2 != .0) _resample(
        project,
        delta2
    ) else resampleNone(project)

private fun resampleNone(project: Projector): (Stream) -> Stream {
    return { stream: Stream ->
        object : DelegateStreamAdapter(stream) {
            override fun point(x: Double, y: Double, z: Double) {
                stream.point(project.projectLambda(x, y), project.projectPhi(x, y), 0.0)
            }
        }
    }
}


private fun _resample(project: Projector, delta2: Double): (Stream) -> Stream {
    return { stream: Stream -> ReSampledStream(stream, project, delta2) }
}


interface PointFunction {
    fun invoke(reSampledStream: ReSampledStream, x: Double, y: Double, z: Double)
}

interface LineStartFunction {
    fun invoke(reSampledStream: ReSampledStream)
}

interface LineEndFunction {
    fun invoke(reSampledStream: ReSampledStream)
}


object DefaultPointFunction : PointFunction {
    override fun invoke(reSampledStream: ReSampledStream, x: Double, y: Double, z: Double) {
//            val p = reSampledStream.project.project(translateX, translateY)
//            reSampledStream.stream.point(p[0], p[1], 0.0)
//            val p = reSampledStream.project.project(translateX, translateY)
        reSampledStream.stream.point(
            reSampledStream.project.projectLambda(x, y),
            reSampledStream.project.projectPhi(x, y),
            0.0
        )
    }

}

object LinePointFunction : PointFunction {


    override fun invoke(reSampledStream: ReSampledStream, x: Double, y: Double, z: Double) {

        reSampledStream.apply {

            val cosPhi = cos(y)
            val cart0 = cosPhi * cos(x)
            val cart1 = cosPhi * sin(x)
            val cart2 = sin(y)

            val p0 = project.projectLambda(x, y)
            val p1 = project.projectPhi(x, y)
            resampleLineTo(x0, y0, lambda0, a0, b0, c0, p0, p1, x, cart0, cart1, cart2,
                MAX_DEPTH, stream)
            x0 = p0
            y0 = p1
            lambda0 = x
            a0 = cart0
            b0 = cart1
            c0 = cart2
            stream.point(x0, y0, z)
        }

    }

}


object RingPointFunction : PointFunction {
    override fun invoke(reSampledStream: ReSampledStream, x: Double, y: Double, z: Double) {
        reSampledStream.apply {
            lambda00 = x
            LinePointFunction.invoke(reSampledStream, x, y, 0.0)
            x00 = x0
            y00 = y0
            a00 = a0
            b00 = b0
            c00 = c0
            currentPoint = LinePointFunction
        }
    }

}

object DefaultLineStartFunction :
    LineStartFunction {
    override fun invoke(reSampledStream: ReSampledStream) {
        reSampledStream.x0 = Double.NaN
        reSampledStream.currentPoint = DefaultPointFunction
        reSampledStream.stream.lineStart()
    }


}

object DefaultLineEndFunction :
    LineEndFunction {
    override fun invoke(reSampledStream: ReSampledStream) {
        reSampledStream.currentPoint = DefaultPointFunction
        reSampledStream.stream.lineEnd()
    }
}

object RingLineStartFunction :
    LineStartFunction {
    override fun invoke(reSampledStream: ReSampledStream) {
        reSampledStream.apply {
            DefaultLineStartFunction.invoke(reSampledStream)
            currentPoint = RingPointFunction
            currentLineEnd = RingLineEndFunction
        }
    }


}

object RingLineEndFunction :
    LineEndFunction {
    override fun invoke(reSampledStream: ReSampledStream) {
        reSampledStream.apply {

            resampleLineTo(x0, y0, lambda0, a0, b0, c0, x00, y00, lambda00, a00, b00, c00,
                MAX_DEPTH, stream)
            currentLineEnd = DefaultLineEndFunction
            lineEnd()
        }
    }
}


class ReSampledStream(val stream: Stream, val project: Projector, val delta2: Double) :
    Stream {

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

    var currentPoint: PointFunction =
        DefaultPointFunction
    var currentLineStart: LineStartFunction =
        DefaultLineStartFunction

    var currentLineEnd: LineEndFunction =
        DefaultLineEndFunction


    var currentPolygonStart = {
        stream.polygonStart()
        currentLineStart = RingLineStartFunction
    }
    var currentPolygonEnd = {
        stream.polygonEnd()
        currentLineStart = DefaultLineStartFunction
    }

    override fun lineEnd() = currentLineEnd.invoke(this)
    override fun lineStart() = currentLineStart.invoke(this)
    override fun point(x: Double, y: Double, z: Double) = currentPoint.invoke(this, x, y, z)
    override fun polygonEnd() = currentPolygonEnd()
    override fun polygonStart() = currentPolygonStart()

    internal fun resampleLineTo(
        x0: Double, y0: Double, lambda0: Double, a0: Double, b0: Double, c0: Double,
        x1: Double, y1: Double, lambda1: Double, a1: Double, b1: Double, c1: Double,
        depth: Int, stream: Stream
    ) {
        var newDepth = depth

        val dx = x1 - x0
        val dy = y1 - y0
        val d2 = dx * dx + dy * dy
        if (d2 > 4 * delta2 && newDepth != 0) {
            newDepth--
            var a = a0 + a1
            var b = b0 + b1
            var c = c0 + c1
            val m = sqrt(a * a + b * b + c * c)
            c /= m
            val phi2 = c.limitedAsin
            val lambda2 = when {
                abs(abs(c) - 1) < EPSILON || abs(lambda0 - lambda1) < EPSILON -> (lambda0 + lambda1) / 2
                else -> atan2(b, a)
            }

            val x2 = project.projectLambda(lambda2, phi2)
            val y2 = project.projectPhi(lambda2, phi2)

            val dx2 = x2 - x0
            val dy2 = y2 - y0
            val dz = dy * dx2 - dx * dy2
            if (dz * dz / d2 > delta2 // perpendicular projected distance
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

}