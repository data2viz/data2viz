package io.data2viz.test

import io.data2viz.test.matchers.*

class ExceptionMatchers : StringSpec(){
    init {
        "block should throw" {
            shouldThrow<NullPointerException> { throw NullPointerException() }
        }
    }
}

class DoubleMatchers: StringSpec(){
    init {
        "double should be exactly equals" {
            1.0 shouldBe exactly(1.0)
        }

        "double should be plusOrMinus" {
            1.1 shouldBe (1.11 plusOrMinus 0.011)
        }

        "listOfNumber" {
            listOf(1.0, 2.0) shouldBe listOf(1.000_000_1, 2.000_000_1)
        }
    }
}

class StringMatchers: StringSpec(){
    init {
        "bépoèd should start with bépo" {
            "bépoè^dl" should startWith("bépo")
        }

        "bépoè^dlj should end with dlj" {
            "bépoè^dlj" should endWith("dlj")
        }

        "auie should  have substring ui" {
            "auie" should  have substring "ui"
        }

        "bépo should match(\"b.+\")"{
            "bépo" should match("b.+")
        }

        "auie should haveLength(4)" {
            "auie" should haveLength(4)
        }
    }
}

class IntMatchers: StringSpec(){
    init {
        "1 should be lt 2" {
            1 should be lt 2
        }
        "1 should be lte 1" {
            1 should be lte 1
        }
        "2 should be gt 1" {
            2 should be gt 1
        }
        "2 should be gte 2" {
            2 should be gte 2
        }
    }
}
class LongMatchers: StringSpec(){
    init {
        "1 should be lt 2" {
            1L should be lt 2L
        }
        "1 should be lte 1" {
            1L should be lte 1L
        }
        "2 should be gt 1" {
            2L should be gt 1L
        }
        "2 should be gte 2" {
            2L should be gte 2L
        }
    }
}

class TestCollectionMatchers : StringSpec() {
    init {
        "empty collection" {
            emptyList<Int>() should beEmpty()
            shouldThrow<AssertionError> {
                listOf(1) should beEmpty()
            }
        }

        "collection size" {
            listOf(1, 2) should haveSize(2)
        }

        "collection contain"{
            listOf(1,2) should contain(2)
        }

        "collection contains elements" {
            listOf(1,2,3,4,5) should containInAnyOrder(4,3,2)
        }
    }
}
