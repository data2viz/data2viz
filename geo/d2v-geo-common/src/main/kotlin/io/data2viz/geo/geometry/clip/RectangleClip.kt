package io.data2viz.geo.geometry.clip

import io.data2viz.geo.stream.Stream
import io.data2viz.geom.Extent
import io.data2viz.math.EPSILON
import kotlin.math.abs

private const val CLIPMAX = 1e9
private const val CLIPMIN = -CLIPMAX

class RectanglePostClip(x0: Double, y0: Double, x1: Double, y1: Double) : StreamPostClip {
    val clipRectangle = ClipRectangle(Extent(x0, y0, x1, y1))
    override fun postClip(stream: Stream): Stream {
        return clipRectangle.clipLine(stream)
    }

}


// TODO refactor function references :: to objects like in CircleClip
//  Function references have poor performance due to GC & memory allocation
/**
 * Generates a clipping function which transforms a stream such that geometries are bounded by The given Extent.
 * Typically used for post-clipping.
 */
class ClipRectangle(val extent: Extent) : Clippable {

    override fun pointVisible(x: Double, y: Double): Boolean {
        return x in extent.x0..extent.x1 && y in extent.y0..extent.y1
    }

    val interpolateFunction = object : InterpolateFunction {
        override fun invoke(from: DoubleArray, to: DoubleArray, direction: Int, stream: Stream) {
            interpolate(from, to, direction, stream)
        }

    }

    override fun clipLine(stream: Stream): ClipStream {

        return object : ClipStream {

            override var clean: Int = 0

            private var activeStream = stream
            private val bufferStream = ClipBufferStream()

            // first point
            private var x__ = Double.NaN
            private var y__ = Double.NaN
            private var v__ = false

            // second point
            private var x_ = Double.NaN
            private var y_ = Double.NaN
            private var v_ = false

            private var segments: MutableList<List<DoubleArray>>? = null
            private var ring: ArrayList<DoubleArray>? = null
            private var polygon: ArrayList<List<DoubleArray>>? = null
            private var first = false

            private var currentPoint = ::justPoint

            override fun point(x: Double, y: Double, z: Double) {
                currentPoint(x, y)
            }

            override fun lineStart() {
                currentPoint = ::linePoint

                val poly = polygon
                if (poly != null) {
                    val r = ArrayList<DoubleArray>()
                    ring = r
                    poly.add(r)
                }

                first = true
                v_ = false
                x_ = Double.NaN
                y_ = Double.NaN
            }

            override fun lineEnd() {
                if (segments != null) {
                    linePoint(x__, y__)
                    if (v__ && v_) bufferStream.rejoin()
                    segments!!.add(bufferStream.result().flatten())
                }
                currentPoint = ::justPoint
                if (v_) activeStream.lineEnd()
            }

            override fun polygonStart() {
                activeStream = bufferStream
                segments = ArrayList()
                polygon = ArrayList()
                clean = 1
            }

            override fun polygonEnd() {
                val startInside = polygonInside() != 0
                val cleanInside = clean != 0 && startInside
                val visible = segments?.isNotEmpty() ?: false

                if (cleanInside || visible) {
                    stream.polygonStart()
                    if (cleanInside) {
                        stream.lineStart()
                        interpolate(null, null, 1, stream)
                        stream.lineEnd()
                    }
                    if (visible) rejoin(
                        segments!!,
                        Comparator { o1: Intersection, o2 -> comparePoint(o1.point, o2.point) },
                        startInside,
                        interpolateFunction,
                        stream
                    )
                    stream.polygonEnd()
                }
                activeStream = stream
                segments = null
                polygon = null
                ring = null
            }

            private fun justPoint(x: Double, y: Double) {
                if (pointVisible(x, y)) {
                    activeStream.point(x, y, 0.0)
                }
            }

            private fun linePoint(x: Double, y: Double) {
                var newX = x
                var newY = y

                val v = pointVisible(newX, newY)
                if (polygon != null) ring?.add(doubleArrayOf(newX, newY))
                if (first) {
                    x__ = newX
                    y__ = newY
                    v__ = v
                    first = false
                    if (v) {
                        activeStream.lineStart()
                        activeStream.point(newX, newY, 0.0)
                    }
                } else {
                    if (v && v_) activeStream.point(newX, newY, 0.0)
                    else {
                        x_ = x_.coerceIn(CLIPMIN, CLIPMAX)
                        y_ = y_.coerceIn(CLIPMIN, CLIPMAX)
                        newX = newX.coerceIn(CLIPMIN, CLIPMAX)
                        newY = newY.coerceIn(CLIPMIN, CLIPMAX)
                        val a = doubleArrayOf(x_, y_)
                        val b = doubleArrayOf(newX, newY)

                        if (clipLine(a, b, extent)) {
                            if (!v_) {
                                activeStream.lineStart()
                                activeStream.point(a[0], a[1], 0.0)
                            }
                            activeStream.point(b[0], b[1], 0.0)
                            if (!v) activeStream.lineEnd()
                            clean = 0
                        } else if (v) {
                            activeStream.lineStart()
                            activeStream.point(newX, newY, 0.0)
                            clean = 0
                        }
                    }
                }

                x_ = newX
                y_ = newY
                v_ = v
            }

            // TODO may have issues. Need rework
            private fun polygonInside(): Int {
                var winding = 0

                var ring: List<DoubleArray>
                var j: Int
                var m: Int
                var point: DoubleArray
                var a0: Double
                var a1: Double
                var b0: Double
                var b1: Double

                val poly = polygon ?: throw IllegalStateException()
                for (i in poly.indices) {
                    ring = poly[i]
                    j = 1
                    m = ring.size
                    point = ring[0]
                    b0 = point[0]
                    b1 = point[1]
                    while (j < m) {
                        a0 = b0
                        a1 = b1
                        point = ring[j]
                        b0 = point[0]
                        b1 = point[1]

                        if (a1 <= extent.y1) {
                            if (b1 > extent.y1 && ((b0 - a0) * (extent.y1 - a1)) > ((b1 - a1) * (extent.x0 - a0))) ++winding
                        } else {
                            if (b1 <= extent.y1 && ((b0 - a0) * (extent.y1 - a1)) < ((b1 - a1) * (extent.x0 - a0))) --winding
                        }
                        ++j
                    }
                }

                return winding
            }
        }
    }

