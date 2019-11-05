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

interface LongMatchers {

  infix fun BeWrapper<Long>.gt(expected: Long): Unit {
    if (value <= expected)
      throw AssertionError("$value is not greater than $expected")
  }

  infix fun BeWrapper<Long>.lt(expected: Long): Unit {
    if (value >= expected)
      throw AssertionError("$value is not less than $expected")
  }

  infix fun BeWrapper<Long>.gte(expected: Long): Unit {
    if (value < expected)
      throw AssertionError("$value is not greater than or equal to $expected")
  }

  infix fun BeWrapper<Long>.lte(expected: Long): Unit {
    if (value > expected)
      throw AssertionError("$value is not less than or equal to $expected")
  }
}
