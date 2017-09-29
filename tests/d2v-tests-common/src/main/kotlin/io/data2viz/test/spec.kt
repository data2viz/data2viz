package io.data2viz.test

import io.data2viz.test.matchers.Matchers


expect fun Double.toFixed():String

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
    class OK(name: String): TestResult(name)
    class KO(name: String, val message: String?): TestResult(name)
}
