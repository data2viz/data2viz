package io.data2viz.experiments.fantasymap

import io.data2viz.color.Color
import io.data2viz.color.colors.black
import io.data2viz.color.colors.blue
import io.data2viz.color.colors.darkgray
import io.data2viz.color.colors.green
import io.data2viz.color.colors.grey
import io.data2viz.color.colors.lightgray
import io.data2viz.color.colors.red
import io.data2viz.color.colors.white
import io.data2viz.core.Point
import io.data2viz.interpolate.interpolateRgbBasis
import io.data2viz.svg.*
import io.data2viz.voronoi.Diagram
import io.data2viz.voronoi.Site
import org.w3c.dom.Node
import kotlin.browser.window
import kotlin.js.Date
import kotlin.js.Math
import kotlin.reflect.KMutableProperty0

private val EPSILON: Double = 1e-5
private val ETERNAL_SNOW: Float = .98f

data class Climate(
        val mountainCountRange: IntRange, // nb of mountains
        val mountainSizeRange: IntRange, // size of mountains in percent (0..100) of mapWidth
        val mountainHeightRange: IntRange, // height of mountains in percent (0..100)
        val seaCountRange: IntRange, // nb of "seas"
        val seaSizeRange: IntRange, // size of seas in percent (0..100) of mapWidth
        val seaDepthRange: IntRange, // depth of seas in percent (-100..0)
        val erosionRange: IntRange, // percent of erosion (0..100)
        val rainRange: IntRange, // percent of "rainRange" (0..100)
        val seaColors: (Number) -> Color,
        val groundColors: (Number) -> Color
)

val snowy = Climate(
        mountainCountRange = (200..300),
        mountainSizeRange = (20..25),
        mountainHeightRange = (10..15),
        seaCountRange = (1..2),
        seaSizeRange = (30..50),
        seaDepthRange = (5..30),
        erosionRange = (3..7),
        rainRange = (10..30),
        seaColors = interpolateRgbBasis(listOf(Color(0x7190f0), Color(0x91b0f0), Color(0xa1c0f0), Color(0xc1e0f0))),
        groundColors = interpolateRgbBasis(listOf(Color(0x99b56e), Color(0x732600), Color(0x8f5c45), Color(0xf2eea2), Color(0xf3f3f3)))
)

val temperate = Climate(
        mountainCountRange = (60..100),
        mountainSizeRange = (20..35),
        mountainHeightRange = (20..50),
        seaCountRange = (6..10),
        seaSizeRange = (10..30),
        seaDepthRange = (20..50),
        erosionRange = (3..7),
        rainRange = (40..60),
        seaColors = interpolateRgbBasis(listOf(Color(0x7190f0), Color(0x91b0f0), Color(0xa1c0f0), Color(0xc1e0f0))),
        groundColors = interpolateRgbBasis(listOf(Color(0x99b56e), Color(0x99b56e), Color(0x99b56e),
                Color(0x709959), Color(0x709959), Color(0xc5d188), Color(0xc5d188), Color(0xf2eea2), Color(0xf2e096), Color(0xebc17f), Color(0xd1926b),
                Color(0xa1694d), Color(0x82462a), Color(0x732600), Color(0x8f5c45), Color(0xacacac), Color(0xececec), Color(0xf3f3f3), Color(0xf3f3f3), Color(0xf3f3f3)))
)

val desert = Climate(
        mountainCountRange = (100..110), // nb of mountains
        mountainSizeRange = (30..40), // size of mountains in percent (0..100) of mapWidth
        mountainHeightRange = (4..6), // height of mountains in percent (0..100)
        seaCountRange = (1..2), // nb of "seas"
        seaSizeRange = (6..10), // size of seas in percent (0..100) of mapWidth
        seaDepthRange = (60..80), // depth of seas in percent (0..100)
        erosionRange = (1..2), // percent of erosion (0..100)
        rainRange = (0..1), // percent of "rainRange" (0..100)
        seaColors = interpolateRgbBasis(listOf(Color(0x7190f0), Color(0x91b0f0), Color(0xa1c0f0), Color(0xc1e0f0))),
        groundColors = interpolateRgbBasis(listOf(Color(0x99b56e), Color(0x709959), Color(0xf2eea2), Color(0xf2eea2),
                Color(0xf2e096), Color(0xf2e096), Color(0xebc17f), Color(0xd1926b),
                Color(0xa1694d), Color(0x82462a), Color(0x732600), Color(0x8f5c45), Color(0xacacac), Color(0xececec), Color(0xf3f3f3), Color(0xf3f3f3), Color(0xf3f3f3)))
)


