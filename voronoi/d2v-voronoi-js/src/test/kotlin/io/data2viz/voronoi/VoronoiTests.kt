package io.data2viz.voronoi

import io.data2viz.geom.Point
import kotlin.js.Math

@Suppress("unused")
class VoronoiTests {

    val width = 450.0
    val height = 450.0

    init {
        launch()
    }

    fun launch() {
        val vSites = generateRandomSites(200).toTypedArray()
        improvePoints(vSites, 3)
    }

    private fun generateRandomSites(nbSites: Int) =
            (0 until nbSites).map {
                Site(Point(Math.random() * width, Math.random() * width), it)
            }

    private fun improvePoints(sites: Array<Site>, i: Int) {
        fun List<Point>.centroid(): Point {
            var x = 0.0
            var y = 0.0
            forEach {
                x += it.x
                y += it.y
            }
            return Point(x / size, y / size)
        }

        var diagram: Diagram?
        var vSites = sites

        // diagram size is 400x400
        (1..i).forEach {
            diagram = Diagram(vSites, clipEnd = Point(400.0, 400.0))
            vSites = diagram!!.polygons().mapIndexed { index, polygon -> Site(polygon.centroid(), index) }.toTypedArray()
        }

        // diagram size is 450x450
        diagram = Diagram(vSites, clipEnd = Point(width, height))
    }
}
