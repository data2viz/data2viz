package io.data2viz.viz


import io.data2viz.geom.Point
import javafx.event.Event
import javafx.event.EventHandler
import javafx.event.EventType
import javafx.scene.canvas.Canvas
import javafx.scene.input.MouseEvent
import javafx.scene.input.ScrollEvent
import javafx.scene.input.ZoomEvent


actual class KPointerDown {
    actual companion object PointerDownEventListener : KEventListener<KPointerEvent> {
        override fun addNativeListener(target: Any, listener: (KPointerEvent) -> Unit): Disposable =
            createSimpleJvmEventHandle(listener, target, MouseEvent.MOUSE_PRESSED)
    }
}

actual class KPointerUp {
    actual companion object PointerUpEventListener : KEventListener<KPointerEvent> {
        override fun addNativeListener(target: Any, listener: (KPointerEvent) -> Unit): Disposable =
            createSimpleJvmEventHandle(listener, target, MouseEvent.MOUSE_RELEASED)
    }
}

actual class KPointerEnter {
    actual companion object PointerEnterEventListener : KEventListener<KPointerEvent> {
        override fun addNativeListener(target: Any, listener: (KPointerEvent) -> Unit): Disposable =
            createSimpleJvmEventHandle(listener, target, MouseEvent.MOUSE_ENTERED)
    }
}

actual class KPointerLeave {
    actual companion object PointerLeaveEventListener : KEventListener<KPointerEvent> {
        override fun addNativeListener(target: Any, listener: (KPointerEvent) -> Unit): Disposable =
            createSimpleJvmEventHandle(listener, target, MouseEvent.MOUSE_EXITED)
    }
}


actual class KPointerDoubleClick {
    actual companion object PointerDoubleClickEventListener : KEventListener<KPointerEvent> {
        override fun addNativeListener(target: Any, listener: (KPointerEvent) -> Unit): Disposable =
            createJvmClickEventHandle(target, listener, eventClickCount = 2)
    }
}

actual class KPointerMove {
    actual companion object PointerMoveEventListener : KEventListener<KPointerEvent> {
        override fun addNativeListener(target: Any, listener: (KPointerEvent) -> Unit): Disposable {

            // Add listeners for both events MOVED & DRAGGED, because MOVED not fires when any button pressed
            // but JS behaviour is different
            val jfxEvents = listOf(MouseEvent.MOUSE_MOVED, MouseEvent.MOUSE_DRAGGED)
            return createSimpleJvmEventHandle(listener, target, jfxEvents)
        }
    }
}

actual class KPointerClick {
    actual companion object PointerClickEventListener : KEventListener<KPointerEvent> {
        override fun addNativeListener(target: Any, listener: (KPointerEvent) -> Unit): Disposable =
            createSimpleJvmEventHandle(listener, target, MouseEvent.MOUSE_CLICKED)
    }
}

actual class KZoom {
    actual companion object ZoomEventListener : KEventListener<KZoomEvent> {
        const val minGestureZoomDeltaValue = 0.8
        const val maxGestureZoomDeltaValue = 1.2

        const val minWheelZoomDeltaValue = -100.0
        const val maxWheelZoomDeltaValue = 100.0

        var lastZoomTime: Long? = null
        lateinit var zoomStartPoint: Point

        override fun addNativeListener(target: Any, listener: (KZoomEvent) -> Unit): Disposable {

            val canvas = target as Canvas

            val zoomHandler = EventHandler<ZoomEvent> { event ->
                val currentDelta = event.zoomFactor
                listener(
                    onZoom(
                        event.x, event.y,
                        currentDelta,
                        minGestureZoomDeltaValue,
                        maxGestureZoomDeltaValue
                    )
                )
            }


            val scrollHandler = EventHandler<ScrollEvent> { event ->
                if (event.isControlDown) {
                    val currentDelta = event.deltaY
                    listener(
                        onZoom(
                            event.x, event.y,
                            currentDelta,
                            minWheelZoomDeltaValue,
                            maxWheelZoomDeltaValue
                        )
                    )
                }
            }

            return JvmZoomHandle(canvas, scrollHandler, zoomHandler).also { it.init() }
        }

        private fun onZoom(
            x:Double, y:Double,
            currentDelta: Double, minDelta: Double,
            maxDelta: Double
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
                )
            )
        }
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