package io.data2viz.viz

import android.annotation.*
import android.content.*
import android.graphics.*
import android.view.*
import io.data2viz.timer.*

typealias ALinearGradient = android.graphics.LinearGradient
typealias ARadialGradient = android.graphics.RadialGradient

val paint = Paint().apply {
    isAntiAlias = true
}


fun Viz.toView(context: Context): VizView = VizView(this, context)


@SuppressLint("ViewConstructor")
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
