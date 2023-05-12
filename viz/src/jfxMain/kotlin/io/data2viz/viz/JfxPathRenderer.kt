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

import io.data2viz.geom.*
import io.data2viz.math.Angle
import io.data2viz.math.toDegrees
import javafx.scene.canvas.GraphicsContext
import javafx.scene.shape.StrokeLineCap


public fun PathNode.render(gc: GraphicsContext) {
    gc.beginPath()
    path.commands.forEach { cmd ->
        when (cmd) {
            is MoveTo -> gc.moveTo(cmd.x, cmd.y)
            is LineTo -> gc.lineTo(cmd.x, cmd.y)
            is QuadraticCurveTo -> gc.quadraticCurveTo(cmd.cpx, cmd.cpy, cmd.x, cmd.y)
            is BezierCurveTo -> gc.bezierCurveTo(cmd.cpx1, cmd.cpy1, cmd.cpx2, cmd.cpy2, cmd.x, cmd.y)
            is Arc -> gc.arc(cmd.centerX, cmd.centerY, cmd.radius, cmd.radius, cmd.start, cmd.length)
            is ArcTo -> gc.arcTo(cmd.fromX, cmd.fromY, cmd.x, cmd.y, cmd.radius)
            ClosePath -> gc.closePath()
            is RectCmd -> gc.rect(cmd.x, cmd.y, cmd.w, cmd.h)
        }
    }

    fill?.let {
        gc.fill()
    }

    strokeColor?.let {
        gc.lineCap = StrokeLineCap.BUTT
        gc.stroke()
    }

}


public val Arc.start: Double
    get() = -startAngle.toDegrees()

public val Arc.length: Double
    get() {
        var length = Angle(endAngle - startAngle)

        if (counterClockWise && length.rad > io.data2viz.math.EPSILON)
            length = Angle(length.rad % io.data2viz.math.TAU - io.data2viz.math.TAU)

        if (!counterClockWise && length.rad < -io.data2viz.math.EPSILON)
            length = Angle(length.rad % io.data2viz.math.TAU + io.data2viz.math.TAU)

        return -length.deg
    }
