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

import io.data2viz.color.*
import org.w3c.dom.*

public fun ColorOrGradient.toCanvasPaint(context: CanvasRenderingContext2D):Any = when(this) {
    is Color            -> this.rgba
    is LinearGradient   -> this.toCanvasGradient(context)
    is RadialGradient   -> this.toCanvasGradient(context)
    else                -> error("Unknown type :: ${this::class}")
}

public fun LinearGradient.toCanvasGradient(context: CanvasRenderingContext2D): CanvasGradient {
    val gradient = context.createLinearGradient(x1, y1, x2, y2)
    this.colorStops.forEach { cs ->
        gradient.addColorStop(cs.percent.value, cs.color.rgba)
    }
    return gradient
}

public fun RadialGradient.toCanvasGradient(context: CanvasRenderingContext2D): CanvasGradient {
    val gradient = context.createRadialGradient(cx, cy, 0.0, cx, cy, radius)
    this.colorStops.forEach { cs ->
        gradient.addColorStop(cs.percent.value, cs.color.rgba)
    }
    return gradient
}

