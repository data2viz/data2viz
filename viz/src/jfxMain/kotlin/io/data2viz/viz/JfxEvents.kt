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


import io.data2viz.geom.Point
import javafx.event.Event
import javafx.event.EventHandler
import javafx.event.EventType
import javafx.scene.canvas.Canvas
import javafx.scene.input.MouseButton
import javafx.scene.input.MouseEvent
import javafx.scene.input.ScrollEvent
import javafx.scene.input.ZoomEvent
import kotlin.math.abs

private val emptyDisposable = object : Disposable { override fun dispose() {} }

private fun mouseButtonToMBP(mouseButton: MouseButton): MouseButtonPressed =
    when (mouseButton) {
        MouseButton.PRIMARY         -> MouseButtonPressed.Left
        MouseButton.MIDDLE          -> MouseButtonPressed.Middle
        MouseButton.SECONDARY       -> MouseButtonPressed.Right
        else                        -> MouseButtonPressed.NotApplicable
    }

private fun mouseEventToMET(mouseEvent: MouseEvent): MouseEventType =
    when (mouseEvent.eventType) {
        MouseEvent.MOUSE_CLICKED    -> MouseEventType.Click
        MouseEvent.MOUSE_RELEASED   -> MouseEventType.Up
        MouseEvent.MOUSE_PRESSED    -> MouseEventType.Down
        MouseEvent.MOUSE_ENTERED    -> MouseEventType.Enter
        MouseEvent.MOUSE_MOVED      -> MouseEventType.Move
        MouseEvent.MOUSE_DRAGGED    -> MouseEventType.Move
        MouseEvent.MOUSE_EXITED     -> MouseEventType.Leave
        else                        -> MouseEventType.Unknown
    }

public actual class KTouch {
    public actual companion object TouchEventListener : KEventListener<KTouchEvent> {
        override fun addNativeListener(target: Any, listener: (KTouchEvent) -> Unit): Disposable = emptyDisposable
    }
}

public actual class KTouchStart {
    public actual companion object TouchStartEventListener : KEventListener<KPointerEvent> {
        override fun addNativeListener(target: Any, listener: (KPointerEvent) -> Unit): Disposable = emptyDisposable
    }
}

public actual class KTouchEnd {
    public actual companion object TouchEndEventListener : KEventListener<KPointerEvent> {
        override fun addNativeListener(target: Any, listener: (KPointerEvent) -> Unit): Disposable = emptyDisposable
    }
}

public actual class KTouchMove {
    public actual companion object TouchMoveEventListener : KEventListener<KPointerEvent> {
        override fun addNativeListener(target: Any, listener: (KPointerEvent) -> Unit): Disposable = emptyDisposable
    }
}

public actual class KTouchCancel {
    public actual companion object TouchCancelEventListener : KEventListener<KPointerEvent> {
        override fun addNativeListener(target: Any, listener: (KPointerEvent) -> Unit): Disposable = emptyDisposable
    }
}


public actual class KMouseDown {
    public actual companion object MouseDownEventListener : KEventListener<KMouseEvent> {
        override fun addNativeListener(target: Any, listener: (KMouseEvent) -> Unit): Disposable =
            createSimpleJvmEventHandle(listener, target, MouseEvent.MOUSE_PRESSED)
    }
}

public actual class KMouseUp {
    public actual companion object MouseUpEventListener : KEventListener<KMouseEvent> {
        override fun addNativeListener(target: Any, listener: (KMouseEvent) -> Unit): Disposable =
            createSimpleJvmEventHandle(listener, target, MouseEvent.MOUSE_RELEASED)
    }
}

public actual class KMouseMove {
    public actual companion object MouseMoveEventListener : KEventListener<KMouseEvent> {
        override fun addNativeListener(target: Any, listener: (KMouseEvent) -> Unit): Disposable {

            // Add listeners for both events MOVED & DRAGGED, because MOVED not fires when any button pressed
            // but JS behaviour is different
            val jfxEvents = listOf(MouseEvent.MOUSE_MOVED, MouseEvent.MOUSE_DRAGGED)
            return createSimpleJvmEventHandle(listener, target, jfxEvents)
        }
    }
}

public actual class KMouseEnter {
    public actual companion object MouseEnterEventListener : KEventListener<KMouseEvent> {
        override fun addNativeListener(target: Any, listener: (KMouseEvent) -> Unit): Disposable =
            createSimpleJvmEventHandle(listener, target, MouseEvent.MOUSE_ENTERED)
    }
}

public actual class KMouseLeave {

    public actual companion object MouseLeaveEventListener : KEventListener<KMouseEvent> {
        override fun addNativeListener(target: Any, listener: (KMouseEvent) -> Unit): Disposable =
            createSimpleJvmEventHandle(listener, target, MouseEvent.MOUSE_EXITED)
    }
}

public actual class KMouseClick {
    public actual companion object MouseClickEventListener : KEventListener<KMouseEvent> {
        override fun addNativeListener(target: Any, listener: (KMouseEvent) -> Unit): Disposable =
            createSimpleJvmEventHandle(listener, target, MouseEvent.MOUSE_CLICKED)
    }
}

public actual class KMouseDoubleClick {
    public actual companion object MouseDoubleClickEventListener : KEventListener<KMouseEvent> {
        override fun addNativeListener(target: Any, listener: (KMouseEvent) -> Unit): Disposable =
            createJvmDblClickEventHandle(target, listener)
    }
}

