package io.data2viz.test

import org.w3c.dom.Element
import kotlin.browser.document
import kotlin.browser.window

fun htmlExecution(vararg testBase: TestBase) {
    val body = window.document.querySelector("body")!!
    testBase.forEach {
        val div = document.createElement("div")
        div.append(document.createElement("h2").apply { textContent = it::class.simpleName })
        it.tests.forEach { test ->

            val resultDescription = document.createElement("span").apply {
                className = "resultDescription"
            }

            val divTest = document.createElement("div").apply {


                appendChild(resultDescription)
                appendChild(document.createElement("span").apply {
                    className = "testName"
                    textContent = test.name
                })

            }
            body.appendChild(divTest)
            val executionContext = HTMLExecutionContext(div)
            val result = test.execute(executionContext)

            val okOrKo: String = when (result) {
                is TestResult.KO -> "KO"
                else -> " OK"
            }

            divTest.className = "testResult $okOrKo"

            resultDescription.textContent = okOrKo


            if (result is TestResult.KO) {
                divTest.appendChild(
                        document.createElement("div").apply {
                            className = "error"
                            textContent = result.message
                        }
                )
            }
        }

    }
}

class HTMLExecutionContext(val element: Element): ExecutionContext
