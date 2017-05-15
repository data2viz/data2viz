package io.data2viz.experiments.fantasymap

import io.data2viz.color.Color
import io.data2viz.color.colors.black
import io.data2viz.color.colors.blue
import io.data2viz.color.colors.darkblue
import io.data2viz.color.colors.darkgreen
import io.data2viz.color.colors.green
import io.data2viz.color.colors.grey
import io.data2viz.color.colors.lightblue
import io.data2viz.color.colors.lightgoldenrodyellow
import io.data2viz.color.colors.red
import io.data2viz.color.colors.saddlebrown
import io.data2viz.color.colors.sandybrown
import io.data2viz.color.colors.white
import io.data2viz.core.Point
import io.data2viz.svg.SVGElement
import io.data2viz.svg.svg
import kotlin.js.Math

external fun computeVoronoi(mapWidth: Int, mapHeight: Int, pts: Array<Array<Number>>): Unit
external fun getPolygons(): Array<Array<Array<Double>>>
external fun getDiagram(): Diagram
external fun getDiagramTriangles(): Array<Array<Array<Double>>>

/*external fun getEdgesNumber():Int
external fun hasEdge(edgeNumber:Int):Boolean
external fun getEdgePoints(edgeNumber:Int):Array<Array<Double>>
external fun getEdgeRight(edgeNumber:Int):Array<Double>
external fun getEdgeLeft(edgeNumber:Int):Array<Double>*/

fun Point.toArray(): Array<Number> = arrayOf(this.x, this.y)
fun Array<Double>.toPoint() = Point(this[0], this[1])
fun Array<Double>.toPoint3D() = Point3D(this[0], this[1], 0.0)
fun Point3D.toPoint() = Point(this.x, this.y)

data class Point3D(
        var x: Double,
        var y: Double,
        var z: Double
)

data class VoronoiEdge(
        var firstPoint: Array<Double> = emptyArray(),
        var secondPoint: Array<Double> = emptyArray(),
        var left: Array<Double> = emptyArray(),
        var right: Array<Double> = emptyArray()
)

data class Vertex(
        var position: Point,
        var adjacents: ArrayList<Point> = ArrayList(),
        var triangles: ArrayList<Point> = ArrayList()
)

data class Mesh(
        var geoFaces: Array<GeoFace> = emptyArray()
)

data class Edge(
        var origin: Point3D,
        var end: Point3D,
        var adjacents: Array<GeoFace> = emptyArray()
)

data class GeoFace(
        var triangle: Array<Edge>,
        var centroid: Point,
        var height: Double = 1e-5
)

fun createFrom(edge: VoronoiEdge, any: dynamic) {
    edge.firstPoint = any[0]
    edge.secondPoint = any[1]
    edge.left = any.left
    edge.right = any.right
}

data class Diagram(
        val edges: Array<Any>,
        var cells: Array<Any>
)

data class Params(
        val npts: Int = 16384, //16384,
        val ncities: Int = 15,
        val nterrs: Int = 5,
        val fontSizeRegion: Int = 40,
        val fontSizeCity: Int = 25,
        val fontSizeTown: Int = 20,

        val mapWidth: Int = 1000,
        val mapHeight: Int = 800,

        val extent: Extent = Extent()
)

data class Extent(
        val width: Int = 1,
        val height: Int = 1
)

var sites: Array<Array<Number>> = emptyArray()
var mesh: Mesh = Mesh()

var cycles: Int = 0


