package io.data2viz.format

import io.data2viz.test.TestBase
import kotlin.test.Test

class FormatTypeDecimalRoundedTests : TestBase() {

    @Test
    fun format_d_can_zerofill() {
        val f = formatter("08d")
        f(0.0) shouldBe "00000000"
        f(42.0) shouldBe "00000042"
        f(42000000.0) shouldBe "42000000"
        f(420000000.0) shouldBe "420000000"
        f(-4.0) shouldBe "-0000004"
        f(-42.0) shouldBe "-0000042"
        f(-4200000.0) shouldBe "-4200000"
        f(-42000000.0) shouldBe "-42000000"
    }


    @Test
    fun format_d_can_space_fill() {
        val f = formatter("8d")
        f(0.0) shouldBe             "       0"
        f(42.0) shouldBe            "      42"
        f(42000000.0) shouldBe      "42000000"
        f(420000000.0) shouldBe    "420000000"
        f(-4.0) shouldBe            "      -4"
        f(-42.0) shouldBe           "     -42"
        f(-4200000.0) shouldBe      "-4200000"
        f(-42000000.0) shouldBe    "-42000000"
    }

    @Test
    fun format_d_can_underscore_fill() {
        val f = formatter("_>8d")
        f(0.0) shouldBe "_______0"
        f(42.0) shouldBe "______42"
        f(42000000.0) shouldBe "42000000"
        f(420000000.0) shouldBe "420000000"
        f(-4.0) shouldBe "______-4"
        f(-42.0) shouldBe "_____-42"
        f(-4200000.0) shouldBe "-4200000"
        f(-42000000.0) shouldBe "-42000000"
    }

    @Test
    fun format_d_can_zero_fill_with_sign_and_group() {
        val f = formatter("+08,d")
        f(0.0) shouldBe "+0,000,000"
        f(42.0) shouldBe "+0,000,042"
        f(42000000.0) shouldBe "+42,000,000"
        f(420000000.0) shouldBe "+420,000,000"
        f(-4.0) shouldBe "-0,000,004"
        f(-42.0) shouldBe "-0,000,042"
        f(-4200000.0) shouldBe "-4,200,000"
        f(-42000000.0) shouldBe "-42,000,000"
    }

    @Test
    fun format_d_always_uses_zero_exponent() {
        val f = formatter(".2d")
        f(0.0) shouldBe "0"
        f(42.0) shouldBe "42"
        f(-4.2) shouldBe "-4"
    }

    @Test
    fun format_d_rounds_non_integers() {
        val f = formatter("d")
        f(4.2) shouldBe "4"
    }

    @Test
    fun format_d_can_group_thousands() {
        val f = formatter(",d")
        f(0.0) shouldBe "0"
        f(42.0) shouldBe "42"
        f(42000000.0) shouldBe "42,000,000"
        f(420000000.0) shouldBe "420,000,000"
        f(-4.0) shouldBe "-4"
        f(-42.0) shouldBe "-42"
        f(-4200000.0) shouldBe "-4,200,000"
        f(-42000000.0) shouldBe "-42,000,000"
        f(1e21) shouldBe "1e+21"
    }

    @Test
    fun format_0_d_can_group_thousands_and_zero_fill () {
        formatter("01,d")   (0.0)           shouldBe "0"
        formatter("01,d")   (0.0)           shouldBe "0"
        formatter("02,d")   (0.0)           shouldBe "00"
        formatter("03,d")   (0.0)           shouldBe "000"
        formatter("04,d")   (0.0)           shouldBe "0,000"
        formatter("05,d")   (0.0)           shouldBe "0,000"
        formatter("06,d")   (0.0)           shouldBe "00,000"
        formatter("08,d")   (0.0)           shouldBe "0,000,000"
        formatter("013,d")  (0.0)           shouldBe "0,000,000,000"
        formatter("021,d")  (0.0)           shouldBe "0,000,000,000,000,000"
        formatter("013,d")  (-42000000.0)   shouldBe "-0,042,000,000"
        formatter("012,d")  (1e21)          shouldBe "0,000,001e+21"
        formatter("013,d")  (1e21)          shouldBe "0,000,001e+21"
        formatter("014,d")  (1e21)          shouldBe "00,000,001e+21"
        formatter("015,d")  (1e21)          shouldBe "000,000,001e+21"
    }

    @Test
    fun format_0_d_can_group_thousands_and_zero_fill_with_overflow () {
        formatter("01,d")   (1.0)               shouldBe "1"
        formatter("01,d")   (1.0)               shouldBe "1"
        formatter("02,d")   (12.0)              shouldBe "12"
        formatter("03,d")   (123.0)             shouldBe "123"
        formatter("05,d")   (12345.0)           shouldBe "12,345"
        formatter("08,d")   (12345678.0)        shouldBe "12,345,678"
        formatter("013,d")  (1234567890123.0)   shouldBe "1,234,567,890,123"
    }

    @Test
    fun format_d_can_group_thousands_and_space_fill () {
        formatter("1,d")    (0.0) shouldBe "0"
        formatter("1,d")    (0.0) shouldBe "0"
        formatter("2,d")    (0.0) shouldBe " 0"
        formatter("3,d")    (0.0) shouldBe "  0"
        formatter("5,d")    (0.0) shouldBe "    0"
        formatter("8,d")    (0.0) shouldBe "       0"
        formatter("13,d")   (0.0) shouldBe "            0"
        formatter("21,d")   (0.0) shouldBe "                    0"
    }

