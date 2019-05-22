package io.data2viz.geo.projection

import io.data2viz.geo.geometry.acos
import io.data2viz.geo.geometry.clip.anglePreClip
import io.data2viz.geo.projection.common.ProjectableProjection
import io.data2viz.geo.projection.common.projection
import io.data2viz.math.deg
import kotlin.math.sin

fun azimuthalEquidistant() = azimuthalEquidistant {}

fun azimuthalEquidistant(init: ProjectableProjection.() -> Unit) =
    projection(AzimuthalEquidistantProjection()) {
        scale = 79.4188
        anglePreClip = (180 - 1e-3).deg
        init()
    }

private val scale = { cxcy: Double ->
    val c = cxcy.acos
    if (c != .0) c / sin(c) else c
}
private val angle: (Double) -> Double = { z -> z }

class AzimuthalEquidistantProjection : AzimuthalProjector(scale, angle)