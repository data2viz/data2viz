package io.data2viz.hierarchy.treemap

import io.data2viz.hierarchy.Row

fun treemapDice(parent: Row, x0: Double, y0: Double, x1: Double, y1: Double) {
    var newX = x0
    val nodes = parent.children
    var i = 0
    val n = nodes.size
    val k = (parent.value != .0) && ((x1 - newX) / parent.value != .0)

    while (i < n) {
        val node = nodes[i]

        node.y0 = y0
        node.y1 = y1
        node.x0 = newX
        newX += if (k) node.value!! else .0
        node.x1 = newX
        i++
    }
}