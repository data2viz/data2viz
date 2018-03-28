package io.data2viz.hexbin

import io.data2viz.core.Extent
import io.data2viz.core.Point
import io.data2viz.math.THIRDPI
import io.data2viz.path.PathAdapter
import kotlin.math.abs
import kotlin.math.cos
import kotlin.math.round
import kotlin.math.sin

val angles = listOf(.0, THIRDPI, 2 * THIRDPI, 3 * THIRDPI, 4 * THIRDPI, 5 * THIRDPI)

data class Bin(
    val points: MutableList<Point> = mutableListOf(),
    val x: Double,
    val y: Double
)

fun hexbinGenerator(init: HexbinGenerator.()->Unit) = HexbinGenerator().apply(init)
fun hexbinGenerator() = HexbinGenerator()

/**
 * Hexagonal binning is useful for aggregating data into a coarser representation for display.
 * For example, rather than rendering a scatterplot of tens of thousands of points, bin the points into a few hundred
 * hexagons to show the distribution. Hexbins can support a color encoding, area encoding, or both.
 * HexbinGenerator constructs a new default hexbin generator.
 */
class HexbinGenerator {

    /**
     * Sets the x-coordinate accessor to the specified function which defaults to { point, _, _ -> point.x }.
     * The x-coordinate accessor is used by hexbin to compute the x-coordinate of each point.
     */
    var x: (point: Point, index: Int, points: List<Point>) -> Double = { point, _, _ -> point.x }

    /**
     * Sets the y-coordinate accessor to the specified function which defaults to { point, _, _ -> point.y }.
     * The y-coordinate accessor is used by hexbin to compute the y-coordinate of each point.
     */
    var y: (point: Point, index: Int, points: List<Point>) -> Double = { point, _, _ -> point.y }

    /**
     * Sets the hexbin generator’s extent to the specified bounds [x0, y0, x1, y1] where x0 and y0 are the lower bounds
     * and x1 and y1 are the upper bounds.
     * The extent defaults to [0, 0, 1, 1].
     */
    var extent = Extent(.0, .0, 1.0, 1.0)

    /**
     * Sets the extent to the specified width [0, ?, width, ?].
     * This is a convenience method for setting the extent.
     */
    var width: Double
        get() = extent.x1 - extent.x0
        set(value) {
            extent.x0 = .0
            extent.x1 = value
        }

    /**
     * Sets the extent to the specified height [?, 0, ?, height].
     * This is a convenience method for setting the extent.
     */
    var height: Double
        get() = extent.y1 - extent.y0
        set(value) {
            extent.y0 = .0
            extent.y1 = value
        }

    private var dx = 2.0 * sin(THIRDPI)
    private var dy = 1.5
    private var _radius = 1.0

    /**
     * Sets the radius of the hexagon to the specified number which defaults to 1.0.
     * The hexagons are pointy-topped (rather than flat-topped);
     * the width of each hexagon is radius × 2 × sin(π / 3) and the height of each hexagon is radius × 3 / 2.
     */
    var radius: Double
        get() = _radius
        set(value) {
            _radius = value
            dx = _radius * 2 * sin(THIRDPI)
            dy = _radius * 1.5
        }

