package io.data2viz.quadtree

/**
 * Removes the specified datum to the quadtree, deriving its coordinates ⟨x,y⟩ using the current x- and y-accessors.
 * If the specified datum does not exist in this quadtree, this method does nothing.
 * TODO : check tests value must be === removed value
 */
fun <D> Quadtree<D>.remove(datum: D) {
    val x = x(datum)
    val y = y(datum)

    if (x.isNaN() || y.isNaN()) return

    var node = root
    var x0 = extent.x0
    var y0 = extent.y0
    var x1 = extent.x1
    var y1 = extent.y1

    var parent: InternalNode<D>? = null
    var retainer: InternalNode<D>? = null
    var previous: QuadtreeNode<D>? = null
    var index = 0
    var jndex = 0


    // If the tree is empty, initialize the root as a leaf.
    if (node == null) return

    // Find the leaf node for the point.
    // While descending, also retain the deepest parent with a non-removed sibling.
    if (node is InternalNode) while (true) {
        val xm = (x0 + x1) / 2
        val right = x >= xm
        if (right) x0 = xm else x1 = xm

        val ym = (y0 + y1) / 2
        val bottom = y >= ym
        if (bottom) y0 = ym else y1 = ym

        parent = node as InternalNode<D>
        index = bottom.toInt() shl 1 or right.toInt()
        node = getNodeFromIndex(node, index)

        if (node == null) return
        if (node is LeafNode) break

        if (getNodeFromIndex(parent, (index + 1) and 3) != null
            || getNodeFromIndex(parent, (index + 2) and 3) != null
            || getNodeFromIndex(parent, (index + 3) and 3) != null
        ) {
            retainer = parent
            jndex = index
        }
    }

    // Find the point to remove.
    while (!(node is LeafNode) || node.data != datum) {
        previous = node
        node = (node as LeafNode).next
        if (node == null) return
    }
    val next = node.next
    if (next != null) node.next = null

    // If there are multiple coincident points, remove just the point.
    if (previous != null) {
        if (next != null) {
            (previous as LeafNode).next = next
        } else {
            (previous as LeafNode).next = null
        }
        return
    }

    // If this is the root point, remove it.
    if (parent == null) {
        root = next
        return
    }

    // Remove this leaf.
    if (next != null) {
        setNodeFromIndex(parent, index, next)
    } else {
        setNodeFromIndex(parent, index, null)
    }

    // If the parent now contains exactly one leaf, collapse superfluous parents.
    val p0 = getNodeFromIndex(parent, 0)
    val p1 = getNodeFromIndex(parent, 1)
    val p2 = getNodeFromIndex(parent, 2)
    val p3 = getNodeFromIndex(parent, 3)
    node = if (p0 != null) p0 else if (p1 != null) p1 else if (p2 != null) p2 else p3

    if (node != null
        && node == (if (p3 != null) p3 else if (p2 != null) p2 else if (p1 != null) p1 else p0)
        && node is LeafNode
    ) {
        if (retainer != null) setNodeFromIndex(retainer, jndex, node)
        else root = node
    }
}

/**
 * Removes the specified data from the quadtree.
 * @see Quadtree.remove(datum)
 */
fun <D> Quadtree<D>.removeAll(data: List<D>) = data.forEach { datum -> remove(datum) }