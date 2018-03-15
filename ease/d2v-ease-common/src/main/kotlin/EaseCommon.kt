package io.data2viz.ease

import kotlin.math.pow

typealias EaseFun = (Double) -> (Double)


class ease {
    companion object {
        val linear: EaseFun = {it}
        val polyIn = PolyIn()
        val polyOut = PolyOut()
        val polyInOut = PolyInOut()
        
    }
}

class PolyIn (private val exponent:Double = 3.0) {
    fun exponent(exponent: Double) = PolyIn(exponent)
    operator fun invoke(t:Double):Double = t.pow(exponent)
}

class PolyOut (private val exponent:Double = 3.0) {
    fun exponent(exponent: Double) = PolyOut(exponent)
    operator fun invoke(t:Double) = 1 - (1-t).pow(exponent)
}

class PolyInOut (private val exponent:Double = 3.0) {
    fun exponent(exponent: Double) = PolyInOut(exponent)
    operator fun invoke(t:Double) = (t*2).let {
        if (it <=1) 
            it.pow(exponent)
        else 
            2 - (2 -it).pow(exponent) 
    } / 2
                
}

