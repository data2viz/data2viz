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

import android.graphics.*

internal fun TextNode.render(renderer: AndroidCanvasRenderer) {
    val canvas = renderer.canvas
    with(renderer) {
        paint.textAlign = hAlign.android
        paint.textSize = fontSize.dp
        paint.typeface = Typeface.create(fontFamily.name, getAndroidStyle(fontWeight, fontStyle))

        val dy = vAlign.dy(renderer, paint.fontMetrics)

        textColor?.let {
            paint.style = Paint.Style.FILL
            it.updatePaint(paint, renderer)
            canvas.drawText(
                textContent,
                x.dp,
                y.dp - dy,
                paint
            )
        }
        strokeColor?.let {
            paint.style = Paint.Style.STROKE
            it.updatePaint(paint, renderer)
            canvas.drawText(
                textContent,
                x.dp,
                y.dp - dy,
                paint
            )
        }
    }
}


/**
 * The y distance to move the text from baseline in order to respect the wanted
 * alignment.
 *
 * The middle alignement is an approximation.
 * TODO resolve by implementing DV-105
 */
internal fun TextVAlign.dy(renderer: AndroidCanvasRenderer, fontMetrics: Paint.FontMetrics): Float =
    with(renderer) {
        when (this@dy) {
            TextVAlign.BASELINE -> 0F
            TextVAlign.HANGING -> fontMetrics.top
            TextVAlign.MIDDLE -> fontMetrics.ascent * .4f
        }
    }

internal val TextHAlign.android: Paint.Align
    get() = when (this) {
        TextHAlign.START, TextHAlign.LEFT -> Paint.Align.LEFT
        TextHAlign.END, TextHAlign.RIGHT -> Paint.Align.RIGHT
        TextHAlign.MIDDLE -> Paint.Align.CENTER
    }


//TODO nba refactor code: make access to the android text Font more reachable
internal fun getAndroidStyle(fontWeight: FontWeight, fontStyle: FontPosture): Int {
    return when (fontWeight) {
        FontWeight.NORMAL ->
            when (fontStyle) {
                FontPosture.NORMAL -> Typeface.NORMAL
                FontPosture.ITALIC -> Typeface.ITALIC
            }

        FontWeight.BOLD ->
            when (fontStyle) {
                FontPosture.NORMAL -> Typeface.BOLD
                FontPosture.ITALIC -> Typeface.BOLD_ITALIC
            }
    }
}
