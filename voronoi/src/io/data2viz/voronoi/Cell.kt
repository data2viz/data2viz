package io.data2viz.voronoi

import kotlin.js.Math

class Cell(val site: Site) {
    val halfedges = mutableListOf<Int>()

    companion object {
        fun createCell(site: Site): Cell {
            val cell = Cell(site)
            cells[site.index] = cell
            return cell
        }
    }

    fun halfEdgeAngle(edge: Edge): Double {
        var va = edge.left
        var vb = edge.right

        if(site === vb) {
            vb = va
            va = site
        }
//        if(vb)
            return Math.atan2(vb.point.y - va.point.y, vb.point.x - va.point.x)

    }
}
