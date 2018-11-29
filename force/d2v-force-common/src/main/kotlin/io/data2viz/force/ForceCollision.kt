package io.data2viz.force

import io.data2viz.math.pct
import io.data2viz.quadtree.*
import kotlin.math.max
import kotlin.math.sqrt

/**
 * The collision force treats nodes as circles with a given radius, rather than points, and prevents nodes from
 * overlapping. More formally, two nodes a and b are separated so that the distance between a and b is at least
 * radius(a) + radius(b).
 * To reduce jitter, this is by default a “soft” constraint with a configurable strength and iteration count.
 */
class ForceCollision<D> internal constructor(): Force<D> {

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
     * of nodes, but also increases the runtime cost to evaluate the force.
     */
    var iterations = 1
        set(value) {
            field = max(1, value)
        }

    /**
     * Sets the force strength to the specified percentage coerced in the range [0%,100%].
     * Value defaults to 70%.
     * Overlapping nodes are resolved through iterative relaxation. For each node, the other nodes that are anticipated
     * to overlap at the next tick (using the anticipated positions ⟨x + vx,y + vy⟩) are determined; the node’s velocity
     * is then modified to push the node out of each overlapping node. The change in velocity is dampened by the force’s
     * strength such that the resolution of simultaneous overlaps can be blended together to find a stable solution.
     */
    var strength = 70.pct
        set(value) {
            field = value.coerceToDefault()
        }

    /**
     * Sets the radius accessor to the specified function, re-evaluates the radius accessor which defaults to { 100.0 }
     * for each node.
     *
     * The radius accessor is invoked for each node in the simulation, being passed the node, its zero-based index
     * and the list of nodes.
     * The resulting number is then stored internally, such that the radius of each node is only recomputed when the
     * force is initialized or when this method is called with a new radius, and not on every application of the force.
     */
    var radiusAccessor: (node: ForceNode<D>, index: Int, nodes: List<ForceNode<D>>) -> Double = { _, _, _ -> 100.0 }
        set(value) {
            field = value
            assignNodes(nodes)
        }

    private var nodes: List<ForceNode<D>> = listOf()
    private val radiuses = mutableListOf<Double>()

    override fun assignNodes(nodes: List<ForceNode<D>>) {
        this.nodes = nodes
        radiuses.clear()
        nodes.forEachIndexed { index, node ->
            radiuses.add(radiusAccessor(node, index, nodes))
        }
    }

    override fun applyForceToNodes(alpha: Double) {
        (0 until iterations).forEach {
            val tree = quadtree(x, y, nodes)
            tree.visitAfter(::prepare)
            nodes.forEachIndexed { index, node ->
                currentNode = node
                ri = radiuses[node.index]
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
            quad.value = radiuses[quad.data.index]
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