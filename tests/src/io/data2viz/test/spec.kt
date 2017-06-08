package io.data2viz.test

import io.data2viz.test.matchers.Matchers


abstract class TestBase : Matchers {
    val tests = mutableListOf<TestCase>()

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
}

open class StringSpec : TestBase() {

    operator fun String.invoke(test: (ExecutionContext) -> Unit): TestCase {
        val tc = TestCase(name = this, test = test)
        tests.add(tc)
        return tc
    }
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
