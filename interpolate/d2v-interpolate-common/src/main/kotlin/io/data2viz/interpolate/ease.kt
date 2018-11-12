package io.data2viz.interpolate

import io.data2viz.math.HALFPI_ANGLE
import kotlin.math.cos
import kotlin.math.pow
import kotlin.math.sqrt

// TODO should be interpolators
// TODO : should also use Percent
fun quad(x:Double) = x * x
fun cubicIn(x:Double) = x * x * x
fun cubicOut(t:Double) = (t-1)*(t-1)*(t-1) + 1
fun cubicInOut(t:Double) =  (if(t <= .5) (8*t*t*t) else ((2*t-2)*(2*t-2)*(2*t-2)+2))/2


fun sin(x:Double) = 1 - cos(x * HALFPI_ANGLE.rad)
fun poly(e:Double) = {t:Double -> t.pow(e)}


fun circleIn(t:Double) = 1 - sqrt(1 - (t * t).coerceAtMost(1.0))
fun circleOut(t:Double) = sqrt(1 - (t-1)*(t-1));
