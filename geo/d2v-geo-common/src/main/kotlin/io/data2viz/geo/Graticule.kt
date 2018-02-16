package io.data2viz.geo

import io.data2viz.core.range
import io.data2viz.geo.projection.Extent
import io.data2viz.path.epsilon
import kotlin.math.abs
import kotlin.math.ceil

// TODO : use Extent
fun graticule() = graticule {}

fun graticule(init: Graticule.() -> Unit): Graticule {
    val g = Graticule()
    g.extentMajor = Extent(-180.0, -90.0 + epsilon, 180.0, 90.0 - epsilon)
    g.extentMinor = Extent(-180.0, -80.0 - epsilon, 180.0, 80.0 + epsilon)

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

    var precision = 2.5
        set(value) {
            minorX = graticuleX(minorExtent.y0, minorExtent.y1, 90.0)
            minorY = graticuleY(minorExtent.x0, minorExtent.x1, precision)
            majorX = graticuleX(majorExtent.y0, majorExtent.y1, 90.0)
            majorY = graticuleY(majorExtent.x0, majorExtent.x1, precision)
        }

    var extent: Extent
        get() = minorExtent
        set(value) {
            extentMajor = value
            extentMinor = value
        }

    var extentMajor: Extent
        get() = majorExtent
        set(value) {
            majorExtent = value
            reorderExtent(majorExtent)
            precision = precision
        }

    var extentMinor: Extent
        get() = minorExtent
        set(value) {
            minorExtent = value
            reorderExtent(minorExtent)
            precision = precision
        }

    var stepMajor: DoubleArray
        get() = doubleArrayOf(majorStepX, majorStepY)
        set(value) {
            majorStepX = value[0]
            majorStepY = value[1]
        }

    var stepMinor: DoubleArray
        get() = doubleArrayOf(minorStepX, minorStepY)
        set(value) {
            minorStepX = value[0]
            minorStepY = value[1]
        }

    var step: DoubleArray
        get() = stepMinor
        set(value) {
            stepMajor = value
            stepMinor = value
        }

    fun graticule() = MultiLineString(_lines().map { it.map { doubleArrayOf(it[0], it[1]) } })

    fun lines() = _lines().map { LineString(it.map { doubleArrayOf(it[0], it[1]) }) }

    fun outline(): Polygon {
        val coords = majorX(majorExtent.x0).toMutableList()
        coords += majorY(majorExtent.y1).subList(1, majorY(majorExtent.y1).lastIndex)
        coords += majorX(majorExtent.x1).asReversed().subList(1, majorX(majorExtent.x1).lastIndex)
        coords += majorY(majorExtent.y0).asReversed().subList(1, majorY(majorExtent.y0).lastIndex)

        return Polygon(listOf(coords.map { doubleArrayOf(it[0], it[1]) }))
    }

    private fun _lines(): List<List<DoubleArray>> {
        val lines = range(ceil(majorExtent.x0 / majorStepX) * majorStepX, majorExtent.x1, majorStepX).map(majorX).toMutableList()
        lines += range(ceil(majorExtent.y0 / majorStepY) * majorStepY, majorExtent.y1, majorStepY).map(majorY)
        lines += range(ceil(minorExtent.x0 / minorStepX) * minorStepX, minorExtent.x1, minorStepX).filter { abs(it % majorStepX) > epsilon }.map(
            minorX
        )
        lines += range(ceil(minorExtent.y0 / minorStepY) * minorStepY, minorExtent.y1, minorStepY).filter { abs(it % majorStepY) > epsilon }.map(
            minorY
        )

        return lines
    }

    private fun graticuleX(y0: Double, y1: Double, dy: Double): (Double) -> List<DoubleArray> {
        val y = range(y0, y1 - epsilon, dy).toMutableList()
        y += y1
        return { x: Double -> y.map { doubleArrayOf(x, it) } }
    }

    private fun graticuleY(x0: Double, x1: Double, dx: Double): (Double) -> List<DoubleArray> {
        val x = range(x0, x1 - epsilon, dx).toMutableList()
        x += x1
        return { y: Double -> x.map { doubleArrayOf(it, y) } }
    }
}