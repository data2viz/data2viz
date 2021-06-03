/*
 * Copyright (c) 2018-2021. data2viz sàrl.
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
import kotlinx.datetime.LocalDateTime
import kotlin.jvm.JvmName

/**
 * Generic signature of scales.
 *
 * A scale can map a domain object dimension DOMAIN to a representation value.
 *
 * Then at runtime, one can ask an RANGE object for a specific value of domain.
 * The rules defining the returns of RANGE from DOMAIN depends a lot on the type and
 * implementation of the Scale.
 *
 * a Domain object -> Range object
 */
public interface Scale<DOMAIN, out RANGE> {

    public operator fun invoke(domainValue: DOMAIN): RANGE


    /**
     * Returns a copy of the current scale
     */
    public fun copy(): Scale<DOMAIN, RANGE>
}

public interface ContinuousDomain<D> {
    public var domain: List<D>
}

public interface DiscreteDomain<D> {
    public var domain: List<D>
}

public interface StrictlyContinuousDomain<D> {
    public var domain: StrictlyContinuous<D>
}

public interface ContinuousRangeScale<D, R> : Scale<D, R>, FirstLastRange<D, R> {
    public var range: List<R>
    override fun start(): R = range.first()
    override fun end(): R = range.last()
}

public interface DiscreteRange<R> {
    public var range: List<R>
}

public interface StrictlyContinuousRange<D, R> : FirstLastRange<D, R> {
    public var range: StrictlyContinuous<R>
    override fun start(): R = range.start
    override fun end(): R = range.end
}

public interface FirstLastRange<D, R> : Scale<D, R> {
    public fun start(): R
    public fun end(): R
}

/**
 * A stricly continuous dimension is only defined by its start and end.
 * There is not intermediary value.
 */
public data class StrictlyContinuous<D>(val start: D, val end: D)

public fun <D> intervalOf(start: D, end: D): StrictlyContinuous<D> = StrictlyContinuous(start, end)
public fun <D> intervalOf(vararg values: D): StrictlyContinuous<D> = StrictlyContinuous(values.first(), values.last())


/**
 * Indicates a scale for which the resulting R
 */
public interface ClampableScale {
    public val clamp: Boolean
}

/**
 * Niceable scales implements [NiceableScale.nice] to possibly extends their domain to the nearest round values.
 *
 * Some niceableScale are: [LinearScale], [TimeScale], [LogScale] and [PowerScale]
 */
public interface NiceableScale<D> : ContinuousDomain<D> {

    /**
     * Extends the domain so that it starts and ends on nice round values.
     * This method typically modifies the scale’s domain, and may only extend the bounds to the nearest round value.
     * An optional tick count argument allows greater control over the step size used to extend the bounds,
     * guaranteeing that the returned ticks will exactly cover the domain. Nicing is useful if the domain is computed
     * from data, say using extent, and may be irregular. For example, for a domain of [0.201479…, 0.996679…],
     * a nice domain might be [0.2, 1.0]. If the domain has more than two values, nicing the domain only affects
     * the first and last value.
     *
     * Nicing a scale only modifies the current domain; it does not automatically nice domains that are
     * subsequently set using continuous.domain. You must re-nice the scale after setting the new domain, if desired.
     */
    public fun nice(count: Int = 10)
}

public interface InvertableScale<D, R> : Scale<D, R> {
    public fun invert(rangeValue: R): D
}

/**
 * Can provide ticks from Domain D.
 */
public interface Tickable<D> {
    public fun ticks(count: Int = 10): List<D>
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
public object Scales {

    public object Continuous {


        /**
         * Identity scales are a special case of linear scales where the domain and range are identical;
         * the scale and its invert method are thus the identity function. These scales are occasionally useful when
         * working with pixel coordinates, say in conjunction with an axis or brush.
         */
        public fun identity(): LinearScale<Double> = LinearScale(::interpolateNumber, ::uninterpolateNumber, naturalOrder()).apply {
            domain = listOf(.0, 1.0)
            range = listOf(.0, 1.0)
        }

        public fun linear(init: LinearScale<Double>.() -> Unit = {}): LinearScale<Double> =
            LinearScale(::interpolateNumber, ::uninterpolateNumber, naturalOrder()).apply(init)


        public fun linearRound(init: LinearScale<Double>.() -> Unit = {}): LinearScale<Double> =
            LinearScale(::interpolateRound, ::uninterpolateNumber, naturalOrder()).apply(init)

        public fun log(
            base: Double = 10.0,
            init: ContinuousScale<Double, Double>.() -> Unit = {}
        ): LogScale =
            LogScale(base, ::interpolateNumber, ::uninterpolateNumber, naturalOrder()).apply(init)

