package io.data2viz.force

import io.data2viz.geom.Point
import io.data2viz.geom.Vector
import io.data2viz.math.EPSILON
import kotlin.math.sqrt

fun forceRadial(init:ForceRadial.()->Unit) = ForceRadial().apply(init)

/**
 * Creates a new positioning force towards a circle of the specified radius centered at "center" Point.
 */
class ForceRadial : Force {

    /**
     * Sets the circle radius to the specified function, re-evaluates the radius accessor for each node.
     * The radius accessor is invoked for each node in the simulation, being passed the node and its zero-based index.
     * The resulting number is then stored internally, such that the radius of each node is only recomputed when the
     * force is initialized or when this method is called with a new radius, and not on every application of the force.
     */
    var radius: (node: ForceNode, index: Int, nodes: List<ForceNode>) -> Double = { _, _, _ -> 100.0 }
        set(value) {
            field = value
            assignNodes(nodes)
        }

    /**
     * Sets the strength accessor to the specified function, re-evaluates the strength accessor for each node.
     * The strength determines how much to increment the node’s x- and y-velocity.
     * For example, the default value of 0.1 indicates that the node should move a tenth of the way from its current
     * position to the closest point on the circle with each application.
     * Higher values moves nodes more quickly to the target position, often at the expense of other forces or constraints.
     * A value outside the range [0,1] is not recommended.
     *
     * The strength accessor is invoked for each node in the simulation, being passed the node and its zero-based index.
     * The resulting number is then stored internally, such that the strength of each node is only recomputed when the
     * force is initialized or when this method is called with a new strength, and not on every application of the force.
     */
    var strength: (node: ForceNode, index: Int, nodes: List<ForceNode>) -> Double = { _, _, _ -> 0.1 }
        set(value) {
            field = value
            assignNodes(nodes)
        }

    /**
     * Sets the coordinate of the circle center which defaults to (0, 0).
     */
    var center: (node: ForceNode, index: Int, nodes: List<ForceNode>) -> Point = { _, _, _ -> defaultCenter }
        set(value) {
            field = value
            assignNodes(nodes)
        }
    private val defaultCenter = Point(.0, .0)

    private var nodes: List<ForceNode> = listOf()
    private val strengths = mutableListOf<Double>()
    private val centers = mutableListOf<Point>()
    private val radiuses = mutableListOf<Double>()

    override fun assignNodes(nodes: List<ForceNode>) {
        this.nodes = nodes

        radiuses.clear()
        centers.clear()
        strengths.clear()

        nodes.forEachIndexed { index, node ->
            radiuses.add(radius(node, index, nodes))
            centers.add(center(node, index, nodes))
            strengths.add(strength(node, index, nodes))
        }
    }

    override fun applyForceToNodes(alpha: Double) {
        nodes.forEachIndexed { index, node ->
            var dx = node.x - centers[index].x
            if (dx == .0) dx = EPSILON
            var dy = node.y - centers[index].y
            if (dy == .0) dy = EPSILON

            val r = sqrt(dx * dx + dy * dy)
            val k = (radiuses[index] - r) * strengths[index] * alpha / r

            node.vx += dx * k
            node.vy += dy * k
        }
    }
}