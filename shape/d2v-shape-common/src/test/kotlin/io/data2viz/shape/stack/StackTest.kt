package io.data2viz.shape.stack

import io.data2viz.test.TestBase
import kotlin.test.Test
import kotlin.test.assertEquals

class StackTest : TestBase() {

    private data class dataClass(
            val apples: Int,
            val bananas: Int,
            val cherries: Int,
            val dates: Int
    )


    // see https://github.com/d3/d3-shape#stacks
    private val data = arrayOf(
            dataClass(3840, 1920, 960, 400),
            dataClass(1600, 3440, 960, 400),
            dataClass(640, 960, 640, 400),
            dataClass(320, 480, 640, 400),
            dataClass(100, 4800, 640, 100)
    )

    private val dataForOffset = arrayOf(
            dataClass(100, 100, 100, 100),
            dataClass(0, 200, 200, 0),
            dataClass(50, 100, 150, 100),
            dataClass(100, 200, 50, 50)
    )

    private val dataDiverging = arrayOf(
            dataClass(100, 100, -100, 100),
            dataClass(0, -200, 200, 0),
            dataClass(-50, 100, -150, 100),
            dataClass(-100, 200, -50, -50)
    )

    private val otherData = arrayOf(
            dataClass(100, 100, -100, 100),
            dataClass(0, -200, -200, 0),
            dataClass(-50, -100, -150, 100),
            dataClass(-100, 200, -50, 50)
    )

