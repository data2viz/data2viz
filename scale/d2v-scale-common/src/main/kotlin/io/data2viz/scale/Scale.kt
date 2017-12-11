package io.data2viz.scale

import io.data2viz.color.Color
import io.data2viz.color.EncodedColors
import io.data2viz.color.HSL
import io.data2viz.interpolate.*


// TODO (DV-63)
/*data class DomainToViz<out D, out V>(
        val domain: D,
        val viz: V          /// ????
)*/

// TODO add more specific interfaces (Roundable ?)


/**
 * Generic signature of scales. A scale is defined by a list of Domain objects. 
 * Then at runtime, one can ask an R object for a specific value of domain.
 * The rules defining the returns of R from D depends a lot on the type and
 * implementation of the Scale.
 *
 * a Domain object -> Range object
 */
interface Scale<D, out R> {
    operator fun invoke(domainValue: D): R
}

interface DiscreteDomain<R>
interface DiscreteRange<R>

interface ContinuousDomain<T> {
    var domain: List<T>
}

interface StrictlyContinuousDomain<T> {
    var domain: StrictlyContinuous<T>
}

data class StrictlyContinuous<D>(val start:D, val end:D)

fun <D> intervalOf(start:D, end:D) = StrictlyContinuous(start,end)
fun <D> intervalOf(vararg  values:D) = StrictlyContinuous(values.first(), values.last())

interface ContinuousRange<T>


/**
 * A scale for which it is possible to define a range List 
 */
interface RangeableScale<D, R> : Scale<D, R> {
    val range: List<R>
}

/**
 * Indicates a scale for which the resulting R
 */
interface ClampableScale  {
    val clamp: Boolean
}

interface NiceableScale<D, R> : Scale<D, R> {
    fun nice(count: Int = 10)
}

interface InvertableScale<D, R> : Scale<D, R> {
    fun invert(rangeValue: R): D
}

/**
 * Can provide ticks from Domain D.
 */
interface Tickable<D>{
    fun ticks(count: Int = 10): List<D>
}


object scales{

    object continuous {
        fun log(base: Double = 10.0, init:ContinuousScale<Double>.() -> Unit = {}): ContinuousScale<Double> =
                LogScale(base, ::interpolateNumber, ::uninterpolateNumber, naturalOrder()).apply(init)

        /**
         * TODO Test
         */
        fun logRound(base: Double = 10.0): ContinuousScale<Double> =
                LogScale(base, ::interpolateRound, ::uninterpolateNumber, naturalOrder())


        /**
         * Identity scales are a special case of linear scales where the domain and range are identical;
         * the scale and its invert method are thus the identity function. These scales are occasionally useful when
         * working with pixel coordinates, say in conjunction with an axis or brush.
         * Identity scales do not support rangeRound, clamp or interpolate.
         */
        fun identity() = ContinuousScale(::interpolateNumber, ::uninterpolateNumber, naturalOrder()).apply {
            domain = listOf(.0, 1.0)
            range = listOf(.0, 1.0)
        }

        fun linear(init:ContinuousScale<Double>.() -> Unit = {}): ContinuousScale<Double> = ContinuousScale(::interpolateNumber, ::uninterpolateNumber, naturalOrder()).apply(init)
        fun linearRound(): ContinuousScale<Double> = ContinuousScale(::interpolateRound, ::uninterpolateNumber, naturalOrder())
        fun linearHSL(): ContinuousScale<HSL> = ContinuousScale(::interpolateHsl)


        fun pow(exponent: Double = 1.0): PowerScale<Double> = PowerScale(exponent, ::interpolateNumber, ::uninterpolateNumber, naturalOrder())
        fun powRound(exponent: Double = 1.0): PowerScale<Double> = PowerScale(exponent, ::interpolateRound, ::uninterpolateNumber, naturalOrder())

        fun sqrt(): PowerScale<Double> = PowerScale(.5, ::interpolateNumber, ::uninterpolateNumber, naturalOrder())
        fun sqrtRound(): PowerScale<Double> = PowerScale(.5, ::interpolateRound, ::uninterpolateNumber, naturalOrder())

    }

    object colors {

        fun <D> category10()       = OrdinalScale<D, Color> (EncodedColors.category10.colors )
        fun <D> category20()       = OrdinalScale<D, Color> (EncodedColors.category20.colors )
        fun <D> category20b()      = OrdinalScale<D, Color> (EncodedColors.category20b.colors)
        fun <D> category20c()      = OrdinalScale<D, Color> (EncodedColors.category20c.colors)
        fun <D> categoryViridis()  = OrdinalScale<D, Color> (EncodedColors.viridis.colors   )
        fun <D> categoryMagma()    = OrdinalScale<D, Color> (EncodedColors.magma.colors     )
        fun <D> categoryInferno()  = OrdinalScale<D, Color> (EncodedColors.inferno.colors   )
        fun <D> categoryPlasma()   = OrdinalScale<D, Color> (EncodedColors.plasma.colors    )
    }
}
