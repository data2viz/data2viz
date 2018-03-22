package io.data2viz.quadtree

import io.data2viz.core.Extent
import kotlin.math.sqrt

/**
 * Returns the datum closest to the position ⟨x,y⟩ with the given search radius. If radius is not specified,
 * it defaults to infinity. If there is no datum within the search area, returns null.
 * TODO : check tests value must be === searched value
 */
fun <D> Quadtree<D>.find(x: Double, y: Double, radius: Double = Double.POSITIVE_INFINITY): D? {

    var data: D? = null
    var newRadius = radius

    var x0 = extent.x0
    var y0 = extent.y0
    var x3 = extent.x1
    var y3 = extent.y1

    val quads = mutableListOf<Quad<D>>()
    var node = root

    if (node != null) quads.add(Quad(node, x0, y0, x3, y3))
    if (newRadius < Double.POSITIVE_INFINITY) {
        x0 = x - newRadius
        y0 = y - newRadius
        x3 = x + newRadius
        y3 = y + newRadius
        newRadius *= newRadius
    }

    while (quads.isNotEmpty()) {

        var quad = quads.removeAt(quads.lastIndex)

        // Stop searching if this quadrant can’t contain a closer node.
        node = quad.node
        val x1 = quad.x0
        val y1 = quad.y0
        val x2 = quad.x1
        val y2 = quad.y1

        if (node == null || x1 > x3 || y1 > y3 || x2 < x0 || y2 < y0) continue

        // Bisect the current quadrant.
        if (node is InternalNode) {
            val xm = (x1 + x2) / 2
            val ym = (y1 + y2) / 2

            quads.add(Quad(node.SW_3, xm, ym, x2, y2))
            quads.add(Quad(node.SE_2, x1, ym, xm, y2))
            quads.add(Quad(node.NW_1, xm, y1, x2, ym))
            quads.add(Quad(node.NE_0, x1, y1, xm, ym))

            // Visit the closest quadrant first.
            val i = (y >= ym).toInt() shl 1 or (x >= xm).toInt()
            if (i != 0) {
                quad = quads[quads.lastIndex]
                quads[quads.lastIndex] = quads[quads.lastIndex - i]
                quads[quads.lastIndex - i] = quad
            }
        }

        // Visit this point. (Visiting coincident points isn’t necessary!)
        else {
            val dx = x - x((node as LeafNode).data)
            val dy = y - y(node.data)
            val d2 = dx * dx + dy * dy
            if (d2 < newRadius) {
                newRadius = d2
                val d = sqrt(newRadius)
                x0 = x - d
                y0 = y - d
                x3 = x + d
                y3 = y + d
                data = node.data
            }
        }
    }

    return data
}