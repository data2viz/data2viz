package io.data2viz.voronoi


internal var wEdges = mutableListOf<Edge>()

data class Point( val x:Double, val y:Double)

/**
 * left: the site on the left side of the edge
 * right: the site on the right side of the edge; null for a clipped border edge
 */
class Edge(
        var left: Site,
        var right: Site?) {

    var start: Point? = null
    var end: Point? = null

    companion object {

        fun createEdge(left: Site, right: Site, v0: Point? = null, v1: Point? = null): Edge {
            val edge = Edge(left, right)
            wEdges.add(edge)
            val index = wEdges.size -1

            wCells!![left.index]!!.halfedges.add(index)
            wCells!![right.index]!!.halfedges.add(index)
            if (v0 != null) setEdgeEnd(edge, left, right, v0)
            if (v1 != null) setEdgeEnd(edge, right, left, v1)
            return edge
        }

        fun setEdgeEnd(edge: Edge, left: Site, right: Site, vertex: Point) {
            if (edge.start == null && edge.end == null) {
                edge.start = vertex
                edge.left = left
                edge.right = right
            } else if (edge.left === right) {
                edge.end = vertex
            } else {
                edge.start = vertex
            }
        }

    }
}
