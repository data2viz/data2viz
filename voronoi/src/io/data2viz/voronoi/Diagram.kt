package io.data2viz.voronoi


fun <T> MutableList<T>.pop(): T? = if(isEmpty()) null else removeAt(lastIndex)

class Diagram(initialSites: Array<Site>, clipStart:Point = Point(.0, .0), clipEnd:Point? = null) {

    var x: Double? = null
    var y: Double? = null
    var site: Site? = null
    var circle: RedBlackNode<Circle>? = null
    val sites: MutableList<Site> = with(initialSites) { sort(); toMutableList() }
    var edges: List<Edge>
    var cells: Array<Cell?>?

    init {
        wCells = arrayOfNulls(initialSites.size)
        site = sites.pop()

        while (true) {
            circle = firstCircle

            if (site != null &&
                    (circle == null
                            || site!!.y < circle!!.y
                            || (site!!.y === circle!!.y && site!!.x < circle!!.x))) {
                if (site!!.x !== x || site!!.y !== y) {
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
//            clipEdges(clipStart, clipEnd)
//            clipCells(clipStart, clipEnd)
        }

        this.edges = wEdges
        this.cells = wCells

        beaches.root = null
        circles.root = null

        wEdges = mutableListOf<Edge>()
        wCells = null

    }


}
