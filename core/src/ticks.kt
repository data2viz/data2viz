import kotlin.js.Math

val e10 = Math.sqrt(50.0)
val e5 = Math.sqrt(10.0)
val e2 = Math.sqrt(2.0)
val ln10:Double by lazy { Math.log(10.0) }

fun ticks(start:Number, stop: Number, count :Int): List<Number> {
    val step = tickStep(start.toDouble(), stop.toDouble(), count);
    return range(
            Math.ceil(start.toDouble() / step) * step,
            Math.floor(stop.toDouble() / step) * step + step / 2, // inclusive
            step
    )
}

fun tickStep(start:Double, stop:Double, count:Int): Double {
    val step0 = Math.abs(stop -start) / count
    var step1 = Math.pow(10.0, Math.floor(Math.log(step0) / ln10).toDouble())
    val error = step0 / step1
    if(error >= e10) step1 *=10
    else if(error >= e5) step1 *=5
    else if(error >= e2) step1 *=2
    return if (stop < start) -step1 else step1
}

fun range (start:Double, stop:Double, step: Double = 1.0): List<Double> {
    val n = Math.max(0, Math.ceil((stop - start) / step))
    return  (0..n-1).map { start + it*step }
}
