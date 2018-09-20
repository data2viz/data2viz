package io.data2viz.math

import kotlin.math.*

val e10 = sqrt(50.0)
val e5 = sqrt(10.0)
val e2 = sqrt(2.0)
val ln10:Double by lazy { ln(10.0) }

fun ticks(start:Number, stop: Number, count :Int): List<Double> {
    // require(count > 0, { "Ticks count must be greater than 0." }) // TODO check or return empty array ?
    val step = tickStep(start.toDouble(), stop.toDouble(), count)
    return range(
            ceil(start.toDouble() / step) * step,
            floor(stop.toDouble() / step) * step + step / 2, // inclusive
            step
    )
}

fun tickStep(start:Double, stop:Double, count:Int): Double {
    val step0 = abs(stop -start) / count
    var step1 = 10.0.pow(floor(ln(step0) / ln10))
    val error = step0 / step1
    if(error >= e10) step1 *=10
    else if(error >= e5) step1 *=5
    else if(error >= e2) step1 *=2
    return if (stop < start) -step1 else step1
}

fun range (start:Double, stop:Double, step: Double = 1.0): List<Double> {
    val n = maxOf(0, ceil((stop - start) / step).toInt())
    return  (0..n-1).map { start + it*step }
}
