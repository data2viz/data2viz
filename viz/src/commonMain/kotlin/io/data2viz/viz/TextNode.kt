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

import io.data2viz.color.*

public class TextNode : Node(),
        HasFill,
        HasStroke,
        HasTransform {

    override var transform: Transform? = null

    public var x: Double = .0
    public var y: Double = .0
    public var textContent: String = "Type something"
    set(value) {
        field = value.makeVizFriendlyText()
    }

    public var fontSize: Double = 12.0
    public var fontFamily: FontFamily          = FontFamily.SANS_SERIF
    public var fontWeight: FontWeight          = FontWeight.NORMAL
    public var fontStyle: FontPosture          = FontPosture.NORMAL

}

private fun String.makeVizFriendlyText(): String = replaceNewLineWithSpace()

private fun String.replaceNewLineWithSpace(): String  = replace('\n', ' ')


public class FontFamily private constructor(
    public val name: String) {

    public companion object {
        public val MONOSPACE    : FontFamily = FontFamily("monospace")
        public val SANS_SERIF   : FontFamily = FontFamily("sans-serif")
        public val SERIF        : FontFamily = FontFamily("serif")

        public fun specifiedFont(name: String): FontFamily = FontFamily(name)
    }

    override fun toString(): String {
        return "FontFamily(name='$name')"
    }


}

public enum class FontWeight {
    BOLD,
    NORMAL,
}

public enum class FontPosture {
    ITALIC,
    NORMAL,
}