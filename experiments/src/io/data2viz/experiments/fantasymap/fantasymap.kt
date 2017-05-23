package io.data2viz.experiments.fantasymap

import io.data2viz.color.Color
import io.data2viz.color.colors.black
import io.data2viz.color.colors.blue
import io.data2viz.color.colors.darkgrey
import io.data2viz.color.colors.white
import io.data2viz.core.Point
import io.data2viz.svg.SVGElement
import io.data2viz.svg.svg
import kotlin.js.Math

external fun computeVoronoi(mapWidth: Int, mapHeight: Int, pts: Array<Array<Number>>): Unit
external fun getPolygons(): Array<Array<Array<Double>>>
external fun getDiagramTriangles(): Array<Array<Array<Double>>>

fun Point.toArray(): Array<Number> = arrayOf(this.x, this.y)
fun Array<Double>.toPoint() = Point(this[0], this[1])
fun Array<Double>.toPoint3D() = Point3D(this[0], this[1], 0.0)
fun Point3D.toPoint() = Point(this.x, this.y)
fun Edge.toKey() = (this.origin.x * this.origin.y * this.end.x * this.end.y).toInt()

val terrainColors: Array<Color> = arrayOf(Color(0x91b0f0), Color(0xa1c0f0), Color(0xc1e0f0), Color(0x709959),
        Color(0x99b56e), Color(0xc5d188), Color(0xf2eea2), Color(0xf2e096), Color(0xebc17f), Color(0xd1926b),
        Color(0xa1694d), Color(0x82462a), Color(0x732600), Color(0x8f5c45), Color(0xacacac), Color(0xececec))

val mapColors: Array<Color> = arrayOf(Color(0xefefef), Color(0xefefef), Color(0xefefef), Color(0xffffff),
        Color(0xffffff), Color(0xffffff), Color(0xffffff), Color(0xffffff), Color(0xffffff), Color(0xffffff),
        Color(0xffffff), Color(0xffffff), Color(0xffffff), Color(0xffffff), Color(0xffffff), Color(0xffffff))

data class Point3D(
        var x: Double,
        var y: Double,
        var z: Double
)

data class River(
        var fromIndex: Int,
        var toIndex: Int,
        var strength: Int = 1,
        var keep: Boolean = false
)

data class GeoFace(
        var index: Int,
        var triangle: Array<Edge>,
        var centroid: Point,
        var height: Double = 1e-5
)

data class Mesh(
        var geoFaces: Array<GeoFace> = emptyArray()
)

data class Edge(
        var origin: Point3D,
        var end: Point3D
)

data class Params(
        val npts: Int = 16384, //16384,
        val nbCities: Int = 5,

        val mapWidth: Int = 450,
        val mapHeight: Int = 450,
        val nbMapsDrawedW: Int = 4,
        val nbMapsDrawedH: Int = 2
)

var sites: Array<Array<Number>> = emptyArray()
var cities: ArrayList<Point> = arrayListOf()
val mesh: Mesh = Mesh()
val geoFaceIndexFromEdge: HashMap<Int, Array<Int>> = hashMapOf()
var rivers: ArrayList<River> = arrayListOf()
val geoFaceIndexToRivers: HashMap<Int, ArrayList<River>> = hashMapOf()

