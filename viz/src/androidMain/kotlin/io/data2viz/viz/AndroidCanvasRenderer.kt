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

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Typeface
import android.view.MotionEvent
import android.view.View
import io.data2viz.geom.Rect
import io.data2viz.geom.RectGeom
import io.data2viz.timer.Timer
import io.data2viz.timer.timer


private typealias AndroidRect = android.graphics.Rect


internal val paint = Paint().apply {
    isAntiAlias = true
}

/**
 * The screen density. Used internaly to convert all the sizes from CSS pixels to device pixels.
 */
internal var density = 1f

/**
 * Converts the viz pixel value (CSS pixel) to device pixel.
 */
internal val Double.dp: Float
    get() = (this * density).toFloat()


internal actual fun textMeasure(
    text: String,
    fontSize: Double,
    fontFamily: FontFamily,
    fontWeight: FontWeight,
    fontStyle: FontPosture
): Rect {

    val bounds = AndroidRect()
    val xxBounds = AndroidRect()
    paint.textSize = fontSize.toFloat()
    paint.typeface = Typeface.create(fontFamily.name, getAndroidStyle(fontWeight, fontStyle))
    paint.getTextBounds("x${text}x", 0, text.length + 2, bounds)
    paint.getTextBounds("xx", 0, 2, xxBounds)
    return RectGeom(
        width = bounds.width().toDouble() - xxBounds.width().toDouble(),
        height = bounds.height().toDouble()
    )
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

    public var canvas: Canvas = Canvas(),

    /**
     * Invalidate callback. The renderer use it to invalidate the view.
     * TODO : we may be more explicit and pass the View to call directly the invalidate function
     */
    internal val renderCallback: () -> Unit

) : VizRenderer {


    public val onTouchListeners: MutableList<VizTouchListener> = mutableListOf<VizTouchListener>()


    private val animationTimers = mutableListOf<Timer>()


    init {
        viz.renderer = this
    }

    /**
     * On the Android platform the rendering is not done directly.
     * Instead we call, through the callback, an invalidate of the view
     * that launch the onDraw
     */
    override fun render() {
        renderCallback()
    }

    /**
     * The real rendering
     */
    internal fun draw() {
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

}
