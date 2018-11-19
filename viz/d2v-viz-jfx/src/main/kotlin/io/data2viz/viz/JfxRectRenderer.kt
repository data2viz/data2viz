package io.data2viz.viz

import javafx.scene.canvas.*

fun RectNode.render(gc: GraphicsContext) {

	style.fill?.let {
		gc.fillRect(x, y, width, height)
	}

	style.stroke?.let {
		gc.strokeRect(x, y, width, height)
	}
}
