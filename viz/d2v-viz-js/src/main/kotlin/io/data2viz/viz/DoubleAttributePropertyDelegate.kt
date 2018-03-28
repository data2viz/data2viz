package io.data2viz.viz

import org.w3c.dom.Element
import kotlin.reflect.KProperty

class DoubleAttributePropertyDelegate(val stateManager: StateManager? = null) :
    StateProperties {

    var propName:String? = null

    private lateinit var stateTarget: Element

    override fun setPercent(percent: Double) {
        stateTarget.setAttribute(propName!!, (states[0] + percent * (states[1]- states[0])).toString())
    }

    val states by lazy { mutableListOf<Double>() }

    operator fun getValue(elementWrapper: ElementWrapper, property: KProperty<*>): Double =
            elementWrapper.domElement.getAttribute(
                propName ?: propertyMapping.getOrElse(property.name, { property.name }).also { propName = it })?.toDouble() ?: 0.0


    operator fun setValue(element: ElementWrapper, property: KProperty<*>, d: Double) {

        if (stateManager?.status == StateManagerStatus.RECORD){
            stateTarget = element.domElement
            if (states.size == 0){
                states.add(getValue(element, property))

            }
            states.add(d)
            stateManager.addStateProperty(this)
        } else {
            element.domElement.setAttribute(
                propName ?: propertyMapping.getOrElse(property.name, { property.name }).also { propName = it }, d.toString())

        }
    }

}