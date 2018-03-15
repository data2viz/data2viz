package io.data2viz.ease

import io.data2viz.test.JsName
import io.data2viz.test.TestBase
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
