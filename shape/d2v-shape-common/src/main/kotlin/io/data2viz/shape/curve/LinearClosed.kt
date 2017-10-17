package io.data2viz.shape.curve

import io.data2viz.path.PathAdapter
import io.data2viz.shape.Curve

class LinearClosed(override val context: PathAdapter) : Curve {

    private var status = 0

    override fun areaStart() {}
    override fun areaEnd() {}

    override fun lineStart() {
        status = 0
    }

    override fun lineEnd() {
        if (status > 0) {
            context.closePath()
        }
    }

    override fun point(x: Double, y: Double) {
        if (status > 0) {
            context.lineTo(x, y)
        } else {
            status = 1
            context.moveTo(x,y)
        }
    }
}
