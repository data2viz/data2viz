package io.data2viz.force

import io.data2viz.geom.Point

@Deprecated("Deprecated", ReplaceWith("forceSimulation { forceCenter { } }", " io.data2viz.force.ForceSimulation"))
fun <D> forceCenter(center: Point) = ForceCenter<D>().apply { this.center = center }

/**
 * The centering force translates nodes uniformly so that the mean position of all nodes
 * (the center of mass if all nodes have equal weight) is at the given position ⟨x,y⟩.
 * This force modifies the positions of nodes on each application; it does not modify velocities,
 * as doing so would typically cause the nodes to overshoot and oscillate around the desired center.
 * This force helps keeps nodes in the center of the viewport, and unlike the positioning force,
 * it does not distort their relative positions.
 */
class ForceCenter<D> internal constructor() : Force<D> {

    private var _nodes = listOf<ForceNode<D>>()

    var center: Point = Point(.0, .0)

    override fun assignNodes(nodes: List<ForceNode<D>>) {
        _nodes = nodes
    }

    override fun applyForceToNodes(alpha: Double) {
        val size = _nodes.size.toDouble()

        var sx = .0
        var sy = .0

        _nodes.forEach { node ->
            sx += node.x
            sy += node.y
        }

        sx = sx / size - center.x
        sy = sy / size - center.y

        _nodes.forEach { node ->
            node.x = node.x - sx
            node.y = node.y - sy
        }
    }
}