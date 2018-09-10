package io.data2viz.scale

import io.data2viz.color.RgbColor
import io.data2viz.color.EncodedColors
import io.data2viz.color.HslColor
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
        fun linearHSL(): LinearScale<HslColor> = LinearScale(::interpolateHsl)


        fun pow(exponent: Double = 1.0): PowerScale<Double> = PowerScale(exponent, ::interpolateNumber, ::uninterpolateNumber, naturalOrder())
        fun powRound(exponent: Double = 1.0): PowerScale<Double> = PowerScale(exponent, ::interpolateRound, ::uninterpolateNumber, naturalOrder())

        fun sqrt(): PowerScale<Double> = PowerScale(.5, ::interpolateNumber, ::uninterpolateNumber, naturalOrder())
        fun sqrtRound(): PowerScale<Double> = PowerScale(.5, ::interpolateRound, ::uninterpolateNumber, naturalOrder())

        fun time(): TimeScale<Double> = TimeScale(::interpolateNumber, ::uninterpolateNumber, naturalOrder())
    }

    object colors {

        fun <D> category10()       = OrdinalScale<D, RgbColor>(EncodedColors.category10.colors )
        fun <D> category20()       = OrdinalScale<D, RgbColor>(EncodedColors.category20.colors )
        fun <D> category20b()      = OrdinalScale<D, RgbColor>(EncodedColors.category20b.colors)
        fun <D> category20c()      = OrdinalScale<D, RgbColor>(EncodedColors.category20c.colors)
        /*fun <D> categoryViridis()  = OrdinalScale<D, Color>(EncodedColors.viridis.colors    )
        fun <D> categoryMagma()    = OrdinalScale<D, Color>(EncodedColors.magma.colors      )
        fun <D> categoryInferno()  = OrdinalScale<D, Color>(EncodedColors.inferno.colors    )
        fun <D> categoryPlasma()   = OrdinalScale<D, Color>(EncodedColors.plasma.colors     )*/

        fun sequentialBrBG()    = SequentialScale(interpolateRgbBasis(EncodedColors.BrBG.last().colors))
        fun sequentialPiYG()    = SequentialScale(interpolateRgbBasis(EncodedColors.PiYG.last().colors))
        fun sequentialPRGn()    = SequentialScale(interpolateRgbBasis(EncodedColors.PRGn.last().colors))
        fun sequentialPuOR()    = SequentialScale(interpolateRgbBasis(EncodedColors.PuOR.last().colors))
        fun sequentialRdBU()    = SequentialScale(interpolateRgbBasis(EncodedColors.RdBU.last().colors))
        fun sequentialRdGY()    = SequentialScale(interpolateRgbBasis(EncodedColors.RdGY.last().colors))
        fun sequentialRdYlBu()  = SequentialScale(interpolateRgbBasis(EncodedColors.RdYlBu.last().colors))
        fun sequentialRdYlGn()  = SequentialScale(interpolateRgbBasis(EncodedColors.RdYlGn.last().colors))
        fun sequentialSpectral()= SequentialScale(interpolateRgbBasis(EncodedColors.spectral.last().colors))

        fun sequentialViridis() = SequentialScale(interpolateRgbBasis(EncodedColors.viridis.colors))
        fun sequentialMagma()   = SequentialScale(interpolateRgbBasis(EncodedColors.magma.colors))
        fun sequentialInferno() = SequentialScale(interpolateRgbBasis(EncodedColors.inferno.colors))
        fun sequentialPlasma()  = SequentialScale(interpolateRgbBasis(EncodedColors.plasma.colors))

        fun sequentialBuGN()    = SequentialScale(interpolateRgbBasis(EncodedColors.BuGN.last().colors))
        fun sequentialBuPu()    = SequentialScale(interpolateRgbBasis(EncodedColors.BuPu.last().colors))
        fun sequentialGnBu()    = SequentialScale(interpolateRgbBasis(EncodedColors.GnBu.last().colors))
        fun sequentialOrRd()    = SequentialScale(interpolateRgbBasis(EncodedColors.OrRd.last().colors))
        fun sequentialPuBu()    = SequentialScale(interpolateRgbBasis(EncodedColors.PuBu.last().colors))
        fun sequentialPuBuGn()  = SequentialScale(interpolateRgbBasis(EncodedColors.PuBuGn.last().colors))
        fun sequentialPuRd()    = SequentialScale(interpolateRgbBasis(EncodedColors.PuRd.last().colors))
        fun sequentialRdPu()    = SequentialScale(interpolateRgbBasis(EncodedColors.RdPu.last().colors))
        fun sequentialYlGn()    = SequentialScale(interpolateRgbBasis(EncodedColors.YlGn.last().colors))
        fun sequentialYlGnbU()  = SequentialScale(interpolateRgbBasis(EncodedColors.YlGnbU.last().colors))
        fun sequentialYlGnBr()  = SequentialScale(interpolateRgbBasis(EncodedColors.YlGnBr.last().colors))
        fun sequentialYlGnRd()  = SequentialScale(interpolateRgbBasis(EncodedColors.YlGnRd.last().colors))
        fun sequentialBlues()   = SequentialScale(interpolateRgbBasis(EncodedColors.blues.last().colors))
        fun sequentialGreens()  = SequentialScale(interpolateRgbBasis(EncodedColors.greens.last().colors))
        fun sequentialGreys()   = SequentialScale(interpolateRgbBasis(EncodedColors.greys.last().colors))
        fun sequentialOranges() = SequentialScale(interpolateRgbBasis(EncodedColors.oranges.last().colors))
        fun sequentialPurples() = SequentialScale(interpolateRgbBasis(EncodedColors.purples.last().colors))
        fun sequentialReds()    = SequentialScale(interpolateRgbBasis(EncodedColors.reds.last().colors))

    }
}
