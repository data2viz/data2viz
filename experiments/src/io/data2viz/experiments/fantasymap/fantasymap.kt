package io.data2viz.experiments.fantasymap

import io.data2viz.color.colors.white
import io.data2viz.core.Point
import io.data2viz.svg.svg
import kotlin.browser.window
import kotlin.js.Math

external fun launchVoronoi(): Array<Array<Array<Double>>>
external fun setSites(sites: Array<Array<Number>>):Unit

fun Point.toArray():Array<Number> = arrayOf(this.x, this.y)
fun Array<Double>.toPoint() = Point(this[0], this[1])

data class Params(
        val npts:Int = 200,//   16384,
        val ncities:Int = 15,
        val nterrs:Int = 5,
        val fontSizeRegion:Int = 40,
        val fontSizeCity:Int = 25,
        val fontSizeTown:Int = 20,

        val mapWidth:Int = 400,
        val mapHeight:Int = 400,

        val extent:Extent = Extent()
)

data class Extent(
        val width:Int = 1,
        val height:Int = 1
)

fun listsToArray(list:List<Point>): Array<Array<Number>> {
    return list.map {it.toArray()}.toTypedArray()
}

fun generatePoints(params:Params): Array<Array<Number>> {
    val pts:Array<Array<Number>> = emptyArray()
    for (i in 0 .. params.npts - 1) {
        val pt:Point = Point(Math.random()* params.mapWidth, Math.random() * params.mapHeight)
        pts[i] = pt.toArray()
    }
    return pts
}

fun improvePoints(polygons: Array<Array<Array<Double>>>): List<Point> {
    return polygons.map { centroid(it) }
}

fun centroid(pts: Array<Array<Double>>): Point {
    var x = 0.0
    var y = 0.0
    pts.forEachIndexed { index, pt ->
        x += pt[0]; y += pt[1]
    }
    return Point(x / pts.size, y / pts.size)
}

fun doMap(params: Params) {

    val polygons = launchVoronoi()

    val svg = svg {

        width = params.mapWidth
        height = params.mapHeight

        rect {
            transform {
                translate(Point(0,0))
            }
            width = params.mapWidth
            height = params.mapHeight
            fill = white
        }

        polygons.forEach { polygon ->
            polygon.forEachIndexed { index, vertex ->
                if (index < polygon.size - 1) {
                    val nextVertex = polygon[index+1]
                    line(vertex[0], vertex[1], nextVertex[0], nextVertex[1])
                }
            }
        }
    }

    setSites(listsToArray(improvePoints(polygons)))
}


fun buildFantasyMap(){
    println("building fantasy map")

    val params = Params()

    setSites(generatePoints(params))
    doMap(params)

    window.setInterval({doMap(params)}, 10)
}
