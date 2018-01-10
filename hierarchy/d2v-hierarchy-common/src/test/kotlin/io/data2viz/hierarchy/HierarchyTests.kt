package io.data2viz.hierarchy

import io.data2viz.test.TestBase
import kotlin.test.Test

class HierarchyTests : TestBase() {

    data class Hierarchical(
        val value: Double,
        val subElements: List<Hierarchical>? = null
    )

    val testValues =
        Hierarchical(1.0, listOf(
                Hierarchical(11.0, listOf(
                        Hierarchical(111.0),
                        Hierarchical(112.0),
                        Hierarchical(113.0),
                        Hierarchical(114.0, listOf(
                                Hierarchical(1141.0),
                                Hierarchical(1142.0)
                            )
                        )
                    )
                ),
                Hierarchical(12.0, listOf(
                        Hierarchical(121.0, listOf(
                                Hierarchical(1211.0),
                                Hierarchical(1212.0)
                            )
                        ),
                        Hierarchical(122.0)
                    )
                )
            )
        )

    @Test
    fun buildHierarchy() {
        val hierarchy = hierarchy(Hierarchical(0.0), { it.subElements }, { it.value })

        hierarchy.descendants().size shouldBe 1
        hierarchy.leaves().size shouldBe 1
    }

    @Test
    fun buildHierarchyFull() {
        val hierarchy = hierarchy(testValues, { it.subElements }, { it.value })

        hierarchy.descendants().size shouldBe 3
        hierarchy.leaves().size shouldBe 8
    }
}