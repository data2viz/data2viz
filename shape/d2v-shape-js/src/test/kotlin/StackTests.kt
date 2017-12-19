import io.data2viz.shape.stack.StackParam
import io.data2viz.shape.stack.StackOffset
import io.data2viz.shape.stack.StackOrder
import io.data2viz.shape.stack.stack
import kotlin.browser.document
import kotlin.dom.appendElement
import kotlin.js.Date


private data class dataClass(
        val date: Date,
        val apples: Int,
        val bananas: Int,
        val cherries: Int,
        val dates: Int
)

/**
 * results produced by D3.js using the same return method
 */
val results = arrayOf(
        arrayOf(
                "INDEX:0 0-3840 0-1600 0-640 0-320 0-100 ",
                "INDEX:1 3840-5760 1600-5040 640-1600 320-800 100-4900 ",
                "INDEX:2 5760-6720 5040-6000 1600-2240 800-1440 4900-5540 ",
                "INDEX:3 6720-7120 6000-6400 2240-2640 1440-1840 5540-5640 "
        ),
        arrayOf(
                "INDEX:2 1360-5200 1360-2960 1040-1680 1040-1360 740-840 ",
                "INDEX:3 5200-7120 2960-6400 1680-2640 1360-1840 840-5640 ",
                "INDEX:1 400-1360 400-1360 400-1040 400-1040 100-740 ",
                "INDEX:0 0-400 0-400 0-400 0-400 0-100 "
        ),
        arrayOf(
                "INDEX:1 1920-5760 3440-5040 960-1600 480-800 4800-4900 ",
                "INDEX:0 0-1920 0-3440 0-960 0-480 0-4800 ",
                "INDEX:2 5760-6720 5040-6000 1600-2240 800-1440 4900-5540 ",
                "INDEX:3 6720-7120 6000-6400 2240-2640 1440-1840 5540-5640 "
        ),
        arrayOf(
                "INDEX:3 3280-7120 4800-6400 2000-2640 1520-1840 5540-5640 ",
                "INDEX:2 1360-3280 1360-4800 1040-2000 1040-1520 740-5540 ",
                "INDEX:1 400-1360 400-1360 400-1040 400-1040 100-740 ",
                "INDEX:0 0-400 0-400 0-400 0-400 0-100 "
        ),
        arrayOf(
                "INDEX:1 1920-5760 3440-5040 960-1600 480-800 4800-4900 ",
                "INDEX:0 0-1920 0-3440 0-960 0-480 0-4800 ",
                "INDEX:2 5760-6720 5040-6000 1600-2240 800-1440 4900-5540 ",
                "INDEX:3 6720-7120 6000-6400 2240-2640 1440-1840 5540-5640 "
        ),
        arrayOf(
                "INDEX:0 0-1920 0-3440 0-960 0-480 0-4800 ",
                "INDEX:1 1920-2320 3440-3840 960-1360 480-880 4800-4900 ",
                "INDEX:2 2320-3280 3840-4800 1360-2000 880-1520 4900-5540 ",
                "INDEX:3 3280-7120 4800-6400 2000-2640 1520-1840 5540-5640 "
        ),
        arrayOf(
                "INDEX:3 5200-7120 2960-6400 1680-2640 1360-1840 840-5640 ",
                "INDEX:0 0-400 0-400 0-400 0-400 0-100 ",
                "INDEX:1 400-1360 400-1360 400-1040 400-1040 100-740 ",
                "INDEX:2 1360-5200 1360-2960 1040-1680 1040-1360 740-840 "
        ),
        arrayOf(
                "INDEX:0 0-1920 0-3440 0-960 0-480 0-4800 ",
                "INDEX:3 6720-7120 6000-6400 2240-2640 1440-1840 5540-5640 ",
                "INDEX:2 5760-6720 5040-6000 1600-2240 800-1440 4900-5540 ",
                "INDEX:1 1920-5760 3440-5040 960-1600 480-800 4800-4900 "
        ),
        arrayOf(
                "INDEX:0 0-100 0--200 0-100 0-200 ",
                "INDEX:1 100-200 -200--200 100-200 200-150 ",
                "INDEX:2 200-100 -200-0 200-50 150-100 ",
                "INDEX:3 100-200 0-0 50-0 100-0 "
        ),
        arrayOf(
                "INDEX:0 0-0.25 0-0.5 0-0.25 0-0.5 ",
                "INDEX:1 0.25-0.5 0.5-0.5 0.25-0.5 0.5-0.625 ",
                "INDEX:2 0.5-0.75 0.5-1 0.5-0.875 0.625-0.75 ",
                "INDEX:3 0.75-1 1-1 0.875-1 0.75-1 "
        ),
        arrayOf(
                "INDEX:0 0-0.5 0--200 0-100 0-200 ",
                "INDEX:1 0.5-1 -200--200 100-200 200-150 ",
                "INDEX:2 1-0.5 -200-0 200-50 150-100 ",
                "INDEX:3 0.5-1 0-0 50-0 100-0 "
        ),
        arrayOf(
                "INDEX:0 0-100 0-200 0-100 0-200 ",
                "INDEX:1 100-200 200-200 100-200 200-250 ",
                "INDEX:2 200-300 200-400 200-350 250-300 ",
                "INDEX:3 300-400 400-400 350-400 300-400 "
        ),
        arrayOf(
                "INDEX:0 0-100 -200-0 0-100 0-200 ",
                "INDEX:1 100-200 0-0 100-200 -50-0 ",
                "INDEX:2 -100-0 0-200 -150-0 -100--50 ",
                "INDEX:3 200-300 200-200 -200--150 -200--100 "
        ),
        arrayOf(
                "INDEX:0 -200--100 -200-0 -200--100 -200-0 ",
                "INDEX:1 -100-0 0-0 -100-0 0-50 ",
                "INDEX:2 0-100 0-200 0-150 50-100 ",
                "INDEX:3 100-200 200-200 150-200 100-200 "
        ),
        arrayOf(
                "INDEX:0 -100-0 0--200 0-100 0-200 ",
                "INDEX:1 0-100 -200--200 100-200 200-150 ",
                "INDEX:2 100-0 -200-0 200-50 150-100 ",
                "INDEX:3 0-100 0-0 50-0 100-0 "
        ),
        arrayOf(
                "INDEX:0 0-100 -50-150 -12.5-87.5 -40.625-159.375 ",
                "INDEX:1 100-200 150-150 87.5-187.5 159.375-209.375 ",
                "INDEX:2 200-300 150-350 187.5-337.5 209.375-259.375 ",
                "INDEX:3 300-400 350-350 337.5-387.5 259.375-359.375 "
        ),
        arrayOf(
                "INDEX:0 0-100 0--200 0-100 0-200 ",
                "INDEX:1 100-200 -200--200 100-200 200-150 ",
                "INDEX:2 200-100 -200-0 200-50 150-100 ",
                "INDEX:3 100-200 0-0 50-0 100-0 "
        ),
        arrayOf(
                "INDEX:1 100-200 0-200 87.5-187.5 65.625-265.625 ",
                "INDEX:3 300-400 400-400 337.5-437.5 315.625-365.625 ",
                "INDEX:2 200-300 200-400 187.5-337.5 265.625-315.625 ",
                "INDEX:0 0-100 0-0 37.5-87.5 -34.375-65.625 "
        ),
        arrayOf(
                "INDEX:3 100-200 0-200 100-200 0-200 ",
                "INDEX:2 0-100 0-0 0-100 -50-0 ",
                "INDEX:1 -100-0 -200-0 -150-0 -100--50 ",
                "INDEX:0 -200--100 -200--200 -200--150 -200--100 "
        ),
        arrayOf(
                "INDEX:1 0.5-1 0-0.5 -0.5-0 0.5-2.5 ",
                "INDEX:0 0-0.5 0-0 0--0.5 0-0.5 ",
                "INDEX:3 1.5-1 0.5-1 0.25-1 1.5-1 ",
                "INDEX:2 1-1.5 0.5-0.5 0-0.25 2.5-1.5 "
        ),
        arrayOf(
                "INDEX:0 0-1920 0-3440 0-960 0-480 0-4800 ",
                "INDEX:3 6720-7120 6000-6400 2240-2640 1440-1840 5540-5640 ",
                "INDEX:2 5760-6720 5040-6000 1600-2240 800-1440 4900-5540 ",
                "INDEX:1 1920-5760 3440-5040 960-1600 480-800 4800-4900 "
        )
)

