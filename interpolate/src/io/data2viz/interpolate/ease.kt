package io.data2viz.interpolate

import io.data2viz.math.halfPI
import kotlin.js.Math

fun quad(x:Double) = x * x

fun cubicIn(x:Double) = x * x * x
fun cubicOut(t:Double) = (t-1)*(t-1)*(t-1) + 1
fun cubicInOut(t:Double) =  (if(t <= .5) (8*t*t*t) else ((2*t-2)*(2*t-2)*(2*t-2)+2))/2


fun sin(x:Double) = 1 - Math.cos(x * halfPI)
fun poly(e:Double) = {t:Double -> Math.pow(t,e)}


fun circleIn(t:Double) = 1 - Math.sqrt(1 - t * t)
fun circleOut(t:Double) = Math.sqrt(1 - (t-1)*(t-1));
