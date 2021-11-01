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


public fun PathNode.render(renderer: IOSCanvasRenderer) {
    with(renderer) {


        CGContextBeginPath(context);

        this@render.path.commands.forEachIndexed { index, cmd ->
            when (cmd) {
                is MoveTo -> CGContextMoveToPoint(context, cmd.x, cmd.y)
                is LineTo -> CGContextAddLineToPoint(context, cmd.x, cmd.y)
                is QuadraticCurveTo -> CGContextAddQuadCurveToPoint(context, cmd.cpx, cmd.cpy, cmd.x, cmd.y)
                is BezierCurveTo -> CGContextAddCurveToPoint(context, cmd.cpx1, cmd.cpy1, cmd.cpx2, cmd.cpy2, cmd.x, cmd.y)
                is ArcTo -> CGContextAddArcToPoint(context, cmd.fromX, cmd.fromY, cmd.x, cmd.y, cmd.radius)
                is Arc -> CGContextAddArc(context, cmd.centerX, cmd.centerY, cmd.radius, cmd.startAngle, cmd.endAngle, if(cmd.counterClockWise) 1 else 0 )
                is Rect -> CGContextAddRect(context, CGRectMake(cmd.x, cmd.y, cmd.width, cmd.height))
                is ClosePath -> CGContextClosePath(context)
                else -> error("Unknown path command:: ${cmd::class}")
            }
        }

        if (fill != null && strokeColor != null && strokeWidth != null) {
            CGContextSetFillColor(context, (fill as Color).toColor()) //todo manage gradient
            CGContextSetStrokeColor(context, (strokeColor as Color).toColor())
            CGContextSetLineWidth(context, strokeWidth!!)
            CGContextDrawPath(context, CGPathDrawingMode.kCGPathFillStroke)
        } else if (fill != null) {
            CGContextSetFillColor(context, (fill as Color).toColor()) //todo manage gradient
            CGContextFillPath(context)
        } else if (strokeColor != null && strokeWidth != null) {
            CGContextSetStrokeColor(context, (strokeColor as Color).toColor())
            CGContextSetLineWidth(context, strokeWidth!!)
            CGContextStrokePath(context)
        }

    }
}
