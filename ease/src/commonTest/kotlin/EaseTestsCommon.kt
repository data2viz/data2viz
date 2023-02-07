package io.data2viz.ease

import io.data2viz.test.TestBase
import kotlin.js.JsName
import kotlin.test.Test


class EaseTestsCommon : TestBase() {


    @Test
    @JsName("easeLinear")
    fun `easeLinear(t) returns the expected results`() {
        ease.linear(.0) shouldBeClose .0
        ease.linear(.0) shouldBeClose .0
        ease.linear(.1) shouldBeClose .1
        ease.linear(.2) shouldBeClose .2
        ease.linear(.3) shouldBeClose .3
        ease.linear(.4) shouldBeClose .4
        ease.linear(.5) shouldBeClose .5
        ease.linear(.6) shouldBeClose .6
        ease.linear(.7) shouldBeClose .7
        ease.linear(.8) shouldBeClose .8
        ease.linear(.9) shouldBeClose .9
        ease.linear(.0) shouldBeClose .0
    }

    @Test
    @JsName("easeBounceIn")
    fun `easeBounceIn(t) returns the expected results`() {
        ease.bounceIn(0.0) shouldBeClose 0.000000
        ease.bounceIn(0.1) shouldBeClose 0.011875
        ease.bounceIn(0.2) shouldBeClose 0.060000
        ease.bounceIn(0.3) shouldBeClose 0.069375
        ease.bounceIn(0.4) shouldBeClose 0.227500
        ease.bounceIn(0.5) shouldBeClose 0.234375
        ease.bounceIn(0.6) shouldBeClose 0.090000
        ease.bounceIn(0.7) shouldBeClose 0.319375
        ease.bounceIn(0.8) shouldBeClose 0.697500
        ease.bounceIn(0.9) shouldBeClose 0.924375
        ease.bounceIn(1.0) shouldBeClose 1.000000
    }

    @Test
    @JsName("easeBounceInOut")
    fun `easeBounceInOut(t) returns the expected results`() {
        val intOut = ease.bounceIn::invoke.inOut
        ease.bounceInOut(0.0) shouldBeClose intOut(0.0)
        ease.bounceInOut(0.1) shouldBeClose intOut(0.1)
        ease.bounceInOut(0.2) shouldBeClose intOut(0.2)
        ease.bounceInOut(0.3) shouldBeClose intOut(0.3)
        ease.bounceInOut(0.4) shouldBeClose intOut(0.4)
        ease.bounceInOut(0.5) shouldBeClose intOut(0.5)
        ease.bounceInOut(0.6) shouldBeClose intOut(0.6)
        ease.bounceInOut(0.7) shouldBeClose intOut(0.7)
        ease.bounceInOut(0.8) shouldBeClose intOut(0.8)
        ease.bounceInOut(0.9) shouldBeClose intOut(0.9)
        ease.bounceInOut(1.0) shouldBeClose intOut(1.0)
    }


    @Test
    @JsName("easeCircleIn")
    fun `easeCircleIn(t) returns the expected results`() {
        ease.circleIn(0.0) shouldBeClose 0.000000
        ease.circleIn(0.1) shouldBeClose 0.005013
        ease.circleIn(0.2) shouldBeClose 0.020204
        ease.circleIn(0.3) shouldBeClose 0.046061
        ease.circleIn(0.4) shouldBeClose 0.083485
        ease.circleIn(0.5) shouldBeClose 0.133975
        ease.circleIn(0.6) shouldBeClose 0.200000
        ease.circleIn(0.7) shouldBeClose 0.285857
        ease.circleIn(0.8) shouldBeClose 0.400000
        ease.circleIn(0.9) shouldBeClose 0.564110
        ease.circleIn(1.0) shouldBeClose 1.000000
    }


