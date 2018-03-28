package io.data2viz.viz

import io.data2viz.color.Color
import io.data2viz.color.ColorOrGradient
import io.data2viz.color.LinearGradient
import io.data2viz.color.color
import io.data2viz.interpolate.interpolateRgb
import org.w3c.dom.Element
import org.w3c.dom.asList


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



class FillDelegate(val element: Element, val stateManager: StateManager? = null) : HasFill, StateProperties {

    val states by lazy { mutableListOf<Color>() }
    var iterator: ((Number) -> Color)? = null

    override fun setPercent(percent: Double) {
        val value = iterator?.let { it(percent) }.toString()
        element.setAttribute("fill", value)
    }

    override var fill: ColorOrGradient?

            //todo parse gradient from string
        get() = element.getAttribute("fill")?.color
        set(value) {

            if (stateManager?.status == StateManagerStatus.RECORD) {
                if (states.size == 0) {
                    states.add(element.getAttribute("fill")?.color!!) //todo manage null
                }
                states.add(value as Color)
                stateManager.addStateProperty(this)

                iterator = interpolateRgb(states[0], states[1])
            } else {


                when (value) {
                    null -> element.setAttribute("fill", "none")
                    is LinearGradient -> addGradient(element, value, "fill")
                    else -> element.setAttribute("fill", value.toString())
                }
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




class StrokeDelegate(val element: Element) : HasStroke {

    override var stroke: ColorOrGradient?
        get() = element.getAttribute("stroke")?.color
        set(value) {
            when (value) {
                null                -> element.setAttribute("stroke", "none")
                is LinearGradient -> addGradient(element, value, "stroke")
                else                -> element.setAttribute("stroke", value.toString())
            }
        }

    override var strokeWidth: Double?
        get() = element.getAttribute("stroke-width")?.toDouble()
        set(value) {
            element.setAttribute("stroke-width", value?.toString() ?: "")
        }
}

