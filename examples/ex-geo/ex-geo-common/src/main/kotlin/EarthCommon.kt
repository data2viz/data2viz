package io.data2viz.examples.geo

import io.data2viz.color.Colors
import io.data2viz.geo.projection.*
import io.data2viz.geojson.GeoJsonObject
import io.data2viz.math.deg
import io.data2viz.viz.*
import kotlin.math.roundToInt


val allProjections = hashMapOf(
    "albers" to albersProjection(),
    "albersUSA" to albersUSAProjection {
        scale = 500.0
    },
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
    "naturalEarth" to naturalEarthProjection(),
    "orthographic" to orthographicProjection(),
    "stereographic" to stereographicProjection(),
    "transverseMercator" to transverseMercatorProjection()
)
val allProjectionsNames = allProjections.keys.toList()

val allFiles = listOf(
    "graticule",
    "world-110m.geojson",
    "world-110m-30percent.json",
    "world-110m-50percent.json",
    "world-110m-70percent.json"
)

val projectionsToSingleFile = hashMapOf(
    "albersUSA" to "us-states.json"
)


val defaultFileIndex = allFiles.indexOf("world-110m-30percent.json")
val defaultProjectionIndex = allProjectionsNames.indexOf("orthographic")


val projection
    get() = geoPathNode.geoProjection

var isProjectionSupportTransformations: Boolean = true

lateinit var geoPathNode: GeoPathNode

internal fun geoViz(world: GeoJsonObject, projectionName: String, vizWidth: Double = 500.0, vizHeight: Double = 500.0): Viz {

    val projection = allProjections[projectionName]!!
    projection.translateX = vizWidth / 2.0
    projection.translateY = vizHeight / 2.0


    return viz {
        width = vizWidth
        height = vizHeight

        geoPathNode = GeoPathNode().apply {
            stroke = Colors.Web.black
            strokeWidth = 1.0
            fill = Colors.Web.whitesmoke
            geoProjection = projection
            geoData = world
            redrawPath()

        }

        add(geoPathNode)

        val fps = text {
            x = 10.0
            y = 15.0
            fill = Colors.Web.red
        }

        text {
            x = 10.0
            y = 30.0
            fill = Colors.Web.red
            textContent = projectionName
        }



        // don't rotate projections which not don't support rotations in d3
        isProjectionSupportTransformations = when (projectionName) {
            "albersUSA", "identity" -> false
            else -> true
        }





        if (isProjectionSupportTransformations) {
            geoPathNode.geoProjection.rotate(0.0.deg, 0.0.deg, 0.0.deg)
            geoPathNode.redrawPath()
        }


        animation { now: Double ->

            FPS.eventuallyUpdate(now)

            if (FPS.value >= 0) {
                fps.textContent = "Internal FPS: ${FPS.value.roundToInt()}"
            }


        }

        onResize { newWidth, newHeight ->

            width = newWidth
            height = newHeight

            geoPathNode.redrawPath()
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