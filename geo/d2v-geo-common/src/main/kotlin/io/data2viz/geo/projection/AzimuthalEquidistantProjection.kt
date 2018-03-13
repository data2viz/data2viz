package io.data2viz.geo.projection

import io.data2viz.geo.acos
import kotlin.math.sin

fun azimuthalEquidistant(init: MutableProjection.() -> Unit) = projection(AzimuthalEquidistantProjection()) {
    scale = 79.4188
    clipAngle = 180 - 1e-3
    init()
}

private val scale = { cxcy: Double ->
    val c = cxcy.acos
    if (c != .0) c / sin(c) else c
}
private val angle: (Double) -> Double = { z -> z }

class AzimuthalEquidistantProjection: Azimuthal(scale, angle)