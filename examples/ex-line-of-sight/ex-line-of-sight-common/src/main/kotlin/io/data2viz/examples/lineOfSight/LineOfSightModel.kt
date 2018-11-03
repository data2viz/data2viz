package io.data2viz.examples.lineOfSight

import io.data2viz.geom.Point
import io.data2viz.geom.Polygon
import io.data2viz.geom.contains
import io.data2viz.geom.polygonHull
import io.data2viz.math.Angle
import io.data2viz.math.PI
import io.data2viz.math.deg
import kotlin.math.atan2
import kotlin.math.cos
import kotlin.math.sin
import kotlin.math.sqrt
import kotlin.random.Random


data class LineOfSightConfig(
    val width: Double,
    val height: Double,
    val polygonNb: Int = 20,
    val polygonSize: Double = 0.15,
    val randomPointsNb: Int = 8
)

class LineOfSightModel(config: LineOfSightConfig) {

    var lightPos: Point

    /**
     * Randomly created polygons
     */
    val polygons: List<Polygon>

    /**
     * All corners: view corners + polygons corners
     */
    private val corners: List<Point>

    /**
     * All segments: view segments + polygons segments
     */
    private val segments: List<Segment>


    private var xSpeed: Double = .0
    private var ySpeed: Double = .0

    private val viewAsPolygon: Polygon


    init {
        polygons = createPolygons(config.polygonNb, config.polygonSize, config.randomPointsNb)
        viewAsPolygon = Polygon(
            listOf(
                Point(.0, .0),
                Point(vizWidth, .0),
                Point(
                    vizWidth,
                    vizHeight
                ),
                Point(.0, vizHeight)
            )
        )
        corners = viewAsPolygon.points + polygons.flatMap { it.points }
        segments = viewAsPolygon.segments() + polygons.flatMap { it.segments() }
        lightPos = findInitialRandomPointOutsideOf(polygons)
        newSpeed(Random.nextDouble() * PI * 2)
    }

    private fun createPolygons(polygonNb: Int, polygonSize: Double, randomPointsNb: Int): List<Polygon> {

        // build random polygons
        val polygons = (1..polygonNb).map {
            val center = Point(
                Random.nextDouble() * vizWidth * (1.0 - polygonSize),
                Random.nextDouble() * vizHeight * (1.0 - polygonSize)
            )
            val points = (1..randomPointsNb).map { _ ->
                Point(
                    center.x + (Random.nextDouble() * (vizWidth * polygonSize)).coerceIn(.0, vizWidth),
                    center.y + (Random.nextDouble() * (vizHeight * polygonSize)).coerceIn(.0, vizHeight)
                )
            }
            polygonHull(points)
        }.toMutableList()

        // if a polygon touch another one, fuse the two polygons
        mergeAdjacentPolygons(polygons)

        return polygons
    }

    private fun mergeAdjacentPolygons(polygons: MutableList<Polygon>) {
        polygons.forEachIndexed { index, polygon ->
            polygons.forEachIndexed { otherIndex, otherPolygon ->
                if (index != otherIndex) {
                    polygon.points.forEach { point ->
                        if (otherPolygon.contains(point)) {
                            polygons[index] = polygonHull(listOf(polygon.points, otherPolygon.points).flatMap { it })
                            polygons.removeAt(otherIndex)
                            mergeAdjacentPolygons(polygons)
                            return
                        }
                    }
                }
            }
        }
    }

    fun getSightPolygon(): Polygon {
        val allAngles = corners.flatMap {
            val rad = atan2(it.y - lightPos.y, it.x - lightPos.x)
            listOf(
                Angle(rad),
                Angle(rad + .00001),
                Angle(rad - .00001)
            )
        }

        val intersections = mutableListOf<Intersection>()
        allAngles.forEach { angle ->

            val dx = angle.cos
            val dy = angle.sin

            val ray = Segment(lightPos, Point(lightPos.x + dx, lightPos.y + dy))

            // Find CLOSEST intersection
            var closestIntersection: Intersection? = null

            segments
                .mapNotNull { segment ->  ray.intersect(segment) }
                .forEach { intersection ->
                    if (closestIntersection == null || (intersection.param < closestIntersection!!.param)) {
                        closestIntersection = intersection
                    }
                }

            // Intersect angle
            closestIntersection?.let {
                it.angle = angle

                // Add to list of intersections
                intersections.add(it)
            }
        }

        intersections.sortBy { it.angle.rad }

        // Polygon is intersects, in order of angle
        return Polygon(intersections.map { it.point })
    }

