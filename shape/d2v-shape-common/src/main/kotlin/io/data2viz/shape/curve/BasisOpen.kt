package io.data2viz.shape.curve

import io.data2viz.path.PathAdapter
import io.data2viz.shape.Curve

class BasisOpen(override val context: PathAdapter): Curve {

    private var x0 = -1.0
    private var y0 = -1.0
    private var x1 = -1.0
    private var y1 = -1.0

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
        pointStatus = 0
    }

    override fun lineEnd() {
        if (lineStatus > 0) context.closePath()
        lineStatus = 1 - lineStatus
    }

    // TODO : non specific, inherit from basis
    private fun curve(x: Double, y: Double){
        context.bezierCurveTo(
                (2 * x0 + x1) / 3,
                (2 * y0 + y1) / 3,
                (x0 + 2 * x1) / 3,
                (y0 + 2 * y1) / 3,
                (x0 + 4 * x1 + x) / 6,
                (y0 + 4 * y1 + y) / 6
        )
    }

    override fun point(x: Double, y: Double) {
        when (pointStatus) {
            0 -> {
               pointStatus = 1
            }
            1 -> {
                pointStatus = 2
            }
            2 -> {
                pointStatus = 3
                val _x = (x0 + 4 * x1 + x) / 6
                val _y = (y0 + 4 * y1 + y) / 6
                if (lineStatus > 0) context.lineTo(_x, _y) else context.moveTo(_x, _y)
            }
            3 -> {
                pointStatus = 4
                curve(x, y)
            }
            else -> {
                curve(x, y)
            }
        }
        x0 = x1
        x1 = x
        y0 = y1
        y1 = y
    }
}