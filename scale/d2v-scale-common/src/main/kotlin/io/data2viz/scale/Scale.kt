package io.data2viz.scale

import io.data2viz.color.Color
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

interface ContinuousRangeScale<D, R> : Scale<D, R>, FirstLastRange<D, R> {
    var range: List<R>
    override fun start() = range.first()
    override fun end() = range.last()
}

interface DiscreteRange<R> {
    var range: List<R>
}

interface StrictlyContinuousRange<D, R> : FirstLastRange<D, R> {
    var range: StrictlyContinuous<R>
    override fun start() = range.start
    override fun end() = range.end
}

interface FirstLastRange<D, R> : Scale<D, R> {
    fun start(): R
    fun end(): R
}

/**
 * A stricly continuous dimension is only defined by its start and end.
 * There is not intermediary value.
 */
data class StrictlyContinuous<D>(val start: D, val end: D)

fun <D> intervalOf(start: D, end: D) = StrictlyContinuous(start, end)
fun <D> intervalOf(vararg values: D) = StrictlyContinuous(values.first(), values.last())


/**
 * Indicates a scale for which the resulting R
 */
interface ClampableScale {
    val clamp: Boolean
}

interface NiceableScale<D> : ContinuousDomain<D> {
    fun nice(count: Int = 10)
}

interface InvertableScale<D, R> : Scale<D, R> {
    fun invert(rangeValue: R): D
}

/**
 * Can provide ticks from Domain D.
 */
interface Tickable<D> {
    fun ticks(count: Int = 10): List<D>
}

/**
 * Access the data2viz collection of scales:
 *  - Continuous scales : linear, power, log, identity, time...
 */
object scales {

    fun <D, R> ordinal(init: OrdinalScale<D, R>.() -> Unit = {}) = OrdinalScale<D, R>().apply(init)
    fun <R> quantile(init: QuantileScale<R>.() -> Unit = {}): QuantileScale<R> = QuantileScale<R>().apply(init)
    fun <R> quantize(init: QuantizeScale<R>.() -> Unit = {}): QuantizeScale<R> = QuantizeScale<R>().apply(init)
    fun <R> threshold(init: ThresholdScale<R>.() -> Unit = {}): ThresholdScale<R> = ThresholdScale<R>().apply(init)
    fun <D> point(init: PointScale<D>.() -> Unit = {}) = PointScale<D>().apply(init)
    fun <D> band(init: BandScale<D>.() -> Unit = {}) = BandScale<D>().apply(init)
    fun <D> band(domain: List<D>, init: BandScale<D>.() -> Unit) =
        BandScale<D>().apply {
            this.domain = domain
            init(this)
        }


    /**
     * Continuous scales map a continuous, quantitative input domain to a continuous output range.
     * If the range is also numeric, the mapping may be inverted.
     * Available continuous scales : linear, power, log, identity or time.
     */
    object continuous {

        internal fun sequential(interpolator: Interpolator<Double>) = SequentialScale(interpolator)

        fun log(
            base: Double = 10.0,
            init: ContinuousScale<Double, Double>.() -> Unit = {}
        ): ContinuousScale<Double, Double> =
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
         */
        fun identity() = LinearScale(::interpolateNumber, ::uninterpolateNumber, naturalOrder()).apply {
            domain = listOf(.0, 1.0)
            range = listOf(.0, 1.0)
        }

        fun linear(init: LinearScale<Double>.() -> Unit = {}) =
            LinearScale(::interpolateNumber, ::uninterpolateNumber, naturalOrder()).apply(init)

        fun linearRound(init: LinearScale<Double>.() -> Unit = {}) =
            LinearScale(::interpolateRound, ::uninterpolateNumber, naturalOrder()).apply(init)

        fun pow(exponent: Double = 1.0, init: PowerScale<Double>.() -> Unit = {}) =
            PowerScale(exponent, ::interpolateNumber, ::uninterpolateNumber, naturalOrder()).apply(init)

        fun powRound(exponent: Double = 1.0, init: PowerScale<Double>.() -> Unit = {}) =
            PowerScale(exponent, ::interpolateRound, ::uninterpolateNumber, naturalOrder()).apply(init)


