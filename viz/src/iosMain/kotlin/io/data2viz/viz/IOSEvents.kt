/*
 * Copyright (c) 2018-2022. data2viz sàrl.
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
import kotlinx.cinterop.*
import platform.CoreGraphics.*
import platform.UIKit.*


internal object FakeDisposable : Disposable {
    override fun dispose() {
//        TODO("Not yet implemented")
    }
}

internal actual fun pointerEventsListener(type: EventType): KEventListener<KPointerEvent> {
    require(type != EventType.Unknown)
    return object : KEventListener<KPointerEvent> {
        override fun addNativeListener(target: Any, listener: (KPointerEvent) -> EventPropagation): Disposable {
            return addTouchIOSEventHandle(target, listener, type)
        }
    }
}

@ExperimentalKEvent
internal actual fun zoomEventsListener(): KEventListener<KZoomEvent> {
    return object : KEventListener<KZoomEvent> {
        override fun addNativeListener(target: Any, listener: (KZoomEvent) -> EventPropagation): Disposable {
            return FakeDisposable
        }
    }
}

private fun addTouchIOSEventHandle(
    target: Any,
    listener: (KPointerEvent) -> EventPropagation,
    type: EventType
): Disposable {
    val renderer = target as IOSCanvasRenderer
    return renderer.uiTouchesHandler.addListener(Listener(type, listener))
}

internal class Listener(
    val type: EventType,
    listener: (KPointerEvent) -> EventPropagation
) : (KPointerEvent) -> EventPropagation by listener

internal actual fun <T> VizRenderer.addNativeEventListenerFromHandle(handle: KEventHandle<T>): Disposable where T : KEvent {
    val iosCanvasRenderer = this as IOSCanvasRenderer
    return handle.eventListener.addNativeListener(iosCanvasRenderer, handle.listener)
}


internal class IOSTouchDisposable(
    val uiTouchesHandler: UITouchesHandler,
    val listener: Listener
) : Disposable {

    override fun dispose() {
        uiTouchesHandler.listeners.remove(this)
    }
}


internal class UITouchesHandler(private val view: IOSCanvasView) {


    init {
        (view as UIView).multipleTouchEnabled = true
        view.uiTouchesHandler = this
    }


    /**
     * La liste de toutes les touches en cours.
     */
    internal val currentTouches: MutableMap<UITouch, KPointer> = mutableMapOf()

    internal val listeners = mutableListOf<IOSTouchDisposable>()


    fun addListener(listener: Listener): Disposable {
        val disposable = IOSTouchDisposable(this, listener)
        listeners.add(disposable)
        return disposable
    }


    private fun allPointers(): List<KPointer> = currentTouches.values.sortedBy { it.id }

    private var touchId = 0


    /**
     * On
     */
    fun touchesBegan(touches: Set<UITouch>, withEvent: UIEvent?) {
        val pointers = touches.map {
            val kPointer = KPointer(touchId++, it.toPosition())
            currentTouches[it] = kPointer
            kPointer
        }.toSet()

        val touchEvent = pointerEvent(EventType.Down, touches, withEvent)
        notifyListeners(touchEvent)
    }

    fun touchesMoved(touches: Set<UITouch>, withEvent: UIEvent?) {

        val pointers = touches.map {
            val pointer = currentTouches[it]!!
            val updatedPointer = pointer.copy(pos = it.toPosition())
            currentTouches[it] = updatedPointer
            updatedPointer
        }.toSet()

        val touchEvent = pointerEvent(EventType.Move, touches, withEvent)
        notifyListeners(touchEvent)
    }


    fun touchesEnded(touches: Set<UITouch>, withEvent: UIEvent?) {

        val touchEvent = pointerEvent(EventType.Up, touches, withEvent)
        notifyListeners(touchEvent)
        val pointers = touches.map {
            val pointer = currentTouches[it]!!
            currentTouches.remove(it)
            pointer
        }.toSet()
    }

    fun touchesCancelled(touches: Set<UITouch>, withEvent: UIEvent?) {

        val touchEvent = pointerEvent(EventType.Cancel, touches, withEvent)
        notifyListeners(touchEvent)
        val pointers = touches.map {
            val pointer = currentTouches[it]!!
            currentTouches.remove(it)
            pointer
        }.toSet()
    }

    private fun notifyListeners(touchEvent: KPointerEvent) {
        listeners.forEach {
            if (it.listener.type == touchEvent.eventType) {
                it.listener(touchEvent)
            }
        }
    }

    private fun UITouch.toPosition(): Point = this.locationInView(view).useContents { Point(this.x, this.y) }

    private fun pointerEvent(
        type: EventType,
        touches: Set<UITouch>,
        event: UIEvent?
    ): KPointerEvent {
        val pointers = allPointers()
        return KPointerEventImpl(
            pos = pointers.first().pos,
            eventType = type,
            pointerType = touches.first().pointerType(),
            buttonPressed = event?.pressedMouseButton() ?: MouseButtonPressed.NotApplicable,
            activePointerIndex = 0,
            pointers = pointers,
            altKey = event?.modifierFlags?.hasFlag(UIKeyModifierAlternate) ?: false,
            ctrlKey = event?.modifierFlags?.hasFlag(UIKeyModifierControl) ?: false,
            shiftKey = event?.modifierFlags?.hasFlag(UIKeyModifierShift) ?: false,
            metaKey = event?.modifierFlags?.hasFlag(UIKeyModifierCommand) ?: false
        )
    }

    private fun UIEvent.pressedMouseButton(): MouseButtonPressed {
        return when {
            buttonMask.hasFlag(UIEventButtonMaskPrimary) -> MouseButtonPressed.Left
            buttonMask.hasFlag(UIEventButtonMaskSecondary) -> MouseButtonPressed.Right
            buttonMask.hasFlag(UIEventButtonMaskForButtonNumber(3)) -> MouseButtonPressed.Middle
            buttonMask.hasFlag(UIEventButtonMaskForButtonNumber(4)) -> MouseButtonPressed.Fourth
            buttonMask.hasFlag(UIEventButtonMaskForButtonNumber(5)) -> MouseButtonPressed.Fifth
            else -> MouseButtonPressed.NotApplicable
        }
    }

    private fun UITouch.pointerType(): PointerType = when (type) {
        UITouchTypeDirect -> PointerType.Touch
        UITouchTypeIndirectPointer, UITouchTypeIndirect -> PointerType.Mouse
        UITouchTypePencil -> PointerType.Pen
        else -> PointerType.Unknown
    }

    private inline fun Long.hasFlag(flag: Long): Boolean = flag and this == flag
}
