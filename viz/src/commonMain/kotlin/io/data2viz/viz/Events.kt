/*
 * Copyright (c) 2018-2021. data2viz sàrl.
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
public interface KEvent

/**
 * TODO: rename with a more explicit name.
 */
public interface Disposable {

    /**
     * Remove the event listener from the Viz.
     * TODO: rename with a more explicit name.
     */
    public fun dispose()
}


/**
 * Common Pointer event. Can be subclassed into more specific events.
 * Gives access to the position of the event.
 */
public open class KPointerEvent(
    public val pos: Point
) : KEvent {
    override fun toString(): String = "KPointerEvent(pos=$pos)"
}

/**
 * Common Dual Pointer event. Can be subclassed into more specific events.
 * Gives access to the position of the event.
 */
public open class KDualPointerEvent(
    public val pos0: Point,
    public val pos1: Point
) : KEvent {
    override fun toString(): String = "KDualPointerEvent(pos0=$pos0, pos1=$pos1)"
}

/**
 * Pointer events for platform with Mouse input device.
 * Somebody may want use KMouseEvent by casting KPointerEvent to more specific type
 * Used in JFX & JS implementations. Android implementation use common KPointerEvent
 */
public class KMouseEvent(
    pos: Point,
    public val altKey: Boolean,
    public val ctrlKey: Boolean,
    public val shiftKey: Boolean,
    public val metaKey: Boolean
) : KPointerEvent(pos) {
    override fun toString(): String = "KMouseEvent(pos=$pos)"
}


// TODO : manage "velocity" and "drag event"

public interface KEventListener<T> where  T : KEvent {
    public fun addNativeListener(target: Any, listener: (T) -> Unit): Disposable
}

public expect class KMouseMove {
    public companion object PointerMoveEventListener : KEventListener<KMouseEvent>
}

public expect class KMouseDown {
    public companion object PointerDownEventListener : KEventListener<KMouseEvent>
}

public expect class KMouseUp {
    public companion object PointerUpEventListener : KEventListener<KMouseEvent>
}

public expect class KTouchStart {
    public companion object TouchStartEventListener : KEventListener<KPointerEvent>
}

public expect class KTouchEnd {
    public companion object TouchEndEventListener : KEventListener<KPointerEvent>
}

public expect class KTouchMove {
    public companion object TouchMoveEventListener : KEventListener<KPointerEvent>
}

public expect class KDualTouchStart {
    public companion object TouchStartEventListener : KEventListener<KDualPointerEvent>
}

public expect class KDualTouchEnd {
    public companion object TouchEndEventListener : KEventListener<KDualPointerEvent>
}

public expect class KDualTouchMove {
    public companion object TouchMoveEventListener : KEventListener<KDualPointerEvent>
}

public expect class KPointerEnter {
    public companion object PointerEnterEventListener : KEventListener<KPointerEvent>
}

public expect class KPointerLeave {
    public companion object PointerLeaveEventListener : KEventListener<KPointerEvent>
}

public expect class KPointerClick {
    public companion object PointerClickEventListener : KEventListener<KPointerEvent>
}

public expect class KPointerDoubleClick {
    public companion object PointerDoubleClickEventListener : KEventListener<KPointerEvent>
}

public class KDragEvent(
    public val action: KDragAction,
    public val pointerEvent: KPointerEvent
) : KEvent {
    public val pos: Point get() = pointerEvent.pos
    override fun toString(): String = "KDragEvent(action=$action, pos=$pos)"

    public enum class KDragAction {
        Start, Dragging, Finish
    }
}


@ExperimentalKEvent
public expect class KZoom {
    public companion object ZoomEventListener : KEventListener<KZoomEvent>
}

@ExperimentalKEvent
public class KZoomEvent(
    public val startZoomPos: Point,
    public val deltaX: Double,
    public val deltaY: Double
) : KEvent {

    public constructor(startZoomPos: Point, delta: Double) : this(startZoomPos, delta, delta)

    public companion object {

        public const val diffTimeBetweenZoomEventsToDetectRestart: Int = 500

        public fun isNewZoom(currentTime: Double, lastTime: Double?): Boolean =
            if (lastTime == null) {
                true
            } else {
                currentTime - lastTime > diffTimeBetweenZoomEventsToDetectRestart
            }

        public fun isNewZoom(currentTime: Long, lastTime: Long?): Boolean =
            if (lastTime == null) {
                true
            } else {
                currentTime - lastTime > diffTimeBetweenZoomEventsToDetectRestart
            }

        public const val minDelta: Double = -100.0
        public const val maxDelta: Double = 100.0

        public fun scaleDelta(
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
        return "KZoomEvent(startZoomPos=$startZoomPos, deltaX=$deltaX, deltaY=$deltaY)"
    }

}

public class KPointerDrag {

    public companion object PointerDragEventListener : KEventListener<KDragEvent> {

        private var downActionPos: Point? = null
        private var dragInProgress: Boolean = false

        override fun addNativeListener(target: Any, listener: (KDragEvent) -> Unit): Disposable {

            val compositeDisposable = CompositeDisposable()

            compositeDisposable.add(KMouseMove.addNativeListener(target) {
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

            compositeDisposable.add(KMouseDown.addNativeListener(target) {
                downActionPos = it.pos
            })

            compositeDisposable.add(KMouseUp.addNativeListener(target) {
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
