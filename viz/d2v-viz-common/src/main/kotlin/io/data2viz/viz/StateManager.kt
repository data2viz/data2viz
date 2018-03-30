package io.data2viz.viz


/**
 * Manage states of an Element with Stateable properties.
 */
class StateManager {

    var status = StateManagerStatus.REST

    val properties = mutableListOf<StateProperty>()

    fun addStateProperty(property: StateProperty){
        properties.add(property)
    }

    fun percentToState(percent: Double) {
        status = StateManagerStatus.UPDATE_PROPERTIES
        properties.forEach {
            it.setPercent(percent)
        }
        status = StateManagerStatus.REST
    }
}
enum class StateManagerStatus {
    REST, RECORD, UPDATE_PROPERTIES
}

interface StateProperty {
    fun setPercent(percent: Double)
}
