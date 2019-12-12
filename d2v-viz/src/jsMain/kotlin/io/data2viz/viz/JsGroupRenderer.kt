/*
 * Copyright (c) 2018-2019. data2viz sàrl.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 *
 */

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
