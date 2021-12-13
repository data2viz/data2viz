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
import org.w3c.dom.HTMLCanvasElement
import org.w3c.dom.HTMLElement
import org.w3c.dom.events.Event
import org.w3c.dom.events.EventListener
import org.w3c.dom.events.MouseEvent
import org.w3c.dom.events.WheelEvent
import kotlinx.browser.window
import kotlin.js.Date


public actual class KMouseDown {
    public actual companion object PointerDownEventListener : KEventListener<KMouseEvent> {
        override fun addNativeListener(target: Any, listener: (KMouseEvent) -> Unit): Disposable =
            createMouseJsListener(target, listener, "mousedown")
    }
}

public actual class KMouseUp {
    public actual companion object PointerUpEventListener : KEventListener<KMouseEvent> {
        override fun addNativeListener(target: Any, listener: (KMouseEvent) -> Unit): Disposable =
            createMouseJsListener(target, listener, "mouseup")
    }
}

public actual class KPointerEnter {
    public actual companion object PointerEnterEventListener : KEventListener<KPointerEvent> {
        override fun addNativeListener(target: Any, listener: (KPointerEvent) -> Unit): Disposable =
            createMouseJsListener(target, listener, "mouseenter")
    }
}

public actual class KMouseMove {
    public actual companion object PointerMoveEventListener : KEventListener<KMouseEvent> {
        override fun addNativeListener(target: Any, listener: (KMouseEvent) -> Unit): Disposable =
            createMouseJsListener(target, listener, "mousemove")
    }
}

private val emptyDisposable = object : Disposable { override fun dispose() {} }


public actual class KTouch {
    public actual companion object TouchEventListener : KEventListener<KTouchEvent> {
        override fun addNativeListener(target: Any, listener: (KTouchEvent) -> Unit): Disposable = emptyDisposable
    }
}

public actual class KTouchStart {
    public actual companion object TouchStartEventListener : KEventListener<KPointerEvent> {
        override fun addNativeListener(target: Any, listener: (KPointerEvent) -> Unit): Disposable =
            createTouchJsListener(target, listener, "touchstart")
    }
}

public actual class KTouchEnd {
    public actual companion object TouchEndEventListener : KEventListener<KPointerEvent> {
        override fun addNativeListener(target: Any, listener: (KPointerEvent) -> Unit): Disposable =
            createTouchJsListener(target, listener, "touchend")
    }
}

public actual class KTouchMove {
    public actual companion object TouchMoveEventListener : KEventListener<KPointerEvent> {
        override fun addNativeListener(target: Any, listener: (KPointerEvent) -> Unit): Disposable =
            createTouchJsListener(target, listener, "touchmove")
    }
}


public actual class KPointerLeave {
    public actual companion object PointerLeaveEventListener : KEventListener<KPointerEvent> {
        override fun addNativeListener(target: Any, listener: (KPointerEvent) -> Unit): Disposable =
            createMouseJsListener(target, listener, "mouseleave")
    }
}


public actual class KPointerDoubleClick {
    public actual companion object PointerDoubleClickEventListener : KEventListener<KPointerEvent> {
        override fun addNativeListener(target: Any, listener: (KPointerEvent) -> Unit): Disposable =
            createMouseJsListener(target, listener, "dblclick")
    }
}


public actual class KPointerClick {
    public actual companion object PointerClickEventListener : KEventListener<KPointerEvent> {
        override fun addNativeListener(target: Any, listener: (KPointerEvent) -> Unit): Disposable =
            createMouseJsListener(target, listener, "click")
    }
}

@ExperimentalKEvent
public actual class KZoom {
    public actual companion object ZoomEventListener : KEventListener<KZoomEvent> {

        private const val minGestureZoomDeltaValue = -10.0
        private const val maxGestureZoomDeltaValue = 10.0

        private const val minWheelZoomDeltaValue = -100.0
        private const val maxWheelZoomDeltaValue = 100.0

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
                                    KZoomEvent.scaleDelta(invertedDelta, minWheelZoomDeltaValue, maxWheelZoomDeltaValue),
                                    hasMetaKeys = HasMetaKeysImpl(event.altKey, event.ctrlKey, event.shiftKey, event.metaKey)
                                )
                            )
                        } else {
                            // gesture
                            listener(
                                KZoomEvent(
                                    zoomStartPoint,
                                    KZoomEvent.scaleDelta(invertedDelta, minGestureZoomDeltaValue, maxGestureZoomDeltaValue),
                                    hasMetaKeys = HasMetaKeysImpl(event.altKey, event.ctrlKey, event.shiftKey, event.metaKey)
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
}


private fun createMouseJsListener(
    target: Any,
    kListener: (KMouseEvent) -> Unit,
    jsEventName: String
): Disposable {
    val htmlElement = target.unsafeCast<HTMLCanvasElement>()
    val nativeListener = object : EventListener {
        override fun handleEvent(event: Event) {
            val nativeEvent = event.toKMouseEvent(htmlElement)
            kListener(nativeEvent)
        }
    }

    return DisposableJsListener(htmlElement, jsEventName, nativeListener).also { it.init() }
}

private fun createTouchJsListener(
    target: Any,
    kListener: (KPointerEvent) -> Unit,
    jsEventName: String
): Disposable {
    val htmlElement = target.unsafeCast<HTMLCanvasElement>()
    val nativeListener = object : EventListener {
        override fun handleEvent(event: Event) {
            val nativeEvent = event.toKTouchEvent(htmlElement)
            if(nativeEvent != null)
                kListener(nativeEvent)
        }
    }

    return DisposableJsListener(htmlElement, jsEventName, nativeListener).also { it.init() }
}

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


private data class MouseData(
    var x: Double = .0,
    var y: Double = .0,
    var lastX: Double = .0,
    var lastY: Double = .0,
    var b1: Boolean = false,
    var b2: Boolean = false,
    var b3: Boolean = false
)

private val mouse = MouseData()

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
    mouse.x = pageX - bounds.left - window.scrollX
    mouse.y = pageY - bounds.top  - window.scrollY

    mouse.x *= canvas.width
    mouse.y *= canvas.height

    mouse.x /= bounds.width
    mouse.y /= bounds.height

    mouse.x /= pixelRatio
    mouse.y /= pixelRatio
    return Point(mouse.x, mouse.y)
}


private fun Event.toKMouseEvent(canvas: HTMLCanvasElement): KMouseEvent = unsafeCast<MouseEvent>().run {
    KMouseEvent(
        this.pointOnCanvas(canvas),
        altKey,
        ctrlKey,
        shiftKey,
        metaKey
    )
}

private fun Event.toKTouchEvent(canvas: HTMLCanvasElement): KPointerEvent? = unsafeCast<TouchEvent>().run {
    val bounds = canvas.getBoundingClientRect()

    if (touches.length > 1)
        return@run null

    val touch = touches[0] ?: return@run KPointerEventImpl(Point(mouse.x, mouse.y))

    mouse.x = touch.pageX.toDouble() - bounds.left - window.scrollX
    mouse.y = touch.pageY.toDouble() - bounds.top  - window.scrollY

    mouse.x *= canvas.width
    mouse.y *= canvas.height

    mouse.x /= bounds.width
    mouse.y /= bounds.height

    mouse.x /= pixelRatio
    mouse.y /= pixelRatio


    KPointerEventImpl(
        Point(mouse.x, mouse.y)
    )
}

