package io.data2viz.test

import org.w3c.dom.Element
import kotlin.browser.document
import kotlin.browser.window

fun htmlExecution(vararg testBases: TestBase) {
    val body = window.document.querySelector("body")!!
    testBases.forEach { testBase ->

        val packageTitle = document.createElement("h2")

        body.append(packageTitle.apply {
            textContent = testBase::class.simpleName
            setAttribute("onclick", "hideShow('${testBase::class.simpleName}')")
        }
        )

        val packageDiv = document.createElement("div")
        packageDiv.apply {
            className = "${testBase::class.simpleName}"
        }
        body.appendChild(packageDiv)

        var allTestsOK = true

        testBase.tests.forEach { test ->

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
            packageDiv.appendChild(divTest)

            val executionContext = DOMExecutionContext(packageDiv)
            val result = test.execute(executionContext)

            allTestsOK = allTestsOK && (result is TestResult.OK)

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

        packageTitle.className = if (allTestsOK) "ok" else "ko"
        if (allTestsOK) packageDiv.setAttribute("style", "display: none;")
    }
}

class DOMExecutionContext(val element: Element): ExecutionContext
