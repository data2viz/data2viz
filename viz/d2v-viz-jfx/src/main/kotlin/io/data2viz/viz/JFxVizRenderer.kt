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


