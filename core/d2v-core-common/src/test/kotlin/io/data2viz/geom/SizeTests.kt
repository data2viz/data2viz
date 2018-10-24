package io.data2viz.geom

import io.data2viz.test.TestBase
import kotlin.test.Test


@Suppress("FunctionName")
class SizeTests : TestBase() {

    @Test
    fun sizeOperator(){
        val size = Size(10.0, 20.0)
        size + 5.0 shouldBe Size(15.0, 25.0)
        size - 5.0 shouldBe Size( 5.0, 15.0)
        size * 3.0 shouldBe Size(30.0, 60.0)
        size / 2.0 shouldBe Size( 5.0, 10.0)
        size % 3.0 shouldBe Size( 1.0, 2.0)

        size + Size(2.0, 3.0) shouldBe Size( 12.0, 23.0)
        size - Size(2.0, 3.0) shouldBe Size(  8.0, 17.0)
        size * Size(2.0, 3.0) shouldBe Size( 20.0, 60.0)
        size / Size(2.0, 4.0) shouldBe Size(  5.0, 5.0)
        size % Size(4.0, 6.0) shouldBe Size(  2.0, 2.0)

    }
}
