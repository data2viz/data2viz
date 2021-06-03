/*
 * Copyright (c) 2018-2021. data2viz sàrl.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 *
 */

package io.data2viz.voronoi

import io.data2viz.geom.Point
import kotlin.math.atan2


internal var wCells: Array<Cell?>? = null

public class Cell(
    public val site: Site) {

    /**
     * List of index of edges of the current Cell.
     */
    public val halfedges: MutableList<Int> = mutableListOf<Int>()



    public companion object {
        public fun createCell(site: Site) {
            wCells!![site.index] = Cell(site)
        }
    }

    public fun halfedgeAngle(edge: Edge): Double {
        var va = edge.left.pos
        var vb = edge.right?.pos

        if (site.pos === vb) {
            vb = va
            va = site.pos
        }

        if (vb != null) {
            return atan2(vb.y - va.y, vb.x - va.x)
        }
        if (site.pos === va) {
            va = edge.end!!
            vb = edge.start
        } else {
            va = edge.start!!
            vb = edge.end
        }
        return atan2(va.x - vb!!.x, vb.y - va.y)
    }

    public fun halfedgeStart(edge: Edge): Point? = if (edge.left !== site) edge.end else edge.start
    public fun halfedgeEnd(edge: Edge): Point? = if (edge.left === site) edge.end else edge.start


    public fun cellHalfedgeStart(edge:Edge): Point? = if (edge.left !== site) edge.end else edge.start
    public fun cellHalfedgeEnd  (edge:Edge): Point? = if (edge.left === site) edge.end else edge.start

}

/**
 * Sort all cells halfedges, necessary for polygons.
 */
public fun sortCellHalfedges() {

    var edgeCount:Int
    var halfedges: MutableList<Int>
    var cell: Cell

    for (cellIndex in 0..wCells!!.lastIndex) {
        cell = wCells!![cellIndex]!!
        halfedges = cell.halfedges
        edgeCount = halfedges.size

        val indexes: Array<Int> = Array(edgeCount) { it }
        val angles: Array<Double> = Array(edgeCount) { cell.halfedgeAngle(wEdges[halfedges[it]]!!)}
        indexes.sortWith(object : Comparator<Int> { override fun compare(a: Int, b: Int) = angles[b].compareTo(angles[a]) })
        val temp: Array<Int> = Array(edgeCount) {halfedges[indexes[it]]}
        (0..edgeCount-1).forEach { halfedges[it] = temp[it] }
    }

}
