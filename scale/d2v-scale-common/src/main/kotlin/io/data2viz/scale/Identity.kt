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
open class IdentityScale : DomainToRangeScale<Double>(::interpolateNumber, ::uninterpolateNumber, naturalOrder<Double>()) {

    override val domain = _domain
    override val range = _range
    override val clamp = false

    init {
        _domain.clear()
        _domain.addAll(arrayListOf(.0, 1.0))
        _range.clear()
        _range.addAll(arrayListOf(.0, 1.0))
    }
}

fun identityScale() = IdentityScale()