package io.data2viz.force

import io.data2viz.geom.Point

fun forceCenter(center: Point) = ForceCenter(center)

// TODO : strength
// TODO : doc
class ForceCenter(val center: Point = Point(.0, .0)) : Force {

    private val _nodes = mutableListOf<ForceNode>()

    override fun initialize(nodes: List<ForceNode>) {
        _nodes.addAll(nodes)
    }

    override fun invoke(alpha: Double) {
        val size = _nodes.size.toDouble()

        var sx = .0
        var sy = .0

        _nodes.forEach { node ->
            sx += node.position.x
            sy += node.position.y
        }

        sx = sx / size - center.x
        sy = sy / size - center.y

        _nodes.forEach { node ->
            node.position = Point(node.position.x - sx, node.position.y - sy)
        }
    }
}