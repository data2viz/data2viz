package io.data2viz.viz

import io.data2viz.test.*
import kotlin.test.Test

class TestTextNode: TestBase(){


    @Test
    fun accessibility(){
        val textNode = TextNode().apply {
            textAlign = textAlign(TextAnchor.END)
        }

        textNode.style.baseline shouldBe TextAlignmentBaseline.BASELINE
        textNode.style.anchor shouldBe TextAnchor.END

    }
}