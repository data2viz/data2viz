package io.data2viz.shape.curve

import io.data2viz.geom.Path
import io.data2viz.shape.Curve


class Linear(override val path: Path) : Curve {

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
            path.closePath()
        }
        if (lineStatus != -1) lineStatus = 1 - lineStatus
    }

    override fun point(x: Double, y: Double) {
        if (pointStatus == 0) {
            pointStatus = 1
            if (lineStatus > 0) path.lineTo(x, y) else path.moveTo(x, y)
            return
        }
        if (pointStatus == 1) {
            pointStatus = 2
        }
        path.lineTo(x, y)
    }
}

