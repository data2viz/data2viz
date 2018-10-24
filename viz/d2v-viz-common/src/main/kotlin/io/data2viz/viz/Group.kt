package io.data2viz.viz


open class Group : Node(), HasChildren, HasTransform {

    val children = mutableListOf<Node>()

    override var transform: Transform? = null

    fun transform(init: Transform.() -> Unit){
        transform = Transform().apply(init)
    }

    override fun add(node: Node) {
        children.add(node)
        node.parent = this
    }

    override fun remove(node: Node) {
        node.parent = null
        children.remove(node)
    }

    override fun clear() {
        children.clear()
    }

    override fun group(init: Group.() -> Unit): Group = Group()
            .apply(init)
            .also { add(it) }


    override fun line(init: Line.() -> Unit): Line = Line()
            .apply(init)
            .also { add(it) }

    override fun circle(init: CircleNode.() -> Unit): CircleNode = CircleNode()
            .apply(init)
            .also { add(it) }


    override fun rect(init: RectNode.() -> Unit): RectNode = RectNode()
            .apply(init)
            .also { add(it) }


    override fun text(init: Text.() -> Unit): Text = Text()
            .apply(init)
            .also { add(it) }


    override fun path(init: PathNode.() -> Unit): PathNode = PathNode()
            .apply(init)
            .also { add(it) }


}