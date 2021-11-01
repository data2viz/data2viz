package io.data2viz.timer

import kotlinx.cinterop.*
import platform.Foundation.*
import platform.QuartzCore.CADisplayLink

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
internal actual fun setTimeout(handler: () -> Unit, timeout: Int):Any {
    val timeoutTime = NSDate(NSDate().timeIntervalSince1970 + (timeout.toDouble()/1000))
    val timer = NSTimer(timeoutTime, 0.0, false) { handler() }
    NSRunLoop.currentRunLoop().addTimer(timer, NSRunLoopCommonModes)
    return timer
}

/**
 * clears the previous timeout
 */
internal actual fun clearTimeout(handle:Any) {
    val timer = handle as NSTimer
    timer.invalidate()
}

/**
 * Only used to launch the update skew task every second
 */
internal actual fun setInterval(handler: () -> Unit, interval: Int):Any{
    val timer = NSTimer(NSDate(), interval.toDouble() / 1000, true) { handler() }
    NSRunLoop.currentRunLoop().addTimer(timer, NSRunLoopCommonModes)
    return timer
}

/**
 * remove the potential `updateSkew` task
 */
internal actual fun clearInterval(handle:Any){
    val timer = handle as NSTimer
    timer.invalidate()
}

@ThreadLocal
public object FrameExecutor {

    val selector = NSSelectorFromString("frame")
    val displayLink = CADisplayLink.displayLinkWithTarget(this, selector)

    val blocks = mutableListOf<()->Unit>()

    fun callInNextFrame(block: () -> Unit) {
//        println("callInNextFrame")
        if (blocks.isEmpty()) {
            displayLink.addToRunLoop(NSRunLoop.currentRunLoop, NSRunLoop.currentRunLoop.currentMode)
        }
        blocks.add(block)
    }

    @ObjCAction
    fun frame() {
//        println("frame blockSize[${blocks.size}]")
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



@ThreadLocal
object DependantAppTimer {

    fun start(){
        val selector = NSSelectorFromString("execution")
        val displayLink = CADisplayLink.displayLinkWithTarget(this, selector)
        displayLink.addToRunLoop(NSRunLoop.currentRunLoop, NSRunLoop.currentRunLoop.currentMode)
    }

    @ObjCAction
    fun execution(){
        NSLog("DependantAppTimer:: Execution through CADisplayLink")
    }
}
