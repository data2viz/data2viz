package io.data2viz.viz

import android.graphics.*

fun LineNode.render(renderer: AndroidCanvasRenderer) {
	style.stroke?.let {
		paint.style = Paint.Style.STROKE
		paint.strokeWidth = style.strokeWidth?.toFloat() ?: 1f
		it.updatePaint(paint)
		with(renderer) {
			canvas.drawLine(x1.toFloat(), y1.toFloat(), x2.toFloat(), y2.toFloat(), paint)
		}
	}
}
