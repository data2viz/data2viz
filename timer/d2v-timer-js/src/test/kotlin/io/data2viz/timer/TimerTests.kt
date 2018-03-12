package io.data2viz.timer

import io.data2viz.test.TestBase
import kotlinx.coroutines.experimental.delay
import kotlinx.coroutines.experimental.promise
import kotlin.browser.window
import kotlin.test.Test
import kotlin.test.assertTrue

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
        println("ElapsedTime:: $elapsedTime")
        elapsedTime shouldBe (17.0 * count plusOrMinus 5.0 * count)
    }
    

}

