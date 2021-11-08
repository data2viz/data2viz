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
import kotlinx.cinterop.*
import platform.CoreGraphics.*
import platform.Foundation.NSString
import platform.UIKit.*


public fun TextNode.render(renderer: IOSCanvasRenderer) {
    with(renderer) {

        val textFontAttributes = mapOf(
            NSFontAttributeName to UIFont.fontWithName("Helvetica", this@render.fontSize),
            NSForegroundColorAttributeName to (this@render.textColor as Color).toUIColor()
        ) as Map<Any?, *>?


        val nsStringText:NSString = this@render.textContent as NSString

        val (textWidth, textHeight) = nsStringText
            .sizeWithAttributes(textFontAttributes)
            .useContents { Pair(this.width, this.height) }

        val textX = when (hAlign) {
            TextHAlign.RIGHT, TextHAlign.END -> x - textWidth
            TextHAlign.MIDDLE -> x - textWidth/2
            TextHAlign.LEFT, TextHAlign.START -> x
        }

        val textY = when (vAlign) {
            TextVAlign.HANGING -> y
            TextVAlign.MIDDLE -> y - textHeight / 2
            TextVAlign.BASELINE -> y - textHeight
        }

        val textRect = CGRectMake(textX, textY, textWidth, textHeight)

        nsStringText.drawInRect(textRect, withAttributes = textFontAttributes)

    }
}


internal actual fun textMeasure(
    text: String,
    fontSize: Double,
    fontFamily: FontFamily,
    fontWeight: FontWeight,
    fontStyle: FontPosture
): Rect {

    val textFontAttributes = mapOf(
        NSFontAttributeName to UIFont.fontWithName("Helvetica", fontSize),
    ) as Map<Any?, *>?


    val nsStringText:NSString = text as NSString

    val (textWidth, textHeight) = nsStringText
        .sizeWithAttributes(textFontAttributes)
        .useContents { Pair(this.width, this.height) }


    return RectGeom(
        width = textWidth,
        height = textHeight
    )
}
