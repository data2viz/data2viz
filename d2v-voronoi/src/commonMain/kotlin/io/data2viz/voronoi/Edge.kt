/*
 * Copyright (c) 2018-2019. data2viz sàrl.
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


internal var wEdges = mutableListOf<Edge?>()

fun createBorderEdge(left:Site, v0: Point, v1: Point?): Edge {
    val ret = Edge (left)
    ret.start = v0
    ret.end = v1
    return ret
}


/**
 * left: the site on the left side of the edge
 * right: the site on the right side of the edge; null for a clipped border edge
 */
class Edge(
        var left: Site,
        var right: Site? =  null) {

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

    fun clip(clipStart: Point, clipEnd: Point):Boolean {
        val a = start
        val b = end
        val ax = a!!.x
        val ay = a.y
        val bx = b!!.x
        val by = b.y

        var t0 = 0.0
        var t1 = 1.0
        val dx = bx - ax
        val dy = by - ay
        var r = clipStart.x - ax

        if(dx == 0.0 && r > 0.0) return false
        r /=  dx
        if (dx < 0) {
            if (r < t0) return false
            if (r < t1) t1 = r
        } else if (dx > 0) {
            if (r > t1) return false
            if (r > t0) t0 = r
        }

        r = clipEnd.x - ax
        if (dx == 0.0 && r < 0) return false
        r /= dx
        if (dx < 0) {
            if (r > t1) return false
            if (r > t0) t0 = r
        } else if (dx > 0) {
            if (r < t0) return false
            if (r < t1) t1 = r
        }

        r = clipStart.y - ay
        if (dy == 0.0 && r > 0) return false
        r /= dy
        if (dy < 0) {
            if (r < t0) return false
            if (r < t1) t1 = r
        } else if (dy > 0) {
            if (r > t1) return false
            if (r > t0) t0 = r
        }

        r = clipEnd.y - ay
        if (dy == 0.0 && r < 0) return false
        r /= dy
        if (dy < 0) {
            if (r > t1) return false
            if (r > t0) t0 = r
        } else if (dy > 0) {
            if (r < t0) return false
            if (r < t1) t1 = r
        }

        if (t0 <= 0.0 && t1 >= 1.0) return true // TODO Better check?

        if (t0 > 0) start = Point(ax + t0 * dx, ay + t0 * dy)
        if (t1 < 1) end = Point(ax + t1 * dx, ay + t1 * dy)
        return true
    }


    fun connect(clipStart: Point, clipEnd: Point):Boolean {
        var v1 = end
        if (v1 != null) return true

        val x0 = clipStart.x
        val y0 = clipStart.y
        val x1 = clipEnd.x
        val y1 = clipEnd.y

        var v0 = start
        val lx = left.x
        val ly = left.y
        val rx = right!!.x
        val ry = right!!.y
        val fx = (lx + rx) / 2
        val fy = (ly + ry) / 2

        val fm:Double
        val fb:Double

        if (ry == ly) {
            if (fx < x0 || fx >= x1) return false
            if (lx > rx) {
                if (v0 == null) v0 = Point(fx, y0)
                else if (v0.y >= y1) return false
                v1 = Point(fx, y1)
            } else {
                if (v0 == null) v0 = Point(fx, y1)
                else if (v0.y < y0) return false
                v1 = Point(fx, y0)
            }
        } else {
            fm = (lx - rx) / (ry - ly)
            fb = fy - fm * fx
            if (fm < -1 || fm > 1) {
                if (lx > rx) {
                    if (v0 == null) v0 = Point((y0 - fb) / fm, y0)
                    else if (v0.y >= y1) return false
                    v1 = Point((y1 - fb) / fm, y1)
                } else {
                    if (v0 == null) v0 = Point((y1 - fb) / fm, y1)
                    else if (v0.y < y0) return false
                    v1 = Point((y0 - fb) / fm, y0)
                }
            } else {
                if (ly < ry) {
                    if (v0 == null) v0 = Point(x0, fm * x0 + fb)
                    else if (v0.x >= x1) return false
                    v1 = Point(x1, fm * x1 + fb)
                } else {
                    if (v0 == null) v0 = Point(x1, fm * x1 + fb)
                    else if (v0.x < x0) return false
                    v1 = Point(x0, fm * x0 + fb)
                }
            }
        }

        start = v0
        end = v1
        return true
    }
}
