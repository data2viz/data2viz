package io.data2viz.viz

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.util.Log
import android.view.MotionEvent
import android.view.View
import io.data2viz.timer.Timer
import io.data2viz.timer.timer


val paint = Paint().apply {
    isAntiAlias = true
}


fun Viz.toView(context: Context): VizView = VizView(this, context)

interface VizTouchListener {
    fun onTouchEvent(view: View, event: MotionEvent?): Boolean

}

@SuppressLint("ViewConstructor")
class VizView(val viz: Viz, context: Context) : View(context) {

    private val renderer: AndroidCanvasRenderer = AndroidCanvasRenderer(context, viz)
    private val timers = mutableListOf<Timer>()

    @SuppressLint("ClickableViewAccessibility")
    override fun onTouchEvent(event: MotionEvent?): Boolean {
//        Log.d(AndroidCanvasRenderer::class.java.simpleName, "onTouchEvent $event")

        var handled = super.onTouchEvent(event)
        if(!handled) {
            renderer.onTouchListeners.forEach {
                it.onTouchEvent(this, event)
            }
        }

        handled = true
        return handled
    }

    fun startAnimations() {
        if (viz.animationTimers.isNotEmpty()) {
            viz.animationTimers.forEach { anim ->
                timers += timer { time ->
                    anim(time)
                }
            }
            timers += timer {
                invalidate()
            }
        }
    }

    fun stopAnimations() {
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
        if (drawCount == 100) {
            val delta = System.currentTimeMillis() - startTime
            val fps = 100_000 / delta
            startTime = System.currentTimeMillis()
            drawCount = -1
        }

        renderer.canvas = canvas
        renderer.render()
    }


}

fun Paint.getNumberHeight(): Int {
    val rect = android.graphics.Rect()
    getTextBounds("a", 0, 1, rect)
    return rect.height()
}

class AndroidCanvasRenderer(
    val context: Context,
    override val viz: Viz,
    var canvas: Canvas = Canvas()
) : VizRenderer {



    val onTouchListeners = mutableListOf<VizTouchListener>()

    var scale = 1F

    private val animationTimers = mutableListOf<Timer>()


    init {
        viz.renderer = this
    }

    override fun render() {
        viz.layers.forEach { layer ->
            if (layer.visible)
                layer.render(this)
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


    val Double.dp: Float
        get() = (this * scale).toFloat()

}
