@file:Suppress("DEPRECATION")

package io.data2viz.color

import io.data2viz.geom.HasStartAndEnd
import io.data2viz.geom.Point
import io.data2viz.math.Percent
import io.data2viz.math.pct

data class LinearGradientFirstColorBuilder
internal constructor(val start: Point, val end: Point) {
    fun withColor(startColor: Color, percent: Percent = 0.pct): LinearGradientSecondColorBuilder =
        LinearGradientSecondColorBuilder(this, ColorStop(percent, startColor))
}

data class LinearGradientSecondColorBuilder
internal constructor(val builder: LinearGradientFirstColorBuilder, val firstColor: ColorStop) {
    fun andColor(color: Color, percent: Percent = 100.pct): LinearGradient = LinearGradient()
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

    fun andColor(color: Color, percent: Percent): LinearGradient {
        colors.add(ColorStop(percent.coerceToDefault(), color))
        return this
    }
}