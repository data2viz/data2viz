package io.data2viz.viz

import org.w3c.dom.events.*


external interface SyntheticEvent {
    var bubbles: Boolean
    var currentTarget: EventTarget /* EventTarget & T */
    var cancelable: Boolean
    var defaultPrevented: Boolean
    var eventPhase: Number
    var isTrusted: Boolean
    val nativeEvent: Event
    fun preventDefault()
    fun isDefaultPrevented(): Boolean
    fun stopPropagation()
    fun isPropagationStopped(): Boolean
    fun persist()
    var target: EventTarget
    var timeStamp: Number
    var type: String
}

external interface TouchEvent : SyntheticEvent {
    var altKey: Boolean
    var changedTouches: TouchList
    var ctrlKey: Boolean
    fun getModifierState(key: String): Boolean
    var metaKey: Boolean
    //    override val nativeEvent: TouchEvent
    override val nativeEvent: Event
    var shiftKey: Boolean
    var targetTouches: TouchList
    var touches: TouchList
}

external interface TouchList {
    @nativeGetter
    operator fun get(index: Number): Touch?

    @nativeSetter
    operator fun set(index: Number, value: Touch)

    var length: Int
    fun item(index: Int): Touch
    fun identifiedTouch(identifier: Number): Touch
}


external interface Touch {
    var identifier: Number
    var target: EventTarget
    var screenX: Number
    var screenY: Number
    var clientX: Number
    var clientY: Number
    var pageX: Number
    var pageY: Number
}

