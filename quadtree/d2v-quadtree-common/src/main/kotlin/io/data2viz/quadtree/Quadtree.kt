package io.data2viz.quadtree

import io.data2viz.core.Extent
import kotlin.math.floor

fun Boolean.toInt() = if (this) 1 else 0

interface QuadtreeNode<D>

private data class NodePair<D>(
    val source: QuadtreeNode<D>,
    val target: QuadtreeNode<D>
)

data class InternalNode<D>(
    var NE_0: QuadtreeNode<D>? = null,
    var NW_1: QuadtreeNode<D>? = null,
    var SE_2: QuadtreeNode<D>? = null,
    var SW_3: QuadtreeNode<D>? = null
) : QuadtreeNode<D>

data class LeafNode<D>(
    val data: D,
    var next: LeafNode<D>?
) : QuadtreeNode<D>

private data class Quad<D>(
    val node: QuadtreeNode<D>,
    val extent: Extent
)

private fun <D> getNodeFromIndex(node: InternalNode<D>, index: Int): QuadtreeNode<D>? {
    require(index in 0..3, { "No $index index in node." })
    return when (index) {
        0 -> node.NE_0
        1 -> node.NW_1
        2 -> node.SE_2
        else -> node.SW_3
    }
}

private fun <D> setNodeFromIndex(node: InternalNode<D>, index: Int, value: QuadtreeNode<D>?) {
    require(index in 0..3, { "No $index index in node." })
    when (index) {
        0 -> node.NE_0 = value
        1 -> node.NW_1 = value
        2 -> node.SE_2 = value
        else -> node.SW_3 = value
    }
}

private fun <D> leafCopy(leaf: LeafNode<D>): LeafNode<D> {
    val copy = LeafNode(leaf.data, null)
    var next = copy

    var newLeaf = leaf
    while (newLeaf.next != null) {
        newLeaf = newLeaf.next!!
        next.next = LeafNode(newLeaf.data, null)
        next = next.next!!
    }
    return copy
}

fun <D> quadtree(x: (D) -> Double, y: (D) -> Double, init: Quadtree<D>.() -> Unit) = Quadtree<D>(x, y).apply(init)

// TODO : use Point ?
/**
 * A quadtree recursively partitions two-dimensional space into squares, dividing each square into four equally-sized
 * squares. Each distinct point exists in a unique leaf node; coincident points are represented by a linked list.
 * Quadtrees can accelerate various spatial operations, such as the Barnes–Hut approximation for computing
 * many-body forces, collision detection, and searching for nearby points.
 */
class Quadtree<D>(val x: (D) -> Double, val y: (D) -> Double) {

    /**
     * The root node of the quadtree.
     */
    var root: QuadtreeNode<D>? = null

    /**
     * Expands the quadtree to cover the specified points [x0, y0] / [x1, y1].
     * The extent may also be expanded by calling quadtree.cover or quadtree.add.
     */
    // TODO double.NaN ou null extent ?
    var extent: Extent = Extent(Double.NaN, Double.NaN, Double.NaN, Double.NaN)
        set(value) {
            cover(value.x0, value.y0)
            cover(value.x1, value.y1)
        }

    /**
     * Sets the current x-coordinate accessor.
     * The x-acccessor is used to derive the x-coordinate of data when adding to and removing from the tree.
     * It is also used when finding to re-access the coordinates of data previously added to the tree;
     * therefore, the x- and y-accessors must be consistent, returning the same value given the same input.
     */
//    var x: (D) -> Double = { .0 }

    /**
     * Sets the current y-coordinate accessor.
     * The y-acccessor is used to derive the y-coordinate of data when adding to and removing from the tree.
     * It is also used when finding to re-access the coordinates of data previously added to the tree;
     * therefore, the x- and y-accessors must be consistent, returning the same value given the same input.
     */
//    var y: (D) -> Double = { .0 }

    /**
     * Adds the specified datum to the quadtree, deriving its coordinates ⟨x,y⟩ using the current x- and y-accessors.
     * If the new point is outside the current extent of the quadtree, the quadtree is automatically expanded to
     * cover the new point.
     */
    fun add(datum: D) {
        val x = x(datum)
        val y = y(datum)
        cover(x, y)
        _add(x, y, datum)
    }

    /**
     * Removes the specified datum to the quadtree, deriving its coordinates ⟨x,y⟩ using the current x- and y-accessors.
     * If the specified datum does not exist in this quadtree, this method does nothing.
     * TODO : datum must implements equals ???
     */
    fun remove(datum:D) {
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
                || getNodeFromIndex(parent, (index + 3) and 3) != null) {
                retainer = parent
                jndex = index
            }

//            if (!(parent = node, node = node[i = bottom << 1 | right])) return this;
//            if (!node.length) break;
//            if (parent[(i + 1) & 3] || parent[(i + 2) & 3] || parent[(i + 3) & 3]) retainer = parent, j = i;
        }

        // Find the point to remove.
        while (!(node is LeafNode) || node.data != datum) {
            previous = node
            node = (node as LeafNode).next
            if (node == null) return
//            if (!(previous = node, node = node.next)) return
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
//            return (next? previous.next = next : delete previous.next), this;
        }

        // If this is the root point, remove it.
        if (parent == null) {
            root = next
            return
        }
