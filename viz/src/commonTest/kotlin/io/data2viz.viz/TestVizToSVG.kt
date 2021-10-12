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

import io.data2viz.test.*
import kotlin.test.Test

class TestVizToSVG: TestBase(){


    @Test
    fun testEmptyViz(){
        viz {
            width = 100.0
            height = 100.0
        }.toSVG() shouldBe """<?xml version="1.0"?><svg xmlns="http://www.w3.org/2000/svg" width="100.0" height="100.0"><g></g></svg>"""

    }

    @Test
    fun testSquare(){
        viz {
            width = 100.0
            height = 100.0

            rect {
                x = 10.0
                y = 10.0
                width = 100.0
                height = 100.0
            }

        }.toSVG() shouldBe """
            <?xml version="1.0"?>
            <svg xmlns="http://www.w3.org/2000/svg" width="100.0" height="100.0">
            <g>
            <rect x="10.0" y="10.0" width="100.0" height="100.0">
            </rect>
            </g>
            </svg>"""
            .trimIndent()
            .replace("\n", "")

    }

}
