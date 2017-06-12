package io.data2viz.random

import io.data2viz.color.colors
import io.data2viz.core.namespace
import io.data2viz.test.DOMExecutionContext
import io.data2viz.test.ExecutionContext
import io.data2viz.test.StringSpec
import kotlin.browser.document
import kotlin.js.Math

class RandomTests : StringSpec() {

    init {
        val max = 1
        val min = 0
        val nbPoints = 60000

        "Uniform random distribution [0..1] ($nbPoints values)"  {
            context ->
            val randomFunction = randomUniform(min, max)
            testAndGraphAndCheckMinMaxValues(context, randomFunction, nbPoints, min, max) shouldBe true
        }

        "Normal random distribution mu=0.5 sigma=0.1 : 99.99% of values should be in range ($nbPoints values)"  {
            context ->
            val randomFunction = randomNormal(max / 2.0, max / 10.0)
            testAndGraphAndCheckMinMaxValues(context, randomFunction, nbPoints, min, max) shouldBe true
        }

        "Log Normal random distribution mu=1 sigma=0.2 ($nbPoints values) (scaled 1/10)"  {
            context ->
            val randomFunction = { randomLogNormal(1, .2)() / 10.0 }
            testAndGraphAndCheckMinMaxValues(context, randomFunction, nbPoints, min, max) shouldBe true
        }

        "Exponential random distribution lambda=1.4 ($nbPoints values) (scaled 1/10)"  {
            context ->
            val randomFunction = {randomExponential(1.4)() / 10.0 }
            testAndGraphAndCheckMinMaxValues(context, randomFunction, nbPoints, min, max) shouldBe true
        }

        "Exponential random distribution lambda=3 ($nbPoints values) (scaled 1/10)"  {
            context ->
            val randomFunction = {randomExponential(3)() / 10.0 }
            testAndGraphAndCheckMinMaxValues(context, randomFunction, nbPoints, min, max) shouldBe true
        }

        "Bates (4 samples) random distribution (${nbPoints} values)"  {
            context ->
            val randomFunction = randomBates(4)
            testAndGraphAndCheckMinMaxValues(context, randomFunction, nbPoints, min, max) shouldBe true
        }

        "Bates (20 samples) random distribution (${nbPoints} values)"  {
            context ->
            val randomFunction = randomBates(20)
            testAndGraphAndCheckMinMaxValues(context, randomFunction, nbPoints, min, max) shouldBe true
        }

        "IrwinHall (4 samples) random distribution (${nbPoints} values)"  {
            context ->
            val randomFunction = { randomIrwinHall(4)() / 4.0 }
            testAndGraphAndCheckMinMaxValues(context, randomFunction, nbPoints, min, max) shouldBe true
        }

        "IrwinHall (20 samples) random distribution (${nbPoints} values)"  {
            context ->
            val randomFunction = { randomIrwinHall(20)() / 20.0 }
            testAndGraphAndCheckMinMaxValues(context, randomFunction, nbPoints, min, max) shouldBe true
        }
    }

    private fun testAndGraphAndCheckMinMaxValues(context: ExecutionContext, randomFunction: () -> Double, loops: Int, min: Int, max: Int): Boolean {
        var check = true
        val resultsXY: ArrayList<Double> = arrayListOf()

        (0 until loops).forEach {
            val x = randomFunction()
            resultsXY.add(x)
            check = check && x >= min && x < max
        }

        if (context !is DOMExecutionContext) return check

        val groupedResults = resultsXY.map { value -> Math.floor(value * 100) }.sorted().groupBy { it }
        val maxResultsFound = groupedResults.values.map { it.size }.max()!!.toDouble()

        context.element.appendChild(
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
        )

        return check
    }
}
