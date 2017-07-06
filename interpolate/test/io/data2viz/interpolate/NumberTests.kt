package io.data2viz.interpolate

import io.data2viz.test.StringSpec

class NumberTests : StringSpec() {
    init {
        "interpolate" {
            val f = interpolateNumber(10, 20)
            f(0.2) shouldBe 12.0
        }

        "uninterpolate" {
            val f = uninterpolate(10, 20)
            f(12) shouldBe 0.2
        }


        "scale linear (10..20) to (100..200) : 12 should be 120" {
            val domainToViz  = scale.linear.numberToNumber(
                    10 linkedTo 100,
                    20 linkedTo 200)

            domainToViz(12) shouldBe 120
        }

        "scale linear (0..100) to (0..1) : 50 should be 0.5" {
            val domainToViz  = scale.linear.numberToNumber(
                    0 linkedTo 0,
                    100 linkedTo 1)

            domainToViz(50) shouldBe 0.5
        }

        "scale linear (0..100) to (-100..100) : 50 should be 0" {
            val domainToViz  = scale.linear.numberToNumber(
                    0 linkedTo -100,
                    100 linkedTo 100)

            domainToViz(50) shouldBe 0
        }

        "scale linear (0..100) to (100..0) : 60 should be 40" {
            val domainToViz  = scale.linear.numberToNumber(
                    0 linkedTo 100,
                    100 linkedTo 0)

            domainToViz(60) shouldBe 40
        }

    }

}
