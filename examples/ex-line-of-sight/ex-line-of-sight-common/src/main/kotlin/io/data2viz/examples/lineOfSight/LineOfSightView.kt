package io.data2viz.examples.lineOfSight

import io.data2viz.color.Color
import io.data2viz.color.RadialGradient
import io.data2viz.color.colors
import io.data2viz.core.Polygon
import io.data2viz.timer.timer
import io.data2viz.viz.PathVizElement
import io.data2viz.viz.VizContext

const val vizWidth = 800.0
const val vizHeight = 800.0

val darkColor = Color(0x131c2b)

fun VizContext.lineOfSightViz() {
    
    val model = LineOfSightModel(LineOfSightConfig(vizWidth, vizHeight))

    renderBackground()
    renderPolygons(model.polygons)

    val radialGradient = lightGradient()

    var path:PathVizElement? = null
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

private fun lightGradient(): RadialGradient {
    return RadialGradient().apply {
        r = .7 * vizWidth
        addColor(.0, colors.white)
        addColor(.01, colors.white)
        addColor(.02, colors.yellow)
        addColor(1.0, darkColor)
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
