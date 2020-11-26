package io.data2viz.test

import io.data2viz.test.matchers.Matchers
import io.data2viz.test.matchers.have
import kotlin.test.Test

class StringMatchersTests : Matchers {
    @Test
    fun bépoèd_should_start_with_bépo ()  { "bépoè^dl" should startWith("bépo") }
    @Test
    fun bépoèdlj_should_end_with_dlj ()   { "bépoè^dlj" should endWith("dlj") }
    @Test
    fun auie_should_have_substring_ui ()  { "auie" should have substring "ui" }
    @Test
    fun bepo_should_match_regexp ()       { "bépo" should match("b.+") }
    @Test
    fun auie_should_haveLength_of_4()     { "auie" should haveLength(4) }
}