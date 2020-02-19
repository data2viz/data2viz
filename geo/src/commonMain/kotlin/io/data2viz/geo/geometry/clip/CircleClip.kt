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

package io.data2viz.geo.geometry.clip

import io.data2viz.geo.GeoPoint
import io.data2viz.geo.stream.Stream
import io.data2viz.geo.geometry.*
import io.data2viz.geo.geojson.path.geoCircle
import io.data2viz.math.*
import kotlin.math.abs
import kotlin.math.sqrt


/**
 * Clip geometries using a Geo Circle.
 * @param radius radius in radians
 */
class CirclePreClip(val radius: Double) : ClipStreamBuilder<GeoPoint> {
    override fun bindTo(downstream: Stream<GeoPoint>): Stream<GeoPoint> =
        ClippableStream(CircleClipper(radius.rad), downstream)
}

/**
 * Generates a clipping function which transforms a stream such that geometries are bounded by a small circle of
 * radius angle around the projection’s center.
 * Typically used for pre-clipping.
 */
class CircleClipper(val radius: Angle) : ClipperWithStart<GeoPoint> {

    private val cosRadius = radius.cos
    private val delta = 6.0.toRadians()
    private val smallRadius = cosRadius > 0
    private val notHemisphere = abs(cosRadius) > EPSILON // TODO optimise for this common case

    override val start: GeoPoint
        get() = if (smallRadius)
            GeoPoint(0.0.rad, -radius)
        else
            GeoPoint(-PI.rad, radius - ANGLE_PI)

    override fun isPointVisible(point: GeoPoint): Boolean = point.lon.cos * point.lat.cos > cosRadius

    override fun clipLine(downstream: Stream<GeoPoint>): ClipStream<GeoPoint> {

        return object : ClipStream<GeoPoint>() {

            private var _clean = 0
            private var point0: GeoPoint? = null             // previous point
            private var c0 = 0                                  // code for previous point
            private var visible0 = false                              // visibility of previous point
            private var visible00 = false                             // visibility of first point

            override var clean: Int = 0
                get() = _clean or ((if (visible00 && visible0) 1 else 0) shl 1)

            override fun point(point: GeoPoint) {
                var point1 = point
                var point2: GeoPoint?
                var visible = isPointVisible(point)
                val c = if (smallRadius) {
                    if (visible) 0 else code(point.lon.rad, point.lat.rad)
                } else {
                    if (visible) code(point.lon.rad + (if (point.lon.rad < 0) PI else -PI), point.lat.rad) else 0
                }
                if (point0 == null) {
                    visible00 = visible
                    visible0 = visible
                    if (visible) downstream.lineStart()
                }

                // Handle degeneracies.
                // TODO ignore if not clipping polygons.
                if (visible != visible0) {
                    point2 = intersect(point0!!, point1)
                    if (point2 == null || pointEqual(point0!!, point2) || pointEqual(point1, point2)) {
                        point1 += GeoPoint(ANGLE_EPSILON, ANGLE_EPSILON)
                        visible = isPointVisible(point1)
                    }
                }

                if (visible != visible0) {
                    _clean = 0
                    if (visible) {
                        // outside going in
                        downstream.lineStart()
                        point2 = intersect(point1, point0!!)
                        downstream.point(point2!!)            // TODO : point2 may be null ??
                    } else {
                        // inside going out
                        point2 = intersect(point0!!, point1)
                        downstream.point(point2!!)            // TODO : point2 may be null ??
                        downstream.lineEnd()
                    }
                    point0 = point2
                } else if (notHemisphere && point0 != null && smallRadius xor visible) {

                    // If the codes for two points are different, or are both zero,
                    // and there this segment intersects with the small circle.
                    if ((c and c0) == 0) {
                        val t = intersects(point1, point0!!)
                        if (t != null) {
                            _clean = 0
                            if (smallRadius) {
                                downstream.lineStart()
                                downstream.point(GeoPoint(t[0].lon, t[0].lat, .0))
                                downstream.point(GeoPoint(t[1].lon, t[1].lat, .0))
                                downstream.lineEnd()
                            } else {
                                downstream.point(GeoPoint(t[1].lon, t[1].lat, .0))
                                downstream.lineEnd()
                                downstream.lineStart()
                                downstream.point(GeoPoint(t[0].lon, t[0].lat, .0))
                            }
                        }
                    }
                }

                if (visible && (point0 == null || !pointEqual(point0!!, point1))) {
                    downstream.point(GeoPoint(point1.lon, point1.lat, .0))
                }
                point0 = point1
                visible0 = visible
                c0 = c
            }

            override fun lineStart() {
                visible00 = false
                visible0 = false
                _clean = 1
            }

            override fun lineEnd() {
                if (visible0) downstream.lineEnd()
                point0 = null
            }
        }
    }

    override fun interpolate(from: GeoPoint?, to: GeoPoint?, direction: Int, stream: Stream<GeoPoint>) {
        geoCircle(stream, radius.rad, delta, direction, from?.toArray(), to?.toArray())
    }