        /**
         * TODO Test
         */
        public fun logRound(base: Double = 10.0): LogScale =
            LogScale(base, ::interpolateRound, ::uninterpolateNumber, naturalOrder())

        public fun vector(init: LinearScale<Point>.() -> Unit = {}): LinearScale<Point> =
            LinearScale(::interpolatePoint, ::uninterpolatePointOnX, PointComparatorX()).apply(init)

        public fun pow(exponent: Double = 1.0, init: PowerScale<Double>.() -> Unit = {}): PowerScale<Double> =
            PowerScale(exponent, ::interpolateNumber, ::uninterpolateNumber, naturalOrder()).apply(init)

        public fun powRound(exponent: Double = 1.0, init: PowerScale<Double>.() -> Unit = {}): PowerScale<Double> =
            PowerScale(exponent, ::interpolateRound, ::uninterpolateNumber, naturalOrder()).apply(init)


        public fun sqrt(init: PowerScale<Double>.() -> Unit = {}): PowerScale<Double> =
            PowerScale(.5, ::interpolateNumber, ::uninterpolateNumber, naturalOrder()).apply(init)

        public fun sqrtRound(init: PowerScale<Double>.() -> Unit = {}): PowerScale<Double> =
            PowerScale(.5, ::interpolateRound, ::uninterpolateNumber, naturalOrder()).apply(init)

        public fun time(init: TimeScale<Double>.() -> Unit = {}): TimeScale<Double> =
            TimeScale(::interpolateNumber, ::uninterpolateNumber, naturalOrder()).apply(init)

        public fun sequential(interpolator: Interpolator<Double>): SequentialScale<Double> = SequentialScale(interpolator)

        @JvmName("sequentialColor")
        public fun sequential(interpolator: Interpolator<Color>): SequentialScale<Color> = SequentialScale(interpolator)
    }

    public object Quantized {
        public fun <R> quantile(init: QuantileScale<R>.() -> Unit = {}): QuantileScale<R> = QuantileScale<R>().apply(init)
        public fun <R> quantize(init: QuantizeScale<R>.() -> Unit = {}): QuantizeScale<R> = QuantizeScale<R>().apply(init)
        public fun <R> threshold(init: ThresholdScale<R>.() -> Unit = {}): ThresholdScale<R> = ThresholdScale<R>().apply(init)
    }

    public object Discrete {
        public fun <D> point(init: PointScale<D>.() -> Unit = {}): PointScale<D> = PointScale<D>().apply(init)
        public fun <D> band(init: BandScale<D>.() -> Unit = {}): BandScale<D> = BandScale<D>().apply(init)
        public fun <D, R> ordinal(init: OrdinalScale<D, R>.() -> Unit = {}): OrdinalScale<D, R> = OrdinalScale<D, R>().apply(init)
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
public object ScalesChromatic {

    public object Continuous {
        public fun linearRGB(init: LinearScale<Color>.() -> Unit = {}): LinearScale<Color> = LinearScale(::rgbLinearInterpolator).apply(init)
        public fun defaultRGB(init: LinearScale<Color>.() -> Unit = {}): LinearScale<Color> = LinearScale(::rgbDefaultInterpolator).apply(init)
        public fun linearLAB(init: LinearScale<Color>.() -> Unit = {}): LinearScale<Color> = LinearScale(::labInterpolator).apply(init)
        public fun linearHCL(init: LinearScale<Color>.() -> Unit = {}): LinearScale<Color> = LinearScale(::hclInterpolator).apply(init)
        public fun linearHCLLong(init: LinearScale<Color>.() -> Unit = {}): LinearScale<Color> = LinearScale(::hclLongInterpolator).apply(init)
        public fun linearHSL(init: LinearScale<Color>.() -> Unit = {}): LinearScale<Color> = LinearScale(::hslInterpolator).apply(init)
        public fun linearHSLLong(init: LinearScale<Color>.() -> Unit = {}): LinearScale<Color> = LinearScale(::hslLongInterpolator).apply(init)
    }

    // TODO these scales should also be available as "ordinal scales"
    public object Sequential {

        public object SingleHue {
            public fun blues(init: SequentialScale<Color>.() -> Unit = {}): SequentialScale<Color> =
                SequentialScale(rgbBasisInterpolator(EncodedColors.blues9.colors)).apply(init)

            public fun greens(init: SequentialScale<Color>.() -> Unit = {}): SequentialScale<Color> =
                SequentialScale(rgbBasisInterpolator(EncodedColors.greens9.colors)).apply(init)

            public fun greys(init: SequentialScale<Color>.() -> Unit = {}): SequentialScale<Color> =
                SequentialScale(rgbBasisInterpolator(EncodedColors.greys9.colors)).apply(init)

