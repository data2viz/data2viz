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


import io.data2viz.geom.Point
import javafx.event.Event
import javafx.event.EventHandler
import javafx.scene.canvas.Canvas
import javafx.scene.input.MouseButton
import javafx.scene.input.MouseEvent
import javafx.scene.input.ScrollEvent
import javafx.scene.input.ZoomEvent
import kotlin.math.abs
import javafx.event.EventType as JfxEventType

private val emptyDisposable = object : Disposable { override fun dispose() {} }

private fun mouseButtonToMBP(mouseButton: MouseButton): MouseButtonPressed =
    when (mouseButton) {
        MouseButton.PRIMARY         -> MouseButtonPressed.Left
        MouseButton.MIDDLE          -> MouseButtonPressed.Middle
        MouseButton.SECONDARY       -> MouseButtonPressed.Right
        else                        -> MouseButtonPressed.NotApplicable
    }

private fun mouseEventToEventType(mouseEvent: MouseEvent): EventType =
    when (mouseEvent.eventType) {
        MouseEvent.MOUSE_CLICKED    -> EventType.Click
        MouseEvent.MOUSE_RELEASED   -> EventType.Up
        MouseEvent.MOUSE_PRESSED    -> EventType.Down
        MouseEvent.MOUSE_ENTERED    -> EventType.Enter
        MouseEvent.MOUSE_MOVED      -> EventType.Move
        MouseEvent.MOUSE_DRAGGED    -> EventType.Move
        MouseEvent.MOUSE_EXITED     -> EventType.Leave
        else                        -> EventType.Unknown
    }

internal actual fun pointerEventsListener(type: EventType): KEventListener<KPointerEvent> {
    require(type != EventType.Unknown)
    return object : KEventListener<KPointerEvent> {
        override fun addNativeListener(target: Any, listener: (KPointerEvent) -> EventPropagation): Disposable {
            return createSimpleJvmEventHandle(
                listener = listener,
                target = target,
                jfxEvent = when (type) {
                    EventType.Down -> MouseEvent.MOUSE_PRESSED
                    EventType.Up -> MouseEvent.MOUSE_RELEASED
                    EventType.Move -> {
                        val jfxEvents = listOf(MouseEvent.MOUSE_MOVED, MouseEvent.MOUSE_DRAGGED)
                        return createSimpleJvmEventHandle(listener, target, jfxEvents)
                    }
                    EventType.Enter -> MouseEvent.MOUSE_ENTERED
                    EventType.Leave -> MouseEvent.MOUSE_EXITED
                    EventType.Click -> MouseEvent.MOUSE_CLICKED
                    EventType.DoubleClick -> return createJvmDblClickEventHandle(target, listener)
                    EventType.Cancel -> return emptyDisposable
                    EventType.Unknown -> error("Impossible")
                }
            )
        }

    }
}

@ExperimentalKEvent
internal actual fun zoomEventsListener(): KEventListener<KZoomEvent> = object : KEventListener<KZoomEvent> {

    private val minGestureZoomDeltaValue = 0.8
    private val maxGestureZoomDeltaValue = 1.2

    private val minWheelZoomDeltaValue = -100.0
    private val maxWheelZoomDeltaValue = 100.0

    var lastZoomTime: Long? = null
    lateinit var zoomStartPoint: Point

    override fun addNativeListener(target: Any, listener: (KZoomEvent) -> EventPropagation): Disposable {
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

private fun createSimpleJvmEventHandle(
    listener: (KPointerEvent) -> EventPropagation,
    target: Any,
    jfxEvent: JfxEventType<MouseEvent>
): JvmEventHandle<MouseEvent> =
    createSimpleJvmEventHandle(listener, target, listOf(jfxEvent))

private fun createSimpleJvmEventHandle(
    listener: (KPointerEvent) -> EventPropagation,
    target: Any,
    jfxEvents: List<JfxEventType<MouseEvent>>
): JvmEventHandle<MouseEvent> {

    val eventHandler = EventHandler<MouseEvent> { event ->
        val kMouseEvent = event.toKMouseEvent(mouseEventToEventType(event))
        val eventPropagation = listener(kMouseEvent)

        if (eventPropagation.stop) event.consume()
    }
    val canvas = target as Canvas
    val jvmEventHandle = JvmEventHandle(canvas, jfxEvents, eventHandler)
    jvmEventHandle.init()
    return jvmEventHandle
}

data class JvmEventHandle<T : Event?>(
    val canvas: Canvas,
    val types: List<JfxEventType<T>>,
    val eventHandler: EventHandler<T>
) : Disposable {

    constructor(canvas: Canvas, type: JfxEventType<T>, eventHandler: EventHandler<T>) : this(
        canvas,
        listOf(type),
        eventHandler
    )

    fun init() {
        types.forEach { jfxEvent: JfxEventType<T> ->
            canvas.addEventHandler(jfxEvent, eventHandler)
        }
    }

    override fun dispose() {

        types.forEach { jfxEvent: JfxEventType<T> ->

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
    listener: (KPointerEvent) -> EventPropagation
): JvmEventHandle<MouseEvent> {
    val jfxEvent = MouseEvent.MOUSE_CLICKED
    val eventHandler = EventHandler<MouseEvent> { event ->
        if (event.clickCount == 2) {
            listener(event.toKMouseEvent(EventType.DoubleClick))
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


private fun MouseEvent.toKMouseEvent(eventType: EventType): KPointerEvent {
    val index = 0
    val pos = Point(x, y)
    return KPointerEventImpl(
        pos,
        eventType,
        PointerType.Mouse,
        mouseButtonToMBP(button),
        index,
        listOf(KPointer(index, pos)),
        isAltDown,
        isControlDown,
        isShiftDown,
        isMetaDown
    )
}

