package io.data2viz.viz

import android.graphics.Rect
import android.util.Log
import android.view.MotionEvent
import android.view.View


actual class KMouseMove {
    actual companion object MouseMoveEventListener : KEventListener<KMouseEvent> {
        override fun addNativeListener(target: Any, listener: (KMouseEvent) -> Unit): Any =
            addSimpleAndroidEventHandle(target, listener, MotionEvent.ACTION_MOVE)

    }
}


actual class KMouseDown {
    actual companion object MouseDownEventListener : KEventListener<KMouseEvent> {
        override fun addNativeListener(target: Any, listener: (KMouseEvent) -> Unit): Any =
            addSimpleAndroidEventHandle(target, listener, MotionEvent.ACTION_DOWN)
    }
}

actual class KMouseUp {
    actual companion object MouseUpEventListener : KEventListener<KMouseEvent> {
        override fun addNativeListener(target: Any, listener: (KMouseEvent) -> Unit): Any =
            addSimpleAndroidEventHandle(target, listener, MotionEvent.ACTION_UP)
    }
}

actual class KMouseEnter {
    actual companion object MouseEnterEventListener : KEventListener<KMouseEvent> {

        var isLastMoveInBounds = false

        override fun addNativeListener(target: Any, listener: (KMouseEvent) -> Unit): Any {

            val renderer = target as AndroidCanvasRenderer

            val action = MotionEvent.ACTION_MOVE
            val handler: (MotionEvent) -> Unit = { evt: MotionEvent ->
                val kevent = evt.convertToKEvent()
                listener(kevent)
            }

            renderer.onTouchListeners.add(object : VizTouchListener {
                override fun onTouchEvent(view: View, event: MotionEvent?): Boolean {

                    if (event?.action != action) {
                        return false
                    }

                    val currentMoveInBounds = checkIsViewInBounds(view, event.x, event.y)


                    if (isLastMoveInBounds != currentMoveInBounds) {
                        if (currentMoveInBounds) {
                            handler(event)
                        }
                    }
                    isLastMoveInBounds = currentMoveInBounds

                    // handle only touches in view bounds
                    return currentMoveInBounds
                }
            })
            return AndroidEventHandle(action, handler)
        }
    }
}

actual class KMouseLeave {
    actual companion object MouseLeaveEventListener : KEventListener<KMouseEvent> {

        var isLastMoveInBounds = false

        override fun addNativeListener(target: Any, listener: (KMouseEvent) -> Unit): Any {

            val renderer = target as AndroidCanvasRenderer

            val action = MotionEvent.ACTION_MOVE
            val handler: (MotionEvent) -> Unit = { evt: MotionEvent ->
                val kevent = evt.convertToKEvent()
                listener(kevent)
            }

            renderer.onTouchListeners.add(object : VizTouchListener {
                override fun onTouchEvent(view: View, event: MotionEvent?): Boolean {

                    if (event?.action != action) {
                        return false
                    }

                    val currentMoveInBounds = checkIsViewInBounds(view, event.x, event.y)


                    if (isLastMoveInBounds != currentMoveInBounds) {
                        if (!currentMoveInBounds) {
                            handler(event)
                        }
                    }
                    isLastMoveInBounds = currentMoveInBounds

                    // handle only touches in view bounds
                    return currentMoveInBounds
                }
            })
            return AndroidEventHandle(action, handler)
        }
    }
}

actual class KMouseOut {
    actual companion object MouseOutEventListener : KEventListener<KMouseEvent> {
        override fun addNativeListener(target: Any, listener: (KMouseEvent) -> Unit): Any =
            KMouseLeave.addNativeListener(target, listener)
    }
}

actual class KMouseOver {
    actual companion object MouseOverEventListener : KEventListener<KMouseEvent> {
        override fun addNativeListener(target: Any, listener: (KMouseEvent) -> Unit): Any =
            KMouseEnter.addNativeListener(target, listener)
    }
}


