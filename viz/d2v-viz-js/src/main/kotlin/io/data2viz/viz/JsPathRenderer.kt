package io.data2viz.viz

import io.data2viz.geom.*
import org.w3c.dom.CanvasRenderingContext2D

fun PathNode.render(context: CanvasRenderingContext2D) {
    context.beginPath()
    path.commands.forEach { cmd ->
        when (cmd) {
            is MoveTo -> context.moveTo(cmd.x, cmd.y)
            is LineTo -> context.lineTo(cmd.x, cmd.y)
            is QuadraticCurveTo -> context.quadraticCurveTo(cmd.cpx, cmd.cpy, cmd.x, cmd.y)
            is BezierCurveTo -> context.bezierCurveTo(cmd.cpx1, cmd.cpy1, cmd.cpx2, cmd.cpy2, cmd.x, cmd.y)
            is Arc -> context.arc(cmd.centerX, cmd.centerY, cmd.radius, cmd.startAngle, cmd.endAngle, cmd.counterClockWise)
            is ArcTo -> context.arcTo(cmd.fromX, cmd.fromY, cmd.x, cmd.y, cmd.radius)
            is ClosePath -> context.closePath()
            is RectCmd -> context.rect(cmd.x, cmd.y, cmd.w, cmd.h)
            else -> error("Unknown canvas command: ${cmd::class}")
        }
    }

    // FIRST fill
    fill?.let {
        context.fillStyle = it.toCanvasPaint(context)
        context.fill()
    }

    // THEN stroke
    stroke?.let {
        context.strokeStyle = it.toCanvasPaint(context)
        context.lineWidth = strokeWidth ?: 1.0
        context.stroke()
    }


}
