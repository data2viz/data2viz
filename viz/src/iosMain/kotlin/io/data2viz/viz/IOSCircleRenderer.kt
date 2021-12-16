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
import io.data2viz.color.LinearGradient
import io.data2viz.color.RadialGradient
import io.data2viz.viz.CircleNode
import platform.CoreGraphics.*
import platform.UIKit.UIBezierPath


public fun CircleNode.render(renderer: IOSCanvasRenderer) {
    with(renderer) {

        val rect = CGRectMake(x - radius, y - radius, 2 * radius, 2 * radius)

		when(val fillColor = fill) {
			is Color -> {
				CGContextSetFillColor(context, (fillColor as Color).toColor()) //todo manage gradient
				CGContextFillEllipseInRect(context, rect)
			}
			is LinearGradient -> {
				val path = UIBezierPath.bezierPathWithOvalInRect(rect)
				drawLinearGradient(path, fillColor)
			}
			is RadialGradient -> {
				val path = UIBezierPath.bezierPathWithOvalInRect(rect)
				drawRadialGradient(path, fillColor)
			}
			null -> {}
			else -> error("Only color and gradient are accepted as fillColor")

		}
		if (strokeColor != null && strokeWidth != null) {
			when (strokeColor) {
				is Color -> {
					CGContextSetStrokeColor(context, (strokeColor as Color).toColor())
					CGContextSetLineWidth(context, strokeWidth!!)
					CGContextStrokeEllipseInRect(context, rect)				}
				else -> error("Only true color is accepted for strokeColor attribute (not gradient)")

			}

        }

    }
}
