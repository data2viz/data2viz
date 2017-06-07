package io.data2viz.interpolate

import io.data2viz.color.HSL
import io.data2viz.math.deg

// TODO must take all types of colors in args (currently RGB only)
// TODO add alpha interpolation
// TODO List instead of start, end ? (validate and check size !!)
// TODO use gamma ?
fun interpolateHsl(start:HSL, end:HSL, long:Boolean = false): (Number) -> HSL {
    val hueInterpolator = hue(start.h, end.h)
    val colorInterpolator = gamma()

    val hslList = arrayListOf(start, end)

    val h = if (!long) hueInterpolator else colorInterpolator(hslList.map { item -> item.h.deg })
    val s = colorInterpolator(hslList.map { item -> item.s })
    val l = colorInterpolator(hslList.map { item -> item.l })

    return fun(percent:Number) = io.data2viz.color.colors.hsla(h(percent.toDouble()).deg, s(percent.toDouble()), l(percent.toDouble()))
}

fun interpolateHslLong(start:HSL, end:HSL) = interpolateHsl(start, end, true)