    /**
     * results produced by D3.js using the same return method
     */
    val results = arrayOf(
            arrayOf(
                    "INDEX:0 0.0-3840.0 0.0-1600.0 0.0-640.0 0.0-320.0 0.0-100.0 ",
                    "INDEX:1 3840.0-5760.0 1600.0-5040.0 640.0-1600.0 320.0-800.0 100.0-4900.0 ",
                    "INDEX:2 5760.0-6720.0 5040.0-6000.0 1600.0-2240.0 800.0-1440.0 4900.0-5540.0 ",
                    "INDEX:3 6720.0-7120.0 6000.0-6400.0 2240.0-2640.0 1440.0-1840.0 5540.0-5640.0 "
            ),
            arrayOf(
                    "INDEX:2 1360.0-5200.0 1360.0-2960.0 1040.0-1680.0 1040.0-1360.0 740.0-840.0 ",
                    "INDEX:3 5200.0-7120.0 2960.0-6400.0 1680.0-2640.0 1360.0-1840.0 840.0-5640.0 ",
                    "INDEX:1 400.0-1360.0 400.0-1360.0 400.0-1040.0 400.0-1040.0 100.0-740.0 ",
                    "INDEX:0 0.0-400.0 0.0-400.0 0.0-400.0 0.0-400.0 0.0-100.0 "
            ),
            arrayOf(
                    "INDEX:1 1920.0-5760.0 3440.0-5040.0 960.0-1600.0 480.0-800.0 4800.0-4900.0 ",
                    "INDEX:0 0.0-1920.0 0.0-3440.0 0.0-960.0 0.0-480.0 0.0-4800.0 ",
                    "INDEX:2 5760.0-6720.0 5040.0-6000.0 1600.0-2240.0 800.0-1440.0 4900.0-5540.0 ",
                    "INDEX:3 6720.0-7120.0 6000.0-6400.0 2240.0-2640.0 1440.0-1840.0 5540.0-5640.0 "
            ),
            arrayOf(
                    "INDEX:3 3280.0-7120.0 4800.0-6400.0 2000.0-2640.0 1520.0-1840.0 5540.0-5640.0 ",
                    "INDEX:2 1360.0-3280.0 1360.0-4800.0 1040.0-2000.0 1040.0-1520.0 740.0-5540.0 ",
                    "INDEX:1 400.0-1360.0 400.0-1360.0 400.0-1040.0 400.0-1040.0 100.0-740.0 ",
                    "INDEX:0 0.0-400.0 0.0-400.0 0.0-400.0 0.0-400.0 0.0-100.0 "
            ),
            arrayOf(
                    "INDEX:1 1920.0-5760.0 3440.0-5040.0 960.0-1600.0 480.0-800.0 4800.0-4900.0 ",
                    "INDEX:0 0.0-1920.0 0.0-3440.0 0.0-960.0 0.0-480.0 0.0-4800.0 ",
                    "INDEX:2 5760.0-6720.0 5040.0-6000.0 1600.0-2240.0 800.0-1440.0 4900.0-5540.0 ",
                    "INDEX:3 6720.0-7120.0 6000.0-6400.0 2240.0-2640.0 1440.0-1840.0 5540.0-5640.0 "
            ),
            arrayOf(
                    "INDEX:0 0.0-1920.0 0.0-3440.0 0.0-960.0 0.0-480.0 0.0-4800.0 ",
                    "INDEX:1 1920.0-2320.0 3440.0-3840.0 960.0-1360.0 480.0-880.0 4800.0-4900.0 ",
                    "INDEX:2 2320.0-3280.0 3840.0-4800.0 1360.0-2000.0 880.0-1520.0 4900.0-5540.0 ",
                    "INDEX:3 3280.0-7120.0 4800.0-6400.0 2000.0-2640.0 1520.0-1840.0 5540.0-5640.0 "
            ),
            arrayOf(
                    "INDEX:3 5200.0-7120.0 2960.0-6400.0 1680.0-2640.0 1360.0-1840.0 840.0-5640.0 ",
                    "INDEX:0 0.0-400.0 0.0-400.0 0.0-400.0 0.0-400.0 0.0-100.0 ",
                    "INDEX:1 400.0-1360.0 400.0-1360.0 400.0-1040.0 400.0-1040.0 100.0-740.0 ",
                    "INDEX:2 1360.0-5200.0 1360.0-2960.0 1040.0-1680.0 1040.0-1360.0 740.0-840.0 "
            ),
            arrayOf(
                    "INDEX:0 0.0-1920.0 0.0-3440.0 0.0-960.0 0.0-480.0 0.0-4800.0 ",
                    "INDEX:3 6720.0-7120.0 6000.0-6400.0 2240.0-2640.0 1440.0-1840.0 5540.0-5640.0 ",
                    "INDEX:2 5760.0-6720.0 5040.0-6000.0 1600.0-2240.0 800.0-1440.0 4900.0-5540.0 ",
                    "INDEX:1 1920.0-5760.0 3440.0-5040.0 960.0-1600.0 480.0-800.0 4800.0-4900.0 "
            ),
            arrayOf(
                    "INDEX:0 0.0-100.0 0.0--200.0 0.0-100.0 0.0-200.0 ",
                    "INDEX:1 100.0-200.0 -200.0--200.0 100.0-200.0 200.0-150.0 ",
                    "INDEX:2 200.0-100.0 -200.0-0.0 200.0-50.0 150.0-100.0 ",
                    "INDEX:3 100.0-200.0 0.0-0.0 50.0-0.0 100.0-0.0 "
            ),
            arrayOf(
                    "INDEX:0 0.0-0.25 0.0-0.5 0.0-0.25 0.0-0.5 ",
                    "INDEX:1 0.25-0.5 0.5-0.5 0.25-0.5 0.5-0.625 ",
                    "INDEX:2 0.5-0.75 0.5-1.0 0.5-0.875 0.625-0.75 ",
                    "INDEX:3 0.75-1.0 1.0-1.0 0.875-1.0 0.75-1.0 "
            ),
            arrayOf(
                    "INDEX:0 0.0-0.5 0.0--200.0 0.0-100.0 0.0-200.0 ",
                    "INDEX:1 0.5-1.0 -200.0--200.0 100.0-200.0 200.0-150.0 ",
                    "INDEX:2 1.0-0.5 -200.0-0.0 200.0-50.0 150.0-100.0 ",
                    "INDEX:3 0.5-1.0 0.0-0.0 50.0-0.0 100.0-0.0 "
            ),
            arrayOf(
                    "INDEX:0 0.0-100.0 0.0-200.0 0.0-100.0 0.0-200.0 ",
                    "INDEX:1 100.0-200.0 200.0-200.0 100.0-200.0 200.0-250.0 ",
                    "INDEX:2 200.0-300.0 200.0-400.0 200.0-350.0 250.0-300.0 ",
                    "INDEX:3 300.0-400.0 400.0-400.0 350.0-400.0 300.0-400.0 "
            ),
            arrayOf(
                    "INDEX:0 0.0-100.0 -200.0-0.0 0.0-100.0 0.0-200.0 ",
                    "INDEX:1 100.0-200.0 0.0-0.0 100.0-200.0 -50.0-0.0 ",
                    "INDEX:2 -100.0-0.0 0.0-200.0 -150.0-0.0 -100.0--50.0 ",
                    "INDEX:3 200.0-300.0 200.0-200.0 -200.0--150.0 -200.0--100.0 "
            ),
            arrayOf(
                    "INDEX:0 -200.0--100.0 -200.0-0.0 -200.0--100.0 -200.0-0.0 ",
                    "INDEX:1 -100.0-0.0 0.0-0.0 -100.0-0.0 0.0-50.0 ",
                    "INDEX:2 0.0-100.0 0.0-200.0 0.0-150.0 50.0-100.0 ",
                    "INDEX:3 100.0-200.0 200.0-200.0 150.0-200.0 100.0-200.0 "
            ),
            arrayOf(
                    "INDEX:0 -100.0-0.0 0.0--200.0 0.0-100.0 0.0-200.0 ",
                    "INDEX:1 0.0-100.0 -200.0--200.0 100.0-200.0 200.0-150.0 ",
                    "INDEX:2 100.0-0.0 -200.0-0.0 200.0-50.0 150.0-100.0 ",
                    "INDEX:3 0.0-100.0 0.0-0.0 50.0-0.0 100.0-0.0 "
            ),
            arrayOf(
                    "INDEX:0 0.0-100.0 -50.0-150.0 -12.5-87.5 -40.625-159.375 ",
                    "INDEX:1 100.0-200.0 150.0-150.0 87.5-187.5 159.375-209.375 ",
                    "INDEX:2 200.0-300.0 150.0-350.0 187.5-337.5 209.375-259.375 ",
                    "INDEX:3 300.0-400.0 350.0-350.0 337.5-387.5 259.375-359.375 "
            ),
            arrayOf(
                    "INDEX:0 0.0-100.0 0.0--200.0 0.0-100.0 0.0-200.0 ",
                    "INDEX:1 100.0-200.0 -200.0--200.0 100.0-200.0 200.0-150.0 ",
                    "INDEX:2 200.0-100.0 -200.0-0.0 200.0-50.0 150.0-100.0 ",
                    "INDEX:3 100.0-200.0 0.0-0.0 50.0-0.0 100.0-0.0 "
            ),
            arrayOf(
                    "INDEX:1 100.0-200.0 0.0-200.0 87.5-187.5 65.625-265.625 ",
                    "INDEX:3 300.0-400.0 400.0-400.0 337.5-437.5 315.625-365.625 ",
                    "INDEX:2 200.0-300.0 200.0-400.0 187.5-337.5 265.625-315.625 ",
                    "INDEX:0 0.0-100.0 0.0-0.0 37.5-87.5 -34.375-65.625 "
            ),
            arrayOf(
                    "INDEX:3 100.0-200.0 0.0-200.0 100.0-200.0 0.0-200.0 ",
                    "INDEX:2 0.0-100.0 0.0-0.0 0.0-100.0 -50.0-0.0 ",
                    "INDEX:1 -100.0-0.0 -200.0-0.0 -150.0-0.0 -100.0--50.0 ",
                    "INDEX:0 -200.0--100.0 -200.0--200.0 -200.0--150.0 -200.0--100.0 "
            ),

            // TODO : NOTE THAT CODE GENERATES "-0.0" DOUBLE
            arrayOf(
                    "INDEX:1 0.5-1.0 -0.0-0.5 -0.5--0.0 0.5-2.5 ",
                    "INDEX:0 0.0-0.5 -0.0--0.0 -0.0--0.5 0.0-0.5 ",
                    "INDEX:3 1.5-1.0 0.5-1.0 0.25-1.0 1.5-1.0 ",
                    "INDEX:2 1.0-1.5 0.5-0.5 -0.0-0.25 2.5-1.5 "
            ),
            arrayOf(
                    "INDEX:0 0.0-1920.0 0.0-3440.0 0.0-960.0 0.0-480.0 0.0-4800.0 ",
                    "INDEX:3 6720.0-7120.0 6000.0-6400.0 2240.0-2640.0 1440.0-1840.0 5540.0-5640.0 ",
                    "INDEX:2 5760.0-6720.0 5040.0-6000.0 1600.0-2240.0 800.0-1440.0 4900.0-5540.0 ",
                    "INDEX:1 1920.0-5760.0 3440.0-5040.0 960.0-1600.0 480.0-800.0 4800.0-4900.0 "
            )
    )

