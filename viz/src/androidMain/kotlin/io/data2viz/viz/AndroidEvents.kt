/*
 * Copyright (c) 2018-2022. data2viz sàrl.
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
import android.os.Build.VERSION.SDK_INT
import android.view.MotionEvent
import android.view.ScaleGestureDetector
import android.view.View
import io.data2viz.geom.Point
import kotlin.math.abs

private val epsilon = 1.0e-3

private val vt = android.view.VelocityTracker.obtain()

internal actual fun pointerEventsListener(type: EventType): KEventListener<KPointerEvent> {
    require(type != EventType.Unknown)
    return object : KEventListener<KPointerEvent> {
        override fun addNativeListener(
            target: Any,
            listener: (KPointerEvent) -> EventPropagation
        ): Disposable {
            return addSingleTouchAndroidEventHandle(
                target = target,
                listener = listener,
                type,
                action = when (type) {
                    EventType.Down -> MotionEvent.ACTION_DOWN
                    EventType.Up -> MotionEvent.ACTION_UP
                    EventType.Move -> MotionEvent.ACTION_MOVE
                    EventType.Enter -> return enterLeaveEventHandler(enter = true, target, listener)
                    EventType.Leave -> return enterLeaveEventHandler(enter = false, target, listener)
                    EventType.Click -> return clickEventHandler(target, listener)
                    EventType.DoubleClick -> return doubleClickEventHandler(target, listener)
                    EventType.Cancel -> MotionEvent.ACTION_CANCEL
                    EventType.Unknown -> error("Impossible")
                }
            )
        }
    }
}

private fun enterLeaveEventHandler(
    enter: Boolean,
    target: Any,
    listener: (KPointerEvent) -> EventPropagation
): AndroidActionEventHandle {
    val renderer = target as AndroidCanvasRenderer
    val handler = object : DetectInBoundsVizTouchListener() {
        override fun onBoundsChanged(event: MotionEvent, oldInBoundsValue: Boolean, newInBoundsValue: Boolean) {
            if (enter == newInBoundsValue) {
                val kEvent = event.toKPointerEvent(if (enter) EventType.Enter else EventType.Leave)
                listener(kEvent)
            }
        }
    }
    return AndroidActionEventHandle(renderer, MotionEvent.ACTION_MOVE, handler).also { it.init() }
}

private fun clickEventHandler(
    target: Any,
    listener: (KPointerEvent) -> EventPropagation
): AndroidActionEventHandle {
    return AndroidActionEventHandle(
        renderer = target as AndroidCanvasRenderer,
        type = MotionEvent.ACTION_UP,
        handler = object : DetectClickVizTouchListener() {
            override fun onClick(event: MotionEvent) {
                val kEvent = event.toKPointerEvent(EventType.Click)
                listener(kEvent)
            }

        }
    ).also { it.init() }
}

private fun doubleClickEventHandler(
    target: Any,
    listener: (KPointerEvent) -> EventPropagation
): AndroidActionEventHandle {
    return AndroidActionEventHandle(
        target as AndroidCanvasRenderer,
        MotionEvent.ACTION_UP,
        object : DetectDoubleClickVizTouchListener() {
            override fun onDoubleClick(event: MotionEvent) {
                val kevent = event.toKPointerEvent(EventType.DoubleClick)
                listener(kevent)
            }
        }
    ).also { it.init() }
}

@ExperimentalKEvent
internal actual fun zoomEventsListener(): KEventListener<KZoomEvent> = object : KEventListener<KZoomEvent> {

    override fun addNativeListener(
        target: Any,
        listener: (KZoomEvent) -> EventPropagation
    ): Disposable {

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
            override fun onTouchEvent(
                view: View,
                event: MotionEvent
            ): EventPropagation = when (gestureDetector.onTouchEvent(event)) {
                true -> EventPropagation.Stop
                false -> EventPropagation.Continue
            }
        }

        return AndroidEventHandle(androidCanvasRenderer, gestureDetectorVizTouchListener).also { it.init() }
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

private fun addSingleTouchAndroidEventHandle(
    target: Any,
    listener: (KPointerEvent) -> EventPropagation,
    type: EventType,
    action: Int
): AndroidActionEventHandle {

    val renderer = target as AndroidCanvasRenderer

    val handler = object : VizTouchListener {
        override fun onTouchEvent(view: View, event: MotionEvent): EventPropagation {

            // Simple events only for single touch
            if (event.pointerCount == 1) {
                if (event.action == action) {
                    val kevent = event.toKPointerEvent(type)
                    return listener(kevent)
                }
            }

            return EventPropagation.Continue
        }
    }
    return AndroidActionEventHandle(renderer, action, handler).also { it.init() }
}

public class AndroidActionEventHandle(
    renderer: AndroidCanvasRenderer,
    public val type: Int,
    handler: VizTouchListener
) : AndroidEventHandle(renderer, handler)

public open class AndroidEventHandle(
    public val renderer: AndroidCanvasRenderer,
    public val handler: VizTouchListener
) : Disposable {

    public fun init() {
        renderer.onTouchListeners.add(handler)
    }


    override fun dispose() {
        renderer.onTouchListeners.remove(handler)
    }
}

public abstract class DetectInBoundsVizTouchListener : VizTouchListener {

    public var isLastMoveInBounds: Boolean = false

    override fun onTouchEvent(view: View, event: MotionEvent): EventPropagation {

        if (event.action != MotionEvent.ACTION_MOVE) {
            return EventPropagation.Continue
        }

        val currentMoveInBounds = checkIsViewInBounds(view, event.x, event.y)

        if (isLastMoveInBounds != currentMoveInBounds) {
            onBoundsChanged(event, isLastMoveInBounds, currentMoveInBounds)
        }
        isLastMoveInBounds = currentMoveInBounds


        return EventPropagation.Continue
    }

    public abstract fun onBoundsChanged(event: MotionEvent, oldInBoundsValue: Boolean, newInBoundsValue: Boolean)
}

public abstract class DetectClickVizTouchListener : VizTouchListener {
    public companion object {
        public const val maxTimeDiffForDetectClick: Int = 500
    }

    public var lastTimeActionDown: Long? = null
    override fun onTouchEvent(view: View, event: MotionEvent): EventPropagation {
        when (event.action) {
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
        return EventPropagation.Continue
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

private fun MotionEvent.toKPointerEvent(type: EventType): KPointerEvent = KPointerEventImpl(
    pos = Point(x.toDouble() / density, y.toDouble() / density),
    eventType = type,
    pointerType = pointerType(),
    buttonPressed = pressedMouseButton(),
    activePointerIndex = actionIndex,
    pointers = List(pointerCount) { index ->
        KPointer(
            id = getPointerId(index),
            pos = Point(
                x = getX(index).toDouble() / density,
                y = getY(index).toDouble() / density
            )
        )
    },
    altKey = false, //TODO: Hook KeyEvents
    ctrlKey = false, //TODO: Hook KeyEvents
    shiftKey = false, //TODO: Hook KeyEvents
    metaKey = false //TODO: Hook KeyEvents
)

private fun Int.actionMaskToEventType(): EventType = when (this) {
    MotionEvent.ACTION_DOWN, MotionEvent.ACTION_POINTER_DOWN -> EventType.Down
    MotionEvent.ACTION_MOVE -> EventType.Move
    MotionEvent.ACTION_UP, MotionEvent.ACTION_POINTER_UP -> EventType.Up
    else -> EventType.Cancel
}

private fun MotionEvent.pointerType(): PointerType = when (getToolType(0)) {
    MotionEvent.TOOL_TYPE_FINGER -> PointerType.Touch
    MotionEvent.TOOL_TYPE_MOUSE -> PointerType.Mouse
    MotionEvent.TOOL_TYPE_STYLUS -> PointerType.Pen
    MotionEvent.TOOL_TYPE_ERASER -> PointerType.Unknown
    MotionEvent.TOOL_TYPE_UNKNOWN -> PointerType.Unknown
    else -> PointerType.Unknown
}

private fun MotionEvent.pressedMouseButton(): MouseButtonPressed {
    if (SDK_INT < 21) return MouseButtonPressed.NotApplicable
    return when {
        isButtonPressed(MotionEvent.BUTTON_PRIMARY) -> MouseButtonPressed.Left
        isButtonPressed(MotionEvent.BUTTON_SECONDARY) -> MouseButtonPressed.Right
        isButtonPressed(MotionEvent.BUTTON_TERTIARY) -> MouseButtonPressed.Middle
        isButtonPressed(MotionEvent.BUTTON_FORWARD) -> MouseButtonPressed.Fourth
        isButtonPressed(MotionEvent.BUTTON_BACK) -> MouseButtonPressed.Fifth
        else -> MouseButtonPressed.NotApplicable
    }
}
