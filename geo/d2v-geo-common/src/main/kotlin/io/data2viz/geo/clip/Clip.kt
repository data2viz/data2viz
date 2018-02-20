/*package io.data2viz.geo.clip

import io.data2viz.geo.polygonContains
import io.data2viz.geo.projection.Stream
import io.data2viz.path.epsilon

interface Clippable {
    fun pointVisible(x: Double, y: Double): Boolean
    fun clipLine(stream: Stream): Stream
    fun interpolate(from: DoubleArray?, to: DoubleArray?, direction: Int, stream: Stream)
    val start: DoubleArray
}

class Clip(val clip: Clippable, val stream: Stream) : Stream {
    private val line = clip.clipLine(stream)
    private val ringBuffer = ClipBuffer()
    private val ringSink = clip.clipLine(ringBuffer)
    private var polygonStarted = false
    private val polygon: List<List<DoubleArray>> = listOf()
    private val segments: List<List<List<DoubleArray>>> = listOf()
    private var ring: List<DoubleArray>? = null

    private var currentPoint: (Double, Double, Double) -> Unit = ::defaultPoint
    private var currentLineStart: () -> Unit = ::defaultLineStart
    private var currentLineEnd: () -> Unit = ::defaultLineEnd

    init {
//        point = ::_point
//        lineStart = ::_lineStart
//        lineEnd = ::_lineEnd

//        polygonStart = {
//            point = ::pointRing
//            lineStart = ::ringStart
//            lineEnd = ::ringEnd
//            segments.clear()
//            polygon.clear()
//        }

        polygonEnd = {
            point = ::_point
            lineStart = ::_lineStart
            lineEnd = ::_lineEnd
            val flattenedSegments = segments.flatten()

            val startInside = polygonContains(polygon, clip.start)

            if (flattenedSegments.isNotEmpty()) {
                if (!polygonStarted) {
                    stream.polygonStart()
                    polygonStarted = true
                }

                val compareIntersection = Comparator<Intersection> { i1, i2 ->
                    val a = i1.x
                    val b = i2.x
                    val ca = if (a[0] < 0) a[1] - halfpi - epsilon else halfpi - a[1]
                    val cb = if (b[0] < 0) b[1] - halfpi - epsilon else halfpi - b[1]
                    ca.compareTo(cb)
                }
                clipRejoin(flattenedSegments, compareIntersection, startInside, clip::interpolate, stream)
            } else if (startInside) {
                if (!polygonStarted) {
                    stream.polygonStart()
                    polygonStarted = true
                }
                stream.lineStart()
                clip.interpolate(null, null, 1, stream)
                stream.lineEnd()
            }

            if (polygonStarted) {
                stream.polygonEnd()
                polygonStarted = false
            }

            segments.clear()
            polygon.clear()
        }

//        sphere = {
//            stream.polygonStart()
//            stream.lineStart()
//            clip.interpolate(null, null, 1, stream)
//            stream.lineEnd()
//            stream.polygonEnd()
//        }
    }

    private fun defaultPoint(x: Double, y: Double, z: Double) {
        if (clip.pointVisible(x, y)) stream.point(x, y, z)
    }

    private fun pointLine(x: Double, y: Double, z: Double) = line.point(x, y, z)
    private fun pointRing(x: Double, y: Double, z: Double) {
        ring!!.add(doubleArrayOf(x, y))
        ringSink.point(x, y, z)
    }


    override fun point(x: Double, y: Double, z: Double) {
        currentPoint(x, y, z)
    }

    override fun lineStart() {
        currentLineStart()
    }

    override fun lineEnd() {
        currentLineEnd()
    }

    override fun polygonStart() {
        currentPoint = ::pointRing
        currentLineStart = ::ringStart
        currentLineEnd = ::ringEnd
    }

    override fun sphere() {
        stream.polygonStart()
        stream.lineStart()
        clip.interpolate(null, null, 1, stream)
        stream.lineEnd()
        stream.polygonEnd()
    }

    private fun defaultLineStart() {
        currentPoint = ::pointLine
        line.lineStart()
    }

    private fun defaultLineEnd() {
        currentPoint = ::defaultPoint
        line.lineEnd()
    }

    private fun ringStart() {
        ringSink.lineStart()
        ring = LinkedList()
    }

    private fun ringEnd() {
        val ring = ring
        if (ring != null) {
            pointRing(ring[0][0], ring[0][1], 0.0)
            ringSink.lineEnd()

            val clean = ringSink.clean
            val ringSegments = LinkedList(ringBuffer.result())

            ring.removeLast()
            polygon.add(ring)
            this.ring = null

            if (ringSegments.isEmpty()) return

            // No intersections
            if ((clean and 1).isTruthy()) {
                val segment = ringSegments[0]
                if (segment != null) {
                    val m = segment.size - 1
                    if (m > 0) {
                        if (!polygonStarted) {
                            stream.polygonStart()
                            polygonStarted = true
                        }
                        stream.lineStart()
                        (0 until m).map { segment[it] }
                                .forEach { stream.point(it[0], it[1], 0.0) }
                        stream.lineEnd()
                    }
                }
                return
            }

            // Rejoin connected segments
            if (ringSegments.size > 1 && (clean and 2).isTruthy()) {
                ringSegments.add(ringSegments.removeLast().toMutableList().apply { addAll(ringSegments.removeFirst()) })
            }

            segments.add(ringSegments.filter { it.size > 1 })
        }
    }
}*/