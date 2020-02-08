/*
 * Copyright (c) 2018-2019. data2viz sàrl.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 *
 */

package io.data2viz.geo.projection.common

import io.data2viz.geo.StreamPoint
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

/**
 * ResampleStream is the core of projection transformation. It uses a Projector to
 * convert geographic coordinates to cartesian coordinates and also generates intermediary
 * points to smooth the cartesian lines (curves).
 *
 * The next Stream is fed with cartesian coordinates.
 */
private class ResampleStream(
    val stream: Stream,
    val projector: Projector,
    val delta2Precision: Double = .5
) : Stream() {

    // context of execution of stream

    // a line can be projected in the context of a polygon or not
    enum class LineStartContext  { DEFAULT, POLYGON}
    enum class LineEndContext    { DEFAULT, POLYGON}

    //a point can be projected in the context of a polygon, a line, or nothing
    enum class PointContext      { DEFAULT, POLYGON, LINE }

    var pointContext         = PointContext.DEFAULT
    var lineStartContext     = LineStartContext.DEFAULT
    var lineEndContext       = LineEndContext.DEFAULT

    override fun polygonStart() {
        stream.polygonStart()
        lineStartContext = LineStartContext.POLYGON
    }

    override fun polygonEnd() {
        stream.polygonEnd()
        lineStartContext = LineStartContext.DEFAULT
    }

    override fun lineStart() {
        when (lineStartContext) {
            LineStartContext.POLYGON -> lineStartPolygon()
            LineStartContext.DEFAULT -> lineStartDefault()
        }
    }

    fun lineStartPolygon() {
        lineStartDefault()
        pointContext = PointContext.POLYGON
        lineEndContext = LineEndContext.POLYGON
    }

    fun lineStartDefault() {
        x0 = Double.NaN  //todo why set only x0
        pointContext = PointContext.LINE
        stream.lineStart()
    }

    override fun lineEnd() {
        when (lineEndContext) {
            LineEndContext.POLYGON -> lineEndPolygon()
            LineEndContext.DEFAULT -> lineEndDefault()
        }
    }

//    override fun point(x: Double, y: Double, z: Double) {
//        point(StreamPoint(x, y, z))
//    }
    override fun point(point: StreamPoint) {
        when (pointContext) {
            PointContext.POLYGON -> pointPolygon(point.x,point.y,point.z ?: .0)
            PointContext.LINE    -> pointLine(point.x, point.y, point.z ?: .0)
            PointContext.DEFAULT -> pointDefault(point.x,point.y,point.z ?: .0)
        }
    }


    fun lineEndDefault() {
        pointContext = PointContext.DEFAULT
        stream.lineEnd()
    }

    fun lineEndPolygon() {
        resampleLineTo(x0, y0, lambda0, a0, b0, c0, x00, y00, lambda00, a00, b00, c00, MAX_DEPTH, stream)
        lineEndContext = LineEndContext.DEFAULT
        lineEnd()
    }

    /**
     * First point of a polygon, same as line but also store initial values.
     */
    fun pointPolygon(lambda: Double, phi: Double, alt: Double) {
        lambda00 = lambda
        pointLine(lambda, phi, alt)
        x00 = x0
        y00 = y0
        a00 = a0
        b00 = b0
        c00 = c0
        pointContext = PointContext.LINE
    }

    fun pointLine(lambda: Double, phi: Double, alt: Double) {
        val radiusAtLat = cos(phi)
        val dz = radiusAtLat * cos(lambda)
        val dx = radiusAtLat * sin(lambda)
        val dy = sin(phi)

        val projected = projector.project(lambda,phi)

        val p0 = projected[0]
        val p1 = projected[1]

        //todo check first call to resample (x0, y0, ... set to NaN)
        resampleLineTo(
            x0, y0, lambda0, a0, b0, c0,
            p0, p1, lambda, dz, dx, dy,
            MAX_DEPTH, stream
        )

        //set previous point with the new projected point
        x0 = p0
        y0 = p1
        lambda0 = lambda
        a0 = dz
        b0 = dx
        c0 = dy
        stream.point(StreamPoint(x0, y0, alt))
    }

    /**
     * A point outside of polygon or line context, just project and delegate to next stream after projection.
     */
    fun pointDefault(lambda: Double, phi: Double, alt: Double) {
        val projected = projector.project(lambda,phi)
        stream.point(StreamPoint(
            projected[0],
            projected[1],
            alt
        ))
    }

    // First point of polygon (used to generate the last line to close the polygon)
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


    /**
     * Recursively smooth the current line by creating intermediary points
     */
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

            val projected2 = projector.project(lambda2, phi2)
            val x2 = projected2[0]
            val y2 = projected2[1]

            val dx2 = x2 - x0
            val dy2 = y2 - y0
            val dz = dy * dx2 - dx * dy2
            if (dz * dz / d2 > delta2Precision // perpendicular projected distance
                || abs((dx * dx2 + dy * dy2) / d2 - 0.5) > 0.3 // midpoint close to an end
                || a0 * a1 + b0 * b1 + c0 * c1 < COS_MIN_DISTANCE
            ) { // angular distance
                a /= m
                b /= m

                //new intermediate point (x2, y2) recursively resample line before and after it.
                resampleLineTo(
                    x0, y0, lambda0, a0, b0, c0,
                    x2, y2, lambda2, a, b, c,
                    newDepth, stream)
                stream.point(StreamPoint(x2, y2, 0.0))
                resampleLineTo(
                    x2, y2, lambda2, a, b, c,
                    x1, y1, lambda1, a1, b1, c1,
                    newDepth, stream)
            }
        }
    }

}

/**
 * No resampling, just project points before passing to next stream.
 */
private fun resampleNone(projector: Projector): (Stream) -> Stream {
    return { stream: Stream ->
        object : DelegateStreamAdapter(stream) {
            override fun point(point: StreamPoint) {

                val projected = projector.project(point.x,point.y)

                stream.point(StreamPoint(
                    projected[0],
                    projected[1],
                    point.z
                ))
            }
        }
    }
}
