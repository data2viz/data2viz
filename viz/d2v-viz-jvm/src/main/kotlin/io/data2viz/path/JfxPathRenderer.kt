package io.data2viz.path

import io.data2viz.viz.JFxVizRenderer
import io.data2viz.viz.PathNode
import io.data2viz.viz.toPaint
import javafx.scene.shape.StrokeLineCap
import kotlin.math.absoluteValue


fun PathNode.render(renderer: JFxVizRenderer) {
    val gc = renderer.gc
    gc.beginPath()
    commands.forEach { cmd ->
        when (cmd) {
            is MoveTo -> gc.moveTo(cmd.x, cmd.y)
            is LineTo -> gc.lineTo(cmd.x, cmd.y)
            is QuadraticCurveTo -> gc.quadraticCurveTo(cmd.cpx, cmd.cpy, cmd.x, cmd.y)
            is BezierCurveTo -> gc.bezierCurveTo(cmd.cpx1, cmd.cpy1, cmd.cpx2, cmd.cpy2, cmd.x, cmd.y)
            is Arc -> gc.arc(cmd.centerX, cmd.centerY, cmd.radius, cmd.radius, cmd.start, cmd.length)
            is ArcTo -> gc.arcTo(cmd.fromX, cmd.fromY, cmd.x, cmd.y, cmd.radius)
            is ClosePath -> gc.closePath()
        }
    }

    stroke?.let {
        gc.stroke = it.toPaint()
        gc.lineWidth = strokeWidth?: 1.0
        gc.lineCap = StrokeLineCap.BUTT
        gc.stroke()
    }

    fill?.let {
        gc.fill = it.toPaint()
        gc.fill()
    }
}

fun Double.toDegrees() = Math.toDegrees(this)

val Arc.start:Double
    get() = -startAngle.toDegrees()

val Arc.length:Double
    get() = (if (counterClockWise) 1 else -1) * (endAngle - startAngle).absoluteValue.toDegrees()