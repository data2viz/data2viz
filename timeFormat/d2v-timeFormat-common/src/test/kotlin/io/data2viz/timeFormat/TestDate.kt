package io.data2viz.timeFormat

import io.data2viz.time.Date
import io.data2viz.test.TestBase

open class TestDate : TestBase() {
    infix fun Date?.shouldBe(date: Date?) {
        if (this == null && date != null || this != null && date == null) throw AssertionError("$this did not equal $date")
        if (this == null && date == null) return
        if (!(date!!.year() == this!!.year()
                && date.month() == this.month()
                && date.dayOfMonth() == this.dayOfMonth()
                && date.hour() == this.hour()
                && date.minute() == this.minute()
                && date.second() == this.second()
                && date.millisecond() == this.millisecond()))
            throw AssertionError("$this did not equal $date")
    }
}