package io.data2viz.viz

open class Node {
    var parent: HasChildren? = null
    var visible: Boolean = true

    fun remove(){
        parent?.remove(this)
    }
}