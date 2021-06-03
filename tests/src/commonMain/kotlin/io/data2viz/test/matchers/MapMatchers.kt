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

interface MapMatchers {

  fun <K> haveKey(key: K): Matcher<Map<K, *>> = object : Matcher<Map<K, *>> {
    override fun test(value: Map<K, *>) {
      if (!value.containsKey(key))
        throw AssertionError("Map did not contain key $key")
    }
  }

  fun <V> haveValue(v: V): Matcher<Map<*, V>> = object : Matcher<Map<*, V>> {
    override fun test(value: Map<*, V>) {
      if (!value.containsValue(v))
        throw AssertionError("Map did not contain value $v")
    }
  }

  fun <K, V> contain(key: K, v: V): Matcher<Map<K, V>> = object : Matcher<Map<K, V>> {
    override fun test(value: Map<K, V>) {
      if (value.get(key) != v)
        throw AssertionError("Map did not contain mapping $key=$value")
    }
  }
}
