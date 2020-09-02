/*
 * Copyright (c) 2018-2019. data2viz sàrl.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 *
 */

package io.data2viz.scale

import io.data2viz.color.Color
import io.data2viz.color.EncodedColors
import io.data2viz.geom.Point
import io.data2viz.interpolate.*
import io.data2viz.time.defaultTZ
import kotlin.jvm.JvmName
import kotlin.time.ExperimentalTime

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
 *          -> ordinal
 *          -> band
 *          -> point
 */
object Scales {

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

        @ExperimentalTime
        fun time(init: TimeScale<Double>.() -> Unit = {}) =
            TimeScale(::interpolateNumber, ::uninterpolateNumber, naturalOrder()).apply(init)

        fun sequential(interpolator: Interpolator<Double>) = SequentialScale(interpolator)

        @JvmName("sequentialColor")
        fun sequential(interpolator: Interpolator<Color>) = SequentialScale(interpolator)
    }

    object Quantized {
        fun <R> quantile(init: QuantileScale<R>.() -> Unit = {}): QuantileScale<R> = QuantileScale<R>().apply(init)
        fun <R> quantize(init: QuantizeScale<R>.() -> Unit = {}): QuantizeScale<R> = QuantizeScale<R>().apply(init)
        fun <R> threshold(init: ThresholdScale<R>.() -> Unit = {}): ThresholdScale<R> = ThresholdScale<R>().apply(init)
    }

    object Discrete {
        fun <D> point(init: PointScale<D>.() -> Unit = {}) = PointScale<D>().apply(init)
        fun <D> band(init: BandScale<D>.() -> Unit = {}) = BandScale<D>().apply(init)
        fun <D, R> ordinal(init: OrdinalScale<D, R>.() -> Unit = {}) = OrdinalScale<D, R>().apply(init)
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
 *          -> linearHSL
 *          -> ...
 *      Sequential              (continuous color scales with pre-configured interpolators and color scheme)
 *          SingleHue               (will scale over a single hue ie. white -> blue)
 *              -> blues
 *              -> greens
 *              -> ...
 *          MultiHue                (convergent scales with multiple hues ie. blue -> green)
 *              -> viridis
 *              -> plasma
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

    // TODO these scales should also be available as "ordinal scales"
    object Sequential {

        object SingleHue {
            fun blues(init: SequentialScale<Color>.() -> Unit = {}) =
                SequentialScale(rgbBasisInterpolator(EncodedColors.blues9.colors)).apply(init)

            fun greens(init: SequentialScale<Color>.() -> Unit = {}) =
                SequentialScale(rgbBasisInterpolator(EncodedColors.greens9.colors)).apply(init)

            fun greys(init: SequentialScale<Color>.() -> Unit = {}) =
                SequentialScale(rgbBasisInterpolator(EncodedColors.greys9.colors)).apply(init)

            fun oranges(init: SequentialScale<Color>.() -> Unit = {}) =
                SequentialScale(rgbBasisInterpolator(EncodedColors.oranges9.colors)).apply(init)

            fun purples(init: SequentialScale<Color>.() -> Unit = {}) =
                SequentialScale(rgbBasisInterpolator(EncodedColors.purples9.colors)).apply(init)

            fun reds(init: SequentialScale<Color>.() -> Unit = {}) =
                SequentialScale(rgbBasisInterpolator(EncodedColors.reds9.colors)).apply(init)
        }

        // TODO warm, cool, cubehelix
        // TODO YlOrBr, YlOrRd
        object MultiHue {
            fun viridis(init: SequentialScale<Color>.() -> Unit = {}) =
                SequentialScale(rgbBasisInterpolator(EncodedColors.viridis.colors)).apply(init)

            fun magma(init: SequentialScale<Color>.() -> Unit = {}) =
                SequentialScale(rgbBasisInterpolator(EncodedColors.magma.colors)).apply(init)

            fun inferno(init: SequentialScale<Color>.() -> Unit = {}) =
                SequentialScale(rgbBasisInterpolator(EncodedColors.inferno.colors)).apply(init)

            fun plasma(init: SequentialScale<Color>.() -> Unit = {}) =
                SequentialScale(rgbBasisInterpolator(EncodedColors.plasma.colors)).apply(init)

            fun blue_green(init: SequentialScale<Color>.() -> Unit = {}) =
                SequentialScale(rgbBasisInterpolator(EncodedColors.BuGN9.colors)).apply(init)

            fun blue_purple(init: SequentialScale<Color>.() -> Unit = {}) =
                SequentialScale(rgbBasisInterpolator(EncodedColors.BuPu9.colors)).apply(init)

            fun green_blue(init: SequentialScale<Color>.() -> Unit = {}) =
                SequentialScale(rgbBasisInterpolator(EncodedColors.GnBu9.colors)).apply(init)

            fun orange_red(init: SequentialScale<Color>.() -> Unit = {}) =
                SequentialScale(rgbBasisInterpolator(EncodedColors.OrRd9.colors)).apply(init)

            fun purple_blue(init: SequentialScale<Color>.() -> Unit = {}) =
                SequentialScale(rgbBasisInterpolator(EncodedColors.PuBu9.colors)).apply(init)

            fun purple_blue_green(init: SequentialScale<Color>.() -> Unit = {}) =
                SequentialScale(rgbBasisInterpolator(EncodedColors.PuBuGn9.colors)).apply(init)

            fun purple_red(init: SequentialScale<Color>.() -> Unit = {}) =
                SequentialScale(rgbBasisInterpolator(EncodedColors.PuRd9.colors)).apply(init)

            fun red_purple(init: SequentialScale<Color>.() -> Unit = {}) =
                SequentialScale(rgbBasisInterpolator(EncodedColors.RdPu9.colors)).apply(init)

            fun yellow_green(init: SequentialScale<Color>.() -> Unit = {}) =
                SequentialScale(rgbBasisInterpolator(EncodedColors.YlGn9.colors)).apply(init)

            fun yellow_green_blue(init: SequentialScale<Color>.() -> Unit = {}) =
                SequentialScale(rgbBasisInterpolator(EncodedColors.YlGnbU9.colors)).apply(init)

            fun yellow_green_brown(init: SequentialScale<Color>.() -> Unit = {}) =
                SequentialScale(rgbBasisInterpolator(EncodedColors.YlGnBr9.colors)).apply(init)

            fun yellow_green_red(init: SequentialScale<Color>.() -> Unit = {}) =
                SequentialScale(rgbBasisInterpolator(EncodedColors.YlGnRd9.colors)).apply(init)
        }

        object Diverging {
            fun brown_blueGreen(init: SequentialScale<Color>.() -> Unit = {}) =
                SequentialScale(rgbBasisInterpolator(EncodedColors.BrBG11.colors)).apply(init)

            fun pink_green(init: SequentialScale<Color>.() -> Unit = {}) =
                SequentialScale(rgbBasisInterpolator(EncodedColors.PiYG11.colors)).apply(init)

            fun purple_green(init: SequentialScale<Color>.() -> Unit = {}) =
                SequentialScale(rgbBasisInterpolator(EncodedColors.PRGn11.colors)).apply(init)

            fun purple_orange(init: SequentialScale<Color>.() -> Unit = {}) =
                SequentialScale(rgbBasisInterpolator(EncodedColors.PuOR11.colors)).apply(init)

            fun red_blue(init: SequentialScale<Color>.() -> Unit = {}) =
                SequentialScale(rgbBasisInterpolator(EncodedColors.RdBU11.colors)).apply(init)

            fun red_grey(init: SequentialScale<Color>.() -> Unit = {}) =
                SequentialScale(rgbBasisInterpolator(EncodedColors.RdGY11.colors)).apply(init)

            fun red_yellow_blue(init: SequentialScale<Color>.() -> Unit = {}) =
                SequentialScale(rgbBasisInterpolator(EncodedColors.RdYlBu11.colors)).apply(init)

            fun blue_yellow_red(init: SequentialScale<Color>.() -> Unit = {}) =
                SequentialScale(rgbBasisInterpolator(EncodedColors.BuYlRd11.colors)).apply(init)

            fun red_yellow_green(init: SequentialScale<Color>.() -> Unit = {}) =
                SequentialScale(rgbBasisInterpolator(EncodedColors.RdYlGn11.colors)).apply(init)

            fun spectral(init: SequentialScale<Color>.() -> Unit = {}) =
                SequentialScale(rgbBasisInterpolator(EncodedColors.spectral11.colors)).apply(init)
        }

        // TODO rainbow
        object Cyclical {
            fun sineBow(init: SequentialScale<Color>.() -> Unit = {}) =
                SequentialScale(rgbSineBowInterpolator()).apply(init)
        }
    }

    object Discrete {
        fun <D> accent8(init: OrdinalScale<D, Color>.() -> Unit = {}) =
            OrdinalScale<D, Color>(EncodedColors.accents.colors).apply(init)

        fun <D> dark8(init: OrdinalScale<D, Color>.() -> Unit = {}) =
            OrdinalScale<D, Color>(EncodedColors.dark2.colors).apply(init)

        fun <D> paired12(init: OrdinalScale<D, Color>.() -> Unit = {}) =
            OrdinalScale<D, Color>(EncodedColors.paired.colors).apply(init)

        fun <D> pastel9(init: OrdinalScale<D, Color>.() -> Unit = {}) =
            OrdinalScale<D, Color>(EncodedColors.pastel1.colors).apply(init)

        fun <D> pastel8(init: OrdinalScale<D, Color>.() -> Unit = {}) =
            OrdinalScale<D, Color>(EncodedColors.pastel2.colors).apply(init)

        fun <D> vivid9(init: OrdinalScale<D, Color>.() -> Unit = {}) =
            OrdinalScale<D, Color>(EncodedColors.set1.colors).apply(init)

        fun <D> vivid8(init: OrdinalScale<D, Color>.() -> Unit = {}) =
            OrdinalScale<D, Color>(EncodedColors.set2.colors).apply(init)

        fun <D> pale12(init: OrdinalScale<D, Color>.() -> Unit = {}) =
            OrdinalScale<D, Color>(EncodedColors.set3.colors).apply(init)

        fun <D> category10(init: OrdinalScale<D, Color>.() -> Unit = {}) =
            OrdinalScale<D, Color>(EncodedColors.category10.colors).apply(init)

        fun <D> categoryA20(init: OrdinalScale<D, Color>.() -> Unit = {}) =
            OrdinalScale<D, Color>(EncodedColors.category20.colors).apply(init)

        fun <D> categoryB20(init: OrdinalScale<D, Color>.() -> Unit = {}) =
            OrdinalScale<D, Color>(EncodedColors.category20b.colors).apply(init)

        fun <D> categoryC20(init: OrdinalScale<D, Color>.() -> Unit = {}) =
            OrdinalScale<D, Color>(EncodedColors.category20c.colors).apply(init)
    }
}