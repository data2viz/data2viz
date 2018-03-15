package io.data2viz.scale

import io.data2viz.color.Color
import io.data2viz.color.EncodedColors
import io.data2viz.color.HSL
import io.data2viz.interpolate.*

/**
 * Generic signature of scales.
 *
 * A scale can map a domain object dimension D to a representation value.
 *
 * Then at runtime, one can ask an R object for a specific value of domain.
 * The rules defining the returns of R from D depends a lot on the type and
 * implementation of the Scale.
 *
 * a Domain object -> Range object
 */
interface Scale<D, out R> {
    operator fun invoke(domainValue: D): R
}

interface ContinuousDomain<D> {
    var domain: List<D>
}

interface DiscreteDomain<D> {
    var domain: List<D>
}

interface StrictlyContinuousDomain<D> {
    var domain: StrictlyContinuous<D>
}

interface ContinuousRangeScale<D, R>: Scale<D, R>, FirstLastRange<D,R>{
    var range: List<R>
    override fun start() = range.first()
    override fun end() = range.last()
}

interface DiscreteRange<R> {
    var range:List<R>
}

interface StrictlyContinuousRange<D, R>: FirstLastRange<D, R> {
    var range: StrictlyContinuous<R>
    override fun start() = range.start
    override fun end() = range.end
}

interface FirstLastRange<D,R>: Scale<D,R>{
    fun start():R
    fun end():R
}

/**
 * A stricly continuous dimension is only defined by its start and end.
 * There is not intermediary value.
 */
data class StrictlyContinuous<D>(val start:D, val end:D)

fun <D> intervalOf(start:D, end:D) = StrictlyContinuous(start,end)
fun <D> intervalOf(vararg  values:D) = StrictlyContinuous(values.first(), values.last())


/**
 * Indicates a scale for which the resulting R
 */
interface ClampableScale  {
    val clamp: Boolean
}

interface NiceableScale<D> :ContinuousDomain<D> {
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

    fun <D, R> ordinal() = OrdinalScale<D, R>()
    fun <R> quantile(): QuantileScale<R> = QuantileScale()
    fun <R> quantize(): QuantizeScale<R> = QuantizeScale()
    fun <R> threshold(): ThresholdScale<R> = ThresholdScale()
    fun <D> point() = PointScale<D>()
    fun <D> band() = BandScale<D>()
    fun <D> band(domain: List<D>, init: BandScale<D>.() -> Unit) =
            BandScale<D>().apply {
                this.domain = domain
                init(this)
        }


    object continuous {

        fun sequential(interpolator: Interpolator<Double>) = SequentialScale(interpolator)

        fun log(base: Double = 10.0, init:ContinuousScale<Double, Double>.() -> Unit = {}): ContinuousScale<Double, Double> =
                LogScale(base, ::interpolateNumber, ::uninterpolateNumber, naturalOrder()).apply(init)

        /**
         * TODO Test
         */
        fun logRound(base: Double = 10.0): ContinuousScale<Double, Double> =
                LogScale(base, ::interpolateRound, ::uninterpolateNumber, naturalOrder())


        /**
         * Identity scales are a special case of linear scales where the domain and range are identical;
         * the scale and its invert method are thus the identity function. These scales are occasionally useful when
         * working with pixel coordinates, say in conjunction with an axis or brush.
         * Identity scales do not support rangeRound, clamp or interpolate.
         */
        fun identity() = LinearScale(::interpolateNumber, ::uninterpolateNumber, naturalOrder()).apply {
            domain = listOf(.0, 1.0)
            range = listOf(.0, 1.0)
        }

        fun linear(init:LinearScale<Double>.() -> Unit = {}): LinearScale<Double> = LinearScale(::interpolateNumber, ::uninterpolateNumber, naturalOrder()).apply(init)
        fun linearRound(): LinearScale<Double> = LinearScale(::interpolateRound, ::uninterpolateNumber, naturalOrder())
        fun linearHSL(): LinearScale<HSL> = LinearScale(::interpolateHsl)


        fun pow(exponent: Double = 1.0): PowerScale<Double> = PowerScale(exponent, ::interpolateNumber, ::uninterpolateNumber, naturalOrder())
        fun powRound(exponent: Double = 1.0): PowerScale<Double> = PowerScale(exponent, ::interpolateRound, ::uninterpolateNumber, naturalOrder())

        fun sqrt(): PowerScale<Double> = PowerScale(.5, ::interpolateNumber, ::uninterpolateNumber, naturalOrder())
        fun sqrtRound(): PowerScale<Double> = PowerScale(.5, ::interpolateRound, ::uninterpolateNumber, naturalOrder())

        fun time(): TimeScale<Double> = TimeScale(::interpolateNumber, ::uninterpolateNumber, naturalOrder())
    }

    object colors {

        fun <D> category10()       = OrdinalScale<D, Color>(EncodedColors.category10.colors )
        fun <D> category20()       = OrdinalScale<D, Color>(EncodedColors.category20.colors )
        fun <D> category20b()      = OrdinalScale<D, Color>(EncodedColors.category20b.colors)
        fun <D> category20c()      = OrdinalScale<D, Color>(EncodedColors.category20c.colors)
        fun <D> categoryViridis()  = OrdinalScale<D, Color>(EncodedColors.viridis.colors    )
        fun <D> categoryMagma()    = OrdinalScale<D, Color>(EncodedColors.magma.colors      )
        fun <D> categoryInferno()  = OrdinalScale<D, Color>(EncodedColors.inferno.colors    )
        fun <D> categoryPlasma()   = OrdinalScale<D, Color>(EncodedColors.plasma.colors     )

        fun sequentialViridis() = SequentialScale(interpolateRgbBasis(EncodedColors.viridis.colors))
        fun sequentialMagma()   = SequentialScale(interpolateRgbBasis(EncodedColors.magma.colors))
        fun sequentialInferno() = SequentialScale(interpolateRgbBasis(EncodedColors.inferno.colors))
        fun sequentialPlasma()  = SequentialScale(interpolateRgbBasis(EncodedColors.plasma.colors))
        fun sequentialOranges() = SequentialScale(interpolateRgbBasis(EncodedColors.oranges.last().colors))

    }
}
