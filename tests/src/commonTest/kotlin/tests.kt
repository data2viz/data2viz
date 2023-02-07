package io.data2viz.test

import io.data2viz.test.matchers.Matchers
import io.data2viz.test.matchers.be
import io.data2viz.test.matchers.have
import kotlin.js.JsName
import kotlin.test.Test


class Rounding: TestBase() {
    
    @Test
    @JsName("specialChars")
    fun `Un nom avec des caractères non autorisés`(){
        true shouldBe true
    }
    
    @Test
    fun normalizeDoublesInString(){
        "0".round() shouldBe "0"
        "0.0".round() shouldBe "0"
        "0.01".round() shouldBe "0.010000"
        "0.000001".round() shouldBe "0.000001"
        "0.0000001".round() shouldBe "0"
    }
}

class DoubleMatchers : Matchers {
    @Test fun double_should_be_exactly_equals()     { 1.0 shouldBe exactly(1.0) }
    @Test fun double_should_be_plusOrMinus()        { 1.1 shouldBe (1.11 plusOrMinus 0.011) }
    @Test fun listOfNumber()                        { listOf(1.0, 2.0) shouldBe listOf(1.000_000_1, 2.000_000_1) }
}

class StringMatchers : Matchers {
    @Test fun bépoèd_should_start_with_bépo ()  { "bépoè^dl" should startWith("bépo") }
    @Test fun bépoèdlj_should_end_with_dlj ()   { "bépoè^dlj" should endWith("dlj") }
    @Test fun auie_should_have_substring_ui ()  { "auie" should have substring "ui" }
    @Test fun bepo_should_match_regexp ()       { "bépo" should match("b.+") }
    @Test fun auie_should_haveLength_of_4()     { "auie" should haveLength(4) }
}

class IntMatchers : Matchers {
    @Test fun one_should_be_lt_two()    { 1 should be lt 2 }
    @Test fun one_should_be_lte_one()   { 1 should be lte 1 }
    @Test fun two_should_be_gt_one()    { 2 should be gt 1 }
    @Test fun two_should_be_gte_two()   { 2 should be gte 2 }
}

class LongMatchers : Matchers {
    @Test fun one_should_be_lt_two()    { 1L should be lt 2L }
    @Test fun one_should_be_lte_one()   { 1L should be lte 1L }
    @Test fun two_should_be_gt_one()    { 2L should be gt 1L }
    @Test fun two_should_be_gte_two()   { 2L should be gte 2L }


}

class TestCollectionMatchers : Matchers {

    @Test
    fun empty_collection()    {
        emptyList<Int>() should beEmpty()
            shouldThrow<AssertionError> {
                listOf(1) should beEmpty()
            }
        }

    @Test fun collection_size()                 { listOf(1, 2) should haveSize(2) }
    @Test fun collection_contain()              { listOf(1, 2) should contain(2) }
    @Test fun collection_contains_elements ()   { listOf(1, 2, 3, 4, 5) should containInAnyOrder(4, 3, 2) }
}
