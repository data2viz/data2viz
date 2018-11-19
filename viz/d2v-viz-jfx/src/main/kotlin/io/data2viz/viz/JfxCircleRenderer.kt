package io.data2viz.viz

import javafx.scene.canvas.*


fun CircleNode.render(gc: GraphicsContext) {

	style.fill?.let {
		gc.fillOval(x - radius, y - radius, radius * 2, radius * 2)
	}

	style.stroke?.let {
		gc.strokeOval(x - radius, y - radius, radius * 2, radius * 2)
	}
}
