package io.data2viz.viz

import io.data2viz.color.Color
import io.data2viz.geom.Rect
import io.data2viz.geom.RectGeom
import io.data2viz.geom.Size
import io.data2viz.math.Angle
import io.data2viz.viz.TextHAlign
import io.data2viz.viz.TextNode
import io.data2viz.viz.TextVAlign



/**
 * Returns the bounds of the [TextNode], no transformation is applied.
 *
 *  - the [Rect] position returned is the same as the [TextNode]
 *  - the Rect [Size] is the same as the [TextNode]
 *  - the [Rect] position may vary depending on the [TextNode] alignment properties
 *
 * @param this@measureText a [TextNode] with all properties set.
 * @param hAlign the [TextHAlign] of the text (defaults to TextHAlign.LEFT).
 * @param vAlign the [TextVAlign] of the text (defaults to TextVAlign.BASELINE).
 * @param rotation the rotation [Angle] applied to the text (defaults to 0).
 * @return A [Rect] that is the bounding box of this text on screen.
 * width.
 */
public fun TextNode.measureText(): Rect =
        measureText(
            textContent,
            fontSize,
            fontFamily,
            fontWeight,
            fontStyle,
            x, y, hAlign, vAlign
        )

/**
 * Returns the bounds of a text to be drawn on screen.
 *
 * @param text a [String] to be displayed.
 * @param font a [Font] this text uses.
 * @param xPosition the expected X position of this text as a [Double] (defaults to .0).
 * @param yPosition the expected Y position of this text as a [Double] (defaults to .0).
 * @param vAlign the [TextVAlign] of the text (defaults to TextVAlign.BASELINE).
 * @param rotation the rotation [Angle] applied to the text (defaults to 0).
 * @return A [Rect] that is the bounding box of this text on screen.
 */
public fun measureText(
    text: String,
    fontSize: Double,
    fontFamily: FontFamily,
    fontWeight: FontWeight,
    fontStyle: FontPosture,
    xPosition: Double = .0,
    yPosition: Double = .0,
    hAlign: TextHAlign = TextHAlign.LEFT,
    vAlign: TextVAlign = TextVAlign.BASELINE
): Rect {
    val rect: Rect = textMeasure(text,
        fontSize,
        fontFamily,
        fontWeight,
        fontStyle,
        )

    val x = xPosition + when (hAlign) {
        TextHAlign.RIGHT -> rect.x - rect.width
        TextHAlign.MIDDLE -> rect.x - (rect.width / 2.0)
        else -> rect.x
    }
    val y = yPosition + when (vAlign) {
        TextVAlign.HANGING -> rect.y + rect.height
        TextVAlign.MIDDLE -> rect.y + (rect.height / 2.0)
        else -> rect.y
    }
    return RectGeom(x, y, rect.width, rect.height)
}

internal expect fun textMeasure(text: String, fontSize: Double, fontFamily: FontFamily, fontWeight: FontWeight, fontStyle: FontPosture): Rect


