package io.data2viz.experiments.fantasymap

import io.data2viz.color.colors.steelblue
import io.data2viz.experiments.animate.Point
import io.data2viz.svg.svg
import kotlin.js.Math

external fun launchVoronoi(): Array<Array<Array<Double>>>

external fun setSites(sites: Array<Array<Double>>):Unit

data class Params(
        val npts:Int = 20,//   16384,
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

fun generatePoints(n:Int, extent:Extent):MutableList<Point> {
    val pts = mutableListOf<Point>()
    for (i in 0..n) {
        pts.add(Point(Math.random() * extent.width, Math.random() * extent.height))
    }
    return pts
}

/*fun generateGoodPoints(n:Int, extent:Extent): List<Point> {
    var pts = generatePoints(n, extent);
    pts.sortBy { it.x }
    return improvePoints(pts, 1, extent);
}*/

/*fun voronoi(pts: List<Point>, passNumber: Int, extent: Extent) {
    var w = extent.width/2;
    var h = extent.height/2;
    return d3.voronoi().extent([[-w, -h], [w, h]])(pts);
}*/

/**
 * Build Voronoi graphs from points, then
 */
fun improvePoints(pts: Array<Array<Double>>): List<Point> {
    var res:List<Point> = mutableListOf()
    /*for (i in 0 .. passNumber) {
        //res = voronoi(pts).map { centroid(it) }
    }*/
    return res
}



fun centroid(pts: Array<Array<Double>>): Point {
    var x = 0.0
    var y = 0.0
    pts.forEach { x += it[0]; y += it[1] }
    return Point(x / pts.size, y / pts.size)
}

fun doMap(params: Params, polygons: Array<Array<Array<Double>>>) {

    val svg = svg {
        width = params.mapWidth
        height = params.mapHeight

        polygons.forEach { polygon ->
            polygon.forEachIndexed { index, vertex ->
                circle {
                    transform {
                        translate()
                    }
                    r = 2
                    fill = steelblue
                }

                if (index < polygon.size - 1) {
                    val nextVertex = polygon[index+1]
                    line(vertex[0], vertex[1], nextVertex[0], nextVertex[1])
                }
            }
        }
    }
}


fun buildFantasyMap(){
    println("building fantasy map")

    val params = Params()
    //doMap(params)

    var pts:Array<Array<Double>> = emptyArray()

    for (i in 0 .. params.npts - 1) {
        val pt:Point = Point(Math.random()* params.mapWidth, Math.random() * params.mapHeight)
        val ptToJS: Array<Double> = arrayOf()
        ptToJS[0] = pt.x;
        ptToJS[1] = pt.y
        pts[i] = ptToJS
    }

    setSites(pts)

    println("launch voronoi")

    var polygons = launchVoronoi()

    doMap(params, polygons)

    //val newSites = improvePoints(polygons)
}
