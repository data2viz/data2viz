package io.data2viz.test

import kotlin.test.Test


class Rounding: TestBase() {
    
    @Test
    @JsName("specialChars")
    fun `Un nom avec des caractères non autorisés`(){
        true shouldBe true
    }
    
    @Test
    fun normalizeDoublesInString(){
        "0".round() shouldBe "0"
        "0.0".round() shouldBe "0"
        "0.01".round() shouldBe "0.010000"
        "0.000001".round() shouldBe "0.000001"
        "0.0000001".round() shouldBe "0"
    }
}

