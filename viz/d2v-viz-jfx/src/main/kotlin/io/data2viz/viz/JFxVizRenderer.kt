package io.data2viz.viz

import io.data2viz.color.*
import io.data2viz.timer.Timer
import io.data2viz.timer.timer
import javafx.geometry.VPos
import javafx.scene.canvas.Canvas
import javafx.scene.paint.CycleMethod
import javafx.scene.paint.Stop
import javafx.scene.text.Font
import javafx.scene.text.TextAlignment
import kotlin.math.PI

typealias JfxLinearGradient = javafx.scene.paint.LinearGradient
typealias JfxRadialGradient = javafx.scene.paint.RadialGradient

/**
 * JFx Canvas version. See https://docs.oracle.com/javafx/2/canvas/jfxpub-canvas.htm
 */
class JFxVizRenderer(val canvas: Canvas, val viz: Viz) : VizRenderer {

    internal val gc = canvas.graphicsContext2D

    private val animationTimers = mutableListOf<Timer>()

    init {
        viz.renderer = this
    }


    override fun render(viz: Viz) {
        gc.clearRect(.0, .0, canvas.width, canvas.height)
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

    fun addTransform(transform: Transform) {
        gc.translate(transform.translate?.x ?: .0, transform.translate?.y ?:.0)
        gc.rotate(+ (transform.rotate?.delta ?: .0) * 180 / PI)
    }

    fun removeTransform(transform: Transform) {
        gc.translate(-(transform.translate?.x ?:.0), -(transform.translate?.y ?:.0))
        gc.rotate(- (transform.rotate?.delta ?: .0) * 180 / PI)
    }

}



fun GroupNode.render(renderer: JFxVizRenderer) {

    children.forEach { node ->

        val gc = renderer.gc

        if (node is HasTransform) {
            node.transform?.also {
                renderer.addTransform(it)
            }
        }

        if (node is HasFill) {
            gc.fill = node.style.fill?.toPaint()
        }

        if (node is HasStroke) {
            gc.stroke = node.style.stroke?.toPaint()
            gc.lineWidth = node.style.strokeWidth ?: 1.0
        }

        if (node.visible)
            when (node) {
                is CircleNode       -> node.render(renderer)
                is RectNode         -> node.render(renderer)
                is GroupNode        -> node.render(renderer)
                is PathNode     -> node.render(renderer)
                is TextNode         -> node.render(renderer)
                is LineNode         -> node.render(renderer)
                else            -> error("Unknow type ${node::class}")
            }

        if (node is HasTransform) {
            node.transform?.also {
                renderer.removeTransform(it)
            }
        }

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

fun RadialGradient.toRadialGradientJFX(): JfxRadialGradient  = JfxRadialGradient(.0, .0, cx, cy, radius,
        false,
        CycleMethod.NO_CYCLE, colorStops.toStops())

private fun List<ColorStop>.toStops(): List<Stop>? =  map { Stop(it.percent, it.color.jfxColor) }

fun CircleNode.render(renderer: JFxVizRenderer) {
    val context = renderer.gc

    style.fill?.let {
        context.fillOval(x - radius, y - radius, radius * 2, radius * 2)
    }

    style.stroke?.let {
        context.strokeOval(x - radius, y - radius, radius * 2, radius * 2)
    }
}

fun RectNode.render(renderer: JFxVizRenderer) {
    val gc = renderer.gc

    style.fill?.let {
        gc.fillRect(x, y, width, height)
    }

    style.stroke?.let {
        gc.strokeRect(x, y, width, height)
    }
}

fun TextNode.render(renderer: JFxVizRenderer){
    val gc = renderer.gc
    gc.textAlign = style.anchor.jfx
    gc.textBaseline = style.baseline.jfx

    gc.font = when(fontFamily) {
        null -> Font(Font.getDefault().name, fontSize)
        else -> Font(fontFamily, fontSize)
    }

    style.fill?.let {
        gc.fillText(textContent, x, y)
    }

    style.stroke?.let {
        gc.strokeText(textContent, x, y)
    }
}

val TextAlignmentBaseline.jfx: VPos
    get() = when(this){
        TextAlignmentBaseline.BASELINE  -> VPos.BASELINE
        TextAlignmentBaseline.HANGING   -> VPos.TOP
        TextAlignmentBaseline.MIDDLE    -> VPos.CENTER
    }

val TextAnchor.jfx: TextAlignment
    get() = when(this){
        TextAnchor.START    -> TextAlignment.LEFT
        TextAnchor.END      -> TextAlignment.RIGHT
        TextAnchor.MIDDLE   -> TextAlignment.CENTER
    }


fun LineNode.render(renderer: JFxVizRenderer){
    val gc = renderer.gc
    gc.beginPath()
    gc.moveTo(x1, y1)
    gc.lineTo(x2, y2)
    gc.stroke()
}

val Color.jfxColor: javafx.scene.paint.Color
    get() = javafx.scene.paint.Color.rgb(r, g, b, alpha)


