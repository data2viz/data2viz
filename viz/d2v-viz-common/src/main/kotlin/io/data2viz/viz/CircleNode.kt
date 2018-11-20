package io.data2viz.viz

import io.data2viz.color.*
import io.data2viz.geom.*


class CircleNode(val circle: Circle = CircleGeom()) : Node(),
        Circle by circle,
        HasStroke,
        HasTransform,
        HasFill {

    override var transform: Transform? = null


    override var fill: ColorOrGradient?
        get() = style.fill
        set(value) {
            style.fill = value
        }

    override var stroke: ColorOrGradient?
        get() = style.stroke
        set(value) {
            style.stroke = value
        }

    override var strokeWidth: Double?
        get() = style.strokeWidth
        set(value) {
            style.strokeWidth = value
        }


}