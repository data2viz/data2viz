package io.data2viz.scale

import io.data2viz.color.HSL
import io.data2viz.core.tickStep
import io.data2viz.interpolate.interpolateHsl
import io.data2viz.interpolate.interpolateNumber
import io.data2viz.interpolate.interpolateRound
import io.data2viz.interpolate.uninterpolateNumber
import kotlin.math.ceil
import kotlin.math.floor


fun scaleLinear(init:ContinuousScale<Double>.() -> Unit = {}): ContinuousScale<Double> = ContinuousScale(::interpolateNumber, ::uninterpolateNumber, naturalOrder<Double>()).apply(init)
fun scaleLinearRound(): ContinuousScale<Double> = ContinuousScale(::interpolateRound, ::uninterpolateNumber, naturalOrder<Double>())
fun linearScaleHSL(): ContinuousScale<HSL> = ContinuousScale(::interpolateHsl)

/**
 * Identity scales are a special case of linear scales where the domain and range are identical;
 * the scale and its invert method are thus the identity function. These scales are occasionally useful when
 * working with pixel coordinates, say in conjunction with an axis or brush.
 * Identity scales do not support rangeRound, clamp or interpolate.
 */
fun scaleIdentity() = ContinuousScale(::interpolateNumber, ::uninterpolateNumber, naturalOrder()).apply {
    domain = listOf(.0, 1.0)
    range = listOf(.0, 1.0)
}