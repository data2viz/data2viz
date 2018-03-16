package io.data2viz.viz

import io.data2viz.color.ColorOrGradient
import io.data2viz.color.LinearGradient
import io.data2viz.color.color
import io.data2viz.core.CssClass
import io.data2viz.core.Point
import io.data2viz.math.Angle
import io.data2viz.path.PathAdapter
import io.data2viz.path.SvgPath
import org.w3c.dom.Element
import org.w3c.dom.asList
import org.w3c.dom.svg.SVGElement
import kotlin.browser.document
import kotlin.reflect.KProperty


actual fun newRect(): Rect = RectDOM()
actual fun newLine(): Line = LineDOM()
actual fun newCircle(): Circle = CircleDOM()
actual fun newGroup(): Group = ParentElement()
actual fun newText(): Text = TextDOM()
actual fun newPath(): PathVizElement = PathDOM()

fun createSVGElement(name: String, classes: String = "") = document.createElementNS("http://www.w3.org/2000/svg", name).apply {
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
 * Looks for the first parent which is a svg Element.
 * @throws IllegalStateException if the current element has no svg as parent
 */
fun Element.svg():Element {

    fun parent(child:Element, predicate:(Element) -> Boolean): Element {
        var parent = child.parentElement
        while (parent != null && !predicate(parent)){
            parent = parent.parentElement
        }
        if (parent == null) throw IllegalArgumentException("No parent matching the predicate")
        return parent
    }

    return parent(this) {it.localName == "svg"}
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


interface DOMVizElement {
    val domElement: Element
}

class ParentElement(override val domElement: Element = createSVGElement("g")) : VizContext, DOMVizElement,
        StyledElement by StyledDelegate(domElement),
        Transformable by TransformableDelegate(domElement) {

    init {
        check(domElement.namespaceURI == "http://www.w3.org/2000/svg")
    }

    override fun add(vizElement: VizElement) {

        when (vizElement) {
            is PathDOM -> {
                val element = createSVGElement("path")
                domElement.append(element)
                element.setAttribute("d", vizElement.svgPath.path)

            }
            else -> domElement.appendChild((vizElement as DOMVizElement).domElement)
        }
    }


    override fun path(init: PathVizElement.() -> Unit): PathVizElement {
        val svgPath = SvgPath()
        val element = createSVGElement("path")
        domElement.append(element)
        val item = PathDOM(element, svgPath)
        init(item)
        element.setAttribute("d", svgPath.path)
        return item
    }

    override fun setStyle(style: String) {
        domElement.setAttribute("style", style)
    }

    override fun circle(init: Circle.() -> Unit): Circle {

        val circle = CircleDOM(createSVGElement("circle"))
        init(circle)
        domElement.append(circle.domElement)
        return circle
    }

    override fun group(init: Group.() -> Unit): Group {
        val group = ParentElement(createSVGElement("g"))
        init(group)
        domElement.append(group.domElement)
        return group
    }


    override fun line(init: Line.() -> Unit): Line {
        val line = LineDOM()
        init(line)
        domElement.append(line.domElement)
        return line
    }

    override fun rect(init: Rect.() -> Unit): Rect {

        val rect = RectDOM()
        init(rect)
        domElement.append(rect.domElement)
        return rect
    }

    override fun text(init: Text.() -> Unit): Text {
        val text = TextDOM()
        init(text)
        domElement.append(text.domElement)
        return text
    }

}


interface AccessByAttributes {
    fun setAttribute(name: String, value: String?)
    fun getAttribute(name: String): String?
}

interface ElementWrapper : AccessByAttributes, DOMVizElement {
    override val domElement: Element

    override fun setAttribute(name: String, value: String?) {
        if (value != null)
            domElement.setAttribute(name, value)
        else
            domElement.removeAttribute(name)
    }

    override fun getAttribute(name: String) = domElement.getAttribute(name)

}

class PathDOM(override val domElement: Element = createSVGElement("path"), val svgPath: SvgPath = SvgPath()) : PathVizElement, ElementWrapper,
        PathAdapter by svgPath,
        HasFill by FillDelegate(domElement),
        HasStroke by StrokeDelegate(domElement),
        StyledElement by StyledDelegate(domElement),
        Transformable by TransformableDelegate(domElement)

//@SvgTagMarker
class CircleDOM(override val domElement: Element = createSVGElement("circle")) : ElementWrapper, Circle,
        HasFill by FillDelegate(domElement),
        HasStroke by StrokeDelegate(domElement),
        StyledElement by StyledDelegate(domElement),
        Transformable by TransformableDelegate(domElement) {

    override var cx: Double by DoubleAttributePropertyDelegate()
    override var cy: Double by DoubleAttributePropertyDelegate()
    override var radius: Double by DoubleAttributePropertyDelegate()
}

class LineDOM(override val domElement: Element = createSVGElement("line")) : ElementWrapper, Line,
        HasFill by FillDelegate(domElement),
        HasStroke by StrokeDelegate(domElement),
    StyledElement by StyledDelegate(domElement),
    Transformable by TransformableDelegate(domElement) {

    override var x1: Double by DoubleAttributePropertyDelegate()
    override var y1: Double by DoubleAttributePropertyDelegate()
    override var x2: Double by DoubleAttributePropertyDelegate()
    override var y2: Double by DoubleAttributePropertyDelegate()
}

class RectDOM(override val domElement: Element = createSVGElement("rect")) : ElementWrapper, Rect,
        HasFill by FillDelegate(domElement),
        HasStroke by StrokeDelegate(domElement),
        StyledElement by StyledDelegate(domElement),
        Transformable by TransformableDelegate(domElement) {

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
    override var fill: ColorOrGradient?
        get() = element.getAttribute("fill")?.color
        set(value) {
            when (value) {
                null                -> element.setAttribute("fill", "none")
                is LinearGradient   -> addGradient(element, value, "fill")
                else                -> element.setAttribute("fill", value.toString())
            }
        }
}

internal fun addGradient(
    element: Element,
    linearGradient: LinearGradient,
    attribute: String
) {
    val id = nextId("LinearGradient")
    element.setAttribute(attribute, "url(#$id)")
    val linearGradientElement = createSVGElement("linearGradient").apply {
        setAttribute("id", id)
        setAttribute("gradientUnits", "userSpaceOnUse")
        setAttribute("x1", linearGradient.x1.toString())
        setAttribute("y1", linearGradient.y1.toString())
        setAttribute("x2", linearGradient.x2.toString())
        setAttribute("y2", linearGradient.y2.toString())
        linearGradient.colorStops.forEach {
            val stop = createSVGElement("stop").apply {
                setAttribute("offset", "${100 * it.percent}%")
                setAttribute("stop-color", "${it.color}")
            }
            appendChild(stop)
        }
    }
    element.defs.appendChild(linearGradientElement)
}


var ids = 1
private fun nextId(name: String): String = "$name${ids++}"

private val Element.defs: Element
    get() {
        val svg = this.svg()
        val defs = svg.childNodes.asList()
            .filterIsInstance<Element>()
            .firstOrNull { it.localName == "defs" }
        return if (defs == null){
            val newDefs = createSVGElement("defs")
            svg.appendChild(newDefs)
            newDefs
        } else {
            defs
        }
    }

class StrokeDelegate(val element: Element) : HasStroke {

    override var stroke: ColorOrGradient?
        get() = element.getAttribute("stroke")?.color
        set(value) {
            when (value) {
                null                -> element.setAttribute("stroke", "none")
                is LinearGradient   -> addGradient(element, value, "stroke")
                else                -> element.setAttribute("stroke", value.toString())
            }
        }

    override var strokeWidth: Double?
        get() = element.getAttribute("stroke-width")?.toDouble()
        set(value) {
            element.setAttribute("stroke-width", value?.toString() ?: "")
        }
}

class DoubleAttributePropertyDelegate {
    operator fun getValue(elementWrapper: ElementWrapper, property: KProperty<*>): Double =
            elementWrapper.domElement.getAttribute(propertyMapping.getOrElse(property.name, { property.name }))?.toDouble() ?: 0.0

    operator fun setValue(element: ElementWrapper, property: KProperty<*>, d: Double) {
        element.domElement.setAttribute(propertyMapping.getOrElse(property.name, { property.name }), d.toString())
    }

}


