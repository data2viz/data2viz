package io.data2viz.examples.geo

import io.data2viz.color.Colors
import io.data2viz.geo.path.geoPath
import io.data2viz.geo.projection.orthographic
import io.data2viz.geojson.GeoJsonObject
import io.data2viz.viz.PathNode
import io.data2viz.viz.Viz
import io.data2viz.viz.viz
import kotlin.math.min
import kotlin.math.roundToInt


fun geoViz(world: GeoJsonObject): Viz = viz {
    width = 960.0
    height = 700.0

    val fps = text {
        x = 40.0
        y = 40.0
        fill = Colors.Web.red
    }

    // OUTER GLOBE
    val projectionOuter = orthographic {
        translate = doubleArrayOf(width / 2.0, height / 2.0)
        scale = min(width, height) / 2.2
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
            fps.textContent = "${FPS.value.roundToInt()} FPS"
        }


        val rotate = geoPathOuter.projection.rotate
        rotate[0] += .5
        rotate[1] = -10.0

        pathOuter.clearPath()
        geoPathOuter.path(world)
        geoPathOuter.projection.rotate = rotate
    }

    onResize { newWidth, newHeight ->

        width = newWidth
        height = newHeight

        geoPathOuter = geoPath(orthographic {
            translate = doubleArrayOf(width / 2.0, height / 2.0)
            scale = min(width, height) / 2.2
        }, pathOuter)
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