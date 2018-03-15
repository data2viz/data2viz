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


internal actual fun setTimeout(handler: () -> Unit, timeout: Int): Any =    timer.setTimeout   (handler, timeout)
internal actual fun clearTimeout(handle: Any)                             { timer.clearTimeout (handle)}
internal actual fun setInterval(handler: () -> Unit, interval: Int): Any =  timer.setInterval  (handler, interval)
internal actual fun clearInterval(handle: Any)                            { timer.clearInterval(handle) }

internal actual fun callInNextFrame(block: () -> Unit)                    { timer.callInNextFrame(block) }
internal actual fun delegateNow(): Double = TimeUnit.NANOSECONDS.toMillis(System.nanoTime()).toDouble()

val timer: JavaFxTimer by lazy { JavaFxTimer().apply { start() } }

class JavaFxTimer : AnimationTimer() {

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

    fun callInNextFrame(block: () -> Unit) {
        blocks += block
    }

    fun setInterval(block: () -> Unit, interval: Int): Any =
        Timeline(KeyFrame(Duration.millis(interval.toDouble()), EventHandler {
            block()
        })).apply {
            cycleCount = Animation.INDEFINITE
            play() 
        }

    fun clearInterval(timeline: Any) { (timeline as Timeline).stop() }


    fun setTimeout(block: () -> Unit, timeout: Int): Any =
        Timeline(KeyFrame(Duration.millis(timeout.toDouble()), EventHandler {
            block()
        })).apply {
            cycleCount = 1
            play() 
        }


    fun clearTimeout(timeline: Any) { (timeline as Timeline).stop() }

}
