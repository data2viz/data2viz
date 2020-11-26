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

public interface StringMatchers {

  public infix fun HaveWrapper<String>.substring(substr: String): Unit {
    if (!value.contains(substr))
      throw AssertionError("String does not have substring $substr")
  }

  public infix fun StartWrapper<String>.with(prefix: String): Unit {
    if (!value.startsWith(prefix))
      throw AssertionError("String does not start with $prefix but with ${value.take(prefix.length)}")
  }

  public infix fun EndWrapper<String>.with(suffix: String): Unit {
    if (!value.endsWith(suffix))
      throw AssertionError("String does not end with $suffix but with ${value.takeLast(suffix.length)}")
  }

  public fun startWith(prefix: String): Matcher<String> = object : Matcher<String> {
    override fun test(value: String) {
      if (!value.startsWith(prefix))
        throw AssertionError("String $value does not start with $prefix")
    }
  }

  public fun endWith(suffix: String): Matcher<String> = object : Matcher<String> {
    override fun test(value: String) {
      if (!value.endsWith(suffix))
        throw AssertionError("String $value does not end with with $suffix")
    }
  }

  public fun match(regex: String): Matcher<String> = object : Matcher<String> {
    override fun test(value: String) {
      if (!value.matches(regex.toRegex()))
        throw AssertionError("String $value does not match regex $regex")
    }
  }

  public fun haveLength(length: Int): Matcher<String> = object : Matcher<String> {
    override fun test(value: String) {
      if (value.length != length)
        throw AssertionError("String $value does not have length $length")
    }
  }
}
