package io.data2viz.geo.projection

import io.data2viz.geo.Point
import io.data2viz.geo.path.geoPath
import io.data2viz.path.SvgPath
import io.data2viz.path.svgPath
import io.data2viz.test.TestBase
import kotlin.math.PI
import kotlin.math.abs
import kotlin.test.Test

class EquirectangularTests : TestBase() {

    val util = ProjectionTests()

    /*@Test
    fun multiPoint_projection() {
        val projection = equirectangular {
            translate = doubleArrayOf(.0, .0)
            scale = 1.0
            clipExtent = null
            precision = .0
        }

        val geoPath = geoPath(projection, svgPath())
        val path: SvgPath = geoPath.path(Point(doubleArrayOf(1.0, 2.0))) as SvgPath
        path.path shouldBe "M0.017453292519943295,-0.03490658503988659m0,4.5a4.5,4.5 0 1,1 0,-9a4.5,4.5 0 1,1 0,9z"
    }*/

    @Test
    fun equirectangular_various_projects_1() {
        val projection = equirectangular {
            translate = doubleArrayOf(.0, .0)
            rotate = doubleArrayOf(20.0, 10.0, 30.0)
            scale = 1.0
        }

        util.checkProjection(projection, 84.0, 59.0, doubleArrayOf(3.06246363239589, -1.2949057330916178))
        util.checkProjection(projection, -22.0, 16.0, doubleArrayOf(-0.2695850649725115, -0.3712480206334109))
        util.checkProjection(projection, 800.0, -800.0, doubleArrayOf(1.3520358022147179, 0.8616854414915359))
    }

    @Test
    fun equirectangular_various_projects_2() {
        val projection = equirectangular {
            translate = doubleArrayOf(40.0, 200.0)
            rotate = doubleArrayOf(5.0, .0, -30.0)
            scale = 1.0
        }

        util.checkProjection(projection, 84.0, 59.0, doubleArrayOf(41.56051867646999, 199.49380967242078))
        util.checkProjection(projection, -22.0, 16.0, doubleArrayOf(39.88565408364662, 199.61103404685258))
        util.checkProjection(projection, 800.0, -800.0, doubleArrayOf(38.47335118588215, 201.2207657535654))
    }

    @Test
    fun equirectangular_various_projects_3() {
        val projection = equirectangular {
            translate = doubleArrayOf(-100.0, 20.0)
            rotate = doubleArrayOf(-15.0, 20.0, .0)
            scale = 1.0
        }

        util.checkProjection(projection, 84.0, 59.0, doubleArrayOf(-98.18516654577843, 18.94762699928887))
        util.checkProjection(projection, -22.0, 16.0, doubleArrayOf(-100.74508864271014, 19.45129534757818))
        util.checkProjection(projection, 800.0, -800.0, doubleArrayOf(-99.63002236034055, 21.12049681215516))
    }

    
    @Test
    fun equirectangular_point_returns_the_expected_result_LEGACY() {
        val projection = equirectangular {
            translate = doubleArrayOf(.0, .0)
            scale = 1.0
        }

       util.checkProjection(projection, .0, .0, doubleArrayOf(.0, .0))
       util.checkProjection(projection, -180.0, .0, doubleArrayOf(-PI, .0))
       util.checkProjection(projection, 180.0, .0, doubleArrayOf(PI, .0))
       util.checkProjection(projection, .0, 30.0, doubleArrayOf(.0, -PI/6))
       util.checkProjection(projection, .0, -30.0, doubleArrayOf(.0, PI/6))
       util.checkProjection(projection, 30.0, 30.0, doubleArrayOf(PI/6, -PI/6))
       util.checkProjection(projection, 30.0, -30.0, doubleArrayOf(PI/6, PI/6))
       util.checkProjection(projection, -30.0, 30.0, doubleArrayOf(-PI/6, -PI/6))
       util.checkProjection(projection, -30.0, -30.0, doubleArrayOf(-PI/6, PI/6))
    }

    @Test
    fun equirectangular_rotate_30_0_point_returns_the_expected_result_LEGACY() {
        val projection = equirectangular {
            translate = doubleArrayOf(.0, .0)
            rotate = doubleArrayOf(30.0, .0)
            scale = 1.0
        }

        util.checkProjection(projection, .0, .0, doubleArrayOf(PI/6, .0))
        util.checkProjection(projection, -180.0, .0, doubleArrayOf(-PI * 5 / 6, .0))
        util.checkProjection(projection, 180.0, .0, doubleArrayOf(-5.0/6 * PI, .0))
        util.checkProjection(projection, .0, 30.0, doubleArrayOf(PI/6, -PI/6))
        util.checkProjection(projection, .0, -30.0, doubleArrayOf(PI/6, PI/6))
        util.checkProjection(projection, 30.0, 30.0, doubleArrayOf(PI/3, -PI/6))
        util.checkProjection(projection, 30.0, -30.0, doubleArrayOf(PI/3, PI/6))
        util.checkProjection(projection, -30.0, 30.0, doubleArrayOf(.0, -PI/6))
        util.checkProjection(projection, -30.0, -30.0, doubleArrayOf(.0, PI/6))
    }

