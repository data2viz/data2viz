package io.data2viz.geo.geometry.path

import io.data2viz.geo.stream.Stream
import io.data2viz.math.TAU
import io.data2viz.geom.Path
import io.data2viz.geo.geojson.GeoPath

/**
 * This stream is the last operation of a projection: using a Path to draw the
 * GeoJson objects on a path.
 *
 * All polygons, and lines produce moveTo and lineTo calls on Path.
 * GeoJson points produce `arc` call to render a circle.
 *
 * @see GeoPath
 */
internal class PathStream(private val context: Path) : Stream {

    /**
     * Radius of the circle used to render a GeoJson Point
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