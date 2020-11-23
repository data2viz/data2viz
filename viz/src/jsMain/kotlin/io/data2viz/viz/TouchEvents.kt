package io.data2viz.viz

import org.w3c.dom.events.*


public external interface SyntheticEvent {
    public var bubbles: Boolean
    public var currentTarget: EventTarget /* EventTarget & T */
    public var cancelable: Boolean
    public var defaultPrevented: Boolean
    public var eventPhase: Number
    public var isTrusted: Boolean
    public val nativeEvent: Event
    public fun preventDefault()
    public fun isDefaultPrevented(): Boolean
    public fun stopPropagation()
    public fun isPropagationStopped(): Boolean
    public fun persist()
    public var target: EventTarget
    public var timeStamp: Number
    public var type: String
}

public external interface TouchEvent : SyntheticEvent {
    public var altKey: Boolean
    public var changedTouches: TouchList
    public var ctrlKey: Boolean
    public fun getModifierState(key: String): Boolean
    public var metaKey: Boolean
    public override val nativeEvent: Event
    public var shiftKey: Boolean
    public var targetTouches: TouchList
    public var touches: TouchList
}

public external interface TouchList {
    @nativeGetter
    public operator fun get(index: Number): Touch?

    @nativeSetter
    public operator fun set(index: Number, value: Touch)

    public var length: Int
    public fun item(index: Int): Touch
    public fun identifiedTouch(identifier: Number): Touch
}


public external interface Touch {
    public var identifier: Number
    public var target: EventTarget
    public var screenX: Number
    public var screenY: Number
    public var clientX: Number
    public var clientY: Number
    public var pageX: Number
    public var pageY: Number
}

