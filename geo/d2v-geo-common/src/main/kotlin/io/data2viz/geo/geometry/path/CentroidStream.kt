package io.data2viz.geo.geometry.path

import io.data2viz.geo.stream.Stream
import kotlin.math.sqrt

internal class CentroidStream : Stream {
    private var _X0 = 0.0
    private var _Y0 = 0.0
    private var _Z0 = 0.0
    private var _X1 = 0.0
    private var _Y1 = 0.0
    private var _Z1 = 0.0
    private var _X2 = 0.0
    private var _Y2 = 0.0
    private var _Z2 = 0.0
    private var x00 = Double.NaN
    private var y00 = Double.NaN
    private var x0 = Double.NaN
    private var y0 = Double.NaN

    private var currentPoint: (Double, Double) -> Unit = ::centroidPoint
    private var currentLineStart: () -> Unit = ::centroidLineStart
    private var currentLineEnd: () -> Unit = ::centroidLineEnd

    internal fun result(): DoubleArray {
        val centroid = when {
            _Z2 != .0 -> doubleArrayOf(_X2 / _Z2, _Y2 / _Z2)
            _Z1 != .0 -> doubleArrayOf(_X1 / _Z1, _Y1 / _Z1)
            _Z0 != .0 -> doubleArrayOf(_X0 / _Z0, _Y0 / _Z0)
            else -> doubleArrayOf(Double.NaN, Double.NaN)
        }

        _X0 = 0.0
        _Y0 = 0.0
        _Z0 = 0.0
        _X1 = 0.0
        _Y1 = 0.0
        _Z1 = 0.0
        _X2 = 0.0
        _Y2 = 0.0
        _Z2 = 0.0

        return centroid
    }

    override fun point(x: Double, y: Double, z: Double) = currentPoint(x, y)
    override fun lineStart() = currentLineStart()
    override fun lineEnd() = currentLineEnd()
    override fun polygonStart() {
        currentLineStart = ::centroidRingStart
        currentLineEnd = ::centroidRingEnd
    }

    override fun polygonEnd() {
        currentPoint = ::centroidPoint
        currentLineStart = ::centroidLineStart
        currentLineEnd = ::centroidLineEnd
    }

    private fun centroidPoint(x: Double, y: Double) {
        _X0 += x
        _Y0 += y
        ++_Z0
    }

    private fun centroidLineStart() {
        currentPoint = ::centroidPointFirstLine
    }

    private fun centroidPointFirstLine(x: Double, y: Double) {
        currentPoint = ::centroidPointLine
        x0 = x
        y0 = y
        centroidPoint(x, y)
    }

    private fun centroidPointLine(x: Double, y: Double) {
        val dx = x - x0
        val dy = y - y0
        val dz = sqrt(dx * dx + dy * dy)
        _X1 += dz * (x0 + x) / 2.0
        _Y1 += dz * (y0 + y) / 2.0
        _Z1 += dz
        x0 = x
        y0 = y
        centroidPoint(x, y)
    }

    private fun centroidLineEnd() {
        currentPoint = ::centroidPoint
    }

    private fun centroidRingStart() {
        currentPoint = ::centroidPointFirstRing
    }

    private fun centroidRingEnd() = centroidPointRing(x00, y00)

    private fun centroidPointFirstRing(x: Double, y: Double) {
        currentPoint = ::centroidPointRing
        x00 = x
        y00 = y
        x0 = x
        y0 = y
        centroidPoint(x, y)
    }

    private fun centroidPointRing(x: Double, y: Double) {
        val dx = x - x0
        val dy = y - y0
        var dz = sqrt((dx * dx) + (dy * dy))

        _X1 += dz * (x0 + x) / 2.0
        _Y1 += dz * (y0 + y) / 2.0
        _Z1 += dz

        dz = y0 * x - x0 * y
        _X2 += dz * (x0 + x)
        _Y2 += dz * (y0 + y)
        _Z2 += dz * 3
        x0 = x
        y0 = y
        centroidPoint(x, y)
    }
}