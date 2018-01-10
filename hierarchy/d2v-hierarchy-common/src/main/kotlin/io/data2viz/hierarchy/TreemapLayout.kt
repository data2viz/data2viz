package io.data2viz.hierarchy

import io.data2viz.hierarchy.treemap.treemapSquarify

data class Row(
    val value: Double,
    val dice:Boolean,
    val children:List<Node<*>>
)

class TreemapLayout<D> {

    val constantZero: (Node<D>) -> Double = { .0 }

    var tilingMethod = ::treemapSquarify
    var round = false
    var dx = 1.0
    var dy = 1.0
    var paddingStack = mutableListOf(.0)
    var paddingInner: (Node<D>) -> Double = constantZero
    var paddingTop: (Node<D>) -> Double = constantZero
    var paddingRight: (Node<D>) -> Double = constantZero
    var paddingBottom: (Node<D>) -> Double = constantZero
    var paddingLeft: (Node<D>) -> Double = constantZero

    /**
     * Introduced by Ben Shneiderman in 1991, a treemap recursively subdivides area into rectangles according to
     * each node’s associated value.
     * Treemap implementation supports an extensible tiling method: the default squarified method seeks to generate
     * rectangles with a golden aspect ratio; this offers better readability and size estimation than slice-and-dice,
     * which simply alternates between horizontal and vertical subdivision by depth.
     *
     * Lays out the specified root hierarchy, assigning the following properties on root and its descendants:
     *
     * - node.x0 - the left edge of the rectangle
     * - node.y0 - the top edge of the rectangle
     * - node.x1 - the right edge of the rectangle
     * - node.y1 - the bottom edge of the rectangle
     *
     * You must call root.sum before passing the hierarchy to the treemap layout so each node as a positive value.
     * // TODO force a call on root.sum ?
     * You probably also want to call root.sort to order the hierarchy before computing the layout.
     */
    fun treemap(root: Node<D>): Node<D> {

        // TODO : require a check on each node to verify that value != null and >0 ? (root.sum has been passed) ?

        paddingStack = MutableList(root.height, { .0 })
        root.x0 = .0
        root.y0 = .0
        root.x1 = dx
        root.y1 = dy

        root.eachBefore(::positionNode)

        return root
    }

    private fun positionNode(node: Node<D>) {
        var p = paddingStack[node.depth]
        var x0 = node.x0 + p
        var y0 = node.y0 + p
        var x1 = node.x1 - p
        var y1 = node.y1 - p

        if (x1 < x0) {
            val mid = (x0 + x1) / 2
            x0 = mid
            x1 = mid
        }
        if (y1 < y0) {
            val mid = (y0 + y1) / 2
            y0 = mid
            y1 = mid
        }
        node.x0 = x0
        node.y0 = y0
        node.x1 = x1
        node.y1 = y1

        if (node.children.isNotEmpty()) {
            paddingStack[node.depth + 1] = paddingInner(node) / 2
            p = paddingInner(node) / 2
            x0 += paddingLeft(node) - p
            y0 += paddingTop(node) - p
            x1 -= paddingRight(node) - p
            y1 -= paddingBottom(node) - p
            if (x1 < x0) {
                val mid = (x0 + x1) / 2
                x0 = mid
                x1 = mid
            }
            if (y1 < y0) {
                val mid = (y0 + y1) / 2
                y0 = mid
                y1 = mid
            }
            tilingMethod(node, x0, y0, x1, y1)
        }
    }
}