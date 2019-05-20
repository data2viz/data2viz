package io.data2viz.viz

import io.data2viz.color.*


/**
 * The base class of each element of the viz hierarchy.
 * Keeps a reference to the parent node.
 */
abstract class Node : Style {

    var parent: HasChildren? = null
        get() = field
        set(value) {
            field = value
            style.parent = value
        }




    /**
     * Specifies whether the item is visible. When set to false, the item won’t be drawn.
     *
     * default to true.
     */
    var visible: Boolean = true

    /**
     * Removes the node from the hierarchy.
     */
    fun remove(){
        parent?.remove(this)
    }

    private val style: HierarchicalStyle = HierarchicalStyle(parent)

    override var fill: ColorOrGradient?
        get() = style.fill
        set(value) {style.fill = value}
    override var stroke: ColorOrGradient?
        get() = style.stroke
        set(value) {style.stroke = value}
    override var strokeWidth: Double?
        get() = style.strokeWidth
        set(value) {style.strokeWidth = value}
    override var textColor: ColorOrGradient?
        get() = style.textColor
        set(value) {style.textColor = value}
    override var hAlign: TextHAlign
        get() = style.hAlign
        set(value) {style.hAlign = value}

    @Deprecated("Use hAlign", ReplaceWith("hAlign"))
    override var anchor: TextHAlign
        get() = hAlign
        set(value) {hAlign = value}
    override var vAlign: TextVAlign
        get() = style.vAlign
        set(value) {
            style.vAlign = value}

    @Deprecated("Use vAlign", ReplaceWith("vAlign"))
    override var baseline: TextVAlign
        get() = vAlign
        set(value) {
            vAlign = value}


}