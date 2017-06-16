package io.data2viz.random

import io.data2viz.test.StringSpec
import io.data2viz.voronoi.Diagram
import io.data2viz.voronoi.Site
import kotlin.js.Math

class VoronoiTests : StringSpec() {

    val width = 450.0
    val height = 450.0

    init {
        "clipping"  { launch() }
    }

    fun launch() {
        val vSites = generateRandomSites(200).toTypedArray()
        improvePoints(vSites, 3)
    }

    private fun generateRandomSites(nbSites: Int) =
            (0 until nbSites).map {
                Site(io.data2viz.voronoi.Point(Math.random() * width, Math.random() * width), it)
            }

    private fun improvePoints(sites: Array<Site>, i: Int) {
        fun List<io.data2viz.voronoi.Point>.centroid(): io.data2viz.voronoi.Point {
            var x = 0.0
            var y = 0.0
            forEach {
                x += it.x
                y += it.y
            }
            return io.data2viz.voronoi.Point(x / size, y / size)
        }

        var diagram: Diagram?
        var vSites = sites

        // diagram size is 400x400
        (1..i).forEach {
            diagram = Diagram(vSites, clipEnd = io.data2viz.voronoi.Point(400.0, 400.0))
            vSites = diagram!!.polygons().mapIndexed { index, polygon -> Site(polygon.centroid(), index) }.toTypedArray()
        }

        // diagram size is 450x450
        diagram = Diagram(vSites, clipEnd = io.data2viz.voronoi.Point(width, height))
    }
}
