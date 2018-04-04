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

const val polygonNb = 15
const val randomPointsNb = 10

val darkColor = Color(0x131c2b)

lateinit var allCorners:List<Point>
val allSegments = mutableListOf<Ray>()

lateinit var lightPoint: Point
lateinit var fromList: List<Point>
var xSpeed: Double = .0
var ySpeed: Double = .0

lateinit var sightGroupParent: Group
lateinit var sightGroup: Group
lateinit var lightGroup: Group


val radialGradient by lazy {
    RadialGradient().apply {
        cx = .0
        cy = .0
        r = .7 * vizWidth
        addColor(.0, colors.yellow)
        addColor(1.0, darkColor)
    }
}

data class Ray(val from: Point, val to: Point)
data class Intersection(val point: Point, val param: Double, var angle: Angle = 0.deg)

fun VizContext.lineOfSightViz() {
    
    //background
    rect {
        fill = darkColor
        x = .0
        y = .0
        width = vizWidth
        height = vizHeight
    }

    val screen = listOf(Point(.0, .0), Point(vizWidth, .0), Point(vizWidth, vizHeight), Point(.0, vizHeight))
    val polygons = createPolygons()
    allCorners = screen + polygons.flatMap { it.points }

    allSegments.add(Ray(screen[0], screen[1]))
    allSegments.add(Ray(screen[1], screen[2]))
    allSegments.add(Ray(screen[2], screen[3]))
    allSegments.add(Ray(screen[3], screen[0]))

    randomSpeed()
    
    lightPoint = posOutsideOf(polygons)
    fromList = listOf(lightPoint)

    timer {
        loop(polygons)
    }

    sightGroupParent = newGroup()
    sightGroup = newGroup()
    lightGroup = newGroup()

    add(sightGroupParent)
    sightGroupParent.add(sightGroup)
    add(lightGroup)

    polygons.forEach { polygon ->
        path {
            var previousPoint: Point? = null
            fill = colors.black
            stroke = null
            moveTo(polygon.points[0].x, polygon.points[0].y)
            polygon.points.forEachIndexed { index, point ->
                if (index != 0) lineTo(point.x, point.y)
                if (previousPoint != null) allSegments.add(Ray(previousPoint!!, point))
                previousPoint = point
            }
            lineTo(polygon.points[0].x, polygon.points[0].y)
            allSegments.add(Ray(previousPoint!!, polygon.points[0]))
        }
    }
}

private fun createPolygons(): List<Polygon> = (1..polygonNb).mapNotNull {
    val center = Point(random() * vizWidth * .9, random() * vizHeight * .9)
    val points = (1..randomPointsNb).map {
        Point(
            center.x + (random() * (vizWidth / 7)).coerceIn(.0, vizWidth),
            center.y + (random() * (vizWidth / 7)).coerceIn(.0, vizWidth)
        )
    }
    polygonHull(points)
}

fun loop(polygons: List<Polygon>) {
    moveToNextPoint(polygons)
    val sightPolygon = getSightPolygon(allCorners)
    val points = sightPolygon.points

    sightGroupParent.remove(sightGroup)
    sightGroup = newGroup()

    radialGradient.cx = lightPoint.x
    radialGradient.cy = lightPoint.y

    sightGroup.apply {
        path {
            moveTo(points[0].x, points[0].y)
            fill = radialGradient
            stroke = null
            points.forEach { point ->
                lineTo(point.x, point.y)
            }
            lineTo(points[0].x, points[0].y)
        }
    }

    sightGroupParent.add(sightGroup)

    lightGroup.selectElement(circle, fromList) {
        onEnter = {
            element.apply {
                stroke = null
                this.fill = colors.white
                radius = 7.0
                cx = lightPoint.x
                cy = lightPoint.y
            }
            lightGroup.add(element)
        }

        onUpdate = {
            element.cx = lightPoint.x
            element.cy = lightPoint.y
        }

        onExit = {
            lightGroup.remove(element)
        }
    }
}