    private tailrec fun findInitialRandomPointOutsideOf(polygons: List<Polygon>): Point {
        val pos = Point(
            Random.nextDouble() * vizWidth,
            Random.nextDouble() * vizHeight
        )
        val insidePolygon = polygons.any { it.contains(pos) }
        return if (insidePolygon)
            findInitialRandomPointOutsideOf(polygons) else pos
    }

    internal fun moveLight() {

        var newPos = Point(lightPos.x + xSpeed, lightPos.y + ySpeed)

        // check for polygon collision for next position (on the extent or one of the polygons on screen)
        val collidedPolygon =
                if (viewAsPolygon.contains(newPos))
                    polygons.find { it.contains(newPos) }
                else
                    viewAsPolygon

        // if collision is detected, compute rebound and recompute next position
        if (collidedPolygon != null) {

            // compute movement vector
            val movement = Segment(lightPos, newPos)
            val collidedSegment = collidedPolygon.segments().find { movement.isIntersection(it) }
            if (collidedSegment != null) {

                // find intersection and compute normal vector of collided segment
                val dx = collidedSegment.to.x - collidedSegment.from.x
                val dy = collidedSegment.to.y - collidedSegment.from.y
                val intersection = movement.intersect(collidedSegment)!!.point
                val normal = Segment(intersection, Point(intersection.x - dy, intersection.y + dx))

                val dx1 = movement.from.x - movement.to.x
                val dx2 = normal.from.x - normal.to.x
                val dy1 = movement.from.y - movement.to.y
                val dy2 = normal.from.y - normal.to.y

                val movementAngle = atan2(dy1, dx1)
                val normalAngle = atan2(dy2, dx2)

                newSpeed(normalAngle + (normalAngle - movementAngle))
                newPos = Point(lightPos.x + xSpeed, lightPos.y + ySpeed)
            }
        }
        lightPos = newPos
    }

    private fun newSpeed(currentDirection:Double) {
        xSpeed = cos(currentDirection) * 0.004 * vizWidth
        ySpeed = sin(currentDirection) * 0.004 * vizHeight
    }

}

private fun Polygon.segments(): List<Segment> =
        List(points.size) { i ->
            Segment(
                    points[i],
                    if (i == points.size - 1) points[0] else points[i + 1]
            )
        }


/**
 * Todo move to core?
 */
data class Segment(val from: Point, val to: Point) {

    /**
     * @return the point of intersection or null if rays are parallel.
     */
    fun intersect(other: Segment): Intersection? {

        val px1 = from.x
        val py1 = from.y
        val dx1 = to.x - from.x
        val dy1 = to.y - from.y

        val px2 = other.from.x
        val py2 = other.from.y
        val dx2 = other.to.x - other.from.x
        val dy2 = other.to.y - other.from.y

        // Are they parallel? If so, no intersect
        val l1 = sqrt(dx1 * dx1 + dy1 * dy1)
        val l2 = sqrt(dx2 * dx2 + dy2 * dy2)
        if (((dx1 / l1) == (dx2 / l2)) && ((dy1 / l1) == (dy2 / l2))) return null

        // SOLVE FOR T1 & T2
        val t2 = (dx1 * (py2 - py1) + dy1 * (px1 - px2)) / (dx2 * dy1 - dy2 * dx1)
        val t1 = (px2 + dx2 * t2 - px1) / dx1  //todo dx1 can be 0 => division by 0 !!

        // Must be within parametic whatevers for RAY/SEGMENT
        if (t1 < 0) return null
        if (t2 < 0 || t2 > 1) return null

        return Intersection(Point(px1 + dx1 * t1, py1 + dy1 * t1), t1)
    }

    fun isIntersection(other: Segment): Boolean {

        val dx1 = to.x - from.x
        val dy1 = to.y - from.y
        val dx2 = other.to.x - other.from.x
        val dy2 = other.to.y - other.from.y

        val denom = (dx1 * dy2) - (dx2 * dy1)
        if (denom == .0)
            return false                                    // colinear

        val denomPositive = denom > 0

        val s02x = from.x - other.from.x
        val s02y = from.y - other.from.y
        val sNumer = dx1 * s02y - dy1 * s02x
        if ((sNumer < 0) == denomPositive)
            return false                                    // No collision

        val tNumer = dx2 * s02y - dy2 * s02x
        if ((tNumer < 0) == denomPositive)
            return false                                    // No collision

        if (((sNumer > denom) == denomPositive) || ((tNumer > denom) == denomPositive))
            return false                                    // No collision

        // Collision detected
        return true
    }

}

data class Intersection(val point: Point, val param: Double) {
    var angle: Angle = 0.deg
}
