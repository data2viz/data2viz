package io.data2viz.voronoi

import kotlin.js.Math


fun <T> MutableList<T>.pop(): T? = if(isEmpty()) null else removeAt(lastIndex)

class Diagram(initialSites: Array<Site>, clipStart:Point = Point(.0, .0), clipEnd:Point? = null) {

    var x: Double? = null
    var y: Double? = null
    var site: Site? = null
    var circle: RedBlackNode<Circle>? = null
    private val sites: MutableList<Site> = with(initialSites) { sort(); toMutableList() }
    var edges: MutableList<Edge?>
    var cells: Array<Cell?>?

    init {
        wCells = arrayOfNulls(initialSites.size)
        site = sites.pop()

        while (true) {
            circle = firstCircle

            if (site != null &&
                    (circle == null
                            || site!!.y < circle!!.y
                            || (site!!.y == circle!!.y && site!!.x < circle!!.x))) {
                if (site!!.x != x || site!!.y != y) {
                    addBeach(site!!)
                    x = site!!.x
                    y = site!!.y
                }
                site = sites.pop()
            } else if (circle != null) {
                removeBeach(circle!!)
            } else {
                break
            }
        }

        sortCellHalfedges()

        if (clipEnd != null) {
            clipEdges(clipStart, clipEnd)
            clipCells(clipStart, clipEnd)
        }

        this.edges = wEdges
        this.cells = wCells

        beaches.root = null
        circles.root = null

        wEdges = mutableListOf()
        wCells = null

    }

    private fun clipCells(clipStart: Point, clipEnd: Point) {
        val nCells = wCells!!.size
        var cell:Cell?
        var site:Site?
        var iHalfedge:Int
        var halfedges:MutableList<Int>
        var nHalfedges:Int
        var start: Point
        var end: Point
        var cover = true

        val x0 = clipStart.x
        val y0 = clipStart.y
        val x1 = clipEnd.x
        val y1 = clipEnd.y

        wCells!!.forEach { cell ->
                site = cell!!.site
                halfedges = cell.halfedges
                iHalfedge = halfedges.size

                // Remove any dangling clipped edges.
                while (iHalfedge-- > 0) {
                    if (wEdges[halfedges[iHalfedge]] == null) {
                        halfedges.removeAt(iHalfedge)
                    }
                }

                // Insert any border edges as necessary.
                iHalfedge = 0
                nHalfedges = halfedges.size
                while (iHalfedge < nHalfedges) {
                    end = cell.cellHalfedgeEnd(wEdges[halfedges[iHalfedge]]!!)!!
                    start = cell.cellHalfedgeStart(wEdges[halfedges[++iHalfedge % nHalfedges]]!!)!!
                    val startX = start.x
                    val startY = start.y
                    val endX = end.x
                    val endY = end.y

                    if (Math.abs(end.x - start.x) > epsilon || Math.abs(end.y - start.y) > epsilon) {

                        val edge = createBorderEdge(site!!, end,
                                when {
                                    Math.abs(endX - x0) < epsilon && y1 - endY > epsilon -> Point(x0, if(Math.abs(startX - x0) < epsilon) startY else y1)
                                    Math.abs(endY - y1) < epsilon && x1 - endX > epsilon -> Point(if (Math.abs(startY - y1) < epsilon) startX else x1, y1)
                                    Math.abs(endX - x1) < epsilon && endY - y0 > epsilon -> Point(x1, if(Math.abs(startX - x1) < epsilon) startY else y0)
                                    Math.abs(endY - y0) < epsilon && endX - x0 > epsilon -> Point(if(Math.abs(startY - y0) < epsilon) startX else x0, y0)
                                    else -> null
                                })

                        wEdges.add(edge)
                        halfedges.add(iHalfedge, wEdges.size -1 )
                        ++nHalfedges
                    }
                }
                if (nHalfedges > 0) cover = false
//            }
        }

        // If there weren’t any edges, have the closest site cover the extent.
        // It doesn’t matter which corner of the extent we measure!
        if (cover) {
            var dx:Double
            var dy:Double
            var d2:Double
            var dc = Double.POSITIVE_INFINITY

            var coverCel: Cell? = null

            for (iCell in 0..nCells-1) {
                cell = wCells!![iCell]
                if (cell != null) {
                    site = cell.site
                    dx = site!!.x - x0
                    dy = site!!.y - y0
                    d2 = dx * dx + dy * dy
                    if (d2 < dc) {
                        dc = d2
                        coverCel = cell
                    }
                }
            }

            if (coverCel != null) {
                val v00 = Point(x0, y0)
                val v01 = Point(x0, y1)
                val v11 = Point(x1, y1)
                val v10 = Point(x1, y0)

                site = coverCel.site
                wEdges.add(createBorderEdge(site!!, v00, v01))
                wEdges.add(createBorderEdge(site!!, v01, v11))
                wEdges.add(createBorderEdge(site!!, v11, v10))
                wEdges.add(createBorderEdge(site!!, v10, v00))

                (wEdges.size-5..wEdges.size-1).forEach { coverCel!!.halfedges.add(it) }
            }
        }

        // Lastly delete any cells with no edges; these were entirely clipped.
        wCells!!.forEachIndexed { index, cell ->
            if (cell?.halfedges?.size == 0) { wEdges[index] = null }
        }
    }