private fun getSightPolygon(allCorners: List<Point>): Polygon {
    val allAngles = mutableListOf<Angle>()
    allCorners.forEach {
        val rad = atan2(it.y - lightPoint.y, it.x - lightPoint.x)
        allAngles.add(Angle(rad))
        allAngles.add(Angle(rad + .00001))
        allAngles.add(Angle(rad - .00001))
    }

    val intersections = mutableListOf<Intersection>()
    allAngles.forEach { angle ->
        
        val dx = angle.cos
        val dy = angle.sin

        // Ray from center of screen to mouse
        val ray = Ray(lightPoint, Point(lightPoint.x + dx, lightPoint.y + dy))

        // Find CLOSEST intersection
        var closestIntersection: Intersection? = null
        
        allSegments
            .mapNotNull { getIntersection(ray, it)}
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
 * Return the POINT OF INTERSECTION 
 */
private fun getIntersection(ray: Ray, segment: Ray): Intersection? {

    // RAY in parametric: Point + Delta*T1
    val rpx = ray.from.x
    val rpy = ray.from.y
    val rdx = ray.to.x - ray.from.x
    val rdy = ray.to.y - ray.from.y

    // SEGMENT in parametric: Point + Delta*T2
    val spx = segment.from.x
    val spy = segment.from.y
    val sdx = segment.to.x - segment.from.x
    val sdy = segment.to.y - segment.from.y

    // Are they parallel? If so, no intersect
    val rmag = sqrt(rdx * rdx + rdy * rdy)
    val smag = sqrt(sdx * sdx + sdy * sdy)
    if (rdx / rmag == sdx / smag && rdy / rmag == sdy / smag) {
        // Unit vectors are the same.
        return null
    }
    // SOLVE FOR T1 & T2
    // r_px+r_dx*T1 = s_px+s_dx*T2 && r_py+r_dy*T1 = s_py+s_dy*T2
    // ==> T1 = (s_px+s_dx*T2-r_px)/r_dx = (s_py+s_dy*T2-r_py)/r_dy
    // ==> s_px*r_dy + s_dx*T2*r_dy - r_px*r_dy = s_py*r_dx + s_dy*T2*r_dx - r_py*r_dx
    // ==> T2 = (r_dx*(s_py-r_py) + r_dy*(r_px-s_px))/(s_dx*r_dy - s_dy*r_dx)
    val t2 = (rdx * (spy - rpy) + rdy * (rpx - spx)) / (sdx * rdy - sdy * rdx)
    val t1 = (spx + sdx * t2 - rpx) / rdx

    // Must be within parametic whatevers for RAY/SEGMENT
    if (t1 < 0) return null
    if (t2 < 0 || t2 > 1) return null
    
    return Intersection(Point(rpx + rdx * t1, rpy + rdy * t1), t1)
}

private fun posOutsideOf(polygons:List<Polygon>):Point {
    val pos = Point(random() * vizWidth, random() * vizHeight)
    val insidePolygon = polygons.any { it.contains(pos) }
    return if (insidePolygon) 
        posOutsideOf(polygons) else pos
}

private fun moveToNextPoint(polygons:List<Polygon>) {
    val nextPoint = Point(lightPoint.x + xSpeed, lightPoint.y + ySpeed)
    
    fun outsideOfViz(point: Point) =            
            point.x <= .0 ||
            point.x >= vizWidth ||
            point.y <= 0 ||
            point.y >= vizHeight

    val collision = polygons.any { it.contains(nextPoint) } || outsideOfViz(nextPoint) 

    if (collision) {
        randomSpeed()
        moveToNextPoint(polygons)
    } else {
        lightPoint = nextPoint
    }
}

private fun randomSpeed() {
    val angle = random() * PI * 2
    xSpeed = cos(angle) * 3
    ySpeed = sin(angle) * 3
}