@ExperimentalKEvent
public actual class KZoom {
    public actual companion object ZoomEventListener : KEventListener<KZoomEvent> {
        const val minGestureZoomDeltaValue = 0.8
        const val maxGestureZoomDeltaValue = 1.2

        const val minWheelZoomDeltaValue = -100.0
        const val maxWheelZoomDeltaValue = 100.0

        var lastZoomTime: Long? = null
        lateinit var zoomStartPoint: Point

        override fun addNativeListener(target: Any, listener: (KZoomEvent) -> Unit): Disposable {

            val canvas = target as Canvas

            val zoomHandler = EventHandler<ZoomEvent> { event ->
                // GESTURE
                val currentDelta = event.zoomFactor
                listener(
                    onZoom(
                        event.x, event.y,
                        currentDelta,
                        minGestureZoomDeltaValue,
                        maxGestureZoomDeltaValue,
                        HasMetaKeysImpl(event.isAltDown, event.isControlDown, event.isShiftDown, event.isMetaDown)
                    )
                )
            }


            val scrollHandler = EventHandler<ScrollEvent> { event ->
                // WHEEL
                var currentDelta = event.deltaY

                // BUG if shift key is down, on windows, then the "scroll" switch from vertical to horizontal
                // https://bugs.openjdk.java.net/browse/JDK-8097935
                // to avoid this, check the deltaX value and switch to if needed
                if (event.isShiftDown && abs(currentDelta) < 1e-3) currentDelta = event.deltaX

                listener(
                    onZoom(
                        event.x, event.y,
                        currentDelta,
                        minWheelZoomDeltaValue,
                        maxWheelZoomDeltaValue,
                        HasMetaKeysImpl(event.isAltDown, event.isControlDown, event.isShiftDown, event.isMetaDown)
                    )
                )
            }

            return JvmZoomHandle(canvas, scrollHandler, zoomHandler).also { it.init() }
        }

        private fun onZoom(
            x: Double, y: Double, currentDelta: Double,
            minDelta: Double,
            maxDelta: Double,
            hasMetaKeys: HasMetaKeys,
        ): KZoomEvent {

            val currentTime = System.currentTimeMillis()
            if (KZoomEvent.isNewZoom(currentTime, lastZoomTime)) {
                zoomStartPoint = Point(x, y)
            }
            lastZoomTime = currentTime
            return KZoomEvent(
                zoomStartPoint,
                KZoomEvent.scaleDelta(
                    currentDelta,
                    minDelta,
                    maxDelta
                ),
                hasMetaKeys
            )
        }
    }
}


private fun createSimpleJvmEventHandle(
    listener: (KMouseEvent) -> Unit,
    target: Any,
    jfxEvent: EventType<MouseEvent>
): JvmEventHandle<MouseEvent> =
    createSimpleJvmEventHandle(listener, target, listOf(jfxEvent))

private fun createSimpleJvmEventHandle(
    listener: (KMouseEvent) -> Unit,
    target: Any,
    jfxEvents: List<EventType<MouseEvent>>
): JvmEventHandle<MouseEvent> {

    val eventHandler = EventHandler<MouseEvent> { event ->
        val kMouseEvent = event.toKMouseEvent(mouseEventToMET(event))
        listener(kMouseEvent)
    }
    val canvas = target as Canvas
    val jvmEventHandle = JvmEventHandle(canvas, jfxEvents, eventHandler)
    jvmEventHandle.init()
    return jvmEventHandle
}

data class JvmEventHandle<T : Event?>(
    val canvas: Canvas,
    val types: List<EventType<T>>,
    val eventHandler: EventHandler<T>
) : Disposable {

    constructor(canvas: Canvas, type: EventType<T>, eventHandler: EventHandler<T>) : this(
        canvas,
        listOf(type),
        eventHandler
    )

    fun init() {
        types.forEach { jfxEvent: EventType<T> ->
            canvas.addEventHandler(jfxEvent, eventHandler)
        }
    }

    override fun dispose() {

        types.forEach { jfxEvent: EventType<T> ->

            canvas.removeEventHandler(jfxEvent, eventHandler)
        }
    }
}

data class JvmZoomHandle(
    val canvas: Canvas,
    val scrollHandler: EventHandler<ScrollEvent>,
    val zoomHandler: EventHandler<ZoomEvent>
) : Disposable {

    fun init() {
        canvas.onScroll = scrollHandler
        canvas.onZoom = zoomHandler
    }

    override fun dispose() {
        canvas.onScroll = null
        canvas.onZoom = null
    }
}

private fun createJvmDblClickEventHandle(
    target: Any,
    listener: (KMouseEvent) -> Unit
): JvmEventHandle<MouseEvent> {
    val jfxEvent = MouseEvent.MOUSE_CLICKED
    val eventHandler = EventHandler<MouseEvent> { event ->
        if (event.clickCount == 2) {
            listener(event.toKMouseEvent(MouseEventType.DoubleClick))
        }
    }
    val canvas = target as Canvas
    return JvmEventHandle(canvas, jfxEvent, eventHandler).also {
        it.init()
    }
}


internal actual fun <T> VizRenderer.addNativeEventListenerFromHandle(handle: KEventHandle<T>): Disposable where T : KEvent {
    return (this as? JFxVizRenderer)?.let {
        handle.eventListener.addNativeListener(it.canvas, handle.listener)
    } ?: object : Disposable { // for the test
            override fun dispose() {
        }
    }
}


private fun MouseEvent.toKMouseEvent(mouseEventType: MouseEventType): KMouseEvent = KMouseEvent(
    Point(x, y),
    mouseEventType,
    mouseButtonToMBP(button),
    isAltDown,
    isControlDown,
    isShiftDown,
    isMetaDown
)