/*fun makeMesh(): HashMap<Point, Vertex> {

    var diagram:Diagram = getDiagram()

    mesh = hashMapOf()

    for (index:Int in 0 .. diagram.edges.size-1) {
        if (diagram.edges[index] == undefined) continue

        val edge: VoronoiEdge = VoronoiEdge()
        createFrom(edge, diagram.edges[index])

        val edgeStartVertex: Point = (edge.firstPoint).toPoint()
        val edgeEndVertex: Point = (edge.secondPoint).toPoint()

        val edgeLeftSite: Point = (edge.left).toPoint()
        var edgeRightSite: Point? = null
        if (edge.right != undefined) edgeRightSite = (edge.right).toPoint()


        var startVertex:Vertex? = mesh.get(edgeStartVertex)
        if (startVertex == null) {
            startVertex = Vertex(edgeStartVertex)
            mesh.put(edgeStartVertex, startVertex)
        }
        startVertex.adjacents.add(edgeEndVertex)
        startVertex.triangles.add(edgeLeftSite)
        if (edgeRightSite != null) startVertex.triangles.add(edgeRightSite)


        var endVertex:Vertex? = mesh.get(edgeEndVertex)
        if (endVertex == null) {
            endVertex = Vertex(edgeEndVertex)
            mesh.put(edgeEndVertex, endVertex)
        }
        endVertex.adjacents.add(edgeStartVertex)
        endVertex.triangles.add(edgeLeftSite)
        if (edgeRightSite != null) endVertex.triangles.add(edgeRightSite)

        /*if (!mesh.containsKey(edgeEndVertex)) {
            val vert1: Vertex = Vertex(edgeEndVertex)
            vert1.adjacents.add(edgeStartVertex)
            vert1.triangles.add(edgeLeftSite)
            if (edgeRightSite != null) vert1.triangles.add(edgeRightSite)
            mesh.put(edgeStartVertex, vert1)
        }*/
    }

    /*var mesh = {
        pts: pts,
        diagram: diagram,
        vxs: vxs,
        adj: adj,
        tris: tris,
        edges: diagram.edges,
        //extent: extent
    }
    mesh.map = function (f) {
        var mapped = vxs.map(f);
        mapped.mesh = mesh;
        return mapped;
    }
    return mesh;*/
    return mesh
}*/

fun listsToArray(list: List<Point>): Array<Array<Number>> {
    return list.map { it.toArray() }.toTypedArray()
}

fun generatePoints(params: Params): Array<Array<Number>> {
    val pts: Array<Array<Number>> = emptyArray()
    for (i in 0..params.npts - 1) {
        val pt: Point = Point(Math.random() * params.mapWidth, Math.random() * params.mapHeight)
        pts[i] = pt.toArray()
    }
    return pts
}

fun improvePoints(polygons: Array<Array<Array<Double>>>): List<Point> {
    return polygons.map { centroid(it) }
}

fun improvePoints(cycles: Int, params: Params): Unit {
    for (i in 1..cycles) {
        computeVoronoi(params.mapWidth, params.mapHeight, sites)
        val polygons = getPolygons()
        val improvedPoints = listsToArray(improvePoints(polygons))
        sites = improvedPoints
    }
    computeVoronoi(params.mapWidth, params.mapHeight, sites)
}

fun centroid(pts: Array<Array<Double>>): Point {
    var x = 0.0
    var y = 0.0
    pts.forEachIndexed { index, pt ->
        x += pt[0]; y += pt[1]
    }
    return Point(x / pts.size, y / pts.size)
}

