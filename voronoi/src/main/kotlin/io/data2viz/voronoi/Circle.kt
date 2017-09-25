package io.data2viz.voronoi

import kotlin.math.sqrt


internal var firstCircle: RedBlackNode<Circle>? = null

internal val circles = RedBlackTree<Circle>()
internal val circlePool = mutableListOf<RedBlackNode<Circle>>()

class Circle {
    var x: Double = .0
    var y: Double = .0
    var cy: Double = .0
    lateinit var site: Site

    var arcNode: RedBlackNode<Beach>? = null
}

var RedBlackNode<Circle>.x: Double
    get() = node.x
    set(value) {node.x = value}

var RedBlackNode<Circle>.y: Double
    get() = node.y
    set(value) {node.y = value}

var RedBlackNode<Circle>.cy: Double
    get() = node.cy
    set(value) {node.cy = value}

var RedBlackNode<Circle>.site: Site
    get() = node.site
    set(value) {node.site = value}


fun attachCircle(arcNode: RedBlackNode<Beach>) {
    val lArc = arcNode.P
    val rArc = arcNode.N

    if (lArc == null || rArc == null) return

    val lSite = lArc.site
    val cSite = arcNode.site
    val rSite = rArc.site

    if (lSite === rSite) return

    val bx = cSite.x
    val by = cSite.y
    val ax = lSite.x - bx
    val ay = lSite.y - by
    val cx = rSite.x - bx
    val cy = rSite.y - by

    val d = 2 * (ax * cy - ay * cx)
    if (d >= -epsilon2) return

    val ha = ax * ax + ay * ay
    val hc = cx * cx + cy * cy
    val x = (cy * ha - ay * hc) / d
    val y = (ax * hc - cx * ha) / d

    val circle = if (circlePool.isEmpty()) Circle().redBlackNode() else circlePool.pop()!!
    circle.node.arcNode= arcNode
    circle.site = cSite
    circle.x = x + bx
    circle.cy = y + by
    circle.y = y + by + sqrt(x * x + y * y) // y bottom

    arcNode.node.circleNode = circle

    var before: RedBlackNode<Circle>? = null
    var node = circles.root

    while (node != null) {
        if (circle.y < node.y || (circle.y === node.y && circle.x <= node.x)) {
            if (node.L != null) node = node.L
            else {
                before = node.P; break; }
        } else {
            if (node.R != null) node = node.R
            else {
                before = node; break; }
        }
    }

    circles.insert(circle, before)
    if (before == null)
        firstCircle = circle
}

fun detachCircle(arcNode: RedBlackNode<Beach>) {
    val circle = arcNode.node.circleNode
    if (circle != null) {
        if (circle.P == null) {
            firstCircle = circle.N
        }
        circles.remove(circle)
        circle.clean()
        circlePool.add(circle)
        arcNode.node.circleNode = null
    }
}