    @Test
    @JsName("easeCircleOut")
    fun `easeCircleOut(t) returns the expected results`() {
        val out = ease.circleIn::invoke.out
        ease.circleOut(0.0) shouldBeClose out(0.0)
        ease.circleOut(0.1) shouldBeClose out(0.1)
        ease.circleOut(0.2) shouldBeClose out(0.2)
        ease.circleOut(0.3) shouldBeClose out(0.3)
        ease.circleOut(0.4) shouldBeClose out(0.4)
        ease.circleOut(0.5) shouldBeClose out(0.5)
        ease.circleOut(0.6) shouldBeClose out(0.6)
        ease.circleOut(0.7) shouldBeClose out(0.7)
        ease.circleOut(0.8) shouldBeClose out(0.8)
        ease.circleOut(0.9) shouldBeClose out(0.9)
        ease.circleOut(1.0) shouldBeClose out(1.0)
    }

    @Test
    @JsName("easeCircleInOut")
    fun `easeCircleInOut(t) returns the expected results`() {
        val inOut = ease.circleIn::invoke.inOut
        ease.circleInOut(0.0) shouldBeClose inOut(0.0)
        ease.circleInOut(0.1) shouldBeClose inOut(0.1)
        ease.circleInOut(0.2) shouldBeClose inOut(0.2)
        ease.circleInOut(0.3) shouldBeClose inOut(0.3)
        ease.circleInOut(0.4) shouldBeClose inOut(0.4)
        ease.circleInOut(0.5) shouldBeClose inOut(0.5)
        ease.circleInOut(0.6) shouldBeClose inOut(0.6)
        ease.circleInOut(0.7) shouldBeClose inOut(0.7)
        ease.circleInOut(0.8) shouldBeClose inOut(0.8)
        ease.circleInOut(0.9) shouldBeClose inOut(0.9)
        ease.circleInOut(1.0) shouldBeClose inOut(1.0)
    }



    @Test
    @JsName("easeCubicIn")
    fun `easeCubicIn(t) returns the expected results`() {
        ease.cubicIn(0.0) shouldBeClose 0.000
        ease.cubicIn(0.1) shouldBeClose 0.001
        ease.cubicIn(0.2) shouldBeClose 0.008
        ease.cubicIn(0.3) shouldBeClose 0.027
        ease.cubicIn(0.4) shouldBeClose 0.064
        ease.cubicIn(0.5) shouldBeClose 0.125
        ease.cubicIn(0.6) shouldBeClose 0.216
        ease.cubicIn(0.7) shouldBeClose 0.343
        ease.cubicIn(0.8) shouldBeClose 0.512
        ease.cubicIn(0.9) shouldBeClose 0.729
        ease.cubicIn(1.0) shouldBeClose 1.000
    }


    @Test
    @JsName("cubicOut")
    fun `cubicOut(t) returns the expected results`() {
        val out = ease.cubicIn::invoke.out
        ease.cubicOut(0.0) shouldBeClose out(0.0)
        ease.cubicOut(0.1) shouldBeClose out(0.1)
        ease.cubicOut(0.2) shouldBeClose out(0.2)
        ease.cubicOut(0.3) shouldBeClose out(0.3)
        ease.cubicOut(0.4) shouldBeClose out(0.4)
        ease.cubicOut(0.5) shouldBeClose out(0.5)
        ease.cubicOut(0.6) shouldBeClose out(0.6)
        ease.cubicOut(0.7) shouldBeClose out(0.7)
        ease.cubicOut(0.8) shouldBeClose out(0.8)
        ease.cubicOut(0.9) shouldBeClose out(0.9)
        ease.cubicOut(1.0) shouldBeClose out(1.0)
    }

