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
        }

        "csv.parseRows parses quotes values" {
            csv.parseRows("\"1\",2,3") shouldBe listOf(listOf("1", "2", "3"))
            csv.parseRows("\"hello\"") shouldBe listOf(listOf("hello"))
            csv.parseRows("\"\"\"hello\"\"\"") shouldBe listOf(listOf("\"hello\""))
        }

        "csv.parseRows parses quoted values with newlines" {
            csv.parseRows("\"new\nline\"") shouldBe listOf(listOf("new\nline"))
            csv.parseRows("\"new\rline\"") shouldBe listOf(listOf("new\rline"))
            csv.parseRows("\"new\r\nline\"") shouldBe listOf(listOf("new\r\nline"))
        }


    }

}