val fjord = Climate(
        mountainCountRange = (150..220), // nb of mountains
        mountainSizeRange = (20..35), // size of mountains in percent (0..100) of mapWidth
        mountainHeightRange = (35..70), // height of mountains in percent (0..100)
        seaCountRange = (6..10), // nb of "seas"
        seaSizeRange = (10..30), // size of seas in percent (0..100) of mapWidth
        seaDepthRange = (20..50), // depth of seas in percent (-100..0)
        erosionRange = (10..12), // percent of erosion (0..100)
        rainRange = (120..160), // percent of "rainRange" (0..100)
        seaColors = interpolateRgbBasis(listOf(Color(0x7190f0), Color(0x91b0f0), Color(0xa1c0f0), Color(0xc1e0f0))),
        groundColors = interpolateRgbBasis(listOf(Color(0x99b56e), Color(0x99b56e), Color(0x99b56e),
                Color(0x709959), Color(0x709959), Color(0xc5d188), Color(0xc5d188), Color(0xf2eea2), Color(0xf2e096), Color(0xebc17f), Color(0xd1926b),
                Color(0xa1694d), Color(0x82462a), Color(0x732600), Color(0x8f5c45), Color(0xacacac), Color(0xececec), Color(0xf3f3f3), Color(0xf3f3f3), Color(0xf3f3f3)))
)


private var randomSites: List<Site> = emptyList()           // pure random sites
private var bakedSites: List<Site> = emptyList()            // sites stored after multiple voronoi optimisations
private var vRelief = mutableListOf<Relief>()
private var vSea = mutableListOf<Relief>()
private var diagram: Diagram? = null
private var climate: Climate = fjord
private var generationStep: Int = 5

private var mapSvg: GroupElement? = null
private var interfaceSvg: GroupElement? = null
private var console: TextElement? = null
private var consoleLog: String = ""

private val GEO_FACES: MutableList<GeoFace> = mutableListOf()
private val GEO_POINTS: MutableList<GeoPoint> = mutableListOf()

data class Params(
        var npts: Int = 5000, //16384,
        val nbCities: Int = 5,

        var mountainCount: Int = 0,
        var mountainSize: Float = 0f,
        var mountainHeight: Float = 0f,
        var seaCount: Int = 0,
        var seaSize: Float = 0f,
        var seaDepth: Float = 0f,
        var erosion: Float = 0f,
        var rain: Float = 0f,

        var mountainCountHigh: Boolean = false,
        var mountainSizeHigh: Boolean = false,
        var mountainHeightHigh: Boolean = false,
        var seaCountHigh: Boolean = false,
        var seaSizeHigh: Boolean = false,
        var seaDepthHigh: Boolean = false,
        var erosionHigh: Boolean = false,
        var rainHigh: Boolean = false,

        // TODO : defaultHeight, moutainPositions

        var seaColors: (Number) -> Color = interpolateRgbBasis(listOf(blue)),
        var groundColors: (Number) -> Color = interpolateRgbBasis(listOf(green)),

        val mapWidth: Int = 800,
        val mapHeight: Int = 800,
        val nbMapsDrawedW: Int = 1,
        val nbMapsDrawedH: Int = 1,

        val interfaceWidth: Int = 455,
        val interfaceHeight: Int = 0,
        val interfaceMarginH: Int = 25,
        val interfaceMarginW: Int = 15,

        val maxMaps: Int = 1,
        var mapCount: Int = 0,
        var renew: Boolean = false,

        var showFaces: Boolean = false,
        var showRivers: Boolean = true,
        var showCoasts: Boolean = true,
        var showRelief: Boolean = true
)

data class GeoFace(
        private val index: Int,
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
        private val site: Site,
        val geoFaces: MutableList<GeoFace> = mutableListOf(),
        val adjacents: MutableList<GeoPoint> = mutableListOf(),
        var riverTo: GeoPoint? = null,
        val riversFrom: MutableList<GeoPoint> = mutableListOf(),
        var riverStrength: Double = 0.0,
        var height: Double = EPSILON
) {
    val isSea
        get() = height < 0
    val isLand
        get() = height >= 0
    val x
        get() = site.pos.x
    val y
        get() = site.pos.y
    val isOnEdge
        get() = geoFaces.filter { it.isOnEdge }.size > 1

    override operator fun equals(other: Any?) = other is GeoPoint && this.site.index == other.site.index
    override fun hashCode(): Int = site.index
}

/**
 * Hold a template of relief : position, future size (0..1) and future height (0..1)
 */
data class Relief(
        val point: Point,
        val size: Double,
        val height: Double
)

var xOffset = 0
var yOffset = 0

val params = Params()

/**
 * Entry point
 */
fun buildFinalFantasyMap() {
    svg { prepareSVG() }
    svg { drawInterface() }

    // generate the maximum number of sites (to keep them through terrain generation)
    timeAndResult("# RANDOM SITE GENERATION") { randomSites = generateRandomSites(50000) }
    timeAndResult("# RANDOM RELIEF GENERATION") {
        vRelief.clear()
        vSea.clear()

        // TODO : clean :)
        vRelief.addAll(generateRandomRelief(999))
        vSea.addAll(generateRandomRelief(999))
    }

    prepare(true)
}

