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

package io.data2viz.force

import io.data2viz.math.EPSILON
import kotlin.random.Random

fun <D> forceSimulation(init: ForceSimulation<D>.() -> Unit) = ForceSimulation<D>().apply { init() }

internal fun jiggle() = (Random.nextDouble() - 0.5) * EPSILON

interface Force<D> {

    /**
     * Assigns nodes to this force.
     * This method is called when a force is bound to a simulation via simulation.force and when the simulation’s
     * nodes change via simulation.nodes.
     * A force may perform necessary work during initialization, such as evaluating per-node parameters, to avoid
     * repeatedly performing work during each application of the force.
     *
     * Todo should be internal. No use outside of package
     */
    fun assignNodes(nodes: List<ForceNode<D>>)

    /**
     * Applies this force, optionally observing the specified intensity.
     * Typically, the force is applied to the array of nodes previously passed to force.assignNodes, however, some
     * forces may apply to a subset of nodes, or behave differently.
     * For example, forceLink applies to the source and target of each link.
     */
    fun applyForceToNodes(intensity: Double)
}