fun addRelief(mesh: Mesh, nbReliefs: Int, params: Params, reliefHeight: Float = 1.0F, reliefSizePercentMap: Double = 0.08) {
    (0..nbReliefs).forEach {
        val reliefPosition = Point(Math.random() * params.mapWidth, Math.random() * params.mapHeight)
        val reliefSizeSquared = Math.pow(reliefSizePercentMap * Math.random() * params.mapWidth, 2.0)
        mesh.geoFaces.forEach { geoFace ->
            var averageHeight: Double = 0.0
            geoFace.triangle.forEach { edge ->
                var vertex = edge.origin
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

fun doMap(params: Params) {

    improvePoints(3, params)

    val polygons = getPolygons()
    val triangles = getDiagramTriangles()



    makeMesh(triangles)

    var cycles: Int = 0

    // 29646 ms pour 16K sites
    //findAdjacents(triangles)

    // 65363 ms pour 16K sites
    //findAdjacent2()

    // 22580 ms pour 16K sites
    findAdjacents3(triangles)

    println(cycles)

    addRelief(mesh, 30, params)
    addRelief(mesh, 5, params, -1.0F, 0.33)

    cleanCoastlines()

    var svg = svg {

        width = params.mapWidth
        height = params.mapHeight

        rect {
            transform {
                translate(Point(0, 0))
            }
            width = params.mapWidth
            height = params.mapHeight
            fill = white
        }

        /*mesh.geoFaces.forEach { geoFace ->
            circle {
                transform {
                    translate(Point(geoFace.centroid.x, geoFace.centroid.y))
                }
                r = 2
                if (geoFace.height >= 0) {
                    fill = Color(0xff0000, geoFace.height.toFloat() / 2)
//                    fill = Color(0xff0000)
                } else {
                    fill = Color(0x0000ff, -geoFace.height.toFloat() / 2)
//                    fill = Color(0x0000ff)
                }
            }
        }*/

        drawGeofaces()


        /*sites.forEachIndexed { index, site ->
            circle {
                transform {
                    translate(Point(site[0],site[1]))
                }
                r = 3
                fill = green
            }
        }*/

        /*polygons.forEach { polygon ->
            polygon.forEachIndexed { index, vertex ->
                if (index < polygon.size - 1) {
                    val nextVertex = polygon[index+1]
                    line(vertex[0], vertex[1], nextVertex[0], nextVertex[1])
                }
            }
        }*/

        /*mesh.forEach { meshEntry ->
            var vert:Vertex = meshEntry.value
            vert.triangles.forEachIndexed { index, triangleSummit ->
                if (index < vert.triangles.size - 1) {
                    val currentSummit = vert.triangles[index]
                    val nextSummit:Point = vert.triangles[index+1]
                    line(currentSummit.x, currentSummit.y, nextSummit.x, nextSummit.y, red)
                }
            }

            /*vert.adjacents.forEachIndexed { index, triangleSummit ->
                val currentSummit = vert.adjacents[index]
                val nextSummit: Point? = mesh.get(vert.position)?.position
                if (nextSummit != null)
                    line(currentSummit.x, currentSummit.y, nextSummit.x, nextSummit.y, blue)
            }*/
        }*/

        /*var triangleStroke:Color = Color(0x0000ff, 0.1F)
        triangles.forEach { triangle ->
            triangle.forEachIndexed { index, vertex ->
                var nextVertex = triangle[index+1]
                if (index >= triangle.size-1) {
                    nextVertex = triangle[0]
                }
                line(vertex[0], vertex[1], nextVertex[0], nextVertex[1], triangleStroke)
            }
        }*/

        /*val triangleStroke: Color = Color(0x0000ff, 0.1F)
        mesh.geoFaces.forEach { geoFace ->
            geoFace.triangle.forEachIndexed { index, edge ->
                line(edge.origin.x, edge.origin.y, edge.end.x, edge.end.y, triangleStroke)
            }
        }*/

        drawSeacoast()
    }

    /*for (index in 0 .. 10) {
        val pt: Point = Point(Math.random() * params.mapWidth, Math.random() * params.mapHeight)
        improvedPoints.set(improvedPoints.size, pt.toArray())
    }*/
}


private fun findAdjacents(triangles: Array<Array<Array<Double>>>) {
    triangles.forEachIndexed { faceIndex1, tri1 ->
        tri1.forEachIndexed { edgeIndex1, tri1Vertex ->
            var found = false
            var faceIndex2 = faceIndex1 + 1
            while (!found && faceIndex2 < triangles.size) {
                val tri2 = triangles.get(faceIndex2)
                tri2.forEachIndexed { edgeIndex2, tri2Vertex ->
                    cycles++
                    if (tri1Vertex[0] == tri2Vertex[0] && tri1Vertex[1] == tri2Vertex[1]) {

                        var nextVertex1 = tri1[0]
                        if (edgeIndex1 < tri1.size - 1) nextVertex1 = tri1[edgeIndex1 + 1]

                        var previousVertex2 = tri2[tri2.size - 1]
                        if (edgeIndex2 > 0) previousVertex2 = tri2[edgeIndex2 - 1]

                        if (nextVertex1[0] == previousVertex2[0] && nextVertex1[1] == previousVertex2[1]) {
                            val adjacents = mesh.geoFaces[faceIndex1].triangle[edgeIndex1].adjacents
                            adjacents[adjacents.size] = mesh.geoFaces[faceIndex2]
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
                val geoFace2 = mesh.geoFaces.get(faceIndex2)
                for (edgeIndex2 in 0..geoFace2.triangle.size - 1) {
                    cycles++
                    val edge2 = geoFace2.triangle[edgeIndex2]
                    if (edge1.origin.x == edge2.end.x && edge1.end.x == edge2.origin.x
                            && edge1.origin.y == edge2.end.y && edge1.end.y == edge2.origin.y) {

                        val adj = mesh.geoFaces.get(faceIndex1).triangle.get(edgeIndex1).adjacents
                        adj[adj.size] = mesh.geoFaces.get(faceIndex2)
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
                    found = b(tri2, edgeIndex2, tri1Vertex, tri1, edgeIndex1, faceIndex1, faceIndex2, found)
                }
                faceIndex2++
            }
        }
    }
}

private fun b(tri2: Array<Array<Double>>, edgeIndex2: Int, tri1Vertex: Array<Double>, tri1: Array<Array<Double>>, edgeIndex1: Int, faceIndex1: Int, faceIndex2: Int, found: Boolean): Boolean {
    var found1 = found
    val tri2Vertex = tri2[edgeIndex2]
    if (tri1Vertex[0] == tri2Vertex[0] && tri1Vertex[1] == tri2Vertex[1]) {

        var nextVertex1 = tri1[0]
        if (edgeIndex1 < tri1.size - 1) nextVertex1 = tri1[edgeIndex1 + 1]

        var previousVertex2 = tri2[tri2.size - 1]
        if (edgeIndex2 > 0) previousVertex2 = tri2[edgeIndex2 - 1]

        if (nextVertex1[0] == previousVertex2[0] && nextVertex1[1] == previousVertex2[1]) {
            val adjacents = mesh.geoFaces[faceIndex1].triangle[edgeIndex1].adjacents
            adjacents[adjacents.size] = mesh.geoFaces[faceIndex2]
            found1 = true
        }
    }
    return found1
}

private fun SVGElement.drawSeacoast() {
    mesh.geoFaces.forEach { geoFace ->
        geoFace.triangle.forEachIndexed { edgeIndex, edge ->
            edge.adjacents.forEach { adjacent ->
                if (geoFace.height * adjacent.height < 0) {
                    line(edge.origin.x, edge.origin.y, edge.end.x, edge.end.y)
                }
            }
        }
    }
}

private fun SVGElement.drawGeofaces() {
    mesh.geoFaces.forEach { geoFace ->
        path {
            path {
                var fill: Color = darkblue;
                if (geoFace.height > -.3) fill = lightblue
                if (geoFace.height > 0) fill = lightgoldenrodyellow
                if (geoFace.height > 0.05) fill = green
                if (geoFace.height > 0.2) fill = darkgreen
                if (geoFace.height > 0.4) fill = sandybrown
                if (geoFace.height > 0.6) fill = grey
                if (geoFace.height > 0.8) fill = white

                stroke = fill;
                strokeWidth = "1"
                setAttribute("fill", fill.toString())
                moveTo(geoFace.triangle[0].origin.x, geoFace.triangle[0].origin.y)
                geoFace.triangle.forEach { vertex ->
                    lineTo(vertex.origin.x, vertex.origin.y)
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
        }
        mesh.geoFaces[triangleIndex] = GeoFace(tri, Point(totalX / 3, totalY / 3))
    }
}

fun cleanCoastlines() {
    mesh.geoFaces.forEach { geoFace ->
        geoFace.triangle.forEach { edge ->

            edge.adjacents.forEach { adjacentFace ->

            }
        }
    }
}

fun isnearedge(vertex: Array<Double>, params: Params): Boolean {
    val x = vertex[0]
    val y = vertex[1]
    return x < 0.05 * params.mapWidth || x > 0.95 * params.mapWidth || y < 0.05 * params.mapHeight || y > 0.95 * params.mapHeight;
}

fun buildFantasyMap() {
    println("building fantasy map")

    val params = Params()

    sites = generatePoints(params)
    doMap(params)

    //window.setInterval({if (cycles<5) {doMap(params); cycles++}}, 10)
}
