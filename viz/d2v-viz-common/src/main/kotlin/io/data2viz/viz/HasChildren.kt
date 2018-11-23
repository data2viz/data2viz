package io.data2viz.viz

interface HasChildren: Style {

    fun add(node: Node)
    fun remove(node: Node)
    fun clear()
    fun group(init: GroupNode.() -> Unit): GroupNode
    fun line(init: LineNode.() -> Unit): LineNode
    fun circle(init: CircleNode.() -> Unit): CircleNode
    fun rect(init: RectNode.() -> Unit): RectNode
    fun text(init: TextNode.() -> Unit): TextNode
    fun path(init: PathNode.() -> Unit): PathNode
}