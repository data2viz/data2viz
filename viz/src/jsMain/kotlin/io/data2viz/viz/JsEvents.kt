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
import org.w3c.dom.HTMLCanvasElement
import org.w3c.dom.HTMLElement
import org.w3c.dom.events.Event
import org.w3c.dom.events.EventListener
import org.w3c.dom.events.WheelEvent
import kotlinx.browser.window
import org.w3c.dom.events.MouseEvent
import org.w3c.dom.pointerevents.PointerEvent
import kotlin.js.Date

private fun translateMBT(jsMouseButton: Short): MouseButtonPressed {
    return when (jsMouseButton) {
        1.toShort() -> MouseButtonPressed.Middle
        2.toShort() -> MouseButtonPressed.Right
        3.toShort() -> MouseButtonPressed.Fourth
        4.toShort() -> MouseButtonPressed.Fifth
        else -> MouseButtonPressed.Left
    }
}

private fun translatePT(jsPointerType: String): PointerType {
    return when (jsPointerType) {
        "mouse" -> PointerType.Mouse
        "pen" -> PointerType.Pen
        "touch" -> PointerType.Touch
        else -> PointerType.Unknown
    }
}

/**
 * Storing all pointers by their pointerId.
 * The pointerId is not an ordered index, the choice of the pointerId is up to the browser implementation.
 */
private val pointersMap: MutableMap<Int, KPointer> = mutableMapOf()

/**
 * Returning a List of re-ordered pointers, so there is no "missing" indexes.
 */
private val allPointers
    get() = pointersMap.values.sortedBy { it.id }.mapIndexed { index, kPointer -> KPointer(index, kPointer.pos) }

internal actual fun pointerEventsListener(type: EventType): KEventListener<KPointerEvent> {
    require(type != EventType.Unknown)
    return object : KEventListener<KPointerEvent> {
        override fun addNativeListener(target: Any, listener: (KPointerEvent) -> Unit): Disposable {
            return createPointerJSListener(
                target = target,
                kListener = listener,
                jsEventName = when (type) {
                    EventType.Down -> "pointerdown"
                    EventType.Up -> "pointerup"
                    EventType.Move -> "pointermove"
                    EventType.Enter -> "pointerenter"
                    EventType.Leave -> "pointerleave"
                    EventType.Click -> "click"
                    EventType.DoubleClick -> "dblclick"
                    EventType.Cancel -> "pointercancel"
                    EventType.Unknown -> error("Impossible")
                },
                type = type
            )
        }
    }
}

@ExperimentalKEvent
internal actual fun zoomEventsListener(): KEventListener<KZoomEvent> = object : KEventListener<KZoomEvent> {

    private val minGestureZoomDeltaValue = -10.0
    private val maxGestureZoomDeltaValue = 10.0

    private val minWheelZoomDeltaValue = -100.0
    private val maxWheelZoomDeltaValue = 100.0

    private var lastZoomTime: Double? = null
    private lateinit var zoomStartPoint: Point

    override fun addNativeListener(target: Any, listener: (KZoomEvent) -> Unit): Disposable {
        val htmlElement = target.unsafeCast<HTMLCanvasElement>()
        val nativeListener = object : EventListener {
            override fun handleEvent(event: Event) {
                (event as WheelEvent).apply {

                    // don't actually zoom/scroll in browser
                    event.preventDefault()

                    // invert value to work as Android & JFX
                    val invertedDelta = deltaY * -1

                    val currentTime = Date.now()
                    if (KZoomEvent.isNewZoom(currentTime, lastZoomTime)) {
                        zoomStartPoint = pointOnCanvas(htmlElement)
                    }
                    if (event.ctrlKey) {
                        // wheel
                        listener(
                            KZoomEvent(
                                zoomStartPoint,
                                KZoomEvent.scaleDelta(
                                    invertedDelta,
                                    minWheelZoomDeltaValue,
                                    maxWheelZoomDeltaValue
                                ),
                                hasMetaKeys = HasMetaKeysImpl(
                                    event.altKey,
                                    event.ctrlKey,
                                    event.shiftKey,
                                    event.metaKey
                                )
                            )
                        )
                    } else {
                        // gesture
                        listener(
                            KZoomEvent(
                                zoomStartPoint,
                                KZoomEvent.scaleDelta(
                                    invertedDelta,
                                    minGestureZoomDeltaValue,
                                    maxGestureZoomDeltaValue
                                ),
                                hasMetaKeys = HasMetaKeysImpl(
                                    event.altKey,
                                    event.ctrlKey,
                                    event.shiftKey,
                                    event.metaKey
                                )
                            )
                        )
                    }
                    lastZoomTime = currentTime
                }
            }
        }
        return DisposableJsListener(htmlElement, "wheel", nativeListener).also { it.init() }
    }
}

