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
open class KMouseEvent(val pos: Point): KEvent {
	override fun toString(): String = "KMouseEvent(pos=$pos)"
}

/**
 *
 */
class KMouseMoveEvent(pos: Point): KMouseEvent(pos)


interface KEventListener<T> {
	fun addNativeListener(target: Any, listener: (T) -> Unit):Any
}


/**
 * Define the common signature of mouse move events handling.
 * A mouse move needs a listener that listen to KMouseMoveEvent.
 */
expect class KMouseMove {
	companion object MouseMoveEventListener : KEventListener<KMouseMoveEvent>
}
