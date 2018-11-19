package io.data2viz.viz

import android.graphics.*

fun RectNode.render(renderer: AndroidCanvasRenderer) {
	val canvas = renderer.canvas
	with(renderer) {
		style.fill?.let {
			paint.style = Paint.Style.FILL
			it.updatePaint(paint, renderer)
			canvas.drawRect(
				x.dp,
				y.dp,
				(x + width).dp,
				(y + height).dp,
				paint)
		}
		style.stroke?.let {
			paint.style = Paint.Style.STROKE
			it.updatePaint(paint, renderer)
			canvas.drawRect(
				x.dp,
				y.dp,
				(x + width).dp,
				(y + height).dp,
				paint)
		}
	}

}
