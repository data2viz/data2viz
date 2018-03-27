package io.data2viz.force

import io.data2viz.core.Point
import io.data2viz.core.Speed
import io.data2viz.quadtree.*
import kotlin.math.sqrt

fun forceCollision(init: ForceCollision.() -> Unit) = ForceCollision().apply(init)

/**
 * The collision force treats nodes as circles with a given radius, rather than points, and prevents nodes from
 * overlapping. More formally, two nodes a and b are separated so that the distance between a and b is at least
 * radius(a) + radius(b).
 * To reduce jitter, this is by default a “soft” constraint with a configurable strength and iteration count.
 */
class ForceCollision : Force {

    private val x = { node: ForceNode -> node.position.x }
    private val y = { node: ForceNode -> node.position.y }

    // variables stored during tree parsing for current node
    private var ri: Double = .0
    private var ri2: Double = .0
    private lateinit var pointi: Point
    private lateinit var currentNode: ForceNode

    /**
     * If iterations is specified, sets the number of iterations per application to the specified number.
     * Increasing the number of iterations greatly increases the rigidity of the constraint and avoids partial overlap
     * of nodes, but also increases the runtime cost to evaluate the force.
     */
    var iterations = 1

    /**
     * Sets the force strength to the specified number in the range [0,1] which defaults to 0.7.
     * Overlapping nodes are resolved through iterative relaxation. For each node, the other nodes that are anticipated
     * to overlap at the next tick (using the anticipated positions ⟨x + vx,y + vy⟩) are determined; the node’s velocity
     * is then modified to push the node out of each overlapping node. The change in velocity is dampened by the force’s
     * strength such that the resolution of simultaneous overlaps can be blended together to find a stable solution.
     */
    var strength: Double = .7

    /**
     * Sets the radius accessor to the specified function, re-evaluates the radius accessor which defaults to { 1.0 }
     * for each node.
     *
     * The radius accessor is invoked for each node in the simulation, being passed the node and its zero-based index.
     * The resulting number is then stored internally, such that the radius of each node is only recomputed when the
     * force is initialized or when this method is called with a new radius, and not on every application of the force.
     */
    var radius: (node: ForceNode, index: Int, nodes: List<ForceNode>) -> Double = { _, _, _ -> 100.0 }
        set(value) {
            field = value
            initialize(nodes)
        }

    private var nodes: List<ForceNode> = listOf()
    private val radiuses = mutableListOf<Double>()

    override fun initialize(nodes: List<ForceNode>) {
        this.nodes = nodes
        radiuses.clear()
        nodes.forEachIndexed { index, node ->
            radiuses.add(radius(node, index, nodes))
        }
    }

    override fun invoke(alpha: Double) {
        (0 until iterations).forEach {
            val tree = quadtree(x, y, nodes)
            tree.visitAfter(::prepare)
            nodes.forEachIndexed { index, node ->
                currentNode = node
                ri = radiuses[node.index]
                ri2 = ri * ri
                pointi = node.position.plus(node.velocity)
                tree.visit(::applyForce)
            }
        }
    }

    private fun applyForce(quad: QuadtreeNode<ForceNode>, x0: Double, y0: Double, x1: Double, y1: Double): Boolean {
        val data = if (quad is LeafNode) quad.data else null
        var rj = quad.value!!
        var r = ri + rj
        if (data != null) {
            if (data.index > currentNode.index) {
                var x = pointi.x - data.position.x - data.velocity.vx
                var y = pointi.y - data.position.y - data.velocity.vy
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
                    l = (r - (sqrtl)) / sqrtl * strength
                    x *= l
                    y *= l
                    rj *= rj
                    r = rj / (ri2 + rj)
                    currentNode.velocity += Speed(x * r, y * r)
                    r = 1 - r
                    data.velocity -= Speed(x * r, y * r)
                }
            }
            return false
        }
        return x0 > pointi.x + r || x1 < pointi.x - r || y0 > pointi.y + r || y1 < pointi.y - r
    }

    private fun prepare(quad: QuadtreeNode<ForceNode>, x0: Double, y0: Double, x1: Double, y1: Double) {
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