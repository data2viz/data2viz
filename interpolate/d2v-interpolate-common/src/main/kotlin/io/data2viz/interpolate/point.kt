package io.data2viz.interpolate

import io.data2viz.geom.Point
import io.data2viz.math.Percent
import io.data2viz.math.pct

class PointComparatorX : Comparator<Point> {
    override fun compare(a: Point, b: Point): Int {
        return a.x.compareTo(b.x)
    }
}

class PointComparatorY : Comparator<Point> {
    override fun compare(a: Point, b: Point): Int {
        return a.x.compareTo(b.x)
    }
}

fun interpolatePoint(start: Point, end: Point): Interpolator<Point> {
    val diff = end - start
    return { percent -> start + diff * percent.value }
}

// TODO ?
//fun interpolateRound(start: Double, end: Double): Interpolator<Double> {
//    val diff = end - start
//    return { percent -> round(start + percent.value * diff) }
//}

// TODO : should do uninterpolate (precise only) / uninterpolateX (on x-axis) / uninterpolateY (on y-axis)
fun uninterpolatePointOnX(start: Point, end: Point): UnInterpolator<Point> {
    val diff = end.x - start.x
    return if (diff != .0) { point -> Percent((point.x - start.x) / diff) } else { _ -> 0.pct }
}

fun uninterpolatePointOnY(start: Point, end: Point): UnInterpolator<Point> {
    val diff = end.y - start.y
    return if (diff != .0) { point -> Percent((point.y - start.y) / diff) } else { _ -> 0.pct }
}