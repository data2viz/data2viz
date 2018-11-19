package io.data2viz.viz

import android.graphics.*

fun RectNode.render(renderer: AndroidCanvasRenderer) {
	val canvas = renderer.canvas
	style.fill?.let {
		paint.style = Paint.Style.FILL
		it.updatePaint(paint)
		canvas.drawRect(
			x.toFloat(),
			y.toFloat(),
			(x + width).toFloat(),
			(y + height).toFloat(),
			paint
		)
	}
	style.stroke?.let {
		paint.style = Paint.Style.STROKE
		it.updatePaint(paint)
		canvas.drawRect(
			x.toFloat(),
			y.toFloat(),
			(x + width).toFloat(),
			(y + height).toFloat(),
			paint
		)
	}

}
