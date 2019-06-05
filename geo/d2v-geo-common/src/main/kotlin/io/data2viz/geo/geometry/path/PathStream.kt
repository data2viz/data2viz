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
internal class PathStream(private val path: Path) : Stream {


    enum class PathCmd {
        MOVE,
        LINE,
        POINT
    }

    /**
     * Radius of the circle used to render a GeoJson Point
     */
    var pointRadius = 4.5

    private var line = false

    private var point = PathCmd.POINT

    override fun polygonStart() {
        line = true
    }

    override fun polygonEnd() {
        line = false
    }

    override fun lineStart() {
        point = PathCmd.MOVE
    }

    override fun lineEnd() {
        if (line) path.closePath()
        point = PathCmd.POINT
    }

    /**
     * Process a Point. Depending of the current draw path
     * it results in different calls on the Path.
     */
    override fun point(x: Double, y: Double, z: Double) {
        when (point) {
            PathCmd.MOVE -> {
                path.moveTo(x, y)
                point = PathCmd.LINE
            }
            PathCmd.LINE -> path.lineTo(x, y)
            PathCmd.POINT ->  {
                path.moveTo(x + pointRadius, y)
                path.arc(x, y, pointRadius, 0.0, TAU, false)
            }
        }
    }
}