package io.data2viz.scale

import io.data2viz.color.HSL
import io.data2viz.math.deg
import io.data2viz.test.TestBase
import io.data2viz.test.shouldThrow
import kotlin.test.Test

class ScaleTests : TestBase() {

    val epsilon = 1e6

    @Test
    fun linear_no_interpolation() {
        val scale = linearScaleDouble()

        scale.domain(.0, 1.0)
        scale.range(.0, 1.0)

//        scale.domainsToRanges(
//                DomainToRange(.0, .0),
//                DomainToRange(1.0, 1.0)
//        )

        scale(.5) shouldBe .5
        scale(0.1) shouldBe 0.1
        scale(0.133) shouldBe 0.133
        scale(0.9) shouldBe 0.9

        scale.domain(.0, 1.0)
        scale.range(1.0, .0)

        /*scale.domainsToRanges(
                DomainToRange(.0, 1.0),
                DomainToRange(1.0, .0)
        )*/

        scale(.5) shouldBe 0.5
        scale(0.1) shouldBe 0.9
        scale(0.133) shouldBe 0.867
        scale(0.9) shouldBe (0.1 plusOrMinus 1e6)            // TODO find why it is in fact 0.09999999999999998
    }

    @Test
    fun linear_Number() {
        val scale = linearScaleDouble()

//        scale.domainsToRanges(
//                DomainToRange(.0, 100.0),
//                DomainToRange(100.0, .0)
//        )

        scale.domain(.0, 100.0)
        scale.range(100.0, .0)

        scale(50.0) shouldBe 50.0
        scale(10.0) shouldBe 90.0
        scale(13.3) shouldBe 86.7
        scale(90.0) shouldBe 10.0
    }


    @Test
    fun linear_Number_clamp_noclamp() {
        val scale = linearScaleDouble()

//        scale.domainsToRanges(
//                DomainToRange(.0, .0),
//                DomainToRange(100.0, -100.0)
//        )

        scale.domain(.0, 100.0)
        scale.range(.0, -100.0)

        scale(50.0) shouldBe -50.0
        scale(10.0) shouldBe (-10.0 plusOrMinus epsilon)
        scale(13.3) shouldBe (-13.3 plusOrMinus epsilon)
        scale(90.0) shouldBe -90.0
        scale(-20.0) shouldBe 20.0
        scale(132.0) shouldBe (-132.0 plusOrMinus epsilon)

        scale.clamp = true

        scale(50.0) shouldBe -50.0
        scale(10.0) shouldBe (-10.0 plusOrMinus epsilon)
        scale(13.3) shouldBe (-13.3 plusOrMinus epsilon)
        scale(90.0) shouldBe -90.0
        scale(-20.0) shouldBe 0.0
        scale(132.0) shouldBe (-100.0 plusOrMinus epsilon)
    }

    @Test
    fun linear_Number_invert() {
        val scale = linearScaleDouble()

//        scale.domainsToRanges(
//                DomainToRange(.0, 100.0),
//                DomainToRange(100.0, .0)
//        )

        scale.domain(.0, 100.0)
        scale.range(100.0, .0)

        scale.invert(50.0) shouldBe 50.0
        scale.invert(90.0) shouldBe 10.0
        scale.invert(86.7) shouldBe (13.3 plusOrMinus epsilon)
        scale.invert(10.0) shouldBe (90.0 plusOrMinus epsilon)
    }

    @Test
    fun linear_Number_multiple_ranges() {
        val scale = linearScaleDouble()

        // x -> 10x between 0 and 10
        // x -> 20x between 10 and 20
//        scale.domainsToRanges(
//                DomainToRange(.0, .0),
//                DomainToRange(10.0, 100.0),
//                DomainToRange(20.0, 300.0)
//        )

        scale.domain(.0, 10.0, 20.0)
        scale.range(.0, 100.0, 300.0)

        scale(0.0) shouldBe 0.0
        scale(10.0) shouldBe 100.0
        scale(20.0) shouldBe 300.0
        scale(5.0) shouldBe 50.0
        scale(15.0) shouldBe 200.0
        scale(17.0) shouldBe 240.0
    }