private fun createPointerJSListener(
    target: Any,
    kListener: (KPointerEvent) -> Unit,
    jsEventName: String,
    type: EventType
): Disposable {
    val canvas = target.unsafeCast<HTMLCanvasElement>()
    val nativeListener = object : EventListener {
        override fun handleEvent(event: Event) {
            event.preventDefault()
            val pe = event.unsafeCast<PointerEvent>()

            val pos = pe.pointOnCanvas(canvas)
            val currentPointer = KPointer(pe.pointerId, pos)
            pointersMap[pe.pointerId] = currentPointer

            val nativeEvent = pe.toKPointerEvent(currentPointer, type)
            kListener(nativeEvent)

            if (type.toBeRemoved) pointersMap.remove(pe.pointerId)
        }
    }

    return DisposableJsListener(canvas, jsEventName, nativeListener).also { it.init() }
}

//private fun createTouchJsListener(
//    target: Any,
//    kListener: (KPointerEvent) -> Unit,
//    jsEventName: String
//): Disposable {
//    val htmlElement = target.unsafeCast<HTMLCanvasElement>()
//    val nativeListener = object : EventListener {
//        override fun handleEvent(event: Event) {
//            event.preventDefault()
//            val nativeEvent = event.toKTouchEvent(htmlElement)
//            if(nativeEvent != null)
//                kListener(nativeEvent)
//        }
//    }
//
//    return DisposableJsListener(htmlElement, jsEventName, nativeListener).also { it.init() }
//}

private data class DisposableJsListener(val htmlElement: HTMLElement, val type: String, val listener: EventListener) :
    Disposable {
    fun init() {
        htmlElement.addEventListener(type, listener)
    }

    override fun dispose() {
        htmlElement.removeEventListener(type, listener)
    }

}

internal actual fun <T> VizRenderer.addNativeEventListenerFromHandle(handle: KEventHandle<T>): Disposable where T : KEvent {
    val jsCanvasRenderer = this as JsCanvasRenderer
    return handle.eventListener.addNativeListener(jsCanvasRenderer.context.canvas, handle.listener)
}


//private data class PointerData(
//    var x: Double = .0,
//    var y: Double = .0,
//    var lastX: Double = .0,
//    var lastY: Double = .0,
//    var b1: Boolean = false,
//    var b2: Boolean = false,
//    var b3: Boolean = false
//)

//private val pointer = PointerData()

private val pixelRatio by lazy { getPixelRatio() }

/**
 * The canvas can have styling with modifies its size. KEvent uses position of the Viz.
 * So we need to convert the current mouse position to the mouse position with Canvas
 * coordinates,
 *
 * See: https://stackoverflow.com/a/43873988
 */
private fun MouseEvent.pointOnCanvas(canvas: HTMLCanvasElement): Point {
    val bounds = canvas.getBoundingClientRect()
    return Point(
        (pageX - bounds.left - window.scrollX) * canvas.width / bounds.width / pixelRatio,
        (pageY - bounds.top - window.scrollY) * canvas.height / bounds.height / pixelRatio
    )
}


private fun PointerEvent.toKPointerEvent(
    kPointer: KPointer,
    type: EventType
): KPointerEvent = KPointerEventImpl(
    pos = kPointer.pos,
    eventType = type,
    pointerType = translatePT(pointerType),
    buttonPressed = translateMBT(button),
    activePointerIndex = kPointer.id,
    pointers = allPointers,
    altKey = altKey,
    ctrlKey = ctrlKey,
    shiftKey = shiftKey,
    metaKey = metaKey
)

//private fun Event.toKTouchEvent(canvas: HTMLCanvasElement): KPointerEvent? = unsafeCast<TouchEvent>().run {
//    val bounds = canvas.getBoundingClientRect()
//
//    if (touches.length > 1)
//        return@run null
//
//    val touch = touches[0] ?: return@run KPointerEventImpl(Point(mouse.x, mouse.y), EventType.Down, PointerType.Touch, MouseButtonPressed.NotApplicable, 0, listOf(),
//        false, false, false, false)
//
//    mouse.x = touch.pageX.toDouble() - bounds.left - window.scrollX
//    mouse.y = touch.pageY.toDouble() - bounds.top  - window.scrollY
//
//    mouse.x *= canvas.width
//    mouse.y *= canvas.height
//
//    mouse.x /= bounds.width
//    mouse.y /= bounds.height
//
//    mouse.x /= pixelRatio
//    mouse.y /= pixelRatio
//
//    KPointerEventImpl(Point(mouse.x, mouse.y), EventType.Down, PointerType.Touch, MouseButtonPressed.NotApplicable, 0, listOf(),
//        false, false, false, false)
//}