//        if (!parent) return this._root = next, this;

        // Remove this leaf.
        if (next != null) {
            setNodeFromIndex(parent, index, next)
        } else {
            setNodeFromIndex(parent, index, null)
        }
//        next ? parent[i] = next : delete parent[i];

        // If the parent now contains exactly one leaf, collapse superfluous parents.
        val p0 = getNodeFromIndex(parent, 0)
        val p1 = getNodeFromIndex(parent, 1)
        val p2 = getNodeFromIndex(parent, 2)
        val p3 = getNodeFromIndex(parent, 3)
        node = if (p0 != null) p0 else if (p1 != null) p1 else if (p2 != null) p2 else p3

        if (node != null
            && node == (if (p3 != null) p3 else if (p2 != null) p2 else if (p1 != null) p1 else p0)
            && node is LeafNode) {
            if (retainer != null) setNodeFromIndex(retainer, jndex, node)
            else root = node
        }
        /*if ((node = parent[0] || parent[1] || parent[2] || parent[3])
            && node === (parent[3] || parent[2] || parent[1] || parent[0])
            && !node.length) {
            if (retainer) retainer[j] = node;
            else this._root = node;
        }

        return this;*/
    }

    private fun _add(x: Double, y: Double, datum: D) {
        if (x.isNaN() || y.isNaN()) return

        var node = root
        val leaf = LeafNode(datum, null)
        var x0 = extent.x0
        var y0 = extent.y0
        var x1 = extent.x1
        var y1 = extent.y1

        var index = 0
        var jndex = 0
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
//            if (right = x >= (xm = (x0 + x1) / 2)) x0 = xm; else x1 = xm

            val ym = (y0 + y1) / 2
            val bottom = y >= ym
            if (bottom) y0 = ym else y1 = ym
//            if (bottom = y >= (ym = (y0 + y1) / 2)) y0 = ym; else y1 = ym

            parent = node
            index = bottom.toInt() shl 1 or right.toInt()
            node = getNodeFromIndex(node, index)
            if (node == null) {
                setNodeFromIndex(parent, index, leaf)
                return
            }
//            if (parent = node, !(node = node[i = bottom << 1 | right])) return parent[i] = leaf, tree
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
            //parent = parent ? parent[i] = new Array(4) : tree._root = new Array(4)
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
//            if (right = x >= (xm = (x0 + x1) / 2)) x0 = xm; else x1 = xm

            val ym = (y0 + y1) / 2
            val bottom = y >= ym
            if (bottom) y0 = ym else y1 = ym

//            if (right = x >= (xm = (x0 + x1) / 2)) x0 = xm; else x1 = xm
//            if (bottom = y >= (ym = (y0 + y1) / 2)) y0 = ym; else y1 = ym

            index = bottom.toInt() shl 1 or right.toInt()
            jndex = (yp >= ym).toInt() shl 1 or (xp >= xm).toInt()
        } while (index == jndex)

