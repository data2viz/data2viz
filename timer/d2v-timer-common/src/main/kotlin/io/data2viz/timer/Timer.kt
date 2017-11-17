package io.data2viz.timer



/**
 * Schedules a new timer, invoking the specified callback repeatedly until the
 * timer is stopped.
 *
 * An optional numeric delay in milliseconds may be specified
 * to invoke the given callback after a delay; if delay is not specified, it
 * defaults to zero.
 *
 * The delay is relative to the specified time in milliseconds;
 * if time is not specified, it defaults to now.
 *
 * The callback is passed the (apparent) elapsed time since the timer became active.
 *
 * (The exact values may vary depending on your JavaScript runtime and what else
 * your computer is doing.)
 *
 * Note that the first elapsed time is 3ms: this is the elapsed time since the
 * timer started, not since the timer was scheduled. Here the timer started 150ms
 * after it was scheduled due to the specified delay. The apparent elapsed time may
 * be less than the true elapsed time if the page is backgrounded and requestAnimationFrame
 * is paused; in the background, apparent time is frozen.If timer is called within the
 * callback of another timer, the new timer callback (if eligible as determined by the
 * specified delay and time) will be invoked immediately at the end of the current frame,
 * rather than waiting until the next frame. Within a frame, timer callbacks are guaranteed
 * to be invoked in the order they were scheduled, regardless of their start time.
 */
fun timer(delay: Double = 0.0, startTime: Double = now(), callback: TimerImpl.(Double) -> Unit): Timer {
    val t = Timer(buildTimerImpl())
    t.restart(callback, delay, startTime)
    return t
}


class Timer(val timerImpl: TimerImpl)  {

    fun restart(callback: TimerImpl.(Double) -> Unit, delay: Double = 0.0, startTime: Double) {
        timerImpl.restart(callback, delay, startTime)
    }

    fun stop() {
        timerImpl.stop()
    }
}

expect internal fun buildTimerImpl(): TimerImpl


expect class TimerImpl {

    internal var _time: Double
    internal var _call: (TimerImpl.(Double) -> Unit)?
    internal var _next: TimerImpl?

    fun restart(callback: TimerImpl.(Double) -> Unit, delay: Double, startTime: Double)
    fun stop()

}

expect fun now():Double
