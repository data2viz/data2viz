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

import io.data2viz.geom.*
import org.w3c.dom.CanvasRenderingContext2D

fun PathNode.render(context: CanvasRenderingContext2D) {
    context.beginPath()
    path.commands.forEach { cmd ->
        when (cmd) {
            is MoveTo -> context.moveTo(cmd.x, cmd.y)
            is LineTo -> context.lineTo(cmd.x, cmd.y)
            is QuadraticCurveTo -> context.quadraticCurveTo(cmd.cpx, cmd.cpy, cmd.x, cmd.y)
            is BezierCurveTo -> context.bezierCurveTo(cmd.cpx1, cmd.cpy1, cmd.cpx2, cmd.cpy2, cmd.x, cmd.y)
            is Arc -> context.arc(cmd.centerX, cmd.centerY, cmd.radius, cmd.startAngle, cmd.endAngle, cmd.counterClockWise)
            is ArcTo -> context.arcTo(cmd.fromX, cmd.fromY, cmd.x, cmd.y, cmd.radius)
            is ClosePath -> context.closePath()
            is RectCmd -> context.rect(cmd.x, cmd.y, cmd.w, cmd.h)
            else -> error("Unknown canvas command: ${cmd::class}")
        }
    }

    // FIRST fill
    fill?.let {
        context.fillStyle = it.toCanvasPaint(context)
        context.fill()
    }

    // THEN stroke
    stroke?.let {
        context.strokeStyle = it.toCanvasPaint(context)
        context.lineWidth = strokeWidth ?: 1.0
        context.stroke()
    }


}
