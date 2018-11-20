package io.data2viz.interpolate

import io.data2viz.color.*
import io.data2viz.math.*

// TODO use type for parameter percent ?
// TODO use gamma ?
private fun interpolateLab(start: Color, end:Color): Interpolator<Color> {
    val startLab = start.toLab()
    val endLab = end.toLab()
    val colorInterpolator = gamma()

    val l = colorInterpolator(startLab.labL.value, endLab.labL.value)
    val a = colorInterpolator(startLab.labA, endLab.labA)
    val b = colorInterpolator(startLab.labB, endLab.labB)

    return fun(percent:Percent) = Colors.lab(Percent(l(percent)), a(percent), b(percent))
}

fun labInterpolator(start:Color, end:Color) = interpolateLab(start, end)