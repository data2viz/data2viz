package io.data2viz.shape.curve

import io.data2viz.path.PathAdapter
import io.data2viz.shape.Curve

class CardinalOpen(override val context: PathAdapter, tension: Double = 0.0) : Curve {

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
        if (lineStatus > 0) context.closePath()
        lineStatus = 1 - lineStatus
    }

    // TODO : non specific, inherit from Cardinal
    private fun curve(x: Double, y: Double) {
        context.bezierCurveTo(
                x1 + k * (x2 - x0),
                y1 + k * (y2 - y0),
                x2 + k * (x1 - x),
                y2 + k * (y1 - y),
                x2,
                y2
        )
    }

    override fun point(x: Double, y: Double) {
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
        x0 = x1
        x1 = x2
        x2 = x
        y0 = y1
        y1 = y2
        y2 = y
    }
}