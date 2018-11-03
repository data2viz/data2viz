package io.data2viz.interpolate

import io.data2viz.color.*
import io.data2viz.math.Angle

// TODO use type for parameter percent ?
// TODO use gamma ?
private fun interpolateHcl(start: Color, end:Color, long:Boolean): (Double) -> Color {
    var startHCL = start.toRgb().toLab().toHcla()
    var endHCL = end.toRgb().toLab().toHcla()
    val colorInterpolator = gamma()

    if (startHCL.isAchromatic()) startHCL = Colors.hcl(endHCL.h, startHCL.c, startHCL.l, startHCL.alpha)
    if (endHCL.isAchromatic()) endHCL = Colors.hcl(startHCL.h, endHCL.c, endHCL.l, endHCL.alpha)

    val h = interpolateHue(startHCL.h, endHCL.h, long)
    val c = colorInterpolator(startHCL.c, endHCL.c)
    val l = colorInterpolator(startHCL.l, endHCL.l)

    return fun(percent:Double) = Colors.hcl(Angle(h(percent)), c(percent), l(percent))
}

fun hclLongInterpolator(start:Color, end: Color) = interpolateHcl(start, end, long = true)
fun hclInterpolator(start:Color, end:Color) = interpolateHcl(start, end, long = false)