            public fun oranges(init: SequentialScale<Color>.() -> Unit = {}): SequentialScale<Color> =
                SequentialScale(rgbBasisInterpolator(EncodedColors.oranges9.colors)).apply(init)

            public fun purples(init: SequentialScale<Color>.() -> Unit = {}): SequentialScale<Color> =
                SequentialScale(rgbBasisInterpolator(EncodedColors.purples9.colors)).apply(init)

            public fun reds(init: SequentialScale<Color>.() -> Unit = {}): SequentialScale<Color> =
                SequentialScale(rgbBasisInterpolator(EncodedColors.reds9.colors)).apply(init)
        }

        // TODO warm, cool, cubehelix
        // TODO YlOrBr, YlOrRd
        public object MultiHue {
            public fun viridis(init: SequentialScale<Color>.() -> Unit = {}): SequentialScale<Color> =
                SequentialScale(rgbBasisInterpolator(EncodedColors.viridis.colors)).apply(init)


            public fun magma(init: SequentialScale<Color>.() -> Unit = {}): SequentialScale<Color> =
                SequentialScale(rgbBasisInterpolator(EncodedColors.magma.colors)).apply(init)

            public fun inferno(init: SequentialScale<Color>.() -> Unit = {}): SequentialScale<Color> =
                SequentialScale(rgbBasisInterpolator(EncodedColors.inferno.colors)).apply(init)

            public fun plasma(init: SequentialScale<Color>.() -> Unit = {}): SequentialScale<Color> =
                SequentialScale(rgbBasisInterpolator(EncodedColors.plasma.colors)).apply(init)

            public fun blue_green(init: SequentialScale<Color>.() -> Unit = {}): SequentialScale<Color> =
                SequentialScale(rgbBasisInterpolator(EncodedColors.BuGN9.colors)).apply(init)

            public fun blue_purple(init: SequentialScale<Color>.() -> Unit = {}): SequentialScale<Color> =
                SequentialScale(rgbBasisInterpolator(EncodedColors.BuPu9.colors)).apply(init)

            public fun green_blue(init: SequentialScale<Color>.() -> Unit = {}): SequentialScale<Color> =
                SequentialScale(rgbBasisInterpolator(EncodedColors.GnBu9.colors)).apply(init)

            public fun orange_red(init: SequentialScale<Color>.() -> Unit = {}): SequentialScale<Color> =
                SequentialScale(rgbBasisInterpolator(EncodedColors.OrRd9.colors)).apply(init)

            public fun purple_blue(init: SequentialScale<Color>.() -> Unit = {}): SequentialScale<Color> =
                SequentialScale(rgbBasisInterpolator(EncodedColors.PuBu9.colors)).apply(init)

            public fun purple_blue_green(init: SequentialScale<Color>.() -> Unit = {}): SequentialScale<Color> =
                SequentialScale(rgbBasisInterpolator(EncodedColors.PuBuGn9.colors)).apply(init)

            public fun purple_red(init: SequentialScale<Color>.() -> Unit = {}): SequentialScale<Color> =
                SequentialScale(rgbBasisInterpolator(EncodedColors.PuRd9.colors)).apply(init)

            public fun red_purple(init: SequentialScale<Color>.() -> Unit = {}): SequentialScale<Color> =
                SequentialScale(rgbBasisInterpolator(EncodedColors.RdPu9.colors)).apply(init)

            public fun yellow_green(init: SequentialScale<Color>.() -> Unit = {}): SequentialScale<Color> =
                SequentialScale(rgbBasisInterpolator(EncodedColors.YlGn9.colors)).apply(init)

            public fun yellow_green_blue(init: SequentialScale<Color>.() -> Unit = {}): SequentialScale<Color> =
                SequentialScale(rgbBasisInterpolator(EncodedColors.YlGnbU9.colors)).apply(init)

            public fun yellow_green_brown(init: SequentialScale<Color>.() -> Unit = {}): SequentialScale<Color> =
                SequentialScale(rgbBasisInterpolator(EncodedColors.YlGnBr9.colors)).apply(init)

            public fun yellow_green_red(init: SequentialScale<Color>.() -> Unit = {}): SequentialScale<Color> =
                SequentialScale(rgbBasisInterpolator(EncodedColors.YlGnRd9.colors)).apply(init)
        }

        public object Diverging {
            public fun brown_blueGreen(init: SequentialScale<Color>.() -> Unit = {}): SequentialScale<Color> =
                SequentialScale(rgbBasisInterpolator(EncodedColors.BrBG11.colors)).apply(init)

            public fun pink_green(init: SequentialScale<Color>.() -> Unit = {}): SequentialScale<Color> =
                SequentialScale(rgbBasisInterpolator(EncodedColors.PiYG11.colors)).apply(init)

