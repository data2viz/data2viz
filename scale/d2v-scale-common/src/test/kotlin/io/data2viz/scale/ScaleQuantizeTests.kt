package io.data2viz.scale

import io.data2viz.test.TestBase
import io.data2viz.test.shouldThrow
import kotlin.test.Test

class ScaleQuantizeTests : TestBase() {

    val epsilon = 1e6

    @Test
    fun quantize_expected_defaults_LEGACY() {
        val scale = scaleQuantize<Double>()
        scale.range = listOf(.0, 1.0)

        scale.domain shouldBe arrayListOf(.0, 1.0)
        scale.range shouldBe arrayListOf(.0, 1.0)
        scale(-1.0) shouldBe .0
        scale(.0) shouldBe .0
        scale(.25) shouldBe .0
        scale(.75) shouldBe 1.0
        scale(1.0) shouldBe 1.0
        scale(2.0) shouldBe 1.0
    }

    @Test
    fun quantize_values_give_expected_discreet_results_LEGACY() {
        val scale = scaleQuantize<Double>()
        scale.range = listOf(.0, 1.0, 2.0)

        scale(.0) shouldBe .0
        scale(.2) shouldBe .0
        scale(.4) shouldBe 1.0
        scale(.6) shouldBe 1.0
        scale(.8) shouldBe 2.0
        scale(1.0) shouldBe 2.0
    }

    @Test
    fun quantize_values_clamps_input_values_to_domain() {
        val scale = scaleQuantize<String>()
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
        val scale = scaleQuantize<()->Unit>()
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
    fun quantize_domain_with_domain_size_different_of_2_throw_exception() {
        val scale = scaleQuantize<String>()
        shouldThrow<IllegalArgumentException> { scale.domain = listOf(1.0); return }
        shouldThrow<IllegalArgumentException> { scale.domain = listOf(1.0, 2.0, 3.0); return }
    }

    @Test
    fun quantize_range_cardinality_determines_degre_of_quantization_LEGACY() {
        val scale = scaleQuantize<Double>()
        val aThird = 1.0 / 3.0

        scale.range = (0..1001).map({it/1000.0}).toMutableList()
        scale(aThird) shouldBe (0.333 plusOrMinus epsilon)

        scale.range = (0..1010).map({it/100.0}).toMutableList()
        scale(aThird) shouldBe (0.330 plusOrMinus epsilon)

        scale.range = (0..1100).map({it/10.0}).toMutableList()
        scale(aThird) shouldBe (0.300 plusOrMinus epsilon)

        scale.range = (0..1200).map({it/6.0}).toMutableList()
        scale(aThird) shouldBe (0.400 plusOrMinus epsilon)

        scale.range = (0..1250).map({it/5.0}).toMutableList()
        scale(aThird) shouldBe (0.250 plusOrMinus epsilon)

        scale.range = (0..1500).map({it/3.0}).toMutableList()
        scale(aThird) shouldBe (0.500 plusOrMinus epsilon)

        scale.range = arrayListOf(0.0, 1.0)
        scale(aThird) shouldBe (.0 plusOrMinus epsilon)
    }

    @Test
    fun quantize_invertExtent_maps_value_in_the_range_to_domain_extent_LEGACY() {
        val scale = scaleQuantize<Double>()
        scale.range = listOf(0.0, 1.0, 2.0, 3.0)

        scale.invertExtent(.0) shouldBe arrayListOf(.0, .25)
        scale.invertExtent(1.0) shouldBe arrayListOf(.25, .5)
        scale.invertExtent(2.0) shouldBe arrayListOf(.5, .75)
        scale.invertExtent(3.0) shouldBe arrayListOf(.75, 1.0)
    }

    @Test
    fun quantize_invertExtent_allows_arbitrary_range_values_LEGACY() {
        val scale = scaleQuantize<()->Unit>()
        val a = {}
        val b = {}
        scale.range = listOf(a, b)

        scale.invertExtent(a) shouldBe arrayListOf(.0, .5)
        scale.invertExtent(b) shouldBe arrayListOf(.5, 1.0)
    }

    @Test
    fun quantize_invertExtent_returns_NaN_NaN_when_given_value_not_in_the_range_LEGACY() {
        val scale = scaleQuantize<Double>()

        val notFound = arrayListOf(Double.NaN, Double.NaN)
        scale.invertExtent(-1.0) shouldBe notFound
        scale.invertExtent(.5) shouldBe notFound
        scale.invertExtent(2.0) shouldBe notFound
    }

    @Test
    fun quantize_invertExtent_returns_first_match_if_duplicates_values_exists_in_the_range_LEGACY() {
        val scale = scaleQuantize<Double>()
        scale.range = listOf(0.0, 1.0, 2.0, 0.0)

        scale.invertExtent(.0) shouldBe arrayListOf(.0, .25)
        scale.invertExtent(1.0) shouldBe arrayListOf(.25, .5)
    }

    @Test
    fun quantize_invertExtent_y_is_exactly_consistent_with_quantize_x_LEGACY() {
        val scale = scaleQuantize<Double>()
        scale.range = (0 until 10).map({it.toDouble()}).toMutableList()
        scale.domain = listOf(4.2, 6.2)

        scale.range.forEach { r ->
            val e = scale.invertExtent(r)
            scale(e[0]) shouldBe r
            if (r < 9.0) scale(e[1]) shouldBe (r + 1.0)
            else scale(e[1]) shouldBe r
        }
    }
}