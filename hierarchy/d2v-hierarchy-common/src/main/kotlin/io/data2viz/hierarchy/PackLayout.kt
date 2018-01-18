package io.data2viz.hierarchy

import io.data2viz.hierarchy.pack.packEnclose
import kotlin.math.max
import kotlin.math.min
import kotlin.math.sqrt

interface CircleValues{
    var x: Double
    var y: Double
    var r: Double
}

data class PackNode<D>(
    val data: D,
    var depth: Int,
    var height: Int,
    override var value: Double?,
    override val children: MutableList<PackNode<D>> = mutableListOf(),
    override var parent: PackNode<D>? = null,
    override var x: Double = .0,
    override var y: Double = .0,
    override var r: Double = .0,
    var previous: PackNode<D>? = null,
    var next: PackNode<D>? = null
) : ParentValued<PackNode<D>>, Children<PackNode<D>>, CircleValues

fun <D> packNode(node: PackNode<D>): PackNode<D> {
    val p =
        PackNode(node.data, node.depth, node.height, node.value, node.children, node.parent, node.x, node.y, node.r, node.previous, node.next)
    /*p.x =
    p.y = node.y
    p.r = node.r*/
    return p
}

/**
 * Enclosure diagrams use containment (nesting) to represent a hierarchy.
 * The size of the leaf circles encodes a quantitative dimension of the data.
 * The enclosing circles show the approximate cumulative size of each subtree, but due to wasted space there is
 * some distortion; only the leaf nodes can be compared accurately. Although circle packing does not use space
 * as efficiently as a treemap, the “wasted” space more prominently reveals the hierarchical structure.
 */
class PackLayout<D> {

    val constantZero: (PackNode<D>) -> Double = { .0 }
    val defaultRadius: (PackNode<D>) -> Double = { sqrt(it.value!!) }

    var radius: ((PackNode<D>) -> Double)? = null
    var dx = 1.0
    var dy = 1.0
    var padding: (PackNode<D>) -> Double = constantZero

    /**
     * Lays out the specified root hierarchy, assigning the following properties on root and its descendants:
     * - node.x - the x-coordinate of the circle’s center
     * - node.y - the y-coordinate of the circle’s center
     * - node.r - the radius of the circle
     *
     * You must call root.sum before passing the hierarchy to the pack layout.
     * You probably also want to call root.sort to order the hierarchy before computing the layout.
     */
    fun pack(root: Node<D>): PackNode<D> {

        val rootPack = makePack(root)

        rootPack.x = dx / 2
        rootPack.y = dy / 2
        if (radius != null) {
            rootPack.eachBefore(radiusLeaf(radius!!))
                .eachAfter(packChildren(padding, 0.5))
                .eachBefore(translateChild(1.0))
        } else {
            rootPack.eachBefore(radiusLeaf(defaultRadius))
                .eachAfter(packChildren(constantZero, 1.0))
                .eachAfter(packChildren(padding, rootPack.r / min(dx, dy)))
                .eachBefore(translateChild(min(dx, dy) / (2 * rootPack.r)))
        }
        return rootPack
    }

    fun size(width: Double, height: Double) {
        dx = width
        dy = height
    }

    /**
     * If radius is specified, sets the pack layout’s radius accessor to the specified function and returns this
     * pack layout.
     * If radius is not specified, returns the current radius accessor, which defaults to null.
     * If the radius accessor is null, the radius of each leaf circle is derived
     * from the leaf node.value (computed by node.sum); the radii are then scaled proportionally to fit the layout size.
     * If the radius accessor is not null, the radius of each leaf circle is specified exactly by the function.
     */
    fun radius(radius: ((PackNode<D>) -> Double)?) {
        this.radius = radius
    }

    /**
     * If padding is specified, sets this pack layout’s padding accessor to the specified number or function
     * or returns this pack layout.
     * If padding is not specified, returns the current padding accessor, which defaults to the constant zero.
     * When siblings are packed, tangent siblings will be separated by approximately the specified padding;
     * the enclosing parent circle will also be separated from its children by approximately the specified padding.
     * If an explicit radius is not specified, the padding is approximate because a two-pass algorithm is needed
     * to fit within the layout size: the circles are first packed without padding; a scaling factor is computed
     * and applied to the specified padding; and lastly the circles are re-packed with padding.
     */
    fun padding(padding: (PackNode<D>) -> Double) {
        this.padding = padding
    }

    // TODO check positive value ?
    private fun radiusLeaf(radius: ((PackNode<D>) -> Double)): ((PackNode<D>) -> Unit) {
        return { node: PackNode<D> ->
            if (node.children.isEmpty()) {
                node.r = max(.0, radius(node))
            }
        }
    }

    private fun <D> makePack(root: Node<D>): PackNode<D> {
        val rootPack = PackNode(root.data, root.depth, root.height, root.value)
        val nodes = mutableListOf(root)
        val nodesP = mutableListOf(rootPack)
        while (nodes.isNotEmpty()) {
            val node = nodes.removeAt(nodes.lastIndex)
            val nodeP = nodesP.removeAt(nodesP.lastIndex)
            node.children.forEach { child ->
                val c = PackNode(child.data, child.depth, child.height, child.value)
                c.parent = nodeP
                nodeP.children.add(c)
                nodes.add(child)
                nodesP.add(c)
            }
        }
        return rootPack
    }

    private fun packChildren(padding: (PackNode<D>) -> Double, k: Double): ((PackNode<D>) -> Unit) {
        return { node: PackNode<D> ->
            if (node.children.isNotEmpty()) {
                val children = node.children
                val r = padding(node) * k

                children.forEach { it.r += r }
                val e = packEnclose(children)
                children.forEach { it.r -= r }
                node.r = e + r
            }
        }
    }

    private fun translateChild(k: Double): ((PackNode<D>) -> Unit) {
        return { node: PackNode<D> ->
            val parent = node.parent
            node.r *= k
            if (parent != null) {
                node.x = parent.x + k * node.x
                node.y = parent.y + k * node.y
            }
        }
    }

}