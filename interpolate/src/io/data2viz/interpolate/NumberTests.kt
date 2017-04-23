package io.data2viz.interpolate

import test.StringSpec

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



    }

}
