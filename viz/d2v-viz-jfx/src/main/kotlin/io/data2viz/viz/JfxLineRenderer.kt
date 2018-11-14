package io.data2viz.viz

fun LineNode.render(renderer: JFxVizRenderer){
	val gc = renderer.gc
	gc.beginPath()
	gc.moveTo(x1, y1)
	gc.lineTo(x2, y2)
	gc.stroke()
}
