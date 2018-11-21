package io.data2viz.random

import io.data2viz.test.*
import kotlin.random.*
import kotlin.test.*

const val max = 1.0
const val min = 0.0
const val nbPoints = 60000

class RandomTests : TestBase() {

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