package io.data2viz.time

import io.data2viz.test.TestBase
import kotlin.test.Test

class DateTests : TestBase() {

    @Test
    fun date_constructor() {
        val date = Date()
        val dateDiff = Date()
        (dateDiff.getTime() - date.getTime() <= 1)  shouldBe true

        val date2 = Date(186955523)
        date2.getTime() shouldBe 186955523L
    }

}