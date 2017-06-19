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

//private var cities: ArrayList<Point> = arrayListOf()

private var vSites: Array<Site> = emptyArray()

private val GEO_FACES: MutableList<GeoFace> = mutableListOf()
private val GEO_POINTS: MutableList<GeoPoint> = mutableListOf()
private val RIVERS: MutableList<River> = mutableListOf()

//private val ADJACENT_FACES: MutableMap<AdjacentKey, MutableList<GeoFace>> = mutableMapOf()

//private val mesh = VMesh()
//private val geoFaceIndexFromEdge: MutableMap<Int, Array<Int>> = mutableMapOf()
//private val geoFaceIndexToRivers: HashMap<Int, ArrayList<River>> = hashMapOf()

private val EPSILON:Double = 1e-5

private var diagram: Diagram? = null

data class GeoFace(
        val index: Int,
        val gpIndexes: List<Int>,
        val centroid: Point,
        var height: Double = EPSILON
)

data class GeoPoint(
        val site: Site,
        val gfIndexes: MutableList<Int> = mutableListOf(),
        var height: Double = EPSILON
) {
    val index
        get() = site.index                          // share index with site
}

data class River(
        val gpFrom: Int,
        val gpTo: Int
)

/*data class Point3D(
        var x: Double,
        var y: Double,
        var z: Double
)*/

/*data class AdjacentKey(
        val gpIndex1: Int,
        val gpIndex2: Int
)*/

val terrainColors: Array<Color> = arrayOf(Color(0x91b0f0), Color(0xa1c0f0), Color(0xc1e0f0), Color(0x709959),
        Color(0x99b56e), Color(0xc5d188), Color(0xf2eea2), Color(0xf2e096), Color(0xebc17f), Color(0xd1926b),
        Color(0xa1694d), Color(0x82462a), Color(0x732600), Color(0x8f5c45), Color(0xacacac), Color(0xececec))

val mapColors: Array<Color> = arrayOf(Color(0xefefef), Color(0xefefef), Color(0xefefef), Color(0xffffff),
        Color(0xffffff), Color(0xffffff), Color(0xffffff), Color(0xffffff), Color(0xffffff), Color(0xffffff),
        Color(0xffffff), Color(0xffffff), Color(0xffffff), Color(0xffffff), Color(0xffffff), Color(0xffffff))

data class Params(
        val npts: Int = 30000, //16384,
        val nbCities: Int = 5,
        val mapWidth: Int = 450,
        val mapHeight: Int = 450,
        val nbMapsDrawedW: Int = 4,
        val nbMapsDrawedH: Int = 2
)

val colorInterpolate = interpolateRgbBasis(terrainColors.asList())
var xOffset = 0
var yOffset = 0

val params = Params()

/**
 * Entry point
 */
