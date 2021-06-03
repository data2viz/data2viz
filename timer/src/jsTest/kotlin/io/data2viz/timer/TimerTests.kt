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

package io.data2viz.timer

import io.data2viz.test.TestBase
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.promise
import kotlin.test.Ignore
import kotlin.test.Test

@Suppress("unused")
class TimerTests : TestBase() {

    @Test
    fun keepOneTest() {
        true shouldBe true
    }

    @Test @Ignore
    fun timerStop() = GlobalScope.promise {
        var count = 0
        timer {
            if (++count == 2) {
                stop()
            }
        }
        delay(50)
        count shouldBe 2
    }

    @Test @Ignore
    @JsName("callbackMeanTime")
    fun `timer(callback) invokes the callback about every 17ms`() = GlobalScope.promise {
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

    @Test @Ignore
    @JsName("callbackDelayed")
    fun `timer(callback, delay) first invokes the callback after the specified delay`() = GlobalScope.promise {
        val then = now()
        val delay = 100.0
        timer(delay = delay) {
            stop()
            val elapsed = now() - then
            elapsed shouldBe (delay plusOrMinus 10.0)

        }
        delay(10L + delay.toInt())
    }

    @Test @Ignore
    @JsName("elapsedRelativeToDelay")
    fun `computes the elapsed time relative to the delay`() = GlobalScope.promise {
        val delay = 100.0
        timer(delay = delay) { elapsed ->
            stop()
            elapsed shouldBe (.0 plusOrMinus 10.0)
        }
        delay(delay.toLong() + 10)
    }

    @Test @Ignore
    @JsName("elapsedRelativeToDelayAndTime")
    fun `timer(callback, delay, time) computes the effective delay relative to the specified time`() = GlobalScope.promise {
        val delay = 100.0
        val skew = 200.0
        timer(delay = delay, startTime = now() - skew) { elapsed ->
            stop()
            elapsed shouldBe (skew - delay plusOrMinus 10.0)

        }
        delay(delay.toLong() + 10)
    }

    @Test @Ignore
    @JsName("flushTimers")
    fun `timer(callback) invokes callbacks in scheduling order during synchronous flush`() {
        val results = mutableListOf<Int>()
        timer { results.add(1); stop() }
        timer { results.add(2); stop() }
        timer { results.add(3); stop() }
        timerFlush()
        results shouldBe listOf(1, 2, 3)
    }

    @Test @Ignore
    @JsName("flushTimersAsync")
    fun `timer(callback) invokes callbacks in scheduling order during asynchronous flush`() = GlobalScope.promise {
        val results = mutableListOf<Int>()
        timer { results.add(1); stop() }
        timer { results.add(2); stop() }
        timer { results.add(3); stop() }
        timer {
            stop()
            results shouldBe listOf(1, 2, 3)
        }
        delay(50)
    }

    @Test @Ignore
    @JsName("flushTimersAsyncWithDelay")
//    @Ignore
    fun `timer(callback, delay) invokes callbacks in scheduling order during asynchronous flush`() = GlobalScope.promise {
        val results = mutableListOf<Int>()
        timer { results.add(1); stop() }
        timer { results.add(2); stop() }
        timer { results.add(3); stop() }
        timer {
            stop()
            println(results)
            results shouldBe listOf(1, 2, 3)
        }
        delay(50)
    }

    @Test @Ignore
    @JsName("timerWithinAFrame")
    fun `timer(callback) within a frame invokes the callback at the end of the same frame`() = GlobalScope.promise {
        timer {
            timer { elapsed2 ->
                stop()
                elapsed2 shouldBeClose 0.0
            }
            stop()
        }
        delay(30)
    }

}