private fun prepare(renew: Boolean) {

    printConsole("############## PASS ${params.mapCount + 1} ##############")

    GEO_POINTS.clear()
    GEO_FACES.clear()

    initClimate()
    if (renew) {
        timeAndResult("# VORONOI DIAGRAM COMPUTE AND OPTIMIZE") { improveSites(1, randomSites) }
    } else {
        timeAndResult("# VORONOI DIAGRAM COMPUTE AND OPTIMIZE") { improveSites(0, bakedSites) }
    }
    timeAndResult { makeMesh() }

    computeStep()
}

private fun computeStep() {
    window.requestAnimationFrame {
        when {
            generationStep == 1 -> computeStep1()
            generationStep == 2 -> computeStep2()
            generationStep == 3 -> computeStep3()
            generationStep == 4 -> computeStep4()
            generationStep == 5 -> computeStep5()
            else -> computeStep5()
        }
    }
    drawStep()
}

private fun drawStep() {
    window.requestAnimationFrame {
        if (mapSvg != null) svg { removeChild(mapSvg!!.element) }
        svg {
            mapSvg = g {
                when {
                    generationStep == 1 -> drawStep1()
                    generationStep == 2 -> drawStep2()
                    generationStep == 3 -> drawStep3()
                    generationStep == 4 -> drawStep4()
                    generationStep == 5 -> drawStep5()
                    else -> drawStep5()
                }
            }
        }
    }
    stepFinal()
}

fun ParentElement.removeChild(child: Node) {
    element.removeChild(child)
}


private fun computeStep1() {
    timeAndResult { addRelief() }
}

private fun GroupElement.drawStep1() {
    drawGeofaces(colorScale = ::getMapColor)
}

private fun computeStep2() {
    timeAndResult { addRelief() }
    timeAndResult { findRivers() }
}

private fun GroupElement.drawStep2() {
    drawGeofaces(colorScale = ::getMapColor)
    drawRivers()
}

private fun computeStep3() {
    timeAndResult { addRelief() }
    timeAndResult { findRivers() }
    timeAndResult { erode() }
}

private fun GroupElement.drawStep3() {
    drawGeofaces(colorScale = ::getMapColor)
    drawRivers()
}

private fun computeStep4() {
    timeAndResult { addRelief() }
    timeAndResult { findRivers() }
    timeAndResult { erode() }
    timeAndResult { cleanCoastsAndPlains() }
}

private fun GroupElement.drawStep4() {
    drawGeofaces(colorScale = ::getMapColor)
    drawRivers()
    drawCoastLines()
}

private fun computeStep5() {
    timeAndResult { addRelief() }
    timeAndResult { findRivers() }
    timeAndResult { erode() }
    timeAndResult { cleanCoastsAndPlains() }
    timeAndResult { cleanRivers() }
}

private fun GroupElement.drawStep5() {
    drawGeofaces(colorScale = ::getMapColor)
    drawRivers(6, 12)
    drawCoastLines()
}

fun stepFinal() {
    params.mapCount++
    /*if (params.mapCount < params.maxMaps) {
        xOffset = params.mapCount % params.nbMapsDrawedW * params.mapWidth
        yOffset = (params.mapCount / params.nbMapsDrawedW) % params.nbMapsDrawedH * params.mapHeight
        prepare(params.renew)
    }*/
}

/**
 * Simulate erosion in multiple passes :
 *  - river erosion that strongly shape relief
 *  - wind & rainRange erosion that round hard shapes
 *  - sea floor normalization (the farther from coast the deeper)
 *
 *  @param pass : the number of times wind & air erosion is called (hard shapes=0 .. 2=soft shapes)
 */
private fun erode(pass: Int = 1) {
    printConsole("# EROSION")
    val landPoints = GEO_POINTS.filter { it.isLand }
    fun erodeRivers() {
        landPoints.forEach { it.height -= Math.sqrt(it.riverStrength) * params.erosion }
        printConsole("  - ${landPoints.size} geoPoints moved by river erosion")
    }

    fun erodeWindAndRain() {
        (0 until pass).forEach {
            val savedHeights = mutableListOf<Double>()
            landPoints.forEach { geoPoint ->
                val landAdjacents = geoPoint.adjacents.filter { it.isLand }
                var totalHeights = landAdjacents.sumByDouble { it.height }
                totalHeights += geoPoint.height
                savedHeights.add(totalHeights / (landAdjacents.size + 1))
            }
            landPoints.forEachIndexed { index, geoPoint -> geoPoint.height = savedHeights[index] }
            printConsole("  - ${landPoints.size} geoPoints moved by wind erosion")
        }
    }

    fun erodeSeaFloor() {
        var depth: Double = -1.0
        val seaPoints = GEO_POINTS.filter { it.isSea }
        val pointsChanged = mutableSetOf<GeoPoint>()

        var pts = 0
        // reset depths
        seaPoints.forEach { it.height = -EPSILON }
        // set first deep at seaside
        landPoints.forEach { geoPoint ->
            geoPoint.adjacents.filter { it.height == -EPSILON }.forEach {
                it.height = depth
                pointsChanged.add(it)
            }
        }
        pts += pointsChanged.size
        // go deeper as we get away from the coast
        while (pointsChanged.isNotEmpty()) {
            depth -= 1.0
            val newPoints = mutableSetOf<GeoPoint>()
            pointsChanged.forEach { geoPoint ->
                geoPoint.adjacents.filter { it.height == -EPSILON }.forEach {
                    it.height = depth
                    newPoints.add(it)
                }
            }
            pointsChanged.clear()
            pointsChanged.addAll(newPoints)
            pts += pointsChanged.size
        }
        normalizeHeights(land = false, sea = true)
        printConsole("  - $pts geoPoints moved by sea erosion")
    }

    erodeRivers()
    erodeWindAndRain()
    erodeSeaFloor()
    GEO_POINTS.forEach { recomputeGeoFacesHeights(it) }
    //cleanCoastsAndPlains()
}

