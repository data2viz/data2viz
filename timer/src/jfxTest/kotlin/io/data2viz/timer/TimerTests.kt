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

package io.data2viz.timer

import io.data2viz.test.TestBase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.javafx.JavaFx
import kotlinx.coroutines.runBlocking
import kotlin.test.Ignore
import kotlin.test.Test

@Suppress("unused")
@Ignore("Github actions")
class TimerTests : TestBase() {

    @Test
    fun timerStop() = runBlocking {
        var count = 0
        timer {
            if (++count == 2) {
                stop()
            }
        }
        delay(50)
        count shouldBe 2
    }

    @Test
    fun `timer(callback) invokes the callback about every 17ms`() = runBlocking {
        val then = now()
        var elapsedTime = 0.0
        var count = 0
        timer {
            if (count++ > 10) {
                stop()
                elapsedTime = now() - then
            }
        }
        delay(230)
        elapsedTime shouldBe (17.0 * count plusOrMinus 5.0 * count)
    }

    @Test
    fun `timer(callback, delay) first invokes the callback after the specified delay`() = runBlocking {
        val then = now()
        val delay = 100.0
        timer(delay = delay) {
            stop()
            val elapsed = now() - then
            elapsed shouldBe (delay plusOrMinus 20.0)

        }
        delay(110L)
    }

    @Test
    fun `computes the elapsed time relative to the delay`() = runBlocking(Dispatchers.JavaFx) {
        val delay = 100.0
        timer(delay = delay) { elapsed ->
            stop()
            println("elapsed $elapsed")
            elapsed shouldBe (.0 plusOrMinus 10.0)
        }
        delay(delay.toLong() + 10L)
    }

    @Test
    fun `timer(callback, delay, time) computes the effective delay relative to the specified time`() =
        runBlocking(Dispatchers.JavaFx) {
            val delay = 100.0
            val skew = 200.0
            timer(delay = delay, startTime = now() - skew) { elapsed ->
                stop()
                elapsed shouldBe (skew - delay plusOrMinus 10.0)

            }
            delay(delay.toLong() + 10)
        }

    @Test
    fun `timer(callback) invokes callbacks in scheduling order during synchronous flush`() {
        val results = mutableListOf<Int>()
        timer { results.add(1); stop() }
        timer { results.add(2); stop() }
        timer { results.add(3); stop() }
        timerFlush()
        results shouldBe listOf(1, 2, 3)
    }

    @Test
    fun `timer(callback) invokes callbacks in scheduling order during asynchronous flush`() =
        runBlocking(Dispatchers.JavaFx) {

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

    @Test
    fun `timer(callback, delay) invokes callbacks in scheduling order during asynchronous flush`() =
        runBlocking(Dispatchers.JavaFx) {
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

    @Test
    fun `timer(callback) within a frame invokes the callback at the end of the same frame`() =
        runBlocking(Dispatchers.JavaFx) {
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
