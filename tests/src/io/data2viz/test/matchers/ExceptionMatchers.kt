package io.data2viz.test.matchers

import kotlin.reflect.KClass

interface ExceptionMatchers {

  @Deprecated("Use shouldThrow<ExceptionType> { }", level = DeprecationLevel.WARNING)
  fun expecting(kclass: KClass<*>, thunk: () -> Any): Unit {
    val exception = try {
      thunk()
      null
    } catch (exception: Exception) {
      exception
    }
    if (exception == null)
      throw AssertionError("Expected exception $kclass but no exception was thrown")
    else if (exception::class.js != kclass.js) {
      throw AssertionError("Expected exception $kclass but $exception was thrown")
    }
  }
}
