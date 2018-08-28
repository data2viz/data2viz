package io.data2viz.viz

import io.data2viz.color.Color
import io.data2viz.color.ColorOrGradient
import io.data2viz.color.LinearGradient
import io.data2viz.color.colors
import io.data2viz.path.*
import org.w3c.dom.CanvasGradient
import org.w3c.dom.CanvasRenderingContext2D
import kotlin.math.PI

fun Viz.configRenderTo(root: CanvasRenderingContext2D) {
    this.renderer = JsCanvasRenderer(root)
}

fun Group.render(context: CanvasRenderingContext2D) {

    transform?.run {
        context.translate(this.translate?.x ?:.0, this.translate?.y ?:.0)
    }

    children.forEach { node ->  
        when (node) {
            is Rect         -> node.render(context)
            is Circle       -> node.render(context)
            is Group        -> node.render(context)
            is PathNode     -> node.render(context)
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
        context.fillStyle = it.rgbHex
//                    context.globalAlpha = fill?.alpha?.toDouble()
        context.fill()
    }

    stroke?.let {
        context.strokeStyle = it.rgbHex
        context.stroke()
    }

}

class JsCanvasRenderer(val context: CanvasRenderingContext2D) : VizRenderer {

    override fun render(viz: Viz) {
        context.clearRect(.0, .0, context.canvas.width.toDouble(), context.canvas.height.toDouble())
        viz.root.render(context)
    }

}


fun ColorOrGradient.toCanvasPaint(context: CanvasRenderingContext2D):Any = when(this) {
    is Color -> this.rgba
    is LinearGradient -> this.toCanvasGradient(context)
    else -> TODO("Implement radial gradient")
}

fun LinearGradient.toCanvasGradient(context: CanvasRenderingContext2D): CanvasGradient {
    val gradient = context.createLinearGradient(x1, y1, x2, y2)
    this.colorStops.forEach { cs ->
        gradient.addColorStop(cs.percent, cs.color.rgba)
    }
    return gradient
}