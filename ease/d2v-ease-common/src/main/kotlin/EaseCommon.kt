package io.data2viz.ease

import kotlin.math.*

typealias EaseFun = (Double) -> (Double)

val tau = 2 * PI
val halfPi = PI / 2

internal val b1 = 4.0 / 11
internal val b2 = 6.0 / 11
internal val b3 = 8.0 / 11
internal val b4 = 3.0 / 4
internal val b5 = 9.0 / 11
internal val b6 = 10.0 / 11
internal val b7 = 15.0 / 16
internal val b8 = 21.0 / 22
internal val b9 = 63.0 / 64
internal val b0 = 1.0 / b1 / b1

class ease {
    companion object {
        val linear: EaseFun = { it }

        val bounceIn: EaseFun   = { 1 - bounceOut(1 - it) }
        val bounceOut: EaseFun  = { t ->
            when {
                t < b1 -> b0 * t * t
                t < b3 -> b0 * (t - b2) * (t - b2) + b4
                t < b6 -> b0 * (t - b5) * (t - b5) + b7
                else ->   b0 * (t - b8) * (t - b8) + b9
            }
        }
        val bounceInOut: EaseFun = {
            (it * 2).let {
                if (it <= 1) 1 - bounceOut(1-it)
                else bounceOut(it - 1) + 1
            } / 2
        }

        val circleIn    : EaseFun = { 1 - sqrt(1 - it * it) }
        val circleOut   : EaseFun = { sqrt(1.0 - (it - 1) * (it - 1)) }
        val circleInOut : EaseFun = {
            (it * 2).let {
                if (it <= 1) 1 - sqrt(1 - it * it)
                else sqrt(1.0 - (it - 2) * (it - 2)) + 1
            } / 2
        }

        val cubicIn    : EaseFun = { it * it * it }
        val cubicOut   : EaseFun = { (it - 1) * (it - 1) * (it - 1) + 1 }
        val cubicInOut : EaseFun = {
            (it * 2).let {
                if (it <= 1) it * it * it
                else (it - 2) * (it - 2) * (it - 2) + 2
            } / 2
        }

        val expIn: EaseFun = { 2.0.pow(10.0 * it - 10) }
        val expOut: EaseFun = { 1 - 2.0.pow(-10 * it) }
        val expInOut: EaseFun =
            { (it * 2).let { if (it <= 1) 2.0.pow(10.0 * it - 10) else 2 - 2.0.pow(10 - 10 * it) } / 2 }

        val quadIn: EaseFun = { it * it }
        val quadOut: EaseFun = { it * (2 - it) }
        val quadInOut: EaseFun = { (it * 2).let { 
            if (it <= 1) it * it 
            else (it - 1) * (3 - it) + 1 } / 2 }


        val sinIn: EaseFun      = { 1 - cos(it * halfPi) }
        val sinOut: EaseFun     = { sin(it * halfPi) }
        val sinInOut: EaseFun   = { (1 - cos(PI * it)) / 2 }
        
        //configurable easing functions (implemented as class with invoke operator)
        val backIn      = BackIn()
        val backOut     = BackOut()
        val backInOut   = BackInOut()

        val elasticIn   = ElasticIn()
        val elasticOut  = ElasticOut()
        val elasticInOut = ElasticInOut()

        val polyIn      = PolyIn()
        val polyOut     = PolyOut()
        val polyInOut   = PolyInOut()
    }
}



class ElasticIn(
    private val amplitude: Double = 1.0,
    private val period: Double = 0.3
) {

    private val a = amplitude.coerceAtLeast(1.0)
    private val p = period / tau
    private val s = asin(1 / a) * p
    
    fun amplitude(amplitude: Double) = ElasticIn(amplitude, period)
    fun period(period: Double) = ElasticIn(amplitude, period)
    
    operator fun invoke(t: Double): Double = a * 2.0.pow(10 * (t-1)) * sin((s -t + 1)/p)
}

class ElasticOut(
    private val amplitude: Double = 1.0,
    private val period: Double = 0.3
) {
    private val a = amplitude.coerceAtLeast(1.0)
    private val p = period / tau
    private val s = asin(1 / a) * p

    fun amplitude(amplitude: Double) = ElasticOut(amplitude, period)
    fun period(period: Double) = ElasticOut(amplitude, period)

    operator fun invoke(t: Double) = 1 - a * 2.0.pow(-10 * t) * sin((t + s) / p)
}

class ElasticInOut(
    private val amplitude: Double = 1.0,
    private val period: Double = 0.3
) {
    private val a = amplitude.coerceAtLeast(1.0)
    private val p = period / tau
    private val s = asin(1 / a) * p

    fun amplitude(amplitude: Double) = ElasticInOut(amplitude, period)
    fun period(period: Double) = ElasticInOut(amplitude, period)

    operator fun invoke(t: Double) = (t * 2 - 1).let {
        if (it < 0) a * 2.0.pow(10 * it) * sin((s - it) / p)
        else 2.0 - a * 2.0.pow(-10 * it) * sin((s + it) / p)
    } / 2
}

class BackIn(private val overshoot: Double = 1.70158) {
    fun overshoot(overshoot: Double) = BackIn(overshoot)
    operator fun invoke(t: Double): Double = t * t * ((overshoot + 1) * t - overshoot)
}
class BackOut(private val overshoot: Double = 1.70158) {
    fun overshoot(overshoot: Double) = BackOut(overshoot)
    operator fun invoke(t: Double): Double = (t-1) * (t-1) * ((overshoot +1) * (t-1) + overshoot) + 1
}

class BackInOut(private val overshoot: Double = 1.70158) {
    fun overshoot(overshoot: Double) = BackInOut(overshoot)
    operator fun invoke(t: Double) = (t * 2).let {
        if (it < 1) it * it * ((overshoot+1) * it - overshoot)
        else (it -2) * (it-2) * ((overshoot +1) * (it-2) + overshoot) + 2
    } / 2
}

class PolyIn(private val exponent: Double = 3.0) {
    fun exponent(exponent: Double) = PolyIn(exponent)
    operator fun invoke(t: Double): Double = t.pow(exponent)
}

class PolyOut(private val exponent: Double = 3.0) {
    fun exponent(exponent: Double) = PolyOut(exponent)
    operator fun invoke(t: Double) = 1 - (1 - t).pow(exponent)
}

class PolyInOut(private val exponent: Double = 3.0) {
    fun exponent(exponent: Double) = PolyInOut(exponent)
    operator fun invoke(t: Double) = (t * 2).let {
        if (it <= 1)
            it.pow(exponent)
        else
            2 - (2 - it).pow(exponent)
    } / 2
}

