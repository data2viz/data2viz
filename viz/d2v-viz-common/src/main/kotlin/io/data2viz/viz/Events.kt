package io.data2viz.viz

import io.data2viz.geom.*

sealed class EventType<T: Event> {
	object MouseMove:EventType<KMouseMoveEvent>()
}

interface Event
class KMouseMoveEvent(val pos: Point):Event


val listeners = mutableListOf<Any>()

fun <T:Event> addEventListener(eventType: EventType<T>, listener: (T) -> Unit){
	listeners.add(listener)
//	renderer.addEventListener()
}

fun usingMouseEvent() {
	addEventListener(EventType.MouseMove) { evt -> println(evt.pos) }
}

