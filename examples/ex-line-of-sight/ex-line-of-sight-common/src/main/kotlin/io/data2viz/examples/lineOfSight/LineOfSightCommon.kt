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

lateinit var sightGroupParent: Group
lateinit var sightGroup: Group
lateinit var lightGroup: Group


data class Segment(val from: Point, val to: Point)
data class Intersection(val point: Point, val param: Double, var angle: Angle = 0.deg)

val radialGradient by lazy {
    RadialGradient().apply {
        r = .7 * vizWidth
        addColor(.0, colors.yellow)
        addColor(1.0, darkColor)
    }
}

fun VizContext.lineOfSightViz() {
    
    val model = LOSModel(LOSConfig())

    renderBackground()
    renderPolygons(model.polygons)

    sightGroupParent = newGroup()
    sightGroup = newGroup()
    lightGroup = newGroup()

    add(sightGroupParent)
    sightGroupParent.add(sightGroup)
    add(lightGroup)

    
    val light = circle {
        stroke = null
        this.fill = colors.white
        radius = 7.0
        cx = model.lightPoint.x
        cy = model.lightPoint.y

    }

    timer {
        
        model.moveLight()

        radialGradient.cx = model.lightPoint.x
        radialGradient.cy = model.lightPoint.y
        
        light.cx = model.lightPoint.x
        light.cy = model.lightPoint.y

        sightGroupParent.remove(sightGroup)
        sightGroup = newGroup()
        sightGroupParent.add(sightGroup)

        val points = model.getSightPolygon().points
        sightGroup.apply {
            path {
                moveTo(points.first().x, points.first().y)
                fill = radialGradient
                stroke = null
                points.forEach { point ->
                    lineTo(point.x, point.y)
                }
                lineTo(points.first().x, points.first().y)
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


data class LOSConfig(
    val width:Double = vizHeight,
    val height:Double = vizWidth,
    val polygonNb:Int = 15,
    val randomPointsNb:Int = 10
)

class LOSModel(config: LOSConfig) {

    val allCorners: List<Point>
    val allSegments: List<Segment>
    var lightPoint: Point
    var xSpeed: Double = .0
    var ySpeed: Double = .0

    val polygons:List<Polygon>
    
    fun Polygon.segments(): List<Segment> =
        List(points.size) { i -> Segment(points[i], points[ if(i < points.size -2) i + 1 else 0]) }

    init {
        polygons = createPolygons(config.polygonNb, config.randomPointsNb)

        val extent = listOf(Point(.0, .0), Point(vizWidth, .0), Point(vizWidth, vizHeight), Point(.0, vizHeight))
        allCorners = extent + polygons.flatMap { it.points }
        
        val extentPolygon = Polygon(extent)
        
        allSegments = extentPolygon.segments() + polygons.flatMap { it.segments() } 

        newRandomSpeed()
        lightPoint = posOutsideOf(polygons)
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
        val allAngles = allCorners.flatMap {
            val rad = atan2(it.y - lightPoint.y, it.x - lightPoint.x)
            listOf(Angle(rad), Angle(rad + .00001), Angle(rad - .00001))
        }

        val intersections = mutableListOf<Intersection>()
        allAngles.forEach { angle ->

            val dx = angle.cos
            val dy = angle.sin

            // Ray from center of screen to mouse
            val ray = Segment(lightPoint, Point(lightPoint.x + dx, lightPoint.y + dy))

            // Find CLOSEST intersection
            var closestIntersection: Intersection? = null

            allSegments
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
    private fun getIntersection(ray: Segment, segment: Segment): Intersection? {

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

    private fun posOutsideOf(polygons: List<Polygon>): Point {
        val pos = Point(random() * vizWidth, random() * vizHeight)
        val insidePolygon = polygons.any { it.contains(pos) }
        return if (insidePolygon)
            posOutsideOf(polygons) else pos
    }

    fun moveLight() {
        val newPos = Point(lightPoint.x + xSpeed, lightPoint.y + ySpeed)

        fun inViz(point: Point) = (.0..vizWidth).contains(point.x) && (.0..vizHeight).contains(point.y)

        val inPolygonOrOutsideViz = polygons.any { it.contains(newPos) } || !inViz(newPos)

        if (inPolygonOrOutsideViz) {
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
