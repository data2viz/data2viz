import io.data2viz.selection.SelectionTests
import io.data2viz.selection.append
import org.w3c.dom.asList
import test.DomUtils
import test.htmlExecution
import kotlin.browser.document
import kotlin.js.Date


fun bindingPerfs() {

    data class TimeAndResult<T>(val time:Double, val result: T)

    fun <T> time(block:() -> T):TimeAndResult<T> {
        val time = Date().getTime()
        val ret = block()
        return TimeAndResult(Date().getTime() - time, ret)
    }

    val count = 2500
    val creationTime = time {
        val svg = DomUtils.body.querySelector("svg")!!
        (1..count).forEach {
            svg.append("g") {
                asDynamic().__data__ = it
            }
        }

    }
    console.log("$count éléments créés en ${creationTime.time} ms")

    val selectionTime = time {
        DomUtils.body.querySelectorAll("g").asList().filter {
            (it.asDynamic().__data__ as Int) % 2 == 0
        }.size
    }

    console.log("Filtrage de ${selectionTime.result} en ${selectionTime.time} ms")

}
