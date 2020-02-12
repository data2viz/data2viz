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
import io.data2viz.geo.Point3D
import io.data2viz.geo.geometry.polygonContains
import io.data2viz.geo.stream.Stream
import io.data2viz.math.EPSILON
import io.data2viz.math.HALFPI

/**
 * Default clipping. Install no Clip Stream and just returns current
 * output Stream.
 */
val NoClipGeoJsonPoint = object : ClipStreamBuilder<GeoPoint> {
    override fun bindTo(downstream: Stream<GeoPoint>) = downstream
}

val NoClipPoint3D = object : ClipStreamBuilder<Point3D> {
    override fun bindTo(downstream: Stream<Point3D>) = downstream
}

/**
 * Installs a ClipStream into the chain of Stream.
 */
interface ClipStreamBuilder<T> {

    /**
     * Adds a ClipStream in front of the [downstream]
     */
    fun bindTo(downstream: Stream<T>): Stream<T>
}

/**
 * // TODO: refactor in sealed/enum or refactor all stream API
 * Takes a line and cuts into visible segments. Values for clean:
 *  0 - there were intersections or the line was empty;
 *  1 - no intersections;
 *  2 - there were intersections, and the first and last segments should be rejoined.
 */
abstract class ClipStream<T> : Stream<T>() {

    abstract var clean: Int
}


interface Clipper<T> {

    /**
     * Indicates if the point will be visible after clipping.
     */
    fun pointVisible(point:T): Boolean

    /**
     * In
     */
    fun clipLine(downstream: Stream<T>): ClipStream<T>

    fun interpolate(
        from: T?,
        to: T?,
        direction: Int,
        stream: Stream<T>
    )
}


internal interface ClipperWithStart<T> : Clipper<T> {
    val start: T
}

