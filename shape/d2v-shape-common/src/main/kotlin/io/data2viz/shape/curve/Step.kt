package io.data2viz.shape.curve

import io.data2viz.path.PathAdapter
import io.data2viz.shape.Curve

// TODO : StepBefore, StepAfter, Step
abstract class AbstractStep(override val context: PathAdapter, changePoint: Double = 0.5) : Curve {

    private var x = -1.0
    private var y = -1.0

    private var lineStatus = -1
    private var pointStatus = -1

    private var _changePoint = changePoint

    override fun areaStart() {
        lineStatus = 0
    }

    override fun areaEnd() {
        lineStatus = -1
    }

    override fun lineStart() {
        x = -1.0
        y = -1.0
        pointStatus = 0
    }

    override fun lineEnd() {
        if (0 < _changePoint && _changePoint < 1 && pointStatus == 2) context.lineTo(x, y)
        if (lineStatus > 0) context.closePath()
        if (lineStatus >= 0) {
            _changePoint = 1 - _changePoint
            lineStatus = 1 - lineStatus
        }
    }

    override fun point(x: Double, y: Double) {
        when (pointStatus) {
            0 -> {
                pointStatus = 1
                if (lineStatus > 0) context.lineTo(x, y) else context.moveTo(x, y)
                this.x = x
                this.y = y
                return
            }
            1 -> pointStatus = 2
            else -> {
            }
        }
        if (_changePoint <= 0) {
            context.lineTo(this.x, y)
            context.lineTo(x, y)
        } else {
            val x1 = this.x * (1 - _changePoint) + x * _changePoint
            context.lineTo(x1, this.y)
            context.lineTo(x1, y)
        }
        this.x = x
        this.y = y
    }
}

class Step(override val context: PathAdapter) : AbstractStep(context, 0.5)
class StepBefore(override val context: PathAdapter) : AbstractStep(context, 0.0)
class StepAfter(override val context: PathAdapter) : AbstractStep(context, 1.0)