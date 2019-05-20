package io.data2viz.viz

import io.data2viz.color.*

class TextNode : Node(),
        HasFill,
        HasStroke,
        HasTransform {

    override var transform: Transform? = null

    var x: Double = .0
    var y: Double = .0
    var textContent: String = "Type something"
    set(value) {
        field = value.makeVizFriendlyText()
    }

    var fontSize: Double = 12.0
    var fontFamily: FontFamily          = FontFamily.SANS_SERIF
    var fontWeight: FontWeight          = FontWeight.NORMAL
    var fontStyle: FontPosture          = FontPosture.NORMAL

}

private fun String.makeVizFriendlyText(): String = replaceNewLineWithSpace()

private fun String.replaceNewLineWithSpace(): String  = replace('\n', ' ')


class FontFamily private constructor(val name: String) {

    companion object {
        val MONOSPACE  = FontFamily("monospace")
        val SANS_SERIF = FontFamily("sans-serif")
        val SERIF 	   = FontFamily("serif")

        fun specifiedFont(name: String) = FontFamily(name)
    }

}

enum class FontWeight {
    BOLD,
    NORMAL,
}

enum class FontPosture {
    ITALIC,
    NORMAL,
}