fun buildFinalFantasyMap() {
    vSites = generateRandomSites(params.npts).toTypedArray()
    timeAndResult("improveSites") { improveSites(2) }

    GEO_POINTS.addAll(vSites.sortedBy { it.index }.map { GeoPoint(it) })
    timeAndResult("makeMesh") { makeMesh() }

    step1()
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

    diagram!!.triangles().forEach { triangle ->

        // building all geofaces
        /*cell.halfedges.forEach { edgeIndex ->
            val edge = diagram!!.edges.get(edgeIndex)!!
            if (edge.start == null || edge.end == null) return@forEach

            val adjacentSiteIndex = if (cell.site == edge.right) edge.left.index else if (edge.right != null) edge.right!!.index else null

            val pt0 = Point3D(edge.start!!.x, edge.start!!.y, 0.0)
            val pt1 = Point3D(edge.end!!.x, edge.end!!.y, 0.0)
            val pt2 = Point3D(sitePos.x, sitePos.y, 0.0)

            val edge0 = Edge(pt0, pt1, geoFaceIndex = GEO_FACES.size, adjacentSiteIndex = adjacentSiteIndex)              // this edge is adjacent to another geoFace we don't already know
            val edge1 = Edge(pt1, pt2, geoFaceIndex = GEO_FACES.size, adjacentSiteIndex = cell.site.index)
            val edge2 = Edge(pt2, pt0, geoFaceIndex = GEO_FACES.size, adjacentSiteIndex = cell.site.index)*/

        /*ADJACENT_EDGES.put(AdjacentKey(cell.site.index, cell.site.index, (edgeIndex+1)%cell.halfedges.size), edge1)
        ADJACENT_EDGES.put(AdjacentKey(cell.site.index, cell.site.index, (edgeIndex-1+cell.halfedges.size)%cell.halfedges.size), edge2)
        if (adjacentSiteIndex != null) ADJACENT_EDGES.put(AdjacentKey(cell.site.index, adjacentSiteIndex, edgeIndex), face)*/

        val faceIndex = GEO_FACES.size
        val centroid = (triangle.a.pos + triangle.b.pos + triangle.c.pos) / 3.0

        val face = GeoFace(faceIndex, listOf(triangle.a.index, triangle.b.index, triangle.c.index), centroid)

        GEO_FACES.add(face)
        GEO_POINTS[triangle.a.index].gfIndexes.add(faceIndex)
        GEO_POINTS[triangle.b.index].gfIndexes.add(faceIndex)
        GEO_POINTS[triangle.c.index].gfIndexes.add(faceIndex)

        /*face.gpIndexes.forEachIndexed { index, siteIndex ->
            val siteAIndex = siteIndex
            val siteBIndex = face.gpIndexes[(index + 1) % 3]
            val key = if (siteAIndex < siteBIndex) AdjacentKey(siteAIndex, siteBIndex) else AdjacentKey(siteBIndex, siteAIndex)


            if (ADJACENT_FACES.containsKey(key)) ADJACENT_FACES[key]!!.add(face) else ADJACENT_FACES.put(key, mutableListOf(face))
        }*/
        //}
    }
    /*ADJACENT_EDGES.forEach { entry ->
        if (entry.key.)
    }*/
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

                moveTo(GEO_POINTS[geoFace.gpIndexes[0]].site.pos.x + xOffset, GEO_POINTS[geoFace.gpIndexes[0]].site.pos.y + yOffset)
                geoFace.gpIndexes.forEach { pointIndex ->
                    lineTo(GEO_POINTS[pointIndex].site.pos.x + xOffset, GEO_POINTS[pointIndex].site.pos.y + yOffset)
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
                    val adjacentFace = getAdjacentGeoFace(geoFace, index) ?: return@triangles

                    moveTo(GEO_POINTS[adjacentFace.gpIndexes[0]].site.pos.x + xOffset, GEO_POINTS[adjacentFace.gpIndexes[0]].site.pos.y + yOffset)
                    adjacentFace.gpIndexes.forEach { pointIndex ->
                        lineTo(GEO_POINTS[pointIndex].site.pos.x + xOffset, GEO_POINTS[pointIndex].site.pos.y + yOffset)
                    }
                    closePath()
                }
            }
        }
    }
}

/**
 * Return the adjacent GeoFace along edge
 *
 * @param currentFace : the current GeoFace
 * @param currentPointIndex : first GeoPoint index of the edge shared with adjacent GeoFace
 */
private fun getAdjacentGeoFace(currentFace: GeoFace, currentPointIndex:Int): GeoFace? {
    val gfIndexesA = GEO_POINTS[currentFace.gpIndexes[currentPointIndex]].gfIndexes
    val gfIndexesB = GEO_POINTS[currentFace.gpIndexes[(currentPointIndex+1)%3]].gfIndexes

    val intersect = gfIndexesA.filter { it != currentFace.index }.intersect(gfIndexesB)
    return if (intersect.isEmpty()) null else GEO_FACES[intersect.first()]
}

private fun addRelief(nbReliefs: Int, params: Params, reliefHeight: Float = 1.0F, reliefSizePercentMap: Double = 0.08) {
    (0..nbReliefs).forEach {
        val reliefPosition = Point(Math.random() * params.mapWidth, Math.random() * params.mapHeight)
        val reliefSizeSquared = Math.pow(reliefSizePercentMap * Math.random() * params.mapWidth, 2.0)
        GEO_POINTS.forEach { geoPoint ->
            val distance = Math.pow(geoPoint.site.x - reliefPosition.x, 2.0) + Math.pow(geoPoint.site.y - reliefPosition.y, 2.0)
            var currentHeight = 0.0
            if (distance <= reliefSizeSquared) currentHeight = reliefHeight - (reliefHeight * (distance / reliefSizeSquared))
            geoPoint.height += currentHeight

            geoPoint.gfIndexes.forEach { gfIndex ->
                GEO_FACES[gfIndex].height += currentHeight / 3.0
            }
        }
    }
}