    @Test
    @JsName("cubicInOut")
    fun `cubicInOut(t) returns the expected results`() {
        val inOut = ease.cubicIn::invoke.inOut
        ease.cubicInOut(0.0) shouldBeClose inOut(0.0)
        ease.cubicInOut(0.1) shouldBeClose inOut(0.1)
        ease.cubicInOut(0.2) shouldBeClose inOut(0.2)
        ease.cubicInOut(0.3) shouldBeClose inOut(0.3)
        ease.cubicInOut(0.4) shouldBeClose inOut(0.4)
        ease.cubicInOut(0.5) shouldBeClose inOut(0.5)
        ease.cubicInOut(0.6) shouldBeClose inOut(0.6)
        ease.cubicInOut(0.7) shouldBeClose inOut(0.7)
        ease.cubicInOut(0.8) shouldBeClose inOut(0.8)
        ease.cubicInOut(0.9) shouldBeClose inOut(0.9)
        ease.cubicInOut(1.0) shouldBeClose inOut(1.0)
    }



    @Test
    @JsName("easeQuadIn")
    fun `easeQuadIn(t) returns the expected results`() {
        ease.quadIn(0.0) shouldBeClose 0.00
        ease.quadIn(0.1) shouldBeClose 0.01
        ease.quadIn(0.2) shouldBeClose 0.04
        ease.quadIn(0.3) shouldBeClose 0.09
        ease.quadIn(0.4) shouldBeClose 0.16
        ease.quadIn(0.5) shouldBeClose 0.25
        ease.quadIn(0.6) shouldBeClose 0.36
        ease.quadIn(0.7) shouldBeClose 0.49
        ease.quadIn(0.8) shouldBeClose 0.64
        ease.quadIn(0.9) shouldBeClose 0.81
        ease.quadIn(1.0) shouldBeClose 1.00
    }

    @Test
    @JsName("easeQuadOut")
    fun `easeQuadOut(t) returns the expected results`() {
        val out = ease.quadIn::invoke.out
        ease.quadOut(0.0) shouldBeClose out(0.0)
        ease.quadOut(0.1) shouldBeClose out(0.1)
        ease.quadOut(0.2) shouldBeClose out(0.2)
        ease.quadOut(0.3) shouldBeClose out(0.3)
        ease.quadOut(0.4) shouldBeClose out(0.4)
        ease.quadOut(0.5) shouldBeClose out(0.5)
        ease.quadOut(0.6) shouldBeClose out(0.6)
        ease.quadOut(0.7) shouldBeClose out(0.7)
        ease.quadOut(0.8) shouldBeClose out(0.8)
        ease.quadOut(0.9) shouldBeClose out(0.9)
        ease.quadOut(1.0) shouldBeClose out(1.0)
    }

    @Test
    @JsName("easeQuadInOut")
    fun `easeQuadInOut(t) returns the expected results`() {
        val inOut = ease.quadIn::invoke.inOut
        ease.quadInOut(0.0) shouldBeClose inOut(0.0)
        ease.quadInOut(0.1) shouldBeClose inOut(0.1)
        ease.quadInOut(0.2) shouldBeClose inOut(0.2)
        ease.quadInOut(0.3) shouldBeClose inOut(0.3)
        ease.quadInOut(0.4) shouldBeClose inOut(0.4)
        ease.quadInOut(0.5) shouldBeClose inOut(0.5)
        ease.quadInOut(0.6) shouldBeClose inOut(0.6)
        ease.quadInOut(0.7) shouldBeClose inOut(0.7)
        ease.quadInOut(0.8) shouldBeClose inOut(0.8)
        ease.quadInOut(0.9) shouldBeClose inOut(0.9)
        ease.quadInOut(1.0) shouldBeClose inOut(1.0)
    } 
    
    
    @Test
    @JsName("easeExpOut")
    fun `easeExpOut(t) returns the expected results`() {
        val expOut = ease.expIn::invoke.out
        ease.expOut(0.0) shouldBeClose expOut(0.0)
        ease.expOut(0.1) shouldBeClose expOut(0.1)
        ease.expOut(0.2) shouldBeClose expOut(0.2)
        ease.expOut(0.3) shouldBeClose expOut(0.3)
        ease.expOut(0.4) shouldBeClose expOut(0.4)
        ease.expOut(0.5) shouldBeClose expOut(0.5)
        ease.expOut(0.6) shouldBeClose expOut(0.6)
        ease.expOut(0.7) shouldBeClose expOut(0.7)
        ease.expOut(0.8) shouldBeClose expOut(0.8)
        ease.expOut(0.9) shouldBeClose expOut(0.9)
        ease.expOut(1.0) shouldBeClose expOut(1.0)
    }

