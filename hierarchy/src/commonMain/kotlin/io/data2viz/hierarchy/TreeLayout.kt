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

package io.data2viz.hierarchy

data class TreeNode<D>(
    val data: D?,
    var depth: Int,
    var height: Int,
    override var value: Double?,
    internal val index: Int = 0,
    var x:Double = .0,
    var y:Double = .0,
    internal var A: TreeNode<D>? = null,             // default ancestor
    internal var ancestor: TreeNode<D>? = null,      // ancestor
    internal var z: Double = .0,                     // prelim (TODO : Int ?)
    internal var m: Double = .0,                     // mod (TODO : Int ?)
    internal var c: Double = .0,                     // change
    internal var s: Double = .0,                     // shift
    internal var t: TreeNode<D>? = null,             // thread
    override val children: MutableList<TreeNode<D>> = mutableListOf(),
    override var parent: TreeNode<D>? = null
): ParentValued<TreeNode<D>>, Children<TreeNode<D>>

class TreeLayout<D> {

    private var nodeSize = false
    private var dx = 1.0
    private var dy = 1.0

    /**
     * Lays out the specified root hierarchy, assigning the following properties on root and its descendants:
     * node.x - the x-coordinate of the node
     * node.y - the y-coordinate of the node
     *
     * The coordinates x and y represent an arbitrary coordinate system; for example, you can treat x as an angle
     * and y as a radius to produce a radial layout.
     * You may want to call root.sort before passing the hierarchy to the tree layout.
     *
     * The tree layout produces tidy node-link diagrams of trees using the Reingold–Tilford “tidy” algorithm,
     * improved to run in linear time by Buchheim et al. Tidy trees are typically more compact than dendograms.
     */
    fun <D> tree(root: Node<D>): TreeNode<D> {
        val rootChild = makeTree(root)

        // Compute the layout using Buchheim et al.’s algorithm.
        rootChild.eachAfter(this::firstWalk)
        rootChild.parent!!.m = -rootChild.z
        rootChild.eachBefore(this::secondWalk)

        // If a fixed node size is specified, scale x and y.
        if (nodeSize) rootChild.eachBefore(this::sizeNode)

        // If a fixed tree size is specified, scale x and y based on the extent.
        // Compute the left-most, right-most, and depth-most nodes for extents.
        else {
            var left = rootChild
            var right = rootChild
            var bottom = rootChild
            rootChild.eachBefore({ node: TreeNode<D> ->
                if (node.x < left.x) left = node
                if (node.x > right.x) right = node
                if (node.depth > bottom.depth) bottom = node
            })
            val s = if (left == right) 1.0 else separation(left, right) / 2.0
            val tx = s - left.x
            val kx = dx / (right.x + s + tx)
            val ky = dy / if (bottom.depth == 0) 1.0 else bottom.depth.toDouble()
            rootChild.eachBefore({ node: TreeNode<D> ->
                node.x = (node.x + tx) * kx
                node.y = node.depth * ky
            })
        }

        return rootChild
    }

    fun size(width: Double, height: Double) {
        nodeSize = false
        dx = width
        dy = height
    }

    fun nodeSize(width: Double, height: Double) {
        nodeSize = true
        dx = width
        dy = height
    }

    /**
     * Computes a preliminary x-coordinate for v. Before that, FIRST WALK is applied recursively to the children of v,
     * as well as the function APPORTION.
     * After spacing out the children by calling EXECUTE SHIFTS, the node v is placed to the midpoint
     * of its outermost children.
     */
    private fun <D> firstWalk(v: TreeNode<D>) {
        val children: MutableList<TreeNode<D>> = v.children
        val siblings: MutableList<TreeNode<D>> = v.parent!!.children
        val w: TreeNode<D>? = if (v.index != 0) siblings[v.index - 1] else null
        if (children.isNotEmpty()) {
            executeShifts(v)
            val firstChild = children[0]
            val lastChild = children[children.lastIndex]
            val midpoint = (firstChild.z + lastChild.z) / 2.0
            if (w != null) {
                v.z = w.z + separation(v, w)
                v.m = v.z - midpoint
            } else {
                v.z = midpoint
            }
        } else if (w != null) {
            v.z = w.z + separation(v, w)
        }
        val parent = v.parent!!
        val ancestor: TreeNode<D> = if (parent.A != null) parent.A!! else siblings[0]
        parent.A = apportion(v, w, ancestor)
    }

    /**
     * Computes all real x-coordinates by summing up the modifiers recursively.
     */
    private fun <D> secondWalk(v: TreeNode<D>) {
        v.x = v.z + v.parent!!.m
        v.m += v.parent!!.m
    }

    private fun <D> sizeNode(node: TreeNode<D>) {
        node.x *= dx
        node.y = node.depth * dy
    }

