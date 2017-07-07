package io.data2viz.format

import io.data2viz.test.StringSpec

class FormatTests : StringSpec() {
    init {

        "Default Format" {
            val specifier: FormatSpecifier = FormatSpecifier()

            val locale = Locale()
            val formatter = locale.format(specifier)

            formatter(123456.0) shouldBe "123456.000000"
        }

        "Default Format Precision 6" {
            val specifier: FormatSpecifier = FormatSpecifier(precision = 6)

            val locale = Locale()
            val formatter = locale.format(specifier)

            formatter(1234.0) shouldBe "1234.00"
        }

        "Default Format Precision 4" {
            val specifier: FormatSpecifier = FormatSpecifier(precision = 4)

            val locale = Locale()
            val formatter = locale.format(specifier)

            formatter(1234.0) shouldBe "1234"
        }

        "Default Format Precision 3" {
            val specifier: FormatSpecifier = FormatSpecifier(precision = 3)

            val locale = Locale()
            val formatter = locale.format(specifier)

            formatter(1234.0) shouldBe "1.23e+3"
        }

        "Default Format (force plus sign)" {
            val specifier: FormatSpecifier = FormatSpecifier(sign = '+')

            val locale = Locale()
            val formatter = locale.format(specifier)

            formatter(123456.0) shouldBe "+123456.000000"
        }

        "Default Format (check minus sign)" {
            val specifier: FormatSpecifier = FormatSpecifier()

            val locale = Locale()
            val formatter = locale.format(specifier)

            formatter(-123456.0) shouldBe "-123456.000000"
        }

        "Default currency Format" {
            val specifier: FormatSpecifier = FormatSpecifier(symbol = "$")

            val locale = Locale()
            val formatter = locale.format(specifier)

            formatter(123456.0) shouldBe "123456.000000 €"
        }
    }

}
