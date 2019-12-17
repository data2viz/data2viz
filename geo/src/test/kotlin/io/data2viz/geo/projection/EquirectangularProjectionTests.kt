/*
 * Copyright (c) 2018-2019. data2viz sàrl.
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

package io.data2viz.geo.projection

import io.data2viz.math.PI
import io.data2viz.math.deg
import io.data2viz.test.TestBase
import kotlin.test.Test

class EquirectangularProjectionTests : TestBase() {

    

    @Test
    fun equirectangular_point_excepted_result() {

        val equirectangular = equirectangularProjection {
            translate(0.0, 0.0)
            scale = 1.0
        }

        checkProjectAndInvert(equirectangular, 0.0, 0.0, 0.0, 0.0)
        checkProjectAndInvert(equirectangular, -180.0, 0.0, -PI, 0.0)
        checkProjectAndInvert(equirectangular, 180.0, 0.0, PI, 0.0)
        checkProjectAndInvert(equirectangular, 0.0, 30.0, 0.0, -PI / 6)
        checkProjectAndInvert(equirectangular, 0.0, -30.0, 0.0, PI / 6)
        checkProjectAndInvert(equirectangular, 30.0, 30.0, PI / 6, -PI / 6)
        checkProjectAndInvert(equirectangular, 30.0, -30.0, PI / 6, PI / 6)
        checkProjectAndInvert(equirectangular, -30.0, 30.0, -PI / 6, -PI / 6)
        checkProjectAndInvert(equirectangular, -30.0, -30.0, -PI / 6, PI / 6)
    }


    @Test
    fun equirectangular_rotate_30_0_0_excepted_result() {

        val equirectangular = equirectangularProjection {
            rotate(30.0.deg, 0.0.deg, 0.0.deg)
            translate(0.0, 0.0)
            scale = 1.0
        }

        checkProjectAndInvert(equirectangular, 0.0, 0.0, PI / 6, 0.0)
        checkProjectAndInvert(equirectangular, -180.0, 0.0, -5.0 / 6.0 * PI, 0.0)
        checkProjectAndInvert(equirectangular, 180.0, 0.0, -5.0 / 6.0 * PI, 0.0)
        checkProjectAndInvert(equirectangular, 0.0, 30.0, PI / 6, -PI / 6)
        checkProjectAndInvert(equirectangular, 0.0, -30.0, PI / 6, PI / 6)
        checkProjectAndInvert(equirectangular, 30.0, 30.0, PI / 3, -PI / 6)
        checkProjectAndInvert(equirectangular, 30.0, -30.0, PI / 3, PI / 6)
        checkProjectAndInvert(equirectangular, -30.0, 30.0, 0.0, -PI / 6)
        checkProjectAndInvert(equirectangular, -30.0, -30.0, 0.0, PI / 6)
    }

    @Test
    fun equirectangular_rotate_30_30_0_excepted_result() {

        val equirectangular = equirectangularProjection {
            translate(0.0, 0.0)
            rotate(30.0.deg, 30.0.deg, 0.0.deg)
            scale = 1.0
        }

        checkProjectAndInvert(equirectangular, 0.0, 0.0,  0.5880026035475674, -0.44783239692893245)
        checkProjectAndInvert(equirectangular, -180.0, 0.0, -2.5535900500422257,  0.44783239692893245)
        checkProjectAndInvert(equirectangular, 180.0, 0.0, -2.5535900500422257,  0.44783239692893245)
        checkProjectAndInvert(equirectangular, 0.0, 30.0,  0.8256075561643480, -0.94077119517052080)
        checkProjectAndInvert(equirectangular, 0.0, -30.0, 0.4486429615608479,  0.05804529130778048)
        checkProjectAndInvert(equirectangular, 30.0, 30.0, 1.4056476493802694, -0.70695172788721770);
        checkProjectAndInvert(equirectangular, 30.0, -30.0, 0.8760580505981933,  0.21823451436745955);
        checkProjectAndInvert(equirectangular, -30.0, 30.0, 0.0000000000000000, -1.04719755119659760);
        checkProjectAndInvert(equirectangular, -30.0, -30.0, 0.0000000000000000,  0.00000000000000000);


    }

    @Test
    fun equirectangular_rotate_0_0_30_excepted_result() {

        val equirectangular = equirectangularProjection {
            translate(0.0, 0.0)
            rotate(0.0.deg, 0.0.deg, 30.0.deg)
            scale = 1.0
        }

        checkProjectAndInvert(equirectangular, 0.0, 0.0,  0.0, 0.0)
        checkProjectAndInvert(equirectangular, -180.0, 0.0, -PI, 0.0)
        checkProjectAndInvert(equirectangular, 180.0, 0.0, PI, 0.0)
        checkProjectAndInvert(equirectangular, 0.0, 30.0,  -0.2810349015028135, -0.44783239692893245)
        checkProjectAndInvert(equirectangular, 0.0, -30.0, 0.2810349015028135,  0.44783239692893245)
        checkProjectAndInvert(equirectangular, 30.0, 30.0, 0.1651486774146268, -0.70695172788721760)
        checkProjectAndInvert(equirectangular, 30.0, -30.0, 0.6947382761967031,  0.21823451436745964)
        checkProjectAndInvert(equirectangular, -30.0, 30.0, -0.6947382761967031, -0.21823451436745964)
        checkProjectAndInvert(equirectangular, -30.0, -30.0, -0.1651486774146268,  0.70695172788721760)


    }

    @Test
    fun equirectangular_rotate_30_30_30_excepted_result() {

        val equirectangular = equirectangularProjection {
            translate(0.0, 0.0)
            rotate(30.0.deg, 30.0.deg, 30.0.deg)
            scale = 1.0
        }

        checkProjectAndInvert(equirectangular, 0.0, 0.0,  0.2810349015028135, -0.67513153293703170)
        checkProjectAndInvert(equirectangular, -180.0, 0.0, -2.8605577520869800,  0.67513153293703170)
        checkProjectAndInvert(equirectangular, 180.0, 0.0, -2.8605577520869800,  0.67513153293703170)
        checkProjectAndInvert(equirectangular, 0.0, 30.0,  -0.0724760059270816, -1.15865677086597720)
        checkProjectAndInvert(equirectangular, 0.0, -30.0, 0.4221351552567053, -0.16704161863132252)
        checkProjectAndInvert(equirectangular, 30.0, 30.0, 1.2033744221750944, -1.21537512510467320)
        checkProjectAndInvert(equirectangular, 30.0, -30.0, 0.8811235701944905, -0.18861638617540410)
        checkProjectAndInvert(equirectangular, -30.0, 30.0, -0.7137243789447654, -0.84806207898148100)
        checkProjectAndInvert(equirectangular, -30.0, -30.0, 0.0,                   0.0)
    }
}