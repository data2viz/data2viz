package io.data2viz.voronoi

import io.data2viz.test.matchers.Matchers
import io.data2viz.voronoi.RedBlackColor.*
import org.junit.Test


/**
 * To simplify the tests we use TreeNode with Int value.
 * The representation references the Upper, Previous, Next, Left, Right node using their value.
 */
class RedBlackTreeTest : Matchers {


    @Test
    fun `7 inserts`() {
        val tree = tree()
        val n1 = tree.insert(1.node())
        tree.root shouldBe n1
        n1.state() shouldBe nodeState(1)

        val n2 = tree.insert(2.node())
        n1.state() shouldBe nodeState(1, P = 2, L = 2)
        n2.state() shouldBe nodeState(2, U = 1, N = 1, color = RED)

        val n3 = tree.insert(3.node())
        n2.state() shouldBe nodeState(2, P = 3, N = 1, L = 3, R = 1)
        n3.state() shouldBe nodeState(3, U = 2, N = 2, color = RED)
        n1.state() shouldBe nodeState(1, U = 2, P = 2, color = RED)

        val n4 = tree.insert(4.node())
        n2.state() shouldBe nodeState(2, P = 3, N = 1, L = 3, R = 1)
        n3.state() shouldBe nodeState(3, U = 2, N = 2, P = 4, L = 4)
        n4.state() shouldBe nodeState(4, U = 3, N = 3, color = RED)
        n1.state() shouldBe nodeState(1, U = 2, P = 2)

        val n5 = tree.insert(5.node())
        n2.state() shouldBe nodeState(2, P = 3, N = 1, L = 4, R = 1)
        n4.state() shouldBe nodeState(4, U = 2, P = 5, L = 5, R = 3, N = 3)
        n3.state() shouldBe nodeState(3, U = 4, N = 2, P = 4, color = RED)
        n5.state() shouldBe nodeState(5, U = 4, N = 4, color = RED)
        n1.state() shouldBe nodeState(1, U = 2, P = 2)

        val n6 = tree.insert(6.node())
        n2.state() shouldBe nodeState(2, P = 3, N = 1, L = 4, R = 1)
        n4.state() shouldBe nodeState(4, U = 2, P = 5, L = 5, R = 3, N = 3, color = RED)
        n3.state() shouldBe nodeState(3, U = 4, N = 2, P = 4)
        n5.state() shouldBe nodeState(5, U = 4, N = 4, P = 6, L = 6)
        n6.state() shouldBe nodeState(6, U = 5, N = 5, color = RED)
        n1.state() shouldBe nodeState(1, U = 2, P = 2)

        val n7 = tree.insert(7.node())
        n2.state() shouldBe nodeState(2, P = 3, N = 1, L = 4, R = 1)
        n4.state() shouldBe nodeState(4, U = 2, P = 5, L = 6, R = 3, N = 3, color = RED)
        n6.state() shouldBe nodeState(6, U = 4, N = 5, P = 7, R = 5, L = 7)
        n3.state() shouldBe nodeState(3, U = 4, N = 2, P = 4)
        n5.state() shouldBe nodeState(5, U = 6, N = 4, P = 6, color = RED)
        n7.state() shouldBe nodeState(7, U = 6, N = 6, color = RED)
        n1.state() shouldBe nodeState(1, U = 2, P = 2)

        println("insert 8")
        val n8 = tree.insert(8.node())
        n4.state() shouldBe nodeState(4, P = 5, L = 6, R = 2, N = 3)
        n6.state() shouldBe nodeState(6, U = 4, N = 5, P = 7, L = 7, R = 5, color = RED)
        n7.state() shouldBe nodeState(7, U = 6, N = 6, P = 8, L = 8)
        n8.state() shouldBe nodeState(8, U = 7, N = 7, color = RED)
        n5.state() shouldBe nodeState(5, U = 6, N = 4, P = 6)
        n2.state() shouldBe nodeState(2, U = 4, P = 3, N = 1, L = 3, R = 1, color = RED)
        n3.state() shouldBe nodeState(3, U = 2, N = 2, P = 4)
        n1.state() shouldBe nodeState(1, U = 2, P = 2)

        println("insert 9")
        val n9 = tree.insert(9.node())
        n4.state() shouldBe nodeState(4, P = 5, L = 6, R = 2, N = 3)
        n6.state() shouldBe nodeState(6, U = 4, N = 5, P = 7, L = 8, R = 5, color = RED)
        n9.state() shouldBe nodeState(9, U = 8, N = 8, color = RED)
        n7.state() shouldBe nodeState(7, U = 8, N = 6, P = 8, color = RED)
        n8.state() shouldBe nodeState(8, U = 6, N = 7, R = 7, L = 9, P = 9)
        n5.state() shouldBe nodeState(5, U = 6, N = 4, P = 6)
        n2.state() shouldBe nodeState(2, U = 4, P = 3, N = 1, L = 3, R = 1, color = RED)
        n3.state() shouldBe nodeState(3, U = 2, N = 2, P = 4)
        n1.state() shouldBe nodeState(1, U = 2, P = 2)

        tree.remove(n4)
    }

