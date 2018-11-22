package io.data2viz.viz

import javafx.scene.canvas.*


fun CircleNode.render(gc: GraphicsContext) {

	fill?.let {
		gc.fillOval(x - radius, y - radius, radius * 2, radius * 2)
	}

	stroke?.let {
		gc.strokeOval(x - radius, y - radius, radius * 2, radius * 2)
	}
}
