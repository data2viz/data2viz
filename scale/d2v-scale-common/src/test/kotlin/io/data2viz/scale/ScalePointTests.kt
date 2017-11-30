package io.data2viz.scale

import io.data2viz.test.TestBase
import io.data2viz.test.shouldThrow
import kotlin.test.Test

class ScalePointTests : TestBase() {

    val epsilon = 1e6

    @Test
    fun point_has_expected_defaults_LEGACY() {
        val scale = pointScale<Int>()

        scale.domain shouldBe listOf()
        scale.range shouldBe listOf(.0, 1.0)
        scale.bandwidth shouldBe .0
        scale.step shouldBe 1.0
        scale.round shouldBe false
        scale.padding shouldBe .0
        scale.align shouldBe .5
    }

    @Test
    fun point_no_paddingInner_paddingOuter_LEGACY() {
//        val scale = pointScale<Int>()

//        scale.pa
    }

    @Test
    fun pointScale_is_similar_to_bandscale_paddinginner_1_LEGACY() {
        val pointScale = pointScale<String>()
        pointScale.range = listOf(.0, 960.0)
        pointScale.domain = listOf("foo", "bar")

        val bandScale = bandScale<String>()
        bandScale.range = listOf(.0, 960.0)
        bandScale.domain = listOf("foo", "bar")
        bandScale.paddingInner = 1.0

        bandScale.domain shouldBe pointScale.domain
        bandScale.range shouldBe pointScale.range
        bandScale.bandwidth shouldBe pointScale.bandwidth
        bandScale.step shouldBe pointScale.step
    }

    @Test
    fun point_padding_p_sets_band_outer_padding_to_p_LEGACY() {
        val pointScale = pointScale<String>()
        pointScale.range = listOf(.0, 960.0)
        pointScale.domain = listOf("foo", "bar")
        pointScale.padding = .5

        val bandScale = bandScale<String>()
        bandScale.range = listOf(.0, 960.0)
        bandScale.domain = listOf("foo", "bar")
        bandScale.paddingInner = 1.0
        bandScale.paddingOuter = .5

        bandScale.domain shouldBe pointScale.domain
        bandScale.range shouldBe pointScale.range
        bandScale.bandwidth shouldBe pointScale.bandwidth
        bandScale.step shouldBe pointScale.step
    }

    // TODO align tests for padding & round
    // TODO : add more scale tests
}