package io.data2viz.shape.curve

import io.data2viz.path.PathAdapter
import io.data2viz.shape.Curve

class LinearClosed(override val context: PathAdapter) : Curve {

    private var pointStatus = -1

    override fun areaStart() {}
    override fun areaEnd() {}

    override fun lineStart() {
        pointStatus = 0
    }

    override fun lineEnd() {
        if (pointStatus > 0) {
            context.closePath()
        }
    }

    override fun point(x: Double, y: Double) {
        if (pointStatus > 0) {
            context.lineTo(x, y)
        } else {
            pointStatus = 1
            context.moveTo(x,y)
        }
    }
}
