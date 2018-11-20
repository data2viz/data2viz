package io.data2viz.interpolate

import io.data2viz.color.*
import io.data2viz.math.*

// TODO add alpha interpolation
// TODO List instead of start, end ? (validate and check size !!)
// TODO use type for parameter percent ?
// TODO use gamma ?
private fun interpolateHsl(start: Color, end:Color, long:Boolean): Interpolator<Color> {
    var startHSL = start.toHsl()
    var endHSL = end.toHsl()
    val colorInterpolator = gamma()

    if (startHSL.isAchromatic()) startHSL = Colors.hsl(endHSL.h, endHSL.s, startHSL.l, startHSL.alpha)
    if (endHSL.isAchromatic()) endHSL = Colors.hsl(startHSL.h, startHSL.s, endHSL.l, endHSL.alpha)

    val h = interpolateHue(startHSL.h, endHSL.h, long)
    val s = colorInterpolator(startHSL.s.value, endHSL.s.value)
    val l = colorInterpolator(startHSL.l.value, endHSL.l.value)

    return fun(percent:Percent) = Colors.hsl(Angle(h(percent)), Percent(s(percent)), Percent(l(percent)))
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

