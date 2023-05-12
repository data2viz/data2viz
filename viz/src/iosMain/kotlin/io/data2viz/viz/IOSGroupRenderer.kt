/*
 * Copyright (c) 2018-2021. data2viz sàrl.
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

import platform.CoreGraphics.CGContextRotateCTM
import platform.CoreGraphics.CGContextSetLineWidth
import platform.CoreGraphics.CGContextTranslateCTM

internal fun GroupNode.render(renderer: IOSCanvasRenderer) {
    with(renderer) {
        children.forEach { node ->
            if (node is HasTransform) {
                node.transform?.transformations?.forEach {
                    when (it) {
                        is Translation  -> CGContextTranslateCTM(context, it.x, it.y)
                        is Rotation     -> CGContextRotateCTM(context, it.delta)
                    }
                }
            }
//
//            var dashedSet = false
//
            if (node is HasStroke) {
                CGContextSetLineWidth(renderer.context, node.strokeWidth ?: 1.0)
                //todo dashed line
//                node.dashedLine?.let {
//                    paint.pathEffect = DashPathEffect(it.toFloat(), 0f)
//                    dashedSet = true
//                }
            }

            if (node.visible)
                when (node) {
                    is CircleNode   -> node.render(renderer)
                    is RectNode     -> node.render(renderer)
                    is GroupNode    -> node.render(renderer)
                    is PathNode     -> node.render(renderer)
                    is TextNode     -> node.render(renderer)
                    is LineNode     -> node.render(renderer)
//                    is ImageNode    -> node.render(renderer)
                    else -> error("Unknow type ${node::class}")
                }

//            if (dashedSet) {
//                paint.pathEffect = null
//            }
//

            if (node is HasTransform) {
                node.transform?.transformations?.reversed()?.forEach {
                    when (it) {
                        is Translation  -> CGContextTranslateCTM(context, -it.x, -it.y)
                        is Rotation     -> CGContextRotateCTM(context, -it.delta)
                    }
                }
            }
        }
    }
}
