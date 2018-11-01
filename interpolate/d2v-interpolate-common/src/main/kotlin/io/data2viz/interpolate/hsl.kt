package io.data2viz.interpolate

import io.data2viz.color.Color
import io.data2viz.color.Colors
import io.data2viz.color.toHsla
import io.data2viz.math.Angle
import io.data2viz.math.deg

// TODO add alpha interpolation
// TODO List instead of start, end ? (validate and check size !!)
// TODO use type for parameter percent ?
// TODO use gamma ?
private fun interpolateHsl(start: Color, end:Color, long:Boolean): (Double) -> Color {
    var startHSL = start.toRgb().toHsla()
    var endHSL = end.toRgb().toHsla()
    val colorInterpolator = gamma()

    if (startHSL.isAchromatic()) startHSL = Colors.hsl(endHSL.h, endHSL.s, startHSL.l, startHSL.alpha)
    if (endHSL.isAchromatic()) endHSL = Colors.hsl(startHSL.h, startHSL.s, endHSL.l, endHSL.alpha)

    val h = if (!long) hue(startHSL.h, endHSL.h) else angle(startHSL.h, endHSL.h)
    val s = colorInterpolator(startHSL.s, endHSL.s)
    val l = colorInterpolator(startHSL.l, endHSL.l)

    return fun(percent:Double) = Colors.hsl(Angle(h(percent)), s(percent), l(percent))
}

/*fun uninterpolateHsl(start:HSL, end:HSL, long:Boolean): (HSL) -> Double {
    val colorInterpolator = ungamma()

    val h = if (!long) hue(start.h, end.h) else colorInterpolator(start.h.deg, end.h.deg)
    val s = colorInterpolator(start.s, end.s)
    val l = colorInterpolator(start.l, end.l)

    return fun(color:HSL) =

    return { .0 }
}*/

fun interpolateHslLong(start:Color, end: Color) = interpolateHsl(start, end, long = true)
fun interpolateHsl(start:Color, end:Color) = interpolateHsl(start, end, long = false)

