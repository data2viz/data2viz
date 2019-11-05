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

package io.data2viz.voronoi

import io.data2viz.voronoi.RedBlackColor.*

class RedBlackTree<T> {

    var root: RedBlackNode<T>? = null

    fun insert(node: RedBlackNode<T>, afterNode: RedBlackNode<T>? = null): RedBlackNode<T> {

        var parent: RedBlackNode<T>?
        var grandpa: RedBlackNode<T>?
        var uncle: RedBlackNode<T>?

        var after = afterNode

        if (after != null) {
            node.P = after
            node.N = after.N
            after.N?.P = node
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

        after = node

        while (parent?.C == RED) {
            grandpa = parent.U
            if (parent === grandpa!!.L) {
                uncle = grandpa!!.R
                if (uncle?.C == RED) {
                    parent.C = BLACK
                    uncle.C = BLACK
                    grandpa.C = RED
                    after = grandpa
                } else {
                    if (after === parent.R) {
                        rotateLeft(parent)
                        after = parent
                        parent = after.U
                    }
                    parent!!.C = BLACK
                    grandpa.C = RED
                    rotateRight(grandpa)
                }
            } else {
                uncle = grandpa!!.L
                if (uncle?.C == RED) {
                    parent.C = BLACK
                    uncle.C = BLACK
                    grandpa.C = RED
                    after = grandpa
                } else {
                    if (after === parent.L){
                        rotateRight(parent)
                        after = parent
                        parent = after.U!!
                    }
                    parent.C = BLACK
                    grandpa.C = RED
                    rotateLeft(grandpa)
                }
            }
            parent = after!!.U
        }
        root!!.C = BLACK
        return node
    }

    fun remove(toRemove: RedBlackNode<T>){
        var node: RedBlackNode<T>? = toRemove
        node!!.N?.P = node.P
        node.P?.N = node.N

        node.N = null
        node.P = null

        var parent = node.U
        var sibling: RedBlackNode<T>?
        val left = node.L
        val right = node.R
        val next: RedBlackNode<T>?
        val red: RedBlackColor

        if (left == null) { next = right }
        else if (right == null) { next = left }
        else { next = right.first() }

        if(parent != null){
            if (parent.L === node) { parent.L = next }
            else { parent.R = next }
        } else { root = next }

        if (left != null && right != null ){
            red = next!!.C
            next.C = node.C
            next.L = left
            left.U = next
            if (next !== right) {
                parent = next.U
                next.U = node.U
                node = next.R
                parent!!.L = node
                next.R = right
                right.U = next
            } else {
                next.U = parent
                parent = next
                node = next.R
            }
        } else {
            red = node.C
            node = next
        }

        if (node != null) {node.U = parent}
        if (red == RED) return
        if (node!=null && node.C == RED) {
            node.C = BLACK
            return
        }

        do {
            if (node === root) break
            if (node === parent!!.L){ //node is not root => parent not null
                sibling = parent!!.R
                if (sibling?.C == RED){
                    sibling.C = BLACK
                    parent.C = RED
                    rotateLeft(parent)
                    sibling = parent.R
                }
                if (sibling?.L?.C == RED || sibling?.R?.C == RED) {
                    if (sibling.R == null || sibling.R?.C == BLACK) {
                        sibling.L!!.C = BLACK
                        sibling.C = RED
                        rotateRight(sibling)
                        sibling = parent.R
                    }

                    sibling!!.C = parent.C
                    parent.C = BLACK
                    sibling.R!!.C = BLACK
                    rotateLeft(parent)
                    node = root
                    break
                }
            } else {
                sibling = parent!!.L
                if (sibling?.C == RED){
                    sibling.C = BLACK
                    parent.C = RED
                    rotateRight(parent)
                    sibling = parent.L
                }
                if( sibling?.L?.C == RED || sibling?.R?.C == RED) {
                    if (sibling.L == null || sibling.L?.C == BLACK) {
                        sibling.R?.C = BLACK
                        sibling.C = RED
                        rotateLeft(sibling)
                        sibling = parent.L
                    }
                    sibling?.C = parent.C
                    parent.C = BLACK
                    parent.L?.C = BLACK
                    rotateRight(parent)
                    node = root
                    break
                }
            }

            sibling?.C = RED
            node = parent
            parent = parent.U

        } while ( node?.C == BLACK)

        if (node != null) {node.C = BLACK
        }
    }

    fun rotateLeft(node: RedBlackNode<T>) {
        val p = node
        val q = node.R
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
        p.R = q.L
        if (p.R != null) {
            p.R!!.U = p
        }
        q.L = p
    }

    fun rotateRight(node: RedBlackNode<T>) {
        val p = node           //le
        val q = node.L         //le nœud qui remplace la cible
        val parent = p.U       //le parent de la cible

        if (parent != null) {
            if (parent.L === p) {
                parent.L = q
            } else {
                parent.R = q
            }
        } else {
            root = q  //pas de parent, la cible était le root. Root change pour devenir q
        }
        q!!.U = parent
        p.U = q
        p.L = q.R
        if(p.L != null) {p.L!!.U = p}
        q.R = p
    }
}

fun <T> RedBlackNode<T>.first(): RedBlackNode<T> {
    var node = this
    while (node.L != null) node = node.L!!
    return node
}

enum class RedBlackColor {
    RED, BLACK
}


class RedBlackNode<T>(val node: T) {
    var U: RedBlackNode<T>? = null //parent
    var L: RedBlackNode<T>? = null //left
    var R: RedBlackNode<T>? = null //right
    var P: RedBlackNode<T>? = null //previous
    var N: RedBlackNode<T>? = null //next
    var C: RedBlackColor = RED     //color - true for red, false for black

    fun clean() {
        U = null
        L = null
        R = null
        P = null
        N = null
    }

}
