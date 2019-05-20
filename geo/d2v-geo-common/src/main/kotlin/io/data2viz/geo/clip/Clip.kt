package io.data2viz.geo.clip

import io.data2viz.geo.polygonContains
import io.data2viz.geo.projection.Stream
import io.data2viz.math.EPSILON
import io.data2viz.math.HALFPI

/**
 * Takes a line and cuts into visible segments. Values for clean:
 *  0 - there were intersections or the line was empty;
 *  1 - no intersections;
 *  2 - there were intersections, and the first and last segments should be rejoined.
 */
interface ClipStream : Stream {
    var clean: Int
}

interface Clippable {
    fun pointVisible(x: Double, y: Double): Boolean
    fun clipLine(stream: Stream): ClipStream
    fun interpolate(from: DoubleArray?, to: DoubleArray?, direction: Int, stream: Stream)
}

interface ClippableHasStart : Clippable {
    val start: DoubleArray
}

interface PointFunction {
    fun invoke(clip: Clip, x: Double, y: Double, z: Double)
}

interface LineStartFunction {
    fun invoke(clip: Clip)
}


interface LineEndFunction {
    fun invoke(clip: Clip)
}

object DefaultPointFunction : PointFunction {
    override fun invoke(clip: Clip, x: Double, y: Double, z: Double) {
        if (clip.clip.pointVisible(x, y)) clip.sink.point(x, y, z)
    }
}

object RingPointFunction : PointFunction {
    override fun invoke(clip: Clip, x: Double, y: Double, z: Double) {
        clip.apply {
            ring!!.add(doubleArrayOf(x, y))
            ringSink.point(x, y, z)
        }
    }
}

object DefaultLineStartFunction : LineStartFunction {
    override fun invoke(clip: Clip) {
        clip.apply {
            currentPoint = LinePointFunction
            line.lineStart()
        }
    }
}


object DefaultLineEndFunction : LineEndFunction {
    override fun invoke(clip: Clip) {
        clip.apply {
            currentPoint = DefaultPointFunction
            line.lineEnd()
        }
    }
}


object RingLineStartFunction : LineStartFunction {
    override fun invoke(clip: Clip) {
        clip.apply {
            ringSink.lineStart()
            ring = mutableListOf()
        }
    }
}

object RingLineEndFunction : LineEndFunction {
    override fun invoke(clip: Clip) {
        clip.apply {
            requireNotNull(ring, { "Error on Clip.ringEnd, ring can't be null." })

            val ringList = ring!!

            RingPointFunction.invoke(this, ringList[0][0], ringList[0][1], 0.0)

            ringSink.lineEnd()

            val clean = ringSink.clean
            val ringSegments: MutableList<List<DoubleArray>> = ringBuffer.result()


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
                        sink.polygonStart()
                        polygonStarted = true
                    }
                    sink.lineStart()
                    (0 until m).forEach {
                        val currentSegmentPiece = segment[it]
                        val x = currentSegmentPiece[0]
                        val y = currentSegmentPiece[1]
                        sink.point(x, y, 0.0)
                    }
                    sink.lineEnd()
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

    }
}


object LinePointFunction : PointFunction {
    override fun invoke(clip: Clip, x: Double, y: Double, z: Double) {
        clip.line.point(x, y, z)
    }


}

object PointRingPointFunction : PointFunction {
    override fun invoke(clip: Clip, x: Double, y: Double, z: Double) {
        clip.ring!!.add(doubleArrayOf(x, y))
        clip.ringSink.point(x, y, z)
    }

}


class Clip(val clip: ClippableHasStart, val sink: Stream) : Stream {


    internal val line = clip.clipLine(sink)

    internal val ringBuffer = ClipBuffer()
    internal val ringSink = clip.clipLine(ringBuffer)

    internal var polygonStarted = false

    internal val segments: MutableList<List<List<DoubleArray>>> = mutableListOf()

    internal val polygon: MutableList<List<DoubleArray>> = mutableListOf()
    internal var ring: MutableList<DoubleArray>? = null

    internal var currentPoint: PointFunction = DefaultPointFunction
    internal var currentLineStart: LineStartFunction = DefaultLineStartFunction
    internal var currentLineEnd: LineEndFunction = DefaultLineEndFunction

    private val compareIntersection = Comparator<Intersection> { i1, i2 ->
        val a = i1.point
        val b = i2.point
        val ca = if (a[0] < 0) a[1] - HALFPI - EPSILON else HALFPI - a[1]
        val cb = if (b[0] < 0) b[1] - HALFPI - EPSILON else HALFPI - b[1]
        ca.compareTo(cb)
    }

    override fun point(x: Double, y: Double, z: Double) {
        currentPoint.invoke(this, x, y, z)
    }

    override fun lineStart() {
        currentLineStart.invoke(this)
    }

    override fun lineEnd() {
        currentLineEnd.invoke(this)
    }

    override fun polygonStart() {
        currentPoint = PointRingPointFunction
        currentLineStart = RingLineStartFunction
        currentLineEnd = RingLineEndFunction
    }

    val interpolateFunction = object : InterpolateFunction {
        override fun invoke(from: DoubleArray, to: DoubleArray, direction: Int, stream: Stream) {
            clip.interpolate(from, to, direction, stream)
        }

    }

    override fun polygonEnd() {
        currentPoint = DefaultPointFunction
        currentLineStart = DefaultLineStartFunction
        currentLineEnd = DefaultLineEndFunction

        val startInside = polygonContains(polygon, clip.start)

        if (segments.isNotEmpty()) {
            if (!polygonStarted) {
                sink.polygonStart()
                polygonStarted = true
            }

            rejoin(segments.flatten(), compareIntersection, startInside, interpolateFunction, sink)
        } else if (startInside) {
            if (!polygonStarted) {
                sink.polygonStart()
                polygonStarted = true
            }
            sink.lineStart()
            clip.interpolate(null, null, 1, sink)
            sink.lineEnd()
        }

        if (polygonStarted) {
            sink.polygonEnd()
            polygonStarted = false
        }

        segments.clear()
        polygon.clear()
    }

    override fun sphere() {
        sink.polygonStart()
        sink.lineStart()
        clip.interpolate(null, null, 1, sink)
        sink.lineEnd()
        sink.polygonEnd()
    }


}