/**
 * In order to "normalize" our Voronoi cells we place the site at the centroid of each triangle then recompute
 * a new Voronoi diagram based on our new sites.
 * Do it a few cycles (2-3) to greatly improve the geometry.
 */
private fun improveSites(cycles: Int, sites: List<Site>): Unit {
    fun List<Point>.centroid(): Point {
        var x = 0.0
        var y = 0.0
        forEach {
            x += it.x
            y += it.y
        }
        return Point(x / size, y / size)
    }

    bakedSites = sites.toList().subList(0, params.npts)
    (0 until cycles).forEach {
        diagram = Diagram(bakedSites.toTypedArray(), clipEnd = Point(params.mapWidth.toDouble(), params.mapHeight.toDouble()))
        bakedSites = diagram!!.polygons().mapIndexed { index, polygon -> Site(polygon.centroid(), index) }
    }
    diagram = Diagram(bakedSites.toTypedArray(), clipEnd = Point(params.mapWidth.toDouble(), params.mapHeight.toDouble()))
    GEO_POINTS.addAll(bakedSites.sortedBy { it.index }.map { GeoPoint(it) })
}

// TODO : sort edges in triangle ?
private fun makeMesh() {
    printConsole("# BUILDING SURFACE")

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
        val face0 = gf.geoPoints[0].geoFaces.intersect(gf.geoPoints[1].geoFaces).filter { gf != it }.firstOrNull()
        val face1 = gf.geoPoints[1].geoFaces.intersect(gf.geoPoints[2].geoFaces).filter { gf != it }.firstOrNull()
        val face2 = gf.geoPoints[2].geoFaces.intersect(gf.geoPoints[0].geoFaces).filter { gf != it }.firstOrNull()
        gf.adjacents.addAll(listOf(face0, face1, face2))
    }

    printConsole(" - ${GEO_POINTS.size} geoPoints created")
    printConsole(" - ${GEO_FACES.size} geoFaces created")
}

private fun generateRandomRelief(nbReliefs: Int): List<Relief> {
    return (0..nbReliefs).map {
        Relief(Point(Math.random() * params.mapWidth, Math.random() * params.mapHeight), Math.random(), Math.random())
    }
}

private fun addRelief() {
    printConsole("# RELIEF MODIFICATION")
    fun applyRelief(reliefs: List<Relief>, numRelief: Int, size: Float, height: Float) {
        (0 until numRelief).forEach {
            val relief = reliefs[it]
            val sizeSquared = Math.pow(relief.size * size, 2.0)
            val computedHeight = relief.height * height
            GEO_POINTS.forEach { geoPoint ->
                val distance = Math.pow(geoPoint.x - relief.point.x, 2.0) + Math.pow(geoPoint.y - relief.point.y, 2.0)
                if (distance <= sizeSquared) {
                    geoPoint.height += computedHeight - (computedHeight * (distance / sizeSquared))
                }
            }
        }
    }

    applyRelief(vRelief, params.mountainCount, params.mountainSize, params.mountainHeight)
    applyRelief(vSea, params.seaCount, params.seaSize, params.seaDepth)
    printConsole(" - ${vRelief.size + vSea.size} relief elements applied")
    normalizeHeights(land = true, sea = true)
    printConsole(" - Normalize heights")
    GEO_POINTS.forEach { recomputeGeoFacesHeights(it) }
}

/**
 * Normalize heights between -1..0 for sea and 0..1 for land
 *
 * @param land normalize "land" geoPoints
 * @param sea normalize "sea" geoPoints
 */
private fun normalizeHeights(land: Boolean, sea: Boolean) {
    if (sea) {
        val min = GEO_POINTS.minBy { it.height }!!.height
        if (min < -1) {
            GEO_POINTS.filter { it.isSea }.forEach { it.height /= -min }
        }
    }
    if (land) {
        val max = GEO_POINTS.maxBy { it.height }!!.height
        if (max > 1) {
            GEO_POINTS.filter { it.isLand }.forEach { it.height /= max }
        }
    }
}

