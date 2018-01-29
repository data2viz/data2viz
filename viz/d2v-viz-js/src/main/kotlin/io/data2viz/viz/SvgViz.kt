package io.data2viz.viz

import io.data2viz.color.Color
import io.data2viz.color.color
import io.data2viz.core.CssClass
import io.data2viz.core.Point
import io.data2viz.math.Angle
import io.data2viz.path.PathAdapter
import io.data2viz.path.SvgPath
import org.w3c.dom.Element
import org.w3c.dom.svg.SVGElement
import kotlin.browser.document
import kotlin.reflect.KProperty


val svgNamespaceURI = "http://www.w3.org/2000/svg"
internal fun createSVGElement(name: String, classes: String = "") = document.createElementNS(svgNamespaceURI, name).apply {
    if (classes.isNotBlank())
        setAttribute("class", classes)
}

fun selectOrCreateSvg(): SVGElement {
    var svgElement = document.querySelector("svg") as SVGElement?
    if (svgElement == null) {
        svgElement = createSVGElement("svg") as SVGElement
        document.querySelector("body")!!.append(svgElement)
    }
    return svgElement
}

/**
 * Bootstrap a VizContext on a existing SVG element.
 */
fun Element.viz(init: VizContext.() -> Unit): VizContext {
    val root = createSVGElement("g")
    append(root)
    val context = ParentElement(root)
    init(context)
    return context
}

class ParentElement(val parent: Element) : VizContext,
        StyledElement by StyledDelegate(parent),
        Transformable by TransformableDelegate(parent) {

    init {
        check(parent.namespaceURI == svgNamespaceURI)
    }

    override fun path(init: PathVizItem.() -> Unit): PathVizItem {
        val svgPath = SvgPath()
        val element = createSVGElement("path")
        val item = PathElement(element, svgPath)
        init(item)
        element.setAttribute("d", svgPath.path)
        parent.append(element)
        return item
    }

    override fun setStyle(style: String) {
        parent.setAttribute("style", style)
    }

    override fun circle(init: CircleVizItem.() -> Unit): CircleVizItem {

        val circle = CircleElement(createSVGElement("circle"))
        init(circle)
        parent.append(circle.element)
        return circle
    }

    override fun group(init: ParentItem.() -> Unit): ParentItem {
        val group = ParentElement(createSVGElement("g"))
        init(group)
        parent.append(group.parent)
        return group
    }


    override fun line(init: LineVizItem.() -> Unit): LineVizItem {
        val line = LineElement(createSVGElement("line"))
        init(line)
        parent.append(line.element)
        return line
    }

    override fun rect(init: RectVizItem.() -> Unit): RectVizItem {

        val rect = RectElement(createSVGElement("rect"))
        init(rect)
        parent.append(rect.element)
        return rect
    }

    override fun text(init: TextVizItem.() -> Unit): TextVizItem {
        val text = TextElement(createSVGElement("text"))
        init(text)
        parent.append(text.element)
        return text
    }

}


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

class PathElement(val element: Element, svgPath: SvgPath) : PathVizItem,
        PathAdapter by svgPath,
        HasFill by FillDelegate(element),
        HasStroke by StrokeDelegate(element),
        StyledElement by StyledDelegate(element),
        Transformable by TransformableDelegate(element)

//@SvgTagMarker
class CircleElement(override val element: Element) : ElementWrapper, CircleVizItem,
        HasFill by FillDelegate(element),
        HasStroke by StrokeDelegate(element),
        StyledElement by StyledDelegate(element),
        Transformable by TransformableDelegate(element) {

    override var cx: Double by DoubleAttributePropertyDelegate()
    override var cy: Double by DoubleAttributePropertyDelegate()
    override var radius: Double by DoubleAttributePropertyDelegate()
}

class LineElement(override val element: Element) : ElementWrapper, LineVizItem,
        HasFill by FillDelegate(element),
        HasStroke by StrokeDelegate(element),
    StyledElement by StyledDelegate(element),
    Transformable by TransformableDelegate(element) {

    override var x1: Double by DoubleAttributePropertyDelegate()
    override var y1: Double by DoubleAttributePropertyDelegate()
    override var x2: Double by DoubleAttributePropertyDelegate()
    override var y2: Double by DoubleAttributePropertyDelegate()
}

class RectElement(override val element: Element) : ElementWrapper, RectVizItem,
        HasFill by FillDelegate(element),
        HasStroke by StrokeDelegate(element),
        StyledElement by StyledDelegate(element),
        Transformable by TransformableDelegate(element) {

    override var x: Double by DoubleAttributePropertyDelegate()
    override var y: Double by DoubleAttributePropertyDelegate()
    override var width: Double by DoubleAttributePropertyDelegate()
    override var height: Double by DoubleAttributePropertyDelegate()
    override var rx: Double by DoubleAttributePropertyDelegate()
    override var ry: Double by DoubleAttributePropertyDelegate()
}


val propertyMapping = mapOf(
        "radius" to "r"
)

class TransformableDelegate(val element: Element) : Transformable {
    override fun transform(init: Transform.() -> Unit) {
        element.setAttribute("transform", TransformSvg().apply(init).toCommand())
    }

}

class TransformSvg : Transform {
    private val commands = mutableMapOf<String, String>()
    fun translate(newPoint: Point) {
        commands.put("translate", "translate(${newPoint.x}, ${newPoint.y})")
    }

    override fun translate(x: Double, y: Double) {
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

    fun rotate(angle: Angle, x: Number = 0, y: Number = 0) {
        commands.put("rotate", "rotate(${angle.deg}, $x, $y)")
    }

    internal fun toCommand(): String = commands.values.joinToString(" ")
}


class StyledDelegate(val element: Element): StyledElement {
    override fun addClass(cssClass: CssClass) {
        element.classList.add(cssClass.name)
    }

}

class FillDelegate(val element: Element) : HasFill {
    override var fill: Color?
        get() = element.getAttribute("fill")?.color
        set(value) {
            element.setAttribute("fill", value?.toString() ?: "none")
        }

}

class StrokeDelegate(val element: Element) : HasStroke {

    init {
        element.setAttribute("stroke", "#000")
    }

    override var stroke: Color?
        get() = element.getAttribute("stroke")?.color
        set(value) {
            if(value!= null){
                element.setAttribute("stroke", value.toString())
            } else element.removeAttribute("stroke")
        }

    override var strokeWidth: Double?
        get() = element.getAttribute("stroke-width")?.toDouble()
        set(value) {
            element.setAttribute("stroke-width", value?.toString() ?: "")
        }
}

class DoubleAttributePropertyDelegate {
    operator fun getValue(elementWrapper: ElementWrapper, property: KProperty<*>): Double =
            elementWrapper.element.getAttribute(propertyMapping.getOrElse(property.name, { property.name }))?.toDouble() ?: 0.0

    operator fun setValue(element: ElementWrapper, property: KProperty<*>, d: Double) {
        element.element.setAttribute(propertyMapping.getOrElse(property.name, { property.name }), d.toString())
    }

}


