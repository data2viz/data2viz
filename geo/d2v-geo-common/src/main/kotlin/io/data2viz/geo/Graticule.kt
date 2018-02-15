package io.data2viz.geo

import io.data2viz.core.range
import io.data2viz.path.epsilon
import kotlin.math.abs
import kotlin.math.ceil

// TODO : use Extent
fun graticule() = graticule {}

fun graticule(init: Graticule.() -> Unit): Graticule {
    val g = Graticule()
    g.extentMajor = arrayOf(doubleArrayOf(-180.0, -90.0 + epsilon), doubleArrayOf(180.0, 90.0 - epsilon))
    g.extentMinor = arrayOf(doubleArrayOf(-180.0, -80.0 - epsilon), doubleArrayOf(180.0, 80.0 + epsilon))

    g.init()

    return g
}

class Graticule {
    private var x0 = Double.NaN
    private var x1 = Double.NaN
    private var _X0 = Double.NaN
    private var _X1 = Double.NaN
    private var y0 = Double.NaN
    private var y1 = Double.NaN
    private var _Y0 = Double.NaN
    private var _Y1 = Double.NaN
    private var dx = 10.0
    private var dy = 10.0
    private var _DX = 90.0
    private var _DY = 360.0
    private lateinit var x: (Double) -> List<DoubleArray>
    private lateinit var y: (Double) -> List<DoubleArray>
    private lateinit var _X: (Double) -> List<DoubleArray>
    private lateinit var _Y: (Double) -> List<DoubleArray>

    var precision = 2.5
        set(value) {
            x = graticuleX(y0, y1, 90.0)
            y = graticuleY(x0, x1, precision)
            _X = graticuleX(_Y0, _Y1, 90.0)
            _Y = graticuleY(_X0, _X1, precision)
        }

    var extent: Array<DoubleArray>
        get() = extentMinor
        set(value) {
            extentMajor = value
            extentMinor = value
        }

    var extentMajor: Array<DoubleArray>
        get() = arrayOf(doubleArrayOf(_X0, _Y0), doubleArrayOf(_X1, _Y1))
        set(value) {
            _X0 = value[0][0]
            _Y0 = value[0][1]
            _X1 = value[1][0]
            _Y1 = value[1][1]

            if (_X0 > _X1) {
                val t = _X0
                _X0 = _X1
                _X1 = t
            }

            if (_Y0 > _Y1) {
                val t = _Y0
                _Y0 = _Y1
                _Y1 = t
            }

            precision = precision
        }

    var extentMinor: Array<DoubleArray>
        get() = arrayOf(doubleArrayOf(x0, y0), doubleArrayOf(x1, y1))
        set(value) {
            x0 = value[0][0]
            y0 = value[0][1]
            x1 = value[1][0]
            y1 = value[1][1]

            if (x0 > x1) {
                val t = x0
                x0 = x1
                x1 = t
            }

            if (y0 > y1) {
                val t = y0
                y0 = y1
                y1 = t
            }

            precision = precision
        }

    var stepMajor: DoubleArray
        get() = doubleArrayOf(_DX, _DY)
        set(value) {
            _DX = value[0]
            _DY = value[1]
        }

    var stepMinor: DoubleArray
        get() = doubleArrayOf(dx, dy)
        set(value) {
            dx = value[0]
            dy = value[1]
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
        val coords = _X(_X0).toMutableList()
        coords += _Y(_Y1).subList(1, _Y(_Y1).lastIndex)
        coords += _X(_X1).asReversed().subList(1, _X(_X1).lastIndex)
        coords += _Y(_Y0).asReversed().subList(1, _Y(_Y0).lastIndex)

        return Polygon(listOf(coords.map { doubleArrayOf(it[0], it[1]) }))
    }

    private fun _lines(): List<List<DoubleArray>> {
        val lines = range(ceil(_X0 / _DX) * _DX, _X1, _DX).map(_X).toMutableList()
        lines += range(ceil(_Y0 / _DY) * _DY, _Y1, _DY).map(_Y)
        lines += range(ceil(x0 / dx) * dx, x1, dx).filter { abs(it % _DX) > epsilon }.map(x)
        lines += range(ceil(y0 / dy) * dy, y1, dy).filter { abs(it % _DY) > epsilon }.map(y)

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