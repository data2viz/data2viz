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

package io.data2viz.transition


import io.data2viz.ease.ease
import io.data2viz.timer.timer
import io.data2viz.viz.StateManager
import io.data2viz.viz.StateManagerStatus
import io.data2viz.viz.StateableElement


inline fun <reified T : StateableElement> T.transitionTo(
    delay: Double = 0.0,
    duration: Double = 1000.0,
    noinline easing: (Double) -> Double = ease.cubicInOut,
    noinline configure: T.() -> Unit
) =
    Transition(this, configure, true, delay, duration, easing)

class Transition<T : StateableElement>(
    private val target: T,
    private val configure: T.() -> Unit,
    rootTransition: Boolean,
    var delay: Double = 0.0,
    var duration: Double,
    var easing: (Double) -> Double = ease.cubicInOut

) {
    private var next: Transition<T>? = null

    init {

        if (rootTransition)
            scheduleTransition()
    }

    /**
     * Asynchronously ran, all the properties are set.
     */
    private fun scheduleTransition() {
        val stateManager = StateManager()
        target.stateManager = stateManager
        stateManager.status = StateManagerStatus.RECORD
        configure(target)
        stateManager.status = StateManagerStatus.REST
        timer(delay) { time ->
            var percent = time / duration
            if (percent > .999999) {
                stop()
                percent = 1.0
            }
            stateManager.percentToState(easing(percent))
            if (percent == 1.0) {
                next?.scheduleTransition()
            }
        }
    }

    fun thenTransitionTo(
        delay: Double = 0.0,
        duration: Double = 1000.0,
        easing: (Double) -> Double = ease.cubicInOut,
        configure: T.() -> Unit
    ): Transition<T> {
        val ret = Transition(target, configure, false, delay, duration, easing)
        this.next = ret
        return ret
    }


}