private fun GroupElement.drawGeofaces(land: Boolean = true, sea: Boolean = true, colorScale: (GeoFace) -> Color) {
    GEO_FACES.forEach { geoFace ->
        if ((sea && geoFace.isSea) || (land && geoFace.isLand)) {
            path {
                path {
                    val fill = colorScale(geoFace)
                    stroke = fill
                    setAttribute("fill", fill.toString())
                    strokeWidth = if (params.showFaces) "0" else "1"

                    moveTo(geoFace.geoPoints[0].x + xOffset, geoFace.geoPoints[0].y + yOffset)
                    geoFace.geoPoints.forEach { geoPoint ->
                        lineTo(geoPoint.x + xOffset, geoPoint.y + yOffset)
                    }
                    closePath()
                }
            }
        }
    }
}

private fun SVGElement.drawSlopes() {
    val slopecolor = Color(0x797979)
    GEO_POINTS.filter { it.isLand }.forEach { geoPoint ->
        geoPoint.adjacents.filter { it.isLand }.forEach { adjacent ->
            val direction = (adjacent.height - geoPoint.height) / (adjacent.x - geoPoint.x)
            if (Math.abs(direction) > 0.07) {
                line(adjacent.x + xOffset, adjacent.y + yOffset, geoPoint.x + xOffset, geoPoint.y + yOffset, slopecolor)
            }
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

private fun SVGElement.prepareSVG() {
    width = params.mapWidth * params.nbMapsDrawedW + params.interfaceWidth
    height = params.mapHeight * params.nbMapsDrawedH + params.interfaceHeight

    /*rect {
        transform {
            translate(Point(0.0, 0.0))
        }
        width = params.mapWidth * params.nbMapsDrawedW + params.interfaceWidth
        height = params.mapHeight * params.nbMapsDrawedH + params.interfaceHeight
        fill = white
    }*/
}

private fun GroupElement.drawCoastLines() {
    if (!params.showCoasts) return
    GEO_FACES.forEach { geoFace ->
        if (geoFace.isLand) return@forEach
        geoFace.geoPoints.forEachIndexed { index, geoPoint ->
            val adjacentGeoFace = geoFace.adjacents[index] ?: return@forEachIndexed
            if (adjacentGeoFace.isLand) {
                val nextPoint = geoFace.geoPoints[(index + 1) % 3]
                line {
                    x1 = geoPoint.x + xOffset
                    y1 = geoPoint.y + yOffset
                    x2 = nextPoint.x + xOffset
                    y2 = nextPoint.y + yOffset
                    stroke = black
                    strokeWidth = "2"
                }
            }
        }
    }
}

private fun GroupElement.drawRivers(minStrength: Int = 0, maxStrength: Int = 99999, riverColor: Color = blue) {
    if (!params.showRivers) return
    val maxStrengthSquared = maxStrength * maxStrength
    var riv: Int = 0
    GEO_POINTS.filter { it.isLand && it.riverStrength > minStrength && it.riverTo != null && it.riverTo!!.isLand }.forEach { geoPoint ->
        riv++
        line {
            x1 = geoPoint.x + xOffset
            y1 = geoPoint.y + yOffset
            x2 = geoPoint.riverTo!!.x + xOffset
            y2 = geoPoint.riverTo!!.y + yOffset
            stroke = riverColor
            strokeWidth = if (geoPoint.riverStrength > maxStrengthSquared) "3" else if (geoPoint.riverStrength > maxStrength) "2" else "1"
        }
    }
}

/**
 * Erode seacoasts and normalize plains
 */
private fun cleanCoastsAndPlains(maxLoops: Int = 12) {
    printConsole("# COASTS AND LAND EROSION")

    /**
     * To simulate coast erosion, just "landify" faces that have 2 inland adjacents
     * and "sea-ify" faces that have 2 insea adjacents
     */
    fun cleanCoasts(maxLoops: Int = 12) {
        var heightsChanged = true
        var currentIteration = 0
        val geoFacesMoved = mutableSetOf<GeoFace>()
        val geoPointsMoved = mutableSetOf<GeoPoint>()
        var totalGeoPointsMoved = 0

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
                    if (geoFace.isLand && geoPoint.isSea) {
                        geoPoint.height = 0.0
                        geoPointsMoved.add(geoPoint)
                        totalGeoPointsMoved++
                    } else if (geoFace.isSea && geoPoint.isLand) {
                        geoPoint.height = 0.0
                        geoPointsMoved.add(geoPoint)
                        totalGeoPointsMoved++
                    }
                }
            }
            geoPointsMoved.forEach { recomputeGeoFacesHeights(it) }
        }
        printConsole(" - $totalGeoPointsMoved geoPoints moved")
        printConsole(" - $currentIteration iterations for eroding coast lines")
    }

    /**
     * To avoid some near-limit aberrations, move up each points of a "land" geoFace to height = EPSILON
     */
    fun cleanPlains() {
        val landPoints = GEO_FACES.filter { it.isLand }
        landPoints.forEach { geoFace ->
            geoFace.geoPoints.forEach { geoPoint ->
                geoPoint.height = Math.max(geoPoint.height, EPSILON)
            }
        }
        printConsole(" - ${landPoints.size} geoFaces moved by erosion")
    }

    cleanCoasts(maxLoops)
    cleanPlains()
}