    @Test
    fun equirectangular_rotate_30_30_point_returns_the_expected_result_LEGACY() {
        val projection = equirectangular {
            translate = doubleArrayOf(.0, .0)
            rotate = doubleArrayOf(30.0, 30.0)
            scale = 1.0
        }

        util.checkProjection(projection, .0, .0, doubleArrayOf(0.5880026035475674, -0.44783239692893245))
        util.checkProjection(projection, -180.0, .0, doubleArrayOf(-2.5535900500422257,  0.44783239692893245))
        util.checkProjection(projection, 180.0, .0, doubleArrayOf(-2.5535900500422257,  0.44783239692893245))
        util.checkProjection(projection, .0, 30.0, doubleArrayOf(0.8256075561643480, -0.94077119517052080))
        util.checkProjection(projection, .0, -30.0, doubleArrayOf(0.4486429615608479,  0.05804529130778048))
        util.checkProjection(projection, 30.0, 30.0, doubleArrayOf(1.4056476493802694, -0.70695172788721770))
        util.checkProjection(projection, 30.0, -30.0, doubleArrayOf(0.8760580505981933,  0.21823451436745955))
        util.checkProjection(projection, -30.0, 30.0, doubleArrayOf(.0, -1.04719755119659760))
        util.checkProjection(projection, -30.0, -30.0, doubleArrayOf(.0, .0))
    }

    @Test
    fun equirectangular_rotate_0_0_30_point_returns_the_expected_result_LEGACY() {
        val projection = equirectangular {
            translate = doubleArrayOf(.0, .0)
            rotate = doubleArrayOf(.0, .0, 30.0)
            scale = 1.0
        }

        util.checkProjection(projection, .0, .0, doubleArrayOf(.0, .0))
        util.checkProjection(projection, -180.0, .0, doubleArrayOf(-PI,  .0))
        util.checkProjection(projection, 180.0, .0, doubleArrayOf(PI,  .0))
        util.checkProjection(projection, .0, 30.0, doubleArrayOf(-0.2810349015028135, -0.44783239692893245))
        util.checkProjection(projection, .0, -30.0, doubleArrayOf(0.2810349015028135,  0.44783239692893245))
        util.checkProjection(projection, 30.0, 30.0, doubleArrayOf(0.1651486774146268, -0.70695172788721760))
        util.checkProjection(projection, 30.0, -30.0, doubleArrayOf(0.6947382761967031,  0.21823451436745964))
        util.checkProjection(projection, -30.0, 30.0, doubleArrayOf(-0.6947382761967031, -0.21823451436745964))
        util.checkProjection(projection, -30.0, -30.0, doubleArrayOf(-0.1651486774146268,  0.70695172788721760))
    }

    @Test
    fun equirectangular_rotate_30_30_30_point_returns_the_expected_result_LEGACY() {
        val projection = equirectangular {
            translate = doubleArrayOf(.0, .0)
            rotate = doubleArrayOf(30.0, 30.0, 30.0)
            scale = 1.0
        }

        util.checkProjection(projection, .0, .0, doubleArrayOf(0.2810349015028135, -0.67513153293703170))
        util.checkProjection(projection, -180.0, .0, doubleArrayOf(-2.8605577520869800,  0.67513153293703170))
        util.checkProjection(projection, 180.0, .0, doubleArrayOf(-2.8605577520869800,  0.67513153293703170))
        util.checkProjection(projection, .0, 30.0, doubleArrayOf(-0.0724760059270816, -1.15865677086597720))
        util.checkProjection(projection, .0, -30.0, doubleArrayOf(0.4221351552567053, -0.16704161863132252))
        util.checkProjection(projection, 30.0, 30.0, doubleArrayOf(1.2033744221750944, -1.21537512510467320))
        util.checkProjection(projection, 30.0, -30.0, doubleArrayOf(0.8811235701944905, -0.18861638617540410))
        util.checkProjection(projection, -30.0, 30.0, doubleArrayOf(-0.7137243789447654, -0.84806207898148100))
        util.checkProjection(projection, -30.0, -30.0, doubleArrayOf(.0, .0))
    }
}