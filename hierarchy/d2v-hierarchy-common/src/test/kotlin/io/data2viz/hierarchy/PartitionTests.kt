package io.data2viz.hierarchy

import io.data2viz.test.TestBase
import kotlin.test.Test

class PartitionTests : TestBase() {

    // DO NOT CHANGE VALUES
    val width = 500.0
    val height = 400.0

    data class Hierarchical(
        val value: Int,
        val x0: Double = .0,
        val y0: Double = .0,
        val x1: Double = .0,
        val y1: Double = .0,
        val subElements: List<Hierarchical>? = null
    )

    val testTreeLight = Hierarchical(
        1, .0, .0, .0, .0, listOf(
            Hierarchical(11, .0, .0, .0, .0),
            Hierarchical(12, .0, .0, .0, .0)
        )
    )

    val testTreeMid =
        Hierarchical(
            1, 0.0, 0.0, 500.0, 100.0, subElements = listOf(
                Hierarchical(
                    11, 0.0, 100.0, 313.0, 200.0, subElements = listOf(
                        Hierarchical(111, 125.0, 200.0, 188.0, 300.0),
                        Hierarchical(112, 188.0, 200.0, 250.0, 300.0),
                        Hierarchical(113, 250.0, 200.0, 313.0, 300.0),
                        Hierarchical(
                            114, 0.0, 200.0, 125.0, 300.0, subElements = listOf(
                                Hierarchical(1141, 0.0, 300.0, 63.0, 400.0),
                                Hierarchical(1142, 63.0, 300.0, 125.0, 400.0)
                            )
                        )
                    )
                ),
                Hierarchical(
                    12, 313.0, 100.0, 500.0, 200.0, subElements = listOf(
                        Hierarchical(
                            121, 313.0, 200.0, 438.0, 300.0, subElements = listOf(
                                Hierarchical(1211, 313.0, 300.0, 375.0, 400.0),
                                Hierarchical(1212, 375.0, 300.0, 438.0, 400.0)
                            )
                        ),
                        Hierarchical(122, 438.0, 200.0, 500.0, 300.0)
                    )
                )
            )
        )

    @Test
    fun buildPartition() {
        val hierarchy = hierarchy(testTreeMid, { it.subElements })
        hierarchy.sum({ it.value.toDouble() })
        hierarchy.sum { if (it.subElements == null) 1.0 else .0 }

        val partitionLayout = PartitionLayout()
        partitionLayout.padding = .0
        partitionLayout.round = true
        partitionLayout.size(width, height)

        val partition = partitionLayout.partition(hierarchy)
        partition.x0 shouldBe .0
        partition.y0 shouldBe .0
        partition.children.size shouldBe 2
        partition.depth shouldBe 0
        partition.height shouldBe 3
        partition.each { partitionNode ->
            partitionNode.x0 shouldBe partitionNode.data.x0
            partitionNode.x1 shouldBe partitionNode.data.x1
            partitionNode.y0 shouldBe partitionNode.data.y0
            partitionNode.y1 shouldBe partitionNode.data.y1
        }
    }
}