package io.data2viz.experiments.fantasymap

import io.data2viz.color.Color
import io.data2viz.color.colors.black
import io.data2viz.color.colors.blue
import io.data2viz.color.colors.red
import io.data2viz.color.colors.rgba
import io.data2viz.color.colors.white
import io.data2viz.core.Point
import io.data2viz.interpolate.interpolateRgbBasis
import io.data2viz.svg.SVGElement
import io.data2viz.svg.svg
import io.data2viz.voronoi.Cell
import io.data2viz.voronoi.Diagram
import io.data2viz.voronoi.Site
import kotlin.browser.window
import kotlin.js.Date
import kotlin.js.Math

private var vSites: Array<Site> = emptyArray()
private var diagram: Diagram? = null

private val GEO_FACES: MutableList<GeoFace> = mutableListOf()
private val GEO_POINTS: MutableList<GeoPoint> = mutableListOf()

private val EPSILON: Double = 1e-5

data class Params(
        val npts: Int = 16384, //16384,
        val nbCities: Int = 5,

        val mountainCount:Int = 100,
        val mountainSize:Float = 0.2f,
        val mountainHeight:Float = 0.15f,
        val seaCount:Int = 10,
        val seaSize:Float = 0.2f,
        val seaDeep: Float = -0.6f,
        val erosion:Float = .08f,

        val mapWidth: Int = 450,
        val mapHeight: Int = 450,
        val nbMapsDrawedW: Int = 4,
        val nbMapsDrawedH: Int = 2,
        var mapCount:Int = 0
)

data class GeoFace(
        val index: Int,
        val centroid: Point,
        val geoPoints: List<GeoPoint>,
        val adjacents: MutableList<GeoFace?> = mutableListOf(),
        var height: Double = EPSILON
) {
    val isOnEdge
        get() = adjacents.contains(null)
    val isSea
        get() = height < 0
    val isLand
        get() = height >= 0

    override operator fun equals(other: Any?) = other is GeoFace && this.index == other.index
    override fun hashCode(): Int = index
}

data class GeoPoint(
        val site: Site,
        val geoFaces: MutableList<GeoFace> = mutableListOf(),
        val adjacents: MutableList<GeoPoint> = mutableListOf(),
        var riverTo: GeoPoint? = null,
        val riversFrom: MutableList<GeoPoint> = mutableListOf(),
        var riverStrength: Double = 0.0,
        var height: Double = EPSILON
) {
    val index
        get() = site.index                          // share index with site

    val isOnEdge
        get() = geoFaces.filter { it.isOnEdge }.size > 1

    override operator fun equals(other: Any?) = other is GeoPoint && this.index == other.index
    override fun hashCode(): Int = index
}

val terrainColors: Array<Color> = arrayOf(Color(0x91b0f0), Color(0xa1c0f0), Color(0xc1e0f0), Color(0x709959),
        Color(0x99b56e), Color(0xc5d188), Color(0xf2eea2), Color(0xf2e096), Color(0xebc17f), Color(0xd1926b),
        Color(0xa1694d), Color(0x82462a), Color(0x732600), Color(0x8f5c45), Color(0xacacac), Color(0xececec))

val seaColors: Array<Color> = arrayOf(Color(0x7190f0), Color(0x91b0f0), Color(0xa1c0f0), Color(0xc1e0f0))
val groundColors: Array<Color> = arrayOf(Color(0xf2eea2)/*Color(0xff0000)*/, Color(0x99b56e), Color(0x99b56e), Color(0x99b56e),
        Color(0x709959), Color(0x709959), Color(0xc5d188), Color(0xc5d188), Color(0xf2eea2), Color(0xf2e096), Color(0xebc17f), Color(0xd1926b),
        Color(0xa1694d), Color(0x82462a), Color(0x732600), Color(0x8f5c45), Color(0xacacac), Color(0xececec))

val mapColors: Array<Color> = arrayOf(Color(0xefefef), Color(0xefefef), Color(0xefefef), Color(0xffffff),
        Color(0xffffff), Color(0xffffff), Color(0xffffff), Color(0xffffff), Color(0xffffff), Color(0xffffff),
        Color(0xffffff), Color(0xffffff), Color(0xffffff), Color(0xffffff), Color(0xffffff), Color(0xffffff))

