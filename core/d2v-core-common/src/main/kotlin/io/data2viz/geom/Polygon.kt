package io.data2viz.geom

import kotlin.math.sqrt

data class Polygon(val points: List<Point>)

/**
 * Returns the signed area of the polygon. If the vertices of the polygon are in counterclockwise order
 * (assuming a coordinate system where the origin ⟨0,0⟩ is in the top-left corner), the returned area is positive;
 * otherwise it is negative, or zero.
 */
val Polygon.area: Double
    get() {
        var i = -1
        val size = points.size
        var b = points.last()
        var area = .0

        while (++i < size) {
            val a = b
            b = points[i]
            area += a.y * b.x - a.x * b.y
        }
        return area / 2
    }

/**
 * Returns the centroid of the polygon.
 */
val Polygon.centroid: Point
    get() {
        var i = -1
        val size = points.size
        var x = .0
        var y = .0
        var b = points.last()
        var k = .0

        while (++i < size) {
            val a = b
            b = points[i]
            val c = a.x * b.y - b.x * a.y
            k += c
            x += (a.x + b.x) * c
            y += (a.y + b.y) * c
        }

        k *= 3.0
        return Point(x / k, y / k)
    }

/**
 * Returns the length of the perimeter of the specified polygon.
 */
val Polygon.length: Double
    get() {
        var i = -1
        val size = points.size
        var b = points.last()
        var xb = b.x
        var yb = b.y
        var perimeter = .0

        while (++i < size) {
            var xa = xb
            var ya = yb
            b = points[i]
            xb = b.x
            yb = b.y
            xa -= xb
            ya -= yb
            perimeter += sqrt(xa * xa + ya * ya)
        }

        return perimeter
    }

/**
 * Returns true if and only if the specified point is inside the polygon.
 */
fun Polygon.contains(point: Point): Boolean {
    val size = points.size
    var p = points.last()
    val x = point.x
    val y = point.y
    var x0 = p.x
    var y0 = p.y
    var inside = false

    (0 until size).forEach { i ->
        p = points[i]
        val x1 = p.x
        val y1 = p.y
        if (((y1 > y) != (y0 > y)) && (x < (x0 - x1) * (y - y1) / (y0 - y1) + x1)) inside = !inside
        x0 = x1
        y0 = y1
    }

    return inside
}

/**
 * Returns the Polygon convex hull of the specified points using Andrew’s monotone chain algorithm.
 * The returned hull is represented as a Polygon containing a subset of the input points arranged in counterclockwise
 * order.
 * Returns null if points has fewer than three elements.
 */
fun polygonHull(points: List<Point>): Polygon {
    val size = points.size
    require(size > 2) {"A polygon must have at least 3 points"}


    data class PointIndex(val point: Point, val index: Int)

    val sortedPoints = mutableListOf<PointIndex>()
    val flippedPoints = mutableListOf<Point>()

    (0 until size).forEach { i -> sortedPoints.add(PointIndex(points[i], i)) }
    sortedPoints.sortWith(compareBy({ it.point.x }, { it.point.y }))

    (0 until size).forEach { i -> flippedPoints.add(Point(sortedPoints[i].point.x, -sortedPoints[i].point.y)) }

    val upperIndexes = computeUpperHullIndexes(sortedPoints.map { it.point })
    val lowerIndexes = computeUpperHullIndexes(flippedPoints)

    // Construct the hull polygon, removing possible duplicate endpoints.
    val skipLeft = lowerIndexes[0] == upperIndexes[0]
    val skipRight = lowerIndexes[lowerIndexes.size - 1] == upperIndexes[upperIndexes.size - 1]
    val hull = mutableListOf<Point>()

    // Add upper hull in right-to-left order.
    // Then add lower hull in left-to-right order.
    (upperIndexes.lastIndex downTo 0).forEach { i -> hull.add(points[sortedPoints[upperIndexes[i]].index]) }

    val start = if (skipLeft) 1 else 0
    val end = if (skipRight) 1 else 0
    (start until lowerIndexes.size - end).forEach { i -> hull.add(points[sortedPoints[lowerIndexes[i]].index]) }

    return Polygon(hull)
}

/**
 * Computes the upper convex hull per the monotone chain algorithm.
 * Assumes points.length >= 3, is sorted by x, unique in y.
 * Returns an array of indices into points in left-to-right order.
 */
private fun computeUpperHullIndexes(points: List<Point>): List<Int> {
    val indexes = MutableList(points.size, { 0 })
    indexes[1] = 1
    var size = 2

    (2 until points.size).forEach { i ->
        while (size > 1 && cross(points[indexes[size - 2]], points[indexes[size - 1]], points[i]) <= 0) --size
        if (size < indexes.lastIndex) indexes[size] = i else indexes.add(i)
        size++
    }

    return indexes.subList(0, size)      // remove popped points
}

/**
 * Returns the 2D cross product of AB and AC vectors, i.e., the z-component of the 3D cross product in a
 * quadrant I Cartesian coordinate system (+x is right, +y is up).
 * Returns a positive value if ABC is counter-clockwise, negative if clockwise, and zero if the points are collinear.
 */
private fun cross(a: Point, b: Point, c: Point): Double {
    return ((b.x - a.x) * (c.y - a.y)) - ((b.y - a.y) * (c.x - a.x))
}
