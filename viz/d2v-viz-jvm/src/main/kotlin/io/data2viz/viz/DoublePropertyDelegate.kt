package io.data2viz.viz

import javafx.beans.property.DoubleProperty
import kotlin.reflect.KProperty


/**
 * Manage the delegation to DoubleProperty.
 * It is a StateProperty and then can be used in transitions.
 */
class DoublePropertyDelegate(val property: DoubleProperty) : StateProperty {


    private var startState: Double? = null
    private var endState: Double? = null

    override fun setPercent(percent: Double) {
        property.set(startState!! + percent * (endState!! - startState!!))
    }

    operator fun getValue(vizElement: VizElement, prop: KProperty<*>): Double = property.get()

    operator fun setValue(element: VizElement, prop: KProperty<*>, d: Double) {
        (element as? StateableElement)?.stateManager?.let {
            if (it.status == StateManagerStatus.RECORD) {
                startState = getValue(element, prop)
                endState = d
                it.addStateProperty(this)
                return
            }
        }
        property.set(d)
    }
}