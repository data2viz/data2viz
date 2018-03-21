package io.data2viz.quadtree

import io.data2viz.core.Extent

/**
 * Visits each node in the quadtree in pre-order traversal, invoking the specified callback with arguments
 * node, x0, y0, x1, y1 for each node, where node is the node being visited, ⟨x0, y0⟩ are the lower bounds of the
 * node, and ⟨x1, y1⟩ are the upper bounds.
 * (Assuming that positive x is right and positive y is down, as is typically the case in Canvas and SVG,
 * ⟨x0, y0⟩ is the top-left corner and ⟨x1, y1⟩ is the lower-right corner; however, the coordinate system is arbitrary,
 * so more formally x0 <= x1 and y0 <= y1).
 *
 * If the callback returns true for a given node, then the children of that node are not visited; otherwise,
 * all child nodes are visited. This can be used to quickly visit only parts of the tree, for example when
 * using the Barnes–Hut approximation.
 * Note, however, that child quadrants are always visited in sibling order: top-left, top-right, bottom-left,
 * bottom-right. In cases such as search, visiting siblings in a specific order may be faster.
 */
fun <D> Quadtree<D>.visit(callback: (QuadtreeNode<D>, Double, Double, Double, Double) -> Boolean) {
    val quads = mutableListOf<Quad<D>>()
    var node = root
    if (node != null) quads.add(Quad(node, extent.copy()))
    while (quads.isNotEmpty()) {
        val q = quads.removeAt(quads.lastIndex)
        node = q.node
        val ext = q.extent
        val x0 = ext.x0
        val y0 = ext.y0
        val x1 = ext.x1
        val y1 = ext.y1
        if (!callback(node!!, x0, y0, x1, y1) && node is InternalNode) {
            val xm = (x0 + x1) / 2
            val ym = (y0 + y1) / 2
            if (node.SW_3 != null) quads.add(Quad(node.SW_3, Extent(xm, ym, x1, y1)))
            if (node.SE_2 != null) quads.add(Quad(node.SE_2, Extent(x0, ym, xm, y1)))
            if (node.NW_1 != null) quads.add(Quad(node.NW_1, Extent(xm, y0, x1, ym)))
            if (node.NE_0 != null) quads.add(Quad(node.NE_0, Extent(x0, y0, xm, ym)))
        }
    }
}

/**
 * Visits each node in the quadtree in post-order traversal, invoking the specified callback with arguments
 * node, x0, y0, x1, y1 for each node, where node is the node being visited, ⟨x0, y0⟩ are the lower bounds of the node,
 * and ⟨x1, y1⟩ are the upper bounds.
 * (Assuming that positive x is right and positive y is down, as is typically the case in Canvas and SVG, ⟨x0, y0⟩
 * is the top-left corner and ⟨x1, y1⟩ is the lower-right corner; however, the coordinate system is arbitrary,
 * so more formally x0 <= x1 and y0 <= y1.) Returns root.
 */
fun <D> Quadtree<D>.visitAfter(callback: (QuadtreeNode<D>, Double, Double, Double, Double) -> Unit) {
    val quads = mutableListOf<Quad<D>>()
    val next = mutableListOf<Quad<D>>()
    if (root != null) quads.add(Quad(root, extent.copy()))
    while (quads.isNotEmpty()) {
        val q = quads.removeAt(quads.lastIndex)
        val node = q.node
        if (node is InternalNode) {
            val ext = q.extent
            val x0 = ext.x0
            val y0 = ext.y0
            val x1 = ext.x1
            val y1 = ext.y1
            val xm = (x0 + x1) / 2
            val ym = (y0 + y1) / 2
            if (node.NE_0 != null) quads.add(Quad(node.NE_0, Extent(x0, y0, xm, ym)))
            if (node.NW_1 != null) quads.add(Quad(node.NW_1, Extent(xm, y0, x1, ym)))
            if (node.SE_2 != null) quads.add(Quad(node.SE_2, Extent(x0, ym, xm, y1)))
            if (node.SW_3 != null) quads.add(Quad(node.SW_3, Extent(xm, ym, x1, y1)))
        }
        next.add(q)
    }
    while (next.isNotEmpty()) {
        val q = next.removeAt(next.lastIndex)
        if (q.node != null) callback(q.node, q.extent.x0, q.extent.y0, q.extent.x1, q.extent.y1)
    }
}