    override fun interpolate(from: DoubleArray?, to: DoubleArray?, direction: Int, stream: Stream) {
        var a = if (from == null) 0 else corner(from, direction)
        val a1 = if (from == null) 0 else corner(to, direction)

        if (from == null || a != a1 || to != null && (comparePoint(from, to) < 0) xor (direction > 0)) {
            do {
                stream.point(
                    if (a == 0 || a == 3) extent.x0 else extent.x1,
                    if (a > 1) extent.y1 else extent.y0,
                    0.0
                )
                a = (a + direction + 4) % 4
            } while (a != a1)
        } else if (to != null) stream.point(to[0], to[1], 0.0)
    }

    private fun corner(p: DoubleArray?, direction: Int): Int {
        if (p == null) return if (direction > 0) 3 else 2
        return when {
            direction > 0 -> when {
                abs(p[0] - extent.x0) < EPSILON -> 0
                abs(p[0] - extent.x1) < EPSILON -> 2
                abs(p[1] - extent.y1) < EPSILON -> 1
                else -> 3
            }
            else -> when {
                abs(p[0] - extent.x0) < EPSILON -> 0
                abs(p[0] - extent.x1) < EPSILON -> 2
                abs(p[1] - extent.y1) < EPSILON -> 1
                else -> 3
            }
        }
    }

    private fun comparePoint(a: DoubleArray, b: DoubleArray): Int {
        val ca = corner(a, 1)
        val cb = corner(b, 1)

        return when {
            ca != cb -> ca.compareTo(cb)
            ca == 0 -> b[1].compareTo(a[1])
            ca == 1 -> a[0].compareTo(b[0])
            ca == 2 -> a[1].compareTo(b[1])
            else -> b[0].compareTo(a[0])
        }
    }

    private fun clipLine(a: DoubleArray, b: DoubleArray, extent: Extent): Boolean {
        val ax = a[0]
        val ay = a[1]
        val bx = b[0]
        val by = b[1]
        var t0 = .0
        var t1 = 1.0
        val dx = bx - ax
        val dy = by - ay

        var r = extent.x0 - ax
        if (dx == .0 && r > 0) return false

        r /= dx
        if (dx < 0) {
            if (r < t0) return false
            if (r < t1) t1 = r
        } else if (dx > 0) {
            if (r > t1) return false
            if (r > t0) t0 = r
        }

        r = extent.x1 - ax
        if (dx == .0 && r < 0) return false

        r /= dx
        if (dx < 0) {
            if (r > t1) return false
            if (r > t0) t0 = r
        } else if (dx > 0) {
            if (r < t0) return false
            if (r < t1) t1 = r
        }

        r = extent.y0 - ay
        if (dy == .0 && r > 0) return false

        r /= dy
        if (dy < 0) {
            if (r < t0) return false
            if (r < t1) t1 = r
        } else if (dy > 0) {
            if (r > t1) return false
            if (r > t0) t0 = r
        }

        r = extent.y1 - ay
        if (dy == .0 && r < 0) return false

        r /= dy
        if (dy < 0) {
            if (r > t1) return false
            if (r > t0) t0 = r
        } else if (dy > 0) {
            if (r < t0) return false
            if (r < t1) t1 = r
        }

        if (t0 > 0) {
            a[0] = ax + t0 * dx
            a[1] = ay + t0 * dy
        }
        if (t1 < 1) {
            b[0] = ax + t1 * dx
            b[1] = ay + t1 * dy
        }

        return true
    }

}