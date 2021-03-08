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
import io.data2viz.timer.Timer
import io.data2viz.timer.timer

@SuppressLint("ViewConstructor")
class VizView(val viz: Viz, context: Context) : View(context) {

    private val renderer: AndroidCanvasRenderer = AndroidCanvasRenderer(context, viz) {
        invalidate()
    }
    private val timers = mutableListOf<Timer>()

    @SuppressLint("ClickableViewAccessibility")
    override fun onTouchEvent(event: MotionEvent?): Boolean {
//        Log.d(AndroidCanvasRenderer::class.java.simpleName, "onTouchEvent $event")

        var handled = super.onTouchEvent(event)
        if (!handled) {
            renderer.onTouchListeners.forEach {
                it.onTouchEvent(this, event)
            }
        }

        handled = true
        return handled

    }

    /**
     *
     */
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

    var fps = 0.0

    override fun onDraw(canvas: Canvas) {
        drawCount++
        if (drawCount == 100) {
            val delta = System.currentTimeMillis() - startTime
            fps = 100_000.0 / delta
            startTime = System.currentTimeMillis()
            drawCount = -1
        }

        renderer.canvas = canvas
        renderer.render()
    }


}