    @Test
    fun linear_Number_multiple_ranges_invert() {
        val scale = linearScaleDouble()

        // x -> 10x between 0 and 10
        // x -> 20x between 10 and 20
        /*scale.domainsToRanges(
                DomainToRange(.0, 0.0),
                DomainToRange(10.0, 100.0),
                DomainToRange(20.0, 300.0)
        )*/
        scale.domain(.0, 10.0, 20.0)
        scale.range(.0, 100.0, 300.0)

        scale.invert(0.0) shouldBe 0.0
        scale.invert(100.0) shouldBe 10.0
        scale.invert(300.0) shouldBe 20.0
        scale.invert(50.0) shouldBe 5.0
        scale.invert(200.0) shouldBe 15.0
        scale.invert(240.0) shouldBe 17.0
    }

    @Test
    fun linear_HSL() {
        val scale = linearScaleHSL()

//        scale.domainsToRanges(
//                DomainToRange(.0, HSL(0.deg)),
//                DomainToRange(100.0, HSL(180.deg))
//        )

        scale.domain(.0, 100.0)
        scale.range(HSL(0.deg), HSL(180.deg))

        scale(50.0) shouldBe HSL(90.deg)
        scale(20.0) shouldBe HSL(36.deg)
        scale(90.0) shouldBe HSL(162.deg)
    }


    @Test
    fun linear_ticks_nice_OK_LEGACY() {
        val scale = linearScaleDouble()

        scale.domain(12.0, 87.0)
        scale.nice(5)
        scale.domain.size shouldBe 2
        scale.domain.first() shouldBe .0
        scale.domain.last() shouldBe 100.0

        scale.domain(12.0, 87.0)
        scale.nice(10)
        scale.domain.size shouldBe 2
        scale.domain.first() shouldBe 10.0
        scale.domain.last() shouldBe 90.0

        scale.domain(12.0, 87.0)
        scale.nice(100)
        scale.domain.size shouldBe 2
        scale.domain.first() shouldBe 12.0
        scale.domain.last() shouldBe 87.0
    }

    @Test
    fun linear_ticks_return_expected_ticks_LEGACY() {
        val scale = linearScaleDouble()
        scale.domain(.0, 1.0)

        scale.ticks(10).size shouldBe 11
        scale.ticks(10) shouldBe arrayListOf(0.0, 0.1, 0.2, 0.3, 0.4, 0.5, 0.6, 0.7, 0.8, 0.9, 1.0)
        scale.ticks(9) shouldBe  arrayListOf(0.0, 0.1, 0.2, 0.3, 0.4, 0.5, 0.6, 0.7, 0.8, 0.9, 1.0)
        scale.ticks(8) shouldBe  arrayListOf(0.0, 0.1, 0.2, 0.3, 0.4, 0.5, 0.6, 0.7, 0.8, 0.9, 1.0)
        scale.ticks(7) shouldBe  arrayListOf(0.0,      0.2,      0.4,      0.6,      0.8,      1.0)
        scale.ticks(6) shouldBe  arrayListOf(0.0,      0.2,      0.4,      0.6,      0.8,      1.0)
        scale.ticks(5) shouldBe  arrayListOf(0.0,      0.2,      0.4,      0.6,      0.8,      1.0)
        scale.ticks(4) shouldBe  arrayListOf(0.0,      0.2,      0.4,      0.6,      0.8,      1.0)
        scale.ticks(3) shouldBe  arrayListOf(0.0,                     0.5,                     1.0)
        scale.ticks(2) shouldBe  arrayListOf(0.0,                     0.5,                     1.0)
        scale.ticks(1) shouldBe  arrayListOf(0.0,                                              1.0)

        scale.domain(-100.0, 100.0)
        scale.ticks(10) shouldBe arrayListOf(-100.0, -80.0, -60.0,        -40.0, -20.0, .0, 20.0, 40.0,       60.0, 80.0, 100.0)
        scale.ticks(9) shouldBe  arrayListOf(-100.0, -80.0, -60.0,        -40.0, -20.0, .0, 20.0, 40.0,       60.0, 80.0, 100.0)
        scale.ticks(8) shouldBe  arrayListOf(-100.0, -80.0, -60.0,        -40.0, -20.0, .0, 20.0, 40.0,       60.0, 80.0, 100.0)
        scale.ticks(7) shouldBe  arrayListOf(-100.0, -80.0, -60.0,        -40.0, -20.0, .0, 20.0, 40.0,       60.0, 80.0, 100.0)
        scale.ticks(6) shouldBe  arrayListOf(-100.0,               -50.0,               .0,             50.0,             100.0)
        scale.ticks(5) shouldBe  arrayListOf(-100.0,               -50.0,               .0,             50.0,             100.0)
        scale.ticks(4) shouldBe  arrayListOf(-100.0,               -50.0,               .0,             50.0,             100.0)
        scale.ticks(3) shouldBe  arrayListOf(-100.0,               -50.0,               .0,             50.0,             100.0)
        scale.ticks(2) shouldBe  arrayListOf(-100.0,                                    .0,                               100.0)
        scale.ticks(1) shouldBe  arrayListOf(                                           .0                                     )
    }