//        } while ((i = bottom < < 1 | right) === (j = (yp >= ym) << 1 | (xp >= xm)))
//        return parent[j] = node, parent[i] = leaf, tree

        setNodeFromIndex(parent!!, jndex, node!!)
        setNodeFromIndex(parent, index, leaf)
    }

    /**
     * Returns a copy of the quadtree. All nodes in the returned quadtree are identical copies of the corresponding
     * node in the quadtree; however, any data in the quadtree is shared by reference and not copied.
     */
    fun copy(): Quadtree<D> {

        val copy = Quadtree(x, y)
        copy.extent = extent.copy()

        val node = root ?: return copy

        if (node is LeafNode) {
            copy.root = leafCopy(node)
            return copy
        }

        copy.root = InternalNode()
        val nodes = mutableListOf(NodePair(node, copy.root!!))
        while (nodes.isNotEmpty()) {
            val currentNode = nodes.removeAt(nodes.lastIndex)
            (0..3).forEach { index ->
                val child = getNodeFromIndex(currentNode.source as InternalNode<D>, index)
                if (child != null) {
                    if (child is InternalNode) {
                        setNodeFromIndex(currentNode.target as InternalNode<D>, index, InternalNode())
                        nodes.add(NodePair(child, getNodeFromIndex(currentNode.target, index)!!))
                    } else {
                        setNodeFromIndex(currentNode.target as InternalNode<D>, index, leafCopy(child as LeafNode<D>))
                    }
                }
            }
        }

        return copy
    }

    /**
     * Expands the quadtree to cover the specified point ⟨x,y⟩.
     * If the quadtree’s extent already covers the specified point, this method does nothing.
     * If the quadtree has an extent, the extent is repeatedly doubled to cover the specified point, wrapping the
     * root node as necessary; if the quadtree is empty, the extent is initialized to the extent (⌊x⌋, ⌊y⌋, ⌈x⌉, ⌈y⌉).
     * (Rounding is necessary such that if the extent is later doubled, the boundaries of existing quadrants do
     * not change due to floating point error.)
     */
    fun cover(x: Double, y: Double) {
        if (x.isNaN() || y.isNaN()) return                 // ignore invalid points

        var x0 = extent.x0
        var y0 = extent.y0
        var x1 = extent.x1
        var y1 = extent.y1

        // If the quadtree has no extent, initialize them.
        // Integer extent are necessary so that if we later double the extent,
        // the existing quadrant boundaries don’t change due to floating point error!
        if (extent.x0.isNaN()) {
            x0 = floor(x)
            y0 = floor(y)
            x1 = x0 + 1
            y1 = y0 + 1
        }

        // Otherwise, double repeatedly to cover.
        else if (x0 > x || x > x1 || y0 > y || y > y1) {
            var z = x1 - x0
            var node = root
            var parent: InternalNode<D>? = null

            val vertical = (y < (y0 + y1) / 2).toInt()
            val horizontal = (x < (x0 + x1) / 2).toInt()
            val i = vertical shl 1 or horizontal
            when (i) {
                0 -> {
                    do {
                        parent = InternalNode(node, null, null, null)
                        node = parent
                        z *= 2
                        x1 = x0 + z
                        y1 = y0 + z
                    } while (x > x1 || y > y1)
                }
                1 -> {
                    do {
                        parent = InternalNode(null, node, null, null)
                        node = parent
                        z *= 2
                        x0 = x1 - z
                        y1 = y0 + z
                    } while (x0 > x || y > y1)
//                    do parent = new Array (4), parent[i] = node, node = parent
//                    while (z *= 2, x0 = x1-z, y1 = y0+z, x0 > x || y > y1)
                }
                2 -> {
                    do {
                        parent = InternalNode(null, null, node, null)
                        node = parent
                        z *= 2
                        x1 = x0 + z
                        y0 = y1 - z
                    } while (x > x1 || y0 > y)
//                    do parent = new Array (4), parent[i] = node, node = parent
//                    while (z *= 2, x1 = x0+z, y0 = y1-z, x > x1 || y0 > y)
                }
                3 -> {
                    do {
                        parent = InternalNode(null, null, null, node)
                        node = parent
                        z *= 2
                        x0 = x1 - z
                        y0 = y1 - z
                    } while (x0 > x || y0 > y)
//                    do parent = new Array (4), parent[i] = node, node = parent
//                    while (z *= 2, x0 = x1-z, y0 = y1-z, x0 > x || y0 > y)
                }
            }

            if (root != null && root is InternalNode) root = node
//            if (this._root && this._root.length) this._root = node
        }

        // If the quadtree covers the point already, just return.
        else return

        extent.x0 = x0
        extent.y0 = y0
        extent.x1 = x1
        extent.y1 = y1
    }

    /**
     * Returns the total number of data in the quadtree.
     */
    fun size(): Int {
        var size = 0
        visit({ node, x0, y0, x1, y1 ->
            var newNode: QuadtreeNode<D>? = node
            if (newNode is LeafNode<*>) {
                do {
                    ++size
                    newNode = (newNode as LeafNode<D>).next
                } while (newNode != null)
            }
            false
        })
        return size
    }

    /**
     * Visits each node in the quadtree in pre-order traversal, invoking the specified callback with arguments
     * node, x0, y0, x1, y1 for each node, where node is the node being visited, ⟨x0, y0⟩ are the lower bounds of the
     * node, and ⟨x1, y1⟩ are the upper bounds, and returns the quadtree. (Assuming that positive x is right and
     * positive y is down, as is typically the case in Canvas and SVG, ⟨x0, y0⟩ is the top-left corner and ⟨x1, y1⟩ is
     * the lower-right corner; however, the coordinate system is arbitrary, so more formally x0 <= x1 and y0 <= y1).
     *
     * If the callback returns true for a given node, then the children of that node are not visited; otherwise,
     * all child nodes are visited. This can be used to quickly visit only parts of the tree, for example when
     * using the Barnes–Hut approximation.
     * Note, however, that child quadrants are always visited in sibling order: top-left, top-right, bottom-left,
     * bottom-right. In cases such as search, visiting siblings in a specific order may be faster.
     */
    private fun visit(callback: (QuadtreeNode<D>, Double, Double, Double, Double) -> Boolean) {
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
            if (!callback(node, x0, y0, x1, y1) && node is InternalNode) {
                val xm = (x0 + x1) / 2
                val ym = (y0 + y1) / 2
                if (node.SW_3 != null) quads.add(Quad(node.SW_3!!, Extent(xm, ym, x1, y1)))
                if (node.SE_2 != null) quads.add(Quad(node.SE_2!!, Extent(x0, ym, xm, y1)))
                if (node.NE_0 != null) quads.add(Quad(node.NE_0!!, Extent(xm, y0, x1, ym)))
                if (node.NW_1 != null) quads.add(Quad(node.NW_1!!, Extent(x0, y0, xm, ym)))
            }
        }
    }
}