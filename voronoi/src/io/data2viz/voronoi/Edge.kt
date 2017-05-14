package io.data2viz.voronoi

class Edge(var left:Site, var right:Site){
    var v0: Point? = null
    var v1: Point? = null
}

fun createEdge(left: Site, right: Site, v0:Point?, v1:Point?): Edge {
    val edge = Edge(left, right)
    edges.add(edge)
    val index = edges.size - 1
    if(v0 != null) setEdgeEnd(edge, left, right, v0)
    if(v1 != null) setEdgeEnd(edge, left, right, v1)

    cells[left.index].halfedges.add(index)
    cells[right.index].halfedges.add(index)
    return edge
}

fun setEdgeEnd(edge: Edge, left: Site, right: Site, vertex: Point) {
    if( edge.v0 == null && edge.v1 == null){
        edge.v0 = vertex
        edge.left = left
        edge.right = right
    } else if(edge.left === right){
        edge.v1 = vertex
    } else {
        edge.v0 = vertex
    }
}