/**
 * rescale GeoFaces whenever some geoPoints have been moved
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
//                            printConsole("ici")
                        } else {
                            if (newHeights[faceIndex] > adjacentNewHeight && adjacentNewHeight > geoFace.height) {
                                newHeights[faceIndex] = adjacentNewHeight
                                changed = true
//                                printConsole("la")
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

private fun generateRandomSites(nbSites: Int) =
        (0 until nbSites).map {
            Site(Point(Math.random() * params.mapWidth, Math.random() * params.mapHeight), it)
        }

private fun findRivers() {
    printConsole("# RIVERS GENERATION")
    var riv = 0
    val orderedGeoPoints = GEO_POINTS.filter { it.isLand && it.height < ETERNAL_SNOW }.filterNot { it.isOnEdge }.sortedByDescending { it.height }
    orderedGeoPoints.forEach { geoPoint ->
        var lowestAdjacentHeight = geoPoint.height
        var lowestAdjacentPoint: GeoPoint? = null
        geoPoint.adjacents.forEach { adjacent ->
            if (adjacent.height <= lowestAdjacentHeight) {
                lowestAdjacentHeight = adjacent.height
                lowestAdjacentPoint = adjacent
            }
        }

        if (lowestAdjacentPoint == null) return@forEach
        riv++
        geoPoint.riverTo = lowestAdjacentPoint
        lowestAdjacentPoint!!.riversFrom.add(geoPoint)

        geoPoint.riverStrength += params.rain
        lowestAdjacentPoint!!.riverStrength += geoPoint.riverStrength
    }
    printConsole(" - created $riv rivers")
}

// TODO actually a river can "end" on a point which is below "0 height" and so considered as sea... but it may not be drawn a sea
/**
 * Search and remove rivers that "ends" (riverTo == null) not in a sea
 */
fun cleanRivers() {
    printConsole("# RIVERS DISPLAY FILTERING")
    val riversToNothing = GEO_POINTS.filter { it.isLand && it.riverTo == null && it.riversFrom.isNotEmpty() }.toMutableList()

    var riv = 0

    riversToNothing.forEach { geoPoint ->
        riversToNothing.addAll(geoPoint.riversFrom)
        geoPoint.riversFrom.clear()
        geoPoint.riverStrength = 0.0
        riv++
    }
    printConsole(" - ${riversToNothing.size} rivers filtered")
}

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
            printConsole("placing city on x=" + bestCity!!.x + " y=" + bestCity!!.y + " score=" + bestScore)
        } else return
    }
}*/


private fun getMapColor(geoFace: GeoFace): Color {
    return if (geoFace.isSea) params.seaColors(geoFace.height + 1) else params.groundColors(geoFace.height)
}

private fun getMapColor2(geoFace: GeoFace): Color {
    return red
}

/*private fun randomClimate(): Climate {
    val rand = (Math.random() * 3).toInt()
    return when {
        rand == 0 -> temperate
        rand == 1 -> desert
        rand == 2 -> fjord
        else -> temperate
    }
}*/

/**
 * Choose a random climate, init climate variables and scale them for map size and sites number
 */
