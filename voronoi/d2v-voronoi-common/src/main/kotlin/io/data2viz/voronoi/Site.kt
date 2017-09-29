package io.data2viz.voronoi

import io.data2viz.core.Point

class Site(val pos: Point, val index: Int) : Comparable<Site> {

    val x: Double
        get() = pos.x

    val y: Double
        get() = pos.y

    override fun compareTo(other: Site) = when {
        other.pos.y < pos.y -> -1
        other.pos.y > pos.y -> 1
        other.pos.x < pos.x -> -1
        other.pos.x > pos.x -> 1
        else -> 0
    }

    override fun toString(): String {
        return "Site(pos=$pos, index=$index)"
    }

}
