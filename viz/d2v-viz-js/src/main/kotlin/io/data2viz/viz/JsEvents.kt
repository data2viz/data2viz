package io.data2viz.viz


import io.data2viz.geom.*
import org.w3c.dom.*
import org.w3c.dom.events.Event
import org.w3c.dom.events.EventListener
import org.w3c.dom.events.MouseEvent

fun <T> Element.on(eventListener: KEventListener<T>, listener: (T) -> Unit): Any {
	return eventListener.addNativeListener(this, listener)
}

private fun Element.removeListener(listener: Any) {
	val jsListener = listener.unsafeCast<JsListener>()
	this.removeEventListener(jsListener.type, jsListener.listener as EventListener, null)
}

actual class KMouseMove {
	actual companion object MouseMoveEventListener : KEventListener<KMouseMoveEvent> {
		override fun addNativeListener(target:Any, listener: (KMouseMoveEvent) -> Unit): Any {
			val htmlElement = target.unsafeCast<HTMLElement>()
			val nativeListener = object: EventListener {
				override fun handleEvent(event: Event) {
					val nativeEvent = convertToKEvent(htmlElement, event)
					listener(nativeEvent)
				}
			}
			htmlElement.addEventListener("mousemove", nativeListener)
			return JsListener("mousemove", nativeListener)
		}

		fun convertToKEvent(target: HTMLElement, event: Event):KMouseMoveEvent = event.unsafeCast<MouseEvent>().run {
			KMouseMoveEvent(Point(clientX.toDouble() - target.offsetLeft, clientY.toDouble() - target.offsetTop))
		}
	}
}

data class JsListener(val type:String, val listener: Any)

/**
 * Add an event listener on a a viz.
 * @return an handler to eventually remove later.
 */
actual fun <T> Viz.on(
    eventListener: KEventListener<T>,
    listener: (T) -> Unit
): Any {
	val jsCanvasRenderer = this.renderer as JsCanvasRenderer
	return  eventListener.addNativeListener(jsCanvasRenderer.context.canvas, listener)

}