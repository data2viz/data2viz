package io.data2viz.viz


import io.data2viz.geom.Point
import org.w3c.dom.HTMLElement
import org.w3c.dom.events.Event
import org.w3c.dom.events.EventListener
import org.w3c.dom.events.MouseEvent
import org.w3c.dom.events.WheelEvent
import kotlin.js.Date


actual class KPointerDown {
    actual companion object PointerDownEventListener : KEventListener<KPointerEvent> {
        override fun addNativeListener(target: Any, listener: (KPointerEvent) -> Unit): Disposable =
            createJsListener(target, listener, "mousedown")
    }
}

actual class KPointerUp {
    actual companion object PointerUpEventListener : KEventListener<KPointerEvent> {
        override fun addNativeListener(target: Any, listener: (KPointerEvent) -> Unit): Disposable =
            createJsListener(target, listener, "mouseup")
    }
}

actual class KPointerEnter {
    actual companion object PointerEnterEventListener : KEventListener<KPointerEvent> {
        override fun addNativeListener(target: Any, listener: (KPointerEvent) -> Unit): Disposable =
            createJsListener(target, listener, "mouseenter")
    }
}

actual class KPointerLeave {
    actual companion object PointerLeaveEventListener : KEventListener<KPointerEvent> {
        override fun addNativeListener(target: Any, listener: (KPointerEvent) -> Unit): Disposable =
            createJsListener(target, listener, "mouseleave")
    }
}


actual class KPointerDoubleClick {
    actual companion object PointerDoubleClickEventListener : KEventListener<KPointerEvent> {
        override fun addNativeListener(target: Any, listener: (KPointerEvent) -> Unit): Disposable =
            createJsListener(target, listener, "dblclick")
    }
}

actual class KPointerMove {
    actual companion object PointerMoveEventListener : KEventListener<KPointerEvent> {
        override fun addNativeListener(target: Any, listener: (KPointerEvent) -> Unit): Disposable =
            createJsListener(target, listener, "mousemove")
    }
}

actual class KPointerClick {
    actual companion object PointerClickEventListener : KEventListener<KPointerEvent> {
        override fun addNativeListener(target: Any, listener: (KPointerEvent) -> Unit): Disposable =
            createJsListener(target, listener, "click")
    }
}

@ExperimentalKZoomEvent
actual class KZoom {
    actual companion object ZoomEventListener : KEventListener<KZoomEvent> {

        const val minGestureZoomDeltaValue = -10.0
        const val maxGestureZoomDeltaValue = 10.0

        const val minWheelZoomDeltaValue = -100.0
        const val maxWheelZoomDeltaValue = 100.0

        var lastZoomTime: Double? = null
        lateinit var zoomStartPoint: Point

        override fun addNativeListener(target: Any, listener: (KZoomEvent) -> Unit): Disposable {
            val htmlElement = target.unsafeCast<HTMLElement>()
            val nativeListener = object : EventListener {
                override fun handleEvent(event: Event) {
                    (event as WheelEvent).apply {
                        // don't actually zoom/scroll in browser
                        event.preventDefault()
                        // invert value to work as Android & JFX
                        val invertedDelta = deltaY * -1

                        val currentTime = Date.now()
                        if(KZoomEvent.isNewZoom(currentTime, lastZoomTime)) {
                            zoomStartPoint =  Point(clientX.toDouble() - htmlElement.offsetLeft, clientY.toDouble() - htmlElement.offsetTop)
                        }
                        if (event.ctrlKey) {
                            // wheel
                            listener(
                                KZoomEvent(
                                    zoomStartPoint,
                                    KZoomEvent.scaleDelta(invertedDelta, minWheelZoomDeltaValue, maxWheelZoomDeltaValue)
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
                                    )
                                )
                            )
                        }
                        lastZoomTime = currentTime
                    }
                }
            }
            return JsListener(htmlElement, "wheel", nativeListener).also { it.init() }
        }
    }
}


private fun createJsListener(
    target: Any,
    listener: (KPointerEvent) -> Unit,
    jsEventName: String
): JsListener {
    val htmlElement = target.unsafeCast<HTMLElement>()
    val nativeListener = object : EventListener {
        override fun handleEvent(event: Event) {
            val nativeEvent = event.convertToKEvent(htmlElement)
            listener(nativeEvent)
        }
    }

    return JsListener(htmlElement, jsEventName, nativeListener).also { it.init() }
}

data class JsListener(val htmlElement: HTMLElement, val type: String, val listener: EventListener) :
    Disposable {
    fun init() {
        htmlElement.addEventListener(type, listener)
    }

    override fun dispose() {
        htmlElement.removeEventListener(type, listener)
    }

}


actual fun <T> VizRenderer.addNativeEventListenerFromHandle(handle: KEventHandle<T>): Disposable where T : KEvent {
    val jsCanvasRenderer = this as JsCanvasRenderer
    return handle.eventListener.addNativeListener(jsCanvasRenderer.context.canvas, handle.listener)
}


fun Event.convertToKEvent(target: HTMLElement): KPointerEvent = unsafeCast<MouseEvent>().run {
    val kPointerMoveEvent =
        KMouseEvent(
            Point(clientX.toDouble() - target.offsetLeft, clientY.toDouble() - target.offsetTop),
            this.altKey,
            this.ctrlKey,
            this.shiftKey,
            this.metaKey
        )
    kPointerMoveEvent
}
