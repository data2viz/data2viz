package io.data2viz.format

import io.data2viz.test.StringSpec

class FormatTests : StringSpec() {
    init {

        /**
         * TYPE F
         */

        "format(\"f\") can output fixed-point notation (8 tests)" {
            Locale().format(".1f")(0.49) shouldBe "0.5"
            Locale().format(".2f")(0.449) shouldBe "0.45"
            Locale().format(".3f")(0.4449) shouldBe "0.445"
            Locale().format(".5f")(0.444449) shouldBe "0.44445"
            Locale().format(".1f")(100.0) shouldBe "100.0"
            Locale().format(".2f")(100.0) shouldBe "100.00"
            Locale().format(".3f")(100.0) shouldBe "100.000"
            Locale().format(".5f")(100.0) shouldBe "100.00000"
        }

        "format(\"+$,f\") can output a currency with comma-grouping and sign (5 tests)" {
            val f = Locale().format("+$,.2f")
            f(0.0) shouldBe "+$0.00"
            f(0.429) shouldBe "+$0.43"
            f(-0.429) shouldBe "-$0.43"
            f(-1.0) shouldBe "-$1.00"
            f(1e4) shouldBe "+$10,000.00"
        }

        "format(\",.f\") can grouping thousands, space fill, and round to significant digits (8 tests)" {
            Locale().format("10,.1f")(123456.49) shouldBe " 123,456.5"
            Locale().format("10,.2f")(1234567.449) shouldBe "1,234,567.45"
            Locale().format("10,.3f")(12345678.4449) shouldBe "12,345,678.445"
            Locale().format("10,.5f")(123456789.444449) shouldBe "123,456,789.44445"
            Locale().format("10,.1f")(123456.0) shouldBe " 123,456.0"
            Locale().format("10,.2f")(1234567.0) shouldBe "1,234,567.00"
            Locale().format("10,.3f")(12345678.0) shouldBe "12,345,678.000"
            Locale().format("10,.5f")(123456789.0) shouldBe "123,456,789.00000"
        }

        "format(\"f\") can display integers in fixed-point notation" {
            Locale().format("f")(42.0) shouldBe "42.000000"
        }

        "format(\"f\") can format negative zero as zero (2 tests)" {
            Locale().format("f")(-0.0) shouldBe "0.000000"
            Locale().format("f")(-1e-12) shouldBe "0.000000"
        }

//        "format(\"f\") can format negative infinity" {
//            Locale().format("f")(-Infinity) shouldBe "-Infinity"
//        }

//        "format(\",f\") does not grouping Infinity" {
//            Locale().format(",f")(Infinity) shouldBe "Infinity"
//        }

        /**
         * TYPE %
         */

        "format(\"%\") can output a whole percentage (7 tests)" {
            val f = Locale().format(".0%")
            f(0.0) shouldBe "0%"
            f(.042) shouldBe "4%"
            f(.42) shouldBe "42%"
            f(4.2) shouldBe "420%"
            f(-.042) shouldBe "-4%"
            f(-.42) shouldBe "-42%"
            f(-4.2) shouldBe "-420%"
        }

        "format(\".%\") can output a percentage with exponent (2 tests)" {
            var f = Locale().format(".1%")
            f(.234) shouldBe "23.4%"
            f = Locale().format(".2%")
            f(.234) shouldBe "23.40%"
        }

        "format(\"%\") fill respects suffix (2 tests)" {
            Locale().format("020.0%")(42.0) shouldBe "0000000000000004200%"
            Locale().format("20.0%")(42.0) shouldBe "               4200%"
        }

        //      -42,200%       did not equal       -42,200%
        //         42%           did not equal          42%

        "format(\"^%\") align center puts suffix adjacent to number (3 tests)" {
            Locale().format("^21.0%")(.42) shouldBe "         42%         "
            Locale().format("^21,.0%")(422.0) shouldBe "       42,200%       "
            Locale().format("^21,.0%")(-422.0) shouldBe "      -42,200%       "
            Locale()
        }

        /**
         * ARABIC LOCALE
         */

        /*"formatLocale(…) can format numbers using ar-001 locale." {
            var locale = d3.formatLocale(require("../locale/ar-001"))
            locale.format("$,.2f")(-1234.56) shouldBe "-١٬٢٣٤٫٥٦"
            
        }

        "formatLocale(…) can format numbers using ar-AE locale." {
            var locale = d3.formatLocale(require("../locale/ar-AE"))
            locale.format("$,.2f")(1234.56) shouldBe "١٬٢٣٤٫٥٦ د.إ."
            
        }

        "formatLocale(…) can format numbers using ar-BH locale." {
            var locale = d3.formatLocale(require("../locale/ar-BH"))
            locale.format("$,.2f")(1234.56) shouldBe "١٬٢٣٤٫٥٦ د.ب."
            
        }

        "formatLocale(…) can format numbers using ar-DJ locale." {
            var locale = d3.formatLocale(require("../locale/ar-DJ"))
            locale.format("$,.2f")(1234.56) shouldBe "\u200fFdj ١٬٢٣٤٫٥٦"
            
        }

        "formatLocale(…) can format numbers using ar-DZ locale." {
            var locale = d3.formatLocale(require("../locale/ar-DZ"))
            locale.format("$,.2f")(1234.56) shouldBe "د.ج. 1.234,56"
            
        }

        "formatLocale(…) can format numbers using ar-EG locale." {
            var locale = d3.formatLocale(require("../locale/ar-EG"))
            locale.format("$,.2f")(1234.56) shouldBe "١٬٢٣٤٫٥٦ ج.م."
            
        }

        "formatLocale(…) can format numbers using ar-EH locale." {
            var locale = d3.formatLocale(require("../locale/ar-EH"))
            locale.format("$,.2f")(1234.56) shouldBe "د.م. 1,234.56"
            
        }

        "formatLocale(…) can format numbers using ar-ER locale." {
            var locale = d3.formatLocale(require("../locale/ar-ER"))
            locale.format("$,.2f")(1234.56) shouldBe "Nfk ١٬٢٣٤٫٥٦"
            
        }

        "formatLocale(…) can format numbers using ar-IL locale." {
            var locale = d3.formatLocale(require("../locale/ar-IL"))
            locale.format("$,.2f")(1234.56) shouldBe "₪ ١٬٢٣٤٫٥٦"
            
        }

        "formatLocale(…) can format numbers using ar-IQ locale." {
            var locale = d3.formatLocale(require("../locale/ar-IQ"))
            locale.format("$,.2f")(1234.56) shouldBe "١٬٢٣٤٫٥٦ د.ع."
            
        }

        "formatLocale(…) can format numbers using ar-JO locale." {
            var locale = d3.formatLocale(require("../locale/ar-JO"))
            locale.format("$,.2f")(1234.56) shouldBe "١٬٢٣٤٫٥٦ د.أ."
            
        }

        "formatLocale(…) can format numbers using ar-KM locale." {
            var locale = d3.formatLocale(require("../locale/ar-KM"))
            locale.format("$,.2f")(1234.56) shouldBe "١٬٢٣٤٫٥٦ ف.ج.ق."
            
        }

        "formatLocale(…) can format numbers using ar-KW locale." {
            var locale = d3.formatLocale(require("../locale/ar-KW"))
            locale.format("$,.2f")(1234.56) shouldBe "١٬٢٣٤٫٥٦ د.ك."
            
        }

        "formatLocale(…) can format numbers using ar-LB locale." {
            var locale = d3.formatLocale(require("../locale/ar-LB"))
            locale.format("$,.2f")(1234.56) shouldBe "١٬٢٣٤٫٥٦ ل.ل."
            
        }

        "formatLocale(…) can format numbers using ar-MA locale." {
            var locale = d3.formatLocale(require("../locale/ar-MA"))
            locale.format("$,.2f")(1234.56) shouldBe "د.م. 1.234,56"
            
        }

        "formatLocale(…) can format numbers using ar-MR locale." {
            var locale = d3.formatLocale(require("../locale/ar-MR"))
            locale.format("$,.2f")(1234.56) shouldBe "١٬٢٣٤٫٥٦ أ.م."
            
        }

        "formatLocale(…) can format numbers using ar-OM locale." {
            var locale = d3.formatLocale(require("../locale/ar-OM"))
            locale.format("$,.2f")(1234.56) shouldBe "١٬٢٣٤٫٥٦ ر.ع."
            
        }

        "formatLocale(…) can format numbers using ar-PS locale." {
            var locale = d3.formatLocale(require("../locale/ar-PS"))
            locale.format("$,.2f")(1234.56) shouldBe "₪ ١٬٢٣٤٫٥٦"
            
        }

        "formatLocale(…) can format numbers using ar-QA locale." {
            var locale = d3.formatLocale(require("../locale/ar-QA"))
            locale.format("$,.2f")(1234.56) shouldBe "١٬٢٣٤٫٥٦ ر.ق."
            
        }

        "formatLocale(…) can format numbers using ar-SA locale." {
            var locale = d3.formatLocale(require("../locale/ar-SA"))
            locale.format("$,.2f")(1234.56) shouldBe "١٬٢٣٤٫٥٦ ر.س."
            
        }

        "formatLocale(…) can format numbers using ar-SD locale." {
            var locale = d3.formatLocale(require("../locale/ar-SD"))
            locale.format("$,.2f")(1234.56) shouldBe "١٬٢٣٤٫٥٦ ج.س."
            
        }

        "formatLocale(…) can format numbers using ar-SO locale." {
            var locale = d3.formatLocale(require("../locale/ar-SO"))
            locale.format("$,.2f")(1234.56) shouldBe "‏S ١٬٢٣٤٫٥٦"
            
        }

        "formatLocale(…) can format numbers using ar-SS locale." {
            var locale = d3.formatLocale(require("../locale/ar-SS"))
            locale.format("$,.2f")(1234.56) shouldBe "£ ١٬٢٣٤٫٥٦"
            
        }

        "formatLocale(…) can format numbers using ar-SY locale." {
            var locale = d3.formatLocale(require("../locale/ar-SY"))
            locale.format("$,.2f")(1234.56) shouldBe "١٬٢٣٤٫٥٦ ل.س."
            
        }

        "formatLocale(…) can format numbers using ar-TD locale." {
            var locale = d3.formatLocale(require("../locale/ar-TD"))
            locale.format("$,.2f")(1234.56) shouldBe "\u200fFCFA ١٬٢٣٤٫٥٦"
            
        }

        "formatLocale(…) can format numbers using ar-TN locale." {
            var locale = d3.formatLocale(require("../locale/ar-TN"))
            locale.format("$,.2f")(1234.56) shouldBe "د.ت. 1.234,56"
            
        }*/

        /**
         * DEFAULT LOCALE
         */

        /*var enUs = {
            "coefficient": ".",
            "thousands": ",",
            "grouping": [3],
            "currency": ["$", ""]
        };

        var frFr = {
            "coefficient": ",",
            "thousands": ".",
            "grouping": [3],
            "currency": ["", "\u00a0€"]
        };

        "d3.formatDefaultLocale(definition) returns the new default locale" {
            var locale = d3.formatDefaultLocale(frFr)
            try {
                locale.format("$,.2f")(12345678.90) shouldBe "12.345.678,90 €"
                
            } finally {
                d3.formatDefaultLocale(enUs)
            }
        }

        "d3.formatDefaultLocale(definition) affects d3.format" {
            var locale = d3.formatDefaultLocale(frFr)
            try {
                d3.format, locale.format)
                d3.format("$,.2f")(12345678.90) shouldBe "12.345.678,90 €"
                
            } finally {
                d3.formatDefaultLocale(enUs)
            }
        }

        "d3.formatDefaultLocale(definition) affects d3.formatPrefix" {
            var locale = d3.formatDefaultLocale(frFr)
            try {
                d3.formatPrefix, locale.formatPrefix)
                d3.formatPrefix(",.2", 1e3)(12345678.90) shouldBe "12.345,68k"
                
            } finally {
                d3.formatDefaultLocale(enUs)
            }
        }*/

        /**
         * TYPE B
         */

        "format(\"b\") binary" {
            Locale().format("b")(10.0) shouldBe "1010"

        }

        "format(\"#b\") binary with prefix" {
            Locale().format("#b")(10.0) shouldBe "0b1010"

        }

        /**
         * TYPE C
         */

        /*"format(\"c\") unicode character" {
            Locale().format("c")("☃") shouldBe "☃"
            Locale().format("020c")("☃") shouldBe "0000000000000000000☃"
            Locale().format(" ^20c")("☃") shouldBe "         ☃          "
            Locale().format("$c")("☃") shouldBe "$☃"
            
        }

        "format(\"c\") does not localize a coefficient point" {
            Locale().formatLocale({coefficient: "/"}).format("c")(".") shouldBe "."
            
        }*/

        /**
         * TYPE D
         */

        "format(\"d\") can zero fill (8 tests)" {
            var f = Locale().format("08d")
            f(0.0) shouldBe "00000000"
            f(42.0) shouldBe "00000042"
            f(42000000.0) shouldBe "42000000"
            f(420000000.0) shouldBe "420000000"
            f(-4.0) shouldBe "-0000004"
            f(-42.0) shouldBe "-0000042"
            f(-4200000.0) shouldBe "-4200000"
            f(-42000000.0) shouldBe "-42000000"
        }

        "format(\"d\") can .0space fill (8 tests)" {
            var f = Locale().format("8d")
            f(0.0) shouldBe "       0"
            f(42.0) shouldBe "      42"
            f(42000000.0) shouldBe "42000000"
            f(420000000.0) shouldBe "420000000"
            f(-4.0) shouldBe "      -4"
            f(-42.0) shouldBe "     -42"
            f(-4200000.0) shouldBe "-4200000"
            f(-42000000.0) shouldBe "-42000000"
        }

        "format(\"d\") can underscore fill (8 tests)" {
            var f = Locale().format("_>8d")
            f(0.0) shouldBe "_______0"
            f(42.0) shouldBe "______42"
            f(42000000.0) shouldBe "42000000"
            f(420000000.0) shouldBe "420000000"
            f(-4.0) shouldBe "______-4"
            f(-42.0) shouldBe "_____-42"
            f(-4200000.0) shouldBe "-4200000"
            f(-42000000.0) shouldBe "-42000000"
        }

        "format(\"d\") can zero fill with sign and group (8 tests)" {
            var f = Locale().format("+08,d")
            f(0.0) shouldBe "+0,000,000"
            f(42.0) shouldBe "+0,000,042"
            f(42000000.0) shouldBe "+42,000,000"
            f(420000000.0) shouldBe "+420,000,000"
            f(-4.0) shouldBe "-0,000,004"
            f(-42.0) shouldBe "-0,000,042"
            f(-4200000.0) shouldBe "-4,200,000"
            f(-42000000.0) shouldBe "-42,000,000"
        }

        "format(\"d\") always uses zero exponent (3 tests)" {
            var f = Locale().format(".2d")
            f(0.0) shouldBe "0"
            f(42.0) shouldBe "42"
            f(-4.2) shouldBe "-4"

        }

        "format(\"d\") rounds non-integers" {
            var f = Locale().format("d")
            f(4.2) shouldBe "4"
        }

        "format(\",d\") can group thousands (9 tests)" {
            var f = Locale().format(",d")
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

        "format(\"0,d\") can group thousands and zero fill (15 tests)" {
            Locale().format("01,d")(0.0) shouldBe "0"
            Locale().format("01,d")(0.0) shouldBe "0"
            Locale().format("02,d")(0.0) shouldBe "00"
            Locale().format("03,d")(0.0) shouldBe "000"
            Locale().format("04,d")(0.0) shouldBe "0,000"
            Locale().format("05,d")(0.0) shouldBe "0,000"
            Locale().format("06,d")(0.0) shouldBe "00,000"
            Locale().format("08,d")(0.0) shouldBe "0,000,000"
            Locale().format("013,d")(0.0) shouldBe "0,000,000,000"
            Locale().format("021,d")(0.0) shouldBe "0,000,000,000,000,000"
            Locale().format("013,d")(-42000000.0) shouldBe "-0,042,000,000"
            Locale().format("012,d")(1e21) shouldBe "0,000,001e+21"
            Locale().format("013,d")(1e21) shouldBe "0,000,001e+21"
            Locale().format("014,d")(1e21) shouldBe "00,000,001e+21"
            Locale().format("015,d")(1e21) shouldBe "000,000,001e+21"
        }

        "format(\"0,d\") can group thousands and zero fill with overflow (7 tests)" {
            Locale().format("01,d")(1.0) shouldBe "1"
            Locale().format("01,d")(1.0) shouldBe "1"
            Locale().format("02,d")(12.0) shouldBe "12"
            Locale().format("03,d")(123.0) shouldBe "123"
            Locale().format("05,d")(12345.0) shouldBe "12,345"
            Locale().format("08,d")(12345678.0) shouldBe "12,345,678"
            Locale().format("013,d")(1234567890123.0) shouldBe "1,234,567,890,123"
        }

        "format(\",d\") can group thousands and space fill (8 tests)" {
            Locale().format("1,d")(0.0) shouldBe "0"
            Locale().format("1,d")(0.0) shouldBe "0"
            Locale().format("2,d")(0.0) shouldBe " 0"
            Locale().format("3,d")(0.0) shouldBe "  0"
            Locale().format("5,d")(0.0) shouldBe "    0"
            Locale().format("8,d")(0.0) shouldBe "       0"
            Locale().format("13,d")(0.0) shouldBe "            0"
            Locale().format("21,d")(0.0) shouldBe "                    0"
        }

        "format(\",d\") can group thousands and space fill with overflow (7 tests)" {
            Locale().format("1,d")(1.0) shouldBe "1"
            Locale().format("1,d")(1.0) shouldBe "1"
            Locale().format("2,d")(12.0) shouldBe "12"
            Locale().format("3,d")(123.0) shouldBe "123"
            Locale().format("5,d")(12345.0) shouldBe "12,345"
            Locale().format("8,d")(12345678.0) shouldBe "12,345,678"
            Locale().format("13,d")(1234567890123.0) shouldBe "1,234,567,890,123"
        }

        "format(\"<d\") align left (8 tests)" {
            Locale().format("<1,d")(0.0) shouldBe "0"
            Locale().format("<1,d")(0.0) shouldBe "0"
            Locale().format("<2,d")(0.0) shouldBe "0 "
            Locale().format("<3,d")(0.0) shouldBe "0  "
            Locale().format("<5,d")(0.0) shouldBe "0    "
            Locale().format("<8,d")(0.0) shouldBe "0       "
            Locale().format("<13,d")(0.0) shouldBe "0            "
            Locale().format("<21,d")(0.0) shouldBe "0                    "
        }

        "format(\">d\") align right (10 tests)" {
            Locale().format(">1,d")(0.0) shouldBe "0"
            Locale().format(">1,d")(0.0) shouldBe "0"
            Locale().format(">2,d")(0.0) shouldBe " 0"
            Locale().format(">3,d")(0.0) shouldBe "  0"
            Locale().format(">5,d")(0.0) shouldBe "    0"
            Locale().format(">8,d")(0.0) shouldBe "       0"
            Locale().format(">13,d")(0.0) shouldBe "            0"
            Locale().format(">21,d")(0.0) shouldBe "                    0"
            Locale().format(">21,d")(1000.0) shouldBe "                1,000"
            Locale().format(">21,d")(1e21) shouldBe "                1e+21"
        }

        "format(\"^d\") align center (10 tests)" {
            Locale().format("^1,d")(0.0) shouldBe "0"
            Locale().format("^1,d")(0.0) shouldBe "0"
            Locale().format("^2,d")(0.0) shouldBe "0 "
            Locale().format("^3,d")(0.0) shouldBe " 0 "
            Locale().format("^5,d")(0.0) shouldBe "  0  "
            Locale().format("^8,d")(0.0) shouldBe "   0    "
            Locale().format("^13,d")(0.0) shouldBe "      0      "
            Locale().format("^21,d")(0.0) shouldBe "          0          "
            Locale().format("^21,d")(1000.0) shouldBe "        1,000        "
            Locale().format("^21,d")(1e21) shouldBe "        1e+21        "
        }

        "format(\"=+,d\") pad after sign (9 tests)" {
            Locale().format("=+1,d")(0.0) shouldBe "+0"
            Locale().format("=+1,d")(0.0) shouldBe "+0"
            Locale().format("=+2,d")(0.0) shouldBe "+0"
            Locale().format("=+3,d")(0.0) shouldBe "+ 0"
            Locale().format("=+5,d")(0.0) shouldBe "+   0"
            Locale().format("=+8,d")(0.0) shouldBe "+      0"
            Locale().format("=+13,d")(0.0) shouldBe "+           0"
            Locale().format("=+21,d")(0.0) shouldBe "+                   0"
            Locale().format("=+21,d")(1e21) shouldBe "+               1e+21"
        }

        "format(\"=+$,d\") pad after sign with currency (9 tests)" {
            Locale().format("=+$1,d")(0.0) shouldBe "+$0"
            Locale().format("=+$1,d")(0.0) shouldBe "+$0"
            Locale().format("=+$2,d")(0.0) shouldBe "+$0"
            Locale().format("=+$3,d")(0.0) shouldBe "+$0"
            Locale().format("=+$5,d")(0.0) shouldBe "+$  0"
            Locale().format("=+$8,d")(0.0) shouldBe "+$     0"
            Locale().format("=+$13,d")(0.0) shouldBe "+$          0"
            Locale().format("=+$21,d")(0.0) shouldBe "+$                  0"
            Locale().format("=+$21,d")(1e21) shouldBe "+$              1e+21"
        }

        "format(\" ,d\") a space can denote positive numbers (9 tests)" {
            Locale().format(" 1,d")(-1.0) shouldBe "-1"
            Locale().format(" 1,d")(0.0) shouldBe " 0"
            Locale().format(" 2,d")(0.0) shouldBe " 0"
            Locale().format(" 3,d")(0.0) shouldBe "  0"
            Locale().format(" 5,d")(0.0) shouldBe "    0"
            Locale().format(" 8,d")(0.0) shouldBe "       0"
            Locale().format(" 13,d")(0.0) shouldBe "            0"
            Locale().format(" 21,d")(0.0) shouldBe "                    0"
            Locale().format(" 21,d")(1e21) shouldBe "                1e+21"
        }

        "format(\"-,d\") explicitly only use a sign for negative numbers (8 tests)" {
            Locale().format("-1,d")(-1.0) shouldBe "-1"
            Locale().format("-1,d")(0.0) shouldBe "0"
            Locale().format("-2,d")(0.0) shouldBe " 0"
            Locale().format("-3,d")(0.0) shouldBe "  0"
            Locale().format("-5,d")(0.0) shouldBe "    0"
            Locale().format("-8,d")(0.0) shouldBe "       0"
            Locale().format("-13,d")(0.0) shouldBe "            0"
            Locale().format("-21,d")(0.0) shouldBe "                    0"
        }

        "format(\"d\") can format negative zero as zero (2 tests)" {
            Locale().format("1d")(-0.0) shouldBe "0"
            Locale().format("1d")(-1e-12) shouldBe "0"
        }

        /**
         * TYPE E
         */

        "format(\"e\") can output exponent notation (10 tests)" {
            var f = Locale().format("e")
            f(0.0) shouldBe "0.000000e+0"
            f(42.0) shouldBe "4.200000e+1"
            f(42000000.0) shouldBe "4.200000e+7"
            f(420000000.0) shouldBe "4.200000e+8"
            f(-4.0) shouldBe "-4.000000e+0"
            f(-42.0) shouldBe "-4.200000e+1"
            f(-4200000.0) shouldBe "-4.200000e+6"
            f(-42000000.0) shouldBe "-4.200000e+7"
            Locale().format(".0e")(42.0) shouldBe "4e+1"
            Locale().format(".3e")(42.0) shouldBe "4.200e+1"
        }

        "format(\"e\") can format negative zero as zero (2 tests)" {
            Locale().format("1e")(-0.0) shouldBe "0.000000e+0"
            Locale().format("1e")(-1e-12) shouldBe "-1.000000e-12"
        }

        /*"format(\",e\") does not group Infinity" {
            Locale().format(",e")(Infinity) shouldBe "Infinity"
            
        }*/

        /**
         * TYPE G
         */

        "format(\"g\") can output general notation (11 tests)" {
            Locale().format(".1g")(0.049) shouldBe "0.05"
            Locale().format(".1g")(0.49) shouldBe "0.5"
            Locale().format(".2g")(0.449) shouldBe "0.45"
            Locale().format(".3g")(0.4449) shouldBe "0.445"
            Locale().format(".5g")(0.444449) shouldBe "0.44445"
            Locale().format(".1g")(100.0) shouldBe "1e+2"
            Locale().format(".2g")(100.0) shouldBe "1.0e+2"
            Locale().format(".3g")(100.0) shouldBe "100"
            Locale().format(".5g")(100.0) shouldBe "100.00"
            Locale().format(".5g")(100.2) shouldBe "100.20"
            Locale().format(".2g")(0.002) shouldBe "0.0020"

        }

        "format(\",g\") can group thousands with general notation (8 tests)" {
            var f = Locale().format(",.12g")
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

        "format(\"n\") is an alias for \",g\" (11 tests)" {
            var f = Locale().format(".12n")
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

        "format(\"n\") uses zero padding (8 tests)" {
            Locale().format("01.0n")(0.0) shouldBe "0"
            Locale().format("02.0n")(0.0) shouldBe "00"
            Locale().format("03.0n")(0.0) shouldBe "000"
            Locale().format("05.0n")(0.0) shouldBe "0,000"
            Locale().format("08.0n")(0.0) shouldBe "0,000,000"
            Locale().format("013.0n")(0.0) shouldBe "0,000,000,000"
            Locale().format("021.0n")(0.0) shouldBe "0,000,000,000,000,000"
            Locale().format("013.8n")(-42000000.0) shouldBe "-0,042,000,000"
        }

        /**
         * TYPE NONE
         */

        "format(\".[exponent]\") uses significant exponent and trims insignificant zeros (10 tests)" {
            Locale().format(".1")(4.9) shouldBe "5"
            Locale().format(".1")(0.49) shouldBe "0.5"
            Locale().format(".2")(4.8) shouldBe "4.8"
            Locale().format(".2")(0.49) shouldBe "0.49"
            Locale().format(".2")(0.449) shouldBe "0.45"
            Locale().format(".3")(4.9) shouldBe "4.9"
            Locale().format(".3")(0.49) shouldBe "0.49"
            Locale().format(".3")(0.449) shouldBe "0.449"
            Locale().format(".3")(0.4449) shouldBe "0.445"
            Locale().format(".5")(0.444449) shouldBe "0.44445"
        }

        "format(\".[exponent]\") does not trim significant zeros (8 tests)" {
            Locale().format(".5")(10.0) shouldBe "10"
            Locale().format(".5")(100.0) shouldBe "100"
            Locale().format(".5")(1000.0) shouldBe "1000"
            Locale().format(".5")(21010.0) shouldBe "21010"
            Locale().format(".5")(1.10001) shouldBe "1.1"
            Locale().format(".5")(1.10001e6) shouldBe "1.1e+6"
            Locale().format(".6")(1.10001) shouldBe "1.10001"
            Locale().format(".6")(1.10001e6) shouldBe "1.10001e+6"
        }

        "format(\".[exponent]\") also trims the coefficient point if there are only insignificant zeros (4 tests)" {
            Locale().format(".5")(1.00001) shouldBe "1"
            Locale().format(".5")(1.00001e6) shouldBe "1e+6"
            Locale().format(".6")(1.00001) shouldBe "1.00001"
            Locale().format(".6")(1.00001e6) shouldBe "1.00001e+6"
        }

        "format(\"$\") can output a currency (7 tests)" {
            var f = Locale().format("$")
            f(0.0) shouldBe "$0"
            f(.042) shouldBe "$0.042"
            f(.42) shouldBe "$0.42"
            f(4.2) shouldBe "$4.2"
            f(-.042) shouldBe "-$0.042"
            f(-.42) shouldBe "-$0.42"
            f(-4.2) shouldBe "-$4.2"
        }

        "format(\"($\") can output a currency with parentheses for negative values (7 tests)" {
            var f = Locale().format("($")
            f(0.0) shouldBe "$0"
            f(.042) shouldBe "$0.042"
            f(.42) shouldBe "$0.42"
            f(4.2) shouldBe "$4.2"
            f(-.042) shouldBe "($0.042)"
            f(-.42) shouldBe "($0.42)"
            f(-4.2) shouldBe "($4.2)"
        }

        "format(\"\") can format negative zero as zero" {
            Locale().format("")(-0.0) shouldBe "0"
        }

        /*"format(\"\") can format negative infinity" {
            Locale().format("")(-Infinity) shouldBe "-Infinity"
            
        }*/

        /**
         * TYPE O
         */

        "format(\"o\") octal" {
            Locale().format("o")(10.0) shouldBe "12"

        }

        "format(\"#o\") octal with prefix" {
            Locale().format("#o")(10.0) shouldBe "0o12"

        }

        /**
         * TYPE P
         */

        "format(\"p\") can output a percentage (9 tests)" {
            var f = Locale().format("p")
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

        "format(\"+p\") can output a percentage with rounding and sign (8 tests)" {
            var f = Locale().format("+.2p")
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

        "format(\"r\") can round to significant digits (28 tests)" {
            Locale().format(".2r")(0.0) shouldBe "0.0"
            Locale().format(".1r")(0.049) shouldBe "0.05"
            Locale().format(".1r")(-0.049) shouldBe "-0.05"
            Locale().format(".1r")(0.49) shouldBe "0.5"
            Locale().format(".1r")(-0.49) shouldBe "-0.5"
            Locale().format(".2r")(0.449) shouldBe "0.45"
            Locale().format(".3r")(0.4449) shouldBe "0.445"
            Locale().format(".3r")(2.00) shouldBe "2.00"
            Locale().format(".3r")(3.9995) shouldBe "4.00"
            Locale().format(".5r")(0.444449) shouldBe "0.44445"
            Locale().format("r")(123.45) shouldBe "123.450"
            Locale().format(".1r")(123.45) shouldBe "100"
            Locale().format(".2r")(123.45) shouldBe "120"
            Locale().format(".3r")(123.45) shouldBe "123"
            Locale().format(".4r")(123.45) shouldBe "123.5"
            Locale().format(".5r")(123.45) shouldBe "123.45"
            Locale().format(".6r")(123.45) shouldBe "123.450"
            Locale().format(".1r")(.9) shouldBe "0.9"
            Locale().format(".1r")(.09) shouldBe "0.09"
            Locale().format(".1r")(.949) shouldBe "0.9"
            Locale().format(".1r")(.0949) shouldBe "0.09"
            Locale().format(".1r")(.0000000129) shouldBe "0.00000001"
            Locale().format(".2r")(.0000000129) shouldBe "0.000000013"
            Locale().format(".2r")(.00000000129) shouldBe "0.0000000013"
            Locale().format(".3r")(.00000000129) shouldBe "0.00000000129"
            Locale().format(".4r")(.00000000129) shouldBe "0.000000001290"
            Locale().format(".10r")(.9999999999) shouldBe "0.9999999999"
            Locale().format(".15r")(.999999999999999) shouldBe "0.999999999999999"

        }

        "format(\"r\") can round very small numbers" {
            var f = Locale().format(".2r")
            f(1e-22) shouldBe "0.00000000000000000000010"

        }

        /**
         * TYPE S
         */

        "format(\"s\") outputs SI-prefix notation with default exponent 6 (12 tests)" {
            var f = Locale().format("s")
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

        "format(\"[.exponent]s\") outputs SI-prefix notation with exponent significant digits (16 tests)" {
            var f = Locale().format(".3s")
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

            f = Locale().format(".4s")
            f(999.5) shouldBe "999.5"
            f(999500.0) shouldBe "999.5k"
            f(.009995) shouldBe "9.995m"

        }

        "format(\"s\") formats numbers smaller than 1e-24 with yocto (20 tests)" {
            var f = Locale().format(".8s")
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

        "format(\"s\") formats numbers larger than 1e24 with yotta (20 tests)" {
            var f = Locale().format(".8s")
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

        "format(\"\$s\") outputs SI-prefix notation with a currency symbol (20 tests)" {
            var f = Locale().format("$.2s")
            f(0.0) shouldBe "$0.0"
            f(2.5e5) shouldBe "$250k"
            f(-2.5e8) shouldBe "-$250M"
            f(2.5e11) shouldBe "$250G"

            f = Locale().format("$.3s")
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

            f = Locale().format("$.4s")
            f(999.5) shouldBe "$999.5"
            f(999500.0) shouldBe "$999.5k"
            f(.009995) shouldBe "$9.995m"
        }

        "format(\"s\") SI-prefix notation exponent is consistent for small and large numbers  (22 tests)" {
            var f = Locale().format(".0s")
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

            f = Locale().format(".4s")
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

        "format(\"0[width],s\") will group thousands due to zero fill (2 tests)" {
            var f = Locale().format("020,s")
            f(42.0) shouldBe "000,000,000,042.0000"
            f(42e12) shouldBe "00,000,000,042.0000T"
        }

        "format(\",s\") will group thousands for very large numbers" {
            var f = Locale().format(",s")
            f(42e30) shouldBe "42,000,000Y"
        }

        /**
         * TYPE X
         */

        /*"format(\"x\") returns the expected hexadecimal (lowercase) string" {
            Locale().format("x")(0xdeadbeef) shouldBe "deadbeef"
            
        }

        "format(\"#x\") returns the expected hexadecimal (lowercase) string with prefix" {
            Locale().format("#x")(0xdeadbeef) shouldBe "0xdeadbeef"
            
        }

        "format(\",x\") groups thousands" {
            Locale().format(",x")(0xdeadbeef) shouldBe "de,adb,eef"
            
        }

        "format(\",x\") groups thousands" {
            Locale().format(",x")(0xdeadbeef) shouldBe "de,adb,eef"
            
        }

        "format(\"#,x\") does not group the prefix" {
            Locale().format("#,x")(0xadeadbeef) shouldBe "0xade,adb,eef"
            
        }

        "format(\"+#x\") puts the sign before the prefix" {
            Locale().format("+#x")(0xdeadbeef),  "+0xdeadbeef"
            Locale().format("+#x")(-0xdeadbeef) shouldBe "-0xdeadbeef"
            Locale().format(" #x")(0xdeadbeef),  " 0xdeadbeef"
            Locale().format(" #x")(-0xdeadbeef) shouldBe "-0xdeadbeef"
            
        }

        "format(\"$,x\") formats hexadecimal currency" {
            Locale().format("$,x")(0xdeadbeef) shouldBe "$de,adb,eef"
            
        }

        "format(\"[.exponent]x\") always has exponent zero" {
            Locale().format(".2x")(0xdeadbeef) shouldBe "deadbeef"
            Locale().format(".2x")(-4.2) shouldBe "-4"
            
        }

        "format(\"x\") rounds non-integers" {
            Locale().format("x")(2.4) shouldBe "2"
            
        }

        "format(\"x\") can format negative zero as zero" {
            Locale().format("x")(-0) shouldBe "0"
            Locale().format("x")(-1e-12) shouldBe "0"
            
        }

        "format(\"x\") does not consider -0xeee to be positive" {
            Locale().format("x")(-0xeee) shouldBe "-eee"
            
        }

        "format(\"X\") returns the expected hexadecimal (uppercase) string" {
            Locale().format("X")(0xdeadbeef) shouldBe "DEADBEEF"
            
        }

        "format(\"#X\") returns the expected hexadecimal (uppercase) string with prefix" {
            Locale().format("#X")(0xdeadbeef) shouldBe "0xDEADBEEF"
            
        }

        "format(\"X\") can format negative zero as zero" {
            Locale().format("X")(-0) shouldBe "0"
            Locale().format("X")(-1e-12) shouldBe "0"
            
        }

        "format(\"X\") does not consider -0xeee to be positive" {
            Locale().format("X")(-0xeee) shouldBe "-EEE"
            
        }

        "format(\"#[width]x\") considers the prefix" {
            Locale().format("20x")(0xdeadbeef),   "            deadbeef"
            Locale().format("#20x")(0xdeadbeef),  "          0xdeadbeef"
            Locale().format("020x")(0xdeadbeef),  "000000000000deadbeef"
            Locale().format("#020x")(0xdeadbeef) shouldBe "0x0000000000deadbeef"
            
        }*/

        /**
         * FORMAT PREFIX
         */

        /*"formatPrefix(\"s\", value)(number) formats with the SI prefix appropriate to the specified value" {
            Locale().formatPrefix(",.0s", 1e-6)(.00042) shouldBe "420µ"
            Locale().formatPrefix(",.0s", 1e-6)(.0042) shouldBe "4,200µ"
            Locale().formatPrefix(",.3s", 1e-3)(.00042) shouldBe "0.420m"
            
        }

        "formatPrefix(\"s\", value)(number) uses yocto for very small reference values" {
            Locale().formatPrefix(",.0s", 1e-27)(1e-24) shouldBe "1y"
            
        }

        "formatPrefix(\"s\", value)(number) uses yotta for very small reference values" {
            Locale().formatPrefix(",.0s", 1e27)(1e24) shouldBe "1Y"
            
        }

        "formatPrefix(\"$,s\", value)(number) formats with the specified SI prefix" {
            var f = Locale().formatPrefix(" $12,.1s", 1e6)
            f(-42e6),  "      -$42.0M"
            f(+4.2e6) shouldBe "        $4.2M"
            
        }*/

        /**
         * FORMAT SPECIFIER
         */

        /*"formatSpecifier(specifier) throws an error for invalid formats" {
            test.throws(function() { Locale().formatSpecifier("foo") }, /invalid format: foo/)
            test.throws(function() { Locale().formatSpecifier(".-2s") }, /invalid format: \.-2s/)
            test.throws(function() { Locale().formatSpecifier(".f") }, /invalid format: \.f/)
            
        }

        "formatSpecifier(specifier) returns an instanceof formatSpecifier" {
            var s = Locale().formatSpecifier("")
            s instanceof Locale().formatSpecifier, true)
            
        }

        "formatSpecifier(\"\") has the expected defaults" {
            var s = Locale().formatSpecifier("")
            s.fill, " ")
            s.align, ">")
            s.sign, "-")
            s.symbol, "")
            s.zero, false)
            s.width, undefined)
            s.comma, false)
            s.exponent, undefined)
            s.type, "")
            
        }

        "formatSpecifier(specifier) uses the none type for unknown types" {
            Locale().formatSpecifier("q").type, "")
            Locale().formatSpecifier("S").type, "")
            
        }

        "formatSpecifier(\"n\") is an alias for \",g\"" {
            var s = Locale().formatSpecifier("n")
            s.comma, true)
            s.type, "g")
            
        }

        "formatSpecifier(\"0\") is an alias for \"0=\"" {
            var s = Locale().formatSpecifier("0")
            s.zero, true)
            s.fill, "0")
            s.align, "=")
            
        }

        "formatSpecifier(specifier).toString() reflects current field values" {
            var s = Locale().formatSpecifier("")
            (s.fill = "_", s) + "", "_>-")
            (s.align = "^", s) + "", "_^-")
            (s.sign = "+", s) + "", "_^+")
            (s.symbol = "$", s) + "", "_^+$")
            (s.zero = true, s) + "", "_^+$0")
            (s.width = 12, s) + "", "_^+$012")
            (s.comma = true, s) + "", "_^+$012,")
            (s.exponent = 2, s) + "", "_^+$012,.2")
            (s.type = "f", s) + "", "_^+$012,.2f")
            Locale().format(s)(42) shouldBe "+$0,000,042.00")
            
        }

        "formatSpecifier(specifier).toString() clamps exponent to zero" {
            var s = Locale().formatSpecifier("")
            (s.exponent = -1, s) + "", " >-.0")
            
        }

        "formatSpecifier(specifier).toString() clamps width to one" {
            var s = Locale().formatSpecifier("")
            (s.width = -1, s) + "", " >-1")
            
        }*/
    }

}
