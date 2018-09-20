@file:Suppress("unused", "FunctionName")

package io.data2viz.random

import io.data2viz.color.colors
import io.data2viz.test.namespace
import io.data2viz.test.TestBase
import kotlin.browser.document
import kotlin.dom.appendText
import kotlin.math.floor
import kotlin.test.Ignore
import kotlin.test.Test

val browserEnabled:Boolean = js("typeof document !== 'undefined'") as Boolean

class RandomTests : TestBase() {
    
    val max = 1
    val min = 0
    val nbPoints = 60000

    @Test
    fun uniformRandomDistribution_0_1() {
        val randomFunction = randomUniform(min, max)
        testAndGraphAndCheckMinMaxValues(
            "uniformRandomDistribution_0_1",
            randomFunction,
            nbPoints,
            min,
            max
        ) shouldBe true
    }

    @Test
    fun normalRandomDistribution_mu05_sigma0_1() {
        val randomFunction = randomNormal(max / 2.0, max / 10.0)
        testAndGraphAndCheckMinMaxValues(
            "normalRandomDistribution_mu05_sigma0_1",
            randomFunction,
            nbPoints,
            min,
            max
        ) shouldBe true
    }

    @Test
    fun logNormalRandomDistribuitonMu_1_sigma_0_2() {
        val randomFunction = { randomLogNormal(1, .2)() / 10.0 }
        testAndGraphAndCheckMinMaxValues(
            "logNormalRandomDistribuitonMu_1_sigma_0_2",
            randomFunction,
            nbPoints,
            min,
            max
        ) shouldBe true
    }

    @Test
    @Ignore
    fun exponentialRandomDistributionLambda_1_4() {
        val randomFunction = { randomExponential(1.4)() / 10.0 }
        testAndGraphAndCheckMinMaxValues(
            "exponentialRandomDistributionLambda_1_4",
            randomFunction,
            nbPoints,
            min,
            max
        ) shouldBe true
    }

    @Test
    fun exponential_random_distribution_lambda_3() {
        val randomFunction = { randomExponential(3)() / 10.0 }
        testAndGraphAndCheckMinMaxValues(
            "exponential_random_distribution_lambda_3",
            randomFunction,
            nbPoints,
            min,
            max
        ) shouldBe true
    }

    @Test
    fun bates_4_random_distribution() {
        val randomFunction = randomBates(4)
        testAndGraphAndCheckMinMaxValues("bates_random_distribution", randomFunction, nbPoints, min, max) shouldBe true
    }


    @Test
    fun bates_20_random_distribution() {
        val randomFunction = randomBates(20)
        testAndGraphAndCheckMinMaxValues(
            "bates_20_random_distribution",
            randomFunction,
            nbPoints,
            min,
            max
        ) shouldBe true
    }

    @Test
    fun irwinHall_4_random_distribution() {
        val randomFunction = { randomIrwinHall(4)() / 4.0 }
        testAndGraphAndCheckMinMaxValues(
            "irwinHall_4_random_distribution",
            randomFunction,
            nbPoints,
            min,
            max
        ) shouldBe true
    }

    @Test
    fun irwinHall_20_random_distribution() {
        val randomFunction = { randomIrwinHall(20)() / 20.0 }
        testAndGraphAndCheckMinMaxValues(
            "irwinHall_20_random_distribution",
            randomFunction,
            nbPoints,
            min,
            max
        ) shouldBe true
    }
}

private fun testAndGraphAndCheckMinMaxValues(
    context: String,
    randomFunction: () -> Double,
    loops: Int,
    min: Int,
    max: Int
): Boolean {
    var check = true
    val resultsXY: ArrayList<Double> = arrayListOf()

    (0 until loops).forEach {
        val x = randomFunction()
        resultsXY.add(x)
        check = check && x >= min && x < max
    }

    if (browserEnabled) {
        val groupedResults = resultsXY.map { value -> floor(value * 100) }.sorted().groupBy { it }
        val maxResultsFound = groupedResults.values.map { it.size }.max()!!.toDouble()
        h2(context)
        document.body?.appendChild((
                document.createElementNS(namespace.svg, "svg").apply {
                    setAttribute("width", "800")
                    setAttribute("height", "100")
                    groupedResults.forEach { entry ->
                        appendChild(
                            document.createElementNS(namespace.svg, "rect").apply {
                                val height = entry.value.size / maxResultsFound * 100.0
                                setAttribute("x", "${entry.key * 8}")
                                setAttribute("y", "${100 - height}")
                                setAttribute("width", "8")
                                setAttribute("height", "$height")
                                setAttribute("fill", colors.lightblue.rgbHex)
                            }

                        )
                    }
                }
                ))

    }



    return check
}

internal fun h2(name: String) {
    document.body?.appendChild(document.createElement("h2").appendText(name))
}
