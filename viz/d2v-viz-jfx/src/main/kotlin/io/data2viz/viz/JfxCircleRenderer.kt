package io.data2viz.viz


fun CircleNode.render(renderer: JFxVizRenderer) {
	val context = renderer.gc

	style.fill?.let {
		context.fillOval(x - radius, y - radius, radius * 2, radius * 2)
	}

	style.stroke?.let {
		context.strokeOval(x - radius, y - radius, radius * 2, radius * 2)
	}
}
