package io.data2viz.scale.intervals

import kotlinx.datetime.TimeZone
import kotlin.test.Test


@JsModule("@js-joda/timezone")
@JsNonModule
external object JsJodaTimeZoneModule

private val jsJodaTz = JsJodaTimeZoneModule

class jsTests : TestDate() {

    @Test
    fun load_timeZone() {
        println(jsJodaTz)
        println(TimeZone.of("America/Los_Angeles"))
    }
}