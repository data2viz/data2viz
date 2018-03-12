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
}
