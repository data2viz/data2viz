package io.data2viz.geo.geometry.clip

import io.data2viz.geo.stream.Stream
import io.data2viz.math.EPSILON
import kotlin.math.abs

data class Intersection(
    val point: DoubleArray,
    val points: List<DoubleArray>?,
    var other: Intersection?,
    var entry: Boolean,
    var visited: Boolean = false,
    var next: Intersection? = null,
    var previous: Intersection? = null
)

interface InterpolateFunction {
    fun invoke(from: DoubleArray, to: DoubleArray, direction: Int, stream: Stream)
}
/**
 * A generalized polygon clipping algorithm: given a polygon that has been cut into its visible line segments,
 * and rejoins the segments by interpolating along the clip edge.
 */
fun rejoin(
    segments: List<List<DoubleArray>>, compareIntersection: Comparator<Intersection>,
    startInside: Boolean, interpolateFunction: InterpolateFunction, stream: Stream
) {
    val subject = mutableListOf<Intersection>()
    val clip = mutableListOf<Intersection>()

    segments.forEach {segment ->
        val n = segment.size - 1
        if (n <= 0) return

        var p0 = segment[0]
        val p1 = segment[n]

        // If the first and last points of a segment are coincident, then treat as a
        // closed ring.
        // TODO if all rings are closed, then the winding order of the exterior ring should be checked.
        if (pointEqual(p0, p1)) {
            stream.lineStart()
            (0 until n).forEach {index ->
                p0 = segment[index]
                stream.point(p0[0], p0[1], .0)
            }
            stream.lineEnd()
            return
        }

        var x = Intersection(p0, segment, null, true)
        subject.add(x)

        x.other = Intersection(p0, null, x, false)
        clip.add(x.other!!)

        x = Intersection(p1, segment, null, false)
        subject.add(x)

        x.other = Intersection(p1, null, x, true)
        clip.add(x.other!!)
    }

    if (subject.isEmpty()) return

    clip.sortWith(compareIntersection)
    link(subject)
    link(clip)

    var newStartInside = startInside

    clip.forEach {
        newStartInside = !newStartInside
        it.entry = newStartInside
    }

    val start = subject[0]

    while (true) {

        // Find first unvisited intersection.
        var current = start
        var isSubject = true

        while (current.visited) {
            current = current.next!!
            if (current == start) return
        }

        var points = current.points
        stream.lineStart()

        do {
            current.other!!.visited = true
            current.visited = true

            if (current.entry) {
                if (isSubject) {
                    if (points != null) points.forEach { stream.point(it[0], it[1], .0) }
                } else {
                    interpolateFunction.invoke(current.point, current.next!!.point, 1, stream)
                }
                current = current.next!!
            } else {
                if (isSubject) {
                    points = current.previous!!.points
                    if (points != null) points.asReversed().forEach { stream.point(it[0], it[1], .0) }
                } else {
                    interpolateFunction.invoke(current.point, current.previous!!.point, -1, stream)
                }
                current = current.previous!!
            }
            current = current.other!!
            points = current.points
            isSubject = !isSubject

        } while (!current.visited)

        stream.lineEnd()
    }
}

fun link(list: List<Intersection>) {
    if (list.isEmpty()) return

    var a = list.first()
    var b:Intersection

    (1 until list.size).forEach { index ->
        a.next = list[index]
        b = list[index]
        b.previous = a
        a = b
    }
    b = list.first()
    a.next = b
    b.previous = a
}

internal fun pointEqual(p0: DoubleArray, p1: DoubleArray): Boolean {
    return abs(p0[0] - p1[0]) < EPSILON && abs(p0[1] - p1[1]) < EPSILON
}