val colorInterpolateSea = interpolateRgbBasis(seaColors.asList())
val colorInterpolateGround = interpolateRgbBasis(groundColors.asList())

var xOffset = 0
var yOffset = 0

val params = Params()

/**
 * Entry point
 */
fun buildFinalFantasyMap() {
    svg {prepareSVG(params) }
    step0()
}

private fun step0() {
    GEO_POINTS.clear()
    GEO_FACES.clear()

    vSites = generateRandomSites(params.npts).toTypedArray()
    timeAndResult("improveSites") { improveSites(2) }
    timeAndResult("makeMesh") { makeMesh() }
    step1()
}

private fun step1() {
    timeAndResult("addRelief") {
        addRelief(params)
    }
    window.requestAnimationFrame {
        svg {
            /*drawGeofaces(colorScale = ::getMapColor)
            drawCoastLines()*/
        }
        step2()
    }
}

private fun step2() {
    timeAndResult("cleanCoasts") { cleanCoasts() }
    timeAndResult("cleanPlains") { cleanPlains() }
    //xOffset += params.mapWidth
    window.requestAnimationFrame {
        svg {
            /*drawGeofaces(colorScale = ::getMapColor)
            drawCoastLines()*/
        }
        step3()
    }
}

private fun step3() {
    timeAndResult("findRivers") { findRivers() }
    //xOffset += params.mapWidth
    window.requestAnimationFrame {
        svg {
            /*drawGeofaces(colorScale = ::getMapColor)
            drawCoastLines()
            drawRivers(0, 20)*/
        }
        step4()
    }
}

private fun step4() {
    timeAndResult("erode") { erode(params) }
    //xOffset += params.mapWidth
    window.requestAnimationFrame {
        svg {
            /*drawGeofaces(colorScale = ::getMapColor)
            drawCoastLines()
            drawRivers(0, 20)*/
        }
        step5()
    }
}

private fun step5() {

    timeAndResult("cleanRivers") { cleanRivers() }
    window.requestAnimationFrame {
        //fillDepressions()
        //xOffset = 0
        //yOffset += params.mapHeight
        svg {
            drawGeofaces(colorScale = ::getMapColor)
            drawCoastLines()
            drawRivers(6, 20)
        }

        params.mapCount++
        xOffset = params.mapCount%4 * params.mapWidth
        yOffset = (params.mapCount/4)%2 * params.mapHeight
        step0()
    }
}

fun erode(params: Params) {
    val filteredPoints = GEO_POINTS.filter { it.height >= 0 }
    filteredPoints.forEach { it.height = it.height - Math.sqrt(it.riverStrength) * params.erosion }
    filteredPoints.forEach { recomputeGeoFacesHeights(it) }

    cleanCoasts()
    cleanPlains()
}

/**
 * In order to "normalize" our Voronoi cells we place the site at the centroid of each triangle then recompute
 * a new Voronoi diagram based on our new sites.
 * Do it a few cycles (2-3) to greatly improve the geometry.
 */
private fun improveSites(cycles: Int): Unit {
    fun List<Point>.centroid(): Point {
        var x = 0.0
        var y = 0.0
        forEach {
            x += it.x
            y += it.y
        }
        return Point(x / size, y / size)
    }

    (0 until cycles).forEach {
        diagram = Diagram(vSites, clipEnd = Point(params.mapWidth.toDouble(), params.mapHeight.toDouble()))
        vSites = diagram!!.polygons().mapIndexed { index, polygon -> Site(polygon.centroid(), index) }.toTypedArray()
    }
    diagram = Diagram(vSites, clipEnd = Point(params.mapWidth.toDouble(), params.mapHeight.toDouble()))
}

