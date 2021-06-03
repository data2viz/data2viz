/*
 * Copyright (c) 2018-2021. data2viz sàrl.
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

import io.data2viz.math.*

@Deprecated("Deprecated", ReplaceWith("forceSimulation { forceX { } }", " io.data2viz.force.ForceSimulation"))
public fun <D> forceX(init: ForceX<D>.() -> Unit): ForceX<D> = ForceX<D>().apply(init)

/**
 * Creates a new positioning force along the x-axis towards the given position x.
 * If x is not specified, it defaults to 0.
 */
public class ForceX<D> internal constructor(): Force<D> {

    /**
     * Sets the x-coordinate accessor to the specified function, re-evaluates the x-accessor for each node.
     * If x is not specified, returns the current x-accessor, which defaults to { .0 }
     * The x-accessor is invoked for each node in the simulation, being passed the node and its zero-based index.
     * The resulting number is then stored internally, such that the target x-coordinate of each node is only recomputed
     * when the force is initialized or when this method is called with a new x, and not on every application of the force.
     */
    public var xGet: ForceNode<D>.() -> Double = { .0 }
        set(value) {
            field = value
            assignNodes(_nodes)
        }

    /**
     * Sets the strength accessor to the specified function, re-evaluates the strength accessor for each node.
     * The strength determines how much to increment the node’s x-velocity: (x - node.x) × strength.
     * For example, a value of 10% indicates that the node should move a tenth of the way from its current x-position
     * to the target x-position with each application. Higher values moves nodes more quickly to the target position,
     * often at the expense of other forces or constraints. A value outside the range [0,100] is not recommended.
     *
     * If strength is not specified, returns the current strength accessor, which defaults to { 10% }.
     *
     * The strength accessor is invoked for each node in the simulation, being passed the node and its zero-based index.
     * The resulting number is then stored internally, such that the strength of each node is only recomputed when the
     * force is initialized or when this method is called with a new strength, and not on every application of the force.
     */
    public var strengthGet: ForceNode<D>.() -> Percent = { 10.pct }
        set(value) {
            field = value
            assignNodes(_nodes)
        }

    private var _nodes = listOf<ForceNode<D>>()
    private var _strengths = listOf<Double>()
    private var _x = listOf<Double>()

    override fun assignNodes(nodes: List<ForceNode<D>>) {
        _nodes = nodes

        _x = nodes.map(xGet)
        _strengths = nodes.map { it.strengthGet().value }
    }

    override fun applyForceToNodes(intensity: Double) {
        _nodes.forEachIndexed { index, node ->
            node.vx += (_x[index] - node.x) * _strengths[index] * intensity
        }
    }
}
