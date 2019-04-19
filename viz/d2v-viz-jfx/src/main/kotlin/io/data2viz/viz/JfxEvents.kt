package io.data2viz.viz


import io.data2viz.geom.*
import javafx.event.Event
import javafx.event.EventType
import javafx.scene.canvas.*
import javafx.scene.input.MouseEvent


actual class KMouseMove {
	actual companion object MouseMoveEventListener : KEventListener<KMouseEvent> {
		override fun addNativeListener(target: Any, listener: (KMouseEvent) -> Unit): Any {
			val handler: (MouseEvent) -> Unit = { evt: MouseEvent ->
				val kevent = evt.convertToKEvent()
				listener(kevent)
			}
			(target as Canvas).addEventHandler(MouseEvent.MOUSE_MOVED, handler)
			return JvmEventHandle(MouseEvent.MOUSE_MOVED, handler)
		}
	}
}

actual class KMouseClick {
	actual companion object MouseClickEventListener : KEventListener<KMouseEvent> {
		override fun addNativeListener(target: Any, listener: (KMouseEvent) -> Unit): Any {
			val handler: (MouseEvent) -> Unit = { evt: MouseEvent ->
				val kevent = evt.convertToKEvent()
				listener(kevent)
			}
			(target as Canvas).addEventHandler(MouseEvent.MOUSE_CLICKED, handler)
			return JvmEventHandle(MouseEvent.MOUSE_CLICKED, handler)
		}
	}
}

data class JvmEventHandle<T : Event?>(val type: EventType<T>, val handler: (MouseEvent) -> Unit)

/**
 * Add an event listener on a a viz.
 * @return an handler to eventually remove later.
 */
actual fun <T> Viz.on(
    eventListener: KEventListener<T>,
    listener: (T) -> Unit
): Any {
    val jFxVizRenderer = this.renderer as JFxVizRenderer
	return  eventListener.addNativeListener(jFxVizRenderer.canvas, listener)
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