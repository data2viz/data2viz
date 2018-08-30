package io.data2viz.viz


class Group : Node, HasTransform {

    val children = mutableListOf<Node>()

    override var transform: Transform? = null

    fun transform(init: Transform.() -> Unit){
        transform = Transform().apply(init)
    }

    fun add(node: Node) {
        children.add(node)
    }

    fun remove(node: Node) {
        children.remove(node)
    }

    fun group(init: Group.() -> Unit): Group {
        val node = Group().apply(init)
        add(node)
        return node
    }

    fun line(init: Line.() -> Unit): Line {
        val node = Line().apply(init)
        add(node)
        return node
    }

    fun circle(init: Circle.() -> Unit): Circle {
        val node = Circle().apply(init)
        add(node)
        return node

    }
    fun rect(init: Rect.() -> Unit): Rect {
        val node = Rect().apply(init)
        add(node)
        return node

    }


    fun text(init: Text.() -> Unit): Text {
        val node = Text().apply(init)
        add(node)
        return node

    }


    fun path(init: PathNode.() -> Unit): PathNode {
        val node = PathNode().apply(init)
        add(node)
        return node
    }


}