package io.data2viz.timer

actual class TimerImpl {

    internal actual var _time: Double = 0.0
    internal actual var _call: (TimerImpl.(Double) -> Unit)? = null
    internal actual var _next: TimerImpl? = null

    actual fun restart(callback: TimerImpl.(Double) -> Unit, delay: Double, startTime: Double) {

    }

    actual fun stop(){

    }

}


internal actual fun buildTimerImpl(): TimerImpl {
    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
}

actual fun now(): Double {
    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
}
