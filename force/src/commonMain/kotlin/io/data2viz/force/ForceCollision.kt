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

import io.data2viz.math.Percent
import io.data2viz.math.pct
import io.data2viz.quadtree.*
import kotlin.math.*

@Deprecated("Deprecated", ReplaceWith("forceSimulation { forceCollision { } }", " io.data2viz.force.ForceSimulation"))
public fun <D> forceCollision(init: ForceCollision<D>.() -> Unit): ForceCollision<D> = ForceCollision<D>().apply(init)

/**
 * The collision force treats _nodes as circles with a given radius, rather than points, and prevents _nodes from
 * overlapping. More formally, two _nodes a and b are separated so that the distance between a and b is at least
 * radius(a) + radius(b).
 * To reduce jitter, this is by default a “soft” constraint with a configurable strength and iteration count.
 */
public class ForceCollision<D> internal constructor(): Force<D> {

    private val x = { node: ForceNode<D> -> node.x }
    private val y = { node: ForceNode<D> -> node.y }

    // variables stored during tree parsing for current node
    private var ri: Double = .0
    private var ri2: Double = .0
    private var xi: Double = Double.NaN
    private var yi: Double = Double.NaN
    private lateinit var currentNode: ForceNode<D>

    /**
     * If iterations is specified, sets the number of iterations per application to the specified number, defaults 1.
     * Increasing the number of iterations greatly increases the rigidity of the constraint and avoids partial overlap
     * of _nodes, but also increases the runtime cost to evaluate the force.
     */
    public var iterations: Int = 1
        set(value) {
            field = max(1, value)
        }

    /**
     * Sets the force strength to the specified percentage coerced in the range [0%,100%].
     * Value defaults to 70%.
     * Overlapping _nodes are resolved through iterative relaxation. For each node, the other _nodes that are anticipated
     * to overlap at the next tick (using the anticipated positions ⟨x + vx,y + vy⟩) are determined; the node’s velocity
     * is then modified to push the node out of each overlapping node. The change in velocity is dampened by the force’s
     * strength such that the resolution of simultaneous overlaps can be blended together to find a stable solution.
     */
    public var strength: Percent = 70.pct
        set(value) {
            field = value.coerceToDefault()
        }

    /**
     * Sets the radius accessor to the specified function, re-evaluates the radius accessor which defaults to { 100.0 }
     * for each node.
     *
     * The radius accessor is invoked for each node in the simulation, being passed the node, its zero-based index
     * and the list of _nodes.
     * The resulting number is then stored internally, such that the radius of each node is only recomputed when the
     * force is initialized or when this method is called with a new radius, and not on every application of the force.
     */
    public var radiusGet: ForceNode<D>.() -> Double = { 100.0 }
        set(value) {
            field = value
            assignNodes(_nodes)
        }

    private var _nodes: List<ForceNode<D>> = listOf()
    private var _radiuses = listOf<Double>()

    override fun assignNodes(nodes: List<ForceNode<D>>) {
        _nodes = nodes
        _radiuses = nodes.map(radiusGet)
    }

    override fun applyForceToNodes(intensity: Double) {
        (0 until iterations).forEach {
            val tree = quadtree(x, y, _nodes)
            tree.visitAfter(::prepare)
            _nodes.forEachIndexed { _, node ->
                currentNode = node
                ri = _radiuses[node.index]
                ri2 = ri * ri
                xi = node.x + node.vx
                yi = node.y + node.vy
                tree.visit(::applyForce)
            }
        }
    }

    private fun applyForce(quad: QuadtreeNode<ForceNode<D>>, x0: Double, y0: Double, x1: Double, y1: Double): Boolean {
        val data = if (quad is LeafNode) quad.data else null
        var rj = quad.value!!
        var r = ri + rj
        if (data != null) {
            if (data.index > currentNode.index) {
                var x = xi - data.x - data.vx
                var y = yi - data.y - data.vy
                var l = x * x + y * y
                if (l < (r * r)) {
                    if (x == .0) {
                        x = jiggle()
                        l += x * x
                    }
                    if (y == .0) {
                        y = jiggle()
                        l += y * y
                    }
                    val sqrtl = sqrt(l)
                    l = (r - (sqrtl)) / sqrtl * strength.value
                    x *= l
                    y *= l
                    rj *= rj
                    r = rj / (ri2 + rj)
                    currentNode.vx += x * r
                    currentNode.vy += y * r
                    r = 1 - r
                    data.vx -= x * r
                    data.vy -= y * r
                }
            }
            return false
        }
        return x0 > xi + r || x1 < xi - r || y0 >yi + r || y1 < yi - r
    }

    private fun prepare(quad: QuadtreeNode<ForceNode<D>>, x0: Double, y0: Double, x1: Double, y1: Double) {
        if (quad is LeafNode) {
            quad.value = _radiuses[quad.data.index]
            return
        }
        quad.value = .0
        (quad as InternalNode).toList().forEach { node ->
            if (node?.value != null && node.value!! > quad.value!!) {
                quad.value = node.value
            }
        }
    }
}
