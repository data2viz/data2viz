package io.data2viz.scale

import io.data2viz.test.TestBase
import io.data2viz.test.shouldThrow
import kotlin.test.Test

class ScaleOrdinalTests : TestBase() {

    @Test
    fun ordinal_has_expected_defaults_LEGACY() {
        val scale = scaleOrdinal<Int, Int>()

        scale.domain shouldBe listOf()
        scale.range shouldBe listOf()
        shouldThrow<IllegalStateException> { scale(0) }
        scale.domain shouldBe listOf(0)
    }

    @Test
    fun ordinal_x_maps_unique_x_in_domain_to_corresponding_value_in_range_LEGACY() {
        val scale = scaleOrdinal<Int, String>()

        scale.domain = listOf(0, 1)
        scale.range = listOf("foo", "bar")
        scale(0) shouldBe "foo"
        scale(1) shouldBe "bar"

        scale.range = listOf("a", "b", "c")
        scale(0) shouldBe "a"
        scale(1) shouldBe "b"
    }

    @Test
    fun ordinal_x_implicitly_extends_the_domain_when_a_range_is_explicitly_specified_LEGACY() {
        val scale = scaleOrdinal<Int, String>()
        scale.range = listOf("foo", "bar")

        scale.domain shouldBe listOf()
        scale(0) shouldBe "foo"
        scale.domain shouldBe listOf(0)
        scale(1) shouldBe "bar"
        scale.domain shouldBe listOf(0, 1)
        scale(0) shouldBe "foo"
        scale.domain shouldBe listOf(0, 1)
    }

    @Test
    fun ordinal_domain_makes_a_copy_of_the_domain_LEGACY() {
        val scale = scaleOrdinal<String, String>()

        val domain = arrayListOf("red", "green")
        scale.domain = domain
        domain.add("blue")
        scale.domain shouldBe listOf("red", "green")
    }

    @Test
    fun ordinal_x_then_domain_makes_a_copy_of_the_domain_LEGACY() {
        val scale = scaleOrdinal<String, String>()
        scale.range = listOf("toto")

        scale.domain = arrayListOf("red", "green")
        val domain = scale.domain
        scale("blue")
        scale.domain shouldBe listOf("red", "green", "blue")
        domain shouldBe listOf("red", "green")
    }

    @Test
    fun ordinal_domain_replaces_previous_values_LEGACY() {
        val scale = scaleOrdinal<Int, String>()
        scale.range = listOf("foo", "bar")

        scale(1) shouldBe "foo"
        scale(0) shouldBe "bar"
        scale.domain shouldBe listOf(1, 0)

        scale.domain = listOf(0, 1)
        scale(0) shouldBe "foo"
        scale(1) shouldBe "bar"
        scale.domain shouldBe listOf(0, 1)
    }

    @Test
    fun ordinal_domain_is_ordered_by_appearance_LEGACY() {
        val scale = scaleOrdinal<String, String>()
        scale.range = listOf("toto")

        scale("foo")
        scale("bar")
        scale("baz")
        scale.domain shouldBe listOf("foo", "bar", "baz")

        scale.domain = listOf("bar", "baz")
        scale("foo")
        scale.domain shouldBe listOf("bar", "baz", "foo")

        scale.domain = listOf("baz", "foo")
        scale.domain shouldBe listOf("baz", "foo")

        scale.domain = listOf()
        scale("foo")
        scale("bar")
        scale.domain shouldBe listOf("foo", "bar")
    }

    @Test
    fun ordinal_range_makes_a_copy_of_the_range_LEGACY() {
        val scale = scaleOrdinal<String, String>()

        val range = arrayListOf("red", "green")
        scale.range = range
        range.add("blue")
        scale.range shouldBe listOf("red", "green")
    }

    @Test
    fun ordinal_x_then_range_makes_a_copy_of_the_range_LEGACY() {
        val scale = scaleOrdinal<String, String>()

        scale.range = arrayListOf("red", "green")
        val range = scale.range
        scale.range = arrayListOf("red", "green", "blue")
        scale.range shouldBe listOf("red", "green", "blue")
        range shouldBe listOf("red", "green")
    }

    @Test
    fun ordinal_range_values_does_not_discard_implicit_domain_associations_LEGACY() {
        val scale = scaleOrdinal<Int, String>()

        scale.range = listOf("toto")
        scale(0) shouldBe ("toto")
        scale(1) shouldBe ("toto")

        scale.range = listOf("red", "green")
        scale(0) shouldBe ("red")
        scale(1) shouldBe ("green")
    }

    @Test
    fun ordinal_value_recylces_values_when_exhausted_LEGACY() {
        val scale = scaleOrdinal<Int, String>()
        scale.range = listOf("a", "b", "c")

        scale(0) shouldBe ("a")
        scale(1) shouldBe ("b")
        scale(2) shouldBe ("c")
        scale(3) shouldBe ("a")
        scale(4) shouldBe ("b")
        scale(5) shouldBe ("c")
        scale(2) shouldBe ("c")
        scale(1) shouldBe ("b")
        scale(0) shouldBe ("a")
    }

    @Test
    fun ordinal_unknown_x_sets_output_value_for_unknown_inputs_LEGACY() {
        val scale = scaleOrdinal<Int, String>()
        scale.domain = listOf(1, 2)
        scale.range = listOf("foo", "bar")
        scale.unknown = "gray"

        scale(1) shouldBe "foo"
        scale(2) shouldBe "bar"
        scale(3) shouldBe "gray"
        scale(-6) shouldBe "gray"
    }

    @Test
    fun ordinal_unknown_x_prevents_domain_extension_if_x_not_implicit_LEGACY() {
        val scale = scaleOrdinal<Int, String>()
        scale.domain = listOf(1, 2)
        scale.range = listOf("foo", "bar")
        scale.unknown = "undefined"

        scale(1) shouldBe "foo"
        scale(2) shouldBe "bar"
        scale(3) shouldBe "undefined"
        scale.domain shouldBe listOf(1, 2)
    }
}