    @Test
    @JsName("easeExpInOut")
    fun `easeExpInOut(t) returns the expected results`() {
        val expInOut = ease.expIn::invoke.inOut
        ease.expInOut(0.0) shouldBeClose expInOut(0.0)
        ease.expInOut(0.1) shouldBeClose expInOut(0.1)
        ease.expInOut(0.2) shouldBeClose expInOut(0.2)
        ease.expInOut(0.3) shouldBeClose expInOut(0.3)
        ease.expInOut(0.4) shouldBeClose expInOut(0.4)
        ease.expInOut(0.5) shouldBeClose expInOut(0.5)
        ease.expInOut(0.6) shouldBeClose expInOut(0.6)
        ease.expInOut(0.7) shouldBeClose expInOut(0.7)
        ease.expInOut(0.8) shouldBeClose expInOut(0.8)
        ease.expInOut(0.9) shouldBeClose expInOut(0.9)
        ease.expInOut(1.0) shouldBeClose expInOut(1.0)
    }

    @Test
    @JsName("easeBackIn")
    fun `easeBackIn(t) returns the expected results`() {
        ease.backIn(0.0) shouldBeClose  0.000000
        ease.backIn(0.1) shouldBeClose -0.014314
        ease.backIn(0.2) shouldBeClose -0.046451
        ease.backIn(0.3) shouldBeClose -0.080200
        ease.backIn(0.4) shouldBeClose -0.099352
        ease.backIn(0.5) shouldBeClose -0.087698
        ease.backIn(0.6) shouldBeClose -0.029028
        ease.backIn(0.7) shouldBeClose +0.092868
        ease.backIn(0.8) shouldBeClose +0.294198
        ease.backIn(0.9) shouldBeClose +0.591172
        ease.backIn(1.0) shouldBeClose +1.000000
    }

    @Test
    @JsName("easeBackOut")
    fun `easeBackOut(t) returns the expected results`() {
        val out = ease.backIn::invoke.out
        ease.backOut(0.0) shouldBeClose out(0.0)
        ease.backOut(0.1) shouldBeClose out(0.1)
        ease.backOut(0.2) shouldBeClose out(0.2)
        ease.backOut(0.3) shouldBeClose out(0.3)
        ease.backOut(0.4) shouldBeClose out(0.4)
        ease.backOut(0.5) shouldBeClose out(0.5)
        ease.backOut(0.6) shouldBeClose out(0.6)
        ease.backOut(0.7) shouldBeClose out(0.7)
        ease.backOut(0.8) shouldBeClose out(0.8)
        ease.backOut(0.9) shouldBeClose out(0.9)
        ease.backOut(1.0) shouldBeClose out(1.0)
    }

    @Test
    @JsName("easeBackInOut")
    fun `easeBackInOut(t) returns the expected results`() {
        val inOut = ease.backIn::invoke.inOut
        ease.backInOut(0.0) shouldBeClose inOut(0.0)
        ease.backInOut(0.1) shouldBeClose inOut(0.1)
        ease.backInOut(0.2) shouldBeClose inOut(0.2)
        ease.backInOut(0.3) shouldBeClose inOut(0.3)
        ease.backInOut(0.4) shouldBeClose inOut(0.4)
        ease.backInOut(0.5) shouldBeClose inOut(0.5)
        ease.backInOut(0.6) shouldBeClose inOut(0.6)
        ease.backInOut(0.7) shouldBeClose inOut(0.7)
        ease.backInOut(0.8) shouldBeClose inOut(0.8)
        ease.backInOut(0.9) shouldBeClose inOut(0.9)
        ease.backInOut(1.0) shouldBeClose inOut(1.0)
    }

