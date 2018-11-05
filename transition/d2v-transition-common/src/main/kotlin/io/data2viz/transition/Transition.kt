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


// TODO : easing use (Percent) -> Double ? It looks like a scale ...
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