package io.data2viz.hierarchy

import io.data2viz.test.TestBase
import kotlin.test.Test

class PackTests : TestBase() {

    // TODO : test radius
    // TODO : test padding

    // DO NOT CHANGE VALUES
    val width = 400.0
    val height = 400.0

    data class Hierarchical(
        val value: Int,
        val x: Double = .0,
        val y: Double = .0,
        val r: Double = .0,
        val subElements: List<Hierarchical>? = null
    )

    val testTreeLight = Hierarchical(
        1, .0, .0, .0, listOf(
            Hierarchical(11, .0, .0, .0),
            Hierarchical(12, .0, .0, .0)
        )
    )

    val testTreeMid =
        Hierarchical(
            1, 200.0, 200.0, 200.0, subElements = listOf(
                Hierarchical(
                    11, 111.50754376139204,200.0, 111.50754376139204, subElements = listOf(
                        Hierarchical(111, 82.01005834852272,230.91891491791125, 29.49748541286932),
                        Hierarchical(112, 141.00502917426135,230.91891491791125, 29.49748541286932),
                        Hierarchical(113, 111.50754376139204,282.01005834852276, 29.49748541286932),
                        Hierarchical(
                            114, 111.50754376139204,147.4874270643466, 58.99497082573864, subElements = listOf(
                                Hierarchical(1141, 82.01005834852272,147.4874270643466, 29.49748541286932),
                                Hierarchical(1142, 141.00502917426135,147.4874270643466, 29.49748541286932)
                            )
                        )
                    )
                ),
                Hierarchical(
                    12, 311.50754376139207,200.0, 88.49245623860796, subElements = listOf(
                        Hierarchical(
                            121, 270.9677419354839,200.0, 64.51612903225806, subElements = listOf(
                                Hierarchical(1211, 238.70967741935488, 282.01005834852276, 29.49748541286932),
                                Hierarchical(1212, 311.50754376139207, 200.0, 29.49748541286932)
                            )
                        ),
                        Hierarchical(122, 370.5025145871307,200.0, 29.49748541286932)
                    )
                )
            )
        )

    @Test
    fun packLightTest() {
        val hierarchy = hierarchy(Hierarchical(0, .0, .0, .0), { it.subElements })
        hierarchy.descendants().size shouldBe 1
        hierarchy.leaves().size shouldBe 1

        hierarchy.sum { if (it.subElements == null) 1.0 else .0 }

        val packLayout = PackLayout<Hierarchical>()
        packLayout.padding = { .0 }
        packLayout.size(width, height)

        packLayout.pack(hierarchy)
    }

    @Test
    fun pack3NodesTest() {
        val hierarchy = hierarchy(testTreeLight, { it.subElements })
        hierarchy.descendants().size shouldBe 2
        hierarchy.leaves().size shouldBe 2

        hierarchy.sum { if (it.subElements == null) 1.0 else .0 }

        val packLayout = PackLayout<Hierarchical>()
        packLayout.padding = { .0 }
        packLayout.size(width, height)

        val pack = packLayout.pack(hierarchy)
    }

    @Test
    fun packMidTest() {
        val hierarchy = hierarchy(testTreeMid, { it.subElements })
        hierarchy.sum { if (it.subElements == null) 1.0 else .0 }

        val packLayout = PackLayout<Hierarchical>()
        packLayout.padding = { .0 }
        packLayout.size(width, height)

        val pack = packLayout.pack(hierarchy)
        pack.each { packNode ->
            packNode.x shouldBeClose packNode.data.x
            packNode.y shouldBeClose packNode.data.y
            packNode.r shouldBeClose packNode.data.r
        }
    }
}