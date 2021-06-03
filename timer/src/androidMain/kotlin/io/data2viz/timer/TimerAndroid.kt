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

import android.view.Choreographer
import java.util.concurrent.TimeUnit


val choreographer:Choreographer by lazy { Choreographer.getInstance() }

internal actual fun setTimeout(handler: () -> Unit, timeout: Int): Any {
    val callback = Choreographer.FrameCallback {handler()}
    choreographer.postFrameCallbackDelayed(callback, timeout.toLong())
    return callback
}

internal actual fun clearTimeout(handle: Any) {
    choreographer.removeFrameCallback(handle as Choreographer.FrameCallback)
}

internal actual fun setInterval(handler: () -> Unit, interval: Int): Any {
    val callback = IntervalCallBack(interval, handler)
    intervalWithCB(callback, interval)
    return callback
}

class IntervalCallBack(var delay: Int, val block: () -> Unit): Choreographer.FrameCallback {
    override fun doFrame(p0: Long) {
        block()
        intervalWithCB(this, delay)
    }
}

private fun intervalWithCB(frameCallback: Choreographer.FrameCallback, interval: Int){
    choreographer.postFrameCallbackDelayed(frameCallback, interval.toLong())

}

internal actual fun clearInterval(handle: Any) {
    choreographer.removeFrameCallback(handle as Choreographer.FrameCallback)
}

internal actual fun callInNextFrame(block: () -> Unit) {
    choreographer.postFrameCallback { block()}
}

internal actual fun delegateNow(): Double = TimeUnit.NANOSECONDS.toMillis(System.nanoTime()).toDouble()

