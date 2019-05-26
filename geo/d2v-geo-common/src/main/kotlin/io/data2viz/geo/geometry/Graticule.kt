package io.data2viz.geo.geometry

import io.data2viz.geom.Extent
import io.data2viz.math.range
import io.data2viz.geojson.LineString
import io.data2viz.geojson.MultiLineString
import io.data2viz.geojson.Polygon
import io.data2viz.math.EPSILON
import kotlin.math.abs
import kotlin.math.ceil


// TODO: Missed API: fun geoGraticule10()


/**
 * @see Graticule
 */
fun geoGraticule() = geoGraticule {}


/**
 * @see Graticule
 */
fun geoGraticule(init: Graticule.() -> Unit): Graticule {
    val g = Graticule()
    g.extentMajor = Extent(-180.0, -90.0 + EPSILON, 180.0, 90.0 - EPSILON)
    g.extentMinor = Extent(-180.0, -80.0 - EPSILON, 180.0, 80.0 + EPSILON)

    g.init()
    return g
}

private fun reorderExtent(extent: Extent) {
    if (extent.x0 > extent.x1) {
        val t = extent.x0
        extent.x0 = extent.x1
        extent.x1 = t
    }

    if (extent.y0 > extent.y1) {
        val t = extent.y0
        extent.y0 = extent.y1
        extent.y1 = t
    }
}

/**
 * Constructs a geometry generator for creating graticules:
 * a uniform grid of meridians and parallels for showing
 * projection distortion.
 * The default graticule has meridians and parallels every 10° between ±80° latitude;
 * for the polar regions, there are meridians every 90°.
 *
 * You [graticule] and [lines] for result
 */
class Graticule {
    private var minorExtent = Extent(Double.NaN, Double.NaN, Double.NaN, Double.NaN)
    private var majorExtent = Extent(Double.NaN, Double.NaN, Double.NaN, Double.NaN)

    private var minorStepX = 10.0
    private var minorStepY = 10.0
    private var majorStepX = 90.0
    private var majorStepY = 360.0

    private lateinit var minorX: (Double) -> List<DoubleArray>
    private lateinit var minorY: (Double) -> List<DoubleArray>
    private lateinit var majorX: (Double) -> List<DoubleArray>
    private lateinit var majorY: (Double) -> List<DoubleArray>



    /**
     * The precision for this graticule, in degrees which defaults to 2.5°.
     */
    var precision = 2.5
        set(value) {
            field = value
            minorX = graticuleX(minorExtent.y0, minorExtent.y1, 90.0)
            minorY = graticuleY(minorExtent.x0, minorExtent.x1, precision)
            majorX = graticuleX(majorExtent.y0, majorExtent.y1, 90.0)
            majorY = graticuleY(majorExtent.x0, majorExtent.x1, precision)
        }

    /**
     * Set: sets the major and minor extents of this graticule.
     * Get: returns the current minor extent, which defaults to ⟨⟨-180°, -80° - ε⟩, ⟨180°, 80° + ε⟩⟩.
     */
    var extent: Extent
        get() = minorExtent
        set(value) {
            extentMajor = value
            extentMinor = value
        }

    /**
     * Set: sets the major extent of this graticule.
     * Get: returns the current major extent, which defaults to ⟨⟨-180°, -90° + ε⟩, ⟨180°, 90° - ε⟩⟩.
     */
    var extentMajor: Extent
        get() = majorExtent
        set(value) {
            majorExtent = value
            reorderExtent(majorExtent)
            precision = precision
        }

    /**
     * Set: sets the minor extent of this graticule.
     * Get: returns the current minor extent, which defaults to ⟨⟨-180°, -80° - ε⟩, ⟨180°, 80° + ε⟩⟩.
     */
    var extentMinor: Extent
        get() = minorExtent
        set(value) {
            minorExtent = value
            reorderExtent(minorExtent)
            precision = precision
        }

    /**
     * Set: sets the major step for this graticule.
     * Get: returns the current major step, which defaults to ⟨90°, 360°⟩.
     */
    var stepMajor: DoubleArray
        get() = doubleArrayOf(majorStepX, majorStepY)
        set(value) {
            majorStepX = value[0]
            majorStepY = value[1]
        }

    /**
     * Set: sets the minor step for this graticule.
     * Get: returns the current minor step, which defaults to ⟨10°, 10°⟩.
     */
    var stepMinor: DoubleArray
        get() = doubleArrayOf(minorStepX, minorStepY)
        set(value) {
            minorStepX = value[0]
            minorStepY = value[1]
        }

    /**
     * Set: sets the major and minor step for this graticule.
     * Get: returns the current minor step, which defaults to ⟨10°, 10°⟩.
     */
    var step: DoubleArray
        get() = stepMinor
        set(value) {
            stepMajor = value
            stepMinor = value
        }

    /**
     * Returns a GeoJSON MultiLineString geometry object representing all meridians and parallels for this graticule.
     */
    fun graticule() = MultiLineString(buildLines().map { it.map { arrayOf(it[0], it[1]) }.toTypedArray() }.toTypedArray())

    /**
     * Returns an array of GeoJSON LineString geometry objects, one for each meridian or parallel for this graticule.
     */
    fun lines() = buildLines().map { LineString(it.map { arrayOf(it[0], it[1]) }.toTypedArray()) }

    /**
     * Returns a GeoJSON Polygon geometry object representing the outline of this graticule, i.e. along the
     * meridians and parallels defining its extent.
     */
    fun outline(): Polygon {
        val coordinates = majorX(majorExtent.x0).toMutableList()
        coordinates += majorY(majorExtent.y1).subList(1, majorY(majorExtent.y1).lastIndex)
        coordinates += majorX(majorExtent.x1).asReversed().subList(1, majorX(majorExtent.x1).lastIndex)
        coordinates += majorY(majorExtent.y0).asReversed().subList(1, majorY(majorExtent.y0).lastIndex)

        return Polygon(arrayOf(coordinates.map { arrayOf(it[0], it[1]) }.toTypedArray()))
    }

    private fun buildLines(): List<List<DoubleArray>> {
        val lines = range(ceil(majorExtent.x0 / majorStepX) * majorStepX, majorExtent.x1, majorStepX).map(majorX)
            .toMutableList()
        lines += range(ceil(majorExtent.y0 / majorStepY) * majorStepY, majorExtent.y1, majorStepY).map(majorY)
        lines += range(
            ceil(minorExtent.x0 / minorStepX) * minorStepX,
            minorExtent.x1,
            minorStepX
        ).filter { abs(it % majorStepX) > EPSILON }.map(
            minorX
        )
        lines += range(
            ceil(minorExtent.y0 / minorStepY) * minorStepY,
            minorExtent.y1,
            minorStepY
        ).filter { abs(it % majorStepY) > EPSILON }.map(
            minorY
        )

        return lines
    }

    private fun graticuleX(y0: Double, y1: Double, dy: Double): (Double) -> List<DoubleArray> {
        val y = range(y0, y1 - EPSILON, dy).toMutableList()
        y += y1
        return { x: Double -> y.map { doubleArrayOf(x, it) } }
    }

    private fun graticuleY(x0: Double, x1: Double, dx: Double): (Double) -> List<DoubleArray> {
        val x = range(x0, x1 - EPSILON, dx).toMutableList()
        x += x1
        return { y: Double -> x.map { doubleArrayOf(it, y) } }
    }
}