// TODO : sort edges in triangle ?
private fun makeMesh() {
    GEO_POINTS.addAll(vSites.sortedBy { it.index }.map { GeoPoint(it) })

    diagram!!.triangles().forEach { triangle ->

        val faceIndex = GEO_FACES.size
        val centroid = (triangle.a.pos + triangle.b.pos + triangle.c.pos) / 3.0

        val face = GeoFace(faceIndex, centroid, listOf(GEO_POINTS[triangle.a.index], GEO_POINTS[triangle.b.index], GEO_POINTS[triangle.c.index]))

        GEO_FACES.add(face)
        GEO_POINTS[triangle.a.index].geoFaces.add(face)
        GEO_POINTS[triangle.b.index].geoFaces.add(face)
        GEO_POINTS[triangle.c.index].geoFaces.add(face)
    }

    GEO_POINTS.forEach { gp ->
        gp.adjacents.addAll(gp.geoFaces.flatMap { it.geoPoints }.toSet().filter { it != gp })
    }
    GEO_FACES.forEach { gf ->
        val face0 = gf.geoPoints[0].geoFaces.intersect(gf.geoPoints[1].geoFaces).filter { gf.index != it.index }.firstOrNull()
        val face1 = gf.geoPoints[1].geoFaces.intersect(gf.geoPoints[2].geoFaces).filter { gf.index != it.index }.firstOrNull()
        val face2 = gf.geoPoints[2].geoFaces.intersect(gf.geoPoints[0].geoFaces).filter { gf.index != it.index }.firstOrNull()
        gf.adjacents.addAll(listOf(face0, face1, face2))
    }
}

private fun SVGElement.drawAdjacent() {
    (0 until 3).forEach {
        val geoFace = GEO_FACES[(Math.random() * GEO_FACES.size.toDouble()).toInt()]
        val fill = rgba(0, 0, 0, 0)
        path {
            path {
                stroke = black
                strokeWidth = "1"
                setAttribute("fill", fill.toString())

                moveTo(geoFace.geoPoints[0].site.pos.x + xOffset, geoFace.geoPoints[0].site.pos.y + yOffset)
                geoFace.geoPoints.forEach { geoPoint ->
                    lineTo(geoPoint.site.pos.x + xOffset, geoPoint.site.pos.y + yOffset)
                }
                closePath()
            }
        }
        path {
            path {
                stroke = rgba(256, 0, 0, 1)
                strokeWidth = "1"
                setAttribute("fill", fill.toString())

                (0..2).forEach triangles@ { index ->
                    val adjacentFace = geoFace.adjacents[index] ?: return@triangles

                    moveTo(adjacentFace.geoPoints[0].site.pos.x + xOffset, adjacentFace.geoPoints[0].site.pos.y + yOffset)
                    adjacentFace.geoPoints.forEach { geoPoint ->
                        lineTo(geoPoint.site.pos.x + xOffset, geoPoint.site.pos.y + yOffset)
                    }
                    closePath()
                }
            }
        }
    }
}

private fun addRelief(params: Params) {
    makeRelief(params.mountainCount, params.mountainHeight, params.mountainSize)
    makeRelief(params.seaCount, params.seaDeep, params.seaSize)
    GEO_POINTS.forEach { recomputeGeoFacesHeights(it) }
}

private fun makeRelief(nbReliefs: Int, reliefHeight: Float, reliefSizePercentMap: Float) {
    (0..nbReliefs).forEach {
        val reliefPosition = Point(Math.random() * params.mapWidth, Math.random() * params.mapHeight)
        val reliefSizeSquared = Math.pow(reliefSizePercentMap * Math.random() * params.mapWidth, 2.0)
        GEO_POINTS.forEach { geoPoint ->
            val distance = Math.pow(geoPoint.site.x - reliefPosition.x, 2.0) + Math.pow(geoPoint.site.y - reliefPosition.y, 2.0)
            if (distance <= reliefSizeSquared) {
                geoPoint.height += reliefHeight - (reliefHeight * (distance / reliefSizeSquared))
            }
        }
    }
}

private fun SVGElement.drawGeofaces(land: Boolean = true, sea: Boolean = true, colorScale: (GeoFace) -> Color) {
    GEO_FACES.forEach { geoFace ->
        if ((sea && geoFace.isSea) || (land && geoFace.isLand)) {
            path {
                path {
                    val fill = colorScale(geoFace)
                    stroke = fill
                    setAttribute("fill", fill.toString())
                    strokeWidth = "1"

                    moveTo(geoFace.geoPoints[0].site.pos.x + xOffset, geoFace.geoPoints[0].site.pos.y + yOffset)
                    geoFace.geoPoints.forEach { geoPoint ->
                        lineTo(geoPoint.site.pos.x + xOffset, geoPoint.site.pos.y + yOffset)
                    }
                    closePath()
                }
            }
        }
    }
}

