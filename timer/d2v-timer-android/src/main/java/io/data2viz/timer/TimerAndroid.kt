package io.data2viz.timer

import android.view.Choreographer
import java.util.concurrent.TimeUnit


internal actual fun setTimeout(handler: () -> Unit, timeout: Int): Any {
    val callback: Choreographer.FrameCallback = Choreographer.FrameCallback {handler()}
    val choreographer = Choreographer.getInstance()
    choreographer.postFrameCallbackDelayed(callback, timeout.toLong())
    println("setTimeout:: $choreographer $callback")
    return callback
}

internal actual fun clearTimeout(handle: Any) {
    val choreographer = Choreographer.getInstance()
    println("clearTimeout:: $choreographer $handle")
    choreographer.removeFrameCallback(handle as Choreographer.FrameCallback)
}

internal actual fun setInterval(handler: () -> Unit, interval: Int): Any{
    Choreographer.getInstance().postFrameCallbackDelayed(
            {time:Long ->
                handler()
                setInterval(handler, interval)
            }
            , interval.toLong())

    return true
}

internal actual fun clearInterval(handle: Any) {}

internal actual fun callInNextFrame(block: () -> Unit) {
    val choreographer = Choreographer.getInstance()
    println("callInNextFrame:: $choreographer")
    choreographer.postFrameCallback { block()}
}


internal actual fun delegateNow(): Double = TimeUnit.NANOSECONDS.toMillis(System.nanoTime()).toDouble()


