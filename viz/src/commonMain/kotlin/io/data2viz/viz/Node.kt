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

import io.data2viz.ExperimentalD2V
import io.data2viz.color.ColorOrGradient


/**
 * The base class of each element of the viz hierarchy.
 * Keeps a reference to the parent node.
 */
public abstract class Node : Style {

    public var parent: HasChildren? = null
        get() = field
        set(value) {
            field = value
            (parent as? Style).let { style.parent = it  }
        }

    /**
     * This property allows to put classes on node to allows some selection like
     * in the DOM through classes selectors.
     *
     * It's use is experimental you can use it but without the garanty that it will
     * be there in future versions of the library.
     */
    @ExperimentalD2V
    public var classes: String? = null

    /**
     * Specifies whether the item is visible. When set to false, the item won’t be drawn.
     *
     * default to true.
     */
    public var visible: Boolean = true

    /**
     * Removes the node from the hierarchy.
     */
    public fun remove() {
        parent?.remove(this)
    }

    private val style: HierarchicalStyle = HierarchicalStyle(parent as? Style)

    override var fill: ColorOrGradient?
        get() = style.fill
        set(value) { style.fill = value }

    @Deprecated("Use strokeColor instead.", ReplaceWith("strokeColor"))
    override var stroke: ColorOrGradient?
        get() = style.strokeColor
        set(value) { style.strokeColor = value }

    override var strokeColor: ColorOrGradient?
        get() = style.strokeColor
        set(value) { style.strokeColor = value }

    override var dashedLine: DoubleArray?
        get() = style.dashedLine
        set(value) { style.dashedLine = value }

    override var strokeWidth: Double?
        get() = style.strokeWidth
        set(value) { style.strokeWidth = value }

    override var textColor: ColorOrGradient?
        get() = style.textColor
        set(value) { style.textColor = value }

    override var hAlign: TextHAlign
        get() = style.hAlign
        set(value) { style.hAlign = value }

    @Deprecated("Use hAlign", ReplaceWith("hAlign"))
    override var anchor: TextHAlign
        get() = hAlign
        set(value) { hAlign = value }

    override var vAlign: TextVAlign
        get() = style.vAlign
        set(value) { style.vAlign = value }

    @Deprecated("Use vAlign", ReplaceWith("vAlign"))
    override var baseline: TextVAlign
        get() = vAlign
        set(value) { vAlign = value }

}
