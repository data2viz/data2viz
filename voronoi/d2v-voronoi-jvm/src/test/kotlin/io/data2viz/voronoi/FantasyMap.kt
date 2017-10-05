package io.data2viz.voronoi

import java.util.*
import io.data2viz.core.Point



val params = Params()


data class Params(
        val npts: Int = 16384, //16384,
        val nbCities: Int = 5,
        val mapWidth: Int = 450,
        val mapHeight: Int = 450,
        val nbMapsDrawedW: Int = 4,
        val nbMapsDrawedH: Int = 2
)


var vSites: List<Site> = emptyList()

fun main(args: Array<String>) {
    vSites = timeAndResult("Points generation") { generatePoints()}
    timeAndResult("improvePoints 3") { improvePoints(3) }
    val triangles = timeAndResult("getDiagramTriangles()") { diagram!!.triangles() }
    println(triangles.size)
}

private fun generatePoints() =
        (0..params.npts - 1).map {
            Site(Point(Math.random() * params.mapWidth, Math.random() * params.mapHeight), it)
        }

var diagram:Diagram? = null

private fun improvePoints(cycles: Int): Unit {

    fun List<Point>.centroid():Point {
        var x = 0.0
        var y = 0.0
        forEach {
            x += it.x
            y += it.y
        }
        return Point(x/size, y/size)
    }

    for (i in 1..cycles) {
        diagram = Diagram(vSites.toTypedArray())
        vSites = diagram!!.polygons().mapIndexed { index,polygon -> Site(polygon.centroid(), index) }
    }
    diagram = Diagram(vSites.toTypedArray(), clipEnd = Point(params.mapWidth.toDouble(), params.mapHeight.toDouble()))
}


fun <T> timeAndResult(msg: String = "", block: () -> T): T {
    val time = Date().getTime()
    val ret = block()
    println("$msg. Execution in ${Date().getTime() - time} ms")
    return ret
}
