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

import kotlinx.browser.window
import kotlin.js.Date


internal actual fun setTimeout(handler: () -> Unit, timeout: Int):Any =
    window.setTimeout(handler, timeout)

internal actual fun clearTimeout(handle:Any) {
    window.clearTimeout(handle as Int)
}

internal actual fun setInterval(handler: () -> Unit, interval: Int):Any =
    window.setInterval(handler, interval)

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
