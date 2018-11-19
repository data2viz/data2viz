package io.data2viz.viz

import javafx.scene.canvas.*

fun LineNode.render(gc: GraphicsContext){
	gc.beginPath()
	gc.moveTo(x1, y1)
	gc.lineTo(x2, y2)
	gc.stroke()
}
