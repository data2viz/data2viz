package io.data2viz.shape.curve

import io.data2viz.path.PathAdapter
import io.data2viz.shape.Curve

class BasisClosed(override val context: PathAdapter): Curve {

    private var x0 = -1.0
    private var y0 = -1.0
    private var x1 = -1.0
    private var y1 = -1.0
    private var x2 = -1.0
    private var y2 = -1.0
    private var x3 = -1.0
    private var y3 = -1.0
    private var x4 = -1.0
    private var y4 = -1.0

    private var pointStatus = -1

    override fun areaStart() {}

    override fun areaEnd() {}

    override fun lineStart() {
        x0 = -1.0
        y0 = -1.0
        x1 = -1.0
        y1 = -1.0
        x2 = -1.0
        y2 = -1.0
        x3 = -1.0
        y3 = -1.0
        x4 = -1.0
        y4 = -1.0
        pointStatus = 0
    }

    override fun lineEnd() {
        when (pointStatus) {
            1 -> {
                context.moveTo(x2, y2)
                context.closePath()
            }
            2 -> {
                context.moveTo((x2 + 2 * x3) / 3, (y2 + 2 * y3) / 3)
                context.lineTo((x3 + 2 * x2) / 3, (y3 + 2 * y2) / 3)
                context.closePath()
            }
            3 -> {
                point(x2, y2)
                point(x3, y3)
                point(x4, y4)
            }
        }
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
                x2 = x
                y2 = y
            }
            1 -> {
                pointStatus = 2
                x3 = x
                y3 = y
            }
            2 -> {
                pointStatus = 3
                x4 = x
                y4 = y
                context.moveTo((x0 + 4 * x1 + x) / 6, (y0 + 4 * y1 + y) / 6)
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