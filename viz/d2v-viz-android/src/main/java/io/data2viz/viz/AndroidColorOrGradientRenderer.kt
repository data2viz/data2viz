package io.data2viz.viz

import android.graphics.*
import io.data2viz.color.*
import io.data2viz.color.Color
import io.data2viz.color.LinearGradient
import io.data2viz.color.RadialGradient

typealias ALinearGradient = android.graphics.LinearGradient
typealias ARadialGradient = android.graphics.RadialGradient


fun ColorOrGradient.updatePaint(paint: Paint) {
	when (this) {
		is Color -> {
			paint.color = this.toColor()
			paint.shader = null
		}
		is LinearGradient -> paint.shader = this.toLinearGradient()
		is RadialGradient -> paint.shader = this.toRadialGradient()
		else -> error("Unknown type :: ${this::class}")
	}
}

private fun RadialGradient.toRadialGradient() =
	ARadialGradient(
		cx.toFloat(),
		cy.toFloat(),
		radius.toFloat(),
		IntArray(colorStops.size) { colorStops[it].color.toColor() },
		FloatArray(colorStops.size) { colorStops[it].percent.toFloat() },
		Shader.TileMode.CLAMP)


private fun LinearGradient.toLinearGradient() =
	ALinearGradient(
		x1.toFloat(),
		y1.toFloat(),
		x2.toFloat(),
		y2.toFloat(),
		IntArray(colorStops.size) { colorStops[it].color.toColor() },
		FloatArray(colorStops.size) { colorStops[it].percent.toFloat() },
		Shader.TileMode.CLAMP)

fun Color.toColor() =
	((255 * this.alpha).toInt() and 0xff shl 24) or
			(this.r and 0xff shl 16) or
			(this.g and 0xff shl 8) or
			(this.b and 0xff)
