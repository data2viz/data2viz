import io.data2viz.format.Locale
import io.data2viz.format.format
import io.data2viz.test.TestBase
import kotlin.js.Math
import kotlin.test.Test


val format = Locale().format(".6f")
fun Double.toFixed() =
        if (Math.abs(this - Math.round(this)) < 1e-6)
            Math.round(this).toString()
        else format(this)

val regex = Regex("[-+]?(?:\\d+\\.\\d+|\\d+\\.|\\.\\d+|\\d+)(?:[eE][-]?\\d+)?")

/**
 * Look for all doubles in the string to replace it by a rounded version
 */
private fun String.round() = replace(regex = regex, transform = { it.value.toDouble().toFixed() })



class LineTest : TestBase() {

    @Test
    fun nrst(){
        true shouldBe true
    }
}