    private val stackGenerator = stack<dataClass> {
        series = {
            arrayOf(
                    it.apples.toDouble(),
                    it.bananas.toDouble(),
                    it.cherries.toDouble(),
                    it.dates.toDouble()
            )
        }
        order = StackOrders.NONE
        offset = StackOffsets.NONE
    }

    private val stackGeneratorDisordered = stack<dataClass> {
        series = {
            arrayOf(
                    it.bananas.toDouble(),
                    it.dates.toDouble(),
                    it.cherries.toDouble(),
                    it.apples.toDouble()
            )
        }
        order = StackOrders.NONE
        offset = StackOffsets.NONE
    }

    private fun checkResults(ret: Array<StackParam<dataClass>>, name: String, resultIndex: Int) {
        ret.forEachIndexed { index, stackParam ->
            var content = "INDEX:${stackParam.index} "
            stackParam.stackedValues.forEach {
                content += "${it.from}-${it.to} "
            }
            assertEquals(results[resultIndex][index].round(), content.round(), name)
        }
    }

    // TODO uncomment tests when kotlinJS 0.toString() and -0.toString() is fixed !!
    @Test
    fun no_order_no_offset() {
        checkResults(stackGenerator.stack(data), "No Order - No Offset", 0)
    }

    @Test
    fun ascending_no_offset() {
        stackGenerator.order = StackOrders.ASCENDING
        checkResults(stackGenerator.stack(data), "Ascending - No Offset", 1)
    }