    @Test
    @JsName("sinIn")
    fun `sinIn(t) returns the expected results`() {
        ease.sinIn(0.0) shouldBeClose 0.000000
        ease.sinIn(0.1) shouldBeClose 0.012312
        ease.sinIn(0.2) shouldBeClose 0.048943
        ease.sinIn(0.3) shouldBeClose 0.108993
        ease.sinIn(0.4) shouldBeClose 0.190983
        ease.sinIn(0.5) shouldBeClose 0.292893
        ease.sinIn(0.6) shouldBeClose 0.412215
        ease.sinIn(0.7) shouldBeClose 0.546010
        ease.sinIn(0.8) shouldBeClose 0.690983
        ease.sinIn(0.9) shouldBeClose 0.843566
        ease.sinIn(1.0) shouldBeClose 1.000000
    }

    @Test
    @JsName("sinOut")
    fun `sinOut(t) returns the expected results`() {
        val out = ease.sinIn::invoke.out
        ease.sinOut(0.0) shouldBeClose out(0.0)
        ease.sinOut(0.1) shouldBeClose out(0.1)
        ease.sinOut(0.2) shouldBeClose out(0.2)
        ease.sinOut(0.3) shouldBeClose out(0.3)
        ease.sinOut(0.4) shouldBeClose out(0.4)
        ease.sinOut(0.5) shouldBeClose out(0.5)
        ease.sinOut(0.6) shouldBeClose out(0.6)
        ease.sinOut(0.7) shouldBeClose out(0.7)
        ease.sinOut(0.8) shouldBeClose out(0.8)
        ease.sinOut(0.9) shouldBeClose out(0.9)
        ease.sinOut(1.0) shouldBeClose out(1.0)
    }

    @Test
    @JsName("sinInOut")
    fun `sinInOut(t) returns the expected results`() {
        val inOut = ease.sinIn::invoke.inOut
        ease.sinInOut(0.0) shouldBeClose inOut(0.0)
        ease.sinInOut(0.1) shouldBeClose inOut(0.1)
        ease.sinInOut(0.2) shouldBeClose inOut(0.2)
        ease.sinInOut(0.3) shouldBeClose inOut(0.3)
        ease.sinInOut(0.4) shouldBeClose inOut(0.4)
        ease.sinInOut(0.5) shouldBeClose inOut(0.5)
        ease.sinInOut(0.6) shouldBeClose inOut(0.6)
        ease.sinInOut(0.7) shouldBeClose inOut(0.7)
        ease.sinInOut(0.8) shouldBeClose inOut(0.8)
        ease.sinInOut(0.9) shouldBeClose inOut(0.9)
        ease.sinInOut(1.0) shouldBeClose inOut(1.0)
    }


    @Test
    @JsName("easeElasticIn")
    fun `easeElasticIn(t) returns the expected results`() {
        ease.elasticIn(0.0) shouldBeClose -0.000488
        ease.elasticIn(0.1) shouldBeClose  0.001953
        ease.elasticIn(0.2) shouldBeClose -0.001953
        ease.elasticIn(0.3) shouldBeClose -0.003906
        ease.elasticIn(0.4) shouldBeClose  0.015625
        ease.elasticIn(0.5) shouldBeClose -0.015625
        ease.elasticIn(0.6) shouldBeClose -0.031250
        ease.elasticIn(0.7) shouldBeClose  0.125000
        ease.elasticIn(0.8) shouldBeClose -0.125000
        ease.elasticIn(0.9) shouldBeClose -0.250000
        ease.elasticIn(1.0) shouldBeClose  1.000000
    }

