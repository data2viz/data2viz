package io.data2viz.dsv

import io.data2viz.test.TestBase
import kotlin.test.Test

class DsvTests : TestBase() {

    val csv = Dsv()

    @Test
    fun one_cell() {

        csv.parseRows("a") shouldBe listOf(listOf("a"))
        csv.parseRows("abc") shouldBe listOf(listOf("abc"))
        csv.parseRows("a\n") shouldBe listOf(listOf("a"))
    }

    @Test
    fun two_cells() {
        csv.parseRows("a,b") shouldBe listOf(listOf("a", "b"))
        csv.parseRows("a,b\n") shouldBe listOf(listOf("a", "b"))
        csv.parseRows("a,b\r\n") shouldBe listOf(listOf("a", "b"))
    }

    @Test
    fun two_rows() {
        csv.parseRows("a\nb") shouldBe listOf(listOf("a"), listOf("b"))
        csv.parseRows("a\r\nb") shouldBe listOf(listOf("a"), listOf("b"))
        csv.parseRows("a,b\n1,2") shouldBe listOf(listOf("a", "b"), listOf("1", "2"))
    }

    @Test
    fun parse_rows_with_quotes_values() {
        csv.parseRows("\"1\",2,3") shouldBe listOf(listOf("1", "2", "3"))
        csv.parseRows("\"hello\"") shouldBe listOf(listOf("hello"))
        csv.parseRows("\"\"\"hello\"\"\"") shouldBe listOf(listOf("\"hello\""))
    }

    @Test
    fun parse_rows_with_quoted_values_with_new_lines() {
        csv.parseRows("\"new\nline\"") shouldBe listOf(listOf("new\nline"))
        csv.parseRows("\"new\rline\"") shouldBe listOf(listOf("new\rline"))
        csv.parseRows("\"new\r\nline\"") shouldBe listOf(listOf("new\r\nline"))
    }

}
