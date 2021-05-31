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

package io.data2viz.viz

import android.graphics.Rect
import android.view.MotionEvent
import android.view.ScaleGestureDetector
import android.view.View
import io.data2viz.geom.Point
import kotlin.math.abs


private val emptyDisposable = object : Disposable { override fun dispose() {} }
private val epsilon = 1.0e-3

private val vt = android.view.VelocityTracker.obtain()

public actual class KTouchStart {
    public actual companion object TouchStartEventListener : KEventListener<KPointerEvent> {
        override fun addNativeListener(target: Any, listener: (KPointerEvent) -> Unit): Disposable =
            addSingleTouchAndroidEventHandle(target, listener, MotionEvent.ACTION_DOWN)
    }
}

public actual class KTouchEnd {
    public actual companion object TouchEndEventListener : KEventListener<KPointerEvent> {
        override fun addNativeListener(target: Any, listener: (KPointerEvent) -> Unit): Disposable =
            addSingleTouchAndroidEventHandle(target, listener, MotionEvent.ACTION_UP)
    }
}

public actual class KTouchMove {
    public actual companion object TouchMoveEventListener : KEventListener<KPointerEvent> {
        override fun addNativeListener(target: Any, listener: (KPointerEvent) -> Unit): Disposable =
            addSingleTouchAndroidEventHandle(target, listener, MotionEvent.ACTION_MOVE)
    }
}

public actual class KDualTouchStart {
    public actual companion object TouchStartEventListener : KEventListener<KDualPointerEvent> {
        override fun addNativeListener(target: Any, listener: (KDualPointerEvent) -> Unit): Disposable =
            addDualTouchAndroidEventHandle(target, listener, MotionEvent.ACTION_DOWN)
    }
}

public actual class KDualTouchEnd {
    public actual companion object TouchEndEventListener : KEventListener<KDualPointerEvent> {
        override fun addNativeListener(target: Any, listener: (KDualPointerEvent) -> Unit): Disposable =
            addDualTouchAndroidEventHandle(target, listener, MotionEvent.ACTION_UP)
    }
}

public actual class KDualTouchMove {
    public actual companion object TouchMoveEventListener : KEventListener<KDualPointerEvent> {
        override fun addNativeListener(target: Any, listener: (KDualPointerEvent) -> Unit): Disposable =
            addDualTouchAndroidEventHandle(target, listener, MotionEvent.ACTION_MOVE)
    }
}

public actual class KMouseMove {
    public actual companion object PointerMoveEventListener : KEventListener<KMouseEvent> {
        override fun addNativeListener(target: Any, listener: (KMouseEvent) -> Unit): Disposable = emptyDisposable
    }
}

public actual class KMouseDown {
    public actual companion object PointerDownEventListener : KEventListener<KMouseEvent> {
        override fun addNativeListener(target: Any, listener: (KMouseEvent) -> Unit): Disposable = emptyDisposable
    }
}

public actual class KMouseUp {
    public actual companion object PointerUpEventListener : KEventListener<KMouseEvent> {
        override fun addNativeListener(target: Any, listener: (KMouseEvent) -> Unit): Disposable = emptyDisposable
    }
}


public actual class KPointerEnter {
    public actual companion object PointerEnterEventListener : KEventListener<KPointerEvent> {
        override fun addNativeListener(target: Any, listener: (KPointerEvent) -> Unit): Disposable {

            val renderer = target as AndroidCanvasRenderer
            val handler = object : DetectInBoundsVizTouchListener() {
                override fun onBoundsChanged(event: MotionEvent, oldInBoundsValue: Boolean, newInBoundsValue: Boolean) {
                    if (newInBoundsValue) {
                        val kevent = event.toKMouseEvent()
                        listener(kevent)
                    }
                }
            }
            return AndroidActionEventHandle(renderer, MotionEvent.ACTION_MOVE, handler).also { it.init() }
        }
    }
}

public actual class KPointerLeave {
    public actual companion object PointerLeaveEventListener : KEventListener<KPointerEvent> {

        override fun addNativeListener(target: Any, listener: (KPointerEvent) -> Unit): Disposable {

            val renderer = target as AndroidCanvasRenderer
            val handler = object : DetectInBoundsVizTouchListener() {
                override fun onBoundsChanged(event: MotionEvent, oldInBoundsValue: Boolean, newInBoundsValue: Boolean) {
                    if (!newInBoundsValue) {
                        val kevent = event.toKMouseEvent()
                        listener(kevent)
                    }
                }
            }
            return AndroidActionEventHandle(renderer, MotionEvent.ACTION_MOVE, handler).also { it.init() }
        }
    }
}

public actual class KPointerClick {
    public actual companion object PointerClickEventListener : KEventListener<KPointerEvent> {
        override fun addNativeListener(target: Any, listener: (KPointerEvent) -> Unit): Disposable =
            AndroidActionEventHandle(
                target as AndroidCanvasRenderer,
                MotionEvent.ACTION_UP,
                object : DetectClickVizTouchListener() {
                    override fun onClick(event: MotionEvent) {
                        val kEvent = event.toKMouseEvent()
                        listener(kEvent)
                    }

                }
            ).also { it.init() }
    }
}


