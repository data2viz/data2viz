package io.data2viz.viz

import android.graphics.Paint
import android.graphics.RectF
import io.data2viz.geom.*
import kotlin.math.*


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

        path.moveTo(lastX.toFloat(), lastY.toFloat())
        path.arcTo(
                RectF((cx - r).toFloat(), (cy - r).toFloat(), (cx + r).toFloat(), (cy + r).toFloat()),
                startAngle,
                sweepAngle, false)
        path.lineTo(x.toFloat(), y.toFloat())
    }

    this@render.path.commands.forEachIndexed { index, cmd ->
        when (cmd) {
            is MoveTo -> path.moveTo(cmd.x.toFloat(), cmd.y.toFloat())
            is LineTo -> path.lineTo(cmd.x.toFloat(), cmd.y.toFloat())
            is QuadraticCurveTo -> path.quadTo(
                cmd.cpx.toFloat(),
                cmd.cpy.toFloat(),
                cmd.x.toFloat(),
                cmd.y.toFloat()
            )
            is BezierCurveTo -> path.cubicTo(
                cmd.cpx1.toFloat(),
                cmd.cpy1.toFloat(),
                cmd.cpx2.toFloat(),
                cmd.cpy2.toFloat(),
                cmd.x.toFloat(),
                cmd.y.toFloat()
            )
            is ArcTo -> arcTo(last(index).x, last(index).y, cmd.fromX, cmd.fromY, cmd.x, cmd.y, cmd.radius)
            is RectCmd -> path.addRect(
                cmd.x.toFloat(),
                cmd.y.toFloat(),
                (cmd.x + cmd.w).toFloat(),
                (cmd.y + cmd.h).toFloat(), android.graphics.Path.Direction.CW)
            is ClosePath -> path.close()
            is Arc -> {
                val r = cmd.radius
                val rect = RectF(
                    (cmd.centerX - r).toFloat(),
                    (cmd.centerY - r).toFloat(),
                    (cmd.centerX + r).toFloat(),
                    (cmd.centerY + r).toFloat()
                )
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

    style.fill?.let {
        paint.style = Paint.Style.FILL
        it.updatePaint(paint)
        canvas.drawPath(path, paint)
    }

    style.stroke?.let {
        paint.style = Paint.Style.STROKE
        it.updatePaint(paint)
        canvas.drawPath(path, paint)
    }
}

val Number.radToDeg: Double
    get() = this.toDouble() * 180 / PI

fun angle(alpha: Double): Double = when {
    alpha > PI -> (alpha - 2 * PI)
    alpha < -PI -> (2 * PI + alpha)
    else -> alpha
}

fun Boolean.toSign() = if (this) 1 else -1

fun Double.radToDegrees() = Math.toDegrees(this).toFloat()

