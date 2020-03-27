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
    private val clipRectangle = RectangleClipper(Extent(x0, y0, x1, y1))

    override fun bindTo(downstream: Stream<Point3D>): Stream<Point3D> =
        clipRectangle.clipLine(downstream)
}

private val NO_POINT = Point3D()

/**
 * Generates a clipping function which transforms a stream such that geometries are bounded by the given Extent.
 * Typically used for post-clipping.
 */
class RectangleClipper(val extent: Extent) : Clipper<Point3D> {

    override fun isPointVisible(point: Point3D): Boolean =
        point.x in extent.x0..extent.x1 &&
        point.y in extent.y0..extent.y1

    enum class PointContext {DEFAULT, LINE}

    override fun clipLine(downstream: Stream<Point3D>): ClipStream<Point3D> = object : ClipStream<Point3D>() {

        override var clean: Int = 0

        private var activeStream = downstream
        private val bufferStream = BufferStream<Point3D>()

        // first point
		private var firstPoint = NO_POINT
//        private var firstX = Double.NaN
//        private var firstY = Double.NaN
        private var firstVisible = false

        // second point
		private var previousPoint = NO_POINT
//        private var previousX = Double.NaN
//        private var previousY = Double.NaN
        private var previousIsVisible = false

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
            previousIsVisible = false
			previousPoint = NO_POINT
//            previousX = Double.NaN
//            previousY = Double.NaN
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

			var newPoint = point

            val isvisible: Boolean = point in extent

            if (polygon != null)
                ring?.add(point)
            if (first) {
				firstPoint = point
                firstVisible = isvisible
                first = false
                if (isvisible) {
                    activeStream.lineStart()
                    activeStream.point(point)
                }
            } else {
                if (isvisible && previousIsVisible)
					activeStream.point(point)
                else {
                    previousPoint = previousPoint.coerce()
					newPoint = newPoint.coerce()
					val clipResult = clipLine(previousPoint, newPoint, extent)
                    if (clipResult != null) {
                        if (!previousIsVisible) {
                            activeStream.lineStart()
							activeStream.point(clipResult.a)
						}
						activeStream.point(clipResult.b)
                        if (!isvisible) activeStream.lineEnd()
                        clean = 0
                    } else if (isvisible) {
                        activeStream.lineStart()
                        activeStream.point(newPoint)
                        clean = 0
                    }
                }
            }
			previousPoint = newPoint
            previousIsVisible = isvisible
        }

		/**
		 * Most of the time returns the current point..
		 * TODO Is this verification necessary? In what cases x and y could be above CLIPMIN CLIPMAX
		 */
		private fun Point3D.coerce(): Point3D =
			if (x in CLIPMIN..CLIPMAX && y in CLIPMIN..CLIPMAX)
				this
			else
				copy(
					x = x.coerceIn(CLIPMIN, CLIPMAX),
					y = y.coerceIn(CLIPMIN, CLIPMAX)
				)

        override fun lineEnd() {
            if (segments != null) {
                linePoint(firstPoint)
                if (firstVisible && previousIsVisible) bufferStream.rejoin()
                segments!!.add(bufferStream.result().flatten())
            }
            pointContext = PointContext.DEFAULT
            if (previousIsVisible) activeStream.lineEnd()
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

            var j: Int
            var m: Int

			var a: Point3D
			var b: Point3D

            val poly = polygon ?: throw IllegalStateException()
            for (ring in poly) {
                j = 1
                m = ring.size
				b = ring[0]
                while (j < m) {
					a = b
                    b = ring[j]

                    if (a.y <= extent.y1) {
                        if (b.y >  extent.y1 && ((b.x - a.x) * (extent.y1 - a.y)) > ((b.y - a.y) * (extent.x0 - a.x)))
							++winding
                    } else {
                        if (b.y <= extent.y1 && ((b.x - a.x) * (extent.y1 - a.y)) < ((b.y - a.y) * (extent.x0 - a.x)))
							--winding
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

        var fromCorner  = if (from == null) 0 else corner(from, direction)
        val toCorner 	= if (from == null) 0 else corner(to, direction)

        if (from == null || fromCorner != toCorner || to != null && (comparePoint(from, to) < 0) xor (direction > 0)) {
            do {
                stream.point(Point3D(
                    if (fromCorner == 0 || fromCorner == 3) extent.x0 else extent.x1,
                    if (fromCorner > 1) extent.y1 else extent.y0
                ))
                fromCorner = (fromCorner + direction + 4) % 4
            } while (fromCorner != toCorner)
        } else if (to != null)
            stream.point(to)
    }

    private fun corner(point: Point3D?, direction: Int): Int =
		if (point == null)
			if (direction > 0) 3 else 2
		else if (abs(point.x - extent.x0) < EPSILON) if (direction > 0) 0 else 3
		else if (abs(point.x - extent.x1) < EPSILON) if (direction > 0) 2 else 1
		else if (abs(point.y - extent.y0) < EPSILON) if (direction > 0) 1 else 0
		else if (direction>0) 											3 else 2

    private fun comparePoint(a: Point3D, b: Point3D): Int {
        val ca = corner(a, 1)
        val cb = corner(b, 1)

        return when {
            ca != cb -> ca.compareTo(cb)
            ca == 0  -> b.y.compareTo(a.y)
            ca == 1  -> a.x.compareTo(b.x)
            ca == 2  -> a.y.compareTo(b.y)
            else 	 -> b.x.compareTo(a.x)
        }
    }

}

internal data class ClipLineResult(val a: Point3D, val b: Point3D)

internal fun clipLine(
	start: Point3D,
	stop: Point3D,
	extent: Extent): ClipLineResult? {

	var t0 = .0
	var t1 = 1.0

	val dx = stop.x - start.x
	val dy = stop.y - start.y

	var r = extent.x0 - start.x

	val vertical = dx == .0

	//vertical && left to extent => no clipping
	if (vertical && r > 0) return null

	r /= dx
	if (dx < 0) {
		if (r < t0) return null
		if (r < t1) t1 = r
	} else if (dx > 0) {
		if (r > t1) return null
		if (r > t0) t0 = r
	}

	r = extent.x1 - start.x
	if (vertical && r < 0) return null

	r /= dx
	if (dx < 0) {
		if (r > t1) return null
		if (r > t0) t0 = r
	} else if (dx > 0) {
		if (r < t0) return null
		if (r < t1) t1 = r
	}

	r = extent.y0 - start.y
	val horizontal = dy == .0
	if (horizontal && r > 0) return null

	r /= dy
	if (dy < 0) {
		if (r < t0) return null
		if (r < t1) t1 = r
	} else if (dy > 0) {
		if (r > t1) return null
		if (r > t0) t0 = r
	}

	r = extent.y1 - start.y
	if (horizontal && r < 0) return null

	r /= dy
	if (dy < 0) {
		if (r > t1) return null
		if (r > t0) t0 = r
	} else if (dy > 0) {
		if (r < t0) return null
		if (r < t1) t1 = r
	}

	return ClipLineResult(
		if (t0 > 0) Point3D(start.x + t0 * dx, start.y + t0 * dy) else start,
		if (t1 < 1) Point3D(start.x + t1 * dx, start.y + t1 * dy) else stop
	)
}


operator fun Extent.contains(point: Point3D) =
	point.x in this.x0.. this.x1 &&
	point.y in this.y0.. this.y1
