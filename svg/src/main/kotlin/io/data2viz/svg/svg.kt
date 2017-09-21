package io.data2viz.svg

import io.data2viz.color.Color
import io.data2viz.color.color
import io.data2viz.color.colors.black
import io.data2viz.core.Point
import io.data2viz.core.namespace
import io.data2viz.math.Angle
import org.w3c.dom.Element
import org.w3c.dom.asList
import org.w3c.dom.events.Event
import kotlin.browser.document


internal fun rect(width: Double = 10.0, height: Double = 10.0) =
        RectElement(createSVGElement("rect")).apply {
            this.width = width
            this.height = width
        }

internal fun circle() = CircleElement(createSVGElement("circle"))
internal fun g() = GroupElement(createSVGElement("g"))
internal fun path() = PathElement(createSVGElement("path"))
internal fun text() = TextElement(createSVGElement("text"))


fun createSVGElement(name: String) = document.createElementNS(namespace.svg, name)


class Path() {
    private val commands = mutableListOf<String>()
    fun curveTo(x: Number = 0, y: Number = 0)        {commands.add("C $x $y")}
    fun curveDeltaTo(dx: Number = 0, dy: Number = 0) {commands.add("c $dx $dy")}
    fun moveTo(x: Number = 0, y: Number = 0)        {commands.add("M $x $y")}
    fun moveDeltaTo(dx: Number = 0, dy: Number = 0) {commands.add("m $dx $dy")}
    fun lineTo(x: Number = 0, y: Number = 0)        {commands.add("L $x $y")}
    fun lineDeltaTo(dx: Number = 0, dy: Number = 0) {commands.add("l $dx $dy")}
    fun horizontalTo(x: Number = 0)                 {commands.add("H $x")}
    fun horizontalDeltaTo(dx: Number = 0)           {commands.add("H $dx")}
    fun verticalLineTo(y: Number = 0)               {commands.add("V $y")}
    fun verticalLineDeltaTo(dy: Number = 0)         {commands.add("v $dy")}
    fun closePath()                                 {commands.add("Z")}

    internal fun toCommand() = commands.joinToString(separator = " ")
}

class Transform() {
    private val commands = mutableMapOf<String, String>()
    fun translate(newPoint: Point) { commands.put("translate", "translate(${newPoint.x}, ${newPoint.y})") }
    fun translate(x: Number = 0, y: Number = 0) { commands.put("translate", "translate($x, $y)") }
    fun scale(x: Number = 1, y: Number = x) { commands.put("scale", "scale($x, $y)") }
    fun skewX(a: Number) { commands.put("skewX", "skewX($a)") }
    fun skewY(a: Number) { commands.put("skewY", "skewX($a)") }
    fun rotate(angle: Angle, x: Number = 0, y: Number = 0) { commands.put("rotate", "rotate(${angle.deg}, $x, $y)") }

    internal fun toCommand(): String = commands.values.joinToString(" ")
}

class Style() {
    private val styles = mutableMapOf<String, String>()

    fun setStyle(property: String, value: String) = styles.put(property, "$property: $value")
    fun fontFamily(name: String) { setStyle("font-family", name) }
    fun fontSize(size: String)   { setStyle("font-size", size) }
    fun toAttribute(): String = styles.values.joinToString("; ")
}

@DslMarker
annotation class SvgTagMarker

interface AccessByAttributes {
    fun setAttribute(name: String, value: String?)
    fun getAttribute(name: String): String?
}

interface ElementWrapper : AccessByAttributes {
    val element: Element

    override fun setAttribute(name: String, value: String?) {
        if (value != null)
            element.setAttribute(name, value)
        else
            element.removeAttribute(name)
    }

    override fun getAttribute(name: String) = element.getAttribute(name)

}

interface HasFill : AccessByAttributes {
    var fill: Color?
        get() = getAttribute("fill")?.color
        set(value) {
            setAttribute("fill", value?.toString() ?: "none")
        }
}

interface HasStroke : AccessByAttributes {
    var stroke: Color?
        get() = getAttribute("stroke")?.color
        set(value) {
            setAttribute("stroke", value?.toString())
        }

    var strokeWidth: String?
        get() = getAttribute("stroke-width")
        set(value) {
            setAttribute("stroke-width", value)
        }

    var strokeLineCap: String?
        get() = getAttribute("stroke-linecap")
        set(value) {
            setAttribute("stroke-linecap", value)
        }

    var strokeDasharray: String?
        get() = getAttribute("stroke-dasharray")
        set(value) {
            setAttribute("stroke-dasharray", value)
        }
}

fun svg(init: SVGElement.() -> Unit = {}): SVGElement {
    var svgElement = document.querySelector("svg")
    if (svgElement == null) {
        svgElement = createSVGElement("svg")
        document.querySelector("body")!!.append(svgElement)
    }
    return SVGElement(svgElement).apply(init)
}

@SvgTagMarker
class CircleElement(override val element: Element) : HasStroke, HasFill, HasCenter, HasRadius, ParentElement, Transformable {
    fun on(eventName: String, block: CircleElement.(Event) -> Unit) {
        element.addEventListener(type = eventName, callback = { event -> block(this, event) })
    }
}

@SvgTagMarker
class RectElement(override val element: Element) : HasStroke, HasFill, HasRoundedCorner, Has2D, ParentElement, Transformable {
    fun on(eventName: String, block: RectElement.(Event) -> Unit) {
        element.addEventListener(type = eventName, callback = { event -> block(this, event) })
    }
}


@SvgTagMarker
class GroupElement(override val element: Element) : HasStroke, HasFill, Has2D, HasPosition, ParentElement, Transformable {
    fun on(eventName: String, block: GroupElement.(Event) -> Unit) {
        element.addEventListener(type = eventName, callback = { event -> block(this, event) })
    }
}

