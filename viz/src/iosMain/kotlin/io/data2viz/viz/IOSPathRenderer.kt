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
import io.data2viz.geom.Arc
import io.data2viz.geom.ArcTo
import io.data2viz.geom.BezierCurveTo
import io.data2viz.geom.ClosePath
import io.data2viz.geom.LineTo
import io.data2viz.geom.MoveTo
import io.data2viz.geom.QuadraticCurveTo
import io.data2viz.geom.RectCmd
import kotlinx.cinterop.CPointer
import platform.CoreGraphics.CGContextAddPath
import platform.CoreGraphics.CGContextFillPath
import platform.CoreGraphics.CGContextSetFillColor
import platform.CoreGraphics.CGContextSetLineWidth
import platform.CoreGraphics.CGContextSetStrokeColor
import platform.CoreGraphics.CGContextStrokePath
import platform.CoreGraphics.CGPath
import platform.CoreGraphics.CGPathAddArc
import platform.CoreGraphics.CGPathAddArcToPoint
import platform.CoreGraphics.CGPathAddCurveToPoint
import platform.CoreGraphics.CGPathAddLineToPoint
import platform.CoreGraphics.CGPathAddQuadCurveToPoint
import platform.CoreGraphics.CGPathAddRect
import platform.CoreGraphics.CGPathCloseSubpath
import platform.CoreGraphics.CGPathCreateMutable
import platform.CoreGraphics.CGPathMoveToPoint
import platform.CoreGraphics.CGRectMake
import platform.UIKit.UIBezierPath


public fun PathNode.render(renderer: IOSCanvasRenderer) {
    with(renderer) {


		val path: CPointer<CGPath>? = CGPathCreateMutable()

		this@render.path.commands.forEachIndexed { index, cmd ->
			when (cmd) {
				is MoveTo -> CGPathMoveToPoint(path, null, cmd.x, cmd.y)
				is LineTo -> CGPathAddLineToPoint(path, null, cmd.x, cmd.y)
				is QuadraticCurveTo -> CGPathAddQuadCurveToPoint(
					path,
					null,
					cmd.cpx,
					cmd.cpy,
					cmd.x,
					cmd.y
				)
				is BezierCurveTo -> CGPathAddCurveToPoint(
					path,
					null,
					cmd.cpx1,
					cmd.cpy1,
					cmd.cpx2,
					cmd.cpy2,
					cmd.x,
					cmd.y
				)
				is ArcTo -> CGPathAddArcToPoint(
					path,
					null,
					cmd.fromX,
					cmd.fromY,
					cmd.x,
					cmd.y,
					cmd.radius
				)
				is Arc -> CGPathAddArc(
					path,
					null,
					cmd.centerX,
					cmd.centerY,
					cmd.radius,
					cmd.startAngle,
					cmd.endAngle,
					cmd.counterClockWise.not()
				)
				is RectCmd -> CGPathAddRect(
					path,
					null,
					CGRectMake(cmd.x, cmd.y, cmd.w, cmd.h)
				)
				is ClosePath -> CGPathCloseSubpath(path)
				else -> error("Unknown path command:: ${cmd::class}")
			}
		}
		CGContextAddPath(context, path)

		when (val fillColor = fill) {
			is Color -> {
				CGContextSetFillColor(context, fillColor.toColor())
				CGContextFillPath(context)
			}
			is LinearGradient -> {
				val uiBezierPath = UIBezierPath.bezierPathWithCGPath(path)
				drawLinearGradient(uiBezierPath, fillColor)
			}
			is RadialGradient -> {
				val uiBezierPath = UIBezierPath.bezierPathWithCGPath(path)
				drawRadialGradient(uiBezierPath, fillColor)
			}
		}

		if ((strokeColor != null) && (strokeWidth != null)) {
			when (strokeColor) {
				is Color -> {
					CGContextAddPath(context, path)
					CGContextSetStrokeColor(context, (strokeColor as Color).toColor())
					CGContextSetLineWidth(context, strokeWidth!!)
					CGContextStrokePath(context)
				}
				else -> error("Only true color is accepted for strokeColor attribute (not gradient)")
			}
		}

    }
}
