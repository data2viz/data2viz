package io.data2viz.viz

import io.data2viz.color.*
import io.data2viz.color.Color
import io.data2viz.color.LinearGradient
import io.data2viz.color.RadialGradient
import javafx.scene.paint.*


typealias JfxLinearGradient = javafx.scene.paint.LinearGradient
typealias JfxRadialGradient = javafx.scene.paint.RadialGradient

val Color.jfxColor: javafx.scene.paint.Color
	get() = javafx.scene.paint.Color.rgb(r, g, b, alpha.value)


fun ColorOrGradient.toPaint() = when(this) {
	is LinearGradient -> toLinearGradientJFX()
	is RadialGradient -> toRadialGradientJFX()
	is Color -> jfxColor
	else -> error("Unknown type $this")
}


fun LinearGradient.toLinearGradientJFX(): JfxLinearGradient = JfxLinearGradient(
	x1, y1, x2, y2,
	false,
	CycleMethod.NO_CYCLE, colorStops.toStops()
)

fun RadialGradient.toRadialGradientJFX(): JfxRadialGradient  = JfxRadialGradient(.0, .0, cx, cy, radius,
	false,
	CycleMethod.NO_CYCLE, colorStops.toStops())

private fun List<ColorStop>.toStops(): List<Stop>? =  map { Stop(it.percent.value, it.color.jfxColor) }
