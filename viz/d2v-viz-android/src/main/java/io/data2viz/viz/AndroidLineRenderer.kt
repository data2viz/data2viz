package io.data2viz.viz

import android.graphics.*

fun LineNode.render(renderer: AndroidCanvasRenderer) {
	stroke?.let {
		paint.style = Paint.Style.STROKE
		paint.strokeWidth = strokeWidth?.toFloat() ?: 1f
		it.updatePaint(paint, renderer)
		with(renderer) {
			canvas.drawLine(x1.dp, y1.dp, x2.dp, y2.dp, paint)
		}
	}
}