    @Test
    fun linear_ticks_descending_domain_return_expected_ticks_LEGACY() {
        val scale = linearScaleDouble()
        scale.domain(1.0, .0)

        scale.ticks(10).size shouldBe 11
        scale.ticks(10) shouldBe arrayListOf(0.0, 0.1, 0.2, 0.3, 0.4, 0.5, 0.6, 0.7, 0.8, 0.9, 1.0).reversed()
        scale.ticks(9) shouldBe  arrayListOf(0.0, 0.1, 0.2, 0.3, 0.4, 0.5, 0.6, 0.7, 0.8, 0.9, 1.0).reversed()
        scale.ticks(8) shouldBe  arrayListOf(0.0, 0.1, 0.2, 0.3, 0.4, 0.5, 0.6, 0.7, 0.8, 0.9, 1.0).reversed()
        scale.ticks(7) shouldBe  arrayListOf(0.0,      0.2,      0.4,      0.6,      0.8,      1.0).reversed()
        scale.ticks(6) shouldBe  arrayListOf(0.0,      0.2,      0.4,      0.6,      0.8,      1.0).reversed()
        scale.ticks(5) shouldBe  arrayListOf(0.0,      0.2,      0.4,      0.6,      0.8,      1.0).reversed()
        scale.ticks(4) shouldBe  arrayListOf(0.0,      0.2,      0.4,      0.6,      0.8,      1.0).reversed()
        scale.ticks(3) shouldBe  arrayListOf(0.0,                     0.5,                     1.0).reversed()
        scale.ticks(2) shouldBe  arrayListOf(0.0,                     0.5,                     1.0).reversed()
        scale.ticks(1) shouldBe  arrayListOf(0.0,                                              1.0).reversed()

        scale.domain(100.0, -100.0)
        scale.ticks(10) shouldBe arrayListOf(-100.0, -80.0, -60.0,        -40.0, -20.0, .0, 20.0, 40.0,       60.0, 80.0, 100.0).reversed()
        scale.ticks(9) shouldBe  arrayListOf(-100.0, -80.0, -60.0,        -40.0, -20.0, .0, 20.0, 40.0,       60.0, 80.0, 100.0).reversed()
        scale.ticks(8) shouldBe  arrayListOf(-100.0, -80.0, -60.0,        -40.0, -20.0, .0, 20.0, 40.0,       60.0, 80.0, 100.0).reversed()
        scale.ticks(7) shouldBe  arrayListOf(-100.0, -80.0, -60.0,        -40.0, -20.0, .0, 20.0, 40.0,       60.0, 80.0, 100.0).reversed()
        scale.ticks(6) shouldBe  arrayListOf(-100.0,               -50.0,               .0,             50.0,             100.0).reversed()
        scale.ticks(5) shouldBe  arrayListOf(-100.0,               -50.0,               .0,             50.0,             100.0).reversed()
        scale.ticks(4) shouldBe  arrayListOf(-100.0,               -50.0,               .0,             50.0,             100.0).reversed()
        scale.ticks(3) shouldBe  arrayListOf(-100.0,               -50.0,               .0,             50.0,             100.0).reversed()
        scale.ticks(2) shouldBe  arrayListOf(-100.0,                                    .0,                               100.0).reversed()
        scale.ticks(1) shouldBe  arrayListOf(                                           .0                                     ).reversed()
    }

