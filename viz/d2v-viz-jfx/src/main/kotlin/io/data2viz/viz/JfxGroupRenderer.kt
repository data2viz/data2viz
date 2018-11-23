package io.data2viz.viz

import javafx.scene.canvas.*


fun GroupNode.render(gc: GraphicsContext) {

	children.forEach { node ->

		if (node is HasTransform) {
			node.transform?.also {
				gc.addTransform(it)
			}
		}

		if (node is HasFill) {
			gc.fill = node.fill?.toPaint()
		}

		if (node is HasStroke) {
			gc.stroke = node.stroke?.toPaint()
			gc.lineWidth = node.strokeWidth ?: 1.0
		}

		if (node.visible)
			when (node) {
				is CircleNode       -> node.render(gc)
				is RectNode         -> node.render(gc)
				is GroupNode        -> node.render(gc)
				is PathNode     	-> node.render(gc)
				is TextNode         -> node.render(gc)
				is LineNode         -> node.render(gc)
				else            -> error("Unknow type ${node::class}")
			}

		if (node is HasTransform) {
			node.transform?.also {
				gc.removeTransform(it)
			}
		}

	}

}