    /**
     * The core of the algorithm. Here, a new subtree is combined with the previous subtrees.
     * Threads are used to traverse the inside and outside contours of the left and right subtree up to the
     * highest common level.
     * The vertices used for the traversals are vi+, vi-, vo-, and vo+, where the superscript o means outside
     * and i means inside, the subscript - means left subtree and + means right subtree.
     * For summing up the modifiers along the contour, we use respective variables si+, si-, so-, and so+.
     * Whenever two nodes of the inside contours conflict, we compute the left one of the greatest uncommon ancestors
     * using the function ANCESTOR and call MOVE SUBTREE to shift the subtree and prepare the shifts of smaller subtrees.
     * Finally, we add a new thread (if necessary).
     */

    private fun <D> apportion(v: TreeNode<D>, w: TreeNode<D>?, ancestor: TreeNode<D>): TreeNode<D> {
        var ancestorNew = ancestor
        if (w != null) {
            var vip: TreeNode<D>? = v
            var vop: TreeNode<D>? = v
            var vim: TreeNode<D>? = w
            var vom: TreeNode<D>? = vip!!.parent!!.children[0]
            var sip = vip.m
            var sop = vop!!.m
            var sim = vim!!.m
            var som = vom!!.m
            var shift: Double

            vim = nextRight(vim)
            vip = nextLeft(vip)
            while (vim != null && vip != null) {
                vom = nextLeft(vom!!)
                vop = nextRight(vop!!)
                vop!!.ancestor = v
                shift = vim.z + sim - vip.z - sip + separation(vim, vip)
                if (shift > 0) {
                    moveSubtree(nextAncestor(vim, v, ancestorNew), v, shift)
                    sip += shift
                    sop += shift
                }
                sim += vim.m
                sip += vip.m
                if (vom != null) som += vom.m
                if (vop != null) sop += vop.m

                vim = nextRight(vim)
                vip = nextLeft(vip)
            }
            if (vim != null && nextRight(vop!!) == null) {
                vop.t = vim
                vop.m += sim - sop
            }
            if (vip != null && nextLeft(vom!!) == null) {
                vom.t = vip
                vom.m += sip - som
                ancestorNew = v
            }
        }
        return ancestorNew
    }

    /**
     * If vi-’s ancestor is a sibling of v, returns vi-’s ancestor. Otherwise, returns the specified (default) ancestor.
     */
    private fun <D> nextAncestor(vim: TreeNode<D>, v: TreeNode<D>, ancestor: TreeNode<D>): TreeNode<D> {
        return if (vim.ancestor?.parent == v.parent) vim.ancestor!! else ancestor
    }

    /**
     * Shifts the current subtree rooted at w+. This is done by increasing prelim(w+) and mod(w+) by shift.
     */
    private fun <D> moveSubtree(wm: TreeNode<D>, wp: TreeNode<D>, shift: Double) {
        val change = shift / (wp.index - wm.index)
        wp.c -= change
        wp.s += shift
        wm.c += change
        wp.z += shift
        wp.m += shift
    }

    /**
     * This function is used to traverse the left contour of a subtree (or subforest).
     * It returns the successor of v on this contour. This successor is either given by the leftmost child of v or by the thread of v.
     * The function returns null if and only if v is on the highest level of its subtree.
     */
    private fun <D> nextLeft(v: TreeNode<D>): TreeNode<D>? {
        return if (v.children.isNotEmpty()) (v.children[0]) else v.t
    }

    /**
     * This function works analogously to nextLeft.
     */
    private fun <D> nextRight(v: TreeNode<D>): TreeNode<D>? {
        return if (v.children.isNotEmpty()) (v.children[v.children.lastIndex]) else v.t
    }

    /**
     * All other shifts, applied to the smaller subtrees between w- and w+, are performed by this function.
     * To prepare the shifts, we have to adjust change(w+), shift(w+), and change(w-).
     */
    private fun <D> executeShifts(v: TreeNode<D>) {
        var shift = .0
        var change = .0
        val children = v.children
        var i = children.size
        while (--i >= 0) {
            val w = children[i]
            w.z += shift
            w.m += shift
            change += w.c
            shift += w.s + change
        }
    }

    private fun <D> makeTree(root: Node<D>): TreeNode<D> {
        val rootTree = TreeNode(root.data, root.depth, root.height, root.value)
        rootTree.ancestor = rootTree
        val nodes = mutableListOf(root)
        val nodesT = mutableListOf(rootTree)

        while (nodes.isNotEmpty()) {
            val node = nodes.removeAt(nodes.lastIndex)
            val nodeT = nodesT.removeAt(nodesT.lastIndex)
            node.children.forEachIndexed {index, child ->
                val c = TreeNode(child.data, child.depth, child.height, child.value, index)
                c.ancestor = c
                c.parent = nodeT
                nodeT.children.add(c)
                nodes.add(child)
                nodesT.add(c)
            }
        }

        val treeRoot = TreeNode<D>(null, 0, 0, null, 0)
        treeRoot.ancestor = treeRoot
        treeRoot.children.add(rootTree)
        rootTree.parent = treeRoot

        return rootTree
    }
}