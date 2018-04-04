package io.data2viz.viz

import io.data2viz.color.*
import io.data2viz.interpolate.interpolateRgb
import org.w3c.dom.Element
import org.w3c.dom.asList
import kotlin.reflect.KProperty
import kotlin.browser.document

var ids = 1
private fun nextId(name: String): String = "$name${ids++}"

private val Element.defs: Element
    get() {
        val svg = this.svg()
        val defs = svg.childNodes.asList()
                .filterIsInstance<Element>()
                .firstOrNull { it.localName == "defs" }
        return if (defs == null) {
            val newDefs = createSVGElement("defs")
            svg.appendChild(newDefs)
            newDefs
        } else {
            defs
        }
    }


class FillDelegate : StateProperty {

    var start: Color? = null
    var end: Color? = null
    var iterator: ((Number) -> Color)? = null

    private lateinit var element: Element

    override fun setPercent(percent: Double) {
        val value = iterator?.let { it(percent) }.toString()
        element.setAttribute("fill", value)
    }

    operator fun getValue(domElement: ElementWrapper, property: KProperty<*>): ColorOrGradient? {
        return domElement.domElement.getAttribute("fill")?.color
    }
    operator fun setValue(wrapper: ElementWrapper, property: KProperty<*>, value: ColorOrGradient?) {
        (wrapper as? StateableElement)?.stateManager?.let {
            if (it.status == StateManagerStatus.RECORD) {
                element = wrapper.domElement
                //Todo manage null color with alpha
                start = element.getAttribute("fill")?.color!!
                end = value as Color
                it.addStateProperty(this)
                iterator = interpolateRgb(start!!, end!!)
                return
            }
        }

        when (value) {
            null -> wrapper.domElement.setAttribute("fill", "none")
            is LinearGradient -> addLinearGradient(wrapper.domElement, value, "fill")
            is RadialGradient -> setRadialGradient(wrapper.domElement, value, "fill")
            else -> wrapper.domElement.setAttribute("fill", value.toString())
        }
    }

}

internal fun addLinearGradient(
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


//TODO manage all gradients the same way, 
//Todo tests
//Todo are colors stops modified during an animation?
internal fun setRadialGradient(
        element: Element,
        gradient: RadialGradient,
        attribute: String
) {
    
    val gradientElement: Element
    if (gradient.asDynamic().gradientId != null){
        val gradientId = gradient.asDynamic().gradientId
        gradientElement = document.querySelector("#$gradientId")!!
        element.setAttribute(attribute, "url(#$gradientId)")
    }
    else {
        val id = nextId("RadialGradient")
        gradient.asDynamic().gradientId = id
        element.setAttribute(attribute, "url(#$id)")
        gradientElement = createSVGElement("radialGradient").apply {
            setAttribute("id", id)
            setAttribute("gradientUnits", "userSpaceOnUse")
            gradient.colorStops.forEach {
                val stop = createSVGElement("stop").apply {
                    setAttribute("offset", "${100 * it.percent}%")
                    setAttribute("stop-color", "${it.color}")
                }
                appendChild(stop)
            }
        }
        element.defs.appendChild(gradientElement)
    }

    gradientElement.apply {
        setAttribute("cx", gradient.cx.toString())
        setAttribute("cy", gradient.cy.toString())
        setAttribute("r", gradient.r.toString())
    }
}


class StrokeDelegate(val element: Element) : HasStroke {

    override var stroke: ColorOrGradient?
        get() = element.getAttribute("stroke")?.color
        set(value) {
            when (value) {
                null -> element.setAttribute("stroke", "none")
                is LinearGradient -> addLinearGradient(element, value, "stroke")
                is RadialGradient -> setRadialGradient(element, value, "stroke")
                else -> element.setAttribute("stroke", value.toString())
            }
        }

    override var strokeWidth: Double?
        get() = element.getAttribute("stroke-width")?.toDouble()
        set(value) {
            element.setAttribute("stroke-width", value?.toString() ?: "")
        }
}

