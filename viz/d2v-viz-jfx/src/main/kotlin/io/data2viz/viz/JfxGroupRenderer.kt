package io.data2viz.viz



fun GroupNode.render(renderer: JFxVizRenderer) {

	children.forEach { node ->

		val gc = renderer.gc

		if (node is HasTransform) {
			node.transform?.also {
				renderer.addTransform(it)
			}
		}

		if (node is HasFill) {
			gc.fill = node.style.fill?.toPaint()
		}

		if (node is HasStroke) {
			gc.stroke = node.style.stroke?.toPaint()
			gc.lineWidth = node.style.strokeWidth ?: 1.0
		}

		if (node.visible)
			when (node) {
				is CircleNode       -> node.render(renderer)
				is RectNode         -> node.render(renderer)
				is GroupNode        -> node.render(renderer)
				is PathNode     -> node.render(renderer)
				is TextNode         -> node.render(renderer)
				is LineNode         -> node.render(renderer)
				else            -> error("Unknow type ${node::class}")
			}

		if (node is HasTransform) {
			node.transform?.also {
				renderer.removeTransform(it)
			}
		}

	}

}
