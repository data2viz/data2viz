package io.data2viz.interpolate

import io.data2viz.color.HSL
import io.data2viz.color.colors.hsla
import io.data2viz.math.deg

// TODO must take all types of colors in args (currently HSL only)
// TODO add alpha interpolation
// TODO List instead of start, end ? (validate and check size !!)
// TODO use type for parameter percent ?
// TODO use gamma ?
private fun interpolateHsl(start:HSL, end:HSL, long:Boolean): (Double) -> HSL {
    val colorInterpolator = gamma()

    val h = if (!long) hue(start.h, end.h) else colorInterpolator(start.h.deg, end.h.deg)
    val s = colorInterpolator(start.s, end.s)
    val l = colorInterpolator(start.l, end.l)

    return fun(percent:Number) = hsla(h(percent.toDouble()).deg, s(percent.toDouble()), l(percent.toDouble()))
}

/*fun uninterpolateHsl(start:HSL, end:HSL, long:Boolean): (HSL) -> Double {
    val colorInterpolator = ungamma()

    val h = if (!long) hue(start.h, end.h) else colorInterpolator(start.h.deg, end.h.deg)
    val s = colorInterpolator(start.s, end.s)
    val l = colorInterpolator(start.l, end.l)

    return fun(color:HSL) =

    return { .0 }
}*/

fun interpolateHslLong(start:HSL, end:HSL) = interpolateHsl(start, end, long = true)
fun interpolateHsl(start:HSL, end:HSL) = interpolateHsl(start, end, long = false)

