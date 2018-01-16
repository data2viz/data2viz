package io.data2viz.hierarchy

import io.data2viz.test.TestBase
import kotlin.test.Test

class HierarchyTests : TestBase() {

    // DO NOT CHANGE VALUES
    val width = 500.0
    val height = 400.0

    data class Hierarchical(
        val value: Int,
        val x: Double = .0,
        val y: Double = .0,
        val subElements: List<Hierarchical>? = null
    )

    val testTreeLight = Hierarchical(
        1, .0, .0, listOf(
            Hierarchical(11, .0, .0),
            Hierarchical(12, .0, .0)
        )
    )

    val testTreeMid =
        Hierarchical(
            1, 263.02083333333, .0, subElements = listOf(
                Hierarchical(
                    11, 119.79166666666667, 133.33333333333334, subElements = listOf(
                        Hierarchical(111, 41.666666666666664, 400.0),
                        Hierarchical(112, 83.33333333333333, 400.0),
                        Hierarchical(113, 125.0, 400.0),
                        Hierarchical(
                            114, 229.16666666666666, 266.6666666666667, subElements = listOf(
                                Hierarchical(1141, 208.33333333333334, 400.0),
                                Hierarchical(1142, 250.0, 400.0)
                            )
                        )
                    )
                ),
                Hierarchical(
                    12, 406.25, 133.33333333333334, subElements = listOf(
                        Hierarchical(
                            121, 354.1666666666667, 266.6666666666667, subElements = listOf(
                                Hierarchical(1211, 333.3333333333333, 400.0),
                                Hierarchical(1212, 375.0, 400.0)
                            )
                        ),
                        Hierarchical(122, 458.3333333333333, 400.0)
                    )
                )
            )
        )

    @Test
    fun buildHierarchy() {
        val hierarchy = hierarchy(Hierarchical(0), { it.subElements })

        hierarchy.descendants().size shouldBe 1
        hierarchy.leaves().size shouldBe 1
    }

    @Test
    fun buildHierarchyFull() {
        val hierarchy = hierarchy(testTreeMid, { it.subElements })

        hierarchy.descendants().size shouldBe 3
        hierarchy.leaves().size shouldBe 8
    }

    @Test
    fun buildCluster() {
        val hierarchy = hierarchy(testTreeMid, { it.subElements })
        val clusterLayout = ClusterLayout()
        clusterLayout.size(width, height)

        val cluster = clusterLayout.cluster(hierarchy)
        cluster.children.size shouldBe 2
        cluster.depth shouldBe 0
        cluster.height shouldBe 3
        cluster.each { clusterNode ->
            clusterNode.x shouldBeClose clusterNode.data.x
            clusterNode.y shouldBeClose clusterNode.data.y
        }
    }
}