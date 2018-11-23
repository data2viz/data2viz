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