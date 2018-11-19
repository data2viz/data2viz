package io.data2viz.viz

import kotlin.math.*

fun GroupNode.render(renderer: AndroidCanvasRenderer) {

	val canvas = renderer.canvas

	children.forEach { node ->

		if (node is HasTransform) {
			node.transform?.also {
				canvas.translate(it.translate?.x?.toFloat() ?: .0f, it.translate?.y?.toFloat() ?: .0f)
				canvas.rotate((+ (it.rotate?.delta ?: .0) * 180 / PI).toFloat())
			}
		}

		if (node is HasStroke) {
			paint.strokeWidth = (node.style.strokeWidth ?: 1.0).toFloat()
		}

		if (node.visible)
			when (node) {
				is CircleNode   -> node.render(renderer)
				is RectNode     -> node.render(renderer)
				is GroupNode    -> node.render(renderer)
				is PathNode     -> node.render(renderer)
				is TextNode     -> node.render(renderer)
				is LineNode     -> node.render(renderer)
				else -> error("Unknow type ${node::class}")
			}

		if (node is HasTransform) {
			node.transform?.also {
				canvas.translate(-(it.translate?.x?.toFloat() ?: .0f), -(it.translate?.y?.toFloat() ?: .0f))
				canvas.rotate((- (it.rotate?.delta ?: .0) * 180 / PI).toFloat())

			}
		}

	}
}
