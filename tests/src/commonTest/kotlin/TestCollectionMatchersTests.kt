package io.data2viz.test

import io.data2viz.test.matchers.Matchers
import kotlin.test.Test

class TestCollectionMatchersTests : Matchers {

    @Test
    fun empty_collection()    {
        emptyList<Int>() should beEmpty()
        shouldThrow<AssertionError> {
            listOf(1) should beEmpty()
        }
        }

    @Test
    fun collection_size()                 { listOf(1, 2) should haveSize(2) }
    @Test
    fun collection_contain()              { listOf(1, 2) should contain(2) }
    @Test
    fun collection_contains_elements ()   { listOf(1, 2, 3, 4, 5) should containInAnyOrder(4, 3, 2) }
}