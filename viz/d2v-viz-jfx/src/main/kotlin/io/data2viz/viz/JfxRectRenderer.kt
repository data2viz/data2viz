package io.data2viz.viz

import javafx.scene.canvas.*

fun RectNode.render(gc: GraphicsContext) {

	fill?.let {
		gc.fillRect(x, y, width, height)
	}

	stroke?.let {
		gc.strokeRect(x, y, width, height)
	}
}
