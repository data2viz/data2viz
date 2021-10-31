/*
 * Copyright (c) 2018-2021. data2viz sàrl.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 *
 */

@file:Suppress("FunctionName", "unused")

package io.data2viz.format

import io.data2viz.test.TestBase
import kotlin.math.PI
import kotlin.test.Ignore
import kotlin.test.Test


class FormatTests : TestBase() {

    @Test
    fun format_types() {
        formatter()                         (PI) shouldBe "3.14159265359"     // <-
        formatter(Type.DECIMAL)             (PI) shouldBe "3.14159"           // <-
        formatter(Type.DECIMAL_OR_EXPONENT) (PI) shouldBe "3.14159"           // <-
        formatter(Type.DECIMAL_ROUNDED)     (PI) shouldBe "3"                 // <- rounded to integer
        formatter(Type.DECIMAL_WITH_SI)     (PI) shouldBe "3.14159"           // <-
        formatter(Type.FIXED_POINT)         (PI) shouldBe "3.141593"          // <-
        formatter(Type.BINARY)              (PI) shouldBe "11"                // <-
        formatter(Type.OCTAL)               (PI) shouldBe "3"                 // <-
        formatter(Type.HEX_LOWERCASE)       (PI) shouldBe "3"                 // <-
        formatter(Type.HEX_UPPERCASE)       (PI) shouldBe "3"                 // <-
        formatter(Type.PERCENT)             (PI) shouldBe "314.159265%"       // <-
        formatter(Type.PERCENT_ROUNDED)     (PI) shouldBe "314.159%"          // <-
    }

    @Test
    @Ignore //TODO IOS
    fun format_types_failing() {
        formatter(Type.EXPONENT)            (PI) shouldBe "3.141593e+0"       // <-
    }

    /**
     * TYPE C
     */

    @Test
    @Ignore
    fun format_c_unicode_character(){
        formatter("c")('☃'.code.toDouble()) shouldBe "☃"
        formatter("020c")('☃'.code.toDouble()) shouldBe "0000000000000000000☃"
        formatter(" ^20c")('☃'.code.toDouble()) shouldBe "         ☃          "
    }

//    "format_c_does not localize a coefficient point" {
//        formatLocale({coefficient: "/"}).format("c")(".") shouldBe "."
//
//    }*/






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

    @Test fun demo(){
        formatter(".0%")(0.123)                         shouldBe "12%"                      // rounded percentage, "12%"
        formatter("+20")(42.0)                          shouldBe "                 +42"     // space-filled and signed, "                 +42"
        formatter(".^20")(42.0)                         shouldBe ".........42........."     // dot-filled and centered, ".........42........."
        formatter(".2s")(42e6)                          shouldBe "42M"                      // SI-prefix with two significant digits, "42M"
        formatter("#x")(48879.0)                        shouldBe "0xbeef"                   // prefixed lowercase hexadecimal, "0xbeef"
        formatter(",.2r")(4223.0)                       shouldBe "4,200"                    // grouped thousands with two significant digits, "4,200"
        Locales.en_GB.formatter("($.2f")(-3.5)       shouldBe "(£3.50)" // localized fixed-point currency, "(£3.50)"
    }

    @Test fun demo_API(){
        formatter(
                Type.PERCENT,
                precision = 0
        )(0.123)                                                                       shouldBe "12%"                  // rounded percentage, "12%"
        formatter(sign = Sign.PLUS, width = 20)(42.0)                                                                       shouldBe "                 +42" // space-filled and signed, "                 +42"
        formatter(fill = '.', align = Align.CENTER, width = 20)(42.0)                                                       shouldBe ".........42........." // dot-filled and centered, ".........42........."
        formatter(
                Type.DECIMAL_WITH_SI,
                precision = 2
        )(42e6)                                                                shouldBe "42M"                  // SI-prefix with two significant digits, "42M"
        formatter(
                Type.HEX_LOWERCASE,
                symbol = Symbol.NUMBER_BASE
        )(48879.0)                                                 shouldBe "0xbeef"               // prefixed lowercase hexadecimal, "0xbeef"
        formatter(
                Type.DECIMAL,
                group = true,
                precision = 2
        )(4223.0)                                                        shouldBe "4,200"                // grouped thousands with two significant digits, "4,200"
        Locales.en_GB
                .formatter(
                        Type.FIXED_POINT,
                        symbol = Symbol.CURRENCY,
                        sign = Sign.PARENTHESES,
                        precision = 2
                )(-3.5) shouldBe "(£3.50)"              // localized fixed-point currency, "(£3.50)"
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

