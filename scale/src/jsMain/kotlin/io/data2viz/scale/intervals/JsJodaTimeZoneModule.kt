package io.data2viz.scale.intervals

@JsModule("@js-joda/timezone")
@JsNonModule
internal external object JsJodaTimeZoneModule

internal val jsJodaTz = JsJodaTimeZoneModule

public fun main() {
    println(jsJodaTz)
}