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

import android.graphics.*

public fun LineNode.render(renderer: AndroidCanvasRenderer) {
    strokeColor?.let {
        paint.style = Paint.Style.STROKE
        setStrokeWidth(strokeWidth)
        it.updatePaint(paint, renderer)
        with(renderer) {
            canvas.drawLine(x1.dp, y1.dp, x2.dp, y2.dp, paint)
        }
    }
}

internal fun setStrokeWidth(width: Double?) {
    paint.strokeWidth = width?.dp?.toFloat() ?: 1f
}
