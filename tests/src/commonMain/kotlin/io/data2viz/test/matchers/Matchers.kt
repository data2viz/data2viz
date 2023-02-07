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


interface Keyword

object have : Keyword

object be : Keyword

object end : Keyword

object start : Keyword

object contain : Keyword

object include : Keyword

val epsilon = 1e-6


interface Matchers : StringMatchers,
        CollectionMatchers,
        DoubleMatchers,
        IntMatchers,
        LongMatchers,
        MapMatchers,
        TypeMatchers {

    fun fail(msg: String): Nothing = throw AssertionError(msg)

    infix fun Double.shouldBe(other: Double): Unit = ToleranceMatcher(other, 0.0).test(this)
    infix fun Double.shouldBeClose(other: Double): Unit = ToleranceMatcher(other, epsilon).test(this)
    infix fun Float.shouldBeClose(other: Float): Unit = ToleranceMatcher(other.toDouble(), epsilon).test(this.toDouble())

    infix fun Array<Int>?.shouldBe(other: Array<Int>?) {
        if (this == null) {
            if (other != null) throw AssertionError("$this did not equal $other")
            return
        }
        if (other == null) throw AssertionError("$this did not equal $other")

        this.size shouldBe other.size
        this.forEachIndexed { index, doubleA ->
            val doubleB = other[index]
            doubleA shouldBe doubleB
        }
    }

    infix fun Array<Double>.shouldBe(other: Array<Double>) = this.shouldBeClose(other)
    infix fun Array<Double>.shouldBeClose(other: Array<Double>) {
        this.size shouldBe other.size
        this.forEachIndexed { index, doubleA ->
            val doubleB = other[index]
            doubleA shouldBeClose doubleB
        }
    }

    infix fun DoubleArray.shouldBe(other: DoubleArray) {
        this.size shouldBe other.size
        this.forEachIndexed { index, doubleA ->
            val doubleB = other[index]
            doubleA shouldBe doubleB
        }
    }
    infix fun DoubleArray.shouldBeClose(other: DoubleArray) {
        this.size shouldBe other.size
        this.forEachIndexed { index, doubleA ->
            val doubleB = other[index]
            doubleA shouldBeClose doubleB
        }
    }

    infix fun <N:Number, T:Number >  Iterable<N>.shouldBe(other: Iterable<T>) {
        if(this.count() != other.count())
            throw AssertionError("$this doesn't have the same size as $other" )
        this.zip(other).forEach {
            ToleranceMatcher(it.second.toDouble(), 1e-6).test(it.first.toDouble())
        }
    }
    infix fun <T> T.shouldBe(any: T?): Unit = shouldEqual(any)
    infix fun <T> T.shouldEqual(any: Any?) {
        if (this == any) return
        @Suppress("UNCHECKED_CAST")
        when (any) {
            is Matcher<*> -> (any as Matcher<T>).test(this)
            else -> {
                if (this == null && any != null)
                    throw AssertionError("$this did not equal $any")
                if (this != null && any == null)
                    throw AssertionError("$this did not equal $any")
                if (this != any)
                    throw AssertionError("$this did not equal $any")
            }
        }
    }

    infix fun <T> T.should(matcher: (T) -> Unit): Unit = matcher(this)
    infix fun <T> T.should(matcher: Matcher<T>) = matcher.test(this)
    infix fun <T> T.should(x: have): HaveWrapper<T> = HaveWrapper(this)
    infix fun <T> T.should(x: start): StartWrapper<T> = StartWrapper(this)
    infix fun <T> T.should(x: end): EndWrapper<T> = EndWrapper(this)
    infix fun <T> T.should(x: be): BeWrapper<T> = BeWrapper(this)
    infix fun <T> T.should(x: contain): ContainWrapper<T> = ContainWrapper(this)
    infix fun <T> T.should(x: include): IncludeWrapper<T> = IncludeWrapper(this)

}

interface Matcher<in T> {
    fun test(value: T)
}

class HaveWrapper<out T>(val value: T)
class BeWrapper<out T>(val value: T)
class StartWrapper<out T>(val value: T)
class EndWrapper<out T>(val value: T)
class IncludeWrapper<out T>(val value: T)
class ContainWrapper<out T>(val value: T)
