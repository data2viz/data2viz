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

import io.data2viz.geo.Point3D
import io.data2viz.geo.stream.Stream
import io.data2viz.geom.Extent
import io.data2viz.math.EPSILON
import kotlin.math.abs

private const val CLIPMAX = 1e9
private const val CLIPMIN = -CLIPMAX

class RectangleClip(x0: Double, y0: Double, x1: Double, y1: Double) : ClipStreamBuilder<Point3D> {
    val clipRectangle = RectangleClipper(Extent(x0, y0, x1, y1))

    override fun bindTo(downstream: Stream<Point3D>): Stream<Point3D> {
        return clipRectangle.clipLine(downstream)
    }
}

/**
 * Generates a clipping function which transforms a stream such that geometries are bounded by the given Extent.
 * Typically used for post-clipping.
 */
class RectangleClipper(val extent: Extent) : Clipper<Point3D> {
    // TODO refactor function references :: to objects like in CircleClip
//  Function references have poor performance due to GC & memory allocation

    override fun isPointVisible(point: Point3D): Boolean =
        point.x in extent.x0..extent.x1 &&
        point.y in extent.y0..extent.y1

    enum class PointContext {DEFAULT, LINE}

    override fun clipLine(downstream: Stream<Point3D>): ClipStream<Point3D> = object : ClipStream<Point3D>() {

        override var clean: Int = 0

        private var activeStream = downstream
        private val bufferStream = BufferStream<Point3D>()

        // first point
        private var x__ = Double.NaN
        private var y__ = Double.NaN
        private var v__ = false

        // second point
        private var x_ = Double.NaN
        private var y_ = Double.NaN
        private var v_ = false

        private var segments: MutableList<List<Point3D>>? = null
        private var ring: MutableList<Point3D>? = null
        private var polygon: MutableList<List<Point3D>>? = null
        private var first = false

        private var pointContext = PointContext.DEFAULT


        override fun polygonStart() {
            activeStream = bufferStream
            segments = ArrayList()
            polygon = ArrayList()
            clean = 1
        }

        override fun lineStart() {
            pointContext = PointContext.LINE

            val poly = polygon
            if (poly != null) {
                val r = ArrayList<Point3D>()
                ring = r
                poly.add(r)
            }

            first = true
            v_ = false
            x_ = Double.NaN
            y_ = Double.NaN
        }

        override fun point(point: Point3D) {
            when(pointContext) {
                PointContext.DEFAULT -> pointDefault(point)
                PointContext.LINE -> linePoint(point)
            }
        }

        private fun pointDefault(point: Point3D) {
            if (isPointVisible(point)) {
                activeStream.point(point)
            }
        }

        private fun linePoint(point: Point3D) {
            var newX = point.x
            var newY = point.y

            val visible = isPointVisible(point)
            if (polygon != null) ring?.add(Point3D(newX, newY))
            if (first) {
                x__ = newX
                y__ = newY
                v__ = visible
                first = false
                if (visible) {
                    activeStream.lineStart()
                    activeStream.point(Point3D(newX, newY, 0.0))
                }
            } else {
                if (visible && v_) activeStream.point(Point3D(newX, newY, 0.0))
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
                            activeStream.point(Point3D(a[0], a[1], 0.0))
                        }
                        activeStream.point(Point3D(b[0], b[1], 0.0))
                        if (!visible) activeStream.lineEnd()
                        clean = 0
                    } else if (visible) {
                        activeStream.lineStart()
                        activeStream.point(Point3D(newX, newY, 0.0))
                        clean = 0
                    }
                }
            }

            x_ = newX
            y_ = newY
            v_ = visible
        }

        override fun lineEnd() {
            if (segments != null) {
                linePoint(Point3D(x__, y__))
                if (v__ && v_) bufferStream.rejoin()
                segments!!.add(bufferStream.result().flatten())
            }
            pointContext = PointContext.DEFAULT
            if (v_) activeStream.lineEnd()
        }

        override fun polygonEnd() {
            val startInside = polygonInside() != 0
            val cleanInside = clean != 0 && startInside
            val visible = segments?.isNotEmpty() ?: false

            if (cleanInside || visible) {
                downstream.polygonStart()
                if (cleanInside) {
                    downstream.lineStart()
                    interpolate(null, null, 1, downstream)
                    downstream.lineEnd()
                }
                if (visible) rejoin(
                    segments!!,
                    Comparator { o1: Intersection<Point3D>, o2 -> comparePoint(o1.point, o2.point) },
                    startInside,
                    this@RectangleClipper,
                    downstream
                )
                downstream.polygonEnd()
            }
            activeStream = downstream
            segments = null
            polygon = null
            ring = null
        }



        // TODO may have issues. Need rework
        private fun polygonInside(): Int {
            var winding = 0

            var ring: List<Point3D>
            var j: Int
            var m: Int
            var point: Point3D
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
                b0 = point.x
                b1 = point.y
                while (j < m) {
                    a0 = b0
                    a1 = b1
                    point = ring[j]
                    b0 = point.x
                    b1 = point.y

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

    override fun interpolate(
        from: Point3D?,
        to: Point3D?,
        direction: Int,
        stream: Stream<Point3D>) {

        var a = if (from == null) 0 else corner(from, direction)
        val a1 = if (from == null) 0 else corner(to, direction)

        if (from == null || a != a1 || to != null && (comparePoint(from, to) < 0) xor (direction > 0)) {
            do {
                stream.point(Point3D(
                    if (a == 0 || a == 3) extent.x0 else extent.x1,
                    if (a > 1) extent.y1 else extent.y0,
                    0.0
                ))
                a = (a + direction + 4) % 4
            } while (a != a1)
        } else if (to != null)
            stream.point(to)
    }

    /**
     *
     */
    private fun corner(p: Point3D?, direction: Int): Int = if (p == null) if (direction > 0) 3 else 2
        else when {
            direction > 0 -> when {
                abs(p.x - extent.x0) < EPSILON -> 0
                abs(p.x - extent.x1) < EPSILON -> 2
                abs(p.y - extent.y1) < EPSILON -> 1
                else -> 3
            }
            else -> when {
                abs(p.x - extent.x0) < EPSILON -> 0
                abs(p.x - extent.x1) < EPSILON -> 2
                abs(p.y - extent.y1) < EPSILON -> 1
                else -> 3
            }
    }

    private fun comparePoint(a: Point3D, b: Point3D): Int {
        val ca = corner(a, 1)
        val cb = corner(b, 1)

        return when {
            ca != cb -> ca.compareTo(cb)
            ca == 0 -> b.y.compareTo(a.y)
            ca == 1 -> a.x.compareTo(b.x)
            ca == 2 -> a.y.compareTo(b.y)
            else -> b.x.compareTo(a.x)
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