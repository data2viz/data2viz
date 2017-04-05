package io.data2viz.selection

import test.DomUtils
import test.StringSpec

class SelectionTests : StringSpec() {

    init {
        "select first element" {
            (1..3).forEach {
                DomUtils.body.append("div") {
                    className = "test-select-first"
                    textContent = it.toString()
                }
            }

            val select = select("div.test-select-first"){
                style("background-color", "lightblue")
            }

            select.groups[0].elements.size shouldBe 1
        }

        "select all element" {
            (1..3).forEach {
                DomUtils.body.append("div") {
                    className = "test-select-all"
                    textContent = it.toString()
                }
            }

            val selectAll = selectAll("div.test-select-all"){
                style("background-color", "lightblue")
            }
            selectAll.groups[0].elements.size shouldBe 3
        }

        "style with function" {
            (1..4).forEach {
                DomUtils.body.append("div") {
                    className = "test-style-with-function"
                    textContent = it.toString()
                }
            }
            selectAll("div.test-style-with-function"){
                style("background-color") { if (it % 2 ==0) "lightgrey" else "lightblue"}
                style("width") { "${10 + 10 * it}px"}
            }
        }
    }
}
