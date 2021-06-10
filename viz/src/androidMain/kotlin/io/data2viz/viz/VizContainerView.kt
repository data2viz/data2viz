/*
 * Copyright (c) 2018-2021. data2viz sàrl.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 *
 */

package io.data2viz.viz

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Canvas
import android.view.MotionEvent
import android.view.View
import io.data2viz.geom.Size
import io.data2viz.geom.size
import io.data2viz.timer.Timer


/**
 *
 */
@SuppressLint("ViewConstructor")
public open class VizContainerView(
    context: Context
)
    : View(context), VizContainer {


    private val vizs = mutableListOf<Viz>()
    private val renderers = mutableListOf<AndroidCanvasRenderer>()

    public override val density: Double
        get() = context.resources.displayMetrics.density.toDouble()

    override fun newViz(init: Viz.() -> Unit): Viz {
        val viz = Viz().apply {
            size = size(this@VizContainerView.size.width, this@VizContainerView.size.height)
            init()
        }
        vizs += viz

        val renderer = AndroidCanvasRenderer(context, viz) {
            //todo stocker la viz à render.
            invalidate()
        }
        renderers += renderer
        return viz
    }


    override var size: Size = size(500.0, 300.0)
        get() = field
        set(value) {
            field = value
            updateScale()
            vizs.forEach { viz: Viz ->
                viz.size = value
            }
        }

    //    private var _scale: Float = 1f
//
//    internal val scale: Float
//        get() = _scale
//
    private val timers = mutableListOf<Timer>()

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        updateScale()
    }

    /**
     * Update the scale
     */
    private fun updateScale() {
        scale = (width / size.width).toFloat()
    }

    override fun onDraw(canvas: Canvas) {
        drawCount++
        if (drawCount == 100) {
            val delta = System.currentTimeMillis() - startTime
            fps = 100_000.0 / delta
            startTime = System.currentTimeMillis()
            drawCount = -1
        }

        for (i in vizs.indices) {
            val renderer = renderers[i]
            renderer.canvas = canvas
            renderer.draw()
        }
    }


    @SuppressLint("ClickableViewAccessibility")
    override fun onTouchEvent(event: MotionEvent?): Boolean {
//        Log.d(AndroidCanvasRenderer::class.java.simpleName, "onTouchEvent $event")

        var handled = super.onTouchEvent(event)
        if (!handled) {
            renderers.forEach{ renderer ->
                renderer.onTouchListeners.forEach {
                    it.onTouchEvent(this, event)
                }
            }
        }

        handled = true
        return handled

    }

    /**
     *
     */
//    public fun startAnimations() {
//        if (viz.animationTimers.isNotEmpty()) {
//            viz.animationTimers.forEach { anim ->
//                timers += timer { time ->
//                    anim(time)
//                }
//            }
//            timers += timer {
//                invalidate()
//            }
//        }
//    }
//
//    public fun stopAnimations() {
//        for (timer in timers) {
//            timer.stop()
//        }
//        timers.clear()
//    }
//

    public var drawCount: Int = -1
    private var startTime = System.currentTimeMillis()

    public var fps: Double = 0.0
}