    /**
     * Bins the specified array of points, returning an array of hexagonal bins.
     * For each point in the specified points array, the x- and y-accessors are invoked to compute the x- and
     * y-coordinates of the point, which is then used to assign the point to a hexagonal bin.
     * If either the x- or y-coordinate is NaN, the point is ignored and will not be in any of the returned bins.
     *
     * Each bin in the returned array is an array containing the bin’s points. Only non-empty bins are returned;
     * empty bins without points are not included in the returned array. Each bin has these additional properties:
     *  - x: the x-coordinate of the center of the associated bin’s hexagon
     *  - y: the y-coordinate of the center of the associated bin’s hexagon
     *
     *  These x- and y-coordinates of the hexagon center can be used to render the hexagon at the appropriate location
     *  in conjunction with HexbinGenerator.hexagon.
     *  This method ignores the hexbin’s extent; it may return bins outside the extent if necessary to contain the
     *  specified points.
     */
    operator fun invoke(points: List<Point>): MutableList<Bin> {

        val binsById = mutableMapOf<String, Bin>()
        val bins = mutableListOf<Bin>()

        points.forEachIndexed { index, point ->
            var px = x(point, index, points)
            var py = y(point, index, points)
            if (px.isNaN() || py.isNaN()) return@forEachIndexed

            py /= dy
            var pj = round(py).toInt()
            px = (px / dx) - ((pj and 1) / 2.0)
            var pi = round(px).toInt()
            val py1 = py - pj

            if ((abs(py1) * 3.0) > 1.0) {
                val px1 = px - pi
                val pi2 = pi + (if (px < pi) -1 else 1) / 2
                val pj2 = pj + (if (py < pj) -1 else 1)
                val px2 = px - pi2
                val py2 = py - pj2
                if (px1 * px1 + py1 * py1 > px2 * px2 + py2 * py2) {
                    pi = pi2 + (if ((pj and 1) != 0) 1 else -1) / 2
                    pj = pj2
                }
            }

            val id = "$pi-$pj"
            var bin = binsById[id]
            if (bin != null) bin.points.add(point)
            else {
                bin = Bin(mutableListOf(point), (pi + ((pj and 1) / 2.0)) * dx, pj * dy)
                binsById[id] = bin
                bins.add(bin)
            }
        }

        return bins
    }

    private fun hexagon(radius: Double): List<Point> {
        var x0 = .0
        var y0 = .0

        return angles.map { angle ->
            val x1 = sin(angle) * radius
            val y1 = -cos(angle) * radius
            val px = x1 - x0
            val py = y1 - y0
            x0 = x1
            y0 = y1
            Point(px, py)
        }
    }

    /**
     * Build the path for the hexagon centered at the given origin Point.
     * If radius is not specified, the hexbin’s current radius is used.
     * If radius is specified, a hexagon with the specified radius is returned; this is useful for area-encoded bivariate
     * hexbins.
     */
    fun hexagon(path: PathAdapter, origin: Point, radius: Double? = null) {
        val hex = hexagon(if (radius == null) _radius else radius)
        path.moveTo(origin.x + hex[0].x, origin.y + hex[0].y)
        (1 until hex.size).forEach { index ->
            path.lineTo(origin.x + hex[index].x, origin.y + hex[index].y)
        }
        path.closePath()
    }

    /**
     * Returns an array of points representing the centers of every hexagon in the extent.
     */
    fun centers(): MutableList<Point> {
        val centers = mutableListOf<Point>()
        var j = round(extent.y0 / dy).toInt()
        val i = round(extent.x0 / dx).toInt()

        val widthLimit = extent.x1 + (dx / 2)
        val heightLimit = extent.y1 + _radius

        var _y = j * dy
        while (_y < heightLimit) {
            var _x = (i * dx) + ((j and 1).toDouble() * (dx / 2.0))
            while (_x < widthLimit) {
                centers.add(Point(_x, _y))
                _x += dx
            }
            _y += dy
            j++
        }
        return centers
    }
    /*
    for (var y = j * dy; y < y1 + r; y += dy, ++j) {
      for (var x = i * dx + (j & 1) * dx / 2; x < x1 + dx / 2; x += dx) {
        centers.push([x, y]);
      }
    }
     */

    /**
     * Returns an path representing the hexagonal mesh that covers the extent; the returned path is intended to be stroked.
     * The mesh may extend slightly beyond the extent and may need to be clipped.
     */
    fun mesh(path: PathAdapter) {
        val fragment = hexagon(_radius).subList(0, 4)
        val centers = centers()
        return centers.forEach { center ->
            path.moveTo(center.x + fragment[0].x, center.y + fragment[0].y)
            (1 until 4).forEach { index ->
                path.lineTo(center.x + fragment[index].x, center.y + fragment[index].y)
            }
            path.closePath()
        }
    }
}
