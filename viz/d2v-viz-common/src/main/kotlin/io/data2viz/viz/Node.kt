package io.data2viz.viz

abstract class Node: HasStyle {

    var parent: HasChildren? = null

    var visible: Boolean = true

    var _style:Style? = null
    override val style: Style
        get() = _style ?: parent?.style ?: Style().also { _style = it }

    fun remove(){
        parent?.remove(this)
    }
}