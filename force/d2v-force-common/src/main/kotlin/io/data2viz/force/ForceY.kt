package io.data2viz.force

import io.data2viz.math.*

@Deprecated("Deprecated", ReplaceWith("forceSimulation { forceY { } }", " io.data2viz.force.ForceSimulation"))
fun <D> forceY(init: ForceY<D>.() -> Unit) = ForceY<D>().apply(init)

/**
 * Creates a new positioning force along the y-axis towards the given position y.
 * If y is not specified, it defaults to 0.
 */
class ForceY<D> internal constructor(): Force<D> {

    /**
     * Sets the y-coordinate accessor to the specified function, re-evaluates the y-accessor for each node.
     * If y is not specified, returns the current y-accessor, which defaults to { .0 }
     * The y-accessor is invoked for each node in the simulation, being passed the node and its zero-based index.
     * The resulting number is then stored internally, such that the target y-coordinate of each node is only recomputed
     * when the force is initialized or when this method is called with a new y, and not on every application of the force.
     */
    var yGet: ForceNode<D>.() -> Double = { .0 }
        set(value) {
            field = value
            assignNodes(_nodes)
        }

    /**
     * Sets the strength accessor to the specified function, re-evaluates the strength accessor for each node.
     * The strength determines how much to increment the node’s y-velocity: (y - node.y) × strength.
     * For example, a value of 0.1 indicates that the node should move a tenth of the way from its current y-position
     * to the target y-position with each application. Higher values moves nodes more quickly to the target position,
     * often at the expense of other forces or constraints. A value outside the range [0,1] is not recommended.
     *
     * If strength is not specified, returns the current strength accessor, which defaults to { 0.1 }.
     *
     * The strength accessor is invoked for each node in the simulation, being passed the node and its zero-based index.
     * The resulting number is then stored internally, such that the strength of each node is only recomputed when the
     * force is initialized or when this method is called with a new strength, and not on every application of the force.
     */
    var strengthGet: ForceNode<D>.() -> Percent = { 10.pct }
        set(value) {
            field = value
            assignNodes(_nodes)
        }

    private var _nodes = listOf<ForceNode<D>>()
    private var _strengths = listOf<Double>()
    private var _y = listOf<Double>()

    override fun assignNodes(nodes: List<ForceNode<D>>) {
        _nodes = nodes

        _y = nodes.map(yGet)
        _strengths = nodes.map { it.strengthGet().value }
    }

    override fun applyForceToNodes(intensity: Double) {
        _nodes.forEachIndexed { index, node ->
            node.vy += (_y[index] - node.y) * _strengths[index] * intensity
        }
    }
}