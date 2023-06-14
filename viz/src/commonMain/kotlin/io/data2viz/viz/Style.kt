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

@file:Suppress("OverridingDeprecatedMember")

package io.data2viz.viz

import io.data2viz.color.ColorOrGradient
import io.data2viz.viz.TextHAlign.*


/**
 * Holds the styling property.
 */
public interface Style {
    public var fill: ColorOrGradient?

    @Deprecated("Use strokeColor instead.", ReplaceWith("strokeColor"))
    public var stroke: ColorOrGradient?

    public var strokeColor: ColorOrGradient?
    public var strokeWidth: Double?
    public var textColor: ColorOrGradient?

    /**
     * The dashedLine can be set to null or to an array of a minimal size of 2.
     */
    public var dashedLine: DoubleArray?

    @Deprecated("Use hAlign", ReplaceWith("hAlign"))
    public var anchor: TextHAlign
    public var hAlign: TextHAlign

    @Deprecated("Use vAlign", ReplaceWith("vAlign"))
    public var baseline: TextVAlign
    public var vAlign: TextVAlign
}

internal class StyleImpl: Style {
    override var fill: ColorOrGradient? = null

    override var strokeColor: ColorOrGradient? = null

    @Deprecated("Use strokeColor instead.", ReplaceWith("strokeColor"))
    override var stroke: ColorOrGradient? = strokeColor

    override var dashedLine: DoubleArray? = null
        get() = field
        set(value) {
            require( value == null || (value.size > 1) )
                { "The dashedLine can be set to null or to an array of a minimal size of 2." }
            field = value
        }

    override var textColor: ColorOrGradient? = null
    override var strokeWidth: Double? = 1.0
    override var hAlign: TextHAlign = TextHAlign.LEFT
    @Deprecated("Use hAlign", replaceWith = ReplaceWith("hAlign"))
    override var anchor: TextHAlign = hAlign
    override var vAlign: TextVAlign = TextVAlign.BASELINE
    @Deprecated("Use vAlign", replaceWith = ReplaceWith("vAlign"))
    override var baseline: TextVAlign = vAlign
}

public class HierarchicalStyle(
    public var parent: Style?
): Style {
    private val style: Style = StyleImpl()

    private var fillSet = false
    override var fill: ColorOrGradient?
        get() = if (fillSet) style.fill else parent?.fill
        set(value) {
            fillSet = true
            style.fill = value
        }

    private var dashedLineSet = false
    override var dashedLine: DoubleArray?
        get() =
            if (dashedLineSet)
                style.dashedLine
            else
                parent?.dashedLine

        set(value) {
            dashedLineSet = true
            style.dashedLine = value
        }

    private var strokeSet = false

    @Deprecated("Use strokeColor instead.", ReplaceWith("strokeColor"))
    override var stroke: ColorOrGradient?
        get() = if (strokeSet) style.strokeColor else parent?.strokeColor
        set(value) {
            strokeSet = true
            style.strokeColor = value
        }

    override var strokeColor: ColorOrGradient?
        get() = if (strokeSet) style.strokeColor else parent?.strokeColor
        set(value) {
            strokeSet = true
            style.strokeColor = value
        }

    private var strokeWidthSet = false
    override var strokeWidth: Double?
        get() = if (strokeWidthSet) style.strokeWidth else parent?.strokeWidth
        set(value) {
            strokeWidthSet = true
            style.strokeWidth = value
        }

    private var textColorSet = false
    override var textColor: ColorOrGradient?
        get() = if (textColorSet) style.textColor else parent?.textColor
        set(value) {
            textColorSet = true
            style.textColor = value
        }


    private var hAlignSet = false
    override var hAlign: TextHAlign
        get() = if (hAlignSet) style.hAlign else parent?.hAlign!!
        set(value) {
            hAlignSet = true
            style.hAlign = value
        }
    @Deprecated("Use hAlign", replaceWith = ReplaceWith("hAlign"))
    override var anchor: TextHAlign
        get() = hAlign
        set(value) {
            hAlign = value
        }

    private var vAlignSet = false
    override var vAlign: TextVAlign
        get() = if (vAlignSet) style.vAlign else parent?.vAlign!!
        set(value) {
            vAlignSet = true
            style.vAlign = value
        }
    @Deprecated("Use vAlign", ReplaceWith("vAlign"))
    override var baseline: TextVAlign
        get() = vAlign
        set(value) {
            vAlign = value
        }

}

/**
 * The text-anchor attribute is used to horizontally align ([LEFT], [MIDDLE] or [RIGHT]-alignment) a string of
 * text relative to a given point.
 * See [CSS text-anchor][https://developer.mozilla.org/en-US/docs/Web/SVG/Attribute/text-anchor]
 */
public enum class TextHAlign {
    LEFT,
    @Deprecated("Use LEFT", ReplaceWith("LEFT"))
    START,
    MIDDLE,
    RIGHT,
    @Deprecated("Use RIGHT", ReplaceWith("RIGHT"))
    END
}

@Deprecated("Use TextHAlign", ReplaceWith("TextHAlign"))
public typealias TextAnchor = TextHAlign

/**
 * Vertical alignment of a text
 */
public enum class TextVAlign {
    HANGING,
    MIDDLE,
    BASELINE
}

@Deprecated("Use TextVAlign", ReplaceWith("TextVAlign"))
public typealias TextAlignmentBaseline = TextVAlign

