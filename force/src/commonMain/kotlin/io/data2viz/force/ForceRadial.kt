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

import io.data2viz.geom.*
import io.data2viz.math.*
import kotlin.math.sqrt

@Deprecated("Deprecated", ReplaceWith("forceSimulation { forceRadial { } }", " io.data2viz.force.ForceSimulation"))
public fun <D> forceRadial(init: ForceRadial<D>.() -> Unit): ForceRadial<D> = ForceRadial<D>().apply(init)

/**
 * Creates a new positioning force towards a circle of the specified radius centered at "center" Point.
 */
public class ForceRadial<D> internal constructor(): Force<D> {

    /**
     * Sets the circle radius to the specified function, re-evaluates the radius accessor for each node.
     * The radius accessor is invoked for each node in the simulation, being passed the node and its zero-based index.
     * The resulting number is then stored internally, such that the radius of each node is only recomputed when the
     * force is initialized or when this method is called with a new radius, and not on every application of the force.
     */
    public var radiusGet: ForceNode<D>.() -> Double = { 100.0 }
        set(value) {
            field = value
            assignNodes(_nodes)
        }

    /**
     * Sets the strength accessor to the specified function, re-evaluates the strength accessor for each node.
     * The strength determines how much to increment the node’s x- and y-velocity.
     * For example, the default value of 10% indicates that the node should move a tenth of the way from its current
     * position to the closest point on the circle with each application.
     * Higher values moves nodes more quickly to the target position, often at the expense of other forces or constraints.
     * A value outside the range [0,100] is not recommended.
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

    /**
     * Sets the coordinate of the circle center which defaults to (0, 0).
     */
    public var centerGet: ForceNode<D>.() -> Point = { defaultCenter }
        set(value) {
            field = value
            assignNodes(_nodes)
        }
    private val defaultCenter = Point(.0, .0)

    private var _nodes: List<ForceNode<D>> = listOf()
    private var _strengths = listOf<Double>()
    private var _centers = listOf<Point>()
    private var _radiuses = listOf<Double>()

    override fun assignNodes(nodes: List<ForceNode<D>>) {
        _nodes = nodes

        _radiuses = nodes.map(radiusGet)
        _strengths = nodes.map { it.strengthGet().value }
        _centers = nodes.map(centerGet)
    }

    override fun applyForceToNodes(intensity: Double) {
        _nodes.forEachIndexed { index, node ->
            var dx = node.x - _centers[index].x
            if (dx == .0) dx = EPSILON
            var dy = node.y - _centers[index].y
            if (dy == .0) dy = EPSILON

            val r = sqrt(dx * dx + dy * dy)
            val k = (_radiuses[index] - r) * _strengths[index] * intensity / r

            node.vx += dx * k
            node.vy += dy * k
        }
    }
}
