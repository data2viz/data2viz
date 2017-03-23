package test

import kotlin.browser.document
import kotlin.browser.window

fun htmlExecution(vararg testBase: TestBase) {
    val body = window.document.querySelector("body")!!
    testBase.forEach {
        body.append(document.createElement("h2").apply { textContent = it::class.simpleName })
        it.tests.forEach {


            val resultDescription = document.createElement("span").apply {
                className = "resultDescription"
            }

            val divTest = document.createElement("div").apply {


                appendChild(resultDescription)
                appendChild(document.createElement("span").apply {
                    className = "testName"
                    textContent = it.name
                })

            }
            body.appendChild(divTest)

            val result = it.execute()

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
