package io.data2viz.viz

import io.data2viz.color.*
import io.data2viz.path.render
import javafx.scene.canvas.Canvas
import javafx.scene.paint.CycleMethod
import javafx.scene.paint.Stop

typealias JfxLinearGradient = javafx.scene.paint.LinearGradient
typealias JfxRadialGradient = javafx.scene.paint.RadialGradient


fun Circle.render(canvas: Canvas) {
    val context = canvas.graphicsContext2D

    fill?.let {
        context.fill = it.toPaint()
        context.fillOval(x - radius, y - radius, radius * 2, radius * 2)
        context.fill()
    }

    stroke?.let {
        context.stroke = it.toPaint()
        context.strokeOval(x - radius, y - radius, radius * 2, radius * 2)
        context.stroke()
    }
}

fun Rect.render(canvas: Canvas) {
    val context = canvas.graphicsContext2D

    fill?.let {
        context.fill = it.toPaint()
        context.fillRect(x, y, width, height)
    }

    stroke?.let {
        context.stroke = it.toPaint()
        context.strokeRect(x, y, width, height)
    }
}




fun Group.render(context: Canvas) {
    transform?.run {
        context.graphicsContext2D.translate(this.translate?.x ?:.0, this.translate?.y ?:.0)
    }


    children.forEach { node ->
        when (node) {
            is Circle       -> node.render(context)
            is Rect         -> node.render(context)
            is Group        -> node.render(context)
            is PathNode     -> node.render(context)
            else -> error("Unknow type ${node::class}")
        }
    }

//    context.translateX = .0
//    context.translateY = .0

}


/**
 * JFx Canvas version. See https://docs.oracle.com/javafx/2/canvas/jfxpub-canvas.htm
 */
class JFxVizRenderer(val canvas: Canvas) : VizRenderer {

    override fun render(viz: Viz) {
        val context = canvas.graphicsContext2D
        context.clearRect(.0, .0, context.canvas.width, context.canvas.height)
        viz.root.render(canvas)
    }

}

fun ColorOrGradient.toPaint() = when(this) {
    is LinearGradient -> toLinearGradientJFX()
    is RadialGradient -> toRadialGradientJFX()
    is Color -> jfxColor
    else -> error("Unknown type $this")
}


fun LinearGradient.toLinearGradientJFX(): JfxLinearGradient = JfxLinearGradient(
        x1, y1, x2, y2,
        false,
        CycleMethod.NO_CYCLE, colorStops.toStops()
)


fun RadialGradient.toRadialGradientJFX(): JfxRadialGradient  = JfxRadialGradient(.0, .0, cx, cy, r,
        false,
        CycleMethod.NO_CYCLE, colorStops.toStops())

private fun List<ColorStop>.toStops(): List<Stop>? =  map { Stop(it.percent, it.color.jfxColor) }

