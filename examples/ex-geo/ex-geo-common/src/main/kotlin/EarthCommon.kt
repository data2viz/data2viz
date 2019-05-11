package io.data2viz.examples.geo

import io.data2viz.color.Colors
import io.data2viz.geo.path.geoPath
import io.data2viz.geo.projection.*
import io.data2viz.geojson.GeoJsonObject
import io.data2viz.math.deg
import io.data2viz.viz.PathNode
import io.data2viz.viz.Viz
import io.data2viz.viz.viz
import kotlin.math.roundToInt


val allProjections = hashMapOf(
    "albers" to albersProjection(),
    "albersUSA" to alberUSAProjection(),
    "azimuthalEqualArea" to azimuthalEqualAreaProjection(),
    "azimuthalEquidistant" to azimuthalEquidistant(),
    "conicConformal" to conicConformalProjection(),
    "conicEqual" to conicEqualAreaProjection(),
    "conicEquidistant" to conicEquidistantProjection(),
    "equalEarth" to equalEarthProjection(),
    "equirectangular" to equirectangularProjection(),
    "gnomonic" to gnomonicProjection(),
    "identity" to identityProjection(),
    "mercator" to mercatorProjection(),
    "naturalEarth1" to naturalEarth1Projection(),
    "orthographic" to orthographicProjection(),
    "stereographic" to stereographicProjection(),
    "transverseMercator" to transverseMercatorProjection()
)
val allProjectionsNames = allProjections.keys.toList()

val allFiles = listOf(
    "world-110m.geojson",
    "world-110m-30percent.json",
    "world-110m-50percent.json",
    "world-110m-70percent.json"
)

val defaultFileIndex = allFiles.indexOf("world-110m-30percent.json")
val defaultProjectionIndex = allProjectionsNames.indexOf("orthographic")

fun geoViz(world: GeoJsonObject, projectionName: String, vizWidth:Double = 960.0, vizHeight:Double = 700.0): Viz {



    val projectionOuter = allProjections[projectionName]
    projectionOuter!!.translate = doubleArrayOf(vizWidth / 2.0, vizHeight / 2.0)


    return viz {
        width = vizWidth
        height = vizHeight

        val fps = text {
            x = 10.0
            y = 40.0
            fill = Colors.Web.red
        }

        text {
            x = 10.0
            y = 60.0
            fill = Colors.Web.red
            textContent = projectionName
        }


        val pathOuter = PathNode().apply {
            stroke = Colors.Web.black
            strokeWidth = 1.0
            fill = Colors.Web.whitesmoke
        }

        var geoPathOuter = geoPath(projectionOuter, pathOuter)

        geoPathOuter.path(world)
        add(pathOuter)

        animation { now: Double ->

            FPS.eventuallyUpdate(now)

            if (FPS.value >= 0) {
                fps.textContent = "Internal FPS: ${FPS.value.roundToInt()}"
            }


            val rotate = geoPathOuter.projection.rotate
            rotate[0] += (0.5).deg
            rotate[1] = (-10.0).deg

            pathOuter.clearPath()
            geoPathOuter.path(world)
            geoPathOuter.projection.rotate = rotate
        }

        onResize { newWidth, newHeight ->

            width = newWidth
            height = newHeight

            geoPathOuter = geoPath(projectionOuter, pathOuter)
        }


    }
}

object FPS {
    val averageCount = 10
    var value = .0
    var count = 0
    var lastStart = Double.NaN

    /**
     * current: current time in ms.
     */
    fun eventuallyUpdate(current: Double) {
        if (lastStart == Double.NaN)
            lastStart = current
        if (count++ == averageCount) {
            val totalTime = current - lastStart
            value = 1.0e3 * averageCount / totalTime
            lastStart = current
            count = 0
        }
    }
}