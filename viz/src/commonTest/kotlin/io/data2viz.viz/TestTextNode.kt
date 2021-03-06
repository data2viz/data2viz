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

class TestTextNode: TestBase(){


    @Test
    fun textAlign(){
        val textNode = TextNode().apply {
            textAlign = textAlign(TextHAlign.RIGHT)
        }

        textNode.vAlign shouldBe TextVAlign.BASELINE
        textNode.hAlign shouldBe TextHAlign.RIGHT

    }

    @Test
    fun parentProperty(){
        val textNode = TextNode().apply {
            parent = Viz().apply {
                textAlign = textAlign(TextHAlign.RIGHT, TextVAlign.HANGING)
            }
        }

        textNode.hAlign shouldBe TextHAlign.RIGHT
        textNode.vAlign shouldBe TextVAlign.HANGING

    }
}
