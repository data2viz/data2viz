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

package io.data2viz.viz

import io.data2viz.color.Colors
import io.data2viz.geom.Size
import io.data2viz.test.TestBase
import kotlin.test.Test

class TestHeadlessVizContainer: TestBase() {

    @Test
    fun defaultVizContainer() {
        val vizContainer = HeadlessVizContainer()
        vizContainer.newViz {
            rect {
                width = 100.0
                height = 50.0
                fill = Colors.Web.red
            }
        }

        vizContainer.vizList.size shouldBe 1
        val rect = vizContainer.vizList[0].activeLayer.children[0] as RectNode
        rect.size shouldBe Size(100.0, 50.0)
        rect.strokeWidth shouldBe 1.0
        rect.strokeColor shouldBe null
        rect.fill shouldBe Colors.Web.red
    }
}