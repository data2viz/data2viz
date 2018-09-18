package io.data2viz.timer


/**
 * used to make the timer sleep until next frame or event.
 */
internal expect fun setTimeout(handler: () -> Unit, timeout: Int):Any

/**
 * clears previous timeout
 */
internal expect fun clearTimeout(handle:Any)

/**
 * Used to launch the update skew task every second
 */
internal expect fun setInterval(handler: () -> Unit, interval: Int):Any

/**
 * remove a potential update skew task
 */
internal expect fun clearInterval(handle:Any)

/**
 * Used to call clearNow() and wake()
 */
internal expect fun callInNextFrame(block: () -> Unit)


/**
 * Platform implementation of now in ms.
 */
internal expect fun delegateNow(): Double


internal var timeoutHandle:Any? = null
internal var pokeHandle:Any? = null

/**
 * is animation frame pending
 * Todo use boolean?
 */
internal var frame = 0

/**
 * how frequently we check for clock skew
 */
private const val pokeDelay = 1000

private var taskHead: Timer? = null
private var taskTail: Timer? = null
private var clockLast = 0.0


/**
 * now set for all timers
 */
internal var clockNow = 0.0

/**
 *
 */
internal var clockSkew = 0.0



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
fun timer(delay: Double = 0.0, startTime: Double = now(), callback: Timer.(Double) -> Unit): Timer {
    return Timer().apply {
        restart(delay, startTime, callback)
    }
}


/**
 * Like timer, except the timer automatically stops on its first callback. A suitable
 * replacement for setTimeout that is guaranteed to not run in the background.
 * The callback is passed the elapsed time.
 */
fun timeout(delay: Double = 0.0, startTime: Double = now(), callback: Timer.(Double) -> Unit): Timer {
    return Timer().apply {
        restart(delay, startTime) { time ->
            stop()
            callback(time)
        }
    }
}

fun interval(delay: Double = 0.0, startTime: Double = now(), callback: Timer.(Double) -> Unit): Timer {

//
//    export default function(callback, delay, time) {
//        var t = new Timer, total = delay;
//        if (delay == null) return t.restart(callback, delay, time), t;
//        delay = +delay, time = time == null ? now() : +time;
//        t.restart(function tick(elapsed) {
//            elapsed += total;
//            t.restart(tick, total += delay, time);
//            callback(elapsed);
//        }, delay, time);
//        return t;
//    }

    fun tick(elapsed:Double){

    }

    var total = delay
    val timer = Timer()
    if (delay == 0.0){

    }

    return Timer().apply {
        restart(delay, startTime) { time ->
            stop()
            callback(time)
        }
    }
}

class Timer {


    internal var _time: Double = 0.0

    /**
     * The lambda to be called.
     * Set to null when stopped
     * (todo: should be notnullable, use another param to indicate a stopped timer)
     */
    internal var _call: (Timer.(Double) -> Unit)? = null

    /**
     * the next timer created
     */
    internal var _next: Timer? = null

    /**
     * Restart a timer with the specified callback and optional delay and time.
     * This is equivalent to stopping this timer and creating a new timer with
     * the specified arguments, although this timer retains the original invocation priority.
     *
     * update taskTail and taskHead (the first timer is both tail and head)
     *
     * todo rename `restartWith`
     */
    fun restart(
        delay: Double = .0,
        startTime: Double = now(),
        callback: Timer.(Double) -> Unit
    ) {
        val newTime = startTime + delay
        if (_next == null && taskTail !== this) {
            val tail = taskTail
            if (tail != null) {
                tail._next = this
            } else
                taskHead = this
            taskTail = this
        }
        _call = callback
        _time = newTime
        sleep()
    }

    /**
     * Stops this timer, preventing subsequent callbacks.
     * This method has no effect if the timer has already stopped.
     */
    fun stop() {
        if (_call != null) {
            _call = null
            _time = Double.POSITIVE_INFINITY
            sleep()
        }
    }

    override fun toString(): String {
        return "Timer(_time=$_time,) _next=$_next"
    }

}

