/*
 * Copyright (c) 2018-2019. data2viz sàrl.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 *
 */

package io.data2viz.viz


/**
 * Manage states of an Element with Stateable properties.
 */
public class StateManager {

    public var status: StateManagerStatus = StateManagerStatus.REST

    public val properties: MutableList<StateProperty> = mutableListOf<StateProperty>()

    public fun addStateProperty(property: StateProperty){
        properties.add(property)
    }

    public fun percentToState(percent: Double) {
        status = StateManagerStatus.UPDATE_PROPERTIES
        properties.forEach {
            it.setPercent(percent)
        }
        status = StateManagerStatus.REST
    }
}
public enum class StateManagerStatus {
    REST, RECORD, UPDATE_PROPERTIES
}

public interface StateProperty {
    public fun setPercent(percent: Double)
}