    @Test
    @JsName("easeElasticInAmpPer")
    fun `easeElasticIn amplitude(1,5) period(1)(t) returns the expected results`() {
        ease.elasticIn.amplitude(1.5).period(1.0)(0.0) shouldBeClose  0.000977
        ease.elasticIn.amplitude(1.5).period(1.0)(0.1) shouldBeClose  0.000297
        ease.elasticIn.amplitude(1.5).period(1.0)(0.2) shouldBeClose -0.002946
        ease.elasticIn.amplitude(1.5).period(1.0)(0.3) shouldBeClose -0.010721
        ease.elasticIn.amplitude(1.5).period(1.0)(0.4) shouldBeClose -0.022909
        ease.elasticIn.amplitude(1.5).period(1.0)(0.5) shouldBeClose -0.031250
        ease.elasticIn.amplitude(1.5).period(1.0)(0.6) shouldBeClose -0.009491
        ease.elasticIn.amplitude(1.5).period(1.0)(0.7) shouldBeClose  0.094287
        ease.elasticIn.amplitude(1.5).period(1.0)(0.8) shouldBeClose  0.343083
        ease.elasticIn.amplitude(1.5).period(1.0)(0.9) shouldBeClose  0.733090
        ease.elasticIn.amplitude(1.5).period(1.0)(1.0) shouldBeClose  1.000000
    }

    @Test
    @JsName("easeElasticOut")
    fun `easeElasticOut(t) returns the expected results`() {
        val out = ease.elasticIn::invoke.out
        ease.elasticOut(0.0) shouldBeClose out(0.0)
        ease.elasticOut(0.1) shouldBeClose out(0.1)
        ease.elasticOut(0.2) shouldBeClose out(0.2)
        ease.elasticOut(0.3) shouldBeClose out(0.3)
        ease.elasticOut(0.4) shouldBeClose out(0.4)
        ease.elasticOut(0.5) shouldBeClose out(0.5)
        ease.elasticOut(0.6) shouldBeClose out(0.6)
        ease.elasticOut(0.7) shouldBeClose out(0.7)
        ease.elasticOut(0.8) shouldBeClose out(0.8)
        ease.elasticOut(0.9) shouldBeClose out(0.9)
        ease.elasticOut(1.0) shouldBeClose out(1.0)
    }

    @Test
    @JsName("easeElasticInOut")
    fun `easeElasticInOut(t) returns the expected results`() {
        val inOut = ease.elasticIn::invoke.inOut
        ease.elasticInOut(0.0) shouldBeClose inOut(0.0)
        ease.elasticInOut(0.1) shouldBeClose inOut(0.1)
        ease.elasticInOut(0.2) shouldBeClose inOut(0.2)
        ease.elasticInOut(0.3) shouldBeClose inOut(0.3)
        ease.elasticInOut(0.4) shouldBeClose inOut(0.4)
        ease.elasticInOut(0.5) shouldBeClose inOut(0.5)
        ease.elasticInOut(0.6) shouldBeClose inOut(0.6)
        ease.elasticInOut(0.7) shouldBeClose inOut(0.7)
        ease.elasticInOut(0.8) shouldBeClose inOut(0.8)
        ease.elasticInOut(0.9) shouldBeClose inOut(0.9)
        ease.elasticInOut(1.0) shouldBeClose inOut(1.0)
    }

    @Test
    @JsName("easePolyInt")
    fun `easePolyIn(t) returns the expected results`() {
        ease.polyIn(0.0) shouldBeClose 0.000
        ease.polyIn(0.1) shouldBeClose 0.001
        ease.polyIn(0.2) shouldBeClose 0.008
        ease.polyIn(0.3) shouldBeClose 0.027
        ease.polyIn(0.4) shouldBeClose 0.064
        ease.polyIn(0.5) shouldBeClose 0.125
        ease.polyIn(0.6) shouldBeClose 0.216
        ease.polyIn(0.7) shouldBeClose 0.343
        ease.polyIn(0.8) shouldBeClose 0.512
        ease.polyIn(0.9) shouldBeClose 0.729
        ease.polyIn(1.0) shouldBeClose 1.000
    }

