package io.data2viz.test

import io.data2viz.test.matchers.Matchers
import io.data2viz.test.matchers.be
import kotlin.test.Test

class IntMatchersTests : Matchers {
    @Test
    fun one_should_be_lt_two()    { 1 should be lt 2 }
    @Test
    fun one_should_be_lte_one()   { 1 should be lte 1 }
    @Test
    fun two_should_be_gt_one()    { 2 should be gt 1 }
    @Test
    fun two_should_be_gte_two()   { 2 should be gte 2 }
}