package io.data2viz.viz


import io.data2viz.geom.*
import javafx.event.Event
import javafx.event.EventType
import javafx.scene.canvas.*
import javafx.scene.input.MouseEvent


actual class KMouseMove {
	actual companion object MouseMoveEventListener : KEventListener<KMouseMoveEvent> {
		override fun addNativeListener(target: Any, listener: (KMouseMoveEvent) -> Unit): Any {
			val canvas = target as Canvas
			val handler: (MouseEvent) -> Unit = { evt: MouseEvent ->
				val kevent = convertToKEvent(evt)
				listener(kevent)
			}
			canvas.addEventHandler(MouseEvent.MOUSE_MOVED, handler)
			return JvmEventHandle(MouseEvent.MOUSE_MOVED, handler)
		}

		private fun convertToKEvent(event: MouseEvent) = KMouseMoveEvent(Point(event.x, event.y))

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