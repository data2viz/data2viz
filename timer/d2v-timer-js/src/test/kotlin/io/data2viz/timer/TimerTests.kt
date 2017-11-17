package io.data2viz.timer

import io.data2viz.test.TestBase
import kotlin.browser.window
import kotlin.test.Test
import kotlin.test.assertTrue


class TimerTests: TestBase() {

    @Test
    fun timerStop(){
        var count = 0
        window.setTimeout({ count shouldBe 2}, 50)

        timer { ti ->
            println("timer execution:: ${ti.toInt()} ms")
            if (++count == 2){
                stop()
                println("stopped")
            }
        }
    }
}