        fun sqrt(init: PowerScale<Double>.() -> Unit = {}) =
            PowerScale(.5, ::interpolateNumber, ::uninterpolateNumber, naturalOrder()).apply(init)

        fun sqrtRound(init: PowerScale<Double>.() -> Unit = {}) =
            PowerScale(.5, ::interpolateRound, ::uninterpolateNumber, naturalOrder()).apply(init)

        fun time(init: TimeScale<Double>.() -> Unit = {}) =
            TimeScale(::interpolateNumber, ::uninterpolateNumber, naturalOrder()).apply(init)
    }

    object colors {

        fun linearHSL(init: LinearScale<Color>.() -> Unit = {}) =
            LinearScale(::interpolateHsl).apply(init)

        fun linearRGB(init: LinearScale<Color>.() -> Unit = {}) =
            LinearScale(::interpolateRgbDefault).apply(init)

        fun <D> category10(init: OrdinalScale<D, Color>.() -> Unit = {}) =
            OrdinalScale<D, Color>(EncodedColors.category10.colors).apply(init)

        fun <D> category20(init: OrdinalScale<D, Color>.() -> Unit = {}) =
            OrdinalScale<D, Color>(EncodedColors.category20.colors).apply(init)

        fun <D> category20b(init: OrdinalScale<D, Color>.() -> Unit = {}) =
            OrdinalScale<D, Color>(EncodedColors.category20b.colors).apply(init)

        fun <D> category20c(init: OrdinalScale<D, Color>.() -> Unit = {}) =
            OrdinalScale<D, Color>(EncodedColors.category20c.colors).apply(init)

        fun <D> categoryViridis(init: OrdinalScale<D, Color>.() -> Unit = {}) =
            OrdinalScale<D, Color>(EncodedColors.viridis.colors).apply(init)

        fun <D> categoryMagma(init: OrdinalScale<D, Color>.() -> Unit = {}) =
            OrdinalScale<D, Color>(EncodedColors.magma.colors).apply(init)

        fun <D> categoryInferno(init: OrdinalScale<D, Color>.() -> Unit = {}) =
            OrdinalScale<D, Color>(EncodedColors.inferno.colors).apply(init)

        fun <D> categoryPlasma(init: OrdinalScale<D, Color>.() -> Unit = {}) =
            OrdinalScale<D, Color>(EncodedColors.plasma.colors).apply(init)

        fun sequentialBrBG(init: SequentialScale<Color>.() -> Unit = {}) =
            SequentialScale(interpolateRgbBasis(EncodedColors.BrBG.last().colors)).apply(init)

        fun sequentialPiYG(init: SequentialScale<Color>.() -> Unit = {}) =
            SequentialScale(interpolateRgbBasis(EncodedColors.PiYG.last().colors)).apply(init)

        fun sequentialPRGn(init: SequentialScale<Color>.() -> Unit = {}) =
            SequentialScale(interpolateRgbBasis(EncodedColors.PRGn.last().colors)).apply(init)

        fun sequentialPuOR(init: SequentialScale<Color>.() -> Unit = {}) =
            SequentialScale(interpolateRgbBasis(EncodedColors.PuOR.last().colors)).apply(init)

        fun sequentialRdBU(init: SequentialScale<Color>.() -> Unit = {}) =
            SequentialScale(interpolateRgbBasis(EncodedColors.RdBU.last().colors)).apply(init)

        fun sequentialRdGY(init: SequentialScale<Color>.() -> Unit = {}) =
            SequentialScale(interpolateRgbBasis(EncodedColors.RdGY.last().colors)).apply(init)

        fun sequentialRdYlBu(init: SequentialScale<Color>.() -> Unit = {}) =
            SequentialScale(interpolateRgbBasis(EncodedColors.RdYlBu.last().colors)).apply(init)

        fun sequentialRdYlGn(init: SequentialScale<Color>.() -> Unit = {}) =
            SequentialScale(interpolateRgbBasis(EncodedColors.RdYlGn.last().colors)).apply(init)

        fun sequentialSpectral(init: SequentialScale<Color>.() -> Unit = {}) =
            SequentialScale(interpolateRgbBasis(EncodedColors.spectral.last().colors)).apply(init)

        fun sequentialViridis(init: SequentialScale<Color>.() -> Unit = {}) =
            SequentialScale(interpolateRgbBasis(EncodedColors.viridis.colors)).apply(init)

