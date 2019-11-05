/*
 * Copyright (c) 2018-2019. data2viz sàrl.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 *
 */

package io.data2viz.quadtree

/**
 * Adds the specified datum to the quadtree, deriving its coordinates ⟨x,y⟩ using the current x- and y-accessors.
 * If the new point is outside the current extent of the quadtree, the quadtree is automatically expanded to
 * cover the new point.
 */
fun <D> Quadtree<D>.add(datum: D) {
    val x = x(datum)
    val y = y(datum)
    cover(x, y)
    _add(x, y, datum)
}

private fun <D> Quadtree<D>._add(x: Double, y: Double, datum: D) {
    if (x.isNaN() || y.isNaN()) return

    var node = root
    val leaf = LeafNode(datum, null)
    var x0 = extent.x0
    var y0 = extent.y0
    var x1 = extent.x1
    var y1 = extent.y1

    var index = 0
    var jndex: Int
    var parent: InternalNode<D>? = null
    var xp = Double.NaN
    var yp = Double.NaN

    // If the tree is empty, initialize the root as a leaf.
    if (node == null) {
        root = leaf
        return
    }

    // Find the existing leaf for the new point, or add it.
    while (node is InternalNode) {
        val xm = (x0 + x1) / 2
        val right = x >= xm
        if (right) x0 = xm else x1 = xm

        val ym = (y0 + y1) / 2
        val bottom = y >= ym
        if (bottom) y0 = ym else y1 = ym

        parent = node
        index = bottom.toInt() shl 1 or right.toInt()
        node = getNodeFromIndex(node, index)
        if (node == null) {
            setNodeFromIndex(parent, index, leaf)
            return
        }
    }

    // Is the new point is exactly coincident with the existing point?
    if (node is LeafNode<D>) {
        xp = x(node.data)
        yp = y(node.data)
        if (x == xp && y == yp) {
            leaf.next = node
            if (parent != null) setNodeFromIndex(parent, index, leaf) else root = leaf
            return
        }
    }

// Otherwise, split the leaf node until the old and new point are separated.
    do {
        if (parent != null) {
            setNodeFromIndex(parent, index, InternalNode())
            parent = getNodeFromIndex(parent, index) as InternalNode<D>
        } else {
            root = InternalNode()
            parent = root as InternalNode<D>
        }

        val xm = (x0 + x1) / 2
        val right = x >= xm
        if (right) x0 = xm else x1 = xm

        val ym = (y0 + y1) / 2
        val bottom = y >= ym
        if (bottom) y0 = ym else y1 = ym

        index = bottom.toInt() shl 1 or right.toInt()
        jndex = (yp >= ym).toInt() shl 1 or (xp >= xm).toInt()
    } while (index == jndex)

    setNodeFromIndex(parent!!, jndex, node!!)
    setNodeFromIndex(parent, index, leaf)
}

fun <D> Quadtree<D>.addAll(data: List<D>) {
    val xz: Array<Double> = Array(data.size) { x(data[it]) }
    val yz: Array<Double> = Array(data.size) { y(data[it]) }
    var x0 = Double.POSITIVE_INFINITY
    var y0 = Double.POSITIVE_INFINITY
    var x1 = Double.NEGATIVE_INFINITY
    var y1 = Double.NEGATIVE_INFINITY

    // Compute the points and their extent.
    // TODO min/max ?
    (0 until data.size).forEach { index ->
        val cx = xz[index]
        val cy = yz[index]

        if (cx.isNaN() || cy.isNaN()) return@forEach

        if (cx < x0) x0 = cx
        if (cx > x1) x1 = cx
        if (cy < y0) y0 = cy
        if (cy > y1) y1 = cy
    }

    // If there were no (valid) points, inherit the existing extent.
    if (x1 < x0) {
        x0 = extent.x0
        x1 = extent.x1
    }
    if (y1 < y0) {
        y0 = extent.y0
        y1 = extent.y1
    }

    // Expand the tree to cover the new points.
    cover(x0, y0)
    cover(x1, y1)

    // Add the new points.
    data.forEachIndexed { index, datum ->
        _add(xz[index], yz[index], datum)
    }
}