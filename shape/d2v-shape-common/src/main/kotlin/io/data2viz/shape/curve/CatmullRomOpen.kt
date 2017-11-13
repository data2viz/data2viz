package io.data2viz.shape.curve

import io.data2viz.path.PathAdapter
import io.data2viz.shape.Curve
import io.data2viz.shape.epsilon
import kotlin.math.pow
import kotlin.math.sqrt

class CatmullRomOpen(override val context: PathAdapter, val alpha: Double = 0.5) : Curve {

    private var x0 = -1.0
    private var y0 = -1.0
    private var x1 = -1.0
    private var y1 = -1.0
    private var x2 = -1.0
    private var y2 = -1.0
    private var _l01_a = 0.0
    private var _l12_a = 0.0
    private var _l23_a = 0.0
    private var _l01_2a = 0.0
    private var _l12_2a = 0.0
    private var _l23_2a = 0.0

    private var lineStatus = -1
    private var pointStatus = -1

    override fun areaStart() {
        lineStatus = 0
    }

    override fun areaEnd() {
        lineStatus = -1
    }

    override fun lineStart() {
        x0 = -1.0
        y0 = -1.0
        x1 = -1.0
        y1 = -1.0
        x2 = -1.0
        y2 = -1.0
        _l01_a = 0.0
        _l12_a = 0.0
        _l23_a = 0.0
        _l01_2a = 0.0
        _l12_2a = 0.0
        _l23_2a = 0.0
        pointStatus = 0
    }

    override fun lineEnd() {
        if (lineStatus > -1) {
            if (lineStatus > 0) {
                context.closePath()
            }
            lineStatus = 1 - lineStatus
        }
    }

    // TODO : inherit from CatmullRom
    private fun curve(x: Double, y: Double) {
        var _x1 = x1
        var _y1 = y1
        var _x2 = x2
        var _y2 = y2

        if (_l01_a > epsilon) {
            val a = 2 * _l01_2a + 3 * _l01_a * _l12_a + _l12_2a
            val n = 3 * _l01_a * (_l01_a + _l12_a)
            _x1 = (x1 * a - x0 * _l12_2a + x2 * _l01_2a) / n
            _y1 = (y1 * a - y0 * _l12_2a + y2 * _l01_2a) / n
        }

        if (_l23_a > epsilon) {
            val b = 2 * _l23_2a + 3 * _l23_a * _l12_a + _l12_2a
            val m = 3 * _l23_a * (_l23_a + _l12_a)
            _x2 = (x2 * b + x1 * _l23_2a - x * _l12_2a) / m
            _y2 = (y2 * b + y1 * _l23_2a - y * _l12_2a) / m
        }

        context.bezierCurveTo(_x1, _y1, _x2, _y2, x2, y2)
    }

    override fun point(x: Double, y: Double) {
        if (pointStatus > 0) {
            val x23 = x2 - x
            val y23 = y2 - y
            _l23_2a = (x23 * x23 + y23 * y23).pow(alpha)
            _l23_a = sqrt(_l23_2a)
        }
        when (pointStatus) {
            0 -> pointStatus = 1
            1 -> pointStatus = 2
            2 -> {
                pointStatus = 3
                if (lineStatus > 0) context.lineTo(x2, y2) else context.moveTo(x2, y2)
            }
            3 -> {
                pointStatus = 4
                curve(x, y)
            }
            else -> curve(x, y)
        }

        _l01_a = _l12_a
        _l12_a = _l23_a
        _l01_2a = _l12_2a
        _l12_2a = _l23_2a
        x0 = x1
        x1 = x2
        x2 = x
        y0 = y1
        y1 = y2
        y2 = y
    }
}