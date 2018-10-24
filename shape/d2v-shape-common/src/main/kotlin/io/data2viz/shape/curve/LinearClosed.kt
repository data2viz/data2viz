package io.data2viz.shape.curve

import io.data2viz.geom.Path
import io.data2viz.shape.Curve

class LinearClosed(override val path: Path) : Curve {

    private var pointStatus = -1

    override fun areaStart() {}
    override fun areaEnd() {}

    override fun lineStart() {
        pointStatus = 0
    }

    override fun lineEnd() {
        if (pointStatus > 0) {
            path.closePath()
        }
    }

    override fun point(x: Double, y: Double) {
        if (pointStatus > 0) {
            path.lineTo(x, y)
        } else {
            pointStatus = 1
            path.moveTo(x,y)
        }
    }
}
