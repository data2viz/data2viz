package io.data2viz.test.matchers

import kotlin.math.abs


interface DoubleMatchers {
  infix fun Double.plusOrMinus(tolerance: Double): ToleranceMatcher = ToleranceMatcher(this, tolerance)

  fun exactly(d: Double): Matcher<Double> = object : Matcher<Double> {
    override fun test(value: Double) {
      if (value != d)
        throw AssertionError("$value is not equal to expected value $d")
    }
  }
}

class ToleranceMatcher(val expected: Double, val tolerance: Double) : Matcher<Double> {

  override fun test(value: Double) {
    if (tolerance == 0.0 && (!expected.isNaN() || !value.isNaN()))
      println("[WARN] When comparing doubles ($expected, $value) consider using tolerance, eg: a shouldBe b plusOrMinus c ")
    if (value.isNaN() && !expected.isNaN()) throw AssertionError("$value is not equal to $expected")
    val diff = abs(value - expected)
    if (diff > tolerance)
      throw AssertionError("$value is not equal to $expected")
  }

  infix fun plusOrMinus(tolerance: Double): ToleranceMatcher = ToleranceMatcher(expected, tolerance)
}
