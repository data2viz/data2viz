package io.data2viz.examples.lineOfSight

import io.data2viz.color.*
import io.data2viz.core.*
import io.data2viz.math.Angle
import io.data2viz.math.PI
import io.data2viz.math.deg
import io.data2viz.timer.timer
import io.data2viz.viz.*
import kotlin.math.*

const val vizWidth = 800.0
const val vizHeight = 800.0

val darkColor = Color(0x131c2b)

fun VizContext.lineOfSightViz() {

    val model = LineOfSightModel(LineOfSightConfig(vizWidth, vizHeight))

    renderBackground()
    renderPolygons(model.polygons)
    
    val radialGradient = RadialGradient().apply {
        r = .7 * vizWidth
        addColor(.0, colors.white)
        addColor(.01, colors.white)
        addColor(.02, colors.yellow)
        addColor(1.0, darkColor)
    }

    var path:PathVizElement? = null
    group {
        timer {
            path?.let { remove(it) }
            model.moveLight()
            radialGradient.cx = model.lightPoint.x
            radialGradient.cy = model.lightPoint.y
            val points = model.getSightPolygon().points
            path = path {
                moveTo(points.first().x, points.first().y)
                fill = radialGradient
                stroke = null
                points.forEach { point ->
                    lineTo(point.x, point.y)
                }
                closePath()
            }
        }
    }
}

private fun VizContext.renderPolygons(polygons: List<Polygon>) {
    polygons.forEach { polygon ->
        path {
            fill = colors.black
            stroke = null
            moveTo(polygon.points.first().x, polygon.points.first().y)
            (1 until polygon.points.size).forEach {
                lineTo(polygon.points[it].x, polygon.points[it].y)
            }
            closePath()
        }
    }
}

private fun VizContext.renderBackground() {
    rect {
        fill = darkColor
        x = .0
        y = .0
        width = vizWidth
        height = vizHeight
    }
}

data class Segment(val from: Point, val to: Point)
data class Intersection(val point: Point, val param: Double, var angle: Angle = 0.deg)

data class LineOfSightConfig(
    val width: Double,
    val height: Double,
    val polygonNb: Int = 15,
    val randomPointsNb: Int = 10
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
        polygons = createPolygons(config.polygonNb, config.randomPointsNb)
        extentPolygon = Polygon(listOf(Point(.0, .0), Point(vizWidth, .0), Point(vizWidth, vizHeight), Point(.0, vizHeight)))
        corners = extentPolygon.points + polygons.flatMap { it.points }
        segments = extentPolygon.segments() + polygons.flatMap { it.segments() }
        lightPoint = posOutsideOf(polygons)
        newRandomSpeed()
    }

    private fun createPolygons(polygonNb: Int, randomPointsNb: Int): List<Polygon> = (1..polygonNb).mapNotNull {
        val center = Point(random() * vizWidth * .9, random() * vizHeight * .9)
        val points = (1..randomPointsNb).map {
            Point(
                center.x + (random() * (vizWidth / 7)).coerceIn(.0, vizWidth),
                center.y + (random() * (vizWidth / 7)).coerceIn(.0, vizWidth)
            )
        }
        polygonHull(points)
    }

    fun getSightPolygon(): Polygon {
        val allAngles = corners.flatMap {
            val rad = atan2(it.y - lightPoint.y, it.x - lightPoint.x)
            listOf(Angle(rad), Angle(rad + .00001), Angle(rad - .00001))
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
        if (dx1 / l1 == dx2 / l2 && 
            dy1 / l1 == dy2 / l2) {
            return null
        }
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

    private tailrec fun posOutsideOf(polygons: List<Polygon>): Point {
        val pos = Point(random() * vizWidth, random() * vizHeight)
        val insidePolygon = polygons.any { it.contains(pos) }
        return if (insidePolygon)
            posOutsideOf(polygons) else pos
    }

    internal tailrec fun moveLight() {
        val newPos = Point(lightPoint.x + xSpeed, lightPoint.y + ySpeed)
        if (polygons.any { it.contains(newPos) } ||
            !extentPolygon.contains(newPos)) {
            newRandomSpeed()
            moveLight()
        } else {
            lightPoint = newPos
        }
    }

    private fun newRandomSpeed() {
        val angle = random() * PI * 2
        xSpeed = cos(angle) * 3
        ySpeed = sin(angle) * 3
    }

}
