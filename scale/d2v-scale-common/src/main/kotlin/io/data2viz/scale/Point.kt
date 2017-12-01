package io.data2viz.scale

/**
 * Point scales are a variant of band scales with the bandwidth fixed to zero.
 * Point scales are typically used for scatterplots with an ordinal or categorical dimension.
 * The unknown value of a point scale is always NaN: they do not allow implicit domain construction.
 */
class PointScale<D> : BandedScale<D>() {

    /**
     * Sets the outer padding to the specified value which must be in the range [0, 1].
     * Returns the current outer padding which defaults to 0.
     * The outer padding determines the ratio of the range that is reserved for blank space before the first
     * point and after the last point. Equivalent to band.paddingOuter.
     */
    var padding:Double
        get() = _paddingOuter
        set(value) {
            _paddingOuter = value
            rescale()
        }

    init {
        _paddingInner = 1.0
        rescale()
    }
}

fun <D> scalePoint() = PointScale<D>()