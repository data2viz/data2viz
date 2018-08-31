package io.data2viz.viz

import io.data2viz.color.*
import javafx.scene.canvas.Canvas
import javafx.scene.paint.CycleMethod
import javafx.scene.paint.Stop

typealias JfxLinearGradient = javafx.scene.paint.LinearGradient
typealias JfxRadialGradient = javafx.scene.paint.RadialGradient

/**
 * JFx Canvas version. See https://docs.oracle.com/javafx/2/canvas/jfxpub-canvas.htm
 */
class JFxVizRenderer(val canvas: Canvas) : VizRenderer {

    internal val gc = canvas.graphicsContext2D


    override fun render(viz: Viz) {
        gc.clearRect(.0, .0, canvas.width, canvas.height)
        viz.root.render(this)
    }

    fun addTransform(transform: Transform) {
        gc.translate(transform.translate?.x ?: .0, transform.translate?.y ?:.0)
    }

    fun removeTransform(transform: Transform) {
        gc.translate(-(transform.translate?.x ?:.0), -(transform.translate?.y ?:.0))
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

fun Circle.render(renderer: JFxVizRenderer) {
    val context = renderer.gc

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

fun Rect.render(renderer: JFxVizRenderer) {
    val gc = renderer.gc

    fill?.let {
        gc.fill = it.toPaint()
        gc.fillRect(x, y, width, height)
    }

    stroke?.let {
        gc.stroke = it.toPaint()
        gc.strokeRect(x, y, width, height)
    }
}

fun Text.render(renderer: JFxVizRenderer){
    val gc = renderer.gc
    gc.fillText(textContent, x, y)
}

fun Line.render(renderer: JFxVizRenderer){
    val gc = renderer.gc
    gc.lineWidth = 1.0
    gc.moveTo(x1, y1)
    gc.lineTo(x2, y2)
    stroke?.let { gc.stroke = it.toPaint() }
    gc.stroke()
}


fun Group.render(renderer: JFxVizRenderer) {

    children.forEach { node ->

        if (node is HasTransform) {
            node.transform?.also {
                renderer.addTransform(it)
            }
        }

        when (node) {
            is Circle       -> node.render(renderer)
            is Rect         -> node.render(renderer)
            is Group        -> node.render(renderer)
            is PathNode     -> node.render(renderer)
            is Text         -> node.render(renderer)
            is Line         -> node.render(renderer)
            else            -> error("Unknow type ${node::class}")
        }

        if (node is HasTransform) {
            node.transform?.also {
                renderer.removeTransform(it)
            }
        }

    }

}
