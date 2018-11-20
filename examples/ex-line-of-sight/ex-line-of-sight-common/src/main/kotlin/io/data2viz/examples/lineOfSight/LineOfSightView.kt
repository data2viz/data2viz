package io.data2viz.examples.lineOfSight

import io.data2viz.color.*
import io.data2viz.geom.Polygon
import io.data2viz.math.pct
import io.data2viz.viz.Viz
import io.data2viz.viz.viz

internal var vizWidth = 800.0
internal var vizHeight = 800.0

lateinit var model: LineOfSightModel

fun lineOfSightViz(): Viz = viz {
    width = vizWidth
    height = vizHeight

    val backgroundLayer = activeLayer
    val polygonsLayer = layer()
    val lineOfSightLayer = layer()

    model = LineOfSightModel(LineOfSightConfig(width, height))

    fun buildBackground() {
        backgroundLayer.clear()
        backgroundLayer.rect {
            style.fill = 0x131c2b.col
            x = .0
            y = .0
            width = vizWidth
            height = vizHeight
        }
    }

    fun buildPolygons(polygons: List<Polygon>) {
        polygonsLayer.clear()
        with(polygonsLayer) {
            polygons.forEach { polygon ->
                path {
                    style.fill = Colors.Web.black
                    style.stroke = null
                    moveTo(polygon.points.first().x, polygon.points.first().y)
                    (1 until polygon.points.size).forEach {
                        lineTo(polygon.points[it].x, polygon.points[it].y)
                    }
                    closePath()
                }
            }
        }
    }

    buildBackground()
    buildPolygons(model.polygons)

    val radialGradient = lightGradient()

    onFrame {
        lineOfSightLayer.clear()
        model.moveLight()
        radialGradient.center = model.lightPos
        val points = model.getSightPolygon().points
        lineOfSightLayer.path {
            moveTo(points.first().x, points.first().y)
            style.fill = radialGradient
            style.stroke = null
            points.forEach { point ->
                lineTo(point.x, point.y)
            }
            closePath()
        }
    }

    onResize { newWidth, newHeight ->
        vizWidth = newWidth
        vizHeight = newHeight

        width = vizWidth
        height = vizHeight

        model = LineOfSightModel(LineOfSightConfig(width, height))
        buildBackground()
        buildPolygons(model.polygons)
    }
}

private fun lightGradient(): RadialGradient {
    val lightColor = 0xFFFFFF.col
    val fromColor = 0xFFFF00.col
    val endColor = Colors.rgb(0xFFFF00, 0.pct)
    return Colors.Gradient.radial(model.lightPos, .7 * vizWidth)
        .withColor(lightColor, 0.pct)
        .andColor(lightColor, 1.pct)
        .andColor(fromColor, 2.pct)
        .andColor(endColor, 100.pct)
}