private fun SVGElement.drawCells(xOffset: Int, yOffset: Int, edges: MutableList<io.data2viz.voronoi.Edge?>) {
    edges.forEach { edge ->
        if (edge == null) return@forEach
        if (edge.start == null || edge.end == null) return@forEach
        path {
            path {
                val fill = red
                stroke = fill
                strokeWidth = "1"
                setAttribute("fill", fill.toString())

                moveTo(edge.start!!.x + xOffset, edge.start!!.y + yOffset)
                lineTo(edge.end!!.x + xOffset, edge.end!!.y + yOffset)
                closePath()
            }
        }
    }
}

private fun SVGElement.drawSites(xOffset: Int, yOffset: Int, cells: Array<Cell?>) {
    cells.forEach { cell ->
        if (cell == null) return@forEach
        circle {
            transform {
                translate(cell.site.x + xOffset, cell.site.y + yOffset)
            }
            stroke = black
            r = 2
        }
    }
}

/*private fun step4() {

    /*
    erode()
        xOffset += params.mapWidth
        svg {
            drawGeofaces(xOffset, yOffset, arrayOfColors = terrainColors)
            //drawRivers(xOffset, yOffset)
            //drawGeofaces(xOffset, yOffset, land = false, arrayOfColors = terrainColors)
            drawSeacoast(xOffset, yOffset)
        }
        //step5()
     */

    window.requestAnimationFrame {
        erode2()
        erode2()
        erode2()
        erode2()
        erode2()
        erode2()
        erode2()
        erode2()
        erode2()
        //fillDepressions()

        xOffset += params.mapWidth
        svg {
            drawGeofaces(xOffset, yOffset, arrayOfColors = terrainColors)
            //drawRivers(xOffset, yOffset)
            //drawGeofaces(xOffset, yOffset, land = false, arrayOfColors = terrainColors)
            drawSeacoast(xOffset, yOffset)
        }
        step5()
    }

}*/

/*private fun step5() {
    window.requestAnimationFrame {
        cleanRivers(30, 120)
        cleanCoastlines()

        xOffset = 0
        yOffset += params.mapHeight
        svg {
            drawGeofaces(xOffset, yOffset, sea = false, arrayOfColors = terrainColors)
            drawRivers(xOffset, yOffset)
            drawGeofaces(xOffset, yOffset, land = false, arrayOfColors = terrainColors)
            drawSeacoast(xOffset, yOffset)
        }
        step6()
    }

}*/

/*private fun step6() {
    window.requestAnimationFrame {
        relaxRivers()
        xOffset += params.mapWidth
        svg {
            drawGeofaces(xOffset, yOffset, sea = false, arrayOfColors = terrainColors)
            drawRivers(xOffset, yOffset)
            drawGeofaces(xOffset, yOffset, land = false, arrayOfColors = terrainColors)
            drawSeacoast(xOffset, yOffset)
            drawCities(xOffset, yOffset)
        }
        step7()
    }
}*/

/*private fun step7() {
    window.requestAnimationFrame {
        findCities(params)

        xOffset += params.mapWidth
        svg {
            drawGeofaces(xOffset, yOffset, sea = false, arrayOfColors = terrainColors)
            drawRivers(xOffset, yOffset)
            drawGeofaces(xOffset, yOffset, land = false, arrayOfColors = terrainColors)
            drawSeacoast(xOffset, yOffset)
            drawCities(xOffset, yOffset)
        }
        step8()
    }
}*/

