package io.data2viz.viz

import android.graphics.Rect
import android.view.MotionEvent
import android.view.ScaleGestureDetector
import android.view.View
import io.data2viz.geom.Point


actual class KPointerMove {
    actual companion object PointerMoveEventListener : KEventListener<KPointerEvent> {
        override fun addNativeListener(target: Any, listener: (KPointerEvent) -> Unit): Disposable =
            addSimpleAndroidEventHandle(target, listener, MotionEvent.ACTION_MOVE)
    }
}

actual class KPointerDown {
    actual companion object PointerDownEventListener : KEventListener<KPointerEvent> {
        override fun addNativeListener(target: Any, listener: (KPointerEvent) -> Unit): Disposable =
            addSimpleAndroidEventHandle(target, listener, MotionEvent.ACTION_DOWN)
    }
}

actual class KPointerUp {
    actual companion object PointerUpEventListener : KEventListener<KPointerEvent> {
        override fun addNativeListener(target: Any, listener: (KPointerEvent) -> Unit): Disposable =
            addSimpleAndroidEventHandle(target, listener, MotionEvent.ACTION_UP)
    }
}

actual class KPointerEnter {
    actual companion object PointerEnterEventListener : KEventListener<KPointerEvent> {
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
            return AndroidActionEventHandle(renderer, MotionEvent.ACTION_MOVE, handler).also { it.init() }
        }
    }
}

actual class KPointerLeave {
    actual companion object PointerLeaveEventListener : KEventListener<KPointerEvent> {


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
            return AndroidActionEventHandle(renderer, MotionEvent.ACTION_MOVE, handler).also { it.init() }
        }
    }
}

actual class KPointerClick {
    actual companion object PointerClickEventListener : KEventListener<KPointerEvent> {
        override fun addNativeListener(target: Any, listener: (KPointerEvent) -> Unit): Disposable =
            AndroidActionEventHandle(
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
    actual companion object PointerDoubleClickEventListener : KEventListener<KPointerEvent> {

        override fun addNativeListener(target: Any, listener: (KPointerEvent) -> Unit): Disposable =
            AndroidActionEventHandle(
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

@ExperimentalKZoomEvent
actual class KZoom {
    actual companion object ZoomEventListener : KEventListener<KZoomEvent> {
        const val minZoomDeltaValue = -100.0
        const val maxZoomDeltaValue = 100.0



        var lastZoomTime: Long? = null
        lateinit var zoomStartPoint: Point

        override fun addNativeListener(target: Any, listener: (KZoomEvent) -> Unit): Disposable {


            val androidCanvasRenderer = target as AndroidCanvasRenderer
            val gestureDetector = ScaleGestureDetector(
                androidCanvasRenderer.context,
                object : ScaleGestureDetector.OnScaleGestureListener {
                    override fun onScaleBegin(detector: ScaleGestureDetector): Boolean {
                        return true
                    }

                    override fun onScaleEnd(detector: ScaleGestureDetector) {

                    }

                    override fun onScale(detector: ScaleGestureDetector): Boolean {
                        val diffSpan = (detector.currentSpan - detector.previousSpan).toDouble()

                        val currentTime = System.currentTimeMillis()
                        if (KZoomEvent.isNewZoom(currentTime, lastZoomTime)) {
                            zoomStartPoint = Point(detector.focusX.toDouble(), detector.focusY.toDouble())
                        }
                        lastZoomTime = currentTime

                        listener(KZoomEvent(zoomStartPoint, KZoomEvent.scaleDelta(diffSpan, minZoomDeltaValue, maxZoomDeltaValue)))

                        return true
                    }

                })
            val gestureDetectorVizTouchListener = object : VizTouchListener {
                override fun onTouchEvent(view: View, event: MotionEvent?): Boolean {
                    return gestureDetector.onTouchEvent(event)
                }
            }
            return AndroidEventHandle(androidCanvasRenderer, gestureDetectorVizTouchListener).also { it.init() }
        }
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
): AndroidActionEventHandle {
    val renderer = target as AndroidCanvasRenderer

    val handler = object : VizTouchListener {
        override fun onTouchEvent(view: View, event: MotionEvent?): Boolean {

            // Simple events only for single touch
            if (event?.pointerCount == 1) {
                if (event?.action == action) {
                    val kevent = event.convertToKEvent()
                    listener(kevent)
                }
            }

            return true
        }
    }

    return AndroidActionEventHandle(renderer, action, handler).also { it.init() }
}


class AndroidActionEventHandle(renderer: AndroidCanvasRenderer, val type: Int, handler: VizTouchListener) :
    AndroidEventHandle(renderer, handler) {
}

open class AndroidEventHandle(val renderer: AndroidCanvasRenderer, val handler: VizTouchListener) :
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