package io.data2viz.shape.curve

import io.data2viz.path.PathAdapter
import io.data2viz.shape.Curve


class Linear(override val context: PathAdapter) : Curve {

    private var pointStatus = -1
    private var lineStatus: Int = -1

    override fun areaStart() {
        lineStatus = 0
    }

    override fun areaEnd() {
        lineStatus = -1
    }

    override fun lineStart() {
        pointStatus = 0
    }

    override fun lineEnd() {
        if (lineStatus > 0 || (lineStatus != 0 && pointStatus == 1)) {
            context.closePath()
        }
        if (lineStatus != -1) lineStatus = 1 - lineStatus
    }

    override fun point(x: Double, y: Double) {
        if (pointStatus == 0) {
            pointStatus = 1
            if (lineStatus > 0) {
                context.lineTo(x, y)
            } else {
                context.moveTo(x, y)
            }
            return
        }
        if (pointStatus == 1) {
            pointStatus = 2
        }
        context.lineTo(x, y)
    }
}

