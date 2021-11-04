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

import cnames.structs.CGContext
import io.data2viz.color.Color
import io.data2viz.color.Colors
import io.data2viz.timer.Timer
import io.data2viz.timer.timer
import io.data2viz.viz.Viz
import io.data2viz.viz.VizRenderer
import kotlinx.cinterop.*
import platform.CoreGraphics.*
import platform.UIKit.*

public class IOSCanvasRenderer(
    override val viz: Viz,
    val iosCanvasView: IOSCanvasView
): VizRenderer {

    init {
        viz.renderer = this
    }

    var context: CPointer<CGContext>? = null

    override fun render() {
        iosCanvasView.setNeedsDisplay()
    }

    private val animationTimers = mutableListOf<Timer>()

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

    /**
     * The real rendering
     */
    internal fun draw(aRect: CValue<CGRect>) {
        updateContext()
        clear(aRect)
        viz.layers.forEach { layer ->
            if (layer.visible)
                layer.render(this)
        }
    }

    private fun updateContext() {
        context = UIGraphicsGetCurrentContext()
    }

    private fun clear(aRect: CValue<CGRect>) {
        CGContextClearRect(context, iosCanvasView.bounds)
    }


}

internal fun Color.toColor(): CValues<DoubleVar> {
    return cValuesOf (this.r/255.0, this.g/255.0, this.b/255.0, this.alpha.value)
}