        fun sequentialMagma(init: SequentialScale<Color>.() -> Unit = {}) =
            SequentialScale(interpolateRgbBasis(EncodedColors.magma.colors)).apply(init)

        fun sequentialInferno(init: SequentialScale<Color>.() -> Unit = {}) =
            SequentialScale(interpolateRgbBasis(EncodedColors.inferno.colors)).apply(init)

        fun sequentialPlasma(init: SequentialScale<Color>.() -> Unit = {}) =
            SequentialScale(interpolateRgbBasis(EncodedColors.plasma.colors)).apply(init)

        fun sequentialBuGN(init: SequentialScale<Color>.() -> Unit = {}) =
            SequentialScale(interpolateRgbBasis(EncodedColors.BuGN.last().colors)).apply(init)

        fun sequentialBuPu(init: SequentialScale<Color>.() -> Unit = {}) =
            SequentialScale(interpolateRgbBasis(EncodedColors.BuPu.last().colors)).apply(init)

        fun sequentialGnBu(init: SequentialScale<Color>.() -> Unit = {}) =
            SequentialScale(interpolateRgbBasis(EncodedColors.GnBu.last().colors)).apply(init)

        fun sequentialOrRd(init: SequentialScale<Color>.() -> Unit = {}) =
            SequentialScale(interpolateRgbBasis(EncodedColors.OrRd.last().colors)).apply(init)

        fun sequentialPuBu(init: SequentialScale<Color>.() -> Unit = {}) =
            SequentialScale(interpolateRgbBasis(EncodedColors.PuBu.last().colors)).apply(init)

        fun sequentialPuBuGn(init: SequentialScale<Color>.() -> Unit = {}) =
            SequentialScale(interpolateRgbBasis(EncodedColors.PuBuGn.last().colors)).apply(init)

        fun sequentialPuRd(init: SequentialScale<Color>.() -> Unit = {}) =
            SequentialScale(interpolateRgbBasis(EncodedColors.PuRd.last().colors)).apply(init)

        fun sequentialRdPu(init: SequentialScale<Color>.() -> Unit = {}) =
            SequentialScale(interpolateRgbBasis(EncodedColors.RdPu.last().colors)).apply(init)

        fun sequentialYlGn(init: SequentialScale<Color>.() -> Unit = {}) =
            SequentialScale(interpolateRgbBasis(EncodedColors.YlGn.last().colors)).apply(init)

        fun sequentialYlGnbU(init: SequentialScale<Color>.() -> Unit = {}) =
            SequentialScale(interpolateRgbBasis(EncodedColors.YlGnbU.last().colors)).apply(init)

        fun sequentialYlGnBr(init: SequentialScale<Color>.() -> Unit = {}) =
            SequentialScale(interpolateRgbBasis(EncodedColors.YlGnBr.last().colors)).apply(init)

        fun sequentialYlGnRd(init: SequentialScale<Color>.() -> Unit = {}) =
            SequentialScale(interpolateRgbBasis(EncodedColors.YlGnRd.last().colors)).apply(init)

        fun sequentialBlues(init: SequentialScale<Color>.() -> Unit = {}) =
            SequentialScale(interpolateRgbBasis(EncodedColors.blues.last().colors)).apply(init)

        fun sequentialGreens(init: SequentialScale<Color>.() -> Unit = {}) =
            SequentialScale(interpolateRgbBasis(EncodedColors.greens.last().colors)).apply(init)

        fun sequentialGreys(init: SequentialScale<Color>.() -> Unit = {}) =
            SequentialScale(interpolateRgbBasis(EncodedColors.greys.last().colors)).apply(init)

        fun sequentialOranges(init: SequentialScale<Color>.() -> Unit = {}) =
            SequentialScale(interpolateRgbBasis(EncodedColors.oranges.last().colors)).apply(init)

        fun sequentialPurples(init: SequentialScale<Color>.() -> Unit = {}) =
            SequentialScale(interpolateRgbBasis(EncodedColors.purples.last().colors)).apply(init)

        fun sequentialReds(init: SequentialScale<Color>.() -> Unit = {}) =
            SequentialScale(interpolateRgbBasis(EncodedColors.reds.last().colors)).apply(init)

    }
}
