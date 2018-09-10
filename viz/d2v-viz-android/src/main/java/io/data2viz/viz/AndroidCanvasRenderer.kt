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



fun Paint.getNumberHeight(): Int {
    val rect = android.graphics.Rect()
    getTextBounds("a", 0, 1, rect)
    return rect.height()
}

class AndroidCanvasRenderer(val context: Context, var canvas: Canvas) : VizRenderer {

    var scale = 1F


    override fun render(viz: Viz) {
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

            if (node is HasStroke) {
                paint.strokeWidth = (node.strokeWidth ?: 1.0).toFloat()
            }


            when (node) {
                is Circle   -> node.render(renderer)
                is Rect     -> node.render(renderer)
                is Group    -> node.render(renderer)
                is PathNode -> node.render(renderer)
                is Text     -> node.render(renderer)
                is Line     -> node.render(renderer)
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

fun Text.render(renderer: AndroidCanvasRenderer) {
    with(renderer) {
        paint.style = Paint.Style.FILL
        paint.textAlign = anchor.android
        paint.color = android.graphics.Color.BLACK
        paint.textSize = 12.0.dp
        val dy = baseline.dy(renderer, paint.fontMetrics)
        renderer.canvas.drawText(textContent, x.dp, y.dp - dy, paint )
    }

}


/**
 * The y distance to move the text from baseline in order to respect the wanted
 * alignment.
 *
 * The middle alignement is an approximation.
 * TODO resolve by implementing DV-105
 */
fun TextAlignmentBaseline.dy(renderer: AndroidCanvasRenderer, fontMetrics: Paint.FontMetrics): Float =
        with(renderer){
            when(this@dy){
                TextAlignmentBaseline.BASELINE  -> 0F
                TextAlignmentBaseline.HANGING   -> fontMetrics.top
                TextAlignmentBaseline.MIDDLE    -> fontMetrics.ascent * .4f
            }
        }

val TextAnchor.android: Paint.Align
    get() = when(this){
        TextAnchor.START    -> Paint.Align.LEFT
        TextAnchor.END      -> Paint.Align.RIGHT
        TextAnchor.MIDDLE   -> Paint.Align.CENTER
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


fun ColorOrGradient.updatePaint(paint: Paint, renderer: AndroidCanvasRenderer) {
    when (this) {
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
        with(renderer) {
            ARadialGradient(
                    cx.dp,
                    cy.dp,
                    r.dp,
                    IntArray(colorStops.size) { colorStops[it].color.toColor() },
                    FloatArray(colorStops.size) { colorStops[it].percent.toFloat() },
                    Shader.TileMode.CLAMP)
        }


private fun LinearGradient.toLinearGradient(renderer: AndroidCanvasRenderer) =
        with(renderer) {
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


fun Line.render(renderer: AndroidCanvasRenderer) {
    with(renderer){
        stroke?.let {
            paint.style = Paint.Style.STROKE
            paint.strokeWidth = strokeWidth?.dp ?: 1f
            it.updatePaint(paint, renderer)
            with(renderer) {
                canvas.drawLine(x1.dp, y1.dp, x2.dp, y2.dp, paint)
            }
        }
    }
}