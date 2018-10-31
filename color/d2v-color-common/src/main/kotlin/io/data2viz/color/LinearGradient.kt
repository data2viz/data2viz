@file:Suppress("DEPRECATION")

package io.data2viz.color

import io.data2viz.geom.Point

// TODO : move to "core.geom" ?
// TODO : remove access to x1, y1, x2, y2
interface HasStartAndEnd {
    var x1: Double
    var y1: Double
    var x2: Double
    var y2: Double

    var start: Point
        get() = Point(x1, y1)
        set(value) {
            x1 = value.x
            y1 = value.y
        }

    var end: Point
        get() = Point(x2, y2)
        set(value) {
            x2 = value.x
            y2 = value.y
        }
}

data class LinearGradientFirstColorBuilder
internal constructor(val start: Point, val end: Point) {
    fun withColor(startColor: Color, percent: Double = .0): LinearGradientSecondColorBuilder =
        LinearGradientSecondColorBuilder(this, ColorStop(percent, startColor))
}

data class LinearGradientSecondColorBuilder
internal constructor(val builder: LinearGradientFirstColorBuilder, val firstColor: ColorStop) {
    fun andColor(color: Color, percent: Double = 1.0): LinearGradient = LinearGradient()
        .apply {
            x1 = builder.start.x
            y1 = builder.start.y
            x2 = builder.end.x
            y2 = builder.end.y
            andColor(firstColor.color, firstColor.percent)
            andColor(color, percent)
        }
}

class LinearGradient
@Deprecated("Deprecated", ReplaceWith("Colors.Gradient.linear()", "io.data2viz.colors.Colors"))
internal constructor() : Gradient, HasStartAndEnd {

    override var x1: Double = .0
    override var y1: Double = .0
    override var x2: Double = .0
    override var y2: Double = .0

    private val colors = mutableListOf<ColorStop>()
    override val colorStops: List<ColorStop>
        get() = colors.toList()

    fun andColor(color: Color, percent: Double): LinearGradient {
        colors.add(ColorStop(percent.coerceIn(.0, 1.0), color))
        return this
    }
}