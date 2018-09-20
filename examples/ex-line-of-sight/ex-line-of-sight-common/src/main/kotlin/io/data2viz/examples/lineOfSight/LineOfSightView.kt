package io.data2viz.examples.lineOfSight

import io.data2viz.color.Color
import io.data2viz.color.RadialGradient
import io.data2viz.color.colors
import io.data2viz.geom.Polygon
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
            fill = Color(0x131c2b)
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
    }

    buildBackground()
    buildPolygons(model.polygons)

    val radialGradient = lightGradient()

    onFrame {
        lineOfSightLayer.clear()
        model.moveLight()
        radialGradient.cx = model.lightPos.x
        radialGradient.cy = model.lightPos.y
        val points = model.getSightPolygon().points
        lineOfSightLayer.path {
            moveTo(points.first().x, points.first().y)
            fill = radialGradient
            stroke = null
            points.forEach { point ->
                lineTo(point.x, point.y)
            }
            closePath()
        }
    }

    onResize { newWidth, newHeight->
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
    val lightColor = Color(0xFFFFFF)
    val fromColor = Color(0xFFFF00)
    val endColor = Color(0xFFFF00, 0f)
    return RadialGradient().apply {
        r = .7 * vizWidth
        addColor(.0, lightColor)
        addColor(.01, lightColor)
        addColor(.02, fromColor)
        addColor(1.0, endColor)
    }
}





