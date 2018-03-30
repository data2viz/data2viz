package io.data2viz.viz

import org.w3c.dom.Element
import kotlin.reflect.KProperty

class DoubleAttributePropertyDelegate :
    StateProperty {

    var propName: String? = null

    private lateinit var stateTarget: Element

    private var startState: Double? = null
    private var endState: Double? = null

    override fun setPercent(percent: Double) {
        stateTarget.setAttribute(propName!!, (startState!! + percent * (endState!! - startState!!)).toString())
    }

    operator fun getValue(elementWrapper: ElementWrapper, property: KProperty<*>): Double =
        elementWrapper.domElement.getAttribute(
            propName ?: propertyMapping.getOrElse(property.name, { property.name }).also { propName = it })?.toDouble()
                ?: 0.0

    operator fun setValue(element: ElementWrapper, property: KProperty<*>, d: Double) {
        (element as StateableElement).stateManager?.let {
            if (it.status == StateManagerStatus.RECORD) {
                stateTarget = element.domElement
                startState = getValue(element, property)
                endState = d
                it.addStateProperty(this)
                return
            }
        }
        element.domElement.setAttribute(
            propName ?: propertyMapping.getOrElse(property.name, { property.name }).also { propName = it },
            d.toString()
        )
    }
}