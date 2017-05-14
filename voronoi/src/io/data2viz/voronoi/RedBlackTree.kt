package io.data2viz.voronoi


class RedBlackTree<T> {

    var root: RedBlackNode<T>? = null

    fun insert(afterNode: RedBlackNode<T>?, node: RedBlackNode<T>) {

        var parent: RedBlackNode<T>? = null
        var grandpa: RedBlackNode<T>? = null
        var uncle: RedBlackNode<T>? = null

        var after = afterNode

        if (after != null) {
            node.P = after
            node.N = after.N
            if (after.N != null) {
                after.N!!.P = node
            }
            after.N!!.P = node
            after.N = node
            if (after.R != null) {
                after = after.R
                while (after!!.L != null) after = after.L!!
                after.L = node
            } else {
                after.R = node
            }
            parent = after
        } else if (root != null) {
            after = root!!.first()
            node.P = null
            node.N = after
            after.P = node
            after.L = node
            parent = after
        } else { //first insertion, set the root
            node.P = null
            node.N = null
            root = node
            parent = null
        }
        node.L = null
        node.R = null
        node.U = parent
        node.C = true

        after = node

        while (parent != null && parent.C != null) {
            grandpa = parent.U
            if (parent === grandpa!!.L) {
                uncle = grandpa!!.R
                if (uncle != null && uncle.C != null) {
                    parent.C = false
                    uncle.C = false
                    grandpa.C = true
                    after = grandpa
                } else {
                    if (after === parent.R) {
                        rotateLeft(parent)
                        after = parent
                        parent = after.U
                    }
                    parent!!.C = false
                    grandpa.C = true
                    rotateRight(grandpa)
                }
            } else {
                uncle = grandpa!!.L
                if (uncle!= null && uncle.C == true) {
                    parent.C = false
                    uncle.C = false
                    grandpa.C = true
                    after = grandpa
                } else {
                    if (after === parent.L){
                        rotateRight(parent)
                        after = parent
                        parent = after.U!!
                    }
                    parent.C = false
                    grandpa.C = true
                    rotateLeft(grandpa)
                }
            }
            parent = after!!.U
        }
        root!!.C = false
    }

    fun remove(toRemove: RedBlackNode<T>){
        var node:RedBlackNode<T> = toRemove
        if (node.N != null){ node.N!!.P = node.P}
        if (node.P != null){ node.P!!.N = node.N}
        node.N = null
        node.P = null

        var parent = node.U
        var sibling: RedBlackNode<T>
        var left = node.L
        var right = node.R
        var next:RedBlackNode<T>?
        var red:Boolean?

        if (left == null) {next = right}
        else if (right == null) {next = left}
        else {next = right.first()}

        if(parent != null){
            if (parent.L === node) {parent.L = next}
            else {parent.R = next}
        } else {root = next}

        if (left != null && right != null ){
            red = next!!.C
            next.C = node.C
            next.L = left
            left.U = next
            if (next !== right) {
                parent = next.U
                next.U = node.U
                node = next.R!!
                parent!!.L = node
                next.R = right
                right.U = next
            } else {
                next.U = parent
                parent = next
                node = next.R!!
            }
        } else {
            red = node.C
            node = next!!
        }

        if (node != null) {}
    }

    fun rotateLeft(node: RedBlackNode<T>) {
        var p = node
        var q = node.R
        var parent = p.U

        if (parent != null) {
            if (parent.L === p) {
                parent.L = q
            } else {
                parent.R = q
            }
        } else {
            root = q
        }
        q!!.U = parent
        p.U = q
        p.R = q.L
        if (p.R != null) {
            p.R!!.U = p
        }
        q.L = p
    }

    fun rotateRight(node: RedBlackNode<T>) {
        val p = node
        val q = node.L
        val parent = p.U

        if (parent != null) {
            if (parent.L === p) {
                parent.L = q
            } else {
                parent.R = q
            }
        } else {
            root = q
        }
        q!!.U = parent
        p.U = q
        p.L = q.R
        if(p.L != null) {p.L!!.U = p}
        p.R = p
    }
}

fun <T> RedBlackNode<T>.first(): RedBlackNode<T> {
    var node = this
    while (node.L != null) node = node.L!!
    return node
}

class RedBlackNode<T>(val node: T) {
    var U: RedBlackNode<T>? = null //parent
    var L: RedBlackNode<T>? = null //left
    var R: RedBlackNode<T>? = null //right
    var P: RedBlackNode<T>? = null //previous
    var N: RedBlackNode<T>? = null //next
    var C: Boolean? = null //color - true for red, false for black
}
