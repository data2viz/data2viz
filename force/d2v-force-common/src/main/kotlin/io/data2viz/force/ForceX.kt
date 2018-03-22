package io.data2viz.force

import io.data2viz.core.Speed

fun forceX(init:ForceX.()->Unit) = ForceX().apply(init)

/**
 * Creates a new positioning force along the x-axis towards the given position x.
 * If x is not specified, it defaults to 0.
 */
class ForceX : Force {

    /**
     * Sets the x-coordinate accessor to the specified function, re-evaluates the x-accessor for each node.
     * If x is not specified, returns the current x-accessor, which defaults to { .0 }
     * The x-accessor is invoked for each node in the simulation, being passed the node and its zero-based index.
     * The resulting number is then stored internally, such that the target x-coordinate of each node is only recomputed
     * when the force is initialized or when this method is called with a new x, and not on every application of the force.
     */
    var x: (node: ForceNode, index: Int, nodes: List<ForceNode>) -> Double = { _, _, _ -> .0 }
        set(value) {
            field = value
            initialize(nodes)
        }

    /**
     * Sets the strength accessor to the specified function, re-evaluates the strength accessor for each node.
     * The strength determines how much to increment the node’s x-velocity: (x - node.x) × strength.
     * For example, a value of 0.1 indicates that the node should move a tenth of the way from its current x-position
     * to the target x-position with each application. Higher values moves nodes more quickly to the target position,
     * often at the expense of other forces or constraints. A value outside the range [0,1] is not recommended.
     *
     * If strength is not specified, returns the current strength accessor, which defaults to { 0.1 }.
     *
     * The strength accessor is invoked for each node in the simulation, being passed the node and its zero-based index.
     * The resulting number is then stored internally, such that the strength of each node is only recomputed when the
     * force is initialized or when this method is called with a new strength, and not on every application of the force.
     */
    var strength: (node: ForceNode, index: Int, nodes: List<ForceNode>) -> Double = { _, _, _ -> 0.1 }
        set(value) {
            field = value
            initialize(nodes)
        }

    private var nodes: List<ForceNode> = listOf()
    private val strengths = mutableListOf<Double>()
    private val xz = mutableListOf<Double>()

    override fun initialize(nodes: List<ForceNode>) {
        this.nodes = nodes

        xz.clear()
        strengths.clear()

        nodes.forEachIndexed { index, node ->
            xz.add(x(node, index, nodes))
            strengths.add(strength(node, index, nodes))
        }
    }

    override fun invoke(alpha: Double) {
        nodes.forEachIndexed { index, node ->
            node.velocity = Speed((xz[index] - node.position.x) * strengths[index] * alpha, node.velocity.vy)
        }
    }
}