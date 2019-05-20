package io.data2viz.examples.geo

import io.data2viz.color.Colors
import io.data2viz.geo.path.GeoPath
import io.data2viz.geo.path.geoPath
import io.data2viz.geo.projection.*
import io.data2viz.geojson.GeoJsonObject
import io.data2viz.geom.PathGeom
import io.data2viz.math.Angle
import io.data2viz.math.deg
import io.data2viz.time.Date
import io.data2viz.viz.PathNode
import io.data2viz.viz.Viz
import io.data2viz.viz.viz
import kotlin.math.roundToInt


val allProjections = hashMapOf(
    "albers" to albersProjection(),
    "albersUSA" to albersUSAProjection() {
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


fun geoViz(world: GeoJsonObject, projectionName: String, vizWidth: Double = 500.0, vizHeight: Double = 500.0): Viz {


    val geoProjection = allProjections[projectionName]!!
    geoProjection.x = vizWidth / 2.0
    geoProjection.y = vizHeight / 2.0

    val pathGeom = PathGeom()

    var geoPath = geoPath(geoProjection, pathGeom)

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


        PathNode(pathGeom).also {
            stroke = Colors.Web.black
            strokeWidth = 1.0
            fill = Colors.Web.whitesmoke
            add(it)
        }

        geoPath.drawPath(world)


        // don't rotate projections which not don't support rotations in d3
        val isNeedRotate = when (projectionName) {
            "albersUSA", "identity" -> false
            else -> true
        }


        if (isNeedRotate) {
            geoProjection.rotate = arrayOf(0.0.deg, 0.0.deg, 0.0.deg)
        }

        animation { now: Double ->

            FPS.eventuallyUpdate(now)

            if (FPS.value >= 0) {
                fps.textContent = "Internal FPS: ${FPS.value.roundToInt()}"
            }


            if (isNeedRotate) {
                rotateByTime(geoPath, pathGeom, world)
            }

        }

        onResize { newWidth, newHeight ->

            width = newWidth
            height = newHeight

            geoPath = geoPath(geoProjection, pathGeom)
        }


    }
}

private fun rotateByTime(
    geoPath: GeoPath,
    pathGeom: PathGeom,
    world: GeoJsonObject
) {

    val projection = geoPath.projection
    val rotate = projection.rotate


    val unixTime = Date().getTime()
    // Full rotation cycles per minute
    val fullRotationCyclesPerMinute = 6

    val minute = 1000 * 60
    val ratio = (unixTime % minute) / minute
    val angle = ratio * 360 * fullRotationCyclesPerMinute % 360
    // Rotate only x axys
    rotate[0] = angle.deg

    rotateByAngles(geoPath, rotate, pathGeom, world)
}

private fun rotateByAngles(
    geoPath: GeoPath,
    angles: Array<Angle>,
    pathGeom: PathGeom,
    world: GeoJsonObject
) {
    val projection = geoPath.projection
    projection.rotate = angles
    pathGeom.clearPath()
    geoPath.drawPath(world)
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