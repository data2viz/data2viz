package io.data2viz.timer

import kotlin.browser.window
import kotlin.js.Date


internal actual fun setTimeout(callback: () -> Unit, timeout: Int):Any =
    window.setTimeout(callback, timeout)

internal actual fun clearTimeout(handle:Any) {
    window.clearTimeout(handle as Int)
}

internal actual fun setInterval(block: () -> Unit, interval: Int):Any =
    window.setInterval(block, interval)

internal actual fun clearInterval(handle:Any) {
    window.clearInterval(handle as Int)
}

/**
 * Todo use timeout if not available
 */
internal actual fun callInNextFrame(block: () -> Unit) {
    window.requestAnimationFrame { block()}
} 

/**
 * Use performance.now when available
 */
internal actual fun delegateNow(): Double = (if (performanceAvailable) js("performance.now()") as Double else Date.now())

val performanceAvailable:Boolean = js("((typeof performance === 'object') && performance.now) ? true : false ") as Boolean   
