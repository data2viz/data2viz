package io.data2viz.viz

import io.data2viz.color.*
import io.data2viz.path.*
import org.w3c.dom.*
import kotlin.math.PI

fun Viz.configRenderTo(root: CanvasRenderingContext2D) {
    this.renderer = JsCanvasRenderer(root)
}


class JsCanvasRenderer(val context: CanvasRenderingContext2D) : VizRenderer {

    override fun render(viz: Viz) {
        context.clearRect(.0, .0, context.canvas.width.toDouble(), context.canvas.height.toDouble())
        viz.root.render(context)
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
            context.fillStyle = node.fill?.toCanvasPaint(context)
        }

        if (node is HasStroke) {
            context.strokeStyle = node.stroke?.toCanvasPaint(context)
            context.lineWidth = node.strokeWidth ?: 1.0
        }


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

    stroke?.let {
        context.strokeStyle = it.toCanvasPaint(context)
        context.strokeRect(x, y, width, height)
    }

    fill?.let {
        context.fillStyle = it.toCanvasPaint(context)
        context.fillRect(x, y, width, height)
    }

}

fun Circle.render(context: CanvasRenderingContext2D) {
    
    context.beginPath()
    context.arc(x, y, radius, .0, 2 * PI, false)
    
    fill?.let {
        context.fill()
    }

    stroke?.let {
        context.stroke()
    }
}

fun Text.render(context: CanvasRenderingContext2D) {
    context.textAlign = this.anchor.js
    context.textBaseline = this.baseline.js
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