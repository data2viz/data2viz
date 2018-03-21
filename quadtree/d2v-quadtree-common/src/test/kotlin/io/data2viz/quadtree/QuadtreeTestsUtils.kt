package io.data2viz.quadtree

import io.data2viz.core.Extent

internal fun Extent.toArray() = arrayOf(this.x0, this.y0, this.x1, this.y1)
internal fun InternalNode<Array<Int>>.toData(): Array<Any?> {
    val NE =
        if (this.NE_0 == null) null else if (this.NE_0 is LeafNode) (this.NE_0!! as LeafNode).data else (this.NE_0 as InternalNode).toData()
    val NW =
        if (this.NW_1 == null) null else if (this.NW_1 is LeafNode) (this.NW_1!! as LeafNode).data else (this.NW_1 as InternalNode).toData()
    val SE =
        if (this.SE_2 == null) null else if (this.SE_2 is LeafNode) (this.SE_2!! as LeafNode).data else (this.SE_2 as InternalNode).toData()
    val SW =
        if (this.SW_3 == null) null else if (this.SW_3 is LeafNode) (this.SW_3!! as LeafNode).data else (this.SW_3 as InternalNode).toData()
    return arrayOf(NE, NW, SE, SW)
}

internal val xAccessor: (Array<Int>) -> Double = { it[0].toDouble() }
internal val yAccessor: (Array<Int>) -> Double = { it[1].toDouble() }

internal fun buildQuadtree(): Quadtree<Array<Int>> {
    return quadtree(xAccessor, yAccessor)
}

internal fun buildQuadtree(init: Quadtree<Array<Int>>.() -> Unit): Quadtree<Array<Int>> =
    buildQuadtree().apply(init)