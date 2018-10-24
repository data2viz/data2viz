package io.data2viz.geom

import io.data2viz.test.TestBase
import kotlin.test.Test


@Suppress("FunctionName")
class RectTests : TestBase() {

    @Test
    fun rectPoints(){
        val rect = RectGeom(10.0, 20.0, 30.0, 40.0)

        rect.top shouldBeClose 20.0
        rect.left shouldBeClose 10.0
        rect.right shouldBeClose 10.0 + 30.0
        rect.bottom shouldBeClose 20.0 + 40.0

        rect.topLeft shouldBe Point(10.0, 20.0)
        rect.topRight shouldBe Point(40.0, 20.0)
        rect.bottomLeft shouldBe Point(10.0, 60.0)
        rect.bottomRight shouldBe Point(40.0, 60.0)

        rect.center shouldBe Point(25.0, 40.0)
    }

    @Test
    fun rectSize() {
        val rect = RectGeom(10.0, 20.0, 30.0, 40.0)
        rect.size shouldBe Size(30.0, 40.0)
        rect.size = Size(10.0, 20.0)
        rect.width shouldBeClose 10.0
        rect.height shouldBeClose 20.0
    }
}
