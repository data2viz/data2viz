package io.data2viz.viz

import android.graphics.*


fun CircleNode.render(renderer: AndroidCanvasRenderer) {
	val canvas = renderer.canvas
	style.fill?.let {
		paint.style = Paint.Style.FILL
		it.updatePaint(paint)
		canvas.drawCircle(
			x.toFloat(),
			y.toFloat(),
			radius.toFloat(),
			paint)
	}
	style.stroke?.let {
		paint.style = Paint.Style.STROKE
		it.updatePaint(paint)
		canvas.drawCircle(
			x.toFloat(),
			y.toFloat(),
			radius.toFloat(),
			paint)
	}
}
