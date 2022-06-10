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

import io.data2viz.InternalAPI
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
 * A KPointerEvent has a position.
 */
public interface KPointerEvent: KEvent {
    public val pos: Point
}

/**
 * Description of the MouseEvent type
 */
public enum class MouseEventType {
    Down,
    Up,
    Enter,
    Move,
    Leave,
    Click,
    DblClick,
    Unknown
}

/**
 * Description of the Mouse button pressed if applicable.
 */
public enum class MouseButtonPressed {
    NotApplicable,
    Left,
    Middle,
    Right,
    Fourth,
    Fifth
}

/**
 * Pointer events for platform with Mouse input device.
 * Somebody may want use KMouseEvent by casting KPointerEvent to more specific type
 * Used in JFX & JS implementations. Android implementation use common KPointerEvent.
 *
 * @property type the mouse event type
 * @property type the mouse burron pressed
 * @see [MouseEventType]
 * @see [MouseButtonPressed]
 */
public interface KMouseEvent: KPointerEvent, HasMetaKeys {
    public val type: MouseEventType
    public val buttonPressed: MouseButtonPressed
}

/**
 * Allow the access to the ALT, CTRL, META, SHIFT key during an event
 */
public interface HasMetaKeys{
    public val altKey: Boolean
    public val ctrlKey: Boolean
    public val shiftKey: Boolean
    public val metaKey: Boolean
}

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
@InternalAPI
internal open class KPointerEventImpl(
    public override val pos: Point
) : KPointerEvent {
    override fun toString(): String = "KPointerEvent(pos=$pos)"
}

/**
 * MultiPlatform Touch Event
 * [pointers]: the list of all current pointers including the one at the origin of the current event
 * [pointer]: the specific pointer at the source of the current event.
 */
public data class KTouchEvent(
    public val type: KTouchEventType,
    public val pointers: List<KPointer>,
    public val actionPointers: Set<KPointer>
) : KEvent

public enum class KTouchEventType {
    DOWN, UP, MOVE, CANCEL
}

public data class KPointer(
    public val id: Int,
    public val pos: Point
)


public fun KMouseEvent(pos: Point,
                       type: MouseEventType,
                       buttonPressed: MouseButtonPressed,
                       altKey: Boolean,
                       ctrlKey: Boolean,
                       shiftKey: Boolean,
                       metaKey: Boolean): KMouseEvent = KMouseEventImpl(pos, type, buttonPressed, altKey, ctrlKey, shiftKey, metaKey)


@InternalAPI
internal class KMouseEventImpl(
    pos: Point,
    public override val type: MouseEventType,
    public override val buttonPressed: MouseButtonPressed,
    public override val altKey: Boolean,
    public override val ctrlKey: Boolean,
    public override val shiftKey: Boolean,
    public override val metaKey: Boolean
) : KPointerEventImpl(pos), KMouseEvent {
    override fun toString(): String = "KMouseEvent(pos=$pos)"
}


@InternalAPI
internal data class HasMetaKeysImpl (
    public override val altKey: Boolean = false,
    public override val ctrlKey: Boolean = false,
    public override val shiftKey: Boolean = false,
    public override val metaKey: Boolean = false
        ): HasMetaKeys

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

public expect class KMouseLeave {
    public companion object PointerLeaveEventListener : KEventListener<KMouseEvent>
}

public expect class KTouch {
    public companion object TouchEventListener : KEventListener<KTouchEvent>
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
    public val deltaY: Double,
    internal val hasMetaKeys: HasMetaKeys = HasMetaKeysImpl()
) : KEvent, HasMetaKeys by hasMetaKeys {

    public constructor(startZoomPos: Point, delta: Double, hasMetaKeys: HasMetaKeys = HasMetaKeysImpl()) : this(startZoomPos, delta, delta, hasMetaKeys)

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
