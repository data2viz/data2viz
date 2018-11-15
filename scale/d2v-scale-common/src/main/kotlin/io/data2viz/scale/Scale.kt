package io.data2viz.scale

import io.data2viz.color.Color
import io.data2viz.color.EncodedColors
import io.data2viz.geom.Point
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
 * Access the data2viz collection of scales.
 * To create a color scale use ScalesChomatic instead.
 * Available factories:
 *
 * Scales
 *      Continuous      (continuous domain to continuous range)
 *          -> linear
 *          -> log
 *          -> time
 *          -> ...
 *      Quantized       (continuous domain to discrete range)
 *          -> threshold
 *          -> quantize
 *          -> quantile
 *      Discrete        (discrete domain)
 *          -> band
 *          -> point
 *          -> ...
 */
object Scales {

    internal fun <D, R> ordinal(init: OrdinalScale<D, R>.() -> Unit = {}) = OrdinalScale<D, R>().apply(init)
    internal fun sequential(interpolator: Interpolator<Double>) = SequentialScale(interpolator)

    object Continuous {


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

        fun vector(init: LinearScale<Point>.() -> Unit = {}) =
            LinearScale(::interpolatePoint, ::uninterpolatePointOnX, PointComparatorX()).apply(init)

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

    object Quantized {
        fun <R> quantile(init: QuantileScale<R>.() -> Unit = {}): QuantileScale<R> = QuantileScale<R>().apply(init)
        fun <R> quantize(init: QuantizeScale<R>.() -> Unit = {}): QuantizeScale<R> = QuantizeScale<R>().apply(init)
        fun <R> threshold(init: ThresholdScale<R>.() -> Unit = {}): ThresholdScale<R> = ThresholdScale<R>().apply(init)
    }

    object Discrete {
        fun <D> point(init: PointScale<D>.() -> Unit = {}) = PointScale<D>().apply(init)
        fun <D> band(init: BandScale<D>.() -> Unit = {}) = BandScale<D>().apply(init)
    }
}

/**
 * Access the data2viz collection of chromatic scales.
 * To create a non-color scale use Scales instead.
 * Available factories:
 *
 * ScalesChromatic
 *      Continuous              (continuous color scales with pre-configured interpolators)
 *          -> linearRGB
 *          -> linearLAB
 *          -> ...
 *      Sequential              (continuous color scales with pre-configured interpolators and color scheme)
 *          SingleHue               (will scale over a single hue ie. white -> blue)
 *              -> blues
 *              -> greens
 *              -> ...
 *          MultiHue                (convergent scales with multiple hues ie. blue -> green)
 *              -> viridis
 *              -> warm
 *              -> blue_green
 *              -> ...
 *          Diverging               (divergent scales with multiple hues ie. blue -> white -> red for temperature)
 *              -> pink_green
 *              -> red_blue
 *              -> ...
 *          Cyclical                (these color scales cycle: start color = end color)
 *              -> rainbow
 *              -> sinebow
 *      Discrete                (contrasted color scheme for discrete domain scales)
 *          -> category10
 *          -> accent8
 *          -> paired12
 *          -> ...
 */
object ScalesChromatic {

    object Continuous {
        fun linearRGB(init: LinearScale<Color>.() -> Unit = {}) = LinearScale(::rgbLinearInterpolator).apply(init)
        fun defaultRGB(init: LinearScale<Color>.() -> Unit = {}) = LinearScale(::rgbDefaultInterpolator).apply(init)
        fun linearLAB(init: LinearScale<Color>.() -> Unit = {}) = LinearScale(::labInterpolator).apply(init)
        fun linearHCL(init: LinearScale<Color>.() -> Unit = {}) = LinearScale(::hclInterpolator).apply(init)
        fun linearHCLLong(init: LinearScale<Color>.() -> Unit = {}) = LinearScale(::hclLongInterpolator).apply(init)
        fun linearHSL(init: LinearScale<Color>.() -> Unit = {}) = LinearScale(::hslInterpolator).apply(init)
        fun linearHSLLong(init: LinearScale<Color>.() -> Unit = {}) = LinearScale(::hslLongInterpolator).apply(init)
    }

    object Sequential {

        object SingleHue {
            fun blues(init: SequentialScale<Color>.() -> Unit = {}) =
                SequentialScale(rgbBasisInterpolator(EncodedColors.blues.last().colors)).apply(init)

            fun greens(init: SequentialScale<Color>.() -> Unit = {}) =
                SequentialScale(rgbBasisInterpolator(EncodedColors.greens.last().colors)).apply(init)

            fun greys(init: SequentialScale<Color>.() -> Unit = {}) =
                SequentialScale(rgbBasisInterpolator(EncodedColors.greys.last().colors)).apply(init)

            fun oranges(init: SequentialScale<Color>.() -> Unit = {}) =
                SequentialScale(rgbBasisInterpolator(EncodedColors.oranges.last().colors)).apply(init)

            fun purples(init: SequentialScale<Color>.() -> Unit = {}) =
                SequentialScale(rgbBasisInterpolator(EncodedColors.purples.last().colors)).apply(init)

            fun reds(init: SequentialScale<Color>.() -> Unit = {}) =
                SequentialScale(rgbBasisInterpolator(EncodedColors.reds.last().colors)).apply(init)
        }

        object MultiHue {
            fun viridis(init: SequentialScale<Color>.() -> Unit = {}) =
                SequentialScale(rgbBasisInterpolator(EncodedColors.viridis.colors)).apply(init)

            fun magma(init: SequentialScale<Color>.() -> Unit = {}) =
                SequentialScale(rgbBasisInterpolator(EncodedColors.magma.colors)).apply(init)

