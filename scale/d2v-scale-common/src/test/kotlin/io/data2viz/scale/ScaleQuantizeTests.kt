package io.data2viz.scale

import io.data2viz.test.TestBase
import kotlin.test.Test

class ScaleQuantizeTests : TestBase() {

    @Test
    fun quantize_expected_defaults_LEGACY() {
        val scale = scales.quantize<Double>()
        scale.range = listOf(.0, 1.0)

        scale.domain shouldBe intervalOf(.0, 1.0)
        scale.range shouldBe listOf(.0, 1.0)
        scale(-1.0) shouldBeClose .0
        scale(.0) shouldBeClose .0
        scale(.25) shouldBeClose .0
        scale(.75) shouldBeClose 1.0
        scale(1.0) shouldBeClose 1.0
        scale(2.0) shouldBeClose 1.0
    }

    @Test
    fun quantize_values_give_expected_discreet_results_LEGACY() {
        val scale = scales.quantize<Double>()
        scale.range = listOf(.0, 1.0, 2.0)

        scale(.0) shouldBeClose .0
        scale(.2) shouldBeClose .0
        scale(.4) shouldBeClose 1.0
        scale(.6) shouldBeClose 1.0
        scale(.8) shouldBeClose 2.0
        scale(1.0) shouldBeClose 2.0
    }

    @Test
    fun quantize_values_clamps_input_values_to_domain() {
        val scale = scales.quantize<String>()
        scale.range = listOf("red", "green", "blue")

        scale(.0) shouldBe "red"
        scale(.2) shouldBe "red"
        scale(.4) shouldBe "green"
        scale(.6) shouldBe "green"
        scale(.8) shouldBe "blue"
        scale(1.0) shouldBe "blue"
    }

    @Test
    fun quantize_values_clamps_input_values_to_domain_LEGACY() {
        val scale = scales.quantize<() -> Unit>()
        val a = {}
        val b = {}
        val c = {}
        scale.range = listOf(a, b, c)

        scale(.0) shouldBe a
        scale(.2) shouldBe a
        scale(.4) shouldBe b
        scale(.6) shouldBe b
        scale(.8) shouldBe c
        scale(1.0) shouldBe c
    }

    @Test
    fun quantize_range_cardinality_determines_degre_of_quantization_LEGACY() {
        val scale = scales.quantize<Double>()
        val aThird = 1.0 / 3.0
        
        
        fun doubleRange(to:Double, step: Double):List<Double>  = 
            (0..(to/step).toInt()).fold(mutableListOf<Double>()) { r,t-> r.apply { add(step*t) }}

        scale.range = doubleRange(1.000, 0.001); scale(aThird) shouldBeClose .333
        scale.range = doubleRange(1.000, 0.010); scale(aThird) shouldBeClose .330
        scale.range = doubleRange(1.000, 0.100); scale(aThird) shouldBeClose .300
        scale.range = doubleRange(1.000, 0.200); scale(aThird) shouldBeClose .400
        scale.range = doubleRange(1.000, 0.250); scale(aThird) shouldBeClose .250
        scale.range = doubleRange(1.000, 0.500); scale(aThird) shouldBeClose .500
        scale.range = doubleRange(1.000, 1.500); scale(aThird) shouldBeClose .0
    }

    @Test
    fun quantize_invertExtent_maps_value_in_the_range_to_domain_extent_LEGACY() {
        val scale = scales.quantize<Double>()
        scale.range = listOf(0.0, 1.0, 2.0, 3.0)

        scale.invertExtent(.0) shouldBe listOf(.0, .25)
        scale.invertExtent(1.0) shouldBe listOf(.25, .5)
        scale.invertExtent(2.0) shouldBe listOf(.5, .75)
        scale.invertExtent(3.0) shouldBe listOf(.75, 1.0)
    }

    @Test
    fun quantize_invertExtent_allows_arbitrary_range_values_LEGACY() {
        val scale = scales.quantize<() -> Unit>()
        val a = {}
        val b = {}
        scale.range = listOf(a, b)

        scale.invertExtent(a) shouldBe listOf(.0, .5)
        scale.invertExtent(b) shouldBe listOf(.5, 1.0)
    }

    @Test
    fun quantize_invertExtent_returns_NaN_NaN_when_given_value_not_in_the_range_LEGACY() {
        val scale = scales.quantize<Double>()

        val notFound = listOf(Double.NaN, Double.NaN)
        scale.invertExtent(-1.0) shouldBe notFound
        scale.invertExtent(.5) shouldBe notFound
        scale.invertExtent(2.0) shouldBe notFound
    }

    @Test
    fun quantize_invertExtent_returns_first_match_if_duplicates_values_exists_in_the_range_LEGACY() {
        val scale = scales.quantize<Double>()
        scale.range = listOf(0.0, 1.0, 2.0, 0.0)

        scale.invertExtent(.0) shouldBe listOf(.0, .25)
        scale.invertExtent(1.0) shouldBe listOf(.25, .5)
    }

    @Test
    fun quantize_invertExtent_y_is_exactly_consistent_with_quantize_x_LEGACY() {
        val scale = scales.quantize<Double>()
        scale.range = (0 until 10).map({it.toDouble()}).toList()
        scale.domain = intervalOf(4.2, 6.2)

        scale.range.forEach { r ->
            val e = scale.invertExtent(r)
            scale(e[0]) shouldBeClose r
            if (r < 9.0) scale(e[1]) shouldBeClose (r + 1.0)
            else scale(e[1]) shouldBeClose r
        }
    }
}