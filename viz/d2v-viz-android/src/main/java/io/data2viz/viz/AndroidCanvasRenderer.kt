package io.data2viz.viz

import io.data2viz.color.Color
import android.content.Context
import android.graphics.*
import io.data2viz.color.ColorOrGradient
import io.data2viz.path.*
import kotlin.math.*

val paint = Paint().apply {
    isAntiAlias = true
}

class AndroidCanvasRenderer(val context: Context, var canvas: Canvas) : VizRenderer {

    override fun render(viz: Viz) {
        viz.root.render(context, canvas)
    }

}

fun Group.render(context: Context, canvas: Canvas) {
    children.forEach { node ->
        when (node) {
            is Circle       -> node.render(canvas)
            is Group        -> node.render(context, canvas)
            is PathNode     -> node.render(context, canvas)
            else            -> error("Unknow type ${node::class}")
        }
    }
}


fun Circle.render(canvas: Canvas) {
    fill?.let {
        paint.style = Paint.Style.FILL
        paint.color = it.asAndroidColor()
        canvas.drawCircle(
                x.toFloat(),
                y.toFloat(),
                radius.toFloat(),
                paint)
    }
    stroke?.let {
        paint.style = Paint.Style.STROKE
        paint.color = it.asAndroidColor()
        canvas.drawCircle(
                x.toFloat(),
                y.toFloat(),
                radius.toFloat(),
                paint)
    }
}


fun Double.radToDegrees() = Math.toDegrees(this).toFloat()

fun ColorOrGradient.asAndroidColor() = when(this) {
    is Color -> this.toColor()
    is LinearGradient -> this.toLinearGradient()
    is RadialGradient -> this.toRadialGradient()
    else -> error("Unknown type :: ${this::class}")
}

private fun RadialGradient.toRadialGradient(): Int {
    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
}

private fun LinearGradient.toLinearGradient(): Int {
    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
}

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
