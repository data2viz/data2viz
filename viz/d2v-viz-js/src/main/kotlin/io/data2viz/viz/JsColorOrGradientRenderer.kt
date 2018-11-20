package io.data2viz.viz

import io.data2viz.color.*
import org.w3c.dom.*

fun ColorOrGradient.toCanvasPaint(context: CanvasRenderingContext2D):Any = when(this) {
	is Color -> this.rgba
	is LinearGradient -> this.toCanvasGradient(context)
	is RadialGradient -> this.toCanvasGradient(context)
	else -> error("Unknown type :: ${this::class}")
}

fun LinearGradient.toCanvasGradient(context: CanvasRenderingContext2D): CanvasGradient {
	val gradient = context.createLinearGradient(x1, y1, x2, y2)
	this.colorStops.forEach { cs ->
		gradient.addColorStop(cs.percent.value, cs.color.rgba)
	}
	return gradient
}

fun RadialGradient.toCanvasGradient(context: CanvasRenderingContext2D): CanvasGradient {
	val gradient = context.createRadialGradient(cx, cy, 0.0, cx, cy, radius)
	this.colorStops.forEach { cs ->
		gradient.addColorStop(cs.percent.value, cs.color.rgba)
	}
	return gradient
}

