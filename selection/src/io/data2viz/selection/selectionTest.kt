package io.data2viz.selection

import org.w3c.dom.Element
import org.w3c.dom.HTMLElement
import test.DomUtils
import test.StringSpec
import kotlin.browser.document

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

            select.groups[0]!!.elements.size shouldBe 1
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
            selectAll.groups[0]!!.elements.size shouldBe 3
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

        "select all nested" {
            DomUtils.body.append("table"){
                (0..3).forEach { i ->
                    append("tr") {
                        (1..4).forEach { j ->
                            append("td"){
                                append("span"){
                                    textContent  = "${i*4 + j}"
                                }
                            }
                        }
                    }
                }
            }

            val tds = selectAll("tr").selectAll("td"){
                style("background-color") { if (it ==0) "lightblue" else ""}
            }
            console.log(tds)
        }

        "selection.data(values) binds the specified values to the selected elements by index" {
            val div = DomUtils.body.append("div.bind-data"){
                listOf("one", "two", "three").forEach { append("div"){id = it} }
            }

            val one  = div.querySelector("#one") as HTMLElement
            val two  = div.querySelector("#two")
            val three  = div.querySelector("#three")

//            selectAllAndBind("div.bind-data div", listOf("foo", "bar", "baz"))
//
//            console.log(one)
//            one.asDynamic().__data__ as String shouldBe "one"
        }
    }
}