    @Test
    fun `insert after second`() {
        val tree = tree()
        val n1 = tree.insert(1.node())
        val n2 = tree.insert(2.node())
        val n3 = tree.insert(3.node(), n2)

        n3.state() shouldBe nodeState(3, P = 2, L = 2, N = 1, R = 1)
        n2.state() shouldBe nodeState(2, U = 3, N = 3, color = RED)
        n1.state() shouldBe nodeState(1, U = 3, P = 3, color = RED)

        val n4 = tree.insert(4.node(), n1)
        n3.state() shouldBe nodeState(3, P = 2, L = 2, N = 1, R = 1)
        n2.state() shouldBe nodeState(2, U = 3, N = 3)
        n1.state() shouldBe nodeState(1, U = 3, P = 3, N = 4, R = 4)
        n4.state() shouldBe nodeState(4, U = 1, P = 1, color = RED)

        tree.remove(n4)
        n3.state() shouldBe nodeState(3, P = 2, L = 2, N = 1, R = 1)
        tree.remove(n3)
        n1.state() shouldBe nodeState(1, P = 2, L = 2)
        n2.state() shouldBe nodeState(2, U = 1, N = 1, color = RED)

    }

    @Test
    fun `remove child in the middle`() {
        val tree = tree()
        val n1 = tree.insert(1.node())
        val n2 = tree.insert(2.node())
        val n3 = tree.insert(3.node())
        val n4 = tree.insert(4.node())
        val n5 = tree.insert(5.node())

        tree.remove(n4)

    }

    @Test
    fun `remove root with child n2`() {
        val n1 = 1.node()
        val n2 = 2.node()
        val tree = tree().apply {
            insert(n1, null)
            insert(n2, null)
        }
        tree.remove(n2)
        tree.root shouldBe n1
        n1.state() shouldBe "B \n 1 \n   "
    }

    @Test
    fun `remove root with child n1`() {
        val n1 = 1.node()
        val n2 = 2.node()
        val tree = tree().apply {
            insert(n1, null)
            insert(n2, null)
        }
        tree.remove(n1)
        tree.root shouldBe n2
        n2.state() shouldBe "B \n 2 \n   "
    }


    @Test
    fun `when remove unique node then root is null`() {
        val tree = tree()
        val n1 = tree.insert(1.node())
        tree.remove(n1)
        tree.root shouldBe null
    }

    @Test
    fun `second insert with reference should be right`() {
        val n1 = 1.node()
        val n2 = 2.node()
        tree().apply {
            insert(n1, null)
            insert(n2, n1)
        }

        n1.state() shouldBe "B \n 12\n  2"
        n2.state() shouldBe "R1\n12 \n   "
    }

    @Test
    fun `third insert with reference on first should rotate left`() {
        val n1 = 1.node()
        val n2 = 2.node()
        val n3 = 3.node()
        tree().apply {
            insert(n1, null)
            insert(n2, n1)
            insert(n3, n1)
        }
        n3.state() shouldBe "B \n132\n1 2"
        n1.state() shouldBe "R3\n 13\n   "
        n2.state() shouldBe "R3\n32 \n   "
    }

    @Test
    fun `third insert with reference should rotate left`() {
        val n1 = 1.node()
        val n2 = 2.node()
        val n3 = 3.node()
        tree().apply {
            insert(n1, null)
            insert(n2, n1)
            insert(n3, n2)
        }
        n2.state() shouldBe "B \n123\n1 3"
        n1.state() shouldBe "R2\n 12\n   "
        n3.state() shouldBe "R2\n23 \n   "
    }

}

private fun tree() = RedBlackTree<Item<Int>>()


data class Item<T>(val value: T)

fun <T> RedBlackNode<Item<T>>.state() = """${if (C == RedBlackColor.RED) "R" else "B"}${U?.node?.value ?: " "}
${P?.node?.value ?: " "}${node.value}${N?.node?.value ?: " "}
${L?.node?.value ?: " "} ${R?.node?.value ?: " "}"""

fun nodeState(node: Int, color: RedBlackColor = RedBlackColor.BLACK, U: Int? = null, P: Int? = null, N: Int? = null, L: Int? = null, R: Int? = null) =
        "${if (color == RedBlackColor.RED) "R" else "B"}${U ?: " "}\n${P ?: " "}$node${N ?: " "}\n${L ?: " "} ${R ?: " "}"

fun Int.node() = RedBlackNode<Item<Int>>(Item(this))

