package io.data2viz.geo.projection

import io.data2viz.math.deg
import io.data2viz.test.TestBase
import kotlin.math.PI
import kotlin.test.Test

class EquirectangularProjectionTests : TestBase() {

    val util = ProjectionTests()

    @Test
    fun equirectangular_various_projects_1() {
        val projection = equirectangularProjection {
            translate = doubleArrayOf(.0, .0)
            rotate = arrayOf(20.0.deg, 10.0.deg, 30.0.deg)
            scale = 1.0
        }

        util.checkProjection(projection, 84.0, 59.0, doubleArrayOf(3.06246363239589, -1.2949057330916178))
        util.checkProjection(projection, -22.0, 16.0, doubleArrayOf(-0.2695850649725115, -0.3712480206334109))
        util.checkProjection(projection, 800.0, -800.0, doubleArrayOf(1.3520358022147179, 0.8616854414915359))
    }
}