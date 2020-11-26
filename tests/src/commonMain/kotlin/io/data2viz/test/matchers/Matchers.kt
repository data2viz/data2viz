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


public interface Keyword

public object have : Keyword

public object be : Keyword

public object end : Keyword

public object start : Keyword

public object contain : Keyword

public object include : Keyword

public val epsilon: Double = 1e-6


public interface Matchers:
        StringMatchers,
        CollectionMatchers,
        DoubleMatchers,
        IntMatchers,
        LongMatchers,
        MapMatchers,
        TypeMatchers
{

    public fun fail(msg: String): Nothing = throw AssertionError(msg)

    public infix fun Double.shouldBe(other: Double): Unit = ToleranceMatcher(other, 0.0).test(this)
    public infix fun Double.shouldBeClose(other: Double): Unit = ToleranceMatcher(other, epsilon).test(this)
    public infix fun Float.shouldBeClose(other: Float): Unit = ToleranceMatcher(other.toDouble(), epsilon).test(this.toDouble())

    public infix fun Array<Int>?.shouldBe(other: Array<Int>?) {
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

    public infix fun Array<Double>.shouldBe(other: Array<Double>): Unit = this.shouldBeClose(other)
    public infix fun Array<Double>.shouldBeClose(other: Array<Double>) {
        this.size shouldBe other.size
        this.forEachIndexed { index, doubleA ->
            val doubleB = other[index]
            doubleA shouldBeClose doubleB
        }
    }

    public infix fun DoubleArray.shouldBe(other: DoubleArray) {
        this.size shouldBe other.size
        this.forEachIndexed { index, doubleA ->
            val doubleB = other[index]
            doubleA shouldBe doubleB
        }
    }
    public infix fun DoubleArray.shouldBeClose(other: DoubleArray) {
        this.size shouldBe other.size
        this.forEachIndexed { index, doubleA ->
            val doubleB = other[index]
            doubleA shouldBeClose doubleB
        }
    }

    public infix fun <N:Number, T:Number >  Iterable<N>.shouldBe(other: Iterable<T>) {
        if(this.count() != other.count())
            throw AssertionError("$this doesn't have the same size as $other" )
        this.zip(other).forEach {
            ToleranceMatcher(it.second.toDouble(), 1e-6).test(it.first.toDouble())
        }
    }
    public infix fun <T> T.shouldBe(any: T?): Unit = shouldEqual(any)
    public infix fun <T> T.shouldEqual(any: Any?) {
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

    public infix fun <T> T.should(matcher: (T) -> Unit): Unit       = matcher(this)
    public infix fun <T> T.should(matcher: Matcher<T>): Unit        = matcher.test(this)
    public infix fun <T> T.should(x: have): HaveWrapper<T>          = HaveWrapper(this)
    public infix fun <T> T.should(x: start): StartWrapper<T>        = StartWrapper(this)
    public infix fun <T> T.should(x: end): EndWrapper<T>            = EndWrapper(this)
    public infix fun <T> T.should(x: be): BeWrapper<T>              = BeWrapper(this)
    public infix fun <T> T.should(x: contain): ContainWrapper<T>    = ContainWrapper(this)
    public infix fun <T> T.should(x: include): IncludeWrapper<T>    = IncludeWrapper(this)

}

public interface Matcher<in T> {
    public fun test(value: T)
}

public class HaveWrapper<out T>(public val value: T)
public class BeWrapper<out T>(public val value: T)
public class StartWrapper<out T>(public val value: T)
public class EndWrapper<out T>(public val value: T)
public class IncludeWrapper<out T>(public val value: T)
public class ContainWrapper<out T>(public val value: T)