fun doMap(params: Params) {

    improvePoints(3, params)

    val triangles = getDiagramTriangles()
    makeMesh(triangles)

    addRelief(10, params, -0.6F, 0.2)
    addRelief(100, params, 0.25F, 0.2)

    var xOffset = 0
    var yOffset = 0
    svg {
        cleanSVG(params)
        drawGeofaces(xOffset, yOffset, arrayOfColors = terrainColors)
        drawSeacoast(xOffset, yOffset)
    }


    findRivers()
    //cleanCoastlines()
    //fillDepressions()

    xOffset += params.mapWidth
    svg {
        drawGeofaces(xOffset, yOffset, arrayOfColors = terrainColors)
        drawSeacoast(xOffset, yOffset)
    }

    erode()

    xOffset += params.mapWidth
    svg {
        drawGeofaces(xOffset, yOffset, arrayOfColors = terrainColors)
        //drawRivers(xOffset, yOffset)
        //drawGeofaces(xOffset, yOffset, land = false, arrayOfColors = terrainColors)
        drawSeacoast(xOffset, yOffset)
    }

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

    relaxRivers()

    xOffset += params.mapWidth
    svg {
        drawGeofaces(xOffset, yOffset, sea = false, arrayOfColors = terrainColors)
        drawRivers(xOffset, yOffset)
        drawGeofaces(xOffset, yOffset, land = false, arrayOfColors = terrainColors)
        drawSeacoast(xOffset, yOffset)
        drawCities(xOffset, yOffset)
    }

    findCities(params)

    xOffset += params.mapWidth
    svg {
        drawGeofaces(xOffset, yOffset, sea = false, arrayOfColors = terrainColors)
        drawRivers(xOffset, yOffset)
        drawGeofaces(xOffset, yOffset, land = false, arrayOfColors = terrainColors)
        drawSeacoast(xOffset, yOffset)
        drawCities(xOffset, yOffset)
    }

    xOffset += params.mapWidth
    svg {
        val slopecolor = Color(0x797979)
        mesh.geoFaces.forEach { geoFace ->
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
}

private fun SVGElement.drawCities(xOffset: Int, yOffset: Int) {
    cities.forEachIndexed { cityIndex, point ->
        circle {
            transform {
                translate(point.x.toDouble() + xOffset, point.y.toDouble() + yOffset)
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
}

/*private fun findAdjacents(triangles: Array<Array<Array<Double>>>) {
    triangles.forEachIndexed { faceIndex1, tri1 ->
        tri1.forEachIndexed { edgeIndex1, tri1Vertex ->
            var found = false
            var faceIndex2 = faceIndex1 + 1
            while (!found && faceIndex2 < triangles.size) {
                val tri2 = triangles.get(faceIndex2)
                tri2.forEachIndexed { edgeIndex2, tri2Vertex ->
                    if (tri1Vertex[0] == tri2Vertex[0] && tri1Vertex[1] == tri2Vertex[1]) {

                        var nextVertex1 = tri1[0]
                        if (edgeIndex1 < tri1.size - 1) nextVertex1 = tri1[edgeIndex1 + 1]

                        var previousVertex2 = tri2[tri2.size - 1]
                        if (edgeIndex2 > 0) previousVertex2 = tri2[edgeIndex2 - 1]

                        if (nextVertex1[0] == previousVertex2[0] && nextVertex1[1] == previousVertex2[1]) {
                            //val adjacents = mesh.geoFaces[faceIndex1].triangle[edgeIndex1].adjacents
                            //adjacents[adjacents.size] = mesh.geoFaces[faceIndex2]
                            found = true
                        }
                    }
                }
                faceIndex2++
            }
        }
    }
}
private fun findAdjacent2() {
    for (faceIndex1 in 0..mesh.geoFaces.size - 1) {
        val geoFace1 = mesh.geoFaces[faceIndex1]
        for (edgeIndex1 in 0..geoFace1.triangle.size - 1) {
            val edge1 = geoFace1.triangle[edgeIndex1]
            var found = false;
            var faceIndex2 = faceIndex1 + 1
            while (!found && faceIndex2 < mesh.geoFaces.size) {
                val geoFace2 = mesh.geoFaces[faceIndex2]
                for (edgeIndex2 in 0..geoFace2.triangle.size - 1) {
                    val edge2 = geoFace2.triangle[edgeIndex2]
                    if (edge1.origin.x == edge2.end.x && edge1.end.x == edge2.origin.x
                            && edge1.origin.y == edge2.end.y && edge1.end.y == edge2.origin.y) {

                        //val adj = edge1.adjacents
                        //adj[adj.size] = geoFace2
                        found = true
                    }
                }
                faceIndex2++
            }
        }
    }
}
private fun findAdjacents3(triangles: Array<Array<Array<Double>>>) {
    for (faceIndex1 in 0 .. triangles.size-1) {
        val tri1 = triangles[faceIndex1]
        for (edgeIndex1 in 0 .. tri1.size-1) {
            val tri1Vertex = tri1[edgeIndex1]
            var found = false
            var faceIndex2 = faceIndex1 + 1
            while (!found && faceIndex2 < triangles.size) {
                val tri2 = triangles.get(faceIndex2)
                for (edgeIndex2 in 0 .. tri2.size-1) {
                    val tri2Vertex = tri2[edgeIndex2]
                    if (tri1Vertex[0] == tri2Vertex[0] && tri1Vertex[1] == tri2Vertex[1]) {

                        var nextVertex1 = tri1[0]
                        if (edgeIndex1 < tri1.size - 1) nextVertex1 = tri1[edgeIndex1 + 1]

                        var previousVertex2 = tri2[tri2.size - 1]
                        if (edgeIndex2 > 0) previousVertex2 = tri2[edgeIndex2 - 1]

                        if (nextVertex1[0] == previousVertex2[0] && nextVertex1[1] == previousVertex2[1]) {
                            //val adjacents = mesh.geoFaces[faceIndex1].triangle[edgeIndex1].adjacents
                            //adjacents[adjacents.size] = mesh.geoFaces[faceIndex2]
                            found = true
                        }
                    }
                }
                faceIndex2++
            }
        }
    }
}
private fun findAdjacents4() {
    for (faceIndex1 in 0..mesh.geoFaces.size - 1) {
        val geoFace1 = mesh.geoFaces[faceIndex1]
        for (edgeIndex1 in 0..geoFace1.triangle.size - 1) {
            val edge = geoFace1.triangle[edgeIndex1]
            //val adjacentEdge = edge.inverse()
            //val adjacentEdge = Edge(edge.end, edge.origin)
            //val adjacentInts:Int? = geoFaceIndexFromEdge.get(adjacentEdge)
            val adjacentInts:Array<Int>? = geoFaceIndexFromEdge.get(edge.toKey())
            adjacentInts?.forEach { adjacentIndex ->
                //edge.adjacents[edge.adjacents.size] = mesh.geoFaces[adjacentIndex]
            }
        }
    }
}*/

private fun SVGElement.cleanSVG(params: Params) {
    width = params.mapWidth * params.nbMapsDrawedW
    height = params.mapHeight * params.nbMapsDrawedH

    rect {
        transform {
            translate(Point(0, 0))
        }
        width = params.mapWidth * params.nbMapsDrawedW
        height = params.mapHeight * params.nbMapsDrawedH
        fill = white
    }
}

private fun SVGElement.drawSeacoast(xOffset: Int, yOffset: Int) {
    /*mesh.geoFaces.forEach { geoFace ->
        geoFace.triangle.forEachIndexed { edgeIndex, edge ->
            edge.adjacents.forEach { adjacent ->
                if (geoFace.height * adjacent.height < 0) {
                    line(edge.origin.x, edge.origin.y, edge.end.x, edge.end.y)
                }
            }
        }
    }*/
    mesh.geoFaces.forEach { geoFace ->
        geoFace.triangle.forEach { edge ->
            val adjacentFaces = geoFaceIndexFromEdge.get(edge.toKey())
            if (adjacentFaces != null && adjacentFaces.size > 1) {
                val adjacent1 = mesh.geoFaces[adjacentFaces[0]]
                val adjacent2 = mesh.geoFaces[adjacentFaces[1]]
                if (adjacent1.height * adjacent2.height < 0) {
                    line(edge.origin.x + xOffset, edge.origin.y + yOffset, edge.end.x + xOffset, edge.end.y + yOffset)
                }
            }
        }
    }
}

private fun SVGElement.drawGeofaces(xOffset: Int, yOffset: Int, land: Boolean = true, sea: Boolean = true, arrayOfColors: Array<Color>) {
    mesh.geoFaces.forEach { geoFace ->
        if ((sea && geoFace.height < 0) || (land && geoFace.height >= 0)) {
            path {
                path {
                    var fill: Color = arrayOfColors[0]
                    if (geoFace.height > -.40) fill = arrayOfColors[1]
                    if (geoFace.height > -.20) fill = arrayOfColors[2]
                    if (geoFace.height > 0.00) fill = arrayOfColors[3]
                    if (geoFace.height > 0.12) fill = arrayOfColors[4]
                    if (geoFace.height > 0.24) fill = arrayOfColors[5]
                    if (geoFace.height > 0.30) fill = arrayOfColors[6]
                    if (geoFace.height > 0.35) fill = arrayOfColors[7]
                    if (geoFace.height > 0.44) fill = arrayOfColors[8]
                    if (geoFace.height > 0.52) fill = arrayOfColors[9]
                    if (geoFace.height > 0.60) fill = arrayOfColors[10]
                    if (geoFace.height > 0.69) fill = arrayOfColors[11]
                    if (geoFace.height > 0.78) fill = arrayOfColors[12]
                    if (geoFace.height > 0.87) fill = arrayOfColors[13]
                    if (geoFace.height > 0.95) fill = arrayOfColors[14]
                    if (geoFace.height > 0.99) fill = arrayOfColors[15]

                    stroke = fill;
                    strokeWidth = "1"
                    setAttribute("fill", fill.toString())
                    moveTo(geoFace.triangle[0].origin.x + xOffset, geoFace.triangle[0].origin.y + yOffset)
                    geoFace.triangle.forEach { vertex ->
                        lineTo(vertex.origin.x + xOffset, vertex.origin.y + yOffset)
                    }
                    closePath()
                }
            }
        }
    }
}

private fun SVGElement.drawRivers(xOffset: Int, yOffset: Int, riverColor: Color = blue) {
    rivers.forEach { river ->
        val riverFrom = mesh.geoFaces[river.fromIndex].centroid
        val riverTo = mesh.geoFaces[river.toIndex].centroid
        line(riverFrom.x.toDouble() + xOffset, riverFrom.y.toDouble() + yOffset, riverTo.x.toDouble() + xOffset, riverTo.y.toDouble() + yOffset, riverColor)
    }
}

private fun makeMesh(triangles: Array<Array<Array<Double>>>) {
    triangles.forEachIndexed { triangleIndex, triangle ->
        val tri: Array<Edge> = emptyArray()
        var totalX: Double = 0.0
        var totalY: Double = 0.0
        triangle.forEachIndexed { index, point ->
            val origin = point.toPoint3D()
            val end: Point3D
            if (index >= triangle.size - 1) {
                end = triangle[0].toPoint3D()
            } else {
                end = triangle[index + 1].toPoint3D()
            }
            val edge = Edge(origin, end)
            tri[index] = edge
            totalX += origin.x
            totalY += origin.y

            val key = edge.toKey()
            var geoFaceIndexes = geoFaceIndexFromEdge.get(key)

            if (geoFaceIndexes == null) geoFaceIndexes = arrayOf(triangleIndex)
            else {
                geoFaceIndexes[geoFaceIndexes.size] = triangleIndex
//                println("triangle num "+triangleIndex)
            }

            geoFaceIndexFromEdge.put(key, geoFaceIndexes)
        }
        mesh.geoFaces[triangleIndex] = GeoFace(triangleIndex, tri, Point(totalX / 3, totalY / 3))
    }
}

private fun cleanCoastlines(iterations: Int = 999) {
    var heightsChanged = true
    var currentIteration = 0

    while (heightsChanged && currentIteration < iterations) {
        heightsChanged = false
        mesh.geoFaces.forEachIndexed { currentGeoFaceIndex, geoFace ->
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
}

private fun fillDepressions() {
    val infinity: Double = 999999.0
    val epsilon: Double = 1e-5
    val newHeights: Array<Double> = emptyArray()
    mesh.geoFaces.forEachIndexed { faceIndex, geoFace ->
        if (isMapEdge(geoFace)) newHeights[faceIndex] = geoFace.height
        else newHeights[faceIndex] = infinity
    }
    while (true) {
        var changed = false;
        mesh.geoFaces.forEachIndexed { faceIndex, geoFace ->
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
            mesh.geoFaces.forEachIndexed { faceIndex, geoFace ->
                // only fill lakes
                if (geoFace.height * newHeights[faceIndex] < 0) geoFace.height = newHeights[faceIndex]
            }
            return
        }
    }
}

private fun generatePoints(params: Params): Array<Array<Number>> {
    val pts: Array<Array<Number>> = emptyArray()
    for (i in 0..params.npts - 1) {
        val pt: Point = Point(Math.random() * params.mapWidth, Math.random() * params.mapHeight)
        pts[i] = pt.toArray()
    }
    return pts
}

private fun improvePoints(polygons: Array<Array<Array<Double>>>): List<Point> {
    return polygons.map { centroid(it) }
}

private fun improvePoints(cycles: Int, params: Params): Unit {
    for (i in 1..cycles) {
        computeVoronoi(params.mapWidth, params.mapHeight, sites)
        val polygons = getPolygons()
        val improvedPoints = listsToArray(improvePoints(polygons))
        sites = improvedPoints
    }
    computeVoronoi(params.mapWidth, params.mapHeight, sites)
}

private fun addRelief(nbReliefs: Int, params: Params, reliefHeight: Float = 1.0F, reliefSizePercentMap: Double = 0.08) {
    (0..nbReliefs).forEach {
        val reliefPosition = Point(Math.random() * params.mapWidth, Math.random() * params.mapHeight)
        val reliefSizeSquared = Math.pow(reliefSizePercentMap * Math.random() * params.mapWidth, 2.0)
        mesh.geoFaces.forEach { geoFace ->
            var averageHeight: Double = 0.0
            geoFace.triangle.forEach { edge ->
                val vertex = edge.origin
                val distance = Math.pow(vertex.x - reliefPosition.x.toDouble(), 2.0) +
                        Math.pow(vertex.y - reliefPosition.y.toDouble(), 2.0)

                var currentHeight = 0.0
                if (distance <= reliefSizeSquared) currentHeight = reliefHeight - (reliefHeight * (distance / reliefSizeSquared))

                vertex.z += currentHeight
                averageHeight += currentHeight
            }
            geoFace.height += (averageHeight / 3.0)
        }
    }
}

private fun findRivers() {
    val orderedGeoFaces: Array<GeoFace> = mesh.geoFaces.copyOf()
    orderedGeoFaces.sortByDescending { it.height }
    orderedGeoFaces.forEachIndexed { geoFaceIndex, geoFace ->
        if (geoFace.height < 0) return@forEachIndexed

        var lowestAdjacentHeight = geoFace.height
        var lowestAdjacent: GeoFace? = null

        geoFace.triangle.forEach { edge ->
            val adjacentFacesIndex = getAdjacentFacesIndex(edge)
            adjacentFacesIndex?.forEach { adjacentFaceIndex ->
                if (mesh.geoFaces[adjacentFaceIndex].height < lowestAdjacentHeight) {
                    lowestAdjacentHeight = mesh.geoFaces[adjacentFaceIndex].height
                    lowestAdjacent = mesh.geoFaces[adjacentFaceIndex]
                }
            }
        }
        if (lowestAdjacent != null) {
            val river = River(geoFace.index, lowestAdjacent!!.index)
            geoFaceIndexToRivers.get(geoFace.index)?.forEach { upRiver ->
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
            }
            rivers.add(river)
        }
    }

    /*val orderedGeoFaces: Array<GeoFace> = mesh.geoFaces.copyOf()
    orderedGeoFaces.sortBy { it.height }
    orderedGeoFaces.forEachIndexed { geoFaceIndex, geoFace ->
        if (geoFace.height > -0.2) {
            if (geoFace.height >= 0) return@forEachIndexed

            var highestAdjacentHeight: Double = 0.0
            var highestAdjacent: GeoFace? = null

            geoFace.triangle.forEach { edge ->
                val adjacentFacesIndex = getAdjacentFacesIndex(edge)
                adjacentFacesIndex?.forEach { adjacentFaceIndex ->
                    if (mesh.geoFaces[adjacentFaceIndex].height > highestAdjacentHeight) {
                        highestAdjacentHeight = mesh.geoFaces[adjacentFaceIndex].height
                        highestAdjacent = mesh.geoFaces[adjacentFaceIndex]
                    }
                }
            }
            if (highestAdjacent != null) {
                val river = River(geoFace.index, highestAdjacent!!.index)
                riverFromGeoFaceIndex.put(highestAdjacent!!.index, river)
                rivers.add(river)
            }
        }
    }

    rivers.forEach { river ->
        val geoFace = mesh.geoFaces[river.toIndex]
        var highestAdjacentHeight: Double = geoFace.height
        var highestAdjacent: GeoFace? = null

        geoFace.triangle.forEach { edge ->
            val adjacentFace:GeoFace? = getAdjacentFace(edge, geoFace.index)
            if (adjacentFace != null) {
                if (adjacentFace.height > highestAdjacentHeight) {
                    highestAdjacentHeight = adjacentFace.height
                    highestAdjacent = adjacentFace
                }
            }
        }
        if (highestAdjacent != null) {
            val river = River(geoFace.index, highestAdjacent!!.index)
            riverFromGeoFaceIndex.put(highestAdjacent!!.index, river)
            rivers.add(river)
        }
    }*/
}

private fun erode() {
    // river erosion
    mesh.geoFaces.forEach { geoFace ->
        val rivers = geoFaceIndexToRivers[geoFace.index]
        rivers?.forEach { river ->
            geoFace.height -= river.strength / 70
        }
        geoFace.height = Math.max(-0.6, geoFace.height)
    }
}
private fun erode2() {
    // weather global erosion
    val newHeights: Array<Double> = emptyArray()
    mesh.geoFaces.forEachIndexed { index, geoFace ->
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
    mesh.geoFaces.map { geoFace -> geoFace.height = newHeights[geoFace.index] }
}

private fun cleanRivers(minUpRiverStrength: Int, minFinalRiverStrength: Int) {
    rivers = ArrayList(rivers.filter { river -> river.strength > minUpRiverStrength })

    var keeping = 0
    rivers.forEach { river ->
        if (!river.keep && river.strength > minFinalRiverStrength && mesh.geoFaces[river.toIndex].height < 0) {
            river.keep = true
            keeping++
        }
    }
    while (true) {
        var changed = false;
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
}

private fun relaxRivers() {
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
}

private fun findCities(params: Params) {
    cities.clear()
    (0..params.nbCities - 1).forEach { cityIndex ->
        var bestScore = -99999999.0
        var bestCity: Point? = null
        mesh.geoFaces.forEach { geoFace ->
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
}

// TODO : le stocker dès le makemesh ?
private fun isMapEdge(geoFace: GeoFace): Boolean {
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
}

private fun getAdjacentFace(edge: Edge, currentFaceIndex: Int): GeoFace? {
    val adjacentFaceIndex = getAdjacentFaceIndex(edge, currentFaceIndex)
    if (adjacentFaceIndex != null) return mesh.geoFaces[adjacentFaceIndex]
    return null
}

private fun centroid(pts: Array<Array<Double>>): Point {
    var x = 0.0
    var y = 0.0
    pts.forEachIndexed { index, pt ->
        x += pt[0]; y += pt[1]
    }
    return Point(x / pts.size, y / pts.size)
}

fun listsToArray(list: List<Point>): Array<Array<Number>> {
    return list.map { it.toArray() }.toTypedArray()
}

fun buildFantasyMap() {
    val params = Params()
    sites = generatePoints(params)
    doMap(params)

    //window.setInterval({if (cycles<5) {doMap(params); cycles++}}, 10)
}
