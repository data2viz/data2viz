package io.data2viz.scale.intervals

import kotlinx.datetime.TimeZone
import kotlin.test.Test

class jsTests : TestDate() {

    @Test
    fun load_timeZone() {
        console.log(jsJodaTz)
        console.log(TimeZone.of("America/Los_Angeles"))
    }
}