            fun inferno(init: SequentialScale<Color>.() -> Unit = {}) =
                SequentialScale(rgbBasisInterpolator(EncodedColors.inferno.colors)).apply(init)

            fun plasma(init: SequentialScale<Color>.() -> Unit = {}) =
                SequentialScale(rgbBasisInterpolator(EncodedColors.plasma.colors)).apply(init)

            // TODO warm, cool, cubehelix

            fun blue_green(init: SequentialScale<Color>.() -> Unit = {}) =
                SequentialScale(rgbBasisInterpolator(EncodedColors.BuGN.last().colors)).apply(init)

            fun blue_purple(init: SequentialScale<Color>.() -> Unit = {}) =
                SequentialScale(rgbBasisInterpolator(EncodedColors.BuPu.last().colors)).apply(init)

            fun green_blue(init: SequentialScale<Color>.() -> Unit = {}) =
                SequentialScale(rgbBasisInterpolator(EncodedColors.GnBu.last().colors)).apply(init)

            fun orange_red(init: SequentialScale<Color>.() -> Unit = {}) =
                SequentialScale(rgbBasisInterpolator(EncodedColors.OrRd.last().colors)).apply(init)

            fun purple_blue(init: SequentialScale<Color>.() -> Unit = {}) =
                SequentialScale(rgbBasisInterpolator(EncodedColors.PuBu.last().colors)).apply(init)

            fun purple_blue_green(init: SequentialScale<Color>.() -> Unit = {}) =
                SequentialScale(rgbBasisInterpolator(EncodedColors.PuBuGn.last().colors)).apply(init)

            fun purple_red(init: SequentialScale<Color>.() -> Unit = {}) =
                SequentialScale(rgbBasisInterpolator(EncodedColors.PuRd.last().colors)).apply(init)

            fun red_purple(init: SequentialScale<Color>.() -> Unit = {}) =
                SequentialScale(rgbBasisInterpolator(EncodedColors.RdPu.last().colors)).apply(init)

            fun yellow_green(init: SequentialScale<Color>.() -> Unit = {}) =
                SequentialScale(rgbBasisInterpolator(EncodedColors.YlGn.last().colors)).apply(init)

            fun yellow_green_blue(init: SequentialScale<Color>.() -> Unit = {}) =
                SequentialScale(rgbBasisInterpolator(EncodedColors.YlGnbU.last().colors)).apply(init)

            fun yellow_green_brown(init: SequentialScale<Color>.() -> Unit = {}) =
                SequentialScale(rgbBasisInterpolator(EncodedColors.YlGnBr.last().colors)).apply(init)

            fun yellow_green_red(init: SequentialScale<Color>.() -> Unit = {}) =
                SequentialScale(rgbBasisInterpolator(EncodedColors.YlGnRd.last().colors)).apply(init)

            // TODO YlOrBr, YlOrRd
        }

        object Diverging {
            fun brown_blueGreen(init: SequentialScale<Color>.() -> Unit = {}) =
                SequentialScale(rgbBasisInterpolator(EncodedColors.BrBG.last().colors)).apply(init)

            fun pink_green(init: SequentialScale<Color>.() -> Unit = {}) =
                SequentialScale(rgbBasisInterpolator(EncodedColors.PiYG.last().colors)).apply(init)

            fun purple_green(init: SequentialScale<Color>.() -> Unit = {}) =
                SequentialScale(rgbBasisInterpolator(EncodedColors.PRGn.last().colors)).apply(init)

            fun purple_orange(init: SequentialScale<Color>.() -> Unit = {}) =
                SequentialScale(rgbBasisInterpolator(EncodedColors.PuOR.last().colors)).apply(init)

            fun red_blue(init: SequentialScale<Color>.() -> Unit = {}) =
                SequentialScale(rgbBasisInterpolator(EncodedColors.RdBU.last().colors)).apply(init)

            fun red_grey(init: SequentialScale<Color>.() -> Unit = {}) =
                SequentialScale(rgbBasisInterpolator(EncodedColors.RdGY.last().colors)).apply(init)

            fun red_yelow_blue(init: SequentialScale<Color>.() -> Unit = {}) =
                SequentialScale(rgbBasisInterpolator(EncodedColors.RdYlBu.last().colors)).apply(init)

            fun red_yellow_green(init: SequentialScale<Color>.() -> Unit = {}) =
                SequentialScale(rgbBasisInterpolator(EncodedColors.RdYlGn.last().colors)).apply(init)

            fun spectral(init: SequentialScale<Color>.() -> Unit = {}) =
                SequentialScale(rgbBasisInterpolator(EncodedColors.spectral.last().colors)).apply(init)
        }

        object Cyclical {
            // TODO rainbow, sinebow
        }
    }

    object Discrete {
        fun <D> category10(init: OrdinalScale<D, Color>.() -> Unit = {}) =
            OrdinalScale<D, Color>(EncodedColors.category10.colors).apply(init)

        fun <D> category20(init: OrdinalScale<D, Color>.() -> Unit = {}) =
            OrdinalScale<D, Color>(EncodedColors.category20.colors).apply(init)

        fun <D> category20b(init: OrdinalScale<D, Color>.() -> Unit = {}) =
            OrdinalScale<D, Color>(EncodedColors.category20b.colors).apply(init)

        fun <D> category20c(init: OrdinalScale<D, Color>.() -> Unit = {}) =
            OrdinalScale<D, Color>(EncodedColors.category20c.colors).apply(init)

        // TODO accent, paired, pastel... https://github.com/d3/d3-scale-chromatic#categorical
    }
}