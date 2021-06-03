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

import io.data2viz.geom.Point

@Deprecated("Deprecated", ReplaceWith("forceSimulation { forceCenter { } }", " io.data2viz.force.ForceSimulation"))
fun <D> forceCenter(center: Point) = ForceCenter<D>().apply { this.center = center }

/**
 * The centering force translates nodes uniformly so that the mean position of all nodes
 * (the center of mass if all nodes have equal weight) is at the given position ⟨x,y⟩.
 * This force modifies the positions of nodes on each application; it does not modify velocities,
 * as doing so would typically cause the nodes to overshoot and oscillate around the desired center.
 * This force helps keeps nodes in the center of the viewport, and unlike the positioning force,
 * it does not distort their relative positions.
 */
public class ForceCenter<D> internal constructor() : Force<D> {

    private var _nodes = listOf<ForceNode<D>>()

    public var center: Point = Point(.0, .0)

    override fun assignNodes(nodes: List<ForceNode<D>>) {
        _nodes = nodes
    }

    override fun applyForceToNodes(intensity: Double) {
        val size = _nodes.size.toDouble()

        var sx = .0
        var sy = .0

        _nodes.forEach { node ->
            sx += node.x
            sy += node.y
        }

        sx = sx / size - center.x
        sy = sy / size - center.y

        _nodes.forEach { node ->
            node.x = node.x - sx
            node.y = node.y - sy
        }
    }
}
