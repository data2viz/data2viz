package io.data2viz.dsv

import io.data2viz.test.StringSpec


class DsvTests : StringSpec() {

    init {

        val csv = Dsv()

        "oneCell" {
            csv.parseRows("a") shouldBe listOf(listOf("a"))
            csv.parseRows("abc") shouldBe listOf(listOf("abc"))
            csv.parseRows("a\n") shouldBe listOf(listOf("a"))
        }
        "twoCells" {
            csv.parseRows("a,b") shouldBe listOf(listOf("a", "b"))
            csv.parseRows("a,b\n") shouldBe listOf(listOf("a", "b"))
            csv.parseRows("a,b\r\n") shouldBe listOf(listOf("a", "b"))
        }
        "twoRows" {
            csv.parseRows("a\nb") shouldBe listOf(listOf("a"), listOf("b"))
            csv.parseRows("a\r\nb") shouldBe listOf(listOf("a"), listOf("b"))
            csv.parseRows("a,b\n1,2") shouldBe listOf(listOf("a","b"), listOf("1", "2"))
//            dsv.parseRows("a,b,c\n")[0] shouldBe listOf("a", "b", "c")
        }
    }

}
