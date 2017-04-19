package io.data2viz.samples.chart

import io.data2viz.color.Color
import io.data2viz.color.color
import io.data2viz.color.colors.black
import io.data2viz.color.colors.steelblue
import io.data2viz.color.colors.white
import namespace
import org.w3c.dom.Element
import io.data2viz.color.rgba
import kotlin.browser.document


fun chart() {

    val datas = listOf(55, 44, 30, 23, 17, 14, 16, 25, 41, 61, 85,
            101, 95, 105, 114, 150, 180, 210, 125, 100, 71,
            75, 72, 67)

    val maxValue = datas.max()!!
    val barWidth = 20
    val barPadding = 3
    val graphWidth = datas.size * (barPadding + barWidth) - barPadding
    val margin = Margins(10, 10, 10, 50)

    val totalWidth = graphWidth + margin.horizontalMargins
    val totalHeight = maxValue + margin.verticalMargins

    val xLoc = { i: Int -> i * (barPadding + barWidth) }
    val yLoc = { d: Int -> maxValue - d }

    svg {
        width = totalWidth
        height = totalHeight

        rect {
            width = totalWidth
            height = totalHeight
            stroke = black
            strokeWidth = "1"
            fill = white
        }

        g {
            transform {
                translate(47,10)
            }
            Axis(listOf(1,2,3)).buildIn(this)
        }

        g {
            transform {
                translate(margin.left, margin.top)
            }

            rect {
                width = totalWidth - margin.horizontalMargins
                height = totalHeight - margin.verticalMargins
                fill = rgba(0, 0, 0, .1)
            }


            datas.forEachIndexed { index, data ->
                g {
                    transform {
                        translate(xLoc(index), yLoc(data))
                    }
                    rect {
                        width = barWidth
                        fill = steelblue
                        height = data
                    }
                    text {
                        fill = white
                        text = data.toString()
                        transform {
                            translate(barWidth / 2)
                        }
                        setAttribute("alignment-baseline", "before-edge")
                        setAttribute("text-anchor", "middle")
                        style {
                            fontFamily("sans-serif")
                            fontSize("10px")
                        }
                    }
                }
            }
        }
    }
}


//------------   API test -----------------------


class Axis (var values:List<Any>) {
    fun buildIn(parent: ParentElement ){

        (0..10).forEach {
            val data = 20 * it
            parent.g {
                transform { translate(0, 210 - data) }
                line (x2 = -6 )
                text {
                    style {
                        setStyle("text-anchor", "end")
                    }
                    x = - 9
                    y = 0
                    setAttribute("dy", ".32em")
                    text = data.toString()
                }
            }
        }

        parent.path {
            strokeWidth = "1"
            stroke = black
            shape {
                moveTo(-6)
                horizontalDeltaTo(0)
                verticalLineDeltaTo(210)
                horizontalDeltaTo(-6)
            }
            setAttribute("fill", "none" )
        }
    }
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
            setAttribute("fill", value?.toString())
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

// scale ----------------
data class DomainToViz<out A, out B>(
        val domain: A,
        val viz: B
)

infix fun <A, B> A.to(that: B): DomainToViz<A, B> = DomainToViz(this, that)

class scale {

    object linear {

        fun pointsToPoints(start: DomainToViz<Point, Point>, end: DomainToViz<Point, Point>) =
                { pt: Point ->
                    Point(
                            doublesToDoubles(start.domain.x to start.viz.x, end.domain.x to end.viz.x)(pt.x),
                            doublesToDoubles(start.domain.y to start.viz.y, end.domain.y to end.viz.y)(pt.y))
                }

        fun doublesToDoubles(start: DomainToViz<Double, Double>, end: DomainToViz<Double, Double>) =
                { domain: Double -> domain * (end.viz - start.viz) / (end.domain - start.domain) + start.viz }

    }
}


fun svg(init: SVGElement.() -> Unit = {}): SVGElement {
    var svgElement = document.querySelector("svg")
    if (svgElement == null) {
        svgElement = svg.createSVGElement("svg")
        document.querySelector("body")!!.append(svgElement)
    }
    return SVGElement(svgElement).apply(init)
}

@SvgTagMarker
class RectElement(override val element: Element) : ElementWrapper, HasStroke, HasFill, Has2D, ParentElement



@SvgTagMarker
class GroupElement(override val element: Element) : ElementWrapper, HasStroke, HasFill, Has2D, HasPosition, ParentElement, Transformable

@SvgTagMarker
class PathElement(override val element: Element) : ElementWrapper, HasStroke, HasFill {

    fun shape(init: Path.() -> Unit){
        val path = Path()
        init(path)
        setAttribute("d", path.toCommand())
    }
}




@SvgTagMarker
class TextElement(override val element: Element) : HasText, HasPosition, ElementWrapper, HasFill, Transformable, HasStyle {
    override var text: String?
        get() = element.textContent
        set(value) {
            element.textContent = value
        }
}

/**
 * The root of an SVG visualization
 */
@SvgTagMarker
class SVGElement(override var element: Element) : Has2D, ParentElement, ElementWrapper {

