package io.data2viz.viz

import android.view.*


actual class KMouseMove {
	actual companion object MouseMoveEventListener : KEventListener<KMouseEvent> {
		override fun addNativeListener(target: Any, listener: (KMouseEvent) -> Unit): Any {
			val view = target as View
			val motionEvent: MotionEvent

			return "todo"
		}





	}
}

/**
 * Add an event listener on a a viz.
 * @return an handler to eventually remove later.
 */
actual inline fun <T> Viz.on(
    eventListener: KEventListener<T>,
    listener: (T) -> Unit
): Any {
    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
}

actual class KMouseClick {
    actual companion object MouseClickEventListener : KEventListener<KMouseEvent>{
		override fun addNativeListener(target: Any, listener: (KMouseEvent) -> Unit): Any {
			TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
		}

	}
}