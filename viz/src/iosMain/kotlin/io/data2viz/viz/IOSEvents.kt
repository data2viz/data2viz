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

import io.data2viz.viz.KEventListener
import io.data2viz.viz.KMouseEvent
import io.data2viz.viz.KPointerEvent
import io.data2viz.viz.KTouchEvent


internal object FakeDisposable: Disposable {
    override fun dispose() {
//        TODO("Not yet implemented")
    }
}


public actual class KTouch {
    public actual companion object TouchEventListener : KEventListener<KTouchEvent> {
        override fun addNativeListener(target: Any, listener: (KTouchEvent) -> Unit): Disposable {
            addTouchAndroidEventHandle(target, listener)
            return FakeDisposable
        }
    }
}

private fun addTouchAndroidEventHandle(target: Any, listener: (KTouchEvent) -> Unit):
        Disposable {

    val renderer = target as IOSCanvasRenderer

//    val handler = object : VizTouchListener {
//        override fun onTouchEvent(view: View, event: MotionEvent?): Boolean {
//            if (event != null) {
//                listener(event.toKTouchEvent())
//            }
//            return true
//        }
//    }

    return FakeDisposable
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
    return object : Disposable { // for the test
        override fun dispose() {
        }
    }
}