    private fun intersect(a: GeoPoint, b: GeoPoint) =
        intersect(a.toArray(), b.toArray()).toGeoJsonPoint()

    private fun GeoPoint.toArray() = doubleArrayOf(lon.rad, lat.rad )

    // Intersects the great circle between a and b with the postClip circle.
    private fun intersect(a: DoubleArray, b: DoubleArray): DoubleArray? {
        val pa = cartesian(a)
        val pb = cartesian(b)

        // We have two planes, n1.p = d1 and n2.p = d2.
        // Find intersection line p(t) = c1 n1 + c2 n2 + t (n1 ⨯ n2).
        val n1 = doubleArrayOf(1.0, .0, .0)                 // normal
        val n2 = cartesianCross(pa, pb)
        val n2n2 = cartesianDot(n2, n2)
        val n1n2 = n2[0]                                        // cartesianDot(n1, n2)
        val determinant = n2n2 - n1n2 * n1n2

        //if (!determinant) return !two && a;
        // Two polar points.
        if (determinant == .0) return a

        val c1 = cosRadius * n2n2 / determinant
        val c2 = -cosRadius * n1n2 / determinant
        val n1xn2 = cartesianCross(n1, n2)
        var A = cartesianScale(n1, c1)
        val B = cartesianScale(n2, c2)
        A = cartesianAdd(A, B)

        // Solve |p(t)|^2 = 1
        val u = n1xn2
        val w = cartesianDot(A, u)
        val uu = cartesianDot(u, u)
        val t2 = w * w - uu * (cartesianDot(A, A) - 1)

        if (t2 < 0) return null

        val t = sqrt(t2)
        var q = cartesianScale(u, (-w - t) / uu)
        q = cartesianAdd(q, A)
        q = spherical(q)
        return q
    }

    fun DoubleArray?.toGeoJsonPoint() = if (this == null) null else GeoPoint(this[0].rad, this[1].rad)

    private fun intersects(a: GeoPoint, b: GeoPoint): Array<GeoPoint>? =
        intersects(a.toArray(), b.toArray())?.let { Array(it.size) { i: Int -> it[i].toGeoJsonPoint()!! } }

    // TODO : factorize with intersect !
    private fun intersects(a: DoubleArray, b: DoubleArray): Array<DoubleArray>? {
        val pa = cartesian(a)
        val pb = cartesian(b)

        // We have two planes, n1.p = d1 and n2.p = d2.
        // Find intersection line p(t) = c1 n1 + c2 n2 + t (n1 ⨯ n2).
        val n1 = doubleArrayOf(1.0, .0, .0)                 // normal
        val n2 = cartesianCross(pa, pb)
        val n2n2 = cartesianDot(n2, n2)
        val n1n2 = n2[0]                                        // cartesianDot(n1, n2)
        val determinant = n2n2 - n1n2 * n1n2

        // Two polar points.
        if (determinant == .0) return null

        val c1 = cosRadius * n2n2 / determinant
        val c2 = -cosRadius * n1n2 / determinant
        val n1xn2 = cartesianCross(n1, n2)
        var A = cartesianScale(n1, c1)
        val B = cartesianScale(n2, c2)
        A = cartesianAdd(A, B)

        // Solve |p(t)|^2 = 1
        val u = n1xn2
        val w = cartesianDot(A, u)
        val uu = cartesianDot(u, u)
        val t2 = w * w - uu * (cartesianDot(A, A) - 1)

        if (t2 < 0) return null

        val t = sqrt(t2)
        var q = cartesianScale(u, (-w - t) / uu)
        q = cartesianAdd(q, A)
        q = spherical(q)

        // Two intersection points.
        var lambda0 = a[0]
        var lambda1 = b[0]
        var phi0 = a[1]
        var phi1 = b[1]

        if (lambda1 < lambda0) {
            val z = lambda0
            lambda0 = lambda1
            lambda1 = z
        }

        val delta = lambda1 - lambda0
        val polar = abs(delta - PI) < EPSILON
        val meridian = polar || delta < EPSILON

        if (!polar && phi1 < phi0) {
            val z = phi0
            phi0 = phi1
            phi1 = z
        }

        // Check that the first point is between a and b.
        val test = if (meridian) {
            if (polar) {
                (phi0 + phi1 > 0) xor (q[1] < if (abs(q[0] - lambda0) < EPSILON) phi0 else phi1)
            } else (q[1] in phi0..phi1)
        } else {
            (delta > PI) xor (q[0] in lambda0..lambda1)
        }

        if (test) {
            var q1 = cartesianScale(u, (-w + t) / uu)
            q1 = cartesianAdd(q1, A)
            return arrayOf(q, spherical(q1))
        }
        return null
    }

    // Generates a 4-bit vector representing the location of a point relative to
    // the small circle's bounding box.
    private fun code(lambda: Double, phi: Double): Int {
        val r = if (smallRadius)
            radius.rad else PI - radius.rad
        var code = 0
        if (lambda < -r) code = code or 1               // left
        else if (phi > r) code = code or 2           // right
        if (phi < -r) code = code or 4               // below
        else if (phi > r) code = code or 8           // above
        return code
    }
}