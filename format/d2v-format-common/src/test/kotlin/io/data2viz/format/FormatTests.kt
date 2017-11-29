@file:Suppress("FunctionName", "unused")

package io.data2viz.format

import io.data2viz.test.TestBase
import kotlin.test.Test

class FormatTests : TestBase() {

    @Test
    fun format_f_can_output_fixed_point_notation() {
        formatter(".1f")(0.49) shouldBe "0.5"
        formatter(".2f")(0.449) shouldBe "0.45"
        formatter(".3f")(0.4449) shouldBe "0.445"
        formatter(".5f")(0.444449) shouldBe "0.44445"
        formatter(".1f")(100.0) shouldBe "100.0"
        formatter(".2f")(100.0) shouldBe "100.00"
        formatter(".3f")(100.0) shouldBe "100.000"
        formatter(".5f")(100.0) shouldBe "100.00000"
    }


    @Test
    fun format_currency_sign_with_comma_grouping_and_sign() {
        val f = formatter("+$,.2f")
        f(0.0) shouldBe "+$0.00"
        f(0.429) shouldBe "+$0.43"
        f(-0.429) shouldBe "-$0.43"
        f(-1.0) shouldBe "-$1.00"
        f(1e4) shouldBe "+$10,000.00"
    }

    @Test
    fun format_f_can_grouping_thousands_space_fill_and_round_to_significant_digits () {

        formatter("10,.1f")(123456.49) shouldBe " 123,456.5"
        formatter("10,.2f")(1234567.449) shouldBe "1,234,567.45"
        formatter("10,.3f")(12345678.4449) shouldBe "12,345,678.445"
        formatter("10,.5f")(123456789.444449) shouldBe "123,456,789.44445"
        formatter("10,.1f")(123456.0) shouldBe " 123,456.0"
        formatter("10,.2f")(1234567.0) shouldBe "1,234,567.00"
        formatter("10,.3f")(12345678.0) shouldBe "12,345,678.000"
        formatter("10,.5f")(123456789.0) shouldBe "123,456,789.00000"
    }

    @Test
    fun  format_f_can_display_integers_in_fixed_point_notation (){
        formatter("f")(42.0) shouldBe "42.000000"
    }

    @Test
    fun format_f_can_format_negative_zero_as_zero() {
        formatter("f")(-0.0) shouldBe "0.000000"
        formatter("f")(-1e-12) shouldBe "0.000000"
    }

//        "format_f_can format negative infinity" {
//            format("f")(-Infinity) shouldBe "-Infinity"
//        }

//        "format_,f_does not grouping Infinity" {
//            format(",f")(Infinity) shouldBe "Infinity"
//        }

    /**
     * TYPE %
     */
    @Test
    fun format_percent_can_output_a_whole_percentage() {
        val f = formatter(".0%")
        f(0.0) shouldBe "0%"
        f(.042) shouldBe "4%"
        f(.42) shouldBe "42%"
        f(4.2) shouldBe "420%"
        f(-.042) shouldBe "-4%"
        f(-.42) shouldBe "-42%"
        f(-4.2) shouldBe "-420%"
    }

    @Test
    fun format_percent_can_output_a_percentage_with_exponent() {
        var f = formatter(".1%")
        f(.234) shouldBe "23.4%"
        f = formatter(".2%")
        f(.234) shouldBe "23.40%"
    }

    @Test
    fun format_percent_fill_respects_suffix () {
        formatter("020.0%")(42.0) shouldBe "0000000000000004200%"
        formatter("20.0%")(42.0) shouldBe "               4200%"
    }

    //      -42,200%       did not equal       -42,200%
    //         42%           did not equal          42%

    @Test
    fun format_percent_align_center_puts_suffix_adjacent_to_number() {
        formatter("^21.0%")(.42) shouldBe "         42%         "
        formatter("^21,.0%")(422.0) shouldBe "       42,200%       "
        formatter("^21,.0%")(-422.0) shouldBe "      -42,200%       "
//        Locale()
    }


    /**
     * TYPE B
     */

    @Test
    fun format_b_binary() {
        formatter("b")(10.0) shouldBe "1010"
    }

    @Test
    fun format_binary_with_prefix() {
        formatter("#b")(10.0) shouldBe "0b1010"
    }

    /**
     * TYPE C
     */

    /*"format_c_unicode character" {
        format("c")("☃") shouldBe "☃"
        format("020c")("☃") shouldBe "0000000000000000000☃"
        format(" ^20c")("☃") shouldBe "         ☃          "
        format("$c")("☃") shouldBe "$☃"

    }

    "format_c_does not localize a coefficient point" {
        formatLocale({coefficient: "/"}).format("c")(".") shouldBe "."

    }*/

    /**
     * TYPE D
     */

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
        f(0.0) shouldBe "       0"
        f(42.0) shouldBe "      42"
        f(42000000.0) shouldBe "42000000"
        f(420000000.0) shouldBe "420000000"
        f(-4.0) shouldBe "      -4"
        f(-42.0) shouldBe "     -42"
        f(-4200000.0) shouldBe "-4200000"
        f(-42000000.0) shouldBe "-42000000"
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

    @Test fun format_d_always_uses_zero_exponent() {
        val f = formatter(".2d")
        f(0.0) shouldBe "0"
        f(42.0) shouldBe "42"
        f(-4.2) shouldBe "-4"
    }

