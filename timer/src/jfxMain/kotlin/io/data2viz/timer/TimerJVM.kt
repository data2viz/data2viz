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

import com.sun.javafx.application.PlatformImpl
import javafx.animation.Animation
import javafx.animation.AnimationTimer
import javafx.animation.KeyFrame
import javafx.animation.Timeline
import javafx.event.EventHandler
import javafx.util.Duration
import java.util.concurrent.CopyOnWriteArrayList
import java.util.concurrent.TimeUnit


internal actual fun setTimeout(handler: () -> Unit, timeout: Int): Any = Timeline(KeyFrame(Duration.millis(timeout.toDouble()), EventHandler {
    handler()
})).apply {
    cycleCount = 1
    play()
}


internal actual fun clearTimeout(handle: Any) {
    (handle as Timeline).stop()
}

internal actual fun setInterval(handler: () -> Unit, interval: Int): Any = Timeline(KeyFrame(Duration.millis(interval.toDouble()), EventHandler {
    handler()
})).apply {
    cycleCount = Animation.INDEFINITE
    play()
}

internal actual fun clearInterval(handle: Any) {
    (handle as Timeline).stop()
}


internal val fxTimer: JavaFxTimer by lazy { JavaFxTimer().apply { start() } }

internal actual fun callInNextFrame(block: () -> Unit) {
    fxTimer.callInNextFrame(block)
}
internal actual fun delegateNow(): Double = TimeUnit.NANOSECONDS.toMillis(System.nanoTime()).toDouble()


internal class JavaFxTimer : AnimationTimer() {

    init {
        PlatformImpl.startup {}
    }

    private val blocks = CopyOnWriteArrayList<() -> Unit>()

    override fun handle(now: Long) {
//        println("handle ${blocks.size} blocks")
        val current = blocks.toTypedArray()
        blocks.clear()
        for (block in current){
            block()
        }
    }

    internal fun callInNextFrame(block: () -> Unit) {
        blocks += block
    }

}

