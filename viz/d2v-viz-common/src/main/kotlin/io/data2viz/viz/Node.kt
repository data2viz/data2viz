package io.data2viz.viz


/**
 * The base class of each element of the viz hierarchy.
 * Keeps a reference to the parent node.
 */
abstract class Node: HasStyle {

    var parent: HasChildren? = null

    /**
     * Specifies whether the item is visible. When set to false, the item won’t be drawn.
     *
     * default to true.
     */
    var visible: Boolean = true

    var _style:Style? = null


    override val style: Style
        get() = _style ?: parent?.style ?: Style().also { _style = it }


    /**
     * Removes the node from the hierarchy.
     */
    fun remove(){
        parent?.remove(this)
    }
}