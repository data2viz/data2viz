package io.data2viz.viz

import io.data2viz.test.*
import kotlin.test.Test

class TestTextNode: TestBase(){


    @Test
    fun accessibility(){
        val textNode = TextNode().apply {
            parent = Viz()
            textAlign = textAlign(TextAnchor.END)
        }

        textNode.baseline shouldBe TextAlignmentBaseline.BASELINE
        textNode.anchor shouldBe TextAnchor.END

    }

    @Test
    fun parentProperty(){
        val textNode = TextNode().apply {
            parent = Viz().apply {
                textAlign = textAlign(TextAnchor.END, TextAlignmentBaseline.HANGING)
            }
        }

        textNode.anchor shouldBe TextAnchor.END
        textNode.baseline shouldBe TextAlignmentBaseline.HANGING

    }
}