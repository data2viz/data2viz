package io.data2viz.viz


import io.data2viz.geom.Point
import org.w3c.dom.HTMLElement
import org.w3c.dom.events.Event
import org.w3c.dom.events.EventListener
import org.w3c.dom.events.MouseEvent

//fun <T> Element.on(eventListener: KEventListener<T>, listener: (T) -> Unit): Any {
//    return eventListener.addNativeListener(this, listener)
//}
//
//private fun Element.removeListener(listener: Any) {
//    val jsListener = listener.unsafeCast<JsListener>()
//    this.removeEventListener(jsListener.type, jsListener.listener as EventListener, null)
//}

actual class KPointerDown {
    actual companion object MouseDownEventListener : KEventListener<KPointerEvent> {
        override fun addNativeListener(target: Any, listener: (KPointerEvent) -> Unit): Disposable =
            createJsListener(target, listener, "mousedown")
    }
}

actual class KPointerUp {
    actual companion object MouseUpEventListener : KEventListener<KPointerEvent> {
        override fun addNativeListener(target: Any, listener: (KPointerEvent) -> Unit): Disposable =
            createJsListener(target, listener, "mouseup")
    }
}

actual class KPointerEnter {
    actual companion object MouseEnterEventListener : KEventListener<KPointerEvent> {
        override fun addNativeListener(target: Any, listener: (KPointerEvent) -> Unit): Disposable =
            createJsListener(target, listener, "mouseenter")
    }
}

actual class KPointerLeave {
    actual companion object MouseLeaveEventListener : KEventListener<KPointerEvent> {
        override fun addNativeListener(target: Any, listener: (KPointerEvent) -> Unit): Disposable =
            createJsListener(target, listener, "mouseleave")
    }
}


actual class KPointerDoubleClick {
    actual companion object MouseDoubleClickEventListener : KEventListener<KPointerEvent> {
        override fun addNativeListener(target: Any, listener: (KPointerEvent) -> Unit): Disposable =
            createJsListener(target, listener, "dblclick")
    }
}

actual class KPointerMove {
    actual companion object MouseMoveEventListener : KEventListener<KPointerEvent> {
        override fun addNativeListener(target: Any, listener: (KPointerEvent) -> Unit): Disposable =
            createJsListener(target, listener, "mousemove")
    }
}

actual class KPointerClick {
    actual companion object MouseClickEventListener : KEventListener<KPointerEvent> {
        override fun addNativeListener(target: Any, listener: (KPointerEvent) -> Unit): Disposable =
            createJsListener(target, listener, "click")
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
