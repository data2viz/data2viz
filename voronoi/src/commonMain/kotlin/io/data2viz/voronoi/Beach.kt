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
import kotlin.math.abs
import kotlin.math.sqrt


internal val epsilon = 1e-6
internal val epsilon2 = 1e-12

//todo don't use global val. Could be a
internal val beaches = RedBlackTree<Beach>()
internal val beachPool = mutableListOf<RedBlackNode<Beach>>()

internal fun <T> T.redBlackNode(): RedBlackNode<T> = RedBlackNode(this)

public class Beach {
    public lateinit var site: Site

    public var circleNode: RedBlackNode<Circle>? = null

    public val circle: Circle?
        get() = circleNode?.node

    public var edge: Edge? = null
}

public val RedBlackNode<Beach>.site : Site
    get() = this.node.site

public val RedBlackNode<Beach>.circle : Circle?
    get() = this.node.circle


public fun createBeach(site: Site): RedBlackNode<Beach> {
    val beach = if( beachPool.isEmpty()) Beach().redBlackNode() else beachPool.pop()!!
    beach.node.site = site
    return beach
}

public fun detachBeach(beach: RedBlackNode<Beach>) {
    detachCircle(beach)
    beaches.remove(beach)
    beachPool.add(beach)
    beach.clean()
}


public fun addBeach(site: Site) {

    val x = site.x
    val directrix = site.y
    var node = beaches.root
    var lArc: RedBlackNode<Beach>? = null
    var rArc: RedBlackNode<Beach>? = null
    var dxl: Double
    var dxr: Double

    while (node != null) {
        dxl = leftBreakPoint(node, directrix) - x
        if (dxl > epsilon) {
            node = node.L
        } else {
            dxr = x - rightBreakPoint(node, directrix)
            if (dxr > epsilon) {
                if (node.R == null) {
                    lArc = node
                    break
                }
                node = node.R
            } else {
                if (dxl > -epsilon) {
                    lArc = node.P
                    rArc = node
                } else if (dxr > -epsilon) {
                    lArc = node
                    rArc = node.N
                } else {
                    lArc = node
                    rArc = node
                }
                break
            }
        }
    }

    Cell.Companion.createCell(site)
    val newArc = createBeach(site)
    beaches.insert(newArc, lArc)

    if (lArc == null && rArc == null) return

    if (lArc === rArc) {
        detachCircle(lArc!!)
        rArc = createBeach(lArc.site)
        beaches.insert(rArc, newArc)
        newArc.node.edge = Edge.Companion.createEdge(lArc.site, newArc.site)
        rArc.node.edge = newArc.node.edge
        attachCircle(lArc)
        attachCircle(rArc)
        return
    }

    if (rArc == null){
        newArc.node.edge = Edge.Companion.createEdge(lArc!!.site, newArc.site)
        return
    }

    detachCircle(lArc!!)
    detachCircle(rArc)

    val lSite =lArc.site
    val ax = lSite.x
    val ay = lSite.y
    val bx = site.x - ax
    val by = site.y - ay
    val rSite = rArc.site
    val cx = rSite.x - ax
    val cy = rSite.y - ay
    val d = 2 * (bx * cy - by * cx)
    val hb = bx * by + by * by
    val hc = cx * cx + cy * cy
    val vertex = Point((cy * hb - by * hc) / d + ax, (bx * hc - cx * hb) / d + ay)

    Edge.Companion.setEdgeEnd(rArc.node.edge!!, lSite, rSite, vertex)
    newArc.node.edge = Edge.Companion.createEdge(lSite, site, null, vertex)
    rArc.node.edge = Edge.Companion.createEdge(site, rSite, null, vertex)
    attachCircle(lArc)
    attachCircle(rArc)
}


public fun removeBeach(circle: RedBlackNode<Circle>) {
    val beach = circle.node.arcNode!!

    val x = circle.x            //centre du cercle x
    val y = circle.cy           //centre du cercle y
    val vertex = Point(x, y)    //le nouveau vertex correspond au centre du cercle supprimé
    var previous = beach.P
    var next = beach.N
    val disappearing = mutableListOf(beach)

    detachBeach(beach)

    var lArc = previous
    while (lArc?.node?.circle != null
            && abs(x - lArc.circle!!.x) < epsilon
            && abs(y - lArc.circle!!.cy) < epsilon) {
        previous = lArc.P
        disappearing.add(0,lArc)
        detachBeach(lArc)
        lArc = previous
    }

    disappearing.add(0, lArc!!)
    detachCircle(lArc)

    var rArc = next
    while (rArc?.node?.circle != null
            && abs(x - rArc.circle!!.x) < epsilon
            && abs(y - rArc.circle!!.cy) < epsilon) {
        next = rArc.N
        disappearing.add(rArc)
        detachBeach(rArc)
        rArc = next
    }

    disappearing.add(rArc!!)
    detachCircle(rArc)

    val nArcs = disappearing.size
//    var iArc = 1

    for (iArc in 1..nArcs-1) { // todo why ++iArc
        rArc = disappearing[iArc]
        lArc = disappearing[iArc - 1]
        Edge.Companion.setEdgeEnd(rArc.node.edge!!, lArc.site, rArc.site, vertex)
    }

    lArc = disappearing[0]
    rArc = disappearing[nArcs - 1]
    rArc.node.edge = Edge.Companion.createEdge(lArc.site, rArc.site, null, vertex)

    attachCircle(lArc)
    attachCircle(rArc)
}


public fun leftBreakPoint(arc: RedBlackNode<Beach>, directrix: Double): Double {
    var site = arc.node.site
    val rfocx = site.x  //foyer x ?
    val rfocy = site.y  //foyer y ?
    val pby2 = rfocy - directrix

    if (pby2 == 0.0) return rfocx

    val lArc = arc.P ?: return Double.NEGATIVE_INFINITY

    site = lArc.node.site
    val lfocx = site.x
    val lfocy = site.y
    val plby2 = lfocy - directrix

    if (plby2 == 0.0) return lfocx

    val hl = lfocx - rfocx
    val aby2 = 1 / pby2 - 1 / plby2
    val b = hl / plby2

    return if (aby2 != 0.0)
        (-b + sqrt(b * b - 2 * aby2 * (hl * hl / (-2 * plby2) - lfocy + plby2 / 2 + rfocy - pby2 / 2))) / aby2 + rfocx
    else
        (rfocx + lfocx) / 2
}

public fun rightBreakPoint(arc: RedBlackNode<Beach>, directrix: Double): Double {
    val rArc = arc.N
    if (rArc != null) return leftBreakPoint(rArc, directrix)
    val site = arc.node.site
    return if (site.y == directrix) site.x else Double.POSITIVE_INFINITY
}