/*private fun step8() {
    xOffset += params.mapWidth
    svg {
        val slopecolor = Color(0x797979)
        mesh.GEO_FACES.forEach { geoFace ->
            if (geoFace.height >= 0) {
                geoFace.triangle.forEach { edge ->
                    val adjacentFace = getAdjacentFace(edge, geoFace.index)
                    if (adjacentFace != null) {
                        val direction = (adjacentFace.height - geoFace.height) / (adjacentFace.centroid.x.toDouble() - geoFace.centroid.x.toDouble())
                        if (Math.abs(direction) > 0.07) {
                            line(adjacentFace.centroid.x.toDouble() + xOffset, adjacentFace.centroid.y.toDouble() + yOffset, edge.end.x + xOffset, edge.end.y + yOffset, slopecolor)
                        }
                    }
                }
            }
        }
        //drawGeofaces(xOffset, yOffset, sea = false, arrayOfColors = mapColors)
        drawRivers(xOffset, yOffset, black)
        drawGeofaces(xOffset, yOffset, land = false, arrayOfColors = mapColors)
        drawSeacoast(xOffset, yOffset)
        drawCities(xOffset, yOffset)
    }
}*/

/*private fun SVGElement.drawCities(xOffset: Int, yOffset: Int) {
    cities.forEachIndexed { cityIndex, point ->
        circle {
            transform {
                translate(point.x + xOffset, point.y + yOffset)
            }
            stroke = black
            if (cityIndex < 3) {
                fill = white
                r = 5
            } else {
                fill = black
                r = 3
            }
        }
    }
}*/

private fun SVGElement.prepareSVG(params: Params) {
    width = params.mapWidth * params.nbMapsDrawedW
    height = params.mapHeight * params.nbMapsDrawedH

    rect {
        transform {
            translate(Point(0.0, 0.0))
        }
        width = params.mapWidth * params.nbMapsDrawedW
        height = params.mapHeight * params.nbMapsDrawedH
        fill = white
    }
}

private fun SVGElement.drawCoastLines() {
    GEO_FACES.forEach { geoFace ->
        if (geoFace.isLand) return@forEach
        geoFace.geoPoints.forEachIndexed { index, geoPoint ->
            val adjacentGeoFace = geoFace.adjacents[index] ?: return@forEachIndexed
            if (adjacentGeoFace.isLand) {
                val nextPoint = geoFace.geoPoints[(index + 1) % 3]
                line(geoPoint.site.x + xOffset, geoPoint.site.y + yOffset, nextPoint.site.x + xOffset, nextPoint.site.y + yOffset)
            }
        }
    }
}


private fun SVGElement.drawRivers(minStrength: Int = 0, maxStrength: Int = 10, riverColor: Color = blue) {
    val maxStrengthSquared = maxStrength * maxStrength
    GEO_POINTS.filter { it.height >= 0 }.forEach { geoPoint ->
        if (geoPoint.riverStrength > minStrength && geoPoint.riverTo != null && geoPoint.riverTo!!.height >= 0)
            line {
                x1 = geoPoint.site.x + xOffset
                y1 = geoPoint.site.y + yOffset
                x2 = geoPoint.riverTo!!.site.x + xOffset
                y2 = geoPoint.riverTo!!.site.y + yOffset
                stroke = riverColor
                strokeWidth = if (geoPoint.riverStrength > maxStrengthSquared) "3" else if (geoPoint.riverStrength > maxStrength) "2" else "1"
            }
    }
}

/*private fun testttt() {
    val globalHeight = mutableListOf<Double>()
    GEO_POINTS.forEach { gp ->
        var height = 0.0
        var count = 0
        gp.geoFaces.filter { it.index != gp.index }.forEach { geoFace ->
            geoFace.geoPoints.forEach { geoPoint->
                geoPoint.geoFaces.filter { it.index != geoPoint.index }.forEach { geoFace2 ->
                    geoFace2.geoPoints.forEach { geoPoint2 ->
                        height += geoPoint2.height
                        count ++
                    }
                }
                height += geoPoint.height
                count ++
            }
        }
        globalHeight.add(height/count)
    }
    GEO_POINTS.forEachIndexed { index, gp ->
        gp.height = globalHeight.get(index)
        recomputeGeoFacesHeights(gp)
    }
}*/

/**
 * To simulate coast erosion, just "landify" faces that have 2 inland adjacents
 * and "sea-ify" faces that have 2 insea adjacents
 */
