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

package io.data2viz.geo.projection


import kotlin.test.Test

class StereographicTests {

    @Test
    fun stereographic_returns_expected_result() {
        val projection = stereographicProjection {
            translate(0.0, 0.0)
            scale = 1.0
        }

        checkProjectAndInvert(projection, 0.0, 0.0, 0.0, 0.0)
        checkProjectAndInvert(projection, -90.0, 0.0, -1.0, 0.0)
        checkProjectAndInvert(projection, 90.0, 0.0, 1.0, 0.0)
        checkProjectAndInvert(projection, 0.0, -90.0, 0.0, 1.0)
        checkProjectAndInvert(projection, 0.0, 90.0, 0.0, -1.0)

    }
}