internal class ClippableStream(
    val clipper: ClipperWithStart<GeoPoint>,
    val downstream: Stream<GeoPoint>
) : Stream<GeoPoint>() {

    // context of execution of stream
    // a line can be projected in the context of a polygon or not
    enum class LineStartContext { DEFAULT, RING }
    enum class LineEndContext { DEFAULT, RING }

    //a point can be projected in the context of a polygon, a line, or nothing
    enum class PointContext { DEFAULT, RING, LINE }

    var pointContext = PointContext.DEFAULT
    var lineStartContext = LineStartContext.DEFAULT
    var lineEndContext = LineEndContext.DEFAULT

    internal var polygonStarted = false


    internal val clipStream = clipper.clipLine(downstream)

    internal val ringBuffer = BufferStream<GeoPoint>()
    internal val ringSink = clipper.clipLine(ringBuffer)


    internal val segments: MutableList<List<List<GeoPoint>>> = mutableListOf()

    internal val polygon: MutableList<List<GeoPoint>> = mutableListOf()
    internal var ring: MutableList<GeoPoint>? = null


    override fun polygonStart() {
        pointContext = PointContext.RING
        lineStartContext = LineStartContext.RING
        lineEndContext = LineEndContext.RING
    }

    override fun lineStart() {
        when (lineStartContext) {
            LineStartContext.DEFAULT -> {
                pointContext = PointContext.LINE
                clipStream.lineStart()
            }
            LineStartContext.RING -> {
                ringSink.lineStart()
                ring = mutableListOf()
            }
        }
    }

//    override fun point(x: Double, y: Double, z: Double) {
//        point(StreamPoint(x, y, z))
//    }
    override fun point(point: GeoPoint) {
        when (pointContext) {
            PointContext.RING -> pointRing(point)
            PointContext.LINE -> pointLine(point)
            PointContext.DEFAULT -> pointDefault(point)
        }
    }

    private fun pointRing(point: GeoPoint) {
        ring!!.add(point)
        ringSink.point(point)
    }


    private fun pointLine(point: GeoPoint) {
        clipStream.point(point)
    }

    private fun pointDefault(point: GeoPoint) {
        if (clipper.pointVisible(point))
            downstream.point(point)
    }

    override fun lineEnd() {
        when (lineEndContext) {
            LineEndContext.DEFAULT -> lineEndDefault()
            LineEndContext.RING -> lineEndRing()
        }
    }

    private fun lineEndDefault() {
        pointContext = PointContext.DEFAULT
        clipStream.lineEnd()
    }

    private fun lineEndRing() {
        requireNotNull(ring, { "Error on ClippableStream.ringEnd, ring can't be null." })

        val ringList = ring!!

        pointRing(ringList[0])

        ringSink.lineEnd()

        val clean = ringSink.clean
        val ringSegments: MutableList<List<GeoPoint>> = ringBuffer.result()


        ringList.removeAt(ringList.lastIndex)

        polygon.add(ringList)
        this.ring = null

        if (ringSegments.isEmpty()) return

        // No intersections
        if ((clean and 1) != 0) {
            val segment = ringSegments[0]
            val m = segment.lastIndex
            if (m > 0) {
                if (!polygonStarted) {
                    downstream.polygonStart()
                    polygonStarted = true
                }
                downstream.lineStart()
                (0 until m).forEach {
                    val currentSegmentPiece: GeoPoint = segment[it]
                    downstream.point(currentSegmentPiece)
                }
                downstream.lineEnd()
            }
            return
        }

        // Rejoin connected segments
        // TODO reuse ringBuffer.rejoin()?
        if (ringSegments.size > 1 && (clean and 2) != 0) {
            val concat = ringSegments.removeAt(ringSegments.lastIndex).toMutableList()
            concat.addAll(ringSegments.removeAt(0))
            ringSegments.add(concat)
        }

        segments.add(ringSegments.filter { it.size > 1 })
    }

    override fun polygonEnd() {
        pointContext = PointContext.DEFAULT
        lineStartContext = LineStartContext.DEFAULT
        lineEndContext = LineEndContext.DEFAULT

        val startInside = polygonContains(polygon, clipper.start)

        if (segments.isNotEmpty()) {
            if (!polygonStarted) {
                downstream.polygonStart()
                polygonStarted = true
            }
            rejoin(segments.flatten(), compareIntersection, startInside, clipper, downstream)
        } else if (startInside) {
            if (!polygonStarted) {
                downstream.polygonStart()
                polygonStarted = true
            }
            downstream.lineStart()
            clipper.interpolate(null, null, 1, downstream)
            downstream.lineEnd()
        }

        if (polygonStarted) {
            downstream.polygonEnd()
            polygonStarted = false
        }

        segments.clear()
        polygon.clear()
    }


    override fun sphere() {
        downstream.polygonStart()
        downstream.lineStart()
        clipper.interpolate(null, null, 1, downstream)
        downstream.lineEnd()
        downstream.polygonEnd()
    }


    private val compareIntersection = Comparator<Intersection<GeoPoint>> { i1, i2 ->
        val a = i1.point
        val b = i2.point
        val ca = if (a.lon.rad < 0) a.lat.rad - HALFPI - EPSILON else HALFPI - a.lat.rad
        val cb = if (b.lon.rad < 0) b.lat.rad - HALFPI - EPSILON else HALFPI - b.lat.rad
        ca.compareTo(cb)
    }
}


internal class BufferStream<T> : Stream<T>() {
    private var lines: MutableList<List<T>> = mutableListOf()
    private lateinit var line: MutableList<T>

    override fun lineStart() {
        line = mutableListOf()
        lines.add(line)
    }

    override fun point(point: T) {
        line.add(point)
    }

    fun rejoin() {
        if (lines.size > 1) {
            val l = mutableListOf<List<T>>()
            l.add(lines.removeAt(lines.lastIndex))
            l.add(lines.removeAt(0))
            lines.addAll(l)
        }
    }

    fun result(): MutableList<List<T>> {
        val oldLines = lines
        lines = mutableListOf()
        return oldLines
    }
}
