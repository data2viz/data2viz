package io.data2viz.geo.projection

import io.data2viz.geo.geometry.asin
import io.data2viz.geo.projection.common.MutableProjection
import io.data2viz.geo.projection.common.projection
import kotlin.math.sqrt

fun azimuthalEqualAreaProjection() = azimuthalEqualAreaProjection {}

fun azimuthalEqualAreaProjection(init: MutableProjection.() -> Unit) =
    projection(AzimuthalEqualArea()) {
        scale = 124.75
        clipAngle = 180 - 1e-3
        init()
    }

private val scale: (Double) -> Double = { cxcy -> sqrt(2 / (1 + cxcy)) }
private val angle: (Double) -> Double = { z -> 2 * (z / 2).asin }

class AzimuthalEqualArea: AzimuthalProjector(scale, angle)