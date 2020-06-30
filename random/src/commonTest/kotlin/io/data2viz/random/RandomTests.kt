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

package io.data2viz.random

import io.data2viz.test.*
import kotlin.random.*
import kotlin.test.*

const val max = 1.0
const val min = 0.0
const val nbPoints = 60000

class RandomTests : TestBase() {


    @Test
    fun integerSeededDistribution() {
        val randomFunction1 = RandomDistribution(42).uniform(min, max)
        val list1 = (0 until 10)
            .map { randomFunction1() }

        val randomFunction2 = RandomDistribution(42).uniform(min, max)

        val list2 = (0 until 10)
            .map { randomFunction2() }

        list1 shouldBe list2
    }

    @Test
    fun longSeededDistribution() {
        val randomFunction1 = RandomDistribution(424242424242424242L).uniform(min, max)
        val list1 = (0 until 10)
            .map { randomFunction1() }

        val randomFunction2 = RandomDistribution(424242424242424242L).uniform(min, max)

        val list2 = (0 until 10)
            .map { randomFunction2() }

        list1 shouldBe list2
    }

    @Test
    fun uniformRandomDistribution_0_1() {
        val randomFunction = RandomDistribution(Random(42)).uniform(min, max)
        (0 until nbPoints)
            .map { randomFunction() }
            .all { it in min..max } shouldBe true
    }

    @Test
    fun normalRandomDistribution_mu05_sigma0_1() {
        val randomFunction = RandomDistribution(Random(42)).normal((max / 2.0), (max / 10.0))
        (0 until nbPoints)
            .map { randomFunction() }
            .all { it in min..max } shouldBe true
    }

    @Test
    fun logNormalRandomDistribuitonMu_1_sigma_0_2() {
        val randomFunction = { (RandomDistribution(Random(42)).logNormal(1.0, .2))() / 10.0 }
        (0 until nbPoints)
            .map { randomFunction() }
            .all { it in min..max } shouldBe true
    }

    @Test
    fun exponentialRandomDistributionLambda_1_4() {
        val randomFunction = { (RandomDistribution(Random(42)).exponential(1.4))() / 10.0 }
        (0 until nbPoints)
            .map { randomFunction() }
            .all { it in min..max } shouldBe true
    }

    @Test
    fun exponential_random_distribution_lambda_3() {
        val randomFunction = { (RandomDistribution(Random(42)).exponential(3.0))() / 10.0 }
        (0 until nbPoints)
            .map { randomFunction() }
            .all { it in min..max } shouldBe true
    }

    @Test
    fun bates_4_random_distribution() {
        val randomFunction = RandomDistribution(Random(42)).bates(4.0)
        (0 until nbPoints)
            .map { randomFunction() }
            .all { it in min..max } shouldBe true
    }


    @Test
    fun bates_20_random_distribution() {
        val randomFunction = RandomDistribution(Random(42)).bates(20.0)
        (0 until nbPoints)
            .map { randomFunction() }
            .all { it in min..max } shouldBe true
    }

    @Test
    fun irwinHall_4_random_distribution() {
        val randomFunction = { (RandomDistribution(Random(42)).irwinHall(4.0))() / 4.0 }
        (0 until nbPoints)
            .map { randomFunction() }
            .all { it in min..max } shouldBe true
    }

    @Test
    fun irwinHall_20_random_distribution() {
        val randomFunction = { (RandomDistribution(Random(42)).irwinHall(20.0))() / 20.0 }
        (0 until nbPoints)
            .map { randomFunction() }
            .all { it in min..max } shouldBe true
    }

}