private fun cleanCoasts(maxLoops: Int = 12) {
    var heightsChanged = true
    var currentIteration = 0
    val geoFacesMoved = mutableSetOf<GeoFace>()
    val geoPointsMoved = mutableSetOf<GeoPoint>()

    while (heightsChanged && currentIteration < maxLoops) {
        heightsChanged = false
        GEO_FACES.filterNot { geoFacesMoved.contains(it) }.forEach { geoFace ->
            var pos = 0
            var neg = 0
            geoFace.adjacents.filterNotNull().forEach { if (it.isLand) pos++ else neg++ }
            if (geoFace.isLand && neg >= 2) {
                heightsChanged = true
                geoFace.height = -EPSILON
                geoFacesMoved.add(geoFace)
            } else if (geoFace.isSea && pos >= 2) {
                heightsChanged = true
                geoFace.height = EPSILON
                geoFacesMoved.add(geoFace)
            }
        }
        currentIteration++

        geoFacesMoved.forEach { geoFace ->
            geoFace.geoPoints.forEach { geoPoint ->
                if (geoFace.isLand && geoPoint.height < 0) {
                    geoPoint.height = 0.0
                    geoPointsMoved.add(geoPoint)
                } else if (geoFace.isSea && geoPoint.height >= 0) {
                    geoPoint.height = 0.0
                    geoPointsMoved.add(geoPoint)
                }
            }
        }
        geoPointsMoved.forEach { recomputeGeoFacesHeights(it) }
    }
    println("Total iterations for cleanCoastlines = $currentIteration (LIMIT = $maxLoops)")
}

/**
 * To avoid some near-limit aberrations, move up each points of a "land" geoFace to height = EPSILON
 */
private fun cleanPlains() {
    GEO_FACES.filter { it.isLand }.forEach { geoFace ->
        geoFace.geoPoints.forEach { geoPoint ->
            geoPoint.height = Math.max(geoPoint.height, EPSILON)
        }
    }
}

/**
 * rescale height
 */
private fun recomputeGeoFacesHeights(point: GeoPoint) {
    point.geoFaces.forEach { geoFace ->
        var height = EPSILON
        geoFace.geoPoints.forEach { geoPoint ->
            height += geoPoint.height
        }
        geoFace.height = height / 3
    }
}

/*private fun fillDepressions() {
    val infinity: Double = 999999.0
    val epsilon: Double = 1e-5
    val newHeights: Array<Double> = emptyArray()
    mesh.GEO_FACES.forEachIndexed { faceIndex, geoFace ->
        if (isMapEdge(geoFace)) newHeights[faceIndex] = geoFace.height
        else newHeights[faceIndex] = infinity
    }
    while (true) {
        var changed = false
        mesh.GEO_FACES.forEachIndexed { faceIndex, geoFace ->
            if (newHeights[faceIndex] != geoFace.height) {
                geoFace.triangle.forEach { edge ->
                    val adjacentFaceIndex = getAdjacentFaceIndex(edge, faceIndex)
                    if (adjacentFaceIndex != null) {
                        val adjacentNewHeight = newHeights[adjacentFaceIndex] + epsilon
                        if (geoFace.height >= adjacentNewHeight) {
                            newHeights[faceIndex] = geoFace.height
                            changed = true
//                            println("ici")
                        } else {
                            if (newHeights[faceIndex] > adjacentNewHeight && adjacentNewHeight > geoFace.height) {
                                newHeights[faceIndex] = adjacentNewHeight
                                changed = true
//                                println("la")
                            }
                        }
                    }
                }
            }
        }
        if (!changed) {
            mesh.GEO_FACES.forEachIndexed { faceIndex, geoFace ->
                // only fill lakes
                if (geoFace.height * newHeights[faceIndex] < 0) geoFace.height = newHeights[faceIndex]
            }
            return
        }
    }
}*/

// TODO remove rivers from edges
private fun generateRandomSites(nbSites: Int) =
        (0 until nbSites).map {
            Site(Point(Math.random() * params.mapWidth, Math.random() * params.mapHeight), it)
        }

