package io.data2viz.interpolate

import io.data2viz.color.Color
import io.data2viz.color.Colors
import io.data2viz.color.toHsla
import io.data2viz.math.Angle
import io.data2viz.math.Percent

// TODO add alpha interpolation
// TODO List instead of start, end ? (validate and check size !!)
// TODO use gamma ?
private fun interpolateHsl(start: Color, end:Color, long:Boolean): (Percent) -> Color {
    var startHSL = start.toRgb().toHsla()
    var endHSL = end.toRgb().toHsla()
    val colorInterpolator = gamma()

    if (startHSL.isAchromatic()) startHSL = Colors.hsl(endHSL.h, endHSL.s, startHSL.l, startHSL.alpha)
    if (endHSL.isAchromatic()) endHSL = Colors.hsl(startHSL.h, startHSL.s, endHSL.l, endHSL.alpha)

    val h = interpolateHue(startHSL.h, endHSL.h, long)
    val s = colorInterpolator(startHSL.s, endHSL.s)
    val l = colorInterpolator(startHSL.l, endHSL.l)

    return fun(percent: Percent) = Colors.hsl(Angle(h(percent)), s(percent), l(percent))
}

/*fun uninterpolateHsl(start:HSL, end:HSL, long:Boolean): (HSL) -> Double {
    val colorInterpolator = ungamma()

    val h = if (!long) interpolateHue(start.h, end.h) else colorInterpolator(start.h.deg, end.h.deg)
    val s = colorInterpolator(start.s, end.s)
    val l = colorInterpolator(start.l, end.l)

    return fun(color:HSL) =

    return { .0 }
}*/

fun hslLongInterpolator(start:Color, end: Color) = interpolateHsl(start, end, long = true)
fun hslInterpolator(start:Color, end:Color) = interpolateHsl(start, end, long = false)