private fun step1() {
    window.requestAnimationFrame {
        svg {
            cleanSVG(params)
            //timeAndResult("drawGeofaces 1") { drawGeofaces(colorScale = ::heightColor) }
            drawGeofaces(colorScale = ::heightColor)
        }
        step2()
    }
}

private fun step2() {
    window.requestAnimationFrame {
        timeAndResult("addRelief") {
            addRelief(10, params, -0.6F, 0.2)
            addRelief(100, params, 0.25F, 0.2)
        }


        xOffset += params.mapWidth
        svg {
            //timeAndResult("drawGeofaces 2") { drawGeofaces(colorScale = ::heightColor) }
            drawGeofaces(colorScale = ::heightColor)
            timeAndResult("drawSeacoast") { drawSeacoast()}
        }
        step3()
    }
}

private fun step3() {
    window.requestAnimationFrame {

        xOffset += params.mapWidth
        svg {
            //timeAndResult("drawGeofaces 3") { drawGeofaces(colorScale = ::getMapColor) }
            drawGeofaces(colorScale = ::getMapColor)
            timeAndResult("drawAdjacent") { drawAdjacent() }
        }
        step4()
    }
}

private fun step4() {
    window.requestAnimationFrame {
        timeAndResult("findRivers") { findRivers() }
        //cleanCoastlines()
        //fillDepressions()
        xOffset += params.mapWidth
        svg {
            //timeAndResult("drawGeofaces 4") { drawGeofaces(colorScale = ::getMapColor) }
            drawGeofaces(colorScale = ::getMapColor)
            timeAndResult("drawRivers") { drawRivers(xOffset, yOffset) }
            //drawGeofaces(xOffset, yOffset, land = false, arrayOfColors = terrainColors)
            //drawSeacoast(xOffset, yOffset)
        }
        //step5()
    }
}