actual class KMouseClick {
    actual companion object MouseClickEventListener : KEventListener<KMouseEvent> {

        const val maxTimeDiffForDetectClick = 500
        var lastTimeActionDown: Long? = null

        override fun addNativeListener(target: Any, listener: (KMouseEvent) -> Unit): Any {
            val renderer = target as AndroidCanvasRenderer

            val action = MotionEvent.ACTION_UP
            val handler: (MotionEvent) -> Unit = { evt: MotionEvent ->
                val kevent = evt.convertToKEvent()
                listener(kevent)
            }

            renderer.onTouchListeners.add(object : VizTouchListener {
                override fun onTouchEvent(view: View, event: MotionEvent?): Boolean {
                    when (event?.action) {
                        MotionEvent.ACTION_DOWN -> lastTimeActionDown = System.currentTimeMillis()
                        MotionEvent.ACTION_UP -> {
                            val timeActionDown = lastTimeActionDown
                            if (timeActionDown != null) {
                                val diff = System.currentTimeMillis() - timeActionDown
                                if (diff < maxTimeDiffForDetectClick) {
                                    handler(event)
                                }
                            }
                        }
                    }
                    return false
                }
            })
            return AndroidEventHandle(action, handler)
        }
    }
}


actual class KMouseDoubleClick {
    actual companion object MouseDoubleClickEventListener : KEventListener<KMouseEvent> {
        const val maxTimeDiffForDetectClick = 500
        const val maxTimeDiffForDetectDoubleClick = 500
        var lastTimeActionDown: Long? = null
        var lastTimeClick: Long? = null

        override fun addNativeListener(target: Any, listener: (KMouseEvent) -> Unit): Any {
            val renderer = target as AndroidCanvasRenderer

            val action = MotionEvent.ACTION_UP
            val handler: (MotionEvent) -> Unit = { evt: MotionEvent ->
                val kevent = evt.convertToKEvent()
                listener(kevent)
            }

            renderer.onTouchListeners.add(object : VizTouchListener {
                override fun onTouchEvent(view: View, event: MotionEvent?): Boolean {
                    when (event?.action) {
                        MotionEvent.ACTION_DOWN -> lastTimeActionDown = System.currentTimeMillis()
                        MotionEvent.ACTION_UP -> {
                            val timeActionDown = lastTimeActionDown
                            if (timeActionDown != null) {
                                val now = System.currentTimeMillis()
                                val diffDownUp = now - timeActionDown
                                if (diffDownUp < maxTimeDiffForDetectClick) {

                                    val timeClick = lastTimeClick
                                    if (timeClick != null) {
                                        val diffClicks = timeClick - now
                                        if (diffClicks < maxTimeDiffForDetectDoubleClick) {
                                            handler(event)
                                        }
                                    }
                                    lastTimeClick = now
                                }
                            }
                        }
                    }
                    return false
                }
            })
            return AndroidEventHandle(action, handler)
        }
    }
}

private fun checkIsViewInBounds(
    view: View,
    x: Float,
    y: Float
): Boolean {
    var boundsRect = Rect()
    var locationOnScreen = IntArray(2)
    view.getDrawingRect(boundsRect)
    view.getLocationOnScreen(locationOnScreen)
    boundsRect.offset(locationOnScreen[0], locationOnScreen[1])
    val isInBounds = boundsRect.contains(x.toInt(), y.toInt())
//    Log.d("AndroidEvents", "inBounds $isInBounds ($x, $y) in $boundsRect ")
    return isInBounds
}

private fun addSimpleAndroidEventHandle(
    target: Any,
    listener: (KMouseEvent) -> Unit,
    action: Int
): AndroidEventHandle {
    val renderer = target as AndroidCanvasRenderer
    val handler: (MotionEvent) -> Unit = { evt: MotionEvent ->
        val kevent = evt.convertToKEvent()
        listener(kevent)
    }

    renderer.onTouchListeners.add(object : VizTouchListener {
        override fun onTouchEvent(view: View, event: MotionEvent?): Boolean {

            if (event?.action == action) {
                handler(event)
            }
            return true
        }
    })

    return AndroidEventHandle(action, handler)
}


data class AndroidEventHandle(val type: Int, val handler: (MotionEvent) -> Unit)


/**
 * Add an event listener on a a viz.
 * @return an handler to eventually remove later.
 */
actual fun <T> Viz.on(
    eventListener: KEventListener<T>,
    listener: (T) -> Unit
): Any {
    val androidCanvasRenderer = this.renderer as AndroidCanvasRenderer
    return eventListener.addNativeListener(androidCanvasRenderer, listener)
}


private fun MotionEvent.convertToKEvent(): KMouseEvent {
    val kMouseMoveEvent = io.data2viz.viz.KMouseEvent(
        io.data2viz.geom.Point(x.toDouble(), y.toDouble()),
        altKey = false,
        ctrlKey = false,
        shiftKey = false,
        metaKey = false
    )
    return kMouseMoveEvent
}