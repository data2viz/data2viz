package io.data2viz.viz

import io.data2viz.color.Color
import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Shader
import android.view.View
import io.data2viz.color.ColorOrGradient
import io.data2viz.color.LinearGradient
import io.data2viz.color.RadialGradient
import io.data2viz.timer.Timer
import io.data2viz.timer.timer
import kotlin.math.*

typealias ALinearGradient = android.graphics.LinearGradient
typealias ARadialGradient = android.graphics.RadialGradient

val paint = Paint().apply {
    isAntiAlias = true
}


fun Viz.toView(context: Context): VizView = VizView(this, context)


class VizView(val viz: Viz, context: Context) : View(context) {

    private val renderer: AndroidCanvasRenderer = AndroidCanvasRenderer(context, viz)
    private val timers = mutableListOf<Timer>()

    fun startAnimations() {
        if (viz.animations.isNotEmpty()) {
            viz.animations.forEach { anim ->
                timers += timer { time ->
                    anim(time)
                }
            }
            timers += timer {
                invalidate()
            }
        }
    }

    fun stopAnimations(){
        for (timer in timers) {
            timer.stop()
        }
        timers.clear()
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        viz.resize(w.toDouble(), h.toDouble())
        updateScale()
    }

    fun updateScale() {
        renderer.scale = (width / viz.width).toFloat()
    }

    var drawCount = -1
    private var startTime = System.currentTimeMillis()

    override fun onDraw(canvas: Canvas) {
        drawCount++
        if (drawCount == 100){
            val delta = System.currentTimeMillis() - startTime
            val fps = 100_000 / delta
            startTime = System.currentTimeMillis()
            drawCount = -1
        }

        renderer.canvas = canvas
        renderer.render(viz)
    }

}

fun Paint.getNumberHeight(): Int {
    val rect = android.graphics.Rect()
    getTextBounds("a", 0, 1, rect)
    return rect.height()
}

class AndroidCanvasRenderer(val context: Context, val viz: Viz, var canvas: Canvas = Canvas()) : VizRenderer {

    var scale = 1F

    private val animationTimers = mutableListOf<Timer>()


    init {
        viz.renderer = this
    }

    override fun render(viz: Viz) {
        viz.layers.forEach { layer ->
            if (layer.visible)
                layer.render(this)
        }
    }

    override fun startAnimations() {
        if (viz.animations.isNotEmpty()) {
            viz.animations.forEach { anim ->
                animationTimers += timer { time ->
                    anim(time)
                }
            }
            animationTimers += timer {
                render(viz)
            }
        }
    }

    override fun stopAnimations() {
        animationTimers.forEach { it.stop() }
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
                    canvas.rotate((+ (it.rotate?.delta ?: .0) * 180 / PI).toFloat())
                }
            }

            if (node is HasStroke) {
                paint.strokeWidth = (node.style.strokeWidth ?: 1.0).toFloat()
            }

            if (node.visible)
                when (node) {
                    is CircleNode   -> node.render(renderer)
                    is RectNode     -> node.render(renderer)
                    is Group    -> node.render(renderer)
                    is PathNode -> node.render(renderer)
                    is Text     -> node.render(renderer)
                    is Line     -> node.render(renderer)
                    else -> error("Unknow type ${node::class}")
                }

            if (node is HasTransform) {
                node.transform?.also {
                    canvas.translate(-(it.translate?.x?.dp ?: .0f), -(it.translate?.y?.dp ?: .0f))
                    canvas.rotate((- (it.rotate?.delta ?: .0) * 180 / PI).toFloat())

                }
            }

        }
    }
}


fun CircleNode.render(renderer: AndroidCanvasRenderer) {
    val canvas = renderer.canvas
    with(renderer) {
        style.fill?.let {
            paint.style = Paint.Style.FILL
            it.updatePaint(paint, renderer)
            canvas.drawCircle(
                    x.dp,
                    y.dp,
                    radius.dp,
                    paint)
        }
        style.stroke?.let {
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
        paint.textAlign = style.anchor.android
        paint.color = android.graphics.Color.BLACK
        paint.textSize = 12.0.dp
        val dy = style.baseline.dy(renderer, paint.fontMetrics)
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


fun RectNode.render(renderer: AndroidCanvasRenderer) {
    val canvas = renderer.canvas
    with(renderer) {
        style.fill?.let {
            paint.style = Paint.Style.FILL
            it.updatePaint(paint, renderer)
            canvas.drawRect(
                    x.dp,
                    y.dp,
                    (x + width).dp,
                    (y + height).dp,
                    paint)
        }
        style.stroke?.let {
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
        ((255 * this.alpha).toInt() and 0xff shl 24) or
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
        style.stroke?.let {
            paint.style = Paint.Style.STROKE
            paint.strokeWidth = style.strokeWidth?.dp ?: 1f
            it.updatePaint(paint, renderer)
            with(renderer) {
                canvas.drawLine(x1.dp, y1.dp, x2.dp, y2.dp, paint)
            }
        }
    }
}