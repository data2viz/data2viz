package io.data2viz.timer

import kotlinx.cinterop.*
import platform.Foundation.*
import platform.QuartzCore.CADisplayLink
import kotlin.native.concurrent.ThreadLocal

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




/**
 * used to make the timer sleep until next event.
 * Always called with timeout > 24 ms for a future Timer
 *
 * There can be only one pending timeout handler.
 */
internal actual fun setTimeout(
    handler: () -> Unit,
    timeout: Int
): Any = NSTimer(
    fireDate = NSDate(NSDate().timeIntervalSince1970 + (timeout.toDouble() / 1000)),
    interval = 0.0,
    repeats = false
) { handler() }.also { NSRunLoop.currentRunLoop().addTimer(it, NSRunLoopCommonModes) }

/**
 * clears the previous timeout
 */
internal actual fun clearTimeout(handle: Any) {
    val timer = handle as NSTimer
    timer.invalidate()
}

/**
 * Only used to launch the update skew task every second
 */
internal actual fun setInterval(
    handler: () -> Unit,
    interval: Int
): Any = NSTimer(
    fireDate = NSDate(),
    interval = interval.toDouble() / 1000,
    repeats = true
) { handler() }.also { NSRunLoop.currentRunLoop().addTimer(it, NSRunLoopCommonModes) }

/**
 * remove the potential `updateSkew` task
 */
internal actual fun clearInterval(handle:Any) {
    val timer = handle as NSTimer
    timer.invalidate()
}

/**
 * This class is responsible for the execution in the next frame.
 * It's a technical class that shouldn't be use directly. It has to
 * be public in order to called by the iOS framework.
 */
@ExportObjCClass
public object FrameExecutor {

    private val selector = NSSelectorFromString("frame")
    private val displayLink = CADisplayLink.displayLinkWithTarget(this, selector)

    private val blocks = mutableListOf<() -> Unit>()

    internal fun callInNextFrame(block: () -> Unit) {
        if (blocks.isEmpty()) {
            displayLink.addToRunLoop(NSRunLoop.currentRunLoop, NSRunLoop.currentRunLoop.currentMode)
        }
        blocks.add(block)
    }

    /**
     * The function that will be call repeatedly through CADisplayLink
     */
    @ObjCAction
    public fun frame() {
        val currBlocks = blocks.toTypedArray()
        blocks.clear()
        displayLink.removeFromRunLoop(NSRunLoop.currentRunLoop, NSRunLoop.currentRunLoop.currentMode)
        currBlocks.forEach {
            it()
        }

    }

}

/**
 * Used to call clearNow() and wake()
 */
internal actual fun callInNextFrame(block: () -> Unit) {
    FrameExecutor.callInNextFrame(block)
}


/**
 * Platform implementation of now in ms.
 */
internal actual fun delegateNow(): Double = NSDate().timeIntervalSince1970 * 1000.0

