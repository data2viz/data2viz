package io.data2viz.geom

import kotlin.math.absoluteValue
import kotlin.math.min


data class RectGeom(
        override var x: Double = .0,
        override var y: Double = .0,
        override var width: Double = .0,
        override var height: Double = .0) : Rect {

    constructor(point: Point, size: Size): this(point.x, point.y, size.width, size.height)
    constructor(from: Point, to: Point):
            this(
                    min(from.x, to.x)
                    ,min(from.y, to.y),
                    (to.x - from.x).absoluteValue,
                    (to.y - from.y).absoluteValue)



}

