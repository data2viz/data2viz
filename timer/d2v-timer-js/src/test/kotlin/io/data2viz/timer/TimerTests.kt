package io.data2viz.timer

import io.data2viz.test.TestBase
import kotlinx.coroutines.experimental.delay
import kotlinx.coroutines.experimental.promise
import kotlin.test.Ignore
import kotlin.test.Test

class TimerTests : TestBase() {

    @Test
    fun timerStop() = promise {
        var count = 0
        timer { ti ->
            println("timer execution:: ${ti.toInt()} ms")
            if (++count == 2) {
                stop()
                println("stopped")
            }
        }
        delay(50)
        count shouldBe 2
    }

    @Test
    @JsName("callbackMeanTime")
    fun `timer(callback) invokes the callback about every 17ms`() = promise {
        val then = now()
        var elapsedTime = 0.0
        var count = 0
        timer {
            if (count++ > 10) {
                stop()
                elapsedTime = now() - then
            }
        }
        delay(200)
        elapsedTime shouldBe (17.0 * count plusOrMinus 5.0 * count)
    }

    @Test
    @JsName("callbackDelayed")
    fun `timer(callback, delay) first invokes the callback after the specified delay`() = promise {
        val then = now()
        val delay = 100.0
        timer(delay = delay) {
            stop()
            val elapsed = now() - then
            elapsed shouldBe (delay plusOrMinus 10.0)

        }
        delay(10 + delay.toInt())
    }

    @Test
    @JsName("elapsedRelativeToDelay")
    fun `computes the elapsed time relative to the delay`() = promise {
        val then = now()
        val delay = 100.0
        timer(delay = delay) { elapsed ->
            stop()
            elapsed shouldBe (.0 plusOrMinus 10.0)

        }
        delay(delay.toInt() + 10)
    }

    @Test
    @JsName("elapsedRelativeToDelayAndTime")
    fun `timer(callback, delay, time) computes the effective delay relative to the specified time`() = promise {
        val delay = 100.0
        val skew = 200.0
        timer(delay = delay, startTime = now() - skew) { elapsed ->
            stop()
            elapsed shouldBe (skew - delay plusOrMinus 10.0)

        }
        delay(delay.toInt() + 10)
    }

    @Test
    @JsName("flushTimers")
    fun `timer(callback) invokes callbacks in scheduling order during synchronous flush`() {
        val results = mutableListOf<Int>()
        timer { results.add(1); stop() }
        timer { results.add(2); stop() }
        timer { results.add(3); stop() }
        timerFlush()
        results shouldBe listOf(1, 2, 3)
    }

    @Test
    @JsName("flushTimersAsync")
    fun `timer(callback) invokes callbacks in scheduling order during asynchronous flush`() = promise {
        val results = mutableListOf<Int>()
        timer { results.add(1); stop() }
        timer { results.add(2); stop() }
        timer { results.add(3); stop() }
        timer {
            stop()
            results shouldBe listOf(1, 2, 3)
        }
        delay(10)
    }


    @Test
    @JsName("flushTimersAsyncWithDelay")
    @Ignore
    fun `timer(callback, delay) invokes callbacks in scheduling order during asynchronous flush`() = promise {
        val then = now()
        val results = mutableListOf<Int>()
        timer(delay = 60.0,  startTime = then) { results.add(1); stop() }
        timer(delay = 40.0,  startTime = then) { results.add(2); stop() }
        timer(delay = 80.0,  startTime = then) { results.add(3); stop() }
        timer(delay = 100.0, startTime = then) {
            stop()
            println(results)
            results shouldBe listOf(1, 2, 3)
        }
        delay(120)
    }


}

