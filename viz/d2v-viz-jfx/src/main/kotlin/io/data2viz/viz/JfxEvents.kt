package io.data2viz.viz


import io.data2viz.geom.Point
import javafx.event.Event
import javafx.event.EventHandler
import javafx.event.EventType
import javafx.scene.canvas.Canvas
import javafx.scene.input.MouseEvent


actual class KPointerDown {
    actual companion object MouseDownEventListener : KEventListener<KPointerEvent> {
        override fun addNativeListener(target: Any, listener: (KPointerEvent) -> Unit): Disposable =
            createSimpleJvmEventHandle(listener, target, MouseEvent.MOUSE_PRESSED)
    }
}

actual class KPointerUp {
    actual companion object MouseUpEventListener : KEventListener<KPointerEvent> {
        override fun addNativeListener(target: Any, listener: (KPointerEvent) -> Unit): Disposable =
            createSimpleJvmEventHandle(listener, target, MouseEvent.MOUSE_RELEASED)
    }
}

actual class KPointerEnter {
    actual companion object MouseEnterEventListener : KEventListener<KPointerEvent> {
        override fun addNativeListener(target: Any, listener: (KPointerEvent) -> Unit): Disposable =
            createSimpleJvmEventHandle(listener, target, MouseEvent.MOUSE_ENTERED)
    }
}

actual class KPointerLeave {
    actual companion object MouseLeaveEventListener : KEventListener<KPointerEvent> {
        override fun addNativeListener(target: Any, listener: (KPointerEvent) -> Unit): Disposable =
            createSimpleJvmEventHandle(listener, target, MouseEvent.MOUSE_EXITED)
    }
}


actual class KPointerDoubleClick {
    actual companion object MouseDoubleClickEventListener : KEventListener<KPointerEvent> {
        override fun addNativeListener(target: Any, listener: (KPointerEvent) -> Unit): Disposable =
            createJvmClickEventHandle(target, listener, eventClickCount = 2)
    }
}

actual class KPointerMove {
    actual companion object MouseMoveEventListener : KEventListener<KPointerEvent> {
        override fun addNativeListener(target: Any, listener: (KPointerEvent) -> Unit): Disposable {

            // Add listeners for both events MOVED & DRAGGED, because MOVED not fires when any button pressed
            // but JS behaviour is different
            val jfxEvents = listOf(MouseEvent.MOUSE_MOVED, MouseEvent.MOUSE_DRAGGED)
            return createSimpleJvmEventHandle(listener, target, jfxEvents)
        }
    }
}

actual class KPointerClick {
    actual companion object MouseClickEventListener : KEventListener<KPointerEvent> {
        override fun addNativeListener(target: Any, listener: (KPointerEvent) -> Unit): Disposable =
            createSimpleJvmEventHandle(listener, target, MouseEvent.MOUSE_CLICKED)
    }
}

private fun createSimpleJvmEventHandle(
    listener: (KPointerEvent) -> Unit,
    target: Any,
    jfxEvent: EventType<MouseEvent>
): JvmEventHandle<MouseEvent> =
    createSimpleJvmEventHandle(listener, target, listOf(jfxEvent))

private fun createSimpleJvmEventHandle(
    listener: (KPointerEvent) -> Unit,
    target: Any,
    jfxEvents: List<EventType<MouseEvent>>
): JvmEventHandle<MouseEvent> {

    val eventHandler = EventHandler<MouseEvent> { event ->
        val kevent = event.convertToKEvent()
        listener(kevent)
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

private fun createJvmClickEventHandle(
    target: Any,
    listener: (KPointerEvent) -> Unit,
    eventClickCount: Int
): JvmEventHandle<MouseEvent> {
    val jfxEvent = MouseEvent.MOUSE_CLICKED

    val eventHandler = EventHandler<MouseEvent> { event ->
        if (event.clickCount == eventClickCount) {
            val kevent = event.convertToKEvent()
            listener(kevent)
        }
    }
    val canvas = target as Canvas
    return JvmEventHandle(canvas, jfxEvent, eventHandler).also {
        it.init()
    }
}

actual fun <T> VizRenderer.addNativeEventListenerFromHandle(handle: KEventHandle<T>): Disposable where T : KEvent {
    val jFxVizRenderer = this as JFxVizRenderer
    return handle.eventListener.addNativeListener(jFxVizRenderer.canvas, handle.listener)
}


/**
 *
 */
private fun MouseEvent.convertToKEvent(): KPointerEvent {
    val kPointerMoveEvent = KMouseEvent(
        Point(x, y),
        this.isAltDown,
        this.isControlDown,
        this.isShiftDown,
        this.isMetaDown
    )
    return kPointerMoveEvent
}