private fun SVGElement.drawGeofaces(land: Boolean = true, sea: Boolean = true, colorScale: (Double) -> Color) {
    GEO_FACES.forEach { geoFace ->
        if ((sea && geoFace.height < 0) || (land && geoFace.height >= 0)) {
            path {
                path {
                    val fill = colorScale(geoFace.height)
                    stroke = fill
                    strokeWidth = "1"
                    setAttribute("fill", fill.toString())

                    moveTo(GEO_POINTS[geoFace.gpIndexes[0]].site.pos.x + xOffset, GEO_POINTS[geoFace.gpIndexes[0]].site.pos.y + yOffset)
                    geoFace.gpIndexes.forEach { pointIndex ->
                        lineTo(GEO_POINTS[pointIndex].site.pos.x + xOffset, GEO_POINTS[pointIndex].site.pos.y + yOffset)
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

private fun SVGElement.cleanSVG(params: Params) {
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

private fun SVGElement.drawSeacoast() {
    GEO_FACES.forEach { geoFace ->
        if (geoFace.height >= 0) return@forEach
        geoFace.gpIndexes.forEachIndexed { index, gpIndex ->
            val geoPoint = GEO_POINTS[gpIndex]
            val adjacentGeoFace = getAdjacentGeoFace(geoFace, index)
            if (adjacentGeoFace != null && adjacentGeoFace.height >= 0) {
                val nextPoint = GEO_POINTS[geoFace.gpIndexes[(index + 1) % 3]]
                line(geoPoint.site.x + xOffset, geoPoint.site.y + yOffset, nextPoint.site.x + xOffset, nextPoint.site.y + yOffset)
            }
        }
    }
}


private fun SVGElement.drawRivers(xOffset: Int, yOffset: Int, riverColor: Color = blue) {
    RIVERS.forEach { river ->
        val riverFrom = GEO_POINTS[river.gpFrom]
        val riverTo = GEO_POINTS[river.gpTo]
        line(riverFrom.site.x + xOffset, riverFrom.site.y + yOffset, riverTo.site.x + xOffset, riverTo.site.y + yOffset, riverColor)
    }
}


/*private fun cleanCoastlines(iterations: Int = 999) {
    var heightsChanged = true
    var currentIteration = 0

    while (heightsChanged && currentIteration < iterations) {
        heightsChanged = false
        mesh.GEO_FACES.forEachIndexed { currentGeoFaceIndex, geoFace ->
            if (geoFace.height >= 0) {
                var countNegativeAdjacents = 0
                var minAdjacentHeight = 1.0
                geoFace.triangle.forEach { edge ->
                    val adjacentFace = getAdjacentFace(edge, currentGeoFaceIndex)
                    if (adjacentFace != null) {
                        if (adjacentFace.height < 0) {
                            countNegativeAdjacents++
                            minAdjacentHeight = Math.min(minAdjacentHeight, adjacentFace.height)
                        }
                    }
                }
                if (countNegativeAdjacents > 1) {
                    geoFace.height = minAdjacentHeight / 2
                    heightsChanged = true
                }
            }
            if (geoFace.height < 0) {
                var countPositiveAdjacents = 0
                var maxAdjacentHeight = -1.0
                geoFace.triangle.forEach { edge ->
                    val adjacentFace = getAdjacentFace(edge, currentGeoFaceIndex)
                    if (adjacentFace != null) {
                        if (adjacentFace.height >= 0) {
                            countPositiveAdjacents++
                            maxAdjacentHeight = Math.max(maxAdjacentHeight, adjacentFace.height)
                        }
                    }
                }
                if (countPositiveAdjacents > 1) {
                    geoFace.height = maxAdjacentHeight / 2
                    heightsChanged = true
                }
            }
        }
        currentIteration++
    }
    println("total iterations for cleanCoastlines = " + currentIteration)
}*/

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

// TODO why not core.point ?
private fun generateRandomSites(nbSites: Int) =
        (0 until nbSites).map {
            Site(Point(Math.random() * params.mapWidth, Math.random() * params.mapHeight), it)
        }

private fun findRivers() {
    val orderedGeoPoints = GEO_POINTS.filter { it.height >= 0 }.sortedByDescending { it.height }
    orderedGeoPoints.forEach { geoPoint ->

        var lowestAdjacentHeight = geoPoint.height
        var lowestAdjacentPoint: GeoPoint? = null

        geoPoint.gfIndexes.forEach { gfIndex ->
            val geoFace = GEO_FACES[gfIndex]
            geoFace.gpIndexes.forEach { gpIndex ->
                if (GEO_POINTS[gpIndex].height < lowestAdjacentHeight) {
                    lowestAdjacentHeight = GEO_POINTS[gpIndex].height
                    lowestAdjacentPoint = GEO_POINTS[gpIndex]
                }
            }
        }

        if (lowestAdjacentPoint != null) {
            val river = River(geoPoint.index, lowestAdjacentPoint!!.index)
            /*geoFaceIndexToRivers.get(geoFace.index)?.forEach { upRiver ->
                river.strength += upRiver.strength
            }
            val downRivers: ArrayList<River>? = geoFaceIndexToRivers.get(lowestAdjacent!!.index)
            if (downRivers == null) {
                geoFaceIndexToRivers.put(lowestAdjacent!!.index, arrayListOf(river))
            } else {
                /*downRivers.forEach { otherRiver ->
                    river.strength += otherRiver.strength
                }*/
                downRivers.add(river)
                geoFaceIndexToRivers.put(lowestAdjacent!!.index, downRivers)
            }*/
            RIVERS.add(river)
        }
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

private fun getMapColor(height: Double): Color {
    return colorInterpolate((height + 0.4) / 1.6)
}

fun heightColor(height: Double): Color = when {
    height < -.40 -> terrainColors[0]
    height < -.20 -> terrainColors[1]
    height < 0.00 -> terrainColors[2]
    height < 0.12 -> terrainColors[3]
    height < 0.24 -> terrainColors[4]
    height < 0.30 -> terrainColors[5]
    height < 0.35 -> terrainColors[6]
    height < 0.44 -> terrainColors[7]
    height < 0.52 -> terrainColors[8]
    height < 0.60 -> terrainColors[9]
    height < 0.69 -> terrainColors[10]
    height < 0.78 -> terrainColors[11]
    height < 0.87 -> terrainColors[12]
    height < 0.95 -> terrainColors[13]
    height < 0.99 -> terrainColors[14]
    else -> terrainColors[15]
}

fun <T> timeAndResult(msg: String = "", block: () -> T): T {
    val time = Date().getTime()
    val ret = block()
    println("$msg. Execution in ${Date().getTime() - time} ms")
    return ret
}