@SvgTagMarker
class LineElement(override val element: Element) : ElementWrapper, HasStroke {
    var x1:Number?
        get() = element.getAttribute("x1")?.toDouble()
        set(value) = element.setAttribute("x1", "$value")
    var x2:Number?
        get() = element.getAttribute("x2")?.toDouble()
        set(value) = element.setAttribute("x2", "$value")
    var y1:Number?
        get() = element.getAttribute("y1")?.toDouble()
        set(value) = element.setAttribute("y1", "$value")
    var y2:Number?
        get() = element.getAttribute("y2")?.toDouble()
        set(value) = element.setAttribute("y2", "$value")
}

@SvgTagMarker
class PathElement(override val element: Element) : ElementWrapper, HasStroke, HasFill {

    fun path(init: Path.() -> Unit) {
        val path = Path()
        init(path)
        setAttribute("d", path.toCommand())
    }
}

enum class TextAnchor {
    start, middle, end, inherit
}

@SvgTagMarker
class TextElement(override val element: Element) : HasText, HasPosition, HasOffset, ElementWrapper, HasFill, Transformable, HasStyle {
    override var text: String?
        get() = element.textContent
        set(value) {
            element.textContent = value
        }

    var textAnchor: TextAnchor
        get() = getAttribute("text-anchor")?.let { TextAnchor.valueOf(it) } ?: TextAnchor.inherit
        set(value) {
            setAttribute("text-anchor", value.name)
        }

}


/**
 * The root of a SVG visualization
 */
@SvgTagMarker
class SVGElement(override var element: Element) : Has2D, ParentElement

interface ParentElement : ElementWrapper {

    fun removeChildren() {
        element.childNodes.asList().forEach { element.removeChild(it) }
    }

    fun circle(init: CircleElement.() -> Unit): CircleElement {
        val circle = circle()
        init(circle)
        element.append(circle.element)
        return circle
    }

    fun rect(init: RectElement.() -> Unit): RectElement {
        val rect = rect()
        init(rect)
        element.append(rect.element)
        return rect
    }


    fun g(init: GroupElement.() -> Unit): GroupElement {
        val g = g()
        init(g)
        element.append(g.element)
        return g
    }

    fun text(init: TextElement.() -> Unit): TextElement {
        val t = text()
        init(t)
        element.append(t.element)
        return t
    }

    fun path(init: PathElement.() -> Unit): PathElement {
        val p = path()
        init(p)
        element.append(p.element)
        return p
    }

    fun line(init: LineElement.() -> Unit): LineElement {
        val l = LineElement(createSVGElement("line"))
        init(l)
        element.append(l.element)
        return l
    }

    fun line(x1: Number = 0, y1: Number = 0, x2: Number = 0, y2: Number = 0, stroke: Color = black): LineElement =
            line {
                this.x1 = x1
                this.y1 = y1
                this.x2 = x2
                this.y2 = y2
                this.stroke = stroke
            }

}

data class Margins(val top: Int = 0, val right: Int = top, val bottom: Int = top, val left: Int = right) {
    val horizontalMargins: Int
        get() = right + left

    val verticalMargins: Int
        get() = top + bottom
}

interface HasText {
    var text: String?
}

interface Transformable : AccessByAttributes {
    fun transform(init: Transform.() -> Unit) {
        setAttribute("transform", Transform().apply(init).toCommand())
    }
}

interface HasStyle : AccessByAttributes {
    fun style(init: Style.() -> Unit) {
        setAttribute("style", Style().apply(init).toAttribute())
    }
}

interface HasPosition : AccessByAttributes {
    var x: Number
        get() = getAttribute("x")?.toFloat() ?: 0f
        set(value) {
            setAttribute("x", "$value")
        }
    var y: Number
        get() = getAttribute("y")?.toFloat() ?: 0f
        set(value) {
            setAttribute("y", "$value")
        }
}

interface HasOffset : AccessByAttributes {
    var dx: Number
        get() = getAttribute("dx")?.toFloat() ?: 0f
        set(value) {
            setAttribute("dx", "$value")
        }
    var dy: Number
        get() = getAttribute("dy")?.toFloat() ?: 0f
        set(value) {
            setAttribute("dy", "$value")
        }
}

interface HasCenter : AccessByAttributes {
    var cx: Number
        get() = getAttribute("cx")?.toFloat() ?: 0f
        set(value) {
            setAttribute("cx", "$value")
        }
    var cy: Number
        get() = getAttribute("cy")?.toFloat() ?: 0f
        set(value) {
            setAttribute("cy", "$value")
        }
}

interface HasRadius : AccessByAttributes {
    var r: Number
        get() = getAttribute("r")?.toFloat() ?: 0f
        set(value) {
            setAttribute("r", "$value")
        }
}

interface HasRoundedCorner : AccessByAttributes {
    var rx: Number
        get() = getAttribute("rx")?.toFloat() ?: 0f
        set(value) {
            setAttribute("rx", "$value")
        }
    var ry: Number
        get() = getAttribute("ry")?.toFloat() ?: 0f
        set(value) {
            setAttribute("ry", "$value")
        }
}

interface HasWidth : AccessByAttributes {
    var width: Number
        get() = getAttribute("width")?.toFloat() ?: 0f
        set(value) {
            setAttribute("width", "$value")
        }
}

interface HasHeight : AccessByAttributes {
    var height: Number
        get() = getAttribute("height")?.toFloat() ?: 0f
        set(value) {
            setAttribute("height", "$value")
        }
}

interface Has2D : HasHeight, HasWidth {
    val center: Point
        get() = Point(width.toDouble() / 2, height.toDouble() / 2)

    val topRight: Point
        get() = Point(width.toDouble(), 0.0)
}
