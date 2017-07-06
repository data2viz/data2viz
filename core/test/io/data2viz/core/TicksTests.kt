package io.data2viz.core

import io.data2viz.test.StringSpec

class TicksTests: StringSpec(){
    init {
        "io.data2viz.core.ticks(0.0, 1.0, 10) io.data2viz.core.ticks(0.0, 1.0, 9) io.data2viz.core.ticks(0.0, 1.0, 8) shouldBe listOf(0.0, 0.1, 0.2, 0.3, 0.4, 0.5, 0.6, 0.7, 0.8, 0.9, 1.0)" {
            ticks(0.0, 1.0, 10) shouldBe listOf(0.0, 0.1, 0.2, 0.3, 0.4, 0.5, 0.6, 0.7, 0.8, 0.9, 1.0)
            ticks(0.0, 1.0,  9) shouldBe listOf(0.0, 0.1, 0.2, 0.3, 0.4, 0.5, 0.6, 0.7, 0.8, 0.9, 1.0)
            ticks(0.0, 1.0,  8) shouldBe listOf(0.0, 0.1, 0.2, 0.3, 0.4, 0.5, 0.6, 0.7, 0.8, 0.9, 1.0)
        }

        "io.data2viz.core.ticks(0.0, 1.0, 7) io.data2viz.core.ticks(0.0, 1.0, 6) io.data2viz.core.ticks(0.0, 1.0, 5) shouldBe listOf(0.0, 0.2, 0.4, 0.6, 0.8, 1.0)" {
            ticks(0.0, 1.0, 7) shouldBe listOf(0.0, 0.2, 0.4, 0.6, 0.8, 1.0)
            ticks(0.0, 1.0, 6) shouldBe listOf(0.0, 0.2, 0.4, 0.6, 0.8, 1.0)
            ticks(0.0, 1.0, 5) shouldBe listOf(0.0, 0.2, 0.4, 0.6, 0.8, 1.0)
            ticks(0.0, 1.0, 4) shouldBe listOf(0.0, 0.2, 0.4, 0.6, 0.8, 1.0)
        }

        "io.data2viz.core.ticks(0.0, 1.0, 3) io.data2viz.core.ticks(0.0, 1.0, 2) shouldBe listOf(0.0, 0.5, 1.0)" {
            ticks(0.0, 1.0, 3) shouldBe listOf(0.0, 0.5, 1.0)
            ticks(0.0, 1.0, 2) shouldBe listOf(0.0, 0.5, 1.0)
        }

        "io.data2viz.core.ticks(0.0, 1.0, 1) io.data2viz.core.ticks(0.0, 1.0, 2) shouldBe listOf(0.0, 1.0)" {
            ticks(0.0, 1.0, 1) shouldBe listOf(0.0, 1.0)
        }

        "io.data2viz.core.ticks(0.0, 10.0, 10) io.data2viz.core.ticks(0.0, 10.0, 9) io.data2viz.core.ticks(0.0, 10.0, 8) shouldBe listOf(0.0, 1.0, 2.0, 3.0, 4.0, 5.0, 6.0, 7.0, 8.0, 9.0, 10.0)" {
            ticks(0.0, 10.0, 10) shouldBe listOf(0.0, 1.0, 2.0, 3.0, 4.0, 5.0, 6.0, 7.0, 8.0, 9.0, 10.0)
            ticks(0.0, 10.0, 9) shouldBe listOf(0.0, 1.0, 2.0, 3.0, 4.0, 5.0, 6.0, 7.0, 8.0, 9.0, 10.0)
            ticks(0.0, 10.0, 8) shouldBe listOf(0.0, 1.0, 2.0, 3.0, 4.0, 5.0, 6.0, 7.0, 8.0, 9.0, 10.0)
        }

        "io.data2viz.core.ticks(0.0, 10.0, 7) io.data2viz.core.ticks(0.0, 10.0, 6) io.data2viz.core.ticks(0.0, 10.0, 5) io.data2viz.core.ticks(0.0, 10.0, 4) shouldBe listOf(0.0, 2.0, 4.0, 6.0, 8.0, 10.0)" {
            ticks(0.0, 10.0, 7) shouldBe listOf(0.0, 2.0, 4.0, 6.0, 8.0, 10.0)
            ticks(0.0, 10.0, 6) shouldBe listOf(0.0, 2.0, 4.0, 6.0, 8.0, 10.0)
            ticks(0.0, 10.0, 5) shouldBe listOf(0.0, 2.0, 4.0, 6.0, 8.0, 10.0)
            ticks(0.0, 10.0, 4) shouldBe listOf(0.0, 2.0, 4.0, 6.0, 8.0, 10.0)
        }

        "io.data2viz.core.ticks(0.0, 10.0, 3) io.data2viz.core.ticks(0.0, 10.0, 2) io.data2viz.core.ticks(0.0, 10.0, 5) io.data2viz.core.ticks(0.0, 10.0, 4) shouldBe listOf(0.0, 5.0, 10.0)" {
            ticks(0.0, 10.0, 3) shouldBe listOf(0.0, 5.0, 10.0)
            ticks(0.0, 10.0, 2) shouldBe listOf(0.0, 5.0, 10.0)
        }

        "io.data2viz.core.ticks(0.0, 10.0, 1) shouldBe listOf(0.0, 10.0)" {
            ticks(0.0, 10.0, 1) shouldBe listOf(0.0, 10.0)
        }
        "io.data2viz.core.ticks(-10.0, 10.0, 10) io.data2viz.core.ticks(-10.0, 10.0, 9) io.data2viz.core.ticks(-10.0, 10.0, 8) io.data2viz.core.ticks(-10.0, 10.0, 7) shouldBe listOf(-10, -8.0, -6.0, -4.0, -2.0, 0.0, 2.0, 4.0, 6.0, 8.0, 10.0)" {
            ticks(-10.0, 10.0, 10) shouldBe listOf(-10, -8.0, -6.0, -4.0, -2.0, 0.0, 2.0, 4.0, 6.0, 8.0, 10.0)
            ticks(-10.0, 10.0, 9) shouldBe listOf(-10, -8.0, -6.0, -4.0, -2.0, 0.0, 2.0, 4.0, 6.0, 8.0, 10.0)
            ticks(-10.0, 10.0, 8) shouldBe listOf(-10, -8.0, -6.0, -4.0, -2.0, 0.0, 2.0, 4.0, 6.0, 8.0, 10.0)
            ticks(-10.0, 10.0, 7) shouldBe listOf(-10, -8.0, -6.0, -4.0, -2.0, 0.0, 2.0, 4.0, 6.0, 8.0, 10.0)
        }
        "io.data2viz.core.ticks(-10.0, 10.0, 6) io.data2viz.core.ticks(-10.0, 10.0, 5) io.data2viz.core.ticks(-10.0, 10.0, 4) io.data2viz.core.ticks(-10.0, 10.0, 3) shouldBe listOf(-10, -5.0, 0.0, 5.0, 10.0)" {
            ticks(-10.0, 10.0, 6) shouldBe listOf(-10, -5.0, 0.0, 5.0, 10.0)
            ticks(-10.0, 10.0, 5) shouldBe listOf(-10, -5.0, 0.0, 5.0, 10.0)
            ticks(-10.0, 10.0, 4) shouldBe listOf(-10, -5.0, 0.0, 5.0, 10.0)
            ticks(-10.0, 10.0, 3) shouldBe listOf(-10, -5.0, 0.0, 5.0, 10.0)
        }
        "io.data2viz.core.ticks(-10.0, 10.0, 2) shouldBe listOf(-10, 0.0, 10.0)" {
            ticks(-10.0, 10.0, 2) shouldBe listOf(-10, 0.0, 10.0)
        }
        "io.data2viz.core.ticks(-10.0, 10.0, 1) shouldBe listOf(-10, 0.0, 10.0)" {
            ticks(-10.0, 10.0, 1) shouldBe listOf(0.0)
        }
    }

}
