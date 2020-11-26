package io.data2viz.test

import io.data2viz.test.matchers.Matchers
import io.data2viz.test.matchers.be
import kotlin.test.Test

class LongMatchersTests : Matchers {
    @Test
    fun one_should_be_lt_two()    { 1L should be lt 2L }
    @Test
    fun one_should_be_lte_one()   { 1L should be lte 1L }
    @Test
    fun two_should_be_gt_one()    { 2L should be gt 1L }
    @Test
    fun two_should_be_gte_two()   { 2L should be gte 2L }


}