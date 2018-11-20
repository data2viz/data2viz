package io.data2viz.viz

import android.graphics.*
import io.data2viz.color.*
import io.data2viz.color.Color
import io.data2viz.color.LinearGradient
import io.data2viz.color.RadialGradient

typealias ALinearGradient = android.graphics.LinearGradient
typealias ARadialGradient = android.graphics.RadialGradient


fun ColorOrGradient.updatePaint(paint: Paint, renderer: AndroidCanvasRenderer) {
	when (this) {
		is Color -> {
			paint.color = this.toColor()
			paint.shader = null
		}
		is LinearGradient -> paint.shader = this.toLinearGradient(renderer)
		is RadialGradient -> paint.shader = this.toRadialGradient(renderer)
		else -> error("Unknown type :: ${this::class}")
	}
}

private fun RadialGradient.toRadialGradient(renderer: AndroidCanvasRenderer) =
	with(renderer) {
		ARadialGradient(
			cx.dp,
			cy.dp,
			radius.dp,
			IntArray(colorStops.size) { colorStops[it].color.toColor() },
			FloatArray(colorStops.size) { colorStops[it].percent.value.toFloat() },
			Shader.TileMode.CLAMP)
	}


private fun LinearGradient.toLinearGradient(renderer: AndroidCanvasRenderer) =
	with(renderer) {
		ALinearGradient(
			x1.dp,
			y1.dp,
			x2.dp,
			y2.dp,
			IntArray(colorStops.size) { colorStops[it].color.toColor() },
			FloatArray(colorStops.size) { colorStops[it].percent.value.toFloat() },
			Shader.TileMode.CLAMP)
	}

fun Color.toColor() =
	((255 * this.alpha).toInt() and 0xff shl 24) or
			(this.r and 0xff shl 16) or
			(this.g and 0xff shl 8) or
			(this.b and 0xff)
