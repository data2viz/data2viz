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
import io.data2viz.color.LinearGradient
import io.data2viz.color.RadialGradient
import io.data2viz.timer.Timer
import io.data2viz.timer.timer
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

	internal val uiTouchesHandler = UITouchesHandler(iosCanvasView)


    var context: CPointer<CGContext>? = null

	internal var colorSpace: CPointer<CGColorSpace>? = null


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
        updateContextAndColorSpace()
        clear(aRect)
        viz.layers.forEach { layer ->
            if (layer.visible)
                layer.render(this)
        }
    }

    private fun updateContextAndColorSpace() {
        context = UIGraphicsGetCurrentContext()
		colorSpace = CGColorSpaceCreateDeviceRGB()
    }

    private fun clear(aRect: CValue<CGRect>) {
        CGContextClearRect(context, iosCanvasView.bounds)
    }

}


internal fun IOSCanvasRenderer.drawLinearGradient(
	path: UIBezierPath,
	fillColor: LinearGradient
) {
	CGContextSaveGState(context)
	path.addClip()
	memScoped {
		val gradientSize = fillColor.colorStops.size
		val locations: CPointer<DoubleVarOf<CGFloat /* = kotlin.Double */>> =
			allocArray<CGFloatVar>(gradientSize)

		fillColor.colorStops.forEachIndexed { index, colorStop ->
			locations[index] = colorStop.percent.value
			locations[index] = colorStop.percent.value
		}

		val options = kCGGradientDrawsBeforeStartLocation or kCGGradientDrawsAfterEndLocation
		val colorComponents = if (gradientSize == 2)
			cValuesOf(
				fillColor.colorStops[0].color.r / 255.0,
				fillColor.colorStops[0].color.g / 255.0,
				fillColor.colorStops[0].color.b / 255.0,
				fillColor.colorStops[0].color.alpha.value,
				fillColor.colorStops[1].color.r / 255.0,
				fillColor.colorStops[1].color.g / 255.0,
				fillColor.colorStops[1].color.b / 255.0,
				fillColor.colorStops[1].color.alpha.value
			)
		else cValuesOf(*fillColor.colorStops.flatMap {  //<-spread operator
			listOf(
				it.color.r / 255.0,
				it.color.g / 255.0,
				it.color.b / 255.0,
				it.color.alpha.value
			)
		}.toDoubleArray())

		val gradient = CGGradientCreateWithColorComponents(
			colorSpace,
			colorComponents,
			locations,
			gradientSize.toULong()
		)

		CGContextDrawLinearGradient(
			context,
			gradient,
			CGPointMake(fillColor.x1, fillColor.y1),
			CGPointMake(fillColor.x2, fillColor.y2),
			options
		)
		CGGradientRelease(gradient)
	}
	CGContextRestoreGState(context)
}

internal fun IOSCanvasRenderer.drawRadialGradient(
	path: UIBezierPath,
	fillColor: RadialGradient
) {
	CGContextSaveGState(context)
	path.addClip()
	memScoped {
		val gradientSize = fillColor.colorStops.size
		val locations: CPointer<DoubleVarOf<CGFloat /* = kotlin.Double */>> =
			allocArray<CGFloatVar>(gradientSize)

		fillColor.colorStops.forEachIndexed { index, colorStop ->
			locations[index] = colorStop.percent.value
			locations[index] = colorStop.percent.value
		}

		val options = kCGGradientDrawsBeforeStartLocation or kCGGradientDrawsAfterEndLocation
		val colorComponents = if (gradientSize == 2)
			cValuesOf(
				fillColor.colorStops[0].color.r / 255.0,
				fillColor.colorStops[0].color.g / 255.0,
				fillColor.colorStops[0].color.b / 255.0,
				fillColor.colorStops[0].color.alpha.value,
				fillColor.colorStops[1].color.r / 255.0,
				fillColor.colorStops[1].color.g / 255.0,
				fillColor.colorStops[1].color.b / 255.0,
				fillColor.colorStops[1].color.alpha.value
			)
		else cValuesOf(*fillColor.colorStops.flatMap {  //<-spread operator
			listOf(
				it.color.r / 255.0,
				it.color.g / 255.0,
				it.color.b / 255.0,
				it.color.alpha.value
			)
		}.toDoubleArray())

		val gradient = CGGradientCreateWithColorComponents(
			colorSpace,
			colorComponents,
			locations,
			gradientSize.toULong()
		)

		CGContextDrawRadialGradient(
			context,
			gradient,
			CGPointMake(fillColor.cx, fillColor.cy),
			0.0,
			CGPointMake(fillColor.cx, fillColor.cy),
			fillColor.radius,
			options
		)
		CGGradientRelease(gradient)
	}
	CGContextRestoreGState(context)
}




internal fun Color.toUIColor():UIColor {
    return UIColor (
        red = this.r/255.0,
        green = this.g/255.0,
        blue = this.b/255.0,
        alpha = this.alpha.value)
}
internal fun Color.toColor(): CValues<DoubleVar> {
    return cValuesOf (this.r/255.0, this.g/255.0, this.b/255.0, this.alpha.value)
}
