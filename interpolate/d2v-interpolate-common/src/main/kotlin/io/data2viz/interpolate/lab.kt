package io.data2viz.interpolate

import io.data2viz.color.*

// TODO use type for parameter percent ?
// TODO use gamma ?
private fun interpolateLab(start: Color, end:Color): (Double) -> Color {
    val startLab = start.toLab()
    val endLab = end.toLab()
    val colorInterpolator = gamma()

    val l = colorInterpolator(startLab.labL, endLab.labL)
    val a = colorInterpolator(startLab.labA, endLab.labA)
    val b = colorInterpolator(startLab.labB, endLab.labB)

    return fun(percent:Double) = Colors.lab(l(percent), a(percent), b(percent))
}

fun labInterpolator(start:Color, end:Color) = interpolateLab(start, end)