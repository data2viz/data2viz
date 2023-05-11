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

import io.data2viz.color.Color
import io.data2viz.geom.*
import platform.CoreGraphics.*

internal fun LineNode.render(renderer: IOSCanvasRenderer): Unit = with(renderer) {
    CGContextMoveToPoint(
        context,
        this@render.x1,
        this@render.y1
    )
    CGContextAddLineToPoint(
        context,
        this@render.x2,
        this@render.y2
    )

		if ((strokeColor != null) && (strokeWidth != null)) {
			when (strokeColor) {
				is Color -> {

					CGContextSetStrokeColor(context, (strokeColor as Color).toColor())
					CGContextSetLineWidth(context, strokeWidth!!)
					CGContextStrokePath(context)
				}
				else -> error("Only true color is accepted for strokeColor attribute (not gradient)")
			}
		}
	}
}
