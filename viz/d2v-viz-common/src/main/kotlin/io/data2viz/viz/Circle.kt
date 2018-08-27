package io.data2viz.viz

import io.data2viz.color.Color
import io.data2viz.color.colors


class Circle : Node {
    var x: Double = 10.0
    var y: Double = 10.0
    var radius: Double = 10.0
    var fill: Color? = null
    var stroke: Color? = null
}