package io.data2viz.experiments.fantasymap

import io.data2viz.color.Color
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

data class Point3D(
        var x: Double,
        var y: Double,
        var z: Double
)

data class GeoFace(
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
        val npts: Int = 4000, //16384,
        val mapWidth: Int = 400,
        val mapHeight: Int = 400
)

var sites: Array<Array<Number>> = emptyArray()
var mesh: Mesh = Mesh()
var geoFaceIndexFromEdge: HashMap<Int, Array<Int>> = hashMapOf()

fun doMap(params: Params) {

    improvePoints(3, params)

    val triangles = getDiagramTriangles()
    makeMesh(triangles)

    addRelief(mesh, 40, params, -0.6F, 0.15)
    addRelief(mesh, 300, params, 0.2F, 0.1)

    svg {
        cleanSVG(params)
        drawGeofaces(0)
        drawSeacoast(0)
    }

    fillDepressions()
    svg {
        drawGeofaces(params.mapWidth)
        drawSeacoast(params.mapWidth)
    }

    cleanCoastlines(3)
    svg {
        drawGeofaces(params.mapWidth*2)
        drawSeacoast(params.mapWidth*2)
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
    width = params.mapWidth * 3
    height = params.mapHeight

    rect {
        transform {
            translate(Point(0, 0))
        }
        width = params.mapWidth * 3
        height = params.mapHeight
        fill = white
    }
}
private fun SVGElement.drawSeacoast(offsetX: Int) {
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
                    line(edge.origin.x + offsetX, edge.origin.y, edge.end.x + offsetX, edge.end.y)
                }
            }
        }
    }
}
private fun SVGElement.drawGeofaces(offsetX:Int) {
    mesh.geoFaces.forEach { geoFace ->
        path {
            path {
                var fill: Color = Color(0x91b0f0);
                if (geoFace.height > -.4) fill = Color(0xa1c0f0)
                if (geoFace.height > -.2) fill = Color(0xc1e0f0)
                if (geoFace.height > 0) fill = Color(0x709959)
                if (geoFace.height > 0.12) fill = Color(0x99b56e)
                if (geoFace.height > 0.24) fill = Color(0xc5d188)
                if (geoFace.height > 0.30) fill = Color(0xf2eea2)
                if (geoFace.height > 0.35) fill = Color(0xf2e096)
                if (geoFace.height > 0.44) fill = Color(0xebc17f)
                if (geoFace.height > 0.52) fill = Color(0xd1926b)
                if (geoFace.height > 0.60) fill = Color(0xa1694d)
                if (geoFace.height > 0.69) fill = Color(0x82462a)
                if (geoFace.height > 0.78) fill = Color(0x732600)
                if (geoFace.height > 0.87) fill = Color(0x8f5c45)
                if (geoFace.height > 0.95) fill = Color(0xacacac)
                if (geoFace.height > 0.99) fill = Color(0xececec)

                stroke = fill;
                strokeWidth = "1"
                setAttribute("fill", fill.toString())
                moveTo(geoFace.triangle[0].origin.x + offsetX, geoFace.triangle[0].origin.y)
                geoFace.triangle.forEach { vertex ->
                    lineTo(vertex.origin.x + offsetX, vertex.origin.y)
                }
                closePath()
            }
        }
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
        mesh.geoFaces[triangleIndex] = GeoFace(tri, Point(totalX / 3, totalY / 3))
    }
}
private fun cleanCoastlines(iterations: Int) {
    (1 .. iterations).forEach {
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
                }
            }
        }
    }
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
                geoFace.height = newHeights[faceIndex]
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
private fun addRelief(mesh: Mesh, nbReliefs: Int, params: Params, reliefHeight: Float = 1.0F, reliefSizePercentMap: Double = 0.08) {
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

// TODO : le stocker dès le makemesh ?
private fun isMapEdge(geoFace: GeoFace): Boolean {
    geoFace.triangle.forEach { edge ->
        if (geoFaceIndexFromEdge.get(edge.toKey())!!.size < 2) return true
    }
    return false
}
private fun getAdjacentFace(edge:Edge, currentFaceIndex:Int):GeoFace? {
    val adjacentFaces = geoFaceIndexFromEdge.get(edge.toKey())
    if (adjacentFaces != null && adjacentFaces.size > 1) {
        var adjacentFaceIndex = adjacentFaces[0]
        if (adjacentFaceIndex == currentFaceIndex) adjacentFaceIndex = adjacentFaces[1]
        return(mesh.geoFaces[adjacentFaceIndex])
    }
    return null
}
private fun getAdjacentFaceIndex(edge:Edge, currentFaceIndex:Int):Int? {
    val adjacentFaces = geoFaceIndexFromEdge.get(edge.toKey())
    if (adjacentFaces != null && adjacentFaces.size > 1) {
        var adjacentFaceIndex = adjacentFaces[0]
        if (adjacentFaceIndex == currentFaceIndex) adjacentFaceIndex = adjacentFaces[1]
        return(adjacentFaceIndex)
    }
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
