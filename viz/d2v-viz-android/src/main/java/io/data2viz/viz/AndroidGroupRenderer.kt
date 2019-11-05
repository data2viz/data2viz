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

import kotlin.math.*

fun GroupNode.render(renderer: AndroidCanvasRenderer) {

	val canvas = renderer.canvas

	with(renderer) {
		children.forEach { node ->

			if (node is HasTransform) {
				node.transform?.also {
					canvas.translate(it.translate?.x?.dp ?: .0f, it.translate?.y?.dp ?: .0f)
					canvas.rotate((+ (it.rotate?.delta ?: .0) * 180 / PI).toFloat())
				}
			}

			if (node is HasStroke) {
				paint.strokeWidth = (node.strokeWidth ?: 1.0).toFloat()
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
					canvas.translate(-(it.translate?.x?.dp ?: .0f), -(it.translate?.y?.dp ?: .0f))
					canvas.rotate((- (it.rotate?.delta ?: .0) * 180 / PI).toFloat())

				}
			}

		}
	}
}