    @Test
    fun format_d_can_group_thousands_and_space_fill_with_overflow () {
        formatter("1,d")    (1.0)               shouldBe "1"
        formatter("1,d")    (1.0)               shouldBe "1"
        formatter("2,d")    (12.0)              shouldBe "12"
        formatter("3,d")    (123.0)             shouldBe "123"
        formatter("5,d")    (12345.0)           shouldBe "12,345"
        formatter("8,d")    (12345678.0)        shouldBe "12,345,678"
        formatter("13,d")   (1234567890123.0)   shouldBe "1,234,567,890,123"
    }

    @Test
    fun format_d_align_left () {
        formatter("<1,d")   (0.0) shouldBe "0"
        formatter("<1,d")   (0.0) shouldBe "0"
        formatter("<2,d")   (0.0) shouldBe "0 "
        formatter("<3,d")   (0.0) shouldBe "0  "
        formatter("<5,d")   (0.0) shouldBe "0    "
        formatter("<8,d")   (0.0) shouldBe "0       "
        formatter("<13,d")  (0.0) shouldBe "0            "
        formatter("<21,d")  (0.0) shouldBe "0                    "
    }

    @Test
    fun format_d_align_right () {
        formatter(">1,d")   (0.0)       shouldBe "0"
        formatter(">1,d")   (0.0)       shouldBe "0"
        formatter(">2,d")   (0.0)       shouldBe " 0"
        formatter(">3,d")   (0.0)       shouldBe "  0"
        formatter(">5,d")   (0.0)       shouldBe "    0"
        formatter(">8,d")   (0.0)       shouldBe "       0"
        formatter(">13,d")  (0.0)       shouldBe "            0"
        formatter(">21,d")  (0.0)       shouldBe "                    0"
        formatter(">21,d")  (1000.0)    shouldBe "                1,000"
        formatter(">21,d")  (1e21)      shouldBe "                1e+21"
    }

    @Test
    fun format_d_align_center () {
        formatter("^1,d")   (0.0)       shouldBe "0"
        formatter("^1,d")   (0.0)       shouldBe "0"
        formatter("^2,d")   (0.0)       shouldBe "0 "
        formatter("^3,d")   (0.0)       shouldBe " 0 "
        formatter("^5,d")   (0.0)       shouldBe "  0  "
        formatter("^8,d")   (0.0)       shouldBe "   0    "
        formatter("^13,d")  (0.0)       shouldBe "      0      "
        formatter("^21,d")  (0.0)       shouldBe "          0          "
        formatter("^21,d")  (1000.0)    shouldBe "        1,000        "
        formatter("^21,d")  (1e21)      shouldBe "        1e+21        "
    }

    @Test
    fun format_d_pad_after_sign () {
        formatter("=+1,d")  (0.0)   shouldBe "+0"
        formatter("=+1,d")  (0.0)   shouldBe "+0"
        formatter("=+2,d")  (0.0)   shouldBe "+0"
        formatter("=+3,d")  (0.0)   shouldBe "+ 0"
        formatter("=+5,d")  (0.0)   shouldBe "+   0"
        formatter("=+8,d")  (0.0)   shouldBe "+      0"
        formatter("=+13,d") (0.0)   shouldBe "+           0"
        formatter("=+21,d") (0.0)   shouldBe "+                   0"
        formatter("=+21,d") (1e21)  shouldBe "+               1e+21"
    }

    @Test
    fun format_d_pad_after_sign_with_currency () {
        formatter("=+$1,d")     (0.0)   shouldBe "+$0"
        formatter("=+$1,d")     (0.0)   shouldBe "+$0"
        formatter("=+$2,d")     (0.0)   shouldBe "+$0"
        formatter("=+$3,d")     (0.0)   shouldBe "+$0"
        formatter("=+$5,d")     (0.0)   shouldBe "+$  0"
        formatter("=+$8,d")     (0.0)   shouldBe "+$     0"
        formatter("=+$13,d")    (0.0)   shouldBe "+$          0"
        formatter("=+$21,d")    (0.0)   shouldBe "+$                  0"
        formatter("=+$21,d")    (1e21)  shouldBe "+$              1e+21"
    }

    @Test
    fun format_d_a_space_can_denote_positive_numbers () {
        formatter(" 1,d")   (-1.0)  shouldBe "-1"
        formatter(" 1,d")   (0.0)   shouldBe " 0"
        formatter(" 2,d")   (0.0)   shouldBe " 0"
        formatter(" 3,d")   (0.0)   shouldBe "  0"
        formatter(" 5,d")   (0.0)   shouldBe "    0"
        formatter(" 8,d")   (0.0)   shouldBe "       0"
        formatter(" 13,d")  (0.0)   shouldBe "            0"
        formatter(" 21,d")  (0.0)   shouldBe "                    0"
        formatter(" 21,d")  (1e21)  shouldBe "                1e+21"
    }

    @Test
    fun format_d_explicitly_only_use_a_sign_for_negative_numbers () {
        formatter("-1,d")   (-1.0)  shouldBe "-1"
        formatter("-1,d")   (0.0)   shouldBe "0"
        formatter("-2,d")   (0.0)   shouldBe " 0"
        formatter("-3,d")   (0.0)   shouldBe "  0"
        formatter("-5,d")   (0.0)   shouldBe "    0"
        formatter("-8,d")   (0.0)   shouldBe "       0"
        formatter("-13,d")  (0.0)   shouldBe "            0"
        formatter("-21,d")  (0.0)   shouldBe "                    0"
    }

    @Test
    fun format_d_can_format_negative_zero_as_zero () {
        formatter("1d")(-0.0) shouldBe "0"
        formatter("1d")(-1e-12) shouldBe "0"
    }
    
}
  