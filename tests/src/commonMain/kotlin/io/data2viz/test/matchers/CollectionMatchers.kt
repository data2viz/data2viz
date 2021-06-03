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

package io.data2viz.test.matchers

interface CollectionMatchers {

  fun beEmpty(): (Collection<*>) -> Unit {
    return { value ->
      if (value.isNotEmpty())
        throw AssertionError("Collection was expected to be empty but has size ${value.size}")
    }
  }

  infix fun HaveWrapper<Collection<*>>.size(expected: Int): Unit {
    val size = value.size
    if (size != expected)
      throw AssertionError("Collection was expected to have size $expected but had size $size")
  }

  infix fun <T> ContainWrapper<Collection<T>>.element(expected: T): Unit {
    if (!value.contains(expected))
      throw AssertionError("Collection did not have expected element $expected")
  }

  fun <T> containInAnyOrder(vararg ts: T): Matcher<Collection<T>> = object : Matcher<Collection<T>> {
    override fun test(value: Collection<T>) {
      for (t in ts) {
        if (!value.contains(t))
          throw AssertionError("Collection did not contain value $t")
      }
    }
  }

  fun <T> haveSize(size: Int): Matcher<Collection<T>> = object : Matcher<Collection<T>> {
    override fun test(value: Collection<T>) {
      if (value.size != size)
        throw AssertionError("Collection did not have size $size")
    }
  }

  fun <T> contain(t: T): Matcher<Collection<T>> = object : Matcher<Collection<T>> {
    override fun test(value: Collection<T>) {
      if (!value.contains(t))
        throw AssertionError("Collection did not contain element $t")
    }
  }
}
