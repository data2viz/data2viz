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
import kotlinx.cinterop.*
import platform.CoreGraphics.*
import platform.Foundation.NSString
import platform.UIKit.*


public fun TextNode.render(renderer: IOSCanvasRenderer) {
    with(renderer) {
        val textRect = CGRectMake(this@render.x, this@render.x, 20.0, 20.0)
        val textStyle = NSMutableParagraphStyle.defaultParagraphStyle.mutableCopy() as NSMutableParagraphStyle
        textStyle.setAlignment(NSTextAlignmentLeft)
        val textFontAtttributes = mapOf(
            NSFontAttributeName to UIFont.fontWithName("Helvetica", 12.0),
            NSForegroundColorAttributeName to UIColor.blackColor,
            NSParagraphStyleAttributeName to textStyle
        ) as Map<Any?, *>?

        val textContent:NSString = this@render.textContent as NSString
        textContent.drawInRect(textRect, withAttributes = textFontAtttributes)
    }
}
