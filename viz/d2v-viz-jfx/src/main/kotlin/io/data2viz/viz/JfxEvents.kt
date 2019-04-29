package io.data2viz.viz


import io.data2viz.geom.Point
import javafx.event.Event
import javafx.event.EventType
import javafx.scene.canvas.Canvas
import javafx.scene.input.MouseEvent


actual class KMouseDown {
    actual companion object MouseDownEventListener : KEventListener<KMouseEvent> {
        override fun addNativeListener(target: Any, listener: (KMouseEvent) -> Unit): Any =
            createSimpleJvmEventHandle(listener, target, MouseEvent.MOUSE_PRESSED)
    }
}

actual class KMouseUp {
    actual companion object MouseUpEventListener : KEventListener<KMouseEvent> {
        override fun addNativeListener(target: Any, listener: (KMouseEvent) -> Unit): Any =
            createSimpleJvmEventHandle(listener, target, MouseEvent.MOUSE_RELEASED)
    }
}

actual class KMouseEnter {
    actual companion object MouseEnterEventListener : KEventListener<KMouseEvent> {
        override fun addNativeListener(target: Any, listener: (KMouseEvent) -> Unit): Any =
            createSimpleJvmEventHandle(listener, target, MouseEvent.MOUSE_ENTERED)
    }
}

actual class KMouseLeave {
    actual companion object MouseLeaveEventListener : KEventListener<KMouseEvent> {
        override fun addNativeListener(target: Any, listener: (KMouseEvent) -> Unit): Any =
            createSimpleJvmEventHandle(listener, target, MouseEvent.MOUSE_EXITED)
    }
}


actual class KMouseDoubleClick {
    actual companion object MouseDoubleClickEventListener : KEventListener<KMouseEvent> {
        override fun addNativeListener(target: Any, listener: (KMouseEvent) -> Unit): Any {

            val handler: (MouseEvent) -> Unit = { evt: MouseEvent ->
                if (evt.clickCount == 2) {
                    val kevent = evt.convertToKEvent()
                    listener(kevent)
                }
            }
            val jfxEvent = MouseEvent.MOUSE_CLICKED
            (target as Canvas).addEventHandler(jfxEvent, handler)
            return JvmEventHandle(jfxEvent, handler)
        }
    }
}

actual class KMouseMove {
    actual companion object MouseMoveEventListener : KEventListener<KMouseEvent> {
        override fun addNativeListener(target: Any, listener: (KMouseEvent) -> Unit): Any {
            // Add listeners for both events MOVED & DRAGGED, because MOVED not fires when any button pressed
            // but JS behaviour is different

            val handler: (MouseEvent) -> Unit = { evt: MouseEvent ->
                val kevent = evt.convertToKEvent()
                listener(kevent)
            }
            val jfxEvents = listOf(MouseEvent.MOUSE_DRAGGED, MouseEvent.MOUSE_MOVED)
            jfxEvents.forEach {
                (target as Canvas).addEventHandler(it, handler)
            }
            return JvmEventHandle(jfxEvents, handler)

        }
    }
}

actual class KMouseClick {
    actual companion object MouseClickEventListener : KEventListener<KMouseEvent> {
        override fun addNativeListener(target: Any, listener: (KMouseEvent) -> Unit): Any =
            createSimpleJvmEventHandle(listener, target, MouseEvent.MOUSE_CLICKED)
    }
}

private fun createSimpleJvmEventHandle(
    listener: (KMouseEvent) -> Unit,
    target: Any,
    jfxEvent: EventType<MouseEvent>
): JvmEventHandle<MouseEvent> {
    val handler: (MouseEvent) -> Unit = { evt: MouseEvent ->
        val kevent = evt.convertToKEvent()
        listener(kevent)
    }
    (target as Canvas).addEventHandler(jfxEvent, handler)
    return JvmEventHandle(jfxEvent, handler)
}

data class JvmEventHandle<T : Event?>(val types: List<EventType<T>>, val handler: (MouseEvent) -> Unit) {
    constructor(type: EventType<T>, handler: (MouseEvent) -> Unit) : this(listOf(type), handler)
}

/**
 * Add an event listener on a a viz.
 * @return an handler to eventually remove later.
 */
actual fun <T> Viz.on(
    eventListener: KEventListener<T>,
    listener: (T) -> Unit
): Any {
    val jFxVizRenderer = this.renderer as JFxVizRenderer
    return eventListener.addNativeListener(jFxVizRenderer.canvas, listener)
}


/**
 *
 */
private fun MouseEvent.convertToKEvent(): KMouseEvent {
    val kMouseMoveEvent = KMouseEvent(
        Point(x, y),
        this.isAltDown,
        this.isControlDown,
        this.isShiftDown,
        this.isMetaDown
    )
    return kMouseMoveEvent
}