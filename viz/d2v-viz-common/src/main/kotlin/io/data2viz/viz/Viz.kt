package io.data2viz.viz

import io.data2viz.color.Color
import io.data2viz.color.ColorOrGradient
import io.data2viz.core.CssClass
import io.data2viz.path.PathAdapter
import io.data2viz.path.SvgPath

/**
 * Common interface to bootstrap visualization into different platform contexts.
 */
interface VizContext : Group

interface VizElement


interface Group : Transformable, StyledElement, VizElement {
    fun add(vizElement: VizElement)
    
    fun group(init: Group.() -> Unit): Group
    fun circle(init: Circle.() -> Unit): Circle
    fun rect(init: Rect.() -> Unit): Rect
    fun line(init: Line.() -> Unit): Line
    fun text(init: Text.() -> Unit): Text
    fun path(init: PathVizElement.() -> Unit): PathVizElement
    fun addPath(path: PathAdapter)
    fun setStyle(style:String)
}

/**
 * Indicate an element on which we can apply a Transformation.
 * todo implement other transformation (rotate, ...)
 */
interface Transformable {
    fun transform(init: Transform.() -> Unit)
}

interface Transform {
    fun translate(x: Double = 0.0, y: Double = 0.0)
}

interface StyledElement {
    fun addClass(cssClass: CssClass)
}

interface PathVizElement : VizElement, Shape, PathAdapter

interface Circle : VizElement, Shape, Transformable, StyledElement {
    var cx: Double
    var cy: Double
    var radius: Double
}

interface Line : VizElement, Shape, Transformable, StyledElement {
    var x1: Double
    var y1: Double
    var x2: Double
    var y2: Double
}

interface Rect : VizElement, Shape, Transformable, StyledElement {
    var x: Double
    var y: Double
    var width: Double
    var height: Double
    var rx: Double
    var ry: Double
}

interface Text : VizElement, Transformable, HasFill, StyledElement {
    var x: Double
    var y: Double
    var textContent: String
    var anchor: TextAnchor
    var baseline: TextAlignmentBaseline
}

/**
 * The text-anchor attribute is used to horizontally align ([START], [MIDDLE] or [END]-alignment) a string of 
 * text relative to a given point.
 * See [CSS text-anchor][https://developer.mozilla.org/en-US/docs/Web/SVG/Attribute/text-anchor]
 */
enum class TextAnchor {
    START, 
    MIDDLE, 
    END}


/**
 * Vertical alignment of a text 
 */
enum class TextAlignmentBaseline {
    HANGING,
    MIDDLE,
    BASELINE
}

interface Shape : HasFill, HasStroke


/**
 * All properties of stroke
 * Todo add remaining common properties
 */
interface HasStroke {
    var stroke: Color?
    var strokeWidth: Double?
}

interface HasFill {
    var fill: ColorOrGradient?
}

interface VizFactory<V : VizElement> {
    fun createVizItem(): V
}

data class Margins(val top: Double, val right: Double = top, val bottom: Double = top, val left: Double = right) {
    val hMargins = right + left
    val vMargins = top + bottom
}

expect fun newGroup(): Group
expect fun newLine(): Line
expect fun newRect(): Rect
expect fun newCircle(): Circle
expect fun newText(): Text