/**
 * Returns the current time as defined by performance.now (elapsed time since document creation) if available,
 * and Date.now if not (elapsed time since 1970/01/01 00:00:00).
 *
 * The current time is updated at the start of a frame; it is thus
 * consistent during the frame, and any timers scheduled during the same frame will be
 * synchronized.
 *
 * If this method is called outside of a frame, such as in response to a user event, the current
 * time is calculated and then fixed until the next frame, again ensuring consistent timing during event handling.
 */
fun now(): Double {
    if (clockNow == 0.0) {
        callInNextFrame(::clearNow)
        clockNow = delegateNow() + clockSkew
    }
    return clockNow
}


private fun clearNow() {
    clockNow = 0.0
}

/**
 * Immediately invoke any eligible timer callbacks. Note that zero-delay timers are normally
 * first executed after one frame (~17ms). This can cause a brief flicker because the browser
 * renders the page twice: once at the end of the first event loop, then again immediately on
 * the first timer callback. By flushing the timer queue at the end of the first event loop,
 * you can run any zero-delay timers immediately and avoid the flicker.
 */
fun timerFlush() {
    log("timerFlush")
    now()                       // Get the current time, if not already set.
    ++frame                     // Pretend we’ve set an alarm, if we haven’t already.
    var t = taskHead
    var elapsed: Double
    while (t != null) {
        elapsed = clockNow - t._time
        if (elapsed >= 0) {
//            log("flushing ${t._time.toInt().toString().takeLast(6)}")
            t._call?.invoke(t, elapsed)
        }
        t = t._next
    }
    --frame
}


/**
 * Before sleeping, cleans timers, starting from head.
 * If taskHead is null, set taskTail to null.
 * @return the time of the sooner timer to execute
 */
private fun updateTimers():Double {
    var t0: Timer? = null
    var t1 = taskHead
    var t2: Timer?
    var time = Double.POSITIVE_INFINITY
    var timerCount = 0
    while (t1 != null) {
        timerCount++
        if (t1._call != null) {
            if (time > t1._time) {
                time = t1._time
            }
            t0 = t1
            t1 = t1._next
        } else {
            // if t1 as no call, remove t1
            t2 = t1._next
            t1._next = null
            t1 = if (t0 != null) {
                t0._next = t2
                t2
            } else {
                taskHead = t2
                taskHead
            }
        }
    }
    taskTail = t0
    log("after updateTimers, timerCount $timerCount")
    return time
}

/**
 * Prepare sleep before wake.
 * If time is set and long (>  24 ms), use timeOut to wake up and remove the poke.
 * If time is not set or short (<= 24 ms), wake up at the next frame.
 */
private fun sleep(time: Double? = null) {


    if (frame > 0) return // Soonest alarm already set, or will be.
    timeoutHandle?.let {
        clearTimeout(it)
        timeoutHandle = null
    }

    if (time != null) {
        val delay = time - clockNow
        if (delay > 24) {
            if (time < Double.POSITIVE_INFINITY) {
                timeoutHandle = setTimeout(::wake, delay.toInt())
            }
            pokeHandle?.let {
                clearInterval(it)
                pokeHandle = null
            }
            return
        }
    }
    if (pokeHandle == null) {
        clockLast = clockNow
        pokeHandle = setInterval(::updateSkew, pokeDelay)
    }
    frame = 1
    callInNextFrame (::wake)
}


/**
 * Every second update the skew
 */
private fun updateSkew() {
    log("updateSkew")
    val now = now()
    val delay = now - clockLast
    if (delay > pokeDelay) {
        clockSkew -= delay
        clockLast = now
    }
}


private fun wake() {
    log("wake")
    clockLast = now()
    clockNow = clockLast + clockSkew
    frame = 0
    timeoutHandle = null
    try {
        timerFlush()
    } finally {
        frame = 0
        val time = updateTimers()
        sleep(time)
        clockNow = 0.0
    }
}

private fun log(msg:String) {
//    println( ("${now().toInt()} ${msg.padEnd(20)}handle:: $timeoutHandle timers::${logTimers()}"))
}

private fun logTimers(): String {
    val sb = StringBuilder("")
    var t = taskHead
    var i = 0
    while (t != null) {
        sb.append(" t$i[${t._time.toInt()}]")
        t = t._next
        i++
    }
    return sb.toString()
}