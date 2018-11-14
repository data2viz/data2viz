package io.data2viz.viz

fun RectNode.render(renderer: JFxVizRenderer) {
	val gc = renderer.gc

	style.fill?.let {
		gc.fillRect(x, y, width, height)
	}

	style.stroke?.let {
		gc.strokeRect(x, y, width, height)
	}
}
