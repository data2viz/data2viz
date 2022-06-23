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
 * The Pointer Event interface.
 * Extends [HasMetaKeys]
 *
 * @property pos The position of the pointer
 * @property eventType The type of the event as an [EventType]
 * @property pointerType The type of the pointer as a [PointerType]
 * @property buttonPressed The button pressed when using a mouse, else MouseButtonPressed.NotApplicable
 * @property activePointerIndex The index of the pointer that triggers the event, in case of a multitouch
 * @property pointers The index-ordered list of all pointers (including this one) as [KPointer], useful for multitouch
 */
public interface KPointerEvent: KEvent, HasMetaKeys {
    public val pos: Point
    public val eventType: EventType
    public val pointerType: PointerType
    public val buttonPressed: MouseButtonPressed
    public val activePointerIndex: Int
    public val pointers: List<KPointer>
}

/**
 * Description of the PointerEvent type
 */
public enum class EventType {

    /**
     * The pointer is "pressed down" could be a touch or a mouse down action
     */
    Down,

    /**
     * The pointer is "released" could be a touch release or a mouse up action
     */
    Up,

    /**
     * The pointer is moved
     */
    Move,

    /**
     * The pointer enters the [Viz]
     */
    Enter,

    /**
     * The pointer leaves the [Viz]
     */
    Leave,

    /**
     * A click event is a quick press and release of the same pointer.
     * Note that you'll receive 3 events: Down, [Up + Click] if they are done fast enough.
     */
    Click,

    /**
     * A double click event is a quick double press and release of the same pointer
     * Note that you'll receive 7 events: Down, [Up + Click], Down, [Up + Click + DoubleClick] if they are
     * done fast enough.
     */
    DoubleClick,

    /**
     * A "cancel" event is fired when the device cancel the current operation (for example in case of a tablet
     * rotation or if the device has been disabled during the event...)
     */
    Cancel,

    /**
     * An unknown event is triggered, this should not happen :)
     */
    Unknown;

    internal val toBeRemoved
        get() = this == Cancel || this == Leave || this == Up || this == Unknown
}

/**
 * Defines the device that triggers a [KPointerEvent]
 */
public enum class PointerType {

    /**
     * A standard Mouse device.
     */
    Mouse,

    /**
     * A touch device
     */
    Touch,

    /**
     * A stylus or pen, note that the [KPointerEvent] does not handle pen specificities like angle and pressure...
     */
    Pen,

    /**
     * Unknown device, this should not happen :)
     */
    Unknown
}

/**
 * Description of the Mouse button pressed if applicable.
 */
public enum class MouseButtonPressed {

    /**
     * The mouse button is not applicable (ie. it is a pen or a touch device)
     */
    NotApplicable,

    /**
     * Left mouse button (index 0)
     */
    Left,

    /**
     * Middle mouse button (index 1)
     */
    Middle,

    /**
     * Right mouse button (index 2)
     */
    Right,

    /**
     * Fourth mouse button (index 3)
     */
    Fourth,

    /**
     * Fifth mouse button (index 4)
     */
    Fifth
}

///**
// * Pointer events for platform with Mouse input device.
// * Somebody may want use KMouseEvent by casting KPointerEvent to more specific type
// * Used in JFX & JS implementations. Android implementation use common KPointerEvent.
// *
// * @property eventType the [PointerEventType]
// * @property buttonPressed the [MouseButtonPressed]
// */
//public interface KMouseEvent: KPointerEvent, HasMetaKeys

/**
 * Allow the access to the ALT, CTRL, META, SHIFT key during an event
 */
public interface HasMetaKeys{
    public val altKey: Boolean
    public val ctrlKey: Boolean
    public val shiftKey: Boolean
    public val metaKey: Boolean

    /**
     * No "meta key" is pressed (not CTRL, SHIFT, ALT or META)
     */
    public fun noKey(): Boolean
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
 * Common Pointer event.
 */
@InternalAPI
internal open class KPointerEventImpl(
    public override val pos: Point,
    public override val eventType: EventType,
    public override val pointerType: PointerType,
    public override val buttonPressed: MouseButtonPressed,
    public override val activePointerIndex: Int,
    public override val pointers: List<KPointer>,
    altKey: Boolean,
    ctrlKey: Boolean,
    shiftKey: Boolean,
    metaKey: Boolean
) : KPointerEvent, HasMetaKeys by HasMetaKeysImpl(altKey, ctrlKey, shiftKey, metaKey) {

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


//public fun KMouseEvent(pos: Point,
//                       type: PointerEventType,
//                       buttonPressed: MouseButtonPressed,
//                       altKey: Boolean,
//                       ctrlKey: Boolean,
//                       shiftKey: Boolean,
//                       metaKey: Boolean): KMouseEvent = KMouseEventImpl(pos, type, buttonPressed, altKey, ctrlKey, shiftKey, metaKey)


//@InternalAPI
//internal class KMouseEventImpl(
//    pos: Point,
//    public override val eventType: PointerEventType,
//    public override val buttonPressed: MouseButtonPressed,
//    public override val altKey: Boolean,
//    public override val ctrlKey: Boolean,
//    public override val shiftKey: Boolean,
//    public override val metaKey: Boolean
//) : KPointerEventImpl(pos, eventType, PointerType.Mouse, buttonPressed, 0, altKey, ctrlKey, shiftKey, metaKey), KMouseEvent {
//    override fun toString(): String = "KMouseEvent(pos=$pos)"
//}


@InternalAPI
internal data class HasMetaKeysImpl(
    public override val altKey: Boolean = false,
    public override val ctrlKey: Boolean = false,
    public override val shiftKey: Boolean = false,
    public override val metaKey: Boolean = false
) : HasMetaKeys {
    public override fun noKey(): Boolean = (!altKey && !ctrlKey && !shiftKey && !metaKey)
}

// TODO : manage "velocity" and "drag event"

public interface KEventListener<T> where  T : KEvent {
    public fun addNativeListener(target: Any, listener: (T) -> Unit): Disposable
}


public expect class KPointerDown {
    public companion object PointerDownEventListener : KEventListener<KPointerEvent>
}

public expect class KPointerUp {
    public companion object PointerUpEventListener : KEventListener<KPointerEvent>
}

public expect class KPointerMove {
    public companion object PointerMoveEventListener : KEventListener<KPointerEvent>
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

public expect class KPointerCancel {
    public companion object PointerCancelEventListener : KEventListener<KPointerEvent>
}

//public expect class KTouch {
//    public companion object TouchEventListener : KEventListener<KTouchEvent>
//}
//
//public expect class KTouchStart {
//    public companion object TouchStartEventListener : KEventListener<KPointerEvent>
//}
//
//public expect class KTouchEnd {
//    public companion object TouchEndEventListener : KEventListener<KPointerEvent>
//}
//
//public expect class KTouchMove {
//    public companion object TouchMoveEventListener : KEventListener<KPointerEvent>
//}
//
//public expect class KTouchCancel {
//    public companion object TouchCancelEventListener : KEventListener<KPointerEvent>
//}

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
