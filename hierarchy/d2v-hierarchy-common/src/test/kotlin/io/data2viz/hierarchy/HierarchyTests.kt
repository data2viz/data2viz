package io.data2viz.hierarchy

import io.data2viz.test.TestBase
import kotlin.test.Test

class HierarchyTests : TestBase() {

    data class Hierarchical(
        val value: Int,
        val subElements: List<Hierarchical>? = null
    )



    val testValues =
        Hierarchical(1, listOf(
                Hierarchical(11, listOf(
                        Hierarchical(111),
                        Hierarchical(112),
                        Hierarchical(113),
                        Hierarchical(114, listOf(
                                Hierarchical(1141),
                                Hierarchical(1142)
                            )
                        )
                    )
                ),
                Hierarchical(12, listOf(
                        Hierarchical(121, listOf(
                                Hierarchical(1211),
                                Hierarchical(1212)
                            )
                        ),
                        Hierarchical(122)
                    )
                )
            )
        )

//    @Test
//    fun buildHierarchy() {
//        val hierarchy = hierarchy(Hierarchical(0), { it.subElements }, { it.value })
//
//        hierarchy.descendants().size shouldBe 1
//        hierarchy.leaves().size shouldBe 1
//    }
//
//    @Test
//    fun buildHierarchyFull() {
//        val hierarchy = hierarchy(testValues, { it.subElements }, { it.value })
//
//        hierarchy.descendants().size shouldBe 3
//        hierarchy.leaves().size shouldBe 8
//    }

    @Test
    fun buildTreemap() {
        val hierarchy = hierarchy(testValues, { it.subElements }, { it.value.toDouble() })
        hierarchy.sum({ it.value.toDouble() })

        hierarchy.sum { 1.0 }
        val treemap = treemap(hierarchy)

        val tree = tree(hierarchy)
        val cluster = cluster(hierarchy)
    }
}