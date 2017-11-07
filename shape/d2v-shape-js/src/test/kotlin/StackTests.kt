import io.data2viz.shape.stack
import kotlin.js.Date


private data class dataClass(
        val date: Date,
        val apples: Int,
        val bananas: Int,
        val cherries: Int,
        val dates: Int
)

@JsName("stackTests")
fun stackTests(arcValues: ArcValues) {

    // see https://github.com/d3/d3-shape#stacks
    val data = arrayOf(
            dataClass(Date(), 3840, 1920, 960, 400),
            dataClass(Date(), 1600, 1440, 960, 400),
            dataClass(Date(), 640, 960, 640, 400),
            dataClass(Date(), 320, 480, 640, 400)
    )

    val stackGenerator = stack<dataClass> {
        values = { arrayOf(it.apples.toDouble(), it.bananas.toDouble(), it.cherries.toDouble(), it.dates.toDouble())}
    }

    val ret = stackGenerator.stack(data)
    println(ret)
}