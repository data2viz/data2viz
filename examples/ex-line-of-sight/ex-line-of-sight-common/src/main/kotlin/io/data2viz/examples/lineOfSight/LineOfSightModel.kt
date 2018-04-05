package io.data2viz.examples.lineOfSight

import io.data2viz.core.*
import io.data2viz.math.Angle
import io.data2viz.math.PI
import io.data2viz.math.deg
import kotlin.math.atan2
import kotlin.math.cos
import kotlin.math.sin
import kotlin.math.sqrt

data class Segment(val from: Point, val to: Point)
data class Intersection(val point: Point, val param: Double, var angle: Angle = 0.deg)

data class LineOfSightConfig(
    val width: Double,
    val height: Double,
    val polygonNb: Int = 20,
    val polygonSize: Double = 0.15,
    val randomPointsNb: Int = 8
)


class LineOfSightModel(config: LineOfSightConfig) {

    var lightPoint: Point

    val polygons: List<Polygon>
    private val corners: List<Point>
    private val segments: List<Segment>
    private var xSpeed: Double = .0
    private var ySpeed: Double = .0
    private val extentPolygon: Polygon

    private fun Polygon.segments(): List<Segment> =
        List(points.size) { i ->
            Segment(
                points[i],
                if (i == points.size - 1) points[0] else points[i + 1]
            )
        }