    @Test fun format_d_rounds_non_integers() {
        val f = formatter("d")
        f(4.2) shouldBe "4"
    }

    @Test fun format_d_can_group_thousands() {
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

    @Test fun format_0_d_can_group_thousands_and_zero_fill () {
        formatter("01,d")(0.0) shouldBe "0"
        formatter("01,d")(0.0) shouldBe "0"
        formatter("02,d")(0.0) shouldBe "00"
        formatter("03,d")(0.0) shouldBe "000"
        formatter("04,d")(0.0) shouldBe "0,000"
        formatter("05,d")(0.0) shouldBe "0,000"
        formatter("06,d")(0.0) shouldBe "00,000"
        formatter("08,d")(0.0) shouldBe "0,000,000"
        formatter("013,d")(0.0) shouldBe "0,000,000,000"
        formatter("021,d")(0.0) shouldBe "0,000,000,000,000,000"
        formatter("013,d")(-42000000.0) shouldBe "-0,042,000,000"
        formatter("012,d")(1e21) shouldBe "0,000,001e+21"
        formatter("013,d")(1e21) shouldBe "0,000,001e+21"
        formatter("014,d")(1e21) shouldBe "00,000,001e+21"
        formatter("015,d")(1e21) shouldBe "000,000,001e+21"
    }

    @Test fun format_0_d_can_group_thousands_and_zero_fill_with_overflow () {
        formatter("01,d")(1.0) shouldBe "1"
        formatter("01,d")(1.0) shouldBe "1"
        formatter("02,d")(12.0) shouldBe "12"
        formatter("03,d")(123.0) shouldBe "123"
        formatter("05,d")(12345.0) shouldBe "12,345"
        formatter("08,d")(12345678.0) shouldBe "12,345,678"
        formatter("013,d")(1234567890123.0) shouldBe "1,234,567,890,123"
    }

    @Test fun format_d_can_group_thousands_and_space_fill () {
        formatter("1,d")(0.0) shouldBe "0"
        formatter("1,d")(0.0) shouldBe "0"
        formatter("2,d")(0.0) shouldBe " 0"
        formatter("3,d")(0.0) shouldBe "  0"
        formatter("5,d")(0.0) shouldBe "    0"
        formatter("8,d")(0.0) shouldBe "       0"
        formatter("13,d")(0.0) shouldBe "            0"
        formatter("21,d")(0.0) shouldBe "                    0"
    }

    @Test fun format_d_can_group_thousands_and_space_fill_with_overflow () {
        formatter("1,d")(1.0) shouldBe "1"
        formatter("1,d")(1.0) shouldBe "1"
        formatter("2,d")(12.0) shouldBe "12"
        formatter("3,d")(123.0) shouldBe "123"
        formatter("5,d")(12345.0) shouldBe "12,345"
        formatter("8,d")(12345678.0) shouldBe "12,345,678"
        formatter("13,d")(1234567890123.0) shouldBe "1,234,567,890,123"
    }

    @Test fun format_d_align_left () {
        formatter("<1,d")(0.0) shouldBe "0"
        formatter("<1,d")(0.0) shouldBe "0"
        formatter("<2,d")(0.0) shouldBe "0 "
        formatter("<3,d")(0.0) shouldBe "0  "
        formatter("<5,d")(0.0) shouldBe "0    "
        formatter("<8,d")(0.0) shouldBe "0       "
        formatter("<13,d")(0.0) shouldBe "0            "
        formatter("<21,d")(0.0) shouldBe "0                    "
    }

    @Test fun format_d_align_right () {
        formatter(">1,d")(0.0) shouldBe "0"
        formatter(">1,d")(0.0) shouldBe "0"
        formatter(">2,d")(0.0) shouldBe " 0"
        formatter(">3,d")(0.0) shouldBe "  0"
        formatter(">5,d")(0.0) shouldBe "    0"
        formatter(">8,d")(0.0) shouldBe "       0"
        formatter(">13,d")(0.0) shouldBe "            0"
        formatter(">21,d")(0.0) shouldBe "                    0"
        formatter(">21,d")(1000.0) shouldBe "                1,000"
        formatter(">21,d")(1e21) shouldBe "                1e+21"
    }

    @Test fun format_d_align_center () {
        formatter("^1,d")(0.0) shouldBe "0"
        formatter("^1,d")(0.0) shouldBe "0"
        formatter("^2,d")(0.0) shouldBe "0 "
        formatter("^3,d")(0.0) shouldBe " 0 "
        formatter("^5,d")(0.0) shouldBe "  0  "
        formatter("^8,d")(0.0) shouldBe "   0    "
        formatter("^13,d")(0.0) shouldBe "      0      "
        formatter("^21,d")(0.0) shouldBe "          0          "
        formatter("^21,d")(1000.0) shouldBe "        1,000        "
        formatter("^21,d")(1e21) shouldBe "        1e+21        "
    }

    @Test fun format_d_pad_after_sign () {
        formatter("=+1,d")(0.0) shouldBe "+0"
        formatter("=+1,d")(0.0) shouldBe "+0"
        formatter("=+2,d")(0.0) shouldBe "+0"
        formatter("=+3,d")(0.0) shouldBe "+ 0"
        formatter("=+5,d")(0.0) shouldBe "+   0"
        formatter("=+8,d")(0.0) shouldBe "+      0"
        formatter("=+13,d")(0.0) shouldBe "+           0"
        formatter("=+21,d")(0.0) shouldBe "+                   0"
        formatter("=+21,d")(1e21) shouldBe "+               1e+21"
    }

    @Test fun format_d_pad_after_sign_with_currency () {
        formatter("=+$1,d")(0.0) shouldBe "+$0"
        formatter("=+$1,d")(0.0) shouldBe "+$0"
        formatter("=+$2,d")(0.0) shouldBe "+$0"
        formatter("=+$3,d")(0.0) shouldBe "+$0"
        formatter("=+$5,d")(0.0) shouldBe "+$  0"
        formatter("=+$8,d")(0.0) shouldBe "+$     0"
        formatter("=+$13,d")(0.0) shouldBe "+$          0"
        formatter("=+$21,d")(0.0) shouldBe "+$                  0"
        formatter("=+$21,d")(1e21) shouldBe "+$              1e+21"
    }

    @Test fun format_d_a_space_can_denote_positive_numbers () {
        formatter(" 1,d")(-1.0) shouldBe "-1"
        formatter(" 1,d")(0.0) shouldBe " 0"
        formatter(" 2,d")(0.0) shouldBe " 0"
        formatter(" 3,d")(0.0) shouldBe "  0"
        formatter(" 5,d")(0.0) shouldBe "    0"
        formatter(" 8,d")(0.0) shouldBe "       0"
        formatter(" 13,d")(0.0) shouldBe "            0"
        formatter(" 21,d")(0.0) shouldBe "                    0"
        formatter(" 21,d")(1e21) shouldBe "                1e+21"
    }

    @Test fun format_d_explicitly_only_use_a_sign_for_negative_numbers () {
        formatter("-1,d")(-1.0) shouldBe "-1"
        formatter("-1,d")(0.0) shouldBe "0"
        formatter("-2,d")(0.0) shouldBe " 0"
        formatter("-3,d")(0.0) shouldBe "  0"
        formatter("-5,d")(0.0) shouldBe "    0"
        formatter("-8,d")(0.0) shouldBe "       0"
        formatter("-13,d")(0.0) shouldBe "            0"
        formatter("-21,d")(0.0) shouldBe "                    0"
    }

    @Test fun format_d_can_format_negative_zero_as_zero () {
        formatter("1d")(-0.0) shouldBe "0"
        formatter("1d")(-1e-12) shouldBe "0"
    }

    /**
     * TYPE E
     */

    @Test fun format_e_can_output_exponent_notation () {
        val f = formatter("e")
        f(0.0) shouldBe "0.000000e+0"
        f(42.0) shouldBe "4.200000e+1"
        f(42000000.0) shouldBe "4.200000e+7"
        f(420000000.0) shouldBe "4.200000e+8"
        f(-4.0) shouldBe "-4.000000e+0"
        f(-42.0) shouldBe "-4.200000e+1"
        f(-4200000.0) shouldBe "-4.200000e+6"
        f(-42000000.0) shouldBe "-4.200000e+7"
        formatter(".0e")(42.0) shouldBe "4e+1"
        formatter(".3e")(42.0) shouldBe "4.200e+1"
    }

    @Test fun format_e_can_format_negative_zero_as_zero () {
        formatter("1e")(-0.0) shouldBe "0.000000e+0"
        formatter("1e")(-1e-12) shouldBe "-1.000000e-12"
    }

    /*"format_,e_does not group Infinity" {
        format(",e")(Infinity) shouldBe "Infinity"
    }*/

    /**
     * TYPE G
     */

    @Test fun format_g_can_output_general_notation () {
        formatter(".1g")(0.049) shouldBe "0.05"
        formatter(".1g")(0.49) shouldBe "0.5"
        formatter(".2g")(0.449) shouldBe "0.45"
        formatter(".3g")(0.4449) shouldBe "0.445"
        formatter(".5g")(0.444449) shouldBe "0.44445"
        formatter(".1g")(100.0) shouldBe "1e+2"
        formatter(".2g")(100.0) shouldBe "1.0e+2"
        formatter(".3g")(100.0) shouldBe "100"
        formatter(".5g")(100.0) shouldBe "100.00"
        formatter(".5g")(100.2) shouldBe "100.20"
        formatter(".2g")(0.002) shouldBe "0.0020"
    }

    @Test fun format_g_can_group_thousands_with_general_notation () {
        val f = formatter(",.12g")
        f(0.0) shouldBe "0.00000000000"
        f(42.0) shouldBe "42.0000000000"
        f(42000000.0) shouldBe "42,000,000.0000"
        f(420000000.0) shouldBe "420,000,000.000"
        f(-4.0) shouldBe "-4.00000000000"
        f(-42.0) shouldBe "-42.0000000000"
        f(-4200000.0) shouldBe "-4,200,000.00000"
        f(-42000000.0) shouldBe "-42,000,000.0000"
    }

    /**
     * TYPE N
     */

    @Test fun format_n_is_an_alias_for_g() {
        val f = formatter(".12n")
        f(0.0) shouldBe "0.00000000000"
        f(42.0) shouldBe "42.0000000000"
        f(42000000.0) shouldBe "42,000,000.0000"
        f(420000000.0) shouldBe "420,000,000.000"
        f(-4.0) shouldBe "-4.00000000000"
        f(-42.0) shouldBe "-42.0000000000"
        f(-4200000.0) shouldBe "-4,200,000.00000"
        f(-42000000.0) shouldBe "-42,000,000.0000"
        f(.0042) shouldBe "0.00420000000000"
        f(.42) shouldBe "0.420000000000"
        f(1e21) shouldBe "1.00000000000e+21"
    }

    @Test fun format_n_uses_zero_padding () {
        formatter("01.0n")(0.0) shouldBe "0"
        formatter("02.0n")(0.0) shouldBe "00"
        formatter("03.0n")(0.0) shouldBe "000"
        formatter("05.0n")(0.0) shouldBe "0,000"
        formatter("08.0n")(0.0) shouldBe "0,000,000"
        formatter("013.0n")(0.0) shouldBe "0,000,000,000"
        formatter("021.0n")(0.0) shouldBe "0,000,000,000,000,000"
        formatter("013.8n")(-42000000.0) shouldBe "-0,042,000,000"
    }

    /**
     * TYPE NONE
     */

    @Test fun format_uses_significant_exponent_and_trims_insignificant_zeros () {
        formatter(".1")(4.9) shouldBe "5"
        formatter(".1")(0.49) shouldBe "0.5"
        formatter(".2")(4.8) shouldBe "4.8"
        formatter(".2")(0.49) shouldBe "0.49"
        formatter(".2")(0.449) shouldBe "0.45"
        formatter(".3")(4.9) shouldBe "4.9"
        formatter(".3")(0.49) shouldBe "0.49"
        formatter(".3")(0.449) shouldBe "0.449"
        formatter(".3")(0.4449) shouldBe "0.445"
        formatter(".5")(0.444449) shouldBe "0.44445"
    }

    @Test fun format_does_not_trim_significant_zeros () {
        formatter(".5")(10.0) shouldBe "10"
        formatter(".5")(100.0) shouldBe "100"
        formatter(".5")(1000.0) shouldBe "1000"
        formatter(".5")(21010.0) shouldBe "21010"
        formatter(".5")(1.10001) shouldBe "1.1"
        formatter(".5")(1.10001e6) shouldBe "1.1e+6"
        formatter(".6")(1.10001) shouldBe "1.10001"
        formatter(".6")(1.10001e6) shouldBe "1.10001e+6"
    }

    @Test fun format_exponent_also_trims_the_coefficient_point_if_there_are_only_insignificant_zeros () {
        formatter(".5")(1.00001) shouldBe "1"
        formatter(".5")(1.00001e6) shouldBe "1e+6"
        formatter(".6")(1.00001) shouldBe "1.00001"
        formatter(".6")(1.00001e6) shouldBe "1.00001e+6"
    }

    @Test fun format_dollar_can_output_a_currency () {
        val f = formatter("$")
        f(0.0) shouldBe "$0"
        f(.042) shouldBe "$0.042"
        f(.42) shouldBe "$0.42"
        f(4.2) shouldBe "$4.2"
        f(-.042) shouldBe "-$0.042"
        f(-.42) shouldBe "-$0.42"
        f(-4.2) shouldBe "-$4.2"
    }

    @Test fun format_dollar_can_output_a_currency_with_parentheses_for_negative_values () {
        val f = formatter("($")
        f(0.0) shouldBe "$0"
        f(.042) shouldBe "$0.042"
        f(.42) shouldBe "$0.42"
        f(4.2) shouldBe "$4.2"
        f(-.042) shouldBe "($0.042)"
        f(-.42) shouldBe "($0.42)"
        f(-4.2) shouldBe "($4.2)"
    }

    @Test fun format__can_format_negative_zero_as_zero () {
        formatter("")(-0.0) shouldBe "0"
    }

    /*"format__can format negative infinity" {
        format("")(-Infinity) shouldBe "-Infinity"

    }*/

    /**
     * TYPE O
     */

    @Test fun format_o_octal () {
        formatter("o")(10.0) shouldBe "12"
    }

    @Test fun format_o_octal_with_prefix () {
        formatter("#o")(10.0) shouldBe "0o12"
    }

    /**
     * TYPE P
     */

    @Test fun format_p_can_output_a_percentage () {
        val f = formatter("p")
        f(.00123) shouldBe "0.123000%"
        f(.0123) shouldBe "1.23000%"
        f(.123) shouldBe "12.3000%"
        f(.234) shouldBe "23.4000%"
        f(1.23) shouldBe "123.000%"
        f(-.00123) shouldBe "-0.123000%"
        f(-.0123) shouldBe "-1.23000%"
        f(-.123) shouldBe "-12.3000%"
        f(-1.23) shouldBe "-123.000%"
    }

    @Test fun format_p_can_output_a_percentage_with_rounding_and_sign () {
        val f = formatter("+.2p")
        f(.00123) shouldBe "+0.12%"
        f(.0123) shouldBe "+1.2%"
        f(.123) shouldBe "+12%"
        f(1.23) shouldBe "+120%"
        f(-.00123) shouldBe "-0.12%"
        f(-.0123) shouldBe "-1.2%"
        f(-.123) shouldBe "-12%"
        f(-1.23) shouldBe "-120%"
    }

    /**
     * TYPE R
     */

    @Test fun format_r_can_round_to_significant_digits () {
        formatter(".2r")(0.0) shouldBe "0.0"
        formatter(".1r")(0.049) shouldBe "0.05"
        formatter(".1r")(-0.049) shouldBe "-0.05"
        formatter(".1r")(0.49) shouldBe "0.5"
        formatter(".1r")(-0.49) shouldBe "-0.5"
        formatter(".2r")(0.449) shouldBe "0.45"
        formatter(".3r")(0.4449) shouldBe "0.445"
        formatter(".3r")(2.00) shouldBe "2.00"
        formatter(".3r")(3.9995) shouldBe "4.00"
        formatter(".5r")(0.444449) shouldBe "0.44445"
        formatter("r")(123.45) shouldBe "123.450"
        formatter(".1r")(123.45) shouldBe "100"
        formatter(".2r")(123.45) shouldBe "120"
        formatter(".3r")(123.45) shouldBe "123"
        formatter(".4r")(123.45) shouldBe "123.5"
        formatter(".5r")(123.45) shouldBe "123.45"
        formatter(".6r")(123.45) shouldBe "123.450"
        formatter(".1r")(.9) shouldBe "0.9"
        formatter(".1r")(.09) shouldBe "0.09"
        formatter(".1r")(.949) shouldBe "0.9"
        formatter(".1r")(.0949) shouldBe "0.09"
        formatter(".1r")(.0000000129) shouldBe "0.00000001"
        formatter(".2r")(.0000000129) shouldBe "0.000000013"
        formatter(".2r")(.00000000129) shouldBe "0.0000000013"
        formatter(".3r")(.00000000129) shouldBe "0.00000000129"
        formatter(".4r")(.00000000129) shouldBe "0.000000001290"
        formatter(".10r")(.9999999999) shouldBe "0.9999999999"
        formatter(".15r")(.999999999999999) shouldBe "0.999999999999999"
    }

    @Test fun format_r_can_round_very_small_numbers () {
        val f = formatter(".2r")
        f(1e-22) shouldBe "0.00000000000000000000010"
    }

    /**
     * TYPE S
     */

    @Test fun format_s_outputs_SI_prefix_notation_with_default_exponent_6 () {
        val f = formatter("s")
        f(0.0) shouldBe "0.00000"
        f(1.0) shouldBe "1.00000"
        f(10.0) shouldBe "10.0000"
        f(100.0) shouldBe "100.000"
        f(999.5) shouldBe "999.500"
        f(999500.0) shouldBe "999.500k"
        f(1000.0) shouldBe "1.00000k"
        f(100.0) shouldBe "100.000"
        f(1400.0) shouldBe "1.40000k"
        f(1500.5) shouldBe "1.50050k"
        f(.00001) shouldBe "10.0000µ"
        f(.000001) shouldBe "1.00000µ"
    }

    @Test fun format_exponent_s_outputs_SI_prefix_notation_with_exponent_significant_digits () {
        var f = formatter(".3s")
        f(0.0) shouldBe "0.00"
        f(1.0) shouldBe "1.00"
        f(10.0) shouldBe "10.0"
        f(100.0) shouldBe "100"
        f(999.5) shouldBe "1.00k"
        f(999500.0) shouldBe "1.00M"
        f(1000.0) shouldBe "1.00k"
        f(1500.5) shouldBe "1.50k"
        f(145500000.0) shouldBe "146M"
        f(145999999.999999347) shouldBe "146M"
        f(1e26) shouldBe "100Y"
        f(.000001) shouldBe "1.00µ"
        f(.009995) shouldBe "10.0m"

        f = formatter(".4s")
        f(999.5) shouldBe "999.5"
        f(999500.0) shouldBe "999.5k"
        f(.009995) shouldBe "9.995m"
    }

    @Test fun format_s_formats_numbers_smaller_than_1e_24_with_yocto () {
        val f = formatter(".8s")
        f(1.29e-30) shouldBe "0.0000013y" // Note: rounded!
        f(1.29e-29) shouldBe "0.0000129y"
        f(1.29e-28) shouldBe "0.0001290y"
        f(1.29e-27) shouldBe "0.0012900y"
        f(1.29e-26) shouldBe "0.0129000y"
        f(1.29e-25) shouldBe "0.1290000y"
        f(1.29e-24) shouldBe "1.2900000y"
        f(1.29e-23) shouldBe "12.900000y"
        f(1.29e-22) shouldBe "129.00000y"
        f(1.29e-21) shouldBe "1.2900000z"
        f(-1.29e-30) shouldBe "-0.0000013y" // Note: rounded!
        f(-1.29e-29) shouldBe "-0.0000129y"
        f(-1.29e-28) shouldBe "-0.0001290y"
        f(-1.29e-27) shouldBe "-0.0012900y"
        f(-1.29e-26) shouldBe "-0.0129000y"
        f(-1.29e-25) shouldBe "-0.1290000y"
        f(-1.29e-24) shouldBe "-1.2900000y"
        f(-1.29e-23) shouldBe "-12.900000y"
        f(-1.29e-22) shouldBe "-129.00000y"
        f(-1.29e-21) shouldBe "-1.2900000z"
    }

    @Test fun format_s_formats_numbers_larger_than_1e24_with_yotta () {
        val f = formatter(".8s")
        f(1.23e+21) shouldBe "1.2300000Z"
        f(1.23e+22) shouldBe "12.300000Z"
        f(1.23e+23) shouldBe "123.00000Z"
        f(1.23e+24) shouldBe "1.2300000Y"
        f(1.23e+25) shouldBe "12.300000Y"
        f(1.23e+26) shouldBe "123.00000Y"
        f(1.23e+27) shouldBe "1230.0000Y"
        f(1.23e+28) shouldBe "12300.000Y"
        f(1.23e+29) shouldBe "123000.00Y"
        f(1.23e+30) shouldBe "1230000.0Y"
        f(-1.23e+21) shouldBe "-1.2300000Z"
        f(-1.23e+22) shouldBe "-12.300000Z"
        f(-1.23e+23) shouldBe "-123.00000Z"
        f(-1.23e+24) shouldBe "-1.2300000Y"
        f(-1.23e+25) shouldBe "-12.300000Y"
        f(-1.23e+26) shouldBe "-123.00000Y"
        f(-1.23e+27) shouldBe "-1230.0000Y"
        f(-1.23e+28) shouldBe "-12300.000Y"
        f(-1.23e+29) shouldBe "-123000.00Y"
        f(-1.23e+30) shouldBe "-1230000.0Y"
    }

    @Test fun format_s_outputs_SI_prefix_notation_with_a_currency_symbol () {
        var f = formatter("$.2s")
        f(0.0) shouldBe "$0.0"
        f(2.5e5) shouldBe "$250k"
        f(-2.5e8) shouldBe "-$250M"
        f(2.5e11) shouldBe "$250G"

        f = formatter("$.3s")
        f(0.0) shouldBe "$0.00"
        f(1.0) shouldBe "$1.00"
        f(10.0) shouldBe "$10.0"
        f(100.0) shouldBe "$100"
        f(999.5) shouldBe "$1.00k"
        f(999500.0) shouldBe "$1.00M"
        f(1000.0) shouldBe "$1.00k"
        f(1500.5) shouldBe "$1.50k"
        f(145500000.0) shouldBe "$146M"
        f(145999999.999999347) shouldBe "$146M"
        f(1e26) shouldBe "$100Y"
        f(.000001) shouldBe "$1.00µ"
        f(.009995) shouldBe "$10.0m"

        f = formatter("$.4s")
        f(999.5) shouldBe "$999.5"
        f(999500.0) shouldBe "$999.5k"
        f(.009995) shouldBe "$9.995m"
    }

    @Test fun format_s_SI_prefix_notation_exponent_is_consistent_for_small_and_large_numbers  () {
        var f = formatter(".0s")
        f(1e-5) shouldBe "10µ"
        f(1e-4) shouldBe "100µ"
        f(1e-3) shouldBe "1m"
        f(1e-2) shouldBe "10m"
        f(1e-1) shouldBe "100m"
        f(1e+0) shouldBe "1"
        f(1e+1) shouldBe "10"
        f(1e+2) shouldBe "100"
        f(1e+3) shouldBe "1k"
        f(1e+4) shouldBe "10k"
        f(1e+5) shouldBe "100k"

        f = formatter(".4s")
        f(1e-5) shouldBe "10.00µ"
        f(1e-4) shouldBe "100.0µ"
        f(1e-3) shouldBe "1.000m"
        f(1e-2) shouldBe "10.00m"
        f(1e-1) shouldBe "100.0m"
        f(1e+0) shouldBe "1.000"
        f(1e+1) shouldBe "10.00"
        f(1e+2) shouldBe "100.0"
        f(1e+3) shouldBe "1.000k"
        f(1e+4) shouldBe "10.00k"
        f(1e+5) shouldBe "100.0k"
    }

    @Test fun format_0_width_s_will_group_thousands_due_to_zero_fill () {
        val f = formatter("020,s")
        f(42.0) shouldBe "000,000,000,042.0000"
        f(42e12) shouldBe "00,000,000,042.0000T"
    }

    @Test fun format__s_will_group_thousands_for_very_large_numbers () {
        val f = formatter(",s")
        f(42e30) shouldBe "42,000,000Y"
    }

    /**
     * TYPE X
     */

    @Test fun format_x_returns_the_expected_hexadecimal__lowercase_string () {
        formatter("x")(0xdeadbeef.toDouble()) shouldBe "deadbeef"

    }

    @Test fun format_x_returns_the_expected_hexadecimal_lowercase_string_with_prefix () {
        formatter("#x")(0xdeadbeef.toDouble()) shouldBe "0xdeadbeef"

    }

    @Test fun format__x_groups_thousands () {
        formatter(",x")(0xdeadbeef.toDouble()) shouldBe "de,adb,eef"

    }

    @Test fun format_x_groups_thousands () {
        formatter(",x")(0xdeadbeef.toDouble()) shouldBe "de,adb,eef"

    }

    @Test fun format_x_does_not_group_the_prefix () {
        formatter("#,x")(0xadeadbeef.toDouble()) shouldBe "0xade,adb,eef"

    }

    /*@Test fun format_+#x_puts the sign before the prefix () {
        format("+#x")(0xdeadbeef.toDouble()) shouldBe  "+0xdeadbeef"
        format("+#x")(-(0xdeadbeef.toDouble())) shouldBe "-0xdeadbeef"
        format(" #x")(0xdeadbeef.toDouble()) shouldBe  " 0xdeadbeef"
        format(" #x")(-(0xdeadbeef.toDouble())) shouldBe "-0xdeadbeef"
    }*/

    @Test fun format_dollar_x_formats_hexadecimal_currency () {
        formatter("$,x")(0xdeadbeef.toDouble()) shouldBe "\$de,adb,eef"

    }

    @Test fun format_exponent_x_always_has_exponent_zero () {
        formatter(".2x")(0xdeadbeef.toDouble()) shouldBe "deadbeef"
        formatter(".2x")(-4.2) shouldBe "-4"

    }

    @Test fun format_x_rounds_non_integers () {
        formatter("x")(2.4) shouldBe "2"

    }

    @Test fun format_x_can_format_negative_zero_as_zero () {
        formatter("x")(-0.0) shouldBe "0"
        formatter("x")(-1e-12) shouldBe "0"

    }

    /*"format_x_does not consider -0xeee to be positive" {
        format("x")(-0xeee.toDouble()) shouldBe "-eee"
    }*/

    @Test fun format_X_returns_the_expected_hexadecimal_uppercase_string () {
        formatter("X")(0xdeadbeef.toDouble()) shouldBe "DEADBEEF"
    }

    @Test fun format_X_returns_the_expected_hexadecimal_uppercase_string_with_prefix () {
        formatter("#X")(0xdeadbeef.toDouble()) shouldBe "0xDEADBEEF"
    }

    @Test fun format_X_can_format_negative_zero_as_zero () {
        formatter("X")(-0.0) shouldBe "0"
        formatter("X")(-1e-12) shouldBe "0"
    }

    /*"format_X_does not consider -0xeee to be positive" {
        format("X")(-0xeee.toDouble()) shouldBe "-EEE"
    }*/

    @Test fun format_width_x_considers_the_prefix () {
        formatter("20x")(0xdeadbeef.toDouble()) shouldBe "            deadbeef"
        formatter("#20x")(0xdeadbeef.toDouble()) shouldBe "          0xdeadbeef"
        formatter("020x")(0xdeadbeef.toDouble()) shouldBe "000000000000deadbeef"
        formatter("#020x")(0xdeadbeef.toDouble()) shouldBe "0x0000000000deadbeef"
    }

    /**
     * FORMAT PREFIX
     */

    @Test fun formatPrefix_s_value_number_formats_with_the_SI_prefix_appropriate_to_the_specified_value () {
        formatPrefix(",.0s", 1e-6)(.00042) shouldBe "420µ"
        formatPrefix(",.0s", 1e-6)(.0042) shouldBe "4,200µ"
        formatPrefix(",.3s", 1e-3)(.00042) shouldBe "0.420m"

    }

    @Test fun formatPrefix_s_value_number_uses_yocto_for_very_small_reference_values () {
        formatPrefix(",.0s", 1e-27)(1e-24) shouldBe "1y"

    }

    @Test fun formatPrefix_s_value_number__uses_yotta_for_very_large_reference_values () {
        formatPrefix(",.0s", 1e27)(1e24) shouldBe "1Y"

    }

    @Test fun formatPrefix_s_value_number_formats_with_the_specified_SI_prefix () {
        val f = formatPrefix(" $12,.1s", 1e6)
        f(-42e6) shouldBe "      -$42.0M"
        f(+4.2e6) shouldBe "        $4.2M"

    }



    /**
     * INDELTA
     */

    /*tape.Test.prototype.inDelta = function(actual, expected) {
    this._assert(expected - 1e-6 < actual && actual < expected + 1e-6, {
        message: "should be in delta",
        operator: "inDelta",
        actual: actual,
        expected: expected
    });
};*/

    /**
     * LOCALE TEST
     */

    /*"formatLocale({decimal: decimal}) observes the specified decimal point" {
    format({decimal: "|"}).format("06.2f")(2) shouldBe "002|00"
    format({decimal: "/"}).format("06.2f")(2) shouldBe "002/00"
}*/

    /*"formatLocale({currency: [prefix, suffix]}) observes the specified currency prefix and suffix" {
    test.equal(d3.formatLocale({decimal: ".", currency: ["฿", ""]}).format("$06.2f")(2), "฿02.00");
    test.equal(d3.formatLocale({decimal: ".", currency: ["", "฿"]}).format("$06.2f")(2), "02.00฿");
}

tape("formatLocale({grouping: null}) does not perform any grouping", function(test) {
    test.equal(d3.formatLocale({decimal: ".", grouping: null}).format("012,.2f")(2), "000000002.00");
});

tape("formatLocale({grouping: [sizes…]}) observes the specified group sizes", function(test) {
    test.equal(d3.formatLocale({decimal: ".", grouping: [3], thousands: ","}).format("012,.2f")(2), "0,000,002.00");
    test.equal(d3.formatLocale({decimal: ".", grouping: [2], thousands: ","}).format("012,.2f")(2), "0,00,00,02.00");
    test.equal(d3.formatLocale({decimal: ".", grouping: [2, 3], thousands: ","}).format("012,.2f")(2), "00,000,02.00");
    test.equal(d3.formatLocale({decimal: ".", grouping: [3, 2, 2, 2, 2, 2, 2], thousands: ","}).format(",d")(1e12), "10,00,00,00,00,000");
});

tape("formatLocale(…) can format numbers using the Indian numbering system.", function(test) {
    var format = d3.formatLocale(require("../locale/en-IN")).format(",");
    test.equal(format(10), "10");
    test.equal(format(100), "100");
    test.equal(format(1000), "1,000");
    test.equal(format(10000), "10,000");
    test.equal(format(100000), "1,00,000");
    test.equal(format(1000000), "10,00,000");
    test.equal(format(10000000), "1,00,00,000");
    test.equal(format(10000000.4543), "1,00,00,000.4543");
    test.equal(format(1000.321), "1,000.321");
    test.equal(format(10.5), "10.5");
    test.equal(format(-10), "-10");
    test.equal(format(-100), "-100");
    test.equal(format(-1000), "-1,000");
    test.equal(format(-10000), "-10,000");
    test.equal(format(-100000), "-1,00,000");
    test.equal(format(-1000000), "-10,00,000");
    test.equal(format(-10000000), "-1,00,00,000");
    test.equal(format(-10000000.4543), "-1,00,00,000.4543");
    test.equal(format(-1000.321), "-1,000.321");
    test.equal(format(-10.5), "-10.5");
});

tape("formatLocale({thousands: separator}) observes the specified group separator", function(test) {
    test.equal(d3.formatLocale({decimal: ".", grouping: [3], thousands: " "}).format("012,.2f")(2), "0 000 002.00");
    test.equal(d3.formatLocale({decimal: ".", grouping: [3], thousands: "/"}).format("012,.2f")(2), "0/000/002.00");
});*/

    /*tape("locale data is valid", function(test) {
    fs.readdir("locale", function(error, locales) {
        if (error) throw error;
        var q = queue.queue(1);
        locales.forEach(function(locale) {
            if (!/\.json$/i.test(locale)) return;
            q.defer(testLocale, path.join("locale", locale));
        });
        q.awaitAll(function(error) {
            if (error) throw error;
            test.end();
        });
    });

    function testLocale(locale, callback) {
        fs.readFile(locale, "utf8", function(error, locale) {
            if (error) return void callback(error);
            locale = JSON.parse(locale);
            test.equal("currency" in locale, true);
            test.equal("decimal" in locale, true);
            test.equal("grouping" in locale, true);
            test.equal("thousands" in locale, true);
            locale = d3.formatLocale(locale);
            callback(null);
        });
    }
});*/

    /**
     * PRECISION FIXED
     */

    @Test fun precisionFixed_number_returns_the_expected_value(){
        precisionFixed(8.9) shouldBe 0
        precisionFixed(1.1) shouldBe 0
        precisionFixed(0.89) shouldBe 1
        precisionFixed(0.11) shouldBe 1
        precisionFixed(0.089) shouldBe 2
        precisionFixed(0.011) shouldBe 2
    }

    /**
     * PRECISION PREFIX
     */

    /**
     * A generalization from µ to all prefixes:
     * test.equal(format.precisionPrefix(1e-6, 1e-6), 0); // 1µ
     * test.equal(format.precisionPrefix(1e-6, 1e-7), 0); // 10µ
     * test.equal(format.precisionPrefix(1e-6, 1e-8), 0); // 100µ
     */
    @Test fun precisionPrefix_step_value_returns_zero_if_step_has_the_same_units_as_value () {
        for (i in -24..24 step 3) {
            for (j in i until i + 3) {
                precisionPrefix("1e$i".toDouble(), "1e$j".toDouble()) shouldBe 0
            }
        }
    }

    /**
     * A generalization from µ to all prefixes:
     * test.equal(format.precisionPrefix(1e-9, 1e-6), 3); // 0.001µ
     * test.equal(format.precisionPrefix(1e-8, 1e-6), 2); // 0.01µ
     * test.equal(format.precisionPrefix(1e-7, 1e-6), 1); // 0.1µ
     */
    @Test fun precisionPrefix_step_value_returns_greater_than_zero_if_fractional_digits_are_needed (){
        for (i in -24..24 step 3) {
            for (j in i - 4 until i) {
                precisionPrefix(("1e$j".toDouble()), ("1e$i".toDouble())) shouldBe (i - j)
            }
        }
    }

    @Test fun precisionPrefix_step_value_returns_the_expected_precision_when_value_is_less_than_one_yocto () {
        precisionPrefix(1e-24, 1e-24) shouldBe 0 // 1y
        precisionPrefix(1e-25, 1e-25) shouldBe 1 // 0.1y
        precisionPrefix(1e-26, 1e-26) shouldBe 2 // 0.01y
        precisionPrefix(1e-27, 1e-27) shouldBe 3 // 0.001y
        precisionPrefix(1e-28, 1e-28) shouldBe 4 // 0.0001y
    }

    @Test fun precisionPrefix_step_value_returns_the_expected_precision_when_value_is_greater_than_than_one_yotta () {
        precisionPrefix(1e24, 1e24) shouldBe 0 // 1Y
        precisionPrefix(1e24, 1e25) shouldBe 0 // 10Y
        precisionPrefix(1e24, 1e26) shouldBe 0 // 100Y
        precisionPrefix(1e24, 1e27) shouldBe 0 // 1000Y
        precisionPrefix(1e23, 1e27) shouldBe 1 // 1000.0Y
    }

    /**
     * PRECISION ROUND
     */
    @Test fun precisionRound_step_max_returns_the_expected_value (){
        precisionRound(0.1, 1.1) shouldBe 2 // "1.0", "1.1"
        precisionRound(0.01, 0.99) shouldBe 2 // "0.98", "0.99"
        precisionRound(0.01, 1.00) shouldBe 2 // "0.99", "1.0"
        precisionRound(0.01, 1.01) shouldBe 3 // "1.00", "1.01"
    }
}