    @Test
    fun linear_ticks_polylinear_domain_return_expected_ticks_LEGACY() {
        val scale = linearScaleDouble()
        scale.domain(.0, 0.25, 0.9, 1.0)

        scale.ticks(10).size shouldBe 11
        scale.ticks(10) shouldBe arrayListOf(0.0, 0.1, 0.2, 0.3, 0.4, 0.5, 0.6, 0.7, 0.8, 0.9, 1.0)
        scale.ticks(9) shouldBe  arrayListOf(0.0, 0.1, 0.2, 0.3, 0.4, 0.5, 0.6, 0.7, 0.8, 0.9, 1.0)
        scale.ticks(8) shouldBe  arrayListOf(0.0, 0.1, 0.2, 0.3, 0.4, 0.5, 0.6, 0.7, 0.8, 0.9, 1.0)
        scale.ticks(7) shouldBe  arrayListOf(0.0,      0.2,      0.4,      0.6,      0.8,      1.0)
        scale.ticks(6) shouldBe  arrayListOf(0.0,      0.2,      0.4,      0.6,      0.8,      1.0)
        scale.ticks(5) shouldBe  arrayListOf(0.0,      0.2,      0.4,      0.6,      0.8,      1.0)
        scale.ticks(4) shouldBe  arrayListOf(0.0,      0.2,      0.4,      0.6,      0.8,      1.0)
        scale.ticks(3) shouldBe  arrayListOf(0.0,                     0.5,                     1.0)
        scale.ticks(2) shouldBe  arrayListOf(0.0,                     0.5,                     1.0)
        scale.ticks(1) shouldBe  arrayListOf(0.0,                                              1.0)

        scale.domain(-100.0, .0, 100.0)
        scale.ticks(10) shouldBe arrayListOf(-100.0, -80.0, -60.0,        -40.0, -20.0, .0, 20.0, 40.0,       60.0, 80.0, 100.0)
        scale.ticks(9) shouldBe  arrayListOf(-100.0, -80.0, -60.0,        -40.0, -20.0, .0, 20.0, 40.0,       60.0, 80.0, 100.0)
        scale.ticks(8) shouldBe  arrayListOf(-100.0, -80.0, -60.0,        -40.0, -20.0, .0, 20.0, 40.0,       60.0, 80.0, 100.0)
        scale.ticks(7) shouldBe  arrayListOf(-100.0, -80.0, -60.0,        -40.0, -20.0, .0, 20.0, 40.0,       60.0, 80.0, 100.0)
        scale.ticks(6) shouldBe  arrayListOf(-100.0,               -50.0,               .0,             50.0,             100.0)
        scale.ticks(5) shouldBe  arrayListOf(-100.0,               -50.0,               .0,             50.0,             100.0)
        scale.ticks(4) shouldBe  arrayListOf(-100.0,               -50.0,               .0,             50.0,             100.0)
        scale.ticks(3) shouldBe  arrayListOf(-100.0,               -50.0,               .0,             50.0,             100.0)
        scale.ticks(2) shouldBe  arrayListOf(-100.0,                                    .0,                               100.0)
        scale.ticks(1) shouldBe  arrayListOf(                                           .0                                     )
    }

    @Test
    fun linear_ticks_count_should_be_greater_than_zero_LEGACY() {
        val scale = linearScaleDouble()
        scale.domain(.0, 1.0)

        scale.ticks(0).size shouldBe 0
        scale.ticks(0) shouldBe arrayListOf<Double>()
        scale.ticks(-2).size shouldBe 0
        scale.ticks(-2) shouldBe arrayListOf<Double>()

        // TODO shouldThrow ??
//        shouldThrow<IllegalArgumentException> { scale.ticks(-2) }
//        shouldThrow<IllegalArgumentException> { scale.ticks(Int.MIN_VALUE) }
    }

