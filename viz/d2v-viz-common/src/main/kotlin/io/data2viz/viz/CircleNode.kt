package io.data2viz.viz

import io.data2viz.geom.Circle
import io.data2viz.geom.CircleGeom


class CircleNode(val circle: Circle = CircleGeom()) : Node(),
        Circle by circle,
        HasStroke,
        HasTransform,
        HasFill {

    override var transform: Transform? = null

}