private fun initClimate() {
    /*params.mountainCount = (Math.random() * (climate.mountainCountRange.last - climate.mountainCountRange.first) + climate.mountainCountRange.first).toInt()
    params.mountainSize = (Math.random() * (climate.mountainSizeRange.last - climate.mountainSizeRange.first) + climate.mountainSizeRange.first).toFloat() / 100f
    params.mountainHeight = (Math.random() * (climate.mountainHeightRange.last - climate.mountainHeightRange.first) + climate.mountainHeightRange.first).toFloat() / 100f
    params.seaCount = (Math.random() * (climate.seaCountRange.last - climate.seaCountRange.first) + climate.seaCountRange.first).toInt()
    params.seaSize = (Math.random() * (climate.seaSizeRange.last - climate.seaSizeRange.first) + climate.seaSizeRange.first).toFloat() / 100f
    params.seaDepth = (Math.random() * (climate.seaDepthRange.last - climate.seaDepthRange.first) + climate.seaDepthRange.first).toFloat() / 100f
    params.erosion = (Math.random() * (climate.erosionRange.last - climate.erosionRange.first) + climate.erosionRange.first).toFloat() / 100f
    params.rainRange = (Math.random() * (climate.rainRange.last - climate.rainRange.first) + climate.rainRange.first).toFloat() / 10f*/

    params.mountainCount = if (params.mountainCountHigh) climate.mountainCountRange.last else climate.mountainCountRange.first
    params.mountainSize = (if (params.mountainSizeHigh) climate.mountainSizeRange.last else climate.mountainSizeRange.first).toFloat() / 100f
    params.mountainHeight = (if (params.mountainHeightHigh) climate.mountainHeightRange.last else climate.mountainSizeRange.first).toFloat() / 100f
    params.seaCount = if (params.seaCountHigh) climate.seaCountRange.last else climate.seaCountRange.first
    params.seaSize = (if (params.seaSizeHigh) climate.seaSizeRange.last else climate.seaSizeRange.first).toFloat() / 100f
    params.seaDepth = (if (params.seaDepthHigh) climate.seaDepthRange.last else climate.seaDepthRange.first).toFloat() / -100f           // inverted size
    params.erosion = (if (params.erosionHigh) climate.erosionRange.last else climate.erosionRange.first).toFloat() / 100f
    params.rain = (if (params.rainHigh) climate.rainRange.last else climate.rainRange.first).toFloat() / 10f

    params.groundColors = climate.groundColors
    params.seaColors = climate.seaColors

    val averageSize = (params.mapWidth + params.mapHeight).toFloat() / 2
    val ratio = (averageSize / params.npts)

    // TODO ratio for rainRange is still inappropriate
    params.rain *= ratio
    params.mountainSize *= averageSize
    params.seaSize *= averageSize
}

/*fun heightColor(geoFace: GeoFace): Color = when {
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
}*/

fun <T> timeAndResult(msg: String = "", block: () -> T): T {
    val time = Date().getTime()
    val ret = block()
    if (msg.isEmpty()) printConsole(" - total time ${Date().getTime() - time} ms") else printConsole("$msg (${Date().getTime() - time} ms)")
    return ret
}


fun GroupElement.toggleButton(name: String, call: () -> Unit, toggled: Boolean, disabled: Boolean): GroupElement {
    return g {
        setAttribute("style", if (!disabled) "cursor:pointer" else "cursor:not-allowed")
        rect {
            width = 125
            height = 22
            rx = 3
            ry = 3
            fill = if (disabled) lightgray else white
            stroke = black
            strokeWidth = if (toggled) "2" else "1"
        }
        text {
            if (disabled) fill = darkgray
            x = 10
            y = 16
            text = name
            if (toggled) setAttribute("style", "font-weight:bold")
        }
        if (!disabled) {
            on("click") {
                call.invoke()
                svg { drawInterface() }
            }
        }
    }
}

fun GroupElement.toggleButton(name: String, property: KMutableProperty0<Boolean>, call: () -> Unit): GroupElement {
    return g {
        setAttribute("style", "cursor:pointer")
        rect {
            width = 125
            height = 22
            rx = 3
            ry = 3
            fill = white
            stroke = black
            strokeWidth = if (property.get()) "2" else "1"
        }
        text {
            x = 10
            y = 16
            text = name
            if (property.get()) setAttribute("style", "font-weight:bold")
        }
        on("click") {
            property.set(!property.get())
            call.invoke()
            svg { drawInterface() }
        }
    }
}

fun GroupElement.console(xOffset: Int): TextElement {
    var ret: TextElement? = null
    g {
        rect {
            width = 300
            height = params.mapHeight - (2 * params.interfaceMarginH)
            rx = 3
            ry = 3
            fill = white
            stroke = black
        }
        ret = text {
            x = 10
            y = 16
            text = ""
        }
    }.apply { transform { translate(x = xOffset) } }
    return ret!!
}

