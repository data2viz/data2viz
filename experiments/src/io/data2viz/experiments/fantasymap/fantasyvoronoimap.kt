//package io.data2viz.experiments.fantasymap
//
//import io.data2viz.color.Color
//import io.data2viz.color.colors.black
//import io.data2viz.color.colors.blue
//import io.data2viz.color.colors.white
//import io.data2viz.core.Point
//import io.data2viz.svg.SVGElement
//import io.data2viz.svg.svg
//import io.data2viz.voronoi.Diagram
//import io.data2viz.voronoi.Site
//import io.data2viz.voronoi.Triangle
//import kotlin.browser.window
//import kotlin.js.Date
//import kotlin.js.Math
//
//private var vSites: Array<Site> = emptyArray()
//private var sites: Array<Array<Number>> = emptyArray()
//private var cities: ArrayList<Point> = arrayListOf()
//private val mesh: Mesh = Mesh()
//
////private val mesh = VMesh()
//private val geoFaceIndexFromEdge: HashMap<Int, Array<Int>> = hashMapOf()
//private var rivers: ArrayList<River> = arrayListOf()
//private val geoFaceIndexToRivers: HashMap<Int, ArrayList<River>> = hashMapOf()
//var diagram: Diagram? = null
//
////data class VMesh(
////        var geoFaces: Array<VGeoFace> = emptyArray()
////)
//
//private val params = Params()
///**
// * Entry point
// */
//fun buildVoronoiFantasyMap() {
//    timeAndResult("Generate ${params.npts} as points") { vSites = generatePoints().toTypedArray() }
//    timeAndResult("improvePoints 3") { improvePoints(3) }
//    val triangles = timeAndResult("getDiagramTriangles()") { diagram!!.triangles() }
//    timeAndResult("makeMesh") { makeMesh(triangles) }
////    timeAndResult("addRelief") {
////        addRelief(10, params, -0.6F, 0.2)
////        addRelief(100, params, 0.25F, 0.2)
////    }
////    step1()
//}
//
//private fun makeMesh(triangles: List<Triangle>) {
//    triangles.forEachIndexed { triangleIndex, triangle ->
//        val tri: Array<Edge> = emptyArray()
//        var totalX: Double = 0.0
//        var totalY: Double = 0.0
//
//
////        triangle.forEachIndexed { index, point ->
////            val origin = point.toPoint3D()
////            val end = if (index >= triangle.size - 1) {
////                triangle[0].toPoint3D()
////            } else {
////                triangle[index + 1].toPoint3D()
////            }
////
////            val edge = Edge(origin, end)
////            tri[index] = edge
////            totalX += origin.x
////            totalY += origin.y
////
////            val key = edge.toKey()
////            var geoFaceIndexes = geoFaceIndexFromEdge.get(key)
////
////            if (geoFaceIndexes == null) geoFaceIndexes = arrayOf(triangleIndex)
////            else {
////                geoFaceIndexes[geoFaceIndexes.size] = triangleIndex
////                println("triangle num "+triangleIndex)
////            }
////
////            geoFaceIndexFromEdge.put(key, geoFaceIndexes)
////        }
////        mesh.geoFaces[triangleIndex] = GeoFace(triangleIndex, tri, Point(totalX / 3, totalY / 3))
//    }
//}
//
//
//private fun step1() {
//
//    window.requestAnimationFrame {
//        timeAndResult("svg1") {
//            svg {
//                cleanSVG(params)
//                drawGeofaces(xOffset, yOffset, arrayOfColors = terrainColors)
//                drawSeacoast(xOffset, yOffset)
//            }
//
//        }
////        step2()
//    }
//}
//
//private fun step2() {
//    window.requestAnimationFrame {
//        findRivers()
//        //cleanCoastlines()
//        //fillDepressions()
//
//        xOffset += params.mapWidth
//        svg {
//            drawGeofaces(xOffset, yOffset, arrayOfColors = terrainColors)
//            drawSeacoast(xOffset, yOffset)
//        }
//        step3()
//    }
//}
//
//private fun step3() {
//    window.requestAnimationFrame {
//        erode()
//        xOffset += params.mapWidth
//        svg {
//            drawGeofaces(xOffset, yOffset, arrayOfColors = terrainColors)
//            //drawRivers(xOffset, yOffset)
//            //drawGeofaces(xOffset, yOffset, land = false, arrayOfColors = terrainColors)
//            drawSeacoast(xOffset, yOffset)
//        }
//        step4()
//    }
//}
//
//private fun step4() {
//    window.requestAnimationFrame {
//        erode2()
//        erode2()
//        erode2()
//        erode2()
//        erode2()
//        erode2()
//        erode2()
//        erode2()
//        erode2()
//        //fillDepressions()
//
//        xOffset += params.mapWidth
//        svg {
//            drawGeofaces(xOffset, yOffset, arrayOfColors = terrainColors)
//            //drawRivers(xOffset, yOffset)
//            //drawGeofaces(xOffset, yOffset, land = false, arrayOfColors = terrainColors)
//            drawSeacoast(xOffset, yOffset)
//        }
//        step5()
//    }
//
//}
//
//private fun step5() {
//    window.requestAnimationFrame {
//        cleanRivers(30, 120)
//        cleanCoastlines()
//
//        xOffset = 0
//        yOffset += params.mapHeight
//        svg {
//            drawGeofaces(xOffset, yOffset, sea = false, arrayOfColors = terrainColors)
//            drawRivers(xOffset, yOffset)
//            drawGeofaces(xOffset, yOffset, land = false, arrayOfColors = terrainColors)
//            drawSeacoast(xOffset, yOffset)
//        }
//        step6()
//    }
//
//}
//
//private fun step6() {
//    window.requestAnimationFrame {
//        relaxRivers()
//        xOffset += params.mapWidth
//        svg {
//            drawGeofaces(xOffset, yOffset, sea = false, arrayOfColors = terrainColors)
//            drawRivers(xOffset, yOffset)
//            drawGeofaces(xOffset, yOffset, land = false, arrayOfColors = terrainColors)
//            drawSeacoast(xOffset, yOffset)
//            drawCities(xOffset, yOffset)
//        }
//        step7()
//    }
//}
//
//private fun step7() {
//    window.requestAnimationFrame {
//        findCities(params)
//
//        xOffset += params.mapWidth
//        svg {
//            drawGeofaces(xOffset, yOffset, sea = false, arrayOfColors = terrainColors)
//            drawRivers(xOffset, yOffset)
//            drawGeofaces(xOffset, yOffset, land = false, arrayOfColors = terrainColors)
//            drawSeacoast(xOffset, yOffset)
//            drawCities(xOffset, yOffset)
//        }
//        step8()
//    }
//}
//
//private fun step8() {
//    xOffset += params.mapWidth
//    svg {
//        val slopecolor = Color(0x797979)
//        mesh.geoFaces.forEach { geoFace ->
//            if (geoFace.height >= 0) {
//                geoFace.triangle.forEach { edge ->
//                    val adjacentFace = getAdjacentFace(edge, geoFace.index)
//                    if (adjacentFace != null) {
//                        val direction = (adjacentFace.height - geoFace.height) / (adjacentFace.centroid.x.toDouble() - geoFace.centroid.x.toDouble())
//                        if (Math.abs(direction) > 0.07) {
//                            line(adjacentFace.centroid.x.toDouble() + xOffset, adjacentFace.centroid.y.toDouble() + yOffset, edge.end.x + xOffset, edge.end.y + yOffset, slopecolor)
//                        }
//                    }
//                }
//            }
//        }
//        //drawGeofaces(xOffset, yOffset, sea = false, arrayOfColors = mapColors)
//        drawRivers(xOffset, yOffset, black)
//        drawGeofaces(xOffset, yOffset, land = false, arrayOfColors = mapColors)
//        drawSeacoast(xOffset, yOffset)
//        drawCities(xOffset, yOffset)
//    }
//}
//
//private fun SVGElement.drawCities(xOffset: Int, yOffset: Int) {
//    cities.forEachIndexed { cityIndex, point ->
//        circle {
//            transform {
//                translate(point.x.toDouble() + xOffset, point.y.toDouble() + yOffset)
//            }
//            stroke = black
//            if (cityIndex < 3) {
//                fill = white
//                r = 5
//            } else {
//                fill = black
//                r = 3
//            }
//        }
//    }
//}
//
//private fun SVGElement.cleanSVG(params: Params) {
//    width = params.mapWidth * params.nbMapsDrawedW
//    height = params.mapHeight * params.nbMapsDrawedH
//
//    rect {
//        transform {
//            translate(Point(0.0, 0.0))
//        }
//        width = params.mapWidth * params.nbMapsDrawedW
//        height = params.mapHeight * params.nbMapsDrawedH
//        fill = white
//    }
//}
//
//private fun SVGElement.drawSeacoast(xOffset: Int, yOffset: Int) {
//    mesh.geoFaces.forEach { geoFace ->
//        geoFace.triangle.forEach { edge ->
//            val adjacentFaces = geoFaceIndexFromEdge.get(edge.toKey())
//            if (adjacentFaces != null && adjacentFaces.size > 1) {
//                val adjacent1 = mesh.geoFaces[adjacentFaces[0]]
//                val adjacent2 = mesh.geoFaces[adjacentFaces[1]]
//                if (adjacent1.height * adjacent2.height < 0) {
//                    line(edge.origin.x + xOffset, edge.origin.y + yOffset, edge.end.x + xOffset, edge.end.y + yOffset)
//                }
//            }
//        }
//    }
//}
//
//
//private fun SVGElement.drawGeofaces(xOffset: Int, yOffset: Int, land: Boolean = true, sea: Boolean = true, arrayOfColors: Array<Color>) {
//    mesh.geoFaces.forEach { geoFace ->
//        if ((sea && geoFace.height < 0) || (land && geoFace.height >= 0)) {
//            path {
//                path {
//                    val fill = heightColor(geoFace.height)
//                    stroke = fill
//                    strokeWidth = "1"
//                    setAttribute("fill", fill.toString())
//                    moveTo(geoFace.triangle[0].origin.x + xOffset, geoFace.triangle[0].origin.y + yOffset)
//                    geoFace.triangle.forEach { vertex ->
//                        lineTo(vertex.origin.x + xOffset, vertex.origin.y + yOffset)
//                    }
//                    closePath()
//                }
//            }
//        }
//    }
//}
//
//private fun SVGElement.drawRivers(xOffset: Int, yOffset: Int, riverColor: Color = blue) {
//    rivers.forEach { river ->
//        val riverFrom = mesh.geoFaces[river.fromIndex].centroid
//        val riverTo = mesh.geoFaces[river.toIndex].centroid
//        line(riverFrom.x.toDouble() + xOffset, riverFrom.y.toDouble() + yOffset, riverTo.x.toDouble() + xOffset, riverTo.y.toDouble() + yOffset, riverColor)
//    }
//}
//
//
//private fun cleanCoastlines(iterations: Int = 999) {
//    var heightsChanged = true
//    var currentIteration = 0
//
//    while (heightsChanged && currentIteration < iterations) {
//        heightsChanged = false
//        mesh.geoFaces.forEachIndexed { currentGeoFaceIndex, geoFace ->
//            if (geoFace.height >= 0) {
//                var countNegativeAdjacents = 0
//                var minAdjacentHeight = 1.0
//                geoFace.triangle.forEach { edge ->
//                    val adjacentFace = getAdjacentFace(edge, currentGeoFaceIndex)
//                    if (adjacentFace != null) {
//                        if (adjacentFace.height < 0) {
//                            countNegativeAdjacents++
//                            minAdjacentHeight = Math.min(minAdjacentHeight, adjacentFace.height)
//                        }
//                    }
//                }
//                if (countNegativeAdjacents > 1) {
//                    geoFace.height = minAdjacentHeight / 2
//                    heightsChanged = true
//                }
//            }
//            if (geoFace.height < 0) {
//                var countPositiveAdjacents = 0
//                var maxAdjacentHeight = -1.0
//                geoFace.triangle.forEach { edge ->
//                    val adjacentFace = getAdjacentFace(edge, currentGeoFaceIndex)
//                    if (adjacentFace != null) {
//                        if (adjacentFace.height >= 0) {
//                            countPositiveAdjacents++
//                            maxAdjacentHeight = Math.max(maxAdjacentHeight, adjacentFace.height)
//                        }
//                    }
//                }
//                if (countPositiveAdjacents > 1) {
//                    geoFace.height = maxAdjacentHeight / 2
//                    heightsChanged = true
//                }
//            }
//        }
//        currentIteration++
//    }
//    println("total iterations for cleanCoastlines = " + currentIteration)
//}
//
//private fun fillDepressions() {
//    val infinity: Double = 999999.0
//    val epsilon: Double = 1e-5
//    val newHeights: Array<Double> = emptyArray()
//    mesh.geoFaces.forEachIndexed { faceIndex, geoFace ->
//        if (isMapEdge(geoFace)) newHeights[faceIndex] = geoFace.height
//        else newHeights[faceIndex] = infinity
//    }
//    while (true) {
//        var changed = false
//        mesh.geoFaces.forEachIndexed { faceIndex, geoFace ->
//            if (newHeights[faceIndex] != geoFace.height) {
//                geoFace.triangle.forEach { edge ->
//                    val adjacentFaceIndex = getAdjacentFaceIndex(edge, faceIndex)
//                    if (adjacentFaceIndex != null) {
//                        val adjacentNewHeight = newHeights[adjacentFaceIndex] + epsilon
//                        if (geoFace.height >= adjacentNewHeight) {
//                            newHeights[faceIndex] = geoFace.height
//                            changed = true
////                            println("ici")
//                        } else {
//                            if (newHeights[faceIndex] > adjacentNewHeight && adjacentNewHeight > geoFace.height) {
//                                newHeights[faceIndex] = adjacentNewHeight
//                                changed = true
////                                println("la")
//                            }
//                        }
//                    }
//                }
//            }
//        }
//        if (!changed) {
//            mesh.geoFaces.forEachIndexed { faceIndex, geoFace ->
//                // only fill lakes
//                if (geoFace.height * newHeights[faceIndex] < 0) geoFace.height = newHeights[faceIndex]
//            }
//            return
//        }
//    }
//}
//
//private fun generatePoints() =
//        (0..params.npts - 1).map {
//            Site(Point(Math.random() * params.mapWidth, Math.random() * params.mapHeight), it)
//        }
//
//private fun improvePoints(cycles: Int): Unit {
//
//    fun List<Point>.centroid():Point {
//        var x = 0.0
//        var y = 0.0
//        forEach {
//            x += it.x
//            y += it.y
//        }
//        return Point(x/size, y/sizeSquared)
//    }
//
//    for (i in 1..cycles) {
//        diagram = Diagram(vSites)
//        vSites = diagram!!.polygons().mapIndexed { index,polygon -> Site(polygon.centroid(), index) }.toTypedArray()
//    }
//    diagram = Diagram(vSites, clipEnd = Point(params.mapWidth.toDouble(), params.mapHeight.toDouble()))
//}
//
//private fun addRelief(nbReliefs: Int, params: Params, reliefHeight: Float = 1.0F, reliefSizePercentMap: Double = 0.08) {
//    (0..nbReliefs).forEach {
//        val reliefPosition = Point(Math.random() * params.mapWidth, Math.random() * params.mapHeight)
//        val reliefSizeSquared = Math.pow(reliefSizePercentMap * Math.random() * params.mapWidth, 2.0)
//        mesh.geoFaces.forEach { geoFace ->
//            var averageHeight: Double = 0.0
//            geoFace.triangle.forEach { edge ->
//                val vertex = edge.origin
//                val distance = Math.pow(vertex.x - reliefPosition.x.toDouble(), 2.0) +
//                        Math.pow(vertex.y - reliefPosition.y.toDouble(), 2.0)
//
//                var currentHeight = 0.0
//                if (distance <= reliefSizeSquared) currentHeight = reliefHeight - (reliefHeight * (distance / reliefSizeSquared))
//
//                vertex.z += currentHeight
//                averageHeight += currentHeight
//            }
//            geoFace.height += (averageHeight / 3.0)
//        }
//    }
//}
//
//private fun findRivers() {
//    val orderedGeoFaces: Array<GeoFace> = mesh.geoFaces.copyOf() //pourquoi faire une copie
//    orderedGeoFaces.sortByDescending { it.height }
//    orderedGeoFaces.forEachIndexed { geoFaceIndex, geoFace ->
//        if (geoFace.height < 0) return@forEachIndexed //filtrer, ou return global il n'y a pas d'instruction ensuite
//
//        var lowestAdjacentHeight = geoFace.height
//        var lowestAdjacent: GeoFace? = null
//
//        geoFace.triangle.forEach { edge ->
//            val adjacentFacesIndex = getAdjacentFacesIndex(edge)
//            adjacentFacesIndex?.forEach { adjacentFaceIndex ->
//                if (mesh.geoFaces[adjacentFaceIndex].height < lowestAdjacentHeight) {
//                    lowestAdjacentHeight = mesh.geoFaces[adjacentFaceIndex].height
//                    lowestAdjacent = mesh.geoFaces[adjacentFaceIndex]
//                }
//            }
//        }
//        if (lowestAdjacent != null) {
//            val river = River(geoFace.index, lowestAdjacent!!.index)
//            geoFaceIndexToRivers.get(geoFace.index)?.forEach { upRiver ->
//                river.strength += upRiver.strength
//            }
//            val downRivers: ArrayList<River>? = geoFaceIndexToRivers.get(lowestAdjacent!!.index)
//            if (downRivers == null) {
//                geoFaceIndexToRivers.put(lowestAdjacent!!.index, arrayListOf(river))
//            } else {
//                /*downRivers.forEach { otherRiver ->
//                    river.strength += otherRiver.strength
//                }*/
//                downRivers.add(river)
//                geoFaceIndexToRivers.put(lowestAdjacent!!.index, downRivers)
//            }
//            rivers.add(river)
//        }
//    }
//
//}
//
//private fun erode() {
//    // river erosion
//    mesh.geoFaces.forEach { geoFace ->
//        val rivers = geoFaceIndexToRivers[geoFace.index]
//        rivers?.forEach { river ->
//            geoFace.height -= river.strength / 70
//        }
//        geoFace.height = Math.max(-0.6, geoFace.height)
//    }
//}
//
//private fun erode2() {
//    // weather global erosion
//    val newHeights: Array<Double> = emptyArray()
//    mesh.geoFaces.forEachIndexed { index, geoFace ->
//        var erodedHeight = 0.0
//        var nbAdjacent = 0
//        geoFace.triangle.forEach { edge ->
//            val adjacentFace = getAdjacentFace(edge, geoFace.index)
//            if (adjacentFace != null) {
//                erodedHeight += adjacentFace.height
//                nbAdjacent++
//            }
//        }
//        newHeights[index] = (erodedHeight + geoFace.height) / (nbAdjacent + 1)
//    }
//    mesh.geoFaces.map { geoFace -> geoFace.height = newHeights[geoFace.index] }
//}
//
//private fun cleanRivers(minUpRiverStrength: Int, minFinalRiverStrength: Int) {
//    rivers = ArrayList(rivers.filter { river -> river.strength > minUpRiverStrength })
//
//    var keeping = 0
//    rivers.forEach { river ->
//        if (!river.keep && river.strength > minFinalRiverStrength && mesh.geoFaces[river.toIndex].height < 0) {
//            river.keep = true
//            keeping++
//        }
//    }
//    while (true) {
//        var changed = false
//        keeping = 0
//        var keepnew = 0
//        rivers.forEach { river ->
//            if (river.keep) {
//                keeping++
//                geoFaceIndexToRivers.get(river.fromIndex)?.forEach { previousRiver ->
//                    if (!previousRiver.keep && previousRiver.strength > minUpRiverStrength) {
//                        previousRiver.keep = true
//                        keepnew++
//                        changed = true
//                    }
//                }
//            }
//        }
//        if (!changed) {
//            rivers = ArrayList(rivers.filter { river -> river.keep })
//            return
//        }
//    }
//}
//
//private fun relaxRivers() {
//    rivers.reverse()
//    rivers.forEach { river ->
//        if (river.keep) {
//            val adjacentRivers = geoFaceIndexToRivers.get(river.fromIndex)
//            if (adjacentRivers?.size == 1) {
//                val adjacentRiver = adjacentRivers[0]
//                river.fromIndex = adjacentRiver.fromIndex
//                adjacentRiver.keep = false
//            }
//        }
//    }
//    rivers = ArrayList(rivers.filter { river -> river.keep })
//}
//
//private fun findCities(params: Params) {
//    cities.clear()
//    (0..params.nbCities - 1).forEach { cityIndex ->
//        var bestScore = -99999999.0
//        var bestCity: Point? = null
//        mesh.geoFaces.forEach { geoFace ->
//            if (geoFace.height >= 0) {
//                var score: Double = 0.0
//
//                // attracted by riversides and sea
//                geoFaceIndexToRivers.get(geoFace.index)?.forEach { river ->
//                    if (river.keep) score += river.strength.toDouble()
//                }
//                score /= 10
//
//                // repulsed by near cities
//                cities.forEach { cityLocation ->
//                    val distance = Math.sqrt(Math.pow(cityLocation.x.toDouble() - geoFace.centroid.x.toDouble(), 2.0) + Math.pow(cityLocation.y.toDouble() - geoFace.centroid.y.toDouble(), 2.0))
//                    score += Math.sqrt(distance)
//                }
//
//                // attracted by map center
//                val distance = Math.sqrt(Math.pow(params.mapWidth / 2 - geoFace.centroid.x.toDouble(), 2.0) + Math.pow(params.mapHeight / 2 - geoFace.centroid.y.toDouble(), 2.0))
//                score -= Math.sqrt(distance)
//
//                if (score > bestScore) {
//                    bestScore = score
//                    bestCity = geoFace.centroid
//                }
//            }
//        }
//        if (bestCity != null) {
//            cities.add(bestCity!!)
//            println("placing city on x=" + bestCity!!.x + " y=" + bestCity!!.y + " score=" + bestScore)
//        } else return
//    }
//}
//
//// TODO : le stocker dès le makemesh ?
//private fun isMapEdge(geoFace: GeoFace): Boolean {
//    geoFace.triangle.forEach { edge ->
//        if (geoFaceIndexFromEdge.get(edge.toKey())!!.size < 2) return true
//    }
//    return false
//}
//
//private fun getAdjacentFacesIndex(edge: Edge): Array<Int>? {
//    val adjacentFaces = geoFaceIndexFromEdge.get(edge.toKey())
//    if (adjacentFaces != null && adjacentFaces.size > 1) {
//        return adjacentFaces
//    }
//    return null
//}
//
//private fun getAdjacentFaceIndex(edge: Edge, currentFaceIndex: Int): Int? {
//    val adjacentFaces = getAdjacentFacesIndex(edge)
//    if (adjacentFaces != null) {
//        var adjacentFaceIndex = adjacentFaces[0]
//        if (adjacentFaceIndex == currentFaceIndex) adjacentFaceIndex = adjacentFaces[1]
//        return (adjacentFaceIndex)
//    }
//    return null
//}
//
//private fun getAdjacentFace(edge: Edge, currentFaceIndex: Int): GeoFace? {
//    val adjacentFaceIndex = getAdjacentFaceIndex(edge, currentFaceIndex)
//    if (adjacentFaceIndex != null) return mesh.geoFaces[adjacentFaceIndex]
//    return null
//}
//
//private fun centroid(pts: Array<Array<Double>>): Point {
//    var x = 0.0
//    var y = 0.0
//    pts.forEachIndexed { index, pt ->
//        x += pt[0]; y += pt[1]
//    }
//    return Point(x / pts.size, y / pts.sizeSquared)
//}
