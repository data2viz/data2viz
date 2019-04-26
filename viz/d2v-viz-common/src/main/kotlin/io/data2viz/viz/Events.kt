package io.data2viz.viz

import io.data2viz.geom.*


/**
 * Add an event listener on a a viz. This is the only entry point you should use when dealing with events in data2viz.
 * @return an handler to eventually remove later.
 */
expect fun <T> Viz.on(eventListener: KEventListener<T>, listener: (T) -> Unit): Any


/**
 * Marker interface on events.
 */
interface KEvent


/**
 * Common Mouse event. Can be subclassed into more specific events.
 * Gives access to the position of the event.
 * Todo rename into MotionEvent?
 */
class KMouseEvent(
	val pos: Point,
	val altKey: Boolean,
	val ctrlKey: Boolean,
	val shiftKey: Boolean,
	val metaKey: Boolean
): KEvent {
	override fun toString(): String = "KMouseEvent(pos=$pos)"

}


interface KEventListener<T> {
	fun addNativeListener(target: Any, listener: (T) -> Unit):Any
}


/**
 * Define the common signature of mouse move events handling.
 * A mouse move needs a listener that listen to KMouseMoveEvent.
 */
expect class KMouseMove {
	companion object MouseMoveEventListener : KEventListener<KMouseEvent>
}

expect class KMouseDown {
	companion object MouseDownEventListener : KEventListener<KMouseEvent>
}

expect class KMouseUp {
	companion object MouseUpEventListener : KEventListener<KMouseEvent>
}

expect class KMouseEnter {
	companion object MouseEnterEventListener : KEventListener<KMouseEvent>
}

expect class KMouseLeave {
	companion object MouseLeaveEventListener : KEventListener<KMouseEvent>
}

expect class KMouseOut {
	companion object MouseOutEventListener : KEventListener<KMouseEvent>
}

expect class KMouseOver {
	companion object MouseOverEventListener : KEventListener<KMouseEvent>
}

expect class KMouseClick {
    companion object MouseClickEventListener : KEventListener<KMouseEvent>
}
expect class KMouseDoubleClick {
	companion object MouseDoubleClickEventListener : KEventListener<KMouseEvent>
}