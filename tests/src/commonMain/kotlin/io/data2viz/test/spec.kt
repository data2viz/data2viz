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

package io.data2viz.test

import io.data2viz.test.matchers.Matchers


expect fun Double.toFixed(): String

abstract class TestBase : Matchers {


    val regex = Regex("[-+]?(?:\\d+\\.\\d+|\\d+\\.|\\.\\d+|\\d+)(?:[eE][-]?\\d+)?")

    /**
     * Look for all doubles in the string to replace it by a rounded version
     */
    fun String.round() = replace(regex = regex, transform = { it.value.toDouble().toFixed() })

    val tests = mutableListOf<TestCase>()

}

// this should live in some matchers class, but can't inline in an interface :(
inline fun <reified T> shouldThrow(thunk: () -> Any?): T {
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


interface ExecutionContext

class TestCase(var name: String, val test: (ExecutionContext) -> Unit) {
    fun execute(executionContext: ExecutionContext) =
            try {
                test(executionContext)
                TestResult.OK(name)
            } catch (e: AssertionError) {
                TestResult.KO(name, e.message)
            }
}

sealed class TestResult(val name: String) {
    class OK(name: String) : TestResult(name)
    class KO(name: String, val message: String?) : TestResult(name)
}
