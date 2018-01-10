package io.data2viz.hierarchy.treemap

import io.data2viz.hierarchy.Row

fun treemapSlice(parent: Row, x0: Double, y0: Double, x1: Double, y1: Double) {
    var newY = y0
    val nodes = parent.children
    var i = 0
    val n = nodes.size
    val k = (parent.value != .0) && ((y1 - newY) / parent.value != .0)

    while (i < n) {
        val node = nodes[i]

        node.x0 = x0
        node.x1 = x1
        node.y0 = newY
        newY += if (k) node.value!! else .0
        node.y1 = newY
        i++
    }
}