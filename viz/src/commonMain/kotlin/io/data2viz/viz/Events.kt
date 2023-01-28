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

@RequiresOptIn
@Retention(AnnotationRetention.BINARY)
public annotation class ExperimentalKEvent

public object KPointerEvents {
    public val down: KEventListener<KPointerEvent> = pointerEventsListener(type = EventType.Down)
    public val move: KEventListener<KPointerEvent> = pointerEventsListener(type = EventType.Move)
    public val up: KEventListener<KPointerEvent> = pointerEventsListener(type = EventType.Up)

    public val enter: KEventListener<KPointerEvent> = pointerEventsListener(type = EventType.Enter)
    public val leave: KEventListener<KPointerEvent> = pointerEventsListener(type = EventType.Leave)
    public val cancel: KEventListener<KPointerEvent> = pointerEventsListener(type = EventType.Cancel)

    public val drag: KEventListener<KDragEvent> = dragEventsListener()

    @ExperimentalKEvent
    public val zoom: KEventListener<KZoomEvent> get() = zoomEventsListener()
}

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
 * Create a new listener for a given [EventType].
 **/
internal expect fun pointerEventsListener(type: EventType): KEventListener<KPointerEvent>

@ExperimentalKEvent
internal expect fun zoomEventsListener(): KEventListener<KZoomEvent>

public data class KPointer(
    public val id: Int,
    public val pos: Point
)


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

/**
 * Manage the propagation of the original platform event.
 */
public enum class EventPropagation {

    /**
     * Base event should be propagated back.
     */
    Continue,

    /**
     * Base event should not be propagated back to the platform.
     * Similar to event.stopPropagation() or event.consume()...
     */
    Stop;

    public val stop: Boolean
        get() = this == Stop
}

/**
 * A [KEventListener] is an interface for handling a <T> KEvent on any given target.
 */
public interface KEventListener<T> where  T : KEvent {

    /**
     * Add a listener on any target for a given <T> KEvent.
     * In return the listener must return a [EventPropagation] to indicate if the base event continues back
     * to the platform or stops here and is not propagated.
     */
    public fun addNativeListener(target: Any, listener: (T) -> EventPropagation): Disposable
}

@Deprecated("Use KPointerEvents.move instead.", ReplaceWith("KPointerEvents.move"))
public class KMouseMove

@Deprecated("Use KPointerEvents.down instead.", ReplaceWith("KPointerEvents.down"))
public class KMouseDown

@Deprecated("Use KPointerEvents.up instead.", ReplaceWith("KPointerEvents.up"))
public class KMouseUp

@Deprecated("Use KPointerEvents instead.")
public class KTouch

@Deprecated("Use KPointerEvents.down instead.", ReplaceWith("KPointerEvents.down"))
public class KTouchStart

@Deprecated("Use KPointerEvents.up instead.", ReplaceWith("KPointerEvents.up"))
public class KTouchEnd

@Deprecated("Use KPointerEvents.move instead.", ReplaceWith("KPointerEvents.move"))
public class KTouchMove

@Deprecated("Use KPointerEvents.enter instead.", ReplaceWith("KPointerEvents.enter"))
public class KPointerEnter

@Deprecated("Use KPointerEvents.leave instead.", ReplaceWith("KPointerEvents.leave"))
public class KPointerLeave

//public class KPointerClick {
//    public companion object PointerClickEventListener : KEventListener<KPointerEvent>
//}
//
//public class KPointerDoubleClick {
//    public companion object PointerDoubleClickEventListener : KEventListener<KPointerEvent>
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
@Deprecated("Use KPointerEvents.zoom instead.", ReplaceWith("KPointerEvents.zoom"))
public class KZoom

@ExperimentalKEvent
public class KZoomEvent(
    public val startZoomPos: Point,
    public val deltaX: Double,
    public val deltaY: Double,
    internal val hasMetaKeys: HasMetaKeys = HasMetaKeysImpl()
) : KEvent, HasMetaKeys by hasMetaKeys {

    public constructor(startZoomPos: Point, delta: Double, hasMetaKeys: HasMetaKeys = HasMetaKeysImpl()) : this(startZoomPos, delta, delta, hasMetaKeys)

    public companion object {

        public const val diffTimeBetweenZoomEventsToDetectRestart: Int = 200

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

private fun dragEventsListener(): KEventListener<KDragEvent> = object : KEventListener<KDragEvent> {

    private var downActionPos: Point? = null
    private var dragInProgress: Boolean = false

    override fun addNativeListener(target: Any, listener: (KDragEvent) -> EventPropagation): Disposable {

        val compositeDisposable = CompositeDisposable()

        compositeDisposable.add(KPointerEvents.move.addNativeListener(target) {
            if (dragInProgress) {
                listener(KDragEvent(KDragEvent.KDragAction.Dragging, it))
            } else {
                val startPos = downActionPos
                if (startPos != null) {
                    dragInProgress = true
                    listener(KDragEvent(KDragEvent.KDragAction.Start, it))
                }
                EventPropagation.Continue
            }
        })

        compositeDisposable.add(KPointerEvents.leave.addNativeListener(target) {
            onDragNotPossible(listener, it)
        })

        compositeDisposable.add(KPointerEvents.down.addNativeListener(target) {
            downActionPos = it.pos
            EventPropagation.Continue
        })

        compositeDisposable.add(KPointerEvents.up.addNativeListener(target) {
            onDragNotPossible(listener, it)
        })

        return compositeDisposable
    }

    private fun onDragNotPossible(listener: (KDragEvent) -> EventPropagation, motionEvent: KPointerEvent): EventPropagation {
        downActionPos = null
        return if (dragInProgress) {
            dragInProgress = false
            listener(KDragEvent(KDragEvent.KDragAction.Finish, motionEvent))
        } else {
            EventPropagation.Continue
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
    val listener: (T) -> EventPropagation,
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
