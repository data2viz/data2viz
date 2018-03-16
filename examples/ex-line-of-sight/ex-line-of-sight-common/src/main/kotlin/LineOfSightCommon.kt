package io.data2viz.examples.lineOfSight

import io.data2viz.color.colors
import io.data2viz.core.Point
import io.data2viz.core.Polygon
import io.data2viz.core.polygonHull
import io.data2viz.core.random
import io.data2viz.timer.timer
import io.data2viz.viz.VizContext
import io.data2viz.viz.line
import io.data2viz.viz.selectElement

val width = 800.0
val height = 600.0

lateinit var viz:VizContext

val polygonNb = 4
val randomPointsNb = 10
val randomPoints = List(polygonNb, { mutableListOf<Point>() })
val polygons = mutableListOf<Polygon>()
val allCorners = mutableListOf<Point>()

var xFrom = width / 2
var yFrom = height / 2

fun VizContext.lineOfSightViz() {

    viz = this

    // drawing random points around random locations
    (0 until polygonNb).forEach { i ->
        val x0 = random() * width * .7 + (width * .15)
        val y0 = random() * height * .7 + (height * .15)
        (0 until randomPointsNb).forEach { j ->
            val x = (x0 + (random() * (width / 6))).coerceIn(.0, width)
            val y = (y0 + (random() * (width / 6))).coerceIn(.0, height)
            randomPoints[i].add(Point(x, y))
        }
    }

    // transform points in Polygons using convex hull
    randomPoints.forEach { points ->
        polygons.add(polygonHull(points)!!)
    }

    polygons.forEach { polygon ->
        path {
            fill = null
            stroke = colors.black
            moveTo(polygon.points[0].x, polygon.points[0].y)
            polygon.points.forEachIndexed { index, point ->
                if (index != 0) lineTo(point.x, point.y)
                allCorners.add(point)
            }
            lineTo(polygon.points[0].x, polygon.points[0].y)
        }
    }

    allCorners.add(Point(.0, .0))
    allCorners.add(Point(width, .0))
    allCorners.add(Point(.0, height))
    allCorners.add(Point(width, height))

    timer { now ->
        loop(now)
    }
}

fun loop(now: Double) {
    xFrom += random()*100 - 50
    yFrom += random()*100 - 50

    viz.selectElement(line, allCorners) {
        onEnter = {
            element.apply {
                stroke = colors.black
                x1 = xFrom
                y1 = yFrom
                x2 = datum.x
                y2 = datum.y
            }
            viz.add(element)
        }

        onUpdate = {
            element.x1 = xFrom
            element.y1 = yFrom
        }
    }
}
