package io.data2viz.shape.curve

import io.data2viz.path.PathAdapter
import io.data2viz.shape.Curve

class Cardinal(override val context: PathAdapter, tension:Double = 0.0): Curve {

    private var x0 = -1.0
    private var y0 = -1.0
    private var x1 = -1.0
    private var y1 = -1.0
    private var x2 = -1.0
    private var y2 = -1.0

    private var lineStatus = -1
    private var pointStatus = -1

    private val k = (1.0 - tension) / 6.0

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
        pointStatus = 0
    }

    override fun lineEnd() {
        when (pointStatus) {
            2 -> context.lineTo(x2, y2)
            3 -> curve(x1, y1)
        }
        if (lineStatus > 0) context.closePath()
        lineStatus = 1 - lineStatus
    }

    private fun curve(x: Number, y: Number){
        context.bezierCurveTo(
                x1 + k * (x2 - x0),
                y1 + k * (y2 - y0),
                x2 + k * (x1 - x.toDouble()),
                y2 + k * (y1 - y.toDouble()),
                x2,
                y2
        )
    }

    override fun point(x: Number, y: Number) {
        when (pointStatus) {
            0 -> {
                pointStatus = 1
                if (lineStatus > 0) context.lineTo(x, y) else context.moveTo(x, y)
            }
            1 -> {
                pointStatus = 2
                x1 = x.toDouble()
                y1 = y.toDouble()
            }
            2 -> {
                pointStatus = 3
                curve(x, y)
            }
            else -> curve(x, y)
        }
        x0 = x1
        x1 = x2
        x2 = x.toDouble()
        y0 = y1
        y1 = y2
        y2 = y.toDouble()
    }
}