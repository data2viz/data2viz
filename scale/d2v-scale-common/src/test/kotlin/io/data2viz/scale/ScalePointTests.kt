package io.data2viz.scale

import io.data2viz.test.TestBase
import kotlin.test.Test

class ScalePointTests : TestBase() {

    @Test
    fun point_has_expected_defaults() {
        val scale = Scales.Discrete.point<Int>()

        scale.domain shouldBe listOf()
        scale.range shouldBe intervalOf(.0, 1.0)
        scale.bandwidth shouldBeClose .0
        scale.step shouldBeClose 1.0
        scale.round shouldBe false
        scale.padding shouldBeClose .0
        scale.align shouldBeClose .5
    }

    @Test
    fun point_no_paddingInner_paddingOuter() {
//        val scale = scalePoint<Int>()

//        scale.pa
    }

    @Test
    fun pointScale_is_similar_to_bandscale_paddinginner_1() {
        val pointScale = Scales.Discrete.point<String>()
        pointScale.range = intervalOf(.0, 960.0)
        pointScale.domain = listOf("foo", "bar")

        val bandScale = Scales.Discrete.band<String>()
        bandScale.range = intervalOf(.0, 960.0)
        bandScale.domain = listOf("foo", "bar")
        bandScale.paddingInner = 1.0

        bandScale.domain shouldBe pointScale.domain
        bandScale.range shouldBe pointScale.range
        bandScale.bandwidth shouldBeClose pointScale.bandwidth
        bandScale.step shouldBeClose pointScale.step
    }

    @Test
    fun point_padding_p_sets_band_outer_padding_to_p() {
        val pointScale = Scales.Discrete.point<String>()
        pointScale.range = intervalOf(.0, 960.0)
        pointScale.domain = listOf("foo", "bar")
        pointScale.padding = .5

        val bandScale = Scales.Discrete.band<String>()
        bandScale.range = intervalOf(.0, 960.0)
        bandScale.domain = listOf("foo", "bar")
        bandScale.paddingInner = 1.0
        bandScale.paddingOuter = .5

        bandScale.domain shouldBe pointScale.domain
        bandScale.range shouldBe pointScale.range
        bandScale.bandwidth shouldBeClose pointScale.bandwidth
        bandScale.step shouldBeClose pointScale.step
    }

    // TODO align tests for padding & round
    // TODO : add more scale tests
}