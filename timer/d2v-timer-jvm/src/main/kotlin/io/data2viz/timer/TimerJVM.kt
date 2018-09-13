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


internal actual fun setTimeout(callback: () -> Unit, timeout: Int): Any = Timeline(KeyFrame(Duration.millis(timeout.toDouble()), EventHandler {
    callback()
})).apply {
    cycleCount = 1
    play()
}


internal actual fun clearTimeout(handle: Any) {
    (handle as Timeline).stop()
}

internal actual fun setInterval(block: () -> Unit, interval: Int): Any = Timeline(KeyFrame(Duration.millis(interval.toDouble()), EventHandler {
    block()
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

}

