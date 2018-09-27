package io.data2viz.viz

import io.data2viz.color.*
import io.data2viz.timer.Timer
import io.data2viz.timer.timer
import org.w3c.dom.*
import kotlin.browser.document
import kotlin.math.PI


/**
 * Allows to quickly bind a renderer on an existing canvas in the current document.
 * This is a way to display multiple visualizations in a unique page. Each viz
 * location in the page can be prepared with by providing a `canvas` tag with a
 * unique id.
 *
 * Raises an error if there is no canvas element with the given id.
 *
 * The viz is immediately rendered if the autoUpdate property of the viz configuration
 * is set to true.
 */
fun Viz.bindRendererOn(canvasId: String) {
    val canvas = requireNotNull(document.getElementById(canvasId) as HTMLCanvasElement?)
        {"No canvas in the document corresponding to $canvasId"}
    bindRendererOn(canvas)
}

fun Viz.bindRendererOn(canvas: HTMLCanvasElement) {
    val context = canvas.getContext("2d") as CanvasRenderingContext2D
    context.canvas.width = width.toInt()
    context.canvas.height = height.toInt()

    this.renderer = JsCanvasRenderer(context, this)

    if (config.autoUpdate) {
        render()
        startAnimations()
    }

}


class JsCanvasRenderer(val context: CanvasRenderingContext2D, val viz: Viz) : VizRenderer {

    private val animationTimers = mutableListOf<Timer>()

    override fun render(viz: Viz) {
        context.clearRect(.0, .0, context.canvas.width.toDouble(), context.canvas.height.toDouble())
        viz.layers.forEach { layer ->
            if (layer.visible)
                layer.render(context)
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

}

fun Group.render(context: CanvasRenderingContext2D) {


    children.forEach { node ->

        if (node is HasTransform) {
            node.transform?.also {
                context.translate(it.translate?.x ?:.0, it.translate?.y ?:.0)
            }
        }

        if (node is HasFill) {
            context.fillStyle = node.style.fill?.toCanvasPaint(context)
        }

        if (node is HasStroke) {
            context.strokeStyle = node.style.stroke?.toCanvasPaint(context)
            context.lineWidth = node.style.strokeWidth ?: 1.0
        }

        if (node.visible)
            when (node) {
                is Circle       -> node.render(context)
                is Rect         -> node.render(context)
                is Group        -> node.render(context)
                is PathNode     -> node.render(context)
                is Text         -> node.render(context)
                is Line         -> node.render(context)
                else            -> error("Unknow type ${node::class}")
            }

        if (node is HasTransform) {
            node.transform?.also {
                context.translate(-(it.translate?.x ?:.0), -(it.translate?.y ?:.0))
            }
        }

    }

    context.translate(.0, .0)
}

fun Rect.render(context: CanvasRenderingContext2D) {

    style.fill?.let {
        context.fillStyle = it.toCanvasPaint(context)
        context.fillRect(x, y, width, height)
    }

    style.stroke?.let {
        context.strokeStyle = it.toCanvasPaint(context)
        context.strokeRect(x, y, width, height)
    }


}

fun Circle.render(context: CanvasRenderingContext2D) {
    
    context.beginPath()
    context.arc(x, y, radius, .0, 2 * PI, false)
    
    style.fill?.let {
        context.fill()
    }

    style.stroke?.let {
        context.stroke()
    }
}

fun Text.render(context: CanvasRenderingContext2D) {
    context.textAlign = this.style.anchor.js
    context.textBaseline = this.style.baseline.js
    context.fillText(textContent, x, y)
}

val TextAlignmentBaseline.js: CanvasTextBaseline
    get() = when(this){
        TextAlignmentBaseline.BASELINE  -> CanvasTextBaseline.BOTTOM
        TextAlignmentBaseline.HANGING   -> CanvasTextBaseline.HANGING
        TextAlignmentBaseline.MIDDLE    -> CanvasTextBaseline.MIDDLE
    }

val TextAnchor.js: CanvasTextAlign
    get() = when(this){
        TextAnchor.START    -> CanvasTextAlign.LEFT
        TextAnchor.END      -> CanvasTextAlign.RIGHT
        TextAnchor.MIDDLE   -> CanvasTextAlign.CENTER
    }


fun ColorOrGradient.toCanvasPaint(context: CanvasRenderingContext2D):Any = when(this) {
    is Color -> this.rgba
    is LinearGradient -> this.toCanvasGradient(context)
    is RadialGradient -> this.toCanvasGradient(context)
    else -> error("Unknown type :: ${this::class}")
}

fun LinearGradient.toCanvasGradient(context: CanvasRenderingContext2D): CanvasGradient {
    val gradient = context.createLinearGradient(x1, y1, x2, y2)
    this.colorStops.forEach { cs ->
        gradient.addColorStop(cs.percent, cs.color.rgba)
    }
    return gradient
}

fun RadialGradient.toCanvasGradient(context: CanvasRenderingContext2D): CanvasGradient {
    val gradient = context.createRadialGradient(cx, cy, 0.0, cx, cy, r)
    this.colorStops.forEach { cs ->
        gradient.addColorStop(cs.percent, cs.color.rgba)
    }
    return gradient
}

fun Line.render(context: CanvasRenderingContext2D) {
    context.beginPath()
    context.moveTo(x1, y1)
    context.lineTo(x2, y2)
    context.stroke()
}