package io.data2viz.viz

import io.data2viz.timer.Timer
import io.data2viz.timer.timer
import javafx.scene.canvas.*
import kotlin.math.PI


/**
 * JFx Canvas version. See https://docs.oracle.com/javafx/2/canvas/jfxpub-canvas.htm
 */
class JFxVizRenderer(
    val canvas: Canvas,
    override val viz: Viz) : VizRenderer {

    private val animationTimers = mutableListOf<Timer>()

    init {
        viz.renderer = this
    }

    override fun render() {
        val gc = canvas.graphicsContext2D
        gc.clearRect(.0, .0, canvas.width, canvas.height)
        viz.layers.forEach { layer ->
            if (layer.visible)
                layer.render(gc)
        }
    }

    override fun startAnimations() {
        if (viz.animationTimers.isNotEmpty()) {
            viz.animationTimers.forEach { anim ->
                animationTimers += timer { time ->
                    anim(time)
                }
            }
            animationTimers += timer {
                render()
            }
        }
    }

    override fun stopAnimations() {
        animationTimers.forEach { it.stop() }
    }


}

fun GraphicsContext.addTransform(transform: Transform) {
    translate(transform.translate?.x ?: .0, transform.translate?.y ?:.0)
    rotate(+ (transform.rotate?.delta ?: .0) * 180 / PI)
}

fun GraphicsContext.removeTransform(transform: Transform) {
    translate(-(transform.translate?.x ?:.0), -(transform.translate?.y ?:.0))
    rotate(- (transform.rotate?.delta ?: .0) * 180 / PI)
}
