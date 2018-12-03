package io.data2viz.force

import io.data2viz.geom.*
import io.data2viz.math.*
import kotlin.math.sqrt

@Deprecated("Deprecated", ReplaceWith("forceSimulation { forceRadial { } }", " io.data2viz.force.ForceSimulation"))
fun <D> forceRadial(init: ForceRadial<D>.() -> Unit) = ForceRadial<D>().apply(init)

/**
 * Creates a new positioning force towards a circle of the specified radius centered at "center" Point.
 */
class ForceRadial<D> internal constructor(): Force<D> {

    /**
     * Sets the circle radius to the specified function, re-evaluates the radius accessor for each node.
     * The radius accessor is invoked for each node in the simulation, being passed the node and its zero-based index.
     * The resulting number is then stored internally, such that the radius of each node is only recomputed when the
     * force is initialized or when this method is called with a new radius, and not on every application of the force.
     */
    var radiusGet: ForceNode<D>.() -> Double = { 100.0 }
        set(value) {
            field = value
            assignNodes(nodes)
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
    var strengthGet: ForceNode<D>.() -> Percent = { 10.pct }
        set(value) {
            field = value
            assignNodes(nodes)
        }

    /**
     * Sets the coordinate of the circle center which defaults to (0, 0).
     */
    var centerGet: ForceNode<D>.() -> Point = { defaultCenter }
        set(value) {
            field = value
            assignNodes(nodes)
        }
    private val defaultCenter = Point(.0, .0)

    private var nodes: List<ForceNode<D>> = listOf()
    private val strengths = mutableListOf<Double>()
    private val centers = mutableListOf<Point>()
    private val radiuses = mutableListOf<Double>()

    override fun assignNodes(nodes: List<ForceNode<D>>) {
        this.nodes = nodes

        radiuses.clear()
        centers.clear()
        strengths.clear()

        nodes.forEach {
            radiuses.add(it.radiusGet())
            centers.add(it.centerGet())
            strengths.add(it.strengthGet().value)
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