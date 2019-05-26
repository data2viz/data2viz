package io.data2viz.geo.geometry.path

import io.data2viz.geo.stream.Stream
import io.data2viz.math.TAU
import io.data2viz.geom.Path
import io.data2viz.geo.geojson.GeoPath

/**
 * Stream polygons and lines to to context as points and arcs
 *
 * @see GeoPath
 */
internal class PathStream(private val context: Path) : Stream {
    /**
     * If radius is specified, sets the radius used to display Point and MultiPoint geometries to the specified number.
     * If radius is not specified, returns the current radius accessor, which defaults to 4.5.
     * While the radius is commonly specified as a number constant,
     * it may also be specified as a function which is computed per feature,
     * being passed the any arguments passed to the path generator.
     */
    var pointRadius = 4.5

    private var line = false
    private var point = -1

    override fun polygonStart() {
        line = true
    }

    override fun polygonEnd() {
        line = false
    }

    override fun lineStart() {
        point = 0
    }

    override fun lineEnd() {
        if (line) context.closePath()
        point = -1
    }

    override fun point(x: Double, y: Double, z: Double) {
        when (point) {
            0 -> {
                context.moveTo(x, y)
                point = 1
            }
            1 -> context.lineTo(x, y)
            else -> {
                context.moveTo(x + pointRadius, y)
                context.arc(x, y, pointRadius, 0.0, TAU, false)
            }
        }
    }
}