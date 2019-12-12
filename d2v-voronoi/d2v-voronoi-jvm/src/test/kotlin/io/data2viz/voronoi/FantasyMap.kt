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

package io.data2viz.voronoi

import java.util.*
import io.data2viz.geom.Point



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

    fun List<Point>.centroid(): Point {
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