    private fun clipEdges(start: Point, end: Point) {
        var i = wEdges.size
        var edge:Edge

        while (i-- > 0) {
            edge = wEdges[i]!!
            if (!edge.connect(start, end)
                    || !edge.clip(start, end)
                    || !(Math.abs(edge.start!!.x - edge.end!!.x) > epsilon
                    ||  Math.abs(edge.start!!.y - edge.end!!.y) > epsilon)) {
                wEdges[i] = null
            }
        }
    }

    private var _found:Int? = null

    fun Point.squareDistance(point: Point): Double {
        val vx = x - point.x
        val vy = y - point.y
        return vx * vx + vy * vy
    }


    fun find(point: Point, radius: Double? = null): Site? {
        val that = this
        var i0:Int
        var i1:Int? = _found ?: 0
        val n = that.cells?.size ?: 0
        var cell: Cell? = cells!![i1!!]

        // Use the previously-found cell, or start with an arbitrary one.
        while (cell == null) {
            if (++i1 >= n) return null
            cell = that.cells?.get(i1)
        }
        var d2 = point.squareDistance(cell.site.pos)

        fun Edge.opposite(site:Site): Site? {
            if (right == null) return null
            return if (left == site) right else left
        }

        // Traverse the half-edges to find a closer cell, if any.
        do {
            i0 = i1!!
            cell = that.cells!![i0]
            i1 = null
            cell!!.halfedges.forEach { e ->
                val edge = that.edges[e]
                val opposite = edge?.opposite(cell!!.site) ?: return@forEach
                val v2 = point.squareDistance(opposite.pos)
                if (v2 < d2) {
                    d2 = v2
                    i1 = opposite.index
                }
            }
        } while (i1 != null)

        that._found = i0

        return if (radius == null || d2 <= radius * radius) cell?.site else null
    }

    fun polygons()  = cells?.asSequence()
            ?.filterNotNull()
            ?.map { cell ->
            cell.halfedges.map { edges[it]?.let { e -> cell.halfedgeStart(e) } }.filterNotNull()
        }?.toList() ?: emptyList()

    data class Link(val source:Point, val target: Point)

    fun links() =
        edges.filter { it?.right != null}
                .map { Link(it!!.left.pos, it.right!!.pos)}


    fun triangles(): MutableList<Triangle> {
        val triangles = mutableListOf<Triangle>()
        cells!!.filterNotNull()
                .forEachIndexed { i, cell ->

            val halfedges = cell.halfedges
            val m = halfedges.size
            if (m == 0) return@forEachIndexed
            val site = cell.site
            var e1 = edges[halfedges[m-1]]
            var s0: Site?
            var s1 = if (e1?.left === site) e1.right else e1?.left

            var j = -1
            while (++j < m){
                 s0 = s1
                e1 = edges[halfedges[j]]
                s1 = if (e1?.left === site) e1.right else e1?.left
                if (s0 != null && s1 != null && i < s0.index && i < s1.index && triangleArea( site.pos, s0.pos, s1.pos) < 0) {
                    triangles.add(Triangle(site.pos, s0.pos, s1.pos))
                }
            }
        }
        return triangles
    }


    fun triangleArea(a:Point, b:Point, c:Point) = (a.x - c.x) * (b.y - a.y) - (a.x - b.x) * (c.y - a.y)

}

data class Triangle(val a:Point, val b: Point, val c: Point)