    @Test
    fun descending_no_offset() {
        stackGenerator.order = StackOrders.DESCENDING
        checkResults(stackGenerator.stack(data), "Descending - No Offset", 2)
    }

    @Test
    fun reverse_no_offset() {
        stackGenerator.order = StackOrders.REVERSE
        checkResults(stackGenerator.stack(data), "Reverse - No Offset", 3)
    }

    @Test
    fun insideout_no_offset() {
        stackGenerator.order = StackOrders.INSIDEOUT
        checkResults(stackGenerator.stack(data), "InsideOut - No Offset", 4)
    }

    @Test
    fun no_order_no_offset_change_order() {
        checkResults(stackGeneratorDisordered.stack(data), "No Order - No Offset (changed values order)", 5)
    }

    @Test
    fun ascending_no_offset_change_order() {
        stackGeneratorDisordered.order = StackOrders.ASCENDING
        checkResults(stackGeneratorDisordered.stack(data), "Ascending - No Offset (changed values)", 6)
    }

    @Test
    fun descending_no_offset_change_order() {
        stackGeneratorDisordered.order = StackOrders.DESCENDING
        checkResults(stackGeneratorDisordered.stack(data), "Descending - No Offset (changed values)", 7)
    }

    @Test
    fun diverging_no_offset() {
        stackGeneratorDisordered.order = StackOrders.NONE
        stackGeneratorDisordered.offset = StackOffsets.NONE
        checkResults(stackGeneratorDisordered.stack(dataDiverging), "No Order - No Offset - with NEGATIVE values", 8)
    }

    @Test
    fun diverging_expand() {
        stackGeneratorDisordered.offset = StackOffsets.EXPAND
        checkResults(stackGeneratorDisordered.stack(dataForOffset), "No Order - Expand Offset", 9)
        checkResults(stackGeneratorDisordered.stack(dataDiverging), "Expand  - with NEGATIVE values", 10)
    }

    @Test
    fun diverging_negatives() {
        stackGeneratorDisordered.offset = StackOffsets.DIVERGING
        checkResults(stackGeneratorDisordered.stack(dataForOffset), "No Order - Diverging Offset", 11)
        checkResults(stackGeneratorDisordered.stack(dataDiverging), "Diverging with NEGATIVE values", 12)
    }

    @Test
    fun silhouette_negatives() {
        stackGeneratorDisordered.offset = StackOffsets.SILHOUETTE
        checkResults(stackGeneratorDisordered.stack(dataForOffset), "No Order - Silhouette Offset", 13)
        checkResults(stackGeneratorDisordered.stack(dataDiverging), "Silhouette with NEGATIVE values", 14)
    }

    @Test
    fun wiggle_negatives() {
        stackGeneratorDisordered.offset = StackOffsets.WIGGLE
        checkResults(stackGeneratorDisordered.stack(dataForOffset), "No Order - Wiggle Offset", 15)
        checkResults(stackGeneratorDisordered.stack(dataDiverging), "Wiggle with NEGATIVE values", 16)
    }

    @Test
    fun insideout_wiggle() {
        stackGeneratorDisordered.offset = StackOffsets.WIGGLE
        stackGeneratorDisordered.order = StackOrders.INSIDEOUT
        checkResults(stackGeneratorDisordered.stack(dataForOffset), "INSIDEOUT + WIGGLE", 17)
    }

    @Test
    fun reverse_silhouette() {
        stackGeneratorDisordered.order = StackOrders.REVERSE
        stackGeneratorDisordered.offset = StackOffsets.SILHOUETTE
        checkResults(stackGeneratorDisordered.stack(dataForOffset), "REVERSE + SILHOUETTE", 18)
    }

    // TODO check round() formula cause a "-0" to disappear ...
    // DESCENDING + EXPAND.
    // Expected <INDEX:1 0.500000-1 0-0.500000 -0.500000-0 0.500000-2.500000 >
    // actual   <INDEX:1 0.500000-1 0-0.500000 -0.5000000 0.500000-2.500000 >.
    /*@Test
    fun descending_expand() {
        stackGeneratorDisordered.order = StackOrders.DESCENDING
        stackGeneratorDisordered.offset = StackOffsets.EXPAND
        val ret = stackGeneratorDisordered.stack(otherData)
        checkResults(ret, "DESCENDING + EXPAND", 19)
    }*/

    @Test
    fun descending_diverging() {
        stackGeneratorDisordered.order = StackOrders.DESCENDING
        stackGeneratorDisordered.offset = StackOffsets.DIVERGING
        checkResults(stackGeneratorDisordered.stack(data), "DESCENDING + DIVERGING", 20)
    }
}