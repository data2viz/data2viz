package io.data2viz.viz

import android.graphics.Rect
import android.util.Log
import android.view.MotionEvent
import android.view.View


actual class KPointerMove {
    actual companion object MouseMoveEventListener : KEventListener<KPointerEvent> {
        override fun addNativeListener(target: Any, listener: (KPointerEvent) -> Unit): Disposable =
            addSimpleAndroidEventHandle(target, listener, MotionEvent.ACTION_MOVE)
    }
}

actual class KPointerDown {
    actual companion object MouseDownEventListener : KEventListener<KPointerEvent> {
        override fun addNativeListener(target: Any, listener: (KPointerEvent) -> Unit): Disposable =
            addSimpleAndroidEventHandle(target, listener, MotionEvent.ACTION_DOWN)
    }
}

actual class KPointerUp {
    actual companion object MouseUpEventListener : KEventListener<KPointerEvent> {
        override fun addNativeListener(target: Any, listener: (KPointerEvent) -> Unit): Disposable =
            addSimpleAndroidEventHandle(target, listener, MotionEvent.ACTION_UP)
    }
}

actual class KPointerEnter {
    actual companion object MouseEnterEventListener : KEventListener<KPointerEvent> {
        override fun addNativeListener(target: Any, listener: (KPointerEvent) -> Unit): Disposable {

            val renderer = target as AndroidCanvasRenderer
            val handler = object : DetectInBoundsVizTouchListener() {
                override fun onBoundsChanged(event: MotionEvent, oldInBoundsValue: Boolean, newInBoundsValue: Boolean) {
                    if (newInBoundsValue) {
                        val kevent = event.convertToKEvent()
                        listener(kevent)
                    }
                }
            }
            return AndroidEventHandle(renderer, MotionEvent.ACTION_MOVE, handler).also { it.init() }
        }
    }
}

actual class KPointerLeave {
    actual companion object MouseLeaveEventListener : KEventListener<KPointerEvent> {


        override fun addNativeListener(target: Any, listener: (KPointerEvent) -> Unit): Disposable {

            val renderer = target as AndroidCanvasRenderer
            val handler = object : DetectInBoundsVizTouchListener() {
                override fun onBoundsChanged(event: MotionEvent, oldInBoundsValue: Boolean, newInBoundsValue: Boolean) {
                    if (!newInBoundsValue) {
                        val kevent = event.convertToKEvent()
                        listener(kevent)
                    }
                }
            }
            return AndroidEventHandle(renderer, MotionEvent.ACTION_MOVE, handler).also { it.init() }
        }
    }
}

actual class KPointerClick {
    actual companion object MouseClickEventListener : KEventListener<KPointerEvent> {
        override fun addNativeListener(target: Any, listener: (KPointerEvent) -> Unit): Disposable =
            AndroidEventHandle(
                target as AndroidCanvasRenderer,
                MotionEvent.ACTION_UP,
                object : DetectClickVizTouchListener() {
                    override fun onClick(event: MotionEvent) {
                        val kevent = event.convertToKEvent()
                        listener(kevent)
                    }

                }
            ).also { it.init() }
    }
}


actual class KPointerDoubleClick {
    actual companion object MouseDoubleClickEventListener : KEventListener<KPointerEvent> {

        override fun addNativeListener(target: Any, listener: (KPointerEvent) -> Unit): Disposable =
            AndroidEventHandle(
                target as AndroidCanvasRenderer,
                MotionEvent.ACTION_UP,
                object : DetectDoubleClickVizTouchListener() {
                    override fun onDoubleClick(event: MotionEvent) {
                        val kevent = event.convertToKEvent()
                        listener(kevent)
                    }
                }
            ).also { it.init() }
    }
}

/**
 * Check is (x,y) in view bounds
 */
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
    return isInBounds
}

private fun addSimpleAndroidEventHandle(
    target: Any,
    listener: (KPointerEvent) -> Unit,
    action: Int
): AndroidEventHandle {
    val renderer = target as AndroidCanvasRenderer

    val handler = object : VizTouchListener {
        override fun onTouchEvent(view: View, event: MotionEvent?): Boolean {

            if (event?.action == action) {
                val kevent = event.convertToKEvent()
                listener(kevent)
            }
            return true
        }
    }

    return AndroidEventHandle(renderer, action, handler).also { it.init() }
}


data class AndroidEventHandle(val renderer: AndroidCanvasRenderer, val type: Int, val handler: VizTouchListener) :
    Disposable {

    fun init() {
        renderer.onTouchListeners.add(handler)
    }


    override fun dispose() {
        renderer.onTouchListeners.remove(handler)
    }
}

abstract class DetectInBoundsVizTouchListener : VizTouchListener {


    var isLastMoveInBounds = false

    override fun onTouchEvent(view: View, event: MotionEvent?): Boolean {

        if (event?.action != MotionEvent.ACTION_MOVE) {
            return false
        }

        val currentMoveInBounds = checkIsViewInBounds(view, event.x, event.y)

        if (isLastMoveInBounds != currentMoveInBounds) {
            onBoundsChanged(event, isLastMoveInBounds, currentMoveInBounds)
        }
        isLastMoveInBounds = currentMoveInBounds


        return false
    }

    abstract fun onBoundsChanged(event: MotionEvent, oldInBoundsValue: Boolean, newInBoundsValue: Boolean)
}

abstract class DetectClickVizTouchListener : VizTouchListener {
    companion object {
        const val maxTimeDiffForDetectClick = 500
    }

    var lastTimeActionDown: Long? = null
    override fun onTouchEvent(view: View, event: MotionEvent?): Boolean {
        when (event?.action) {
            MotionEvent.ACTION_DOWN -> lastTimeActionDown = System.currentTimeMillis()
            MotionEvent.ACTION_UP -> {
                val timeActionDown = lastTimeActionDown
                if (timeActionDown != null) {
                    val diff = System.currentTimeMillis() - timeActionDown
                    if (diff < maxTimeDiffForDetectClick) {

                        onClick(event)
                    }
                }
            }
        }
        return false
    }

    abstract fun onClick(event: MotionEvent)
}

abstract class DetectDoubleClickVizTouchListener :
    DetectClickVizTouchListener() {

    companion object {
        const val maxTimeDiffForDetectDoubleClick = 500
    }

    var lastTimeClick: Long? = null

    abstract fun onDoubleClick(event: MotionEvent)

    override fun onClick(event: MotionEvent) {
        val now = System.currentTimeMillis()
        val timeClick = lastTimeClick
        if (timeClick != null) {
            val diffClicks = now - timeClick
            if (diffClicks < maxTimeDiffForDetectDoubleClick) {
                onDoubleClick(event)

            }
        }
        lastTimeClick = now
    }

}


actual fun <T> VizRenderer.addNativeEventListenerFromHandle(handle: KEventHandle<T>): Disposable where T : KEvent {

    val androidCanvasRenderer = this as AndroidCanvasRenderer
    return handle.eventListener.addNativeListener(androidCanvasRenderer, handle.listener)
}


private fun MotionEvent.convertToKEvent(): KPointerEvent {
    val KPointerMoveEvent = io.data2viz.viz.KPointerEvent(
        io.data2viz.geom.Point(x.toDouble(), y.toDouble())
    )
    return KPointerMoveEvent
}