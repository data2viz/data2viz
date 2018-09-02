package io.data2viz.viz

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.RectF
import io.data2viz.path.*
import kotlin.math.absoluteValue
import kotlin.math.atan2
import kotlin.math.cos
import kotlin.math.sin


fun PathNode.render(context: Context, canvas:Canvas) {


    fun PathNode.last(index:Int): PathCommand {
        require(index>0) {"Index should be up to 0"}
        return commands[index - 1]
    }


    val path = android.graphics.Path()

    fun Number.px() =
//                this.toFloat().dipToPx(context) //todo manage screen density
            this.toFloat()

    fun arcTo(lastX: Double, lastY: Double, cpX: Double, cpY:Double, x: Double, y: Double, r: Double) {

        val density = context.resources.displayMetrics.density
        println("density::$density")

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

//            paint.color = android.graphics.Color.BLACK
        path.moveTo(lastX.px(), lastY.px())
        path.arcTo(
                RectF((cx - r).px(), (cy - r).px(), (cx + r).px(), (cy + r).px()),
                startAngle,
                sweepAngle, false)
        path.lineTo(x.px(), y.px())
        canvas.drawPath(path, paint)
    }


    commands.forEachIndexed { index, cmd ->
        when (cmd) {
            is MoveTo -> path.moveTo(cmd.x.toFloat(), cmd.y.toFloat())
            is LineTo -> path.lineTo(cmd.x.toFloat(), cmd.y.toFloat())
            is QuadraticCurveTo -> path.quadTo(cmd.cpx.toFloat(), cmd.cpy.toFloat(), cmd.x.toFloat(), cmd.y.toFloat())
            is BezierCurveTo -> path.cubicTo(cmd.cpx1.toFloat(), cmd.cpy1.toFloat(), cmd.cpx2.toFloat(), cmd.cpy2.toFloat(), cmd.x.toFloat(), cmd.y.toFloat())
            is Arc -> {
                val left = (cmd.centerX - cmd.radius).toFloat()
                val right = (cmd.centerX + cmd.radius).toFloat()
                val top = (cmd.centerY - cmd.radius).toFloat()
                val bottom = (cmd.centerY + cmd.radius).toFloat()
                val startAngle = cmd.startAngle.radToDegrees()
                var sweepAngle = (cmd.endAngle.radToDegrees() + 360 - cmd.startAngle.radToDegrees()) % 360
                if (cmd.counterClockWise) {
                    sweepAngle -= 360
                }
                path.arcTo(RectF(left, top, right, bottom), startAngle, sweepAngle)
            }
            is ArcTo -> arcTo(last(index).x, last(index).y, cmd.fromX, cmd.fromY, cmd.x, cmd.y, cmd.radius )
//                is Rect -> path.addRect(cmd.x.toFloat(), cmd.y.toFloat(), (cmd.x + cmd.width).toFloat(), (cmd.y + cmd.height).toFloat(), android.graphics.Path.Direction.CW )
            is ClosePath -> path.close()
            else -> error("Unknown path command:: ${cmd::class}")
        }
    }
    stroke?.let {
        paint.style = Paint.Style.STROKE
        paint.color = it.asAndroidColor()
    }
    canvas.drawPath(path, paint)
}
