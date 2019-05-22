package io.data2viz.geo.geometry.clip

import io.data2viz.geo.stream.Stream
import io.data2viz.geo.geometry.*
import io.data2viz.geo.geojson.path.geoCircle
import io.data2viz.math.EPSILON
import io.data2viz.math.PI
import io.data2viz.math.toRadians
import kotlin.math.abs
import kotlin.math.cos
import kotlin.math.sqrt


//fun clipCircle(radius: Double) = { stream: Stream -> Clip(ClipCircle(radius), stream) }

class ClipCirclePreClip(val radius: Double): StreamPreClip {
    override fun preClip(stream: Stream): Stream {
        return Clip(ClipCircle(radius), stream)
    }

}

/**
 * Generates a clipping function which transforms a stream such that geometries are bounded by a small circle of
 * radius angle around the projection’s center.
 * Typically used for pre-clipping.
 */
class ClipCircle(val radius: Double) : ClippableHasStart {

    private val cr = cos(radius)
    private val delta = 6.0.toRadians()
    private val smallRadius = cr > 0
    private val notHemisphere = abs(cr) > EPSILON // TODO optimise for this common case

    override val start: DoubleArray
        get() = if (smallRadius) doubleArrayOf(0.0, -radius) else doubleArrayOf(-PI, radius - PI)

    override fun pointVisible(x: Double, y: Double): Boolean {
        return cos(x) * cos(y) > cr
    }

    override fun clipLine(stream: Stream): ClipStream {

        return object : ClipStream {

            private var _clean = 0
            private var point0: DoubleArray? = null             // previous point
            private var c0 = 0                                  // code for previous point
            private var v0 = false                              // visibility of previous point
            private var v00 = false                             // visibility of first point

            override var clean: Int = 0
                get() = _clean or ((if (v00 && v0) 1 else 0) shl 1)

            override fun point(x: Double, y: Double, z: Double) {
                val point1 = doubleArrayOf(x, y)
                var point2: DoubleArray?
                var v = pointVisible(x, y)
                val c = if (smallRadius) {
                    if (v) 0 else code(x, y)
                } else {
                    if (v) code(x + (if (x < 0) PI else -PI), y) else 0
                }
                if (point0 == null) {
                    v00 = v
                    v0 = v
                    if (v) stream.lineStart()
                }

                // Handle degeneracies.
                // TODO ignore if not clipping polygons.
                if (v != v0) {
                    point2 = intersect(point0!!, point1)
                    if (point2 == null || pointEqual(point0!!, point2) || pointEqual(point1, point2)) {
                        point1[0] += EPSILON
                        point1[1] += EPSILON
                        v = pointVisible(point1[0], point1[1])
                    }
                }

                if (v != v0) {
                    _clean = 0
                    if (v) {
                        // outside going in
                        stream.lineStart()
                        point2 = intersect(point1, point0!!)
                        stream.point(point2!![0], point2[1], .0)            // TODO : point2 may be null ??
                    } else {
                        // inside going out
                        point2 = intersect(point0!!, point1)
                        stream.point(point2!![0], point2[1], .0)            // TODO : point2 may be null ??
                        stream.lineEnd()
                    }
                    point0 = point2
                } else if (notHemisphere && point0 != null && smallRadius xor v) {

                    // If the codes for two points are different, or are both zero,
                    // and there this segment intersects with the small circle.
                    if ((c and c0) == 0) {
                        val t = intersects(point1, point0!!)
                        if (t != null) {
                            _clean = 0
                            if (smallRadius) {
                                stream.lineStart()
                                stream.point(t[0][0], t[0][1], .0)
                                stream.point(t[1][0], t[1][1], .0)
                                stream.lineEnd()
                            } else {
                                stream.point(t[1][0], t[1][1], .0)
                                stream.lineEnd()
                                stream.lineStart()
                                stream.point(t[0][0], t[0][1], .0)
                            }
                        }
                    }
                }

                if (v && (point0 == null || !pointEqual(point0!!, point1))) {
                    stream.point(point1[0], point1[1], .0)
                }
                point0 = point1
                v0 = v
                c0 = c
            }

            override fun lineStart() {
                v00 = false
                v0 = false
                _clean = 1
            }

            override fun lineEnd() {
                if (v0) stream.lineEnd()
                point0 = null
            }
        }
    }

    override fun interpolate(from: DoubleArray?, to: DoubleArray?, direction: Int, stream: Stream) {
        geoCircle(stream, radius, delta, direction, from, to)
    }

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

        val c1 = cr * n2n2 / determinant
        val c2 = -cr * n1n2 / determinant
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

        val c1 = cr * n2n2 / determinant
        val c2 = -cr * n1n2 / determinant
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
    fun code(x: Double, y: Double): Int {
        val r = if (smallRadius) radius else PI - radius
        var code = 0
        if (x < -r) code = code or 1               // left
        else if (y > r) code = code or 2           // right
        if (y < -r) code = code or 4               // below
        else if (y > r) code = code or 8           // above
        return code
    }
}