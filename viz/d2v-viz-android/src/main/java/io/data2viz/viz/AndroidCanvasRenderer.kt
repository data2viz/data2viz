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

    var scale = 1F

    override fun render(viz: Viz) {
        println("Start rendering")
        viz.root.render(this)
    }

    val Double.dp: Float
        get() = (this * scale).toFloat()

}

fun Group.render(renderer: AndroidCanvasRenderer) {

    val canvas = renderer.canvas

    with(renderer) {
        children.forEach { node ->

            if (node is HasTransform) {
                node.transform?.also {
                    canvas.translate(it.translate?.x?.dp ?: .0f, it.translate?.y?.dp ?: .0f)
                }
            }

            when (node) {
                is Circle -> node.render(renderer)
                is Rect -> node.render(renderer)
                is Group -> node.render(renderer)
                is PathNode -> node.render(renderer)
                else -> error("Unknow type ${node::class}")
            }

            if (node is HasTransform) {
                node.transform?.also {
                    canvas.translate(-(it.translate?.x?.dp ?: .0f), -(it.translate?.y?.dp ?: .0f))
                }
            }

        }
    }
}


fun Circle.render(renderer: AndroidCanvasRenderer) {
    val canvas = renderer.canvas
    with(renderer) {
        fill?.let {
            paint.style = Paint.Style.FILL
            it.updatePaint(paint, renderer)
            canvas.drawCircle(
                    x.dp,
                    y.dp,
                    radius.dp,
                    paint)
        }
        stroke?.let {
            paint.style = Paint.Style.STROKE
            it.updatePaint(paint, renderer)
            canvas.drawCircle(
                    x.dp,
                    y.dp,
                    radius.dp,
                    paint)
        }
    }
}

fun Rect.render(renderer: AndroidCanvasRenderer) {
    val canvas = renderer.canvas
    with(renderer) {
        fill?.let {
            paint.style = Paint.Style.FILL
            it.updatePaint(paint, renderer)
            canvas.drawRect(
                    x.dp,
                    y.dp,
                    (x + width).dp,
                    (y + height).dp,
                    paint)
        }
        stroke?.let {
            paint.style = Paint.Style.STROKE
            it.updatePaint(paint, renderer)
            canvas.drawRect(
                    x.dp,
                    y.dp,
                    (x + width).dp,
                    (y + height).dp,
                    paint)
        }
    }

}


fun Double.radToDegrees() = Math.toDegrees(this).toFloat()


fun ColorOrGradient.updatePaint(paint: Paint, renderer: AndroidCanvasRenderer){
    when(this) {
        is Color -> {
            paint.color = this.toColor()
            paint.shader = null
        }
        is LinearGradient -> paint.shader = this.toLinearGradient(renderer)
        is RadialGradient -> paint.shader = this.toRadialGradient(renderer)
        else -> error("Unknown type :: ${this::class}")
    }
}

private fun RadialGradient.toRadialGradient(renderer: AndroidCanvasRenderer) =
        with(renderer){
            ARadialGradient(
                    cx.dp,
                    cy.dp,
                    r.dp,
                    IntArray(colorStops.size){ colorStops[it].color.toColor() },
                    FloatArray(colorStops.size){ colorStops[it].percent.toFloat() },
                    Shader.TileMode.CLAMP)
        }


private fun LinearGradient.toLinearGradient(renderer: AndroidCanvasRenderer) =
        with(renderer){
            ALinearGradient(
                    x1.dp,
                    y1.dp,
                    x2.dp,
                    y2.dp,
                    IntArray(colorStops.size) { colorStops[it].color.toColor() },
                    FloatArray(colorStops.size) { colorStops[it].percent.toFloat() },
                    Shader.TileMode.CLAMP)
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
