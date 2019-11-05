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

import kotlin.math.*

// TODO Link rename to something more precise ?
/**
 * A Link object records a link a source and a target ForceNode.
 * The force will try to keep the 2 nodes at the specified distance, with the specified strength.
 * Default distance is 30.0.
 * Default strength is NaN. If strength is left at NaN, a strength of 1/X will be applied where X is the minimum
 * number of links between the 2 nodes. This default was chosen because it automatically reduces the strength of links
 * connected to heavily-connected nodes, improving stability.
 */
data class Link<D>(
    val source: ForceNode<D>,
    val target: ForceNode<D>,
    val distance: Double = 30.0,
    var strength: Double = Double.NaN
)

@Deprecated("Deprecated", ReplaceWith("forceSimulation { forceLink { } }", " io.data2viz.force.ForceSimulation"))
fun <D> forceLink(init: ForceLink<D>.() -> Unit) = ForceLink<D>().apply(init)

/**
 * The link force pushes linked nodes together or apart according to the desired link distance.
 * The strength of the force is proportional to the difference between the linked nodes’ distance and the target
 * distance, similar to a spring force.
 */
class ForceLink<D> internal constructor(): Force<D> {

    private var _nodes = listOf<ForceNode<D>>()

    private var _links = listOf<Link<D>>()
    val links: List<Link<D>>
        get() = _links

    private var bias: Array<Double> =  arrayOf()
    private var count:Array<Int> = arrayOf()

    /**
     * Number of iterations per application.
     * Increasing the number of iterations greatly increases the rigidity of the constraint and avoids partial overlap
     * of nodes, but also increases the runtime cost to evaluate the force.
     */
    var iterations = 1

    /**
     * Get the list of links from a given ForceNode, defaults to null.
     * Each Link must have a reference to a source node, a target node, a distance value (defaults to 30.0) and
     * a strength (defaults to Double.NaN) value.
     */
    var linkGet: ForceNode<D>.()-> List<Link<D>>? = { null }

    override fun assignNodes(nodes: List<ForceNode<D>>) {
        _nodes = nodes
        _links = nodes.mapNotNull(linkGet).flatten()

        // count links for each nodes
        count = Array(nodes.size){ 0 }
        _links.forEachIndexed { index, link ->
            count[link.source.index] += 1
            count[link.target.index] += 1
        }

        // count bias
        bias = Array(_links.size) {.0}
        _links.forEachIndexed { index, link ->
            bias[index] = count[link.source.index].toDouble() / (count[link.source.index] + count[link.target.index])
        }

        initializeStrengths()
    }

    /**
     * When a link strength is not set (default value is Double.NaN) it automatically compute it strength.
     * Strength computed is 1 / the number of links with the given node as a source or target.
     * This default was chosen because it automatically reduces the strength of links connected to
     * heavily-connected nodes, improving stability.
     */
    private fun initializeStrengths() {
        _links.filter { it.strength.isNaN() }
            .forEach { it.strength = 1.0 / min(count[it.source.index], count[it.target.index]) }
    }

    override fun applyForceToNodes(intensity: Double) {
        (0 until iterations).forEach {
            _links.forEachIndexed { index, link ->
                val source = link.source
                val target = link.target

                var x = target.x + target.vx - source.x - source.vx
                if (x == .0) x = jiggle()

                var y = target.y + target.vy - source.y - source.vy
                if (y == .0) y = jiggle()

                var l = sqrt(x * x + y * y)
                l = (l - link.distance) / l * intensity * link.strength
                x *= l
                y *= l

                var b = bias[index]
                target.vx -= x * b
                target.vy -= y * b

                b = 1 - b
                source.vx += x * b
                source.vy += y * b
            }
        }
    }
}