@JsName("stackTests")
fun stackTests(arcValues: ArcValues) {

    // see https://github.com/d3/d3-shape#stacks
    val data = arrayOf(
            dataClass(Date(), 3840, 1920, 960, 400),
            dataClass(Date(), 1600, 3440, 960, 400),
            dataClass(Date(), 640, 960, 640, 400),
            dataClass(Date(), 320, 480, 640, 400),
            dataClass(Date(), 100, 4800, 640, 100)
    )

    val dataForOffset = arrayOf(
            dataClass(Date(), 100, 100, 100, 100),
            dataClass(Date(), 0, 200, 200, 0),
            dataClass(Date(), 50, 100, 150, 100),
            dataClass(Date(), 100, 200, 50, 50)
    )

    val dataDiverging = arrayOf(
            dataClass(Date(), 100, 100, -100, 100),
            dataClass(Date(), 0, -200, 200, 0),
            dataClass(Date(), -50, 100, -150, 100),
            dataClass(Date(), -100, 200, -50, -50)
    )

    val otherData = arrayOf(
            dataClass(Date(), 100, 100, -100, 100),
            dataClass(Date(), 0, -200, -200, 0),
            dataClass(Date(), -50, -100, -150, 100),
            dataClass(Date(), -100, 200, -50, 50)
    )

    val stackGenerator = stack<dataClass> {
        series = {
            arrayOf(
                    it.apples.toDouble(),
                    it.bananas.toDouble(),
                    it.cherries.toDouble(),
                    it.dates.toDouble()
            )
        }
        order = StackOrder.NONE
        offset = StackOffset.NONE
    }

    /*printResult(stackGenerator.stack(data), "No Order - No Offset", 0)

    stackGenerator.order = StackOrder.ASCENDING
    printResult(stackGenerator.stack(data), "Ascending - No Offset", 1)

    stackGenerator.order = StackOrder.DESCENDING
    printResult(stackGenerator.stack(data), "Descending - No Offset", 2)

    stackGenerator.order = StackOrder.REVERSE
    printResult(stackGenerator.stack(data), "Reverse - No Offset", 3)

    stackGenerator.order = StackOrder.INSIDEOUT
    printResult(stackGenerator.stack(data), "InsideOut - No Offset", 4)

    stackGenerator.order = StackOrder.NONE
    stackGenerator.values = {
        arrayOf(
                it.bananas.toDouble(),
                it.dates.toDouble(),
                it.cherries.toDouble(),
                it.apples.toDouble()
        )
    }
    printResult(stackGenerator.stack(data), "No Order - No Offset (changed values)", 5)

    stackGenerator.order = StackOrder.ASCENDING
    printResult(stackGenerator.stack(data), "Ascending - No Offset (changed values)", 6)

    stackGenerator.order = StackOrder.DESCENDING
    printResult(stackGenerator.stack(data), "Descending - No Offset (changed values)", 7)

    stackGenerator.order = StackOrder.NONE
    stackGenerator.offset = StackOffset.NONE
    printResult(stackGenerator.stack(dataDiverging), "No Order - No Offset - with NEGATIVE values", 8)

    stackGenerator.offset = StackOffset.EXPAND
    printResult(stackGenerator.stack(dataForOffset), "No Order - Expand Offset", 9)
    printResult(stackGenerator.stack(dataDiverging), "Expand  - with NEGATIVE values", 10)

    stackGenerator.offset = StackOffset.DIVERGING
    printResult(stackGenerator.stack(dataForOffset), "No Order - Diverging Offset", 11)
    printResult(stackGenerator.stack(dataDiverging), "Diverging with NEGATIVE values", 12)

    stackGenerator.offset = StackOffset.SILHOUETTE
    printResult(stackGenerator.stack(dataForOffset), "No Order - Silhouette Offset", 13)
    printResult(stackGenerator.stack(dataDiverging), "Silhouette with NEGATIVE values", 14)

    stackGenerator.offset = StackOffset.WIGGLE
    printResult(stackGenerator.stack(dataForOffset), "No Order - Wiggle Offset", 15)
    printResult(stackGenerator.stack(dataDiverging), "Wiggle with NEGATIVE values", 16)

    stackGenerator.order = StackOrder.INSIDEOUT
    printResult(stackGenerator.stack(dataForOffset), "INSIDEOUT + WIGGLE", 17)

    stackGenerator.order = StackOrder.REVERSE
    stackGenerator.offset = StackOffset.SILHOUETTE
    printResult(stackGenerator.stack(dataForOffset), "REVERSE + SILHOUETTE", 18)

    stackGenerator.order = StackOrder.DESCENDING
    stackGenerator.offset = StackOffset.EXPAND
    printResult(stackGenerator.stack(otherData), "DESCENDING + EXPAND", 19)

    stackGenerator.offset = StackOffset.DIVERGING
    printResult(stackGenerator.stack(data), "DESCENDING + DIVERGING", 20)*/
}

private fun printResult(ret: Array<StackParam<dataClass>>, name: String, resultIndex: Int) {
    val element = document.getElementById("d2vSamples")!!
    element.appendElement("h2") {
        textContent = name
    }
    ret.forEachIndexed { index, stackParam ->
        element.appendElement("pre") {
//            var content = "INDEX:${stackParam.index} SUM:${stackParam.stackedValues.sumByDouble { it.to - it.from }} "
            var content = "INDEX:${stackParam.index} "
            stackParam.stackedValues.forEach {
                content += "${it.from}-${it.to} "
            }
            if (results[resultIndex][index] == content) {
                className = "ok"
            } else {
                className = "error"
            }
            textContent = content
        }
    }
}