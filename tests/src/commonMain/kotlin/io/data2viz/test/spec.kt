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

package io.data2viz.test

import io.data2viz.test.matchers.Matchers


public expect fun Double.toFixed(): String

public abstract class TestBase : Matchers {


    public val regex: Regex = Regex("[-+]?(?:\\d+\\.\\d+|\\d+\\.|\\.\\d+|\\d+)(?:[eE][-]?\\d+)?")

    /**
     * Look for all doubles in the string to replace it by a rounded version
     */
    public fun String.round(): String = replace(regex = regex, transform = { it.value.toDouble().toFixed() })


    public val tests: MutableList<TestCase> = mutableListOf<TestCase>()

}

// this should live in some matchers class, but can't inline in an interface :(
public inline fun <reified T> shouldThrow(thunk: () -> Any?): T {
    val e = try {
        thunk()
        null
    } catch (e: Throwable) {
        e
    }

    val exceptionClassName = T::class.simpleName

    if (e == null)
        throw AssertionError("Expected exception ${T::class.simpleName} but no exception was thrown")
    else if (e::class.simpleName != exceptionClassName)
        throw AssertionError("Expected exception ${T::class.simpleName} but ${e::class.simpleName} was thrown")
    else
        return e as T
}


public interface ExecutionContext

public class TestCase(
    public var name: String,
    public val test: (ExecutionContext) -> Unit) {


    public fun execute(executionContext: ExecutionContext): TestResult =
            try {
                test(executionContext)
                TestResult.OK(name)
            } catch (e: AssertionError) {
                TestResult.KO(name, e.message)
            }
}

public sealed class TestResult(
    public val name: String) {

    public class OK(name: String) : TestResult(name)
    public class KO(name: String,
                    public val message: String?) : TestResult(name)
}


public expect annotation class JsName(val name:String)