public actual class KPointerDoubleClick {
    public actual companion object PointerDoubleClickEventListener : KEventListener<KPointerEvent> {

        override fun addNativeListener(target: Any, listener: (KPointerEvent) -> Unit): Disposable =
            AndroidActionEventHandle(
                target as AndroidCanvasRenderer,
                MotionEvent.ACTION_UP,
                object : DetectDoubleClickVizTouchListener() {
                    override fun onDoubleClick(event: MotionEvent) {
                        val kevent = event.toKMouseEvent()
                        listener(kevent)
                    }
                }
            ).also { it.init() }
    }
}

@ExperimentalKEvent
public actual class KZoom {
    public actual companion object ZoomEventListener : KEventListener<KZoomEvent> {

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
                        var scaleFactorX = .0
                        var scaleFactorY = .0
                        val minSpanX = 50.0
                        val minSpanY = 50.0

                        val zoomStartPoint = Point(detector.focusX.toDouble(), detector.focusY.toDouble())

                        if (abs(detector.currentSpanX) > minSpanX) {
                            scaleFactorX = (detector.currentSpanX / detector.previousSpanX) - 1.0
                        }
                        if (abs(detector.currentSpanY) > minSpanY) {
                            scaleFactorY = (detector.currentSpanY / detector.previousSpanY) - 1.0
                        }

                        if (abs(scaleFactorX) > epsilon && abs(scaleFactorY) > epsilon) {
                            listener(KZoomEvent(zoomStartPoint, scaleFactorX, scaleFactorY))
                        }
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
    val boundsRect = Rect()
    val locationOnScreen = IntArray(2)
    view.getDrawingRect(boundsRect)
    view.getLocationOnScreen(locationOnScreen)
    boundsRect.offset(locationOnScreen[0], locationOnScreen[1])
    val isInBounds = boundsRect.contains(x.toInt(), y.toInt())
    return isInBounds
}

private fun addSingleTouchAndroidEventHandle(target: Any, listener: (KPointerEvent) -> Unit, action: Int):
    AndroidActionEventHandle {

    val renderer = target as AndroidCanvasRenderer

    val handler = object : VizTouchListener {
        override fun onTouchEvent(view: View, event: MotionEvent?): Boolean {

            // Simple events only for single touch
            if (event?.pointerCount == 1) {
                if (event.action == action) {
                    val kevent = event.toKMouseEvent()
                    listener(kevent)
                }
            }

            return true
        }
    }

    return AndroidActionEventHandle(renderer, action, handler).also { it.init() }
}

private fun addDualTouchAndroidEventHandle(target: Any, listener: (KDualPointerEvent) -> Unit, action: Int):
    AndroidActionEventHandle {

    val renderer = target as AndroidCanvasRenderer

    val handler = object : VizTouchListener {
        override fun onTouchEvent(view: View, event: MotionEvent?): Boolean {

            // Simple events only for dual touch
            if (event?.pointerCount == 2) {
                if (event.action == action) {
                    val kevent = event.toDualPointerEvent()
                    listener(kevent)
                }
            }

            return true
        }
    }

    return AndroidActionEventHandle(renderer, action, handler).also { it.init() }
}


public class AndroidActionEventHandle(
    renderer: AndroidCanvasRenderer,
    public val type: Int,
    handler: VizTouchListener) :

    AndroidEventHandle(renderer, handler)

public open class AndroidEventHandle(
    public val renderer: AndroidCanvasRenderer,
    public val handler: VizTouchListener) :

    Disposable {

    public fun init() {
        renderer.onTouchListeners.add(handler)
    }


    override fun dispose() {
        renderer.onTouchListeners.remove(handler)
    }
}

public abstract class DetectInBoundsVizTouchListener : VizTouchListener {


    public var isLastMoveInBounds: Boolean = false

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

    public abstract fun onBoundsChanged(event: MotionEvent, oldInBoundsValue: Boolean, newInBoundsValue: Boolean)
}

public abstract class DetectClickVizTouchListener : VizTouchListener {
    public companion object {
        public const val maxTimeDiffForDetectClick: Int = 500
    }

    public var lastTimeActionDown: Long? = null
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

    public abstract fun onClick(event: MotionEvent)
}

public abstract class DetectDoubleClickVizTouchListener :
    DetectClickVizTouchListener() {

    public companion object {
        public const val maxTimeDiffForDetectDoubleClick: Int = 500
    }

    public var lastTimeClick: Long? = null

    public abstract fun onDoubleClick(event: MotionEvent)

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

internal actual fun <T> VizRenderer.addNativeEventListenerFromHandle(handle: KEventHandle<T>): Disposable where T : KEvent {
    val androidCanvasRenderer = this as AndroidCanvasRenderer
    return handle.eventListener.addNativeListener(androidCanvasRenderer, handle.listener)
}

private fun MotionEvent.toKMouseEvent(): KPointerEvent = KPointerEvent(Point(x.toDouble(), y.toDouble()))
private fun MotionEvent.toDualPointerEvent(): KDualPointerEvent = KDualPointerEvent(
    Point(getX(0).toDouble(), getY(0).toDouble()),
    Point(getX(1).toDouble(), getY(1).toDouble())
)
