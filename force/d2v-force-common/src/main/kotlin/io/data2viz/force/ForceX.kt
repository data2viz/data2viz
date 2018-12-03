package io.data2viz.force

/**
 * Creates a new positioning force along the x-axis towards the given position x.
 * If x is not specified, it defaults to 0.
 */
class ForceX<D> internal constructor(): Force<D> {

    /**
     * Sets the x-coordinate accessor to the specified function, re-evaluates the x-accessor for each node.
     * If x is not specified, returns the current x-accessor, which defaults to { .0 }
     * The x-accessor is invoked for each node in the simulation, being passed the node and its zero-based index.
     * The resulting number is then stored internally, such that the target x-coordinate of each node is only recomputed
     * when the force is initialized or when this method is called with a new x, and not on every application of the force.
     */
    var xGet: ForceNode<D>.() -> Double = { .0 }
        set(value) {
            field = value
            assignNodes(nodes)
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
    var strengthGet: ForceNode<D>.() -> Double = { 0.1 }
        set(value) {
            field = value
            assignNodes(nodes)
        }

    private var nodes: List<ForceNode<D>> = listOf()
    private val strengths = mutableListOf<Double>()
    private val xz = mutableListOf<Double>()

    override fun assignNodes(nodes: List<ForceNode<D>>) {
        this.nodes = nodes

        xz.clear()
        strengths.clear()

        nodes.forEach {
            xz.add(it.xGet())
            strengths.add(it.strengthGet())
        }
    }

    override fun applyForceToNodes(alpha: Double) {
        nodes.forEachIndexed { index, node ->
            node.vx += (xz[index] - node.x) * strengths[index] * alpha
        }
    }
}