    @Test
    @JsName("easePolyIntExp")
    fun `easePolyIn exponent(2,5)(t) returns the expected results`() {
        ease.polyIn.exponent(2.5)(0.0) shouldBeClose 0.000000
        ease.polyIn.exponent(2.5)(0.1) shouldBeClose 0.003162
        ease.polyIn.exponent(2.5)(0.2) shouldBeClose 0.017889
        ease.polyIn.exponent(2.5)(0.3) shouldBeClose 0.049295
        ease.polyIn.exponent(2.5)(0.4) shouldBeClose 0.101193
        ease.polyIn.exponent(2.5)(0.5) shouldBeClose 0.176777
        ease.polyIn.exponent(2.5)(0.6) shouldBeClose 0.278855
        ease.polyIn.exponent(2.5)(0.7) shouldBeClose 0.409963
        ease.polyIn.exponent(2.5)(0.8) shouldBeClose 0.572433
        ease.polyIn.exponent(2.5)(0.9) shouldBeClose 0.768433
        ease.polyIn.exponent(2.5)(1.0) shouldBeClose 1.000000
    }

    @Test
    @JsName("easePolyOutExp")
    fun `easePolyOut exponent(2,5)(t) returns the expected results`() {
        val polyOut = ease.polyIn.exponent(2.5)::invoke.out
        ease.polyOut.exponent(2.5)(0.0) shouldBeClose polyOut(0.0)
        ease.polyOut.exponent(2.5)(0.1) shouldBeClose polyOut(0.1)
        ease.polyOut.exponent(2.5)(0.2) shouldBeClose polyOut(0.2)
        ease.polyOut.exponent(2.5)(0.3) shouldBeClose polyOut(0.3)
        ease.polyOut.exponent(2.5)(0.4) shouldBeClose polyOut(0.4)
        ease.polyOut.exponent(2.5)(0.5) shouldBeClose polyOut(0.5)
        ease.polyOut.exponent(2.5)(0.6) shouldBeClose polyOut(0.6)
        ease.polyOut.exponent(2.5)(0.7) shouldBeClose polyOut(0.7)
        ease.polyOut.exponent(2.5)(0.8) shouldBeClose polyOut(0.8)
        ease.polyOut.exponent(2.5)(0.9) shouldBeClose polyOut(0.9)
        ease.polyOut.exponent(2.5)(1.0) shouldBeClose polyOut(1.0)
    }

    @Test
    @JsName("easePolyInOutExp")
    fun `easePolyInOut exponent(2,5)(t) returns the expected results`() {
        val polyInOut = ease.polyIn.exponent(2.5)::invoke.inOut
        ease.polyInOut.exponent(2.5)(0.0) shouldBeClose polyInOut(0.0)
        ease.polyInOut.exponent(2.5)(0.1) shouldBeClose polyInOut(0.1)
        ease.polyInOut.exponent(2.5)(0.2) shouldBeClose polyInOut(0.2)
        ease.polyInOut.exponent(2.5)(0.3) shouldBeClose polyInOut(0.3)
        ease.polyInOut.exponent(2.5)(0.4) shouldBeClose polyInOut(0.4)
        ease.polyInOut.exponent(2.5)(0.5) shouldBeClose polyInOut(0.5)
        ease.polyInOut.exponent(2.5)(0.6) shouldBeClose polyInOut(0.6)
        ease.polyInOut.exponent(2.5)(0.7) shouldBeClose polyInOut(0.7)
        ease.polyInOut.exponent(2.5)(0.8) shouldBeClose polyInOut(0.8)
        ease.polyInOut.exponent(2.5)(0.9) shouldBeClose polyInOut(0.9)
        ease.polyInOut.exponent(2.5)(1.0) shouldBeClose polyInOut(1.0)
    }

}

val EaseFun.out: EaseFun
    get() = { 1 - this(1 - it) }

val EaseFun.inOut: EaseFun
    get() = {
        (if (it < .5)
            this(it * 2)
        else (2 - this((1 - it) * 2))) / 2
    }
