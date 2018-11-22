package io.data2viz.viz

import org.w3c.dom.*

fun RectNode.render(context: CanvasRenderingContext2D) {

	fill?.let {
		context.fillStyle = it.toCanvasPaint(context)
		context.fillRect(x, y, width, height)
	}

	stroke?.let {
		context.strokeStyle = it.toCanvasPaint(context)
		context.strokeRect(x, y, width, height)
	}

}
