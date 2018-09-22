package io.data2viz.force

import io.data2viz.geom.Point
import io.data2viz.geom.Vector
import io.data2viz.math.random
import io.data2viz.math.EPSILON
import io.data2viz.quadtree.*
import kotlin.math.abs
import kotlin.math.sqrt

fun forceNBody(init: ForceNBody.() -> Unit = {}) = ForceNBody().apply(init)

internal fun jiggle() = (random() - 0.5) * EPSILON

/**
 * The n-body force applies mutually amongst all nodes.
 * It can be used to simulate gravity (attraction) if the strength is positive, or electrostatic charge (repulsion)
 * if the strength is negative.
 * This implementation uses quadtrees and the Barnes–Hut approximation to greatly improve performance;
 * the accuracy can be customized using the theta parameter.
 *
 * Unlike links, which only affect two linked nodes, the charge force is global: every node affects every other node,
 * even if they are on disconnected subgraphs.
 */
class ForceNBody : Force {

    private var theta2 = .81
    private var distanceMin2 = 1.0
    private var distanceMax2 = 10000.0

    private val x = { node: ForceNode -> node.x }
    private val y = { node: ForceNode -> node.y }

    // store the alpha value for the current force(alpha) call
    private var currentAlpha: Double = .0

    // store the current node we're applying force
    private lateinit var currentNode: ForceNode

    /**
     * Sets the Barnes–Hut approximation criterion to the specified number which defaults to 0.9.
     *
     * To accelerate computation, this force implements the Barnes–Hut approximation which takes O(n log n) per
     * application where n is the number of nodes.
     * For each application, a quadtree stores the current node positions; then for each node, the combined force of
     * all other nodes on the given node is computed. For a cluster of nodes that is far away, the charge force can be
     * approximated by treating the cluster as a single, larger node. The theta parameter determines the accuracy of the
     * approximation: if the ratio w / l of the width w of the quadtree cell to the distance l from the node to the
     * cell’s center of mass is less than theta, all nodes in the given cell are treated as a single node rather
     * than individually.
     */
    var theta: Double
        get() = sqrt(theta2)
        set(value) {
            theta2 = value * value
        }

    /**
     * Sets the minimum distance between nodes (which defaults to 1) over which this force is considered.
     * A minimum distance establishes an upper bound on the strength of the force between two nearby nodes, avoiding
     * instability. In particular, it avoids an infinitely-strong force if two nodes are exactly coincident; in this
     * case, the direction of the force is random.
     */
    var distanceMin: Double
        get() = sqrt(distanceMin2)
        set(value) {
            distanceMin2 = value * value
        }

    /**
     * Sets the maximum distance between nodes (which defaults to infinity) over which this force is considered.
     * Specifying a finite maximum distance improves performance and produces a more localized layout.
     */
    var distanceMax: Double
        get() = sqrt(distanceMax2)
        set(value) {
            distanceMax2 = value * value
        }

    /**
     * Sets the strength accessor to the specified function, re-evaluates the strength accessor for each node.
     * A positive value causes nodes to attract each other, similar to gravity, while a negative value causes nodes to
     * repel each other, similar to electrostatic charge.
     *
     * If strength is not specified, returns the current strength accessor, which defaults to { -30.0 }.
     *
     * The strength accessor is invoked for each node in the simulation, being passed the node and its zero-based index.
     * The resulting number is then stored internally, such that the strength of each node is only recomputed when the
     * force is initialized or when this method is called with a new strength, and not on every application of the force.
     */
    var strength: (node: ForceNode, index: Int, nodes: List<ForceNode>) -> Double = { _, _, _ -> -30.0 }
        set(value) {
            field = value
            assignNodes(nodes)
        }

    private var nodes: List<ForceNode> = listOf()
    private val strengths = mutableListOf<Double>()

    override fun assignNodes(nodes: List<ForceNode>) {
        this.nodes = nodes
        strengths.clear()
        nodes.forEachIndexed { index, node ->
            strengths.add(strength(node, index, nodes))
        }
    }

    override fun applyForceToNodes(alpha: Double) {
        currentAlpha = alpha
        val tree = quadtree(x, y, nodes)
        tree.visitAfter(::accumulate)
        nodes.forEachIndexed { index, node ->
            currentNode = node
            tree.visit(::applyForce)
        }
    }

    private fun applyForce(quad: QuadtreeNode<ForceNode>, x0: Double, y0: Double, x1: Double, y1: Double): Boolean {
        if (quad.value == null) return true

        var x: Double = quad.position.x - currentNode.x
        var y: Double = quad.position.y - currentNode.y
        var w = x1 - x0
        var l = x * x + y * y

        // Apply the Barnes-Hut approximation if possible.
        // Limit forces for very close nodes; randomize direction if coincident.
        if (w * w / theta2 < l) {
            if (l < distanceMax2) {
                if (x == .0) {
                    x = jiggle()
                    l += x * x
                }
                if (y == .0) {
                    y = jiggle()
                    l += y * y
                }
                if (l < distanceMin2) l = sqrt(distanceMin2 * l)
                val increment: Double = quad.value!! * currentAlpha / l
                currentNode.vx += x * increment
                currentNode.vy += y * increment
            }

            return true
        }

        // Otherwise, process points directly.
        else if (quad is InternalNode || l >= distanceMax2) return false

        // Limit forces for very close nodes; randomize direction if coincident.
        var newQuad = quad as LeafNode?
        if (newQuad!!.data !== currentNode || newQuad!!.next == null) {
            if (x == .0) {
                x = jiggle()
                l += x * x
            }
            if (y == .0) {
                y = jiggle()
                l += y * y
            }
            if (l < distanceMin2) l = sqrt(distanceMin2 * l)
        }

        do {
            if (newQuad!!.data !== currentNode) {
                w = strengths[newQuad!!.data.index] * currentAlpha / l
                currentNode.vx += x * w
                currentNode.vy += y * w
            }
            newQuad = newQuad!!.next
        } while (newQuad != null)

        return false
    }

    private fun accumulate(quad: QuadtreeNode<ForceNode>, x0: Double, y0: Double, x1: Double, y1: Double) {
        var strength = .0
        var weight = .0

        // For internal nodes, accumulate forces from child quadrants.
        when (quad) {
            is InternalNode -> {
                var x = .0
                var y = .0
                quad.toList().forEach { q ->
                    if (q?.value != null) {
                        val c = abs(q.value!!)
                        strength += q.value!!
                        weight += c
                        x += c * q.position.x
                        y += c * q.position.y
                    }
                }
                quad.position = Point(x / weight, y / weight)
            }

            // For leaf nodes, accumulate forces from coincident quadrants.
            is LeafNode -> {
                var q: LeafNode<ForceNode>? = quad
                q!!.position = Point(q.data.x, q.data.y)
                do {
                    strength += strengths[q!!.data.index]
                    q = q.next
                } while (q != null)
            }
        }

        quad.value = strength
    }
}