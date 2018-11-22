package io.data2viz.viz

import org.w3c.dom.*

fun GroupNode.render(context: CanvasRenderingContext2D) {


	children.forEach { node ->

		if (node is HasTransform) {
			node.transform?.also {
				context.translate(it.translate?.x ?:.0, it.translate?.y ?:.0)
				context.rotate(it.rotate?.delta ?: .0)
			}
		}

		if (node is HasFill) {
			context.fillStyle = node.fill?.toCanvasPaint(context)
		}

		if (node is HasStroke) {
			context.strokeStyle = node.stroke?.toCanvasPaint(context)
			context.lineWidth = node.strokeWidth ?: 1.0
		}

		if (node.visible)
			when (node) {
				is CircleNode       -> node.render(context)
				is RectNode         -> node.render(context)
				is GroupNode        -> node.render(context)
				is PathNode         -> node.render(context)
				is TextNode         -> node.render(context)
				is LineNode         -> node.render(context)
				else                -> error("Unknow type ${node::class}")
			}

		if (node is HasTransform) {
			node.transform?.also {
				context.translate(-(it.translate?.x ?:.0), -(it.translate?.y ?:.0))
				context.rotate(-(it.rotate?.delta ?:.0))
			}
		}

	}

	context.translate(.0, .0)
}
