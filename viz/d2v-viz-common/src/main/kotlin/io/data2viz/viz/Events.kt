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
class KPointerEvent(
    val pos: Point,
    val altKey: Boolean,
    val ctrlKey: Boolean,
    val shiftKey: Boolean,
    val metaKey: Boolean
) : KEvent {
    override fun toString(): String = "KPointerEvent(pos=$pos)"

}

class KDragEvent(
    val action: KDragAction,
    val motionEvent: KPointerEvent
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



expect class KPointerMove {
    companion object MouseMoveEventListener : KEventListener<KPointerEvent>
}

expect class KPointerDown {
    companion object MouseDownEventListener : KEventListener<KPointerEvent>
}

expect class KPointerUp {
    companion object MouseUpEventListener : KEventListener<KPointerEvent>
}

expect class KPointerEnter {
    companion object MouseEnterEventListener : KEventListener<KPointerEvent>
}

expect class KPointerLeave {
    companion object MouseLeaveEventListener : KEventListener<KPointerEvent>
}


expect class KPointerClick {
    companion object MouseClickEventListener : KEventListener<KPointerEvent>
}

expect class KPointerDoubleClick {
    companion object MouseDoubleClickEventListener : KEventListener<KPointerEvent>
}


class KPointerDrag {
    companion object MouseDragEventListener : KEventListener<KDragEvent> {

        const val minDistanceForDetectDragging = 100

        private var downActionPos: Point? = null
        private var dragInProgress: Boolean = false

        override fun addNativeListener(target: Any, listener: (KDragEvent) -> Unit) {

            KPointerMove.addNativeListener(target) {
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

            KPointerLeave.addNativeListener(target) {
                onDragNotPossible(listener, it)
            }

            KPointerDown.addNativeListener(target) {
                downActionPos = it.pos

            }

            KPointerUp.addNativeListener(target) {
                onDragNotPossible(listener, it)
            }
        }

        private fun distance(pos1: Point, pos2: Point): Double {
            val xSquare = (pos1.x - pos2.x).pow(2.0)
            val ySquare = (pos1.y - pos2.y).pow(2.0)
            return sqrt(xSquare + ySquare)
        }

        private fun onDragNotPossible(listener: (KDragEvent) -> Unit, motionEvent: KPointerEvent) {
            downActionPos = null
            if (dragInProgress) {
                dragInProgress = false
                listener(KDragEvent(KDragEvent.KDragAction.Finish, motionEvent))
            }

        }
    }
}


