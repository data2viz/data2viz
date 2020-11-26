/*
 * Copyright (c) 2018-2019. data2viz sàrl.
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

package io.data2viz.test.matchers

import kotlin.math.abs


public interface DoubleMatchers {
  public infix fun Double.plusOrMinus(tolerance: Double): ToleranceMatcher = ToleranceMatcher(this, tolerance)

  public fun exactly(d: Double): Matcher<Double> = object : Matcher<Double> {
    override fun test(value: Double) {
      if (value != d)
        throw AssertionError("$value is not equal to expected value $d")
    }
  }
}

public class ToleranceMatcher(
    public val expected: Double,
    public val tolerance: Double) : Matcher<Double> {

  override fun test(value: Double) {
    if (tolerance == 0.0 && (!expected.isNaN() || !value.isNaN()))
      println("[WARN] When comparing doubles ($expected, $value) consider using tolerance, eg: a shouldBe b plusOrMinus c ")
    if (value.isNaN() && !expected.isNaN()) throw AssertionError("$value is not equal to $expected")
    val diff = abs(value - expected)
    if (diff > tolerance)
      throw AssertionError("$value is not equal to $expected")
  }

  public infix fun plusOrMinus(tolerance: Double): ToleranceMatcher = ToleranceMatcher(expected, tolerance)
}
