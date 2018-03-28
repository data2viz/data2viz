package io.data2viz.transition


import io.data2viz.ease.ease
import io.data2viz.timer.timer
import io.data2viz.viz.StateableElement



inline fun <reified T: StateableElement<T>> T.transition(noinline configure: T.() -> Unit ){
    Transition(this, configure)
}

class Transition<T: StateableElement<T>>(
    private val target: T,
    private val configure: T.() -> Unit) {

    var easing: (Double) -> Double = ease.cubicInOut
    var delay = 0.0
    var duration = 1000.0

    init {
        timer {
            scheduleTransition()
            stop()
        }
    }

    /**
     * Asynchronously ran, all the properties are set.
     */
    private fun scheduleTransition(){
//        println("Schedule transition")
        target.addState(configure)
        timer { time ->
            var percent  = time / duration
            if (percent> .999999){
                stop()
                percent = 1.0
            }
            target.percentToState( easing(percent))
        }
    }

}