    @Test
    fun linear_ticks_count_without_arguments_should_be_count_equals_10_LEGACY() {
        val scale = linearScaleDouble()
        scale.domain(.0, 1.0)

        scale.ticks().size shouldBe scale.ticks(10).size
    }
        /*

    tape("linear.tickFormat() is an alias for linear.tickFormat(10)", function(test) {
        test.equal(scale.scaleLinear().tickFormat()(0.2), "0.2");
        test.equal(scale.scaleLinear().domain([-100, 100]).tickFormat()(-20), "-20");
        test.end();
    });

    tape("linear.tickFormat(count) returns a format suitable for the ticks", function(test) {
        test.equal(scale.scaleLinear().tickFormat(10)(0.2), "0.2");
        test.equal(scale.scaleLinear().tickFormat(20)(0.2), "0.20");
        test.equal(scale.scaleLinear().domain([-100, 100]).tickFormat(10)(-20), "-20");
        test.end();
    });

    tape("linear.tickFormat(count, specifier) sets the appropriate fixed precision if not specified", function(test) {
        test.equal(scale.scaleLinear().tickFormat(10, "+f")(0.2), "+0.2");
        test.equal(scale.scaleLinear().tickFormat(20, "+f")(0.2), "+0.20");
        test.equal(scale.scaleLinear().tickFormat(10, "+%")(0.2), "+20%");
        test.equal(scale.scaleLinear().domain([0.19, 0.21]).tickFormat(10, "+%")(0.2), "+20.0%");
        test.end();
    });

    tape("linear.tickFormat(count, specifier) sets the appropriate round precision if not specified", function(test) {
        test.equal(scale.scaleLinear().domain([0, 9]).tickFormat(10, "")(2.10), "2");
        test.equal(scale.scaleLinear().domain([0, 9]).tickFormat(100, "")(2.01), "2");
        test.equal(scale.scaleLinear().domain([0, 9]).tickFormat(100, "")(2.11), "2.1");
        test.equal(scale.scaleLinear().domain([0, 9]).tickFormat(10, "e")(2.10), "2e+0");
        test.equal(scale.scaleLinear().domain([0, 9]).tickFormat(100, "e")(2.01), "2.0e+0");
        test.equal(scale.scaleLinear().domain([0, 9]).tickFormat(100, "e")(2.11), "2.1e+0");
        test.equal(scale.scaleLinear().domain([0, 9]).tickFormat(10, "g")(2.10), "2");
        test.equal(scale.scaleLinear().domain([0, 9]).tickFormat(100, "g")(2.01), "2.0");
        test.equal(scale.scaleLinear().domain([0, 9]).tickFormat(100, "g")(2.11), "2.1");
        test.equal(scale.scaleLinear().domain([0, 9]).tickFormat(10, "r")(2.10e6), "2000000");
        test.equal(scale.scaleLinear().domain([0, 9]).tickFormat(100, "r")(2.01e6), "2000000");
        test.equal(scale.scaleLinear().domain([0, 9]).tickFormat(100, "r")(2.11e6), "2100000");
        test.equal(scale.scaleLinear().domain([0, 0.9]).tickFormat(10, "p")(0.210), "20%");
        test.equal(scale.scaleLinear().domain([0.19, 0.21]).tickFormat(10, "p")(0.201), "20.1%");
        test.end();
    });

    tape("linear.tickFormat(count, specifier) sets the appropriate prefix precision if not specified", function(test) {
        test.equal(scale.scaleLinear().domain([0, 1e6]).tickFormat(10, "$s")(0.51e6), "$0.5M");
        test.equal(scale.scaleLinear().domain([0, 1e6]).tickFormat(100, "$s")(0.501e6), "$0.50M");
        test.end();
    });

    tape("linear.tickFormat() uses the default precision when the domain is invalid", function(test) {
        var f = scale.scaleLinear().domain([0, NaN]).tickFormat();
        test.equal(f + "", " >-,f");
        test.equal(f(0.12), "0.120000");
        test.end();
    });*/

}