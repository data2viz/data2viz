package test

import kotlin.browser.document
import kotlin.browser.window


fun htmlExecution(vararg testBase: TestBase){
    val body = window.document.querySelector("body")!!
    testBase.forEach {
        body.append(document.createElement("h2").apply { textContent = it::class.simpleName })
        it.tests
                .map { it.execute() }
                .forEach { body.appendChild(it.toHtml()) }
    }
}

fun TestResult.toHtml() =
        document.createElement("div").apply {
            className = "testResult ${this@toHtml.result()}"

            appendChild(document.createElement("span").apply {
                className = "testName"
                textContent = name
            })

            appendChild(document.createElement("span").apply {
                className = "resultDescription"
                textContent = when (this@toHtml) {
                    is TestResult.KO -> this@toHtml.message
                    else -> " OK"
                }
            })
        }
