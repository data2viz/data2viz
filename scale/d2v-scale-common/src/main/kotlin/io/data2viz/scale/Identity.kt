package io.data2viz.scale

import io.data2viz.interpolate.interpolateNumber
import io.data2viz.interpolate.interpolateRound
import io.data2viz.interpolate.uninterpolateNumber
import kotlin.math.*

/**
 * Identity scales are a special case of linear scales where the domain and range are identical;
 * the scale and its invert method are thus the identity function. These scales are occasionally useful when
 * working with pixel coordinates, say in conjunction with an axis or brush.
 * Identity scales do not support rangeRound, clamp or interpolate.
 */
open class IdentityScale(): LinearScale<Double>(::interpolateNumber, ::uninterpolateNumber, naturalOrder<Double>()) {

    override var domain: MutableList<Double> = arrayListOf(.0, 1.0)
    override var range: MutableList<Double> = arrayListOf(.0, 1.0)
    override var clamp: Boolean = false
}

fun identityScale() = IdentityScale()