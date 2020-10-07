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

import javafx.scene.canvas.GraphicsContext


private val emptySegments = doubleArrayOf()


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

        var dashedSet = false

        if (node is HasStroke) {
            gc.stroke = node.stroke?.toPaint()
            gc.lineWidth = node.strokeWidth ?: 1.0
            node.dashedLine?.let {
                gc.setLineDashes(*it)
                dashedSet = true
            }
        }

        if (node.visible)
            when (node) {
                is CircleNode   -> node.render(gc)
                is RectNode     -> node.render(gc)
                is GroupNode    -> node.render(gc)
                is PathNode     -> node.render(gc)
                is TextNode     -> node.render(gc)
                is LineNode     -> node.render(gc)
                is ImageNode    -> node.render(gc)
                else -> error("Unknow type ${node::class}")
            }

        if (dashedSet) {
            gc.setLineDashes(*emptySegments)
        }


        if (node is HasTransform) {
            node.transform?.also {
                gc.removeTransform(it)
            }
        }

    }

}