private fun findRivers() {
    val orderedGeoPoints = GEO_POINTS.filter { it.height >= 0 }.filterNot { it.isOnEdge }.sortedByDescending { it.height }
    orderedGeoPoints.forEach { geoPoint ->
        var lowestAdjacentHeight = geoPoint.height
        var lowestAdjacentPoint: GeoPoint? = null
        geoPoint.adjacents.forEach { adjacent ->
            if (adjacent.height < lowestAdjacentHeight) {
                lowestAdjacentHeight = adjacent.height
                lowestAdjacentPoint = adjacent
            }
        }

        if (lowestAdjacentPoint == null) return@forEach
        geoPoint.riverTo = lowestAdjacentPoint
        lowestAdjacentPoint!!.riversFrom.add(geoPoint)

        geoPoint.riverStrength++
        lowestAdjacentPoint!!.riverStrength += geoPoint.riverStrength
    }
}

// TODO actually a river can "end" on a point which is below "0 height" and so considered as sea... but it may not be drawn a sea
/**
 * Search and remove rivers that "ends" (riverTo == null) not in a sea
 */
fun cleanRivers() {
    val riversToNothing = GEO_POINTS.filter { point ->
        point.height >= 0
                && point.riverTo == null
                && point.riversFrom.isNotEmpty()
    }.toMutableList()

    riversToNothing.forEach { geoPoint ->
        riversToNothing.addAll(geoPoint.riversFrom)
        geoPoint.riversFrom.clear()
        geoPoint.riverStrength = 0.0
    }
}

/*private fun erode() {
    // river erosion
    mesh.GEO_FACES.forEach { geoFace ->
        val rivers = geoFaceIndexToRivers[geoFace.index]
        rivers?.forEach { river ->
            geoFace.height -= river.strength / 70
        }
        geoFace.height = Math.max(-0.6, geoFace.height)
    }
}*/

/*private fun erode2() {
    // weather global erosion
    val newHeights: Array<Double> = emptyArray()
    mesh.GEO_FACES.forEachIndexed { index, geoFace ->
        var erodedHeight = 0.0
        var nbAdjacent = 0
        geoFace.triangle.forEach { edge ->
            val adjacentFace = getAdjacentFace(edge, geoFace.index)
            if (adjacentFace != null) {
                erodedHeight += adjacentFace.height
                nbAdjacent++
            }
        }
        newHeights[index] = (erodedHeight + geoFace.height) / (nbAdjacent + 1)
    }
    mesh.GEO_FACES.map { geoFace -> geoFace.height = newHeights[geoFace.index] }
}*/

/*private fun cleanRivers(minUpRiverStrength: Int, minFinalRiverStrength: Int) {
    rivers = ArrayList(rivers.filter { river -> river.strength > minUpRiverStrength })

    var keeping = 0
    rivers.forEach { river ->
        if (!river.keep && river.strength > minFinalRiverStrength && mesh.GEO_FACES[river.toIndex].height < 0) {
            river.keep = true
            keeping++
        }
    }
    while (true) {
        var changed = false
        keeping = 0
        var keepnew = 0
        rivers.forEach { river ->
            if (river.keep) {
                keeping++
                geoFaceIndexToRivers.get(river.fromIndex)?.forEach { previousRiver ->
                    if (!previousRiver.keep && previousRiver.strength > minUpRiverStrength) {
                        previousRiver.keep = true
                        keepnew++
                        changed = true
                    }
                }
            }
        }
        if (!changed) {
            rivers = ArrayList(rivers.filter { river -> river.keep })
            return
        }
    }
}*/

/*private fun relaxRivers() {
    rivers.reverse()
    rivers.forEach { river ->
        if (river.keep) {
            val adjacentRivers = geoFaceIndexToRivers.get(river.fromIndex)
            if (adjacentRivers?.size == 1) {
                val adjacentRiver = adjacentRivers[0]
                river.fromIndex = adjacentRiver.fromIndex
                adjacentRiver.keep = false
            }
        }
    }
    rivers = ArrayList(rivers.filter { river -> river.keep })
}*/

