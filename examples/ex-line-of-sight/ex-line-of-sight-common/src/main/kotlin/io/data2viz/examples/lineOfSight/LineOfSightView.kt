/*
 * Copyright (c) 2018-2019. data2viz sàrl.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 *
 */

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
            fill = 0x131c2b.col
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
                    fill = Colors.Web.black
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

    animation {
        lineOfSightLayer.clear()
        model.moveLight()
        radialGradient.center = model.lightPos
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





