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
import io.data2viz.viz.Viz
import io.data2viz.viz.VizRenderer
import kotlinx.cinterop.*
import platform.CoreGraphics.*
import platform.UIKit.UIGraphicsGetCurrentContext
import platform.UIKit.setNeedsDisplay

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

    override fun startAnimations() {
        TODO("Not yet implemented")
    }

    override fun stopAnimations() {
        TODO("Not yet implemented")
    }

    /**
     * The real rendering
     */
    internal fun draw(aRect: CValue<CGRect>) {
//		println("IOSCanvasRenderer.draw")
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
        CGContextSetFillColor(context, Colors.Web.white.toColor())
        CGContextFillRect(context, aRect)
    }


}

internal fun Color.toColor(): CValues<DoubleVar> {
    return cValuesOf (this.r/255.0, this.g/255.0, this.b/255.0, this.alpha.value)
}