/*private fun findCities(params: Params) {
    cities.clear()
    (0..params.nbCities - 1).forEach { cityIndex ->
        var bestScore = -99999999.0
        var bestCity: Point? = null
        mesh.GEO_FACES.forEach { geoFace ->
            if (geoFace.height >= 0) {
                var score: Double = 0.0

                // attracted by riversides and sea
                geoFaceIndexToRivers.get(geoFace.index)?.forEach { river ->
                    if (river.keep) score += river.strength.toDouble()
                }
                score /= 10

                // repulsed by near cities
                cities.forEach { cityLocation ->
                    val distance = Math.sqrt(Math.pow(cityLocation.x.toDouble() - geoFace.centroid.x.toDouble(), 2.0) + Math.pow(cityLocation.y.toDouble() - geoFace.centroid.y.toDouble(), 2.0))
                    score += Math.sqrt(distance)
                }

                // attracted by map center
                val distance = Math.sqrt(Math.pow(params.mapWidth / 2 - geoFace.centroid.x.toDouble(), 2.0) + Math.pow(params.mapHeight / 2 - geoFace.centroid.y.toDouble(), 2.0))
                score -= Math.sqrt(distance)

                if (score > bestScore) {
                    bestScore = score
                    bestCity = geoFace.centroid
                }
            }
        }
        if (bestCity != null) {
            cities.add(bestCity!!)
            println("placing city on x=" + bestCity!!.x + " y=" + bestCity!!.y + " score=" + bestScore)
        } else return
    }
}*/

// TODO : le stocker dès le makemesh ?
/*private fun isMapEdge(geoFace: GeoFace): Boolean {
    geoFace.triangle.forEach { edge ->
        if (geoFaceIndexFromEdge.get(edge.toKey())!!.size < 2) return true
    }
    return false
}

private fun getAdjacentFacesIndex(edge: Edge): Array<Int>? {
    val adjacentFaces = geoFaceIndexFromEdge.get(edge.toKey())
    if (adjacentFaces != null && adjacentFaces.size > 1) {
        return adjacentFaces
    }
    return null
}

private fun getAdjacentFaceIndex(edge: Edge, currentFaceIndex: Int): Int? {
    val adjacentFaces = getAdjacentFacesIndex(edge)
    if (adjacentFaces != null) {
        var adjacentFaceIndex = adjacentFaces[0]
        if (adjacentFaceIndex == currentFaceIndex) adjacentFaceIndex = adjacentFaces[1]
        return (adjacentFaceIndex)
    }
    return null
}*/

/*private fun getAdjacentFace(edge: Edge, currentFaceIndex: Int): GeoFace? {
    val adjacentFaceIndex = getAdjacentFaceIndex(edge, currentFaceIndex)
    if (adjacentFaceIndex != null) return mesh.GEO_FACES[adjacentFaceIndex]
    return null
}*/

private fun getMapColor(geoFace: GeoFace): Color {
    return if (geoFace.isSea) colorInterpolateSea(geoFace.height + 1) else colorInterpolateGround(geoFace.height)
}

fun heightColor(geoFace: GeoFace): Color = when {
    geoFace.height < -.40 -> terrainColors[0]
    geoFace.height < -.20 -> terrainColors[1]
    geoFace.height < 0.00 -> terrainColors[2]
    geoFace.height < 0.12 -> terrainColors[3]
    geoFace.height < 0.24 -> terrainColors[4]
    geoFace.height < 0.30 -> terrainColors[5]
    geoFace.height < 0.35 -> terrainColors[6]
    geoFace.height < 0.44 -> terrainColors[7]
    geoFace.height < 0.52 -> terrainColors[8]
    geoFace.height < 0.60 -> terrainColors[9]
    geoFace.height < 0.69 -> terrainColors[10]
    geoFace.height < 0.78 -> terrainColors[11]
    geoFace.height < 0.87 -> terrainColors[12]
    geoFace.height < 0.95 -> terrainColors[13]
    geoFace.height < 0.99 -> terrainColors[14]
    else -> terrainColors[15]
}

fun <T> timeAndResult(msg: String = "", block: () -> T): T {
    val time = Date().getTime()
    val ret = block()
    println("$msg. Execution in ${Date().getTime() - time} ms")
    return ret
}