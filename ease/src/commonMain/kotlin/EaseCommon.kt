package io.data2viz.ease

import kotlin.math.*

public typealias EaseFun = (Double) -> (Double)

private const val tau = 2 * PI
private const val halfPi = PI / 2
private const val b1 = 4.0 / 11
private const val b2 = 6.0 / 11
private const val b3 = 8.0 / 11
private const val b4 = 3.0 / 4
private const val b5 = 9.0 / 11
private const val b6 = 10.0 / 11
private const val b7 = 15.0 / 16
private const val b8 = 21.0 / 22
private const val b9 = 63.0 / 64
private const val b0 = 1.0 / b1 / b1


public class ease {
    public companion object {
        public val linear: EaseFun = { it }

        public val bounceIn: EaseFun   = { 1 - bounceOut(1 - it) }

        public val bounceOut: EaseFun  = { t ->
            when {
                t < b1 -> b0 * t * t
                t < b3 -> b0 * (t - b2) * (t - b2) + b4
                t < b6 -> b0 * (t - b5) * (t - b5) + b7
                else ->   b0 * (t - b8) * (t - b8) + b9
            }
        }

        public val bounceInOut: EaseFun = {
            (it * 2).let {
                if (it <= 1) 1 - bounceOut(1-it)
                else bounceOut(it - 1) + 1
            } / 2
        }

        public val circleIn    : EaseFun = { 1 - sqrt(1 - it * it) }
        public val circleOut   : EaseFun = { sqrt(1.0 - (it - 1) * (it - 1)) }
        public val circleInOut : EaseFun = {
            (it * 2).let {
                if (it <= 1) 1 - sqrt(1 - it * it)
                else sqrt(1.0 - (it - 2) * (it - 2)) + 1
            } / 2
        }

        public val cubicIn    : EaseFun = { it * it * it }
        public val cubicOut   : EaseFun = { (it - 1) * (it - 1) * (it - 1) + 1 }
        public val cubicInOut : EaseFun = {
            (it * 2).let {
                if (it <= 1) it * it * it
                else (it - 2) * (it - 2) * (it - 2) + 2
            } / 2
        }

        public val expIn: EaseFun = { 2.0.pow(10.0 * it - 10) }
        public val expOut: EaseFun = { 1 - 2.0.pow(-10 * it) }
        public val expInOut: EaseFun =
            { (it * 2).let { if (it <= 1) 2.0.pow(10.0 * it - 10) else 2 - 2.0.pow(10 - 10 * it) } / 2 }

        public val quadIn: EaseFun = { it * it }
        public val quadOut: EaseFun = { it * (2 - it) }
        public val quadInOut: EaseFun = { (it * 2).let {
            if (it <= 1) it * it 
            else (it - 1) * (3 - it) + 1 } / 2 }


        public val sinIn: EaseFun      = { 1 - cos(it * halfPi) }
        public val sinOut: EaseFun     = { sin(it * halfPi) }
        public val sinInOut: EaseFun   = { (1 - cos(PI * it)) / 2 }
        
        //configurable easing functions (implemented as class with invoke operator)
        public val backIn:       BackIn = BackIn()
        public val backOut:     BackOut = BackOut()
        public val backInOut: BackInOut = BackInOut()

        public val elasticIn:       ElasticIn = ElasticIn()
        public val elasticOut:     ElasticOut = ElasticOut()
        public val elasticInOut: ElasticInOut = ElasticInOut()

        public val polyIn:       PolyIn = PolyIn()
        public val polyOut:     PolyOut = PolyOut()
        public val polyInOut: PolyInOut = PolyInOut()
    }
}

public class ElasticIn(
    private val amplitude: Double = 1.0,
    private val period: Double = 0.3
) {

    private val a = amplitude.coerceAtLeast(1.0)
    private val p = period / tau
    private val s = asin(1 / a) * p
    
    public fun amplitude(amplitude: Double): ElasticIn = ElasticIn(amplitude, period)
    public fun period(period: Double): ElasticIn = ElasticIn(amplitude, period)
    
    public operator fun invoke(t: Double): Double = a * 2.0.pow(10 * (t-1)) * sin((s -t + 1)/p)
}

public class ElasticOut(
    private val amplitude: Double = 1.0,
    private val period: Double = 0.3
) {
    private val a = amplitude.coerceAtLeast(1.0)
    private val p = period / tau
    private val s = asin(1 / a) * p

    public fun amplitude(amplitude: Double): ElasticOut = ElasticOut(amplitude, period)
    public fun period(period: Double): ElasticOut = ElasticOut(amplitude, period)

    public operator fun invoke(t: Double): Double = 1 - a * 2.0.pow(-10 * t) * sin((t + s) / p)
}

public class ElasticInOut(
    private val amplitude: Double = 1.0,
    private val period: Double = 0.3
) {
    private val a = amplitude.coerceAtLeast(1.0)
    private val p = period / tau
    private val s = asin(1 / a) * p

    public fun amplitude(amplitude: Double): ElasticInOut = ElasticInOut(amplitude, period)
    public fun period(period: Double): ElasticInOut = ElasticInOut(amplitude, period)

    public operator fun invoke(t: Double): Double = (t * 2 - 1).let {
        if (it < 0) a * 2.0.pow(10 * it) * sin((s - it) / p)
        else 2.0 - a * 2.0.pow(-10 * it) * sin((s + it) / p)
    } / 2
}

public class BackIn(private val overshoot: Double = 1.70158) {
    public fun overshoot(overshoot: Double): BackIn = BackIn(overshoot)
    public operator fun invoke(t: Double): Double = t * t * ((overshoot + 1) * t - overshoot)
}
public class BackOut(private val overshoot: Double = 1.70158) {
    public fun overshoot(overshoot: Double): BackOut = BackOut(overshoot)
    public operator fun invoke(t: Double): Double = (t-1) * (t-1) * ((overshoot +1) * (t-1) + overshoot) + 1
}

public class BackInOut(private val overshoot: Double = 1.70158) {
    public fun overshoot(overshoot: Double): BackInOut = BackInOut(overshoot)
    public operator fun invoke(t: Double): Double = (t * 2).let {
        if (it < 1) it * it * ((overshoot+1) * it - overshoot)
        else (it -2) * (it-2) * ((overshoot +1) * (it-2) + overshoot) + 2
    } / 2
}

public class PolyIn(private val exponent: Double = 3.0) {
    public fun exponent(exponent: Double): PolyIn = PolyIn(exponent)
    public operator fun invoke(t: Double): Double = t.pow(exponent)
}

public class PolyOut(private val exponent: Double = 3.0) {
    public fun exponent(exponent: Double): PolyOut = PolyOut(exponent)
    public operator fun invoke(t: Double): Double = 1 - (1 - t).pow(exponent)
}

public class PolyInOut(private val exponent: Double = 3.0) {
    public fun exponent(exponent: Double): PolyInOut = PolyInOut(exponent)
    public operator fun invoke(t: Double): Double = (t * 2).let {
        if (it <= 1)
            it.pow(exponent)
        else
            2 - (2 - it).pow(exponent)
    } / 2
}

