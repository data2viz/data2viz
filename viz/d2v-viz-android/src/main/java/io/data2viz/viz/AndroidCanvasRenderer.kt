package io.data2viz.viz

import io.data2viz.color.Color
import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Shader
import io.data2viz.color.ColorOrGradient
import io.data2viz.color.LinearGradient
import io.data2viz.color.RadialGradient
import kotlin.math.*

typealias ALinearGradient = android.graphics.LinearGradient
typealias ARadialGradient = android.graphics.RadialGradient

val paint = Paint().apply {
    isAntiAlias = true
}

class AndroidCanvasRenderer(val context: Context, var canvas: Canvas) : VizRenderer {

    override fun render(viz: Viz) {
        println("Start rendering")
        viz.root.render(context, canvas)
    }

}

fun Group.render(context: Context, canvas: Canvas) {

    children.forEach { node ->

        if (node is HasTransform) {
            node.transform?.also {
                canvas.translate(it.translate?.x?.toFloat() ?:.0f, it.translate?.y?.toFloat() ?:.0f)
            }
        }


        when (node) {
            is Circle       -> node.render(canvas)
            is Rect         -> node.render(canvas)
            is Group        -> node.render(context, canvas)
            is PathNode     -> node.render(context, canvas)
            else            -> error("Unknow type ${node::class}")
        }

        if (node is HasTransform) {
            node.transform?.also {
                canvas.translate(-(it.translate?.x?.toFloat() ?:.0f), -(it.translate?.y?.toFloat() ?:.0f))
            }
        }

    }
}


fun Circle.render(canvas: Canvas) {
    fill?.let {
        paint.style = Paint.Style.FILL
        it.updatePaint(paint)
        canvas.drawCircle(
                x.toFloat(),
                y.toFloat(),
                radius.toFloat(),
                paint)
    }
    stroke?.let {
        paint.style = Paint.Style.STROKE
        it.updatePaint(paint)
        canvas.drawCircle(
                x.toFloat(),
                y.toFloat(),
                radius.toFloat(),
                paint)
    }
}

fun Rect.render(canvas: Canvas) {
    fill?.let {
        paint.style = Paint.Style.FILL
        it.updatePaint(paint)
        canvas.drawRect(
                android.graphics.RectF(
                        x.toFloat(),
                        y.toFloat(),
                        (x + width).toFloat(),
                        (y + height).toFloat()
                        ),
                paint)
    }
    stroke?.let {
        paint.style = Paint.Style.STROKE
        it.updatePaint(paint)
        canvas.drawRect(
                android.graphics.RectF(
                        x.toFloat(),
                        y.toFloat(),
                        (x + width).toFloat(),
                        (y + height).toFloat()
                ),
                paint)
    }
}


fun Double.radToDegrees() = Math.toDegrees(this).toFloat()


fun ColorOrGradient.updatePaint(paint: Paint){
    when(this) {
        is Color -> {
            paint.color = this.toColor()
            paint.shader = null
        }
        is LinearGradient -> paint.shader = this.toLinearGradient()
        is RadialGradient -> paint.shader = this.toRadialGradient()
        else -> error("Unknown type :: ${this::class}")
    }
}

private fun RadialGradient.toRadialGradient() =
        ARadialGradient(
                this.cx.toFloat(),
                this.cy.toFloat(),
                this.r.toFloat(),
                IntArray(colorStops.size){ colorStops[it].color.toColor() },
                FloatArray(colorStops.size){ colorStops[it].percent.toFloat() },
                Shader.TileMode.CLAMP)


private fun LinearGradient.toLinearGradient() =
         ALinearGradient(
                 this.x1.toFloat(),
                 this.y1.toFloat(),
                 this.x2.toFloat(),
                 this.y2.toFloat(),
                 IntArray(colorStops.size){ colorStops[it].color.toColor() },
                 FloatArray(colorStops.size){ colorStops[it].percent.toFloat() },
                 Shader.TileMode.CLAMP)

fun Color.toColor() =
            ((255 * this.alpha.toFloat()).toInt() and 0xff shl 24) or 
            (this.r and 0xff shl 16) or 
            (this.g and 0xff shl 8) or 
            (this.b and 0xff)

fun angle(alpha: Double): Double = when {
    alpha > PI -> (alpha - 2 * PI)
    alpha < -PI -> (2 * PI + alpha)
    else -> alpha
}

fun Boolean.toSign() = if (this) 1 else -1

val Number.radToDeg: Double
    get() = this.toDouble() * 180 / PI

fun Float.dipToPx(c: Context) = this * c.resources.displayMetrics.density
fun Double.dipToPx(c: Context) = this.toFloat() * c.resources.displayMetrics.density
fun Int.dipToPx(c: Context) = this.toFloat() * c.resources.displayMetrics.density
