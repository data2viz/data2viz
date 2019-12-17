/*
 * Copyright (c) 2018-2019. data2viz sàrl.
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