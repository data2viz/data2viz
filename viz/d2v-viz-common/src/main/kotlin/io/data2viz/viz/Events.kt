package io.data2viz.viz

import io.data2viz.geom.Point
import kotlin.math.pow
import kotlin.math.sqrt


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
) : KEvent {
    override fun toString(): String = "KMouseEvent(pos=$pos)"

}

class KDragEvent(
    val action: KDragAction,
    val motionEvent: KMouseEvent
) : KEvent {
    val pos get() = motionEvent.pos
    override fun toString(): String = "KDragEvent(action=$action, pos=$pos)"

    enum class KDragAction {
        Start, Dragging, Finish
    }
}


interface KEventListener<T> {
    fun addNativeListener(target: Any, listener: (T) -> Unit): Any
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


class KMouseDrag {
    companion object MouseDragEventListener : KEventListener<KDragEvent> {

        const val minDistanceForDetectDragging = 100

        private var downActionPos: Point? = null
        private var dragInProgress: Boolean = false

        override fun addNativeListener(target: Any, listener: (KDragEvent) -> Unit) {

            KMouseMove.addNativeListener(target) {
                if (dragInProgress) {
                    listener(KDragEvent(KDragEvent.KDragAction.Dragging, it))
                } else {

                    val startPos = downActionPos
                    if (startPos != null) {

                        val distance = distance(startPos, it.pos)

                        if (distance > minDistanceForDetectDragging) {
                            dragInProgress = true
                            listener(KDragEvent(KDragEvent.KDragAction.Start, it))
                        }
                    }
                }
            }

            KMouseLeave.addNativeListener(target) {
                onDragNotPossible(listener, it)
            }

            KMouseDown.addNativeListener(target) {
                downActionPos = it.pos

            }

            KMouseUp.addNativeListener(target) {
                onDragNotPossible(listener, it)
            }
        }

        private fun distance(pos1: Point, pos2: Point): Double {
            val xSquare = (pos1.x - pos2.x).pow(2.0)
            val ySquare = (pos1.y - pos2.y).pow(2.0)
            return sqrt(xSquare + ySquare)
        }

        private fun onDragNotPossible(listener: (KDragEvent) -> Unit, motionEvent: KMouseEvent) {
            downActionPos = null
            if (dragInProgress) {
                dragInProgress = false
                listener(KDragEvent(KDragEvent.KDragAction.Finish, motionEvent))
            }

        }
    }
}


