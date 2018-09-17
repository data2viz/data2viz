package io.data2viz.timer

import android.view.Choreographer
import java.util.concurrent.TimeUnit
import io.data2viz.logging.KotlinLogging

private val log = KotlinLogging.logger{}

val choreographer:Choreographer by lazy { Choreographer.getInstance() }

internal actual fun setTimeout(handler: () -> Unit, timeout: Int): Any {
    val callback = Choreographer.FrameCallback {handler()}
    choreographer.postFrameCallbackDelayed(callback, timeout.toLong())
    log.debug{"setTimeout:: $timeout ms, $callback"}
    return callback
}

internal actual fun clearTimeout(handle: Any) {
    log.debug { "clearTimeout:: $handle"}
    choreographer.removeFrameCallback(handle as Choreographer.FrameCallback)
}

internal actual fun setInterval(handler: () -> Unit, interval: Int): Any {
    val callback = IntervalCallBack(interval, handler)
    log.debug {"setInterval:: $interval $callback"}
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
    log.debug { "clearInterval:: $handle" }
    choreographer.removeFrameCallback(handle as Choreographer.FrameCallback)
}

internal actual fun callInNextFrame(block: () -> Unit) {
    choreographer.postFrameCallback { block()}
}

internal actual fun delegateNow(): Double = TimeUnit.NANOSECONDS.toMillis(System.nanoTime()).toDouble()


