/*
 * Copyright (c) 2018-2019. data2viz sàrl.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 *
 */

package io.data2viz.viz

import io.data2viz.geom.Point


@Experimental
@Retention(AnnotationRetention.BINARY)
@Target(AnnotationTarget.CLASS, AnnotationTarget.FUNCTION)
annotation class ExperimentalKEvent


/**
 * Marker interface on events.
 */
interface KEvent

/**
 * TODO: rename with a more explicit name.
 */
interface Disposable {

    /**
     * Remove the event listener from the Viz.
     * TODO: rename with a more explicit name.
     */
    fun dispose()
}


/**
 * Common Pointer event. Can be subclassed into more specific events.
 * Gives access to the position of the event.
 */
open class KPointerEvent(
    val pos: Point
) : KEvent {
    override fun toString(): String = "KPointerEvent(pos=$pos)"
}

/**
 * Pointer events for platform with Mouse input device.
 * Somebody may want use KMouseEvent by casting KPointerEvent to more specific type
 * Used in JFX & JS implementations. Android implementation use common KPointerEvent
 */
class KMouseEvent(
    pos: Point,
    val altKey: Boolean,
    val ctrlKey: Boolean,
    val shiftKey: Boolean,
    val metaKey: Boolean
) : KPointerEvent(pos) {
    override fun toString(): String = "KMouseEvent(pos=$pos)"
}


interface KEventListener<T> where  T : KEvent {
    fun addNativeListener(target: Any, listener: (T) -> Unit): Disposable
}

expect class KPointerMove {
    companion object PointerMoveEventListener : KEventListener<KPointerEvent>
}

expect class KPointerDown {
    companion object PointerDownEventListener : KEventListener<KPointerEvent>
}

expect class KPointerUp {
    companion object PointerUpEventListener : KEventListener<KPointerEvent>
}

expect class KPointerEnter {
    companion object PointerEnterEventListener : KEventListener<KPointerEvent>
}

expect class KPointerLeave {
    companion object PointerLeaveEventListener : KEventListener<KPointerEvent>
}

expect class KPointerClick {
    companion object PointerClickEventListener : KEventListener<KPointerEvent>
}

expect class KPointerDoubleClick {
    companion object PointerDoubleClickEventListener : KEventListener<KPointerEvent>
}

class KDragEvent(
    val action: KDragAction,
    val pointerEvent: KPointerEvent
) : KEvent {
    val pos get() = pointerEvent.pos
    override fun toString(): String = "KDragEvent(action=$action, pos=$pos)"

    enum class KDragAction {
        Start, Dragging, Finish
    }
}


@ExperimentalKEvent
expect class KZoom {
    companion object ZoomEventListener : KEventListener<KZoomEvent>
}


@ExperimentalKEvent
class KZoomEvent(
    val startZoomPos: Point,
    val delta: Double
) : KEvent {
    companion object {

        const val diffTimeBetweenZoomEventsToDetectRestart = 500
        fun isNewZoom(currentTime: Double, lastTime: Double?) =
            if (lastTime == null) {
                true
            } else {
                currentTime - lastTime > diffTimeBetweenZoomEventsToDetectRestart
            }

        fun isNewZoom(currentTime: Long, lastTime: Long?) =
            if (lastTime == null) {
                true
            } else {
                currentTime - lastTime > diffTimeBetweenZoomEventsToDetectRestart
            }

        const val minDelta = -100.0
        const val maxDelta = 100.0

        fun scaleDelta(
            currentDelta: Double,
            originMinDelta: Double,
            originMaxDelta: Double,
            newMinDelta: Double = minDelta,
            newMaxDelta: Double = maxDelta
        ): Double {
            val originBoundsSize = originMaxDelta - originMinDelta
            val currentDeltaPercentInBounds = (currentDelta - originMinDelta) / originBoundsSize

            val newBoundsSize = newMaxDelta - newMinDelta
            var newDeltaValue = newMinDelta + newBoundsSize * currentDeltaPercentInBounds

            if (newDeltaValue > maxDelta) {
                newDeltaValue = maxDelta
            }

            if (newDeltaValue < minDelta) {
                newDeltaValue = minDelta
            }

            return newDeltaValue
        }
    }

    override fun toString(): String {
        return "KZoomEvent(startZoomPos=$startZoomPos, delta=$delta)"
    }

}

class KPointerDrag {

    companion object PointerDragEventListener : KEventListener<KDragEvent> {

        private var downActionPos: Point? = null
        private var dragInProgress: Boolean = false

        override fun addNativeListener(target: Any, listener: (KDragEvent) -> Unit): Disposable {

            val compositeDisposable = CompositeDisposable()

            compositeDisposable.add(KPointerMove.addNativeListener(target) {
                if (dragInProgress) {
                    listener(KDragEvent(KDragEvent.KDragAction.Dragging, it))
                } else {
                    val startPos = downActionPos
                    if (startPos != null) {
                        dragInProgress = true
                        listener(KDragEvent(KDragEvent.KDragAction.Start, it))
                    }
                }
            })

            compositeDisposable.add(KPointerLeave.addNativeListener(target) {
                onDragNotPossible(listener, it)
            })

            compositeDisposable.add(KPointerDown.addNativeListener(target) {
                downActionPos = it.pos
            })

            compositeDisposable.add(KPointerUp.addNativeListener(target) {
                onDragNotPossible(listener, it)
            })

            return compositeDisposable
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

internal fun <T> VizRenderer.addEventHandle(handle: KEventHandle<T>) where T : KEvent {
    check(!handle.isAddedToRenderer)
    { "Can't add event handle which already added to Renderer" }

    handle.disposable = addNativeEventListenerFromHandle(handle)
}

internal expect fun <T> VizRenderer.addNativeEventListenerFromHandle(handle: KEventHandle<T>): Disposable where T : KEvent

internal class KEventHandle<T>(
    val eventListener: KEventListener<T>,
    val listener: (T) -> Unit,
    val onDispose: (KEventHandle<T>) -> Unit
) : Disposable where T : KEvent {

    var disposable: Disposable? = null

    val isAddedToRenderer
        get() = disposable != null

    override fun dispose() {
        onDispose(this)
    }

    override fun toString(): String = "KEventHandle(eventListener=$eventListener)"
}

/**
 * Todo check the reason of VizRenderer receiver. Why not using KEventHandle.remove()
 */
internal fun <T> VizRenderer.removeEventHandle(handle: KEventHandle<T>) where T : KEvent {

    check(handle.isAddedToRenderer) { "Can't remove event handle which not added to Renderer. $handle" }

    handle.disposable!!.dispose()
    handle.disposable = null
}


internal class CompositeDisposable(val disposables: MutableList<Disposable> = mutableListOf()) : Disposable {
    override fun dispose() {
        disposables.forEach { it.dispose() }
        disposables.clear()
    }

    fun add(disposable: Disposable) {
        disposables.add(disposable)
    }

}
