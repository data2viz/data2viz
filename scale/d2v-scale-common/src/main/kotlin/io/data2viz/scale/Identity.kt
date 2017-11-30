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

    // TODO : find a better way to do this...
    override var domain: List<Double>
        get() = _domain.toList()
        set(value) = throw RuntimeException("Identity Scale has a constant domain that must not be modified.")

    override var range: List<Double>
        get() = _range.toList()
        set(value) = throw RuntimeException("Identity Scale has a constant range that must not be modified.")

    override var clamp: Boolean = false
        set(value) = throw RuntimeException("Identity Scale has a constant clamping value (false) that must not be modified.")

    init {
        _domain.clear()
        _domain.addAll(arrayListOf(.0, 1.0))
        _range.clear()
        _range.addAll(arrayListOf(.0, 1.0))
    }
}

fun identityScale() = IdentityScale()