fun SVGElement.drawInterface() {
    if (interfaceSvg != null) {
        svg { removeChild(interfaceSvg!!.element) }
    }
    interfaceSvg = g {
        transform { translate(params.mapWidth + params.interfaceMarginW, params.interfaceMarginH) }

        g {
            rect {
                width = 155
                height = 260
                rx = 3
                ry = 3
                fill = white
                stroke = grey
                strokeWidth = "1"
            }
            text {
                x = 20
                y = -5
                text = "GENERATION"
                setAttribute("style", "font-weight:bold")
            }
            g {
                toggleButton("Generate New", { buildFinalFantasyMap() }, false, false)
                toggleButton("1'000 Faces", { params.npts = 500; prepare(true) }, params.npts == 500, params.npts == 500).apply { transform { translate(y = 50) } }
                toggleButton("2'000 Faces", { params.npts = 1000; prepare(true) }, params.npts == 1000, params.npts == 1000).apply { transform { translate(y = 80) } }
                toggleButton("10'000 Faces", { params.npts = 5000; prepare(true) }, params.npts == 5000, params.npts == 5000).apply { transform { translate(y = 110) } }
                toggleButton("20'000 Faces", { params.npts = 10000; prepare(true) }, params.npts == 10000, params.npts == 10000).apply { transform { translate(y = 140) } }
                toggleButton("40'000 Faces", { params.npts = 20000; prepare(true) }, params.npts == 20000, params.npts == 20000).apply { transform { translate(y = 170) } }
                toggleButton("100'000 Faces", { params.npts = 50000; prepare(true) }, params.npts == 50000, params.npts == 50000).apply { transform { translate(y = 200) } }
            }.apply { transform { translate(x = 15, y = 16) } }
        }

        g {
            rect {
                width = 155
                height = 260
                rx = 3
                ry = 3
                fill = white
                stroke = grey
                strokeWidth = "1"
            }
            text {
                x = 10
                y = -5
                text = "CUSTOMIZATION"
                setAttribute("style", "font-weight:bold")
            }
            g {
                toggleButton("More relief", params::mountainCountHigh, { prepare(false) })
                toggleButton("Wider relief", params::mountainSizeHigh, { prepare(false) }).apply { transform { translate(y = 30) } }
                toggleButton("Taller relief", params::mountainHeightHigh, { prepare(false) }).apply { transform { translate(y = 60) } }
                toggleButton("More sea", params::seaCountHigh, { prepare(false) }).apply { transform { translate(y = 90) } }
                toggleButton("Wider sea", params::seaSizeHigh, { prepare(false) }).apply { transform { translate(y = 120) } }
                toggleButton("Deeper sea", params::seaDepthHigh, { prepare(false) }).apply { transform { translate(y = 150) } }
                toggleButton("Softer ground", params::erosionHigh, { prepare(false) }).apply { transform { translate(y = 180) } }
                toggleButton("More rain", params::rainHigh, { prepare(false) }).apply { transform { translate(y = 210) } }
            }.apply { transform { translate(x = 15, y = 16) } }
        }.apply { transform { translate(x = 180) } }

        g {
            rect {
                width = 155
                height = 140
                rx = 3
                ry = 3
                fill = white
                stroke = grey
                strokeWidth = "1"
            }
            text {
                x = 24
                y = -5
                text = "TEMPLATES"
                setAttribute("style", "font-weight:bold")
            }
            g {
                toggleButton("Sea side", { climate = temperate; prepare(false) }, climate == temperate, climate == temperate)
                toggleButton("Desert", { climate = desert; prepare(false) }, climate == desert, climate == desert).apply { transform { translate(y = 30) } }
                toggleButton("Fjord", { climate = fjord; prepare(false) }, climate == fjord, climate == fjord).apply { transform { translate(y = 60) } }
                toggleButton("Mountains", { climate = snowy; prepare(false) }, climate == snowy, climate == snowy).apply { transform { translate(y = 90) } }
            }.apply { transform { translate(x = 15, y = 16) } }
        }.apply { transform { translate(x = 180, y = 300) } }

        g {
            rect {
                width = 155
                height = 170
                rx = 3
                ry = 3
                fill = white
                stroke = grey
                strokeWidth = "1"
            }
            text {
                x = 20
                y = -5
                text = "PROCESSING"
                setAttribute("style", "font-weight:bold")
            }
            g {
                toggleButton("Step 1 - Relief", { generationStep = 1; prepare(false) }, generationStep == 1, generationStep == 1)
                toggleButton("Step 2 - Water", { generationStep = 2; prepare(false) }, generationStep == 2, generationStep == 2).apply { transform { translate(y = 30) } }
                toggleButton("Step 3 - Erosion", { generationStep = 3; prepare(false) }, generationStep == 3, generationStep == 3).apply { transform { translate(y = 60) } }
                toggleButton("Step 4 - Coasts", { generationStep = 4; prepare(false) }, generationStep == 4, generationStep == 4).apply { transform { translate(y = 90) } }
                toggleButton("Step 5 - Rivers", { generationStep = 5; prepare(false) }, generationStep == 5, generationStep == 5).apply { transform { translate(y = 120) } }
            }.apply { transform { translate(x = 15, y = 16) } }
        }.apply { transform { translate(y = 300) } }

        g {
            rect {
                width = 155
                height = 170
                rx = 3
                ry = 3
                fill = white
                stroke = grey
                strokeWidth = "1"
            }
            text {
                x = 50
                y = -5
                text = "TOOLS"
                setAttribute("style", "font-weight:bold")
            }
            g {
                toggleButton("Show Faces", params::showFaces, { drawStep() })
                toggleButton("Show Rivers", params::showRivers, { drawStep() }).apply { transform { translate(y = 30) } }
                toggleButton("Show Coasts", params::showCoasts, { drawStep() }).apply { transform { translate(y = 60) } }
                //toggleButton("Show Relief", params::showRelief, { prepare(false) }).apply { transform { translate(y = 90) } }
            }.apply { transform { translate(x = 15, y = 16) } }
        }.apply { transform { translate(y = 510) } }


        //console = console(xOffset = 138)
    }
}

fun printConsole(out: String) {
    println(out)
}
