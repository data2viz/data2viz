package io.data2viz.viz

import android.graphics.Paint
import android.graphics.RectF
import io.data2viz.geom.*
import kotlin.math.absoluteValue
import kotlin.math.atan2
import kotlin.math.cos
import kotlin.math.sin


private const val EPSILON_FLOAT = 0.0001f
private const val EPSILON_CIRCLE = 360 - EPSILON_FLOAT

fun PathNode.render(renderer: AndroidCanvasRenderer) {

    val canvas = renderer.canvas

    fun PathNode.last(index: Int): PathCommand {
        require(index > 0) { "Index should be up to 0" }
        return path.commands[index - 1]
    }

    val path = android.graphics.Path()

    fun arcTo(lastX: Double, lastY: Double, cpX: Double, cpY: Double, x: Double, y: Double, r: Double) {

        with(renderer) {

            val alpha1 = atan2(lastY - cpY, lastX - cpX)
            val alpha2 = atan2(y - cpY, x - cpX)
            val alpha = angle(alpha1 - alpha2)
            val d = r / sin(alpha / 2).absoluteValue
            val cx = cpX + d * cos(alpha1 - alpha / 2)
            val cy = cpY + d * sin(alpha1 - alpha / 2)

            val clockwise = alpha > 0
            val startAngle = alpha1.radToDeg.toFloat() + clockwise.toSign() * 90f
            val sweepAngle =
                    if (clockwise)
                        360 - ((180f + alpha.radToDeg.toFloat()) % 360f)
                    else
                        (-180f - alpha.radToDeg.toFloat()) % 360f

            path.moveTo(lastX.dp, lastY.dp)
            path.arcTo(
                    RectF((cx - r).dp, (cy - r).dp, (cx + r).dp, (cy + r).dp),
                    startAngle,
                    sweepAngle, false)
            path.lineTo(x.dp, y.dp)
        }
    }

    this@render.path.commands.forEachIndexed { index, cmd ->
        with(renderer) {
            when (cmd) {
                is MoveTo -> path.moveTo(cmd.x.dp, cmd.y.dp)
                is LineTo -> path.lineTo(cmd.x.dp, cmd.y.dp)
                is QuadraticCurveTo -> path.quadTo(cmd.cpx.dp, cmd.cpy.dp, cmd.x.dp, cmd.y.dp)
                is BezierCurveTo -> path.cubicTo(cmd.cpx1.dp, cmd.cpy1.dp, cmd.cpx2.dp, cmd.cpy2.dp, cmd.x.dp, cmd.y.dp)
                is ArcTo -> arcTo(last(index).x, last(index).y, cmd.fromX, cmd.fromY, cmd.x, cmd.y, cmd.radius)
                is RectCmd -> path.addRect(cmd.x.dp, cmd.y.dp, (cmd.x + cmd.w).dp, (cmd.y + cmd.h).dp, android.graphics.Path.Direction.CW)
                is ClosePath -> path.close()
                is Arc -> {
                    val r = cmd.radius
                    val rect = RectF((cmd.centerX - r).dp, (cmd.centerY - r).dp, (cmd.centerX + r).dp, (cmd.centerY + r).dp)
                    val startAngle = cmd.startAngle.radToDegrees()
                    var sweepAngle = cmd.endAngle.radToDegrees() - startAngle

                    if (!cmd.counterClockWise && sweepAngle < -EPSILON_FLOAT) sweepAngle = (sweepAngle % 360) + 360
                    if (cmd.counterClockWise && sweepAngle > EPSILON_FLOAT) sweepAngle = (sweepAngle % 360) - 360

                    // on Android an arc with an angle of 360 is not drawn !
                    if (sweepAngle.absoluteValue > 360) sweepAngle = EPSILON_CIRCLE
                    else if (sweepAngle.absoluteValue < -360) sweepAngle = -EPSILON_CIRCLE

                    path.arcTo(rect, startAngle, sweepAngle)
                }
                else -> error("Unknown path command:: ${cmd::class}")
            }
        }
    }

    style.fill?.let {
        paint.style = Paint.Style.FILL
        it.updatePaint(paint, renderer)
        canvas.drawPath(path, paint)
    }

    style.stroke?.let {
        paint.style = Paint.Style.STROKE
        it.updatePaint(paint, renderer)
        canvas.drawPath(path, paint)
    }
}

