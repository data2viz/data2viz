/*
 * Copyright (c) 2018-2019. data2viz sàrl.
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

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.view.MotionEvent
import android.view.View
import io.data2viz.timer.Timer
import io.data2viz.timer.timer


internal val paint = Paint().apply {
    isAntiAlias = true
}


public fun Viz.toView(context: Context): VizView = VizView(this, context)

public interface VizTouchListener {
    public fun onTouchEvent(view: View, event: MotionEvent?): Boolean
}

internal fun Paint.getNumberHeight(): Int {
    val rect = android.graphics.Rect()
    getTextBounds("a", 0, 1, rect)
    return rect.height()
}

public class AndroidCanvasRenderer(

    public val context: Context,

    override val viz: Viz,

    /**
     *
     */
    public var canvas: Canvas = Canvas(),

    /**
     * Invalidate callback. The renderer use it to invalidate the view.
     * TODO : we may be more explicit and pass the View to call directly the invalidate function
     */
    internal val renderCallback: () -> Unit

) : VizRenderer {


    public val onTouchListeners: MutableList<VizTouchListener> = mutableListOf<VizTouchListener>()

    public var scale: Float = 1F

    private val animationTimers = mutableListOf<Timer>()


    init {
        viz.renderer = this
    }

    /**
     *
     */
    override fun render() {
        viz.layers.forEach { layer ->
            if (layer.visible)
                layer.render(this)
        }
        renderCallback()
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


    public val Double.dp: Float
        get() = (this * scale).toFloat()

}
