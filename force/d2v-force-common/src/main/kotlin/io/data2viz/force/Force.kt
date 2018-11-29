package io.data2viz.force

import io.data2viz.math.EPSILON
import kotlin.random.Random

fun <D> forceSimulation(init: ForceSimulation<D>.() -> Unit) = ForceSimulation<D>().apply {
    init()
}

object Forces {

    fun <D> forceX(init: ForceX<D>.() -> Unit = {}) = ForceX<D>().apply(init)
    fun <D> forceY(init: ForceY<D>.() -> Unit = {}) = ForceY<D>().apply(init)
    fun <D> forceRadial(init: ForceRadial<D>.() -> Unit) = ForceRadial<D>().apply(init)
    fun <D> forceNBody(init: ForceNBody<D>.() -> Unit = {}) = ForceNBody<D>().apply(init)
    fun <D> forceCollision(init: ForceCollision<D>.() -> Unit) = ForceCollision<D>().apply(init)
    fun <D> forceCenter(init: ForceCenter<D>.() -> Unit) = ForceCenter<D>().apply(init)

    /**
     * Todo evaluate having a signature with a fixed list of links and one with an accessor from nodes
     */
    fun <D> forceLink(init: ForceLink<D>.() -> Unit = {}) = ForceLink<D>().apply(init)
}

internal fun jiggle() = (Random.nextDouble() - 0.5) * EPSILON

interface Force<D> {

    /**
     * Assigns nodes to this force.
     * This method is called when a force is bound to a simulation via simulation.force and when the simulation’s
     * nodes change via simulation.nodes.
     * A force may perform necessary work during initialization, such as evaluating per-node parameters, to avoid
     * repeatedly performing work during each application of the force.
     *
     * Todo should be internal. No use outside of package
     */
    fun assignNodes(nodes: List<ForceNode<D>>)

    /**
     * Applies this force, optionally observing the specified alpha.
     * Typically, the force is applied to the array of nodes previously passed to force.assignNodes, however, some
     * forces may apply to a subset of nodes, or behave differently.
     * For example, forceLink applies to the source and target of each link.
     */
    fun applyForceToNodes(alpha: Double)
}