    init {
        polygons = createPolygons(config.polygonNb, config.polygonSize, config.randomPointsNb)
        extentPolygon = Polygon(
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
        corners = extentPolygon.points + polygons.flatMap { it.points }
        segments = extentPolygon.segments() + polygons.flatMap { it.segments() }
        lightPoint = posOutsideOf(polygons)
        newRandomSpeed()
    }

    private fun createPolygons(polygonNb: Int, polygonSize: Double, randomPointsNb: Int): List<Polygon> {

        // build random polygons
        val polygons = (1..polygonNb).mapNotNull {
            val center = Point(
                random() * vizWidth * (1.0 - polygonSize),
                random() * vizHeight * (1.0 - polygonSize)
            )
            val points = (1..randomPointsNb).map {
                Point(
                    center.x + (random() * (vizWidth * polygonSize)).coerceIn(
                        .0,
                        vizWidth
                    ),
                    center.y + (random() * (vizWidth * polygonSize)).coerceIn(
                        .0,
                        vizWidth
                    )
                )
            }
            polygonHull(points)
        }.toMutableList()

        // if a polygon touch another one, fuse the two polygons
        fuseAdjacentPolygons(polygons)

        return polygons.toList()
    }

    private fun fuseAdjacentPolygons(polygons: MutableList<Polygon>) {
        polygons.forEachIndexed { index, polygon ->
            polygons.forEachIndexed { otherIndex, otherPolygon ->
                if (index != otherIndex) {
                    polygon.points.forEach { point ->
                        if (otherPolygon.contains(point)) {
                            polygons[index] = polygonHull(listOf(polygon.points, otherPolygon.points).flatMap { it })!!
                            polygons.removeAt(otherIndex)
                            fuseAdjacentPolygons(polygons)
                            return
                        }
                    }
                }
            }
        }
    }

    fun getSightPolygon(): Polygon {
        val allAngles = corners.flatMap {
            val rad = atan2(it.y - lightPoint.y, it.x - lightPoint.x)
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

            val ray = Segment(lightPoint, Point(lightPoint.x + dx, lightPoint.y + dy))

            // Find CLOSEST intersection
            var closestIntersection: Intersection? = null

            segments
                .mapNotNull { getIntersection(ray, it) }
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

    /*public boolean lineSegmentTouchesOrCrossesLine(LineSegment a,
        LineSegment b) {
    return isPointOnLine(a, b.first)
            || isPointOnLine(a, b.second)
            || (isPointRightOfLine(a, b.first) ^
                isPointRightOfLine(a, b.second));
}*/

    /**
     * @return the point of intersection or null if rays are parallel.
     */
    private fun getIntersection(s1: Segment, s2: Segment): Intersection? {

        val px1 = s1.from.x
        val py1 = s1.from.y
        val dx1 = s1.to.x - s1.from.x
        val dy1 = s1.to.y - s1.from.y

        val px2 = s2.from.x
        val py2 = s2.from.y
        val dx2 = s2.to.x - s2.from.x
        val dy2 = s2.to.y - s2.from.y

        // Are they parallel? If so, no intersect
        val l1 = sqrt(dx1 * dx1 + dy1 * dy1)
        val l2 = sqrt(dx2 * dx2 + dy2 * dy2)
        if (((dx1 / l1) == (dx2 / l2)) && ((dy1 / l1) == (dy2 / l2))) return null

        // SOLVE FOR T1 & T2
        // r_px+r_dx*T1 = s_px+s_dx*T2 && r_py+r_dy*T1 = s_py+s_dy*T2
        // ==> T1 = (s_px+s_dx*T2-r_px)/r_dx = (s_py+s_dy*T2-r_py)/r_dy
        // ==> s_px*r_dy + s_dx*T2*r_dy - r_px*r_dy = s_py*r_dx + s_dy*T2*r_dx - r_py*r_dx
        // ==> T2 = (r_dx*(s_py-r_py) + r_dy*(r_px-s_px))/(s_dx*r_dy - s_dy*r_dx)
        val t2 = (dx1 * (py2 - py1) + dy1 * (px1 - px2)) / (dx2 * dy1 - dy2 * dx1)
        val t1 = (px2 + dx2 * t2 - px1) / dx1

        // Must be within parametic whatevers for RAY/SEGMENT
        if (t1 < 0) return null
        if (t2 < 0 || t2 > 1) return null

        return Intersection(Point(px1 + dx1 * t1, py1 + dy1 * t1), t1)
    }

    private fun isIntersection(s1: Segment, s2: Segment): Boolean {

        val dx1 = s1.to.x - s1.from.x
        val dy1 = s1.to.y - s1.from.y
        val dx2 = s2.to.x - s2.from.x
        val dy2 = s2.to.y - s2.from.y

        val denom = (dx1 * dy2) - (dx2 * dy1)
        if (denom == .0)
            return false                                    // colinear

        val denomPositive = denom > 0

        val s02x = s1.from.x - s2.from.x
        val s02y = s1.from.y - s2.from.y
        val sNumer = dx1 * s02y - dy1 * s02x
        if ((sNumer < 0) == denomPositive)
            return false                                    // No collision

        val tNumer = dx2 * s02y - dy2 * s02x
        if ((tNumer < 0) == denomPositive)
            return false                                    // No collision

        if (((sNumer > denom) == denomPositive) || ((tNumer > denom) == denomPositive))
            return false                                    // No collision

        // Collision detected
        val t = tNumer / denom
        /*if (i_x != NULL)
        *i_x = p0_x + (t * s10_x)
        if (i_y != NULL)
        *i_y = p0_y + (t * s10_y)*/

        return true
    }

    private tailrec fun posOutsideOf(polygons: List<Polygon>): Point {
        val pos = Point(
            random() * vizWidth,
            random() * vizHeight
        )
        val insidePolygon = polygons.any { it.contains(pos) }
        return if (insidePolygon)
            posOutsideOf(polygons) else pos
    }

    internal fun moveLight() {
        // compute next position
        var newPos = Point(lightPoint.x + xSpeed, lightPoint.y + ySpeed)

        // check for polygon collision for next position (on the extent or one of the polygons on screen)
        val collidedPolygon =
            if (!extentPolygon.contains(newPos)) extentPolygon else polygons.find { it.contains(newPos) }

        // if collision is detected, compute rebound and recompute next position
        if (collidedPolygon != null) {

            // compute movement vector
            val movement = Segment(lightPoint, newPos)
            val collidedSegment = collidedPolygon.segments().find { isIntersection(movement, it) }
            if (collidedSegment != null) {

                // find intersection and compute normal vector of collided segment
                val dx = collidedSegment.to.x - collidedSegment.from.x
                val dy = collidedSegment.to.y - collidedSegment.from.y
                val intersection = getIntersection(movement, collidedSegment)!!.point
                val normal = Segment(intersection, Point(intersection.x - dy, intersection.y + dx))

                val dx1 = movement.from.x - movement.to.x
                val dx2 = normal.from.x - normal.to.x
                val dy1 = movement.from.y - movement.to.y
                val dy2 = normal.from.y - normal.to.y

                val a1 = atan2(dy1, dx1)                // movement angle
                val a2 = atan2(dy2, dx2)                // normal angle
                val newAngle = a2 + (a2 - a1)

                xSpeed = cos(newAngle) * 3
                ySpeed = sin(newAngle) * 3

                newPos = Point(lightPoint.x + xSpeed, lightPoint.y + ySpeed)
            }
        }
        lightPoint = newPos
    }

    private fun newRandomSpeed() {
        val angle = random() * PI * 2
        xSpeed = cos(angle) * 3
        ySpeed = sin(angle) * 3
    }

}