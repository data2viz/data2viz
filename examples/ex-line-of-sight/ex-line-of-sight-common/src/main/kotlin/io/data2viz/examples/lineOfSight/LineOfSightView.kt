package io.data2viz.examples.lineOfSight

import io.data2viz.color.Color
import io.data2viz.color.RadialGradient
import io.data2viz.color.colors
import io.data2viz.core.Polygon
import io.data2viz.timer.timer
import io.data2viz.viz.PathNode
import io.data2viz.viz.Viz

const val vizWidth = 800.0
const val vizHeight = 800.0

fun lineOfSightViz(): Viz = Viz().apply {
    with(root) {


        val model = LineOfSightModel(LineOfSightConfig(vizWidth, vizHeight))

        renderBackground()
        renderPolygons(model.polygons)

        val radialGradient = lightGradient()

        var path: PathNode? = null
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

private fun lightGradient(): RadialGradient {
    val lightColor = Color(0xFFFFFF)
    val fromColor = Color(0xFFFF00)
    val endColor = Color(0xFFFF00, .0)
    return RadialGradient().apply {
        r = .7 * vizWidth
        addColor(.0, lightColor)
        addColor(.01, lightColor)
        addColor(.02, fromColor)
        addColor(1.0, endColor)
    }
}

private fun Viz.renderPolygons(polygons: List<Polygon>) = with(root) {
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

private fun Viz.renderBackground() = with(root) {
    rect {
        fill = Color(0x131c2b)
        x = .0
        y = .0
        width = vizWidth
        height = vizHeight
    }
}
