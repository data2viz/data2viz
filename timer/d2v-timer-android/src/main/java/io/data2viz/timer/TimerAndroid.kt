package io.data2viz.timer

import kotlinx.coroutines.experimental.Job
import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.delay
import kotlinx.coroutines.experimental.launch
import java.util.concurrent.TimeUnit


internal actual fun setTimeout(handler: () -> Unit, timeout: Int): Any =
    launch(UI) {
        delay(timeout)
        handler()
    }

internal actual fun clearTimeout(handle: Any) {
    (handle as Job).cancel()
}

internal actual fun setInterval(handler: () -> Unit, interval: Int): Any =  launch(UI) {
    while (true) {
        delay(interval)
        handler()
    }
}
internal actual fun clearInterval(handle: Any) {
    (handle as Job).cancel()
}

internal actual fun callInNextFrame(block: () -> Unit) {
    launch(UI) {
        block()
    }
}
internal actual fun delegateNow(): Double = TimeUnit.NANOSECONDS.toMillis(System.nanoTime()).toDouble()