            public fun purple_green(init: SequentialScale<Color>.() -> Unit = {}): SequentialScale<Color> =
                SequentialScale(rgbBasisInterpolator(EncodedColors.PRGn11.colors)).apply(init)

            public fun purple_orange(init: SequentialScale<Color>.() -> Unit = {}): SequentialScale<Color> =
                SequentialScale(rgbBasisInterpolator(EncodedColors.PuOR11.colors)).apply(init)

            public fun red_blue(init: SequentialScale<Color>.() -> Unit = {}): SequentialScale<Color> =
                SequentialScale(rgbBasisInterpolator(EncodedColors.RdBU11.colors)).apply(init)

            public fun red_grey(init: SequentialScale<Color>.() -> Unit = {}): SequentialScale<Color> =
                SequentialScale(rgbBasisInterpolator(EncodedColors.RdGY11.colors)).apply(init)

            public fun red_yellow_blue(init: SequentialScale<Color>.() -> Unit = {}): SequentialScale<Color> =
                SequentialScale(rgbBasisInterpolator(EncodedColors.RdYlBu11.colors)).apply(init)

            public fun blue_yellow_red(init: SequentialScale<Color>.() -> Unit = {}): SequentialScale<Color> =
                SequentialScale(rgbBasisInterpolator(EncodedColors.BuYlRd11.colors)).apply(init)

            public fun red_yellow_green(init: SequentialScale<Color>.() -> Unit = {}): SequentialScale<Color> =
                SequentialScale(rgbBasisInterpolator(EncodedColors.RdYlGn11.colors)).apply(init)

            public fun spectral(init: SequentialScale<Color>.() -> Unit = {}): SequentialScale<Color> =
                SequentialScale(rgbBasisInterpolator(EncodedColors.spectral11.colors)).apply(init)
        }

        // TODO rainbow
        public object Cyclical {
            public fun sineBow(init: SequentialScale<Color>.() -> Unit = {}): SequentialScale<Color> =
                SequentialScale(rgbSineBowInterpolator()).apply(init)
        }
    }

    public object Discrete {
        public fun <D> accent8(init: OrdinalScale<D, Color>.() -> Unit = {}): OrdinalScale<D, Color> =
            OrdinalScale<D, Color>(EncodedColors.accents.colors).apply(init)

        public fun <D> dark8(init: OrdinalScale<D, Color>.() -> Unit = {}): OrdinalScale<D, Color> =
            OrdinalScale<D, Color>(EncodedColors.dark2.colors).apply(init)

        public fun <D> paired12(init: OrdinalScale<D, Color>.() -> Unit = {}): OrdinalScale<D, Color> =
            OrdinalScale<D, Color>(EncodedColors.paired.colors).apply(init)

        public fun <D> pastel9(init: OrdinalScale<D, Color>.() -> Unit = {}): OrdinalScale<D, Color> =
            OrdinalScale<D, Color>(EncodedColors.pastel1.colors).apply(init)

        public fun <D> pastel8(init: OrdinalScale<D, Color>.() -> Unit = {}): OrdinalScale<D, Color> =
            OrdinalScale<D, Color>(EncodedColors.pastel2.colors).apply(init)

        public fun <D> vivid9(init: OrdinalScale<D, Color>.() -> Unit = {}): OrdinalScale<D, Color> =
            OrdinalScale<D, Color>(EncodedColors.set1.colors).apply(init)

        public fun <D> vivid8(init: OrdinalScale<D, Color>.() -> Unit = {}): OrdinalScale<D, Color> =
            OrdinalScale<D, Color>(EncodedColors.set2.colors).apply(init)

        public fun <D> pale12(init: OrdinalScale<D, Color>.() -> Unit = {}): OrdinalScale<D, Color> =
            OrdinalScale<D, Color>(EncodedColors.set3.colors).apply(init)

        public fun <D> category10(init: OrdinalScale<D, Color>.() -> Unit = {}): OrdinalScale<D, Color> =
            OrdinalScale<D, Color>(EncodedColors.category10.colors).apply(init)

        public fun <D> categoryA20(init: OrdinalScale<D, Color>.() -> Unit = {}): OrdinalScale<D, Color> =
            OrdinalScale<D, Color>(EncodedColors.category20.colors).apply(init)

        public fun <D> categoryB20(init: OrdinalScale<D, Color>.() -> Unit = {}): OrdinalScale<D, Color> =
            OrdinalScale<D, Color>(EncodedColors.category20b.colors).apply(init)

        public fun <D> categoryC20(init: OrdinalScale<D, Color>.() -> Unit = {}): OrdinalScale<D, Color> =
            OrdinalScale<D, Color>(EncodedColors.category20c.colors).apply(init)
    }
}
