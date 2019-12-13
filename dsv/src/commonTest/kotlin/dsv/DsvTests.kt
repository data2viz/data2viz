/*
 * Copyright (c) 2018-2019. data2viz sàrl.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 *
 */

package io.data2viz.dsv

import io.data2viz.test.TestBase
import kotlin.test.Test

class DsvTests : TestBase() {

    val csv = Dsv()

    @Test
    fun one_cell() {
        csv.parseRows("a") shouldBe listOf(listOf("a"))
        csv.parseRows("abc") shouldBe listOf(listOf("abc"))
        csv.parseRows("a\n") shouldBe listOf(listOf("a"))
    }

    @Test
    fun two_cells() {
        csv.parseRows("a,b") shouldBe listOf(listOf("a", "b"))
        csv.parseRows("a,b\n") shouldBe listOf(listOf("a", "b"))
        csv.parseRows("a,b\r\n") shouldBe listOf(listOf("a", "b"))
    }

    @Test
    fun two_rows() {
        csv.parseRows("a\nb") shouldBe listOf(listOf("a"), listOf("b"))
        csv.parseRows("a\r\nb") shouldBe listOf(listOf("a"), listOf("b"))
        csv.parseRows("a,b\n1,2") shouldBe listOf(listOf("a", "b"), listOf("1", "2"))
    }

    @Test
    fun parse_rows_with_quotes_values() {
        csv.parseRows("\"1\",2,3") shouldBe listOf(listOf("1", "2", "3"))
        csv.parseRows("\"hello\"") shouldBe listOf(listOf("hello"))
        csv.parseRows("\"\"\"hello\"\"\"") shouldBe listOf(listOf("\"hello\""))
    }

    @Test
    fun parse_rows_with_quoted_values_with_new_lines() {
        csv.parseRows("\"new\nline\"") shouldBe listOf(listOf("new\nline"))
        csv.parseRows("\"new\rline\"") shouldBe listOf(listOf("new\rline"))
        csv.parseRows("\"new\r\nline\"") shouldBe listOf(listOf("new\r\nline"))
    }

    @Test
    fun parse_rows_with_comma_in_quotes() {
        csv.parseRows("John,Doe,120 any st.,\"Anytown, WW\",08123") shouldBe
                listOf(listOf("John","Doe","120 any st.","Anytown, WW","08123"))
    }

    @Test
    fun parse_rows_with_UTF8() {
        csv.parseRows("a,b,c\n1,2,3\n4,5,ʤ") shouldBe
                listOf(listOf("a", "b", "c"),
                    listOf("1", "2", "3"),
                    listOf("4", "5", "ʤ"))
    }

    @Test
    fun parse_rows_with_empty_cells() {
        csv.parseRows("a,b,c\n1,\"\",\"\"\n2,3,4") shouldBe
                listOf(listOf("a", "b", "c"),
                    listOf("1", "", ""),
                    listOf("2", "3", "4"))
    }

    @Test
    fun parse_rows_with_missing_cells() {
        csv.parseRows("a,b,c\n1\n2,3,4") shouldBe
                listOf(listOf("a", "b", "c"),
                    listOf("1"),
                    listOf("2", "3", "4"))
    }

    @Test
    fun parse_rows_containing_json() {
        csv.parseRows("1,\"{\"\"type\"\": \"\"Point\"\", \"\"coordinates\"\": [102.0, 0.5]}\"") shouldBe
                listOf(listOf("1", "{\"type\": \"Point\", \"coordinates\": [102.0, 0.5]}"))
    }

}
