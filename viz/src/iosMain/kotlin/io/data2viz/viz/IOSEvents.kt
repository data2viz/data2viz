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
import kotlinx.cinterop.*
import platform.CoreGraphics.*
import platform.UIKit.*


internal object FakeDisposable: Disposable {
    override fun dispose() {
//        TODO("Not yet implemented")
    }
}


public actual class KTouch {
    public actual companion object TouchEventListener : KEventListener<KTouchEvent> {
        override fun addNativeListener(target: Any, listener: (KTouchEvent) -> Unit): Disposable {
            return addTouchIOSEventHandle(target, listener)
        }
    }
}

private fun addTouchIOSEventHandle(target: Any, listener: (KTouchEvent) -> Unit):
        Disposable {
    val renderer = target as IOSCanvasRenderer
    return renderer.uiTouchesHandler.addListener(listener)
}


public actual class KTouchStart {
    public actual companion object TouchStartEventListener : KEventListener<KPointerEvent> {
        override fun addNativeListener(target: Any, listener: (KPointerEvent) -> Unit): Disposable {
            return FakeDisposable
        }
    }
}

public actual class KTouchEnd {
    public actual companion object TouchEndEventListener : KEventListener<KPointerEvent> {
        override fun addNativeListener(target: Any, listener: (KPointerEvent) -> Unit): Disposable {
            return FakeDisposable
        }
    }
}

public actual class KTouchMove {
    public actual companion object TouchMoveEventListener : KEventListener<KPointerEvent> {
        override fun addNativeListener(target: Any, listener: (KPointerEvent) -> Unit): Disposable {
            return FakeDisposable
        }
    }
}


public actual class KMouseMove {
    public actual companion object PointerMoveEventListener : KEventListener<KMouseEvent> {
        override fun addNativeListener(target: Any, listener: (KMouseEvent) -> Unit): Disposable {
            return FakeDisposable
        }
    }
}

public actual class KMouseDown {
    public actual companion object PointerDownEventListener : KEventListener<KMouseEvent> {
        override fun addNativeListener(target: Any, listener: (KMouseEvent) -> Unit): Disposable {
            return FakeDisposable
        }
    }
}

public actual class KMouseUp {
    public actual companion object PointerUpEventListener : KEventListener<KMouseEvent> {
        override fun addNativeListener(target: Any, listener: (KMouseEvent) -> Unit): Disposable {
            return FakeDisposable
        }
    }
}


public actual class KPointerEnter {
    public actual companion object PointerEnterEventListener : KEventListener<KPointerEvent> {
        override fun addNativeListener(target: Any, listener: (KPointerEvent) -> Unit): Disposable {
            return FakeDisposable
        }
    }
}

public actual class KPointerLeave {
    public actual companion object PointerLeaveEventListener : KEventListener<KPointerEvent> {
        override fun addNativeListener(target: Any, listener: (KPointerEvent) -> Unit): Disposable {
            return FakeDisposable
        }
    }
}

public actual class KPointerClick {
    public actual companion object PointerClickEventListener : KEventListener<KPointerEvent> {
        override fun addNativeListener(target: Any, listener: (KPointerEvent) -> Unit): Disposable {
            return FakeDisposable
        }
    }
}

public actual class KPointerDoubleClick {
    public actual companion object PointerDoubleClickEventListener : KEventListener<KPointerEvent> {
        override fun addNativeListener(target: Any, listener: (KPointerEvent) -> Unit): Disposable {
            return FakeDisposable
        }
    }
}

@ExperimentalKEvent
public actual class KZoom {
    public actual companion object ZoomEventListener : KEventListener<KZoomEvent> {
        override fun addNativeListener(target: Any, listener: (KZoomEvent) -> Unit): Disposable {
            return FakeDisposable
        }
    }
}



internal actual fun <T> VizRenderer.addNativeEventListenerFromHandle(handle: KEventHandle<T>): Disposable where T : KEvent {
    val iosCanvasRenderer = this as IOSCanvasRenderer
    return handle.eventListener.addNativeListener(iosCanvasRenderer, handle.listener)
}


internal class IOSTouchDisposable(
    val uiTouchesHandler: UITouchesHandler,
    val listener: (KTouchEvent) -> Unit): Disposable {

    override fun dispose() {
        uiTouchesHandler.listeners.remove(this)
    }

}


internal class UITouchesHandler(private val view:IOSCanvasView) {


    init {
        (view as UIView).multipleTouchEnabled = true
        view.uiTouchesHandler = this
    }


    /**
     * La liste de toutes les touches en cours.
     */
    internal val currentTouches: MutableMap<UITouch, KPointer> = mutableMapOf()

    internal val listeners = mutableListOf<IOSTouchDisposable>()



    fun addListener(listener: (KTouchEvent) -> Unit): Disposable {
        val disposable = IOSTouchDisposable(this, listener)
        listeners.add(disposable)
        return disposable
    }


    private fun allPointers(): List<KPointer> = currentTouches.values.sortedBy { it.id }

    private var touchId = 0


    /**
     * On
     */
    fun touchesBegan(touches: Set<*>, withEvent: UIEvent?) {
        val pointers = (touches as Set<UITouch>)
            .map {
                val kPointer = KPointer(touchId++, it.toPosition())
                currentTouches[it] = kPointer
                kPointer
            }.toSet()

        val touchEvent = KTouchEvent(KTouchEventType.DOWN, allPointers(), pointers)
        notifyListeners(touchEvent)
    }

    fun touchesMoved(touches: Set<*>, withEvent: UIEvent?) {

		val uiTouches = touches as Set<UITouch>
		val pointers = uiTouches
            .map {
				val pointer = currentTouches[it]!!
				val updatedPointer = pointer.copy(pos = it.toPosition())
				currentTouches[it] = updatedPointer
				updatedPointer
            }
            .toSet()

        val touchEvent = KTouchEvent(KTouchEventType.MOVE, allPointers(), pointers)
        notifyListeners(touchEvent)
    }


    fun touchesEnded(touches: Set<*>, withEvent: UIEvent?) {
        val pointers = (touches as Set<UITouch>)
            .map {
                val pointer = currentTouches[it]!!
                currentTouches.remove(it)
                pointer
            }
            .toSet()

        val touchEvent = KTouchEvent(KTouchEventType.UP, allPointers(), pointers)
        notifyListeners(touchEvent)
    }

    fun touchesCancelled(touches: Set<*>, withEvent: UIEvent?) {
        val pointers = (touches as Set<UITouch>)
            .map {
                val pointer = currentTouches[it]!!
                currentTouches.remove(it)
                pointer
            }
            .toSet()

        val touchEvent = KTouchEvent(KTouchEventType.CANCEL, allPointers(), pointers)
        notifyListeners(touchEvent)
    }

    private fun notifyListeners(touchEvent: KTouchEvent) {
        listeners.forEach {
            it.listener(touchEvent)
        }
    }

    private fun UITouch.toPosition(): Point =
        this.locationInView(view).useContents { Point(this.x, this.y) }

}