    var margins: Margins = Margins()
        set(value) {
            element
        }
}

interface ParentElement : ElementWrapper {

    fun rect(init: RectElement.() -> Unit) {
        val rect = svg.rect()
        init(rect)
        element.append(rect.element)
    }

    fun line(x1: Number = 0, y1: Number = 0, x2:Number = 0, y2:Number = 0, stroke:Color = black) {
        element.append(svg.createSVGElement("line").apply {
            setAttribute("x1", "$x1")
            setAttribute("y1", "$y1")
            setAttribute("x2", "$x2")
            setAttribute("y2", "$y2")
            setAttribute("stroke", "$stroke")
        })
    }

    fun g(init: GroupElement.() -> Unit) {
        val g = svg.g()
        init(g)
        element.append(g.element)
    }

    fun text(init: TextElement.() -> Unit) {
        val t = svg.text()
        init(t)
        element.append(t.element)
    }

    fun path(init:PathElement.() -> Unit){
        val p = svg.path()
        init(p)
        element.append(p.element)
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
            setAttribute("x", value.toString())
        }
    var y: Number
        get() = getAttribute("y")?.toFloat() ?: 0f
        set(value) {
            setAttribute("y", value.toString())
        }
}

interface HasWidth : AccessByAttributes {
    var width: Number
        get() = getAttribute("width")?.toFloat() ?: 0f
        set(value) {
            setAttribute("width", value.toString())
        }
}

interface HasHeight : AccessByAttributes {
    var height: Number
        get() = getAttribute("height")?.toFloat() ?: 0f
        set(value) {
            setAttribute("height", value.toString())
        }
}

interface Has2D : HasHeight, HasWidth {
    val center: Point
        get() = Point(width.toDouble() / 2, height.toDouble() / 2)

    val topRight: Point
        get() = Point(width.toDouble(), 0.0)
}

class svg {
    companion object {

        fun createSVGElement(name: String) = document.createElementNS(namespace.svg, name)

        fun rect(width: Double = 10.0, height: Double = 10.0) =
                RectElement(createSVGElement("rect")).apply {
                    this.width = width
                    this.height = width
                }

        fun g() = GroupElement(createSVGElement("g"))
        fun path() = PathElement(createSVGElement("path"))
        fun text() = TextElement(createSVGElement("text"))
    }
}


class Path() {
    private val commands = mutableListOf<String>()
    fun moveTo(x: Number = 0, y: Number = 0)        {commands.add("M $x $y")}
    fun moveDeltaTo(dx: Number = 0, dy: Number = 0) {commands.add("m $dx $dy")}
    fun lineTo(x: Number = 0, y: Number = 0)        {commands.add("L $x $y")}
    fun lineDeltaTo(dx: Number = 0, dy: Number = 0) {commands.add("l $dx $dy")}
    fun horizontalTo(x: Number = 0)                 {commands.add("H $x")}
    fun horizontalDeltaTo(dx: Number = 0)           {commands.add("H $dx")}
    fun verticalLineTo(y: Number = 0)               {commands.add("V $y")}
    fun verticalLineDeltaTo(dy: Number = 0)         {commands.add("v $dy")}
    fun closePath()                                 {commands.add("Z")}

    fun toCommand() = commands.joinToString(separator = " ")
}

class Transform() {
    private val commands = mutableMapOf<String, String>()

    fun translate(x: Number = 0, y: Number = 0) {
        commands.put("translate", "translate($x, $y)")
    }

    fun scale(x: Number = 1, y: Number = x) {
        commands.put("scale", "scale($x, $y)")
    }

    fun skewX(a: Number) {
        commands.put("skewX", "skewX($a)")
    }

    fun skewY(a: Number) {
        commands.put("skewY", "skewX($a)")
    }

    fun rotate(angle: Number, x: Number = 0, y: Number = 0) {
        commands.put("rotate", "rotate($angle, $x, $y")
    }

    fun toCommand(): String = commands.values.joinToString(" ")
}

class Style() {
    private val styles = mutableMapOf<String, String>()

    fun setStyle(property: String, value: String) = styles.put(property, "$property: $value")

    fun fontFamily(name: String) { setStyle("font-family", name) }
    fun fontSize(size: String)   { setStyle("font-size", size) }

    fun toAttribute(): String = styles.values.joinToString("; ")
}


data class Point(val x: Double = 0.0, val y: Double = 0.0) {

    companion object {
        val origin = Point()
    }

    operator fun plus(speed: Speed) = Point(x + speed.vx, y + speed.vy)
}

data class Speed(val vx: Double = 0.0, val vy: Double = 0.0) {
    operator fun plus(speed: Speed) = Speed(vx + speed.vx, vy + speed.vy)
}
