package io.data2viz.sankey

import io.data2viz.test.TestBase
import kotlin.test.Test

class ChordTests : TestBase() {

    val width = 960.0
    val height = 500.0

    val data = listOf(0, 1, 2, 3, 4)
    val matrix = listOf(
        listOf(0, 0, 2, 0, 2),
        listOf(0, 0, 2, 2, 0),
        listOf(0, 0, 0, 2, 2),
        listOf(0, 0, 0, 0, 4),
        listOf(0, 0, 0, 0, 0)
    )

    val dataX0 = listOf(.0, .0, 315.0, 630.0, 945.0)
    val dataX1 = listOf(15.0, 15.0, 330.0, 645.0, 960.0)
    val dataY0 = listOf(.0, 255.0, 128.03282153303928, 176.653761069132, .0)
    val dataY1 = listOf(245.0, 500.0, 373.03282153303928, 421.653761069132, 490.0)

    fun fromTo(from:Int, to:Int):Double = matrix[from][to].toDouble()


    @Test
    fun testSankey() {
        val sankeyLayout = SankeyLayout<Int>()
        sankeyLayout.nodeWidth = 15.0
        sankeyLayout.nodePadding = 10.0
        sankeyLayout.width = width
        sankeyLayout.height = height

        val sankey = sankeyLayout.sankey(data, ::fromTo)
        data.forEachIndexed { index, _ ->
            sankey.nodes[index].x0 shouldBeClose dataX0[index]
            sankey.nodes[index].x1 shouldBeClose dataX1[index]
            sankey.nodes[index].y0 shouldBeClose dataY0[index]
            sankey.nodes[index].y1 shouldBeClose dataY1[index]
        }
    }
}