package io.data2viz.shape.curve

import io.data2viz.path.PathAdapter
import io.data2viz.shape.Curve


class Basis(override val context: PathAdapter): Curve {

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
        if (pointStatus == 3) {
            curve(x1,y1)
            context.lineTo(x1, y1)
        }
        if (pointStatus == 2) context.lineTo(x1, y1)

        if (lineStatus > 0 || (lineStatus != 0 && pointStatus == 1))
            context.closePath()
        lineStatus = 1 - lineStatus
    }

    private fun curve(x: Number, y: Number){
        context.bezierCurveTo(
                (2 * x0 + x1) / 3,
                (2 * y0 + y1) / 3,
                (x0 + 2 * x1) / 3,
                (y0 + 2 * y1) / 3,
                (x0 + 4 * x1 + x.toDouble()) / 6,
                (y0 + 4 * y1 + y.toDouble()) / 6
        )
    }

    override fun point(x: Number, y: Number) {
        if (pointStatus == 0) {
            pointStatus = 1
            if (lineStatus > 0) context.lineTo(x, y) else context.moveTo(x, y)
        } else if (pointStatus == 1) {
            pointStatus = 2
        } else if (pointStatus == 2) {
            pointStatus = 3
            context.lineTo((5 * x0 + x1) / 6, (5 * y0 + y1) / 6)
            curve(x, y)
        } else {
            curve(x, y)
        }
        x0 = x1
        x1 = x.toDouble()
        y0 = y1
        y1 = y.toDouble()
    }
}

