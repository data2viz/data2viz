package io.data2viz.examples.geo

import io.data2viz.color.colors
import io.data2viz.geo.path.geoPath
import io.data2viz.geo.projection.orthographic
import io.data2viz.geojson.GeoJsonObject
import io.data2viz.viz.PathNode
import io.data2viz.viz.Viz
import io.data2viz.viz.viz
import kotlin.math.min

fun geoViz(world: GeoJsonObject): Viz = viz {
    width = 960.0
    height = 700.0

    // OUTER GLOBE
    val projectionOuter = orthographic {
        translate = doubleArrayOf(width / 2.0, height / 2.0)
        scale = min(width, height) / 2.2
    }

    val pathOuter = PathNode().apply {
        stroke = colors.black
        strokeWidth = 2.0
        fill = colors.whitesmoke
    }

    var geoPathOuter = geoPath(projectionOuter, pathOuter)

    geoPathOuter.path(world)
    add(pathOuter)

    onFrame {
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