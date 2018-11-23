package io.data2viz.viz

import org.w3c.dom.*
import kotlin.math.*

fun CircleNode.render(context: CanvasRenderingContext2D) {

	context.beginPath()
	context.arc(x, y, radius, .0, 2 * PI, false)

	fill?.let {
		context.fill()
	}

	stroke?.let {
		context.stroke()
	}
}

