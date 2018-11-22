package io.data2viz.viz

import io.data2viz.test.*
import kotlin.test.Test

class TestTextNode: TestBase(){


    @Test
    fun accessibility(){
        val textNode = TextNode().apply {
            parent = Viz()
            textAlign = textAlign(THAlign.RIGHT)
        }

        textNode.vAlign shouldBe TVAlign.BASELINE
        textNode.hAlign shouldBe THAlign.RIGHT

    }

    @Test
    fun parentProperty(){
        val textNode = TextNode().apply {
            parent = Viz().apply {
                textAlign = textAlign(THAlign.RIGHT, TVAlign.HANGING)
            }
        }

        textNode.hAlign shouldBe THAlign.RIGHT
        textNode.vAlign shouldBe TVAlign.HANGING

    }
}