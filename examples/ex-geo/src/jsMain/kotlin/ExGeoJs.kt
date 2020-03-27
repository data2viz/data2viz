import geo.path
import io.data2viz.viz.bindRendererOnNewCanvas
import io.data2viz.viz.viz

fun main() {

    val antartic = path()
    viz {
        width = 1000.0
        height = 1000.0
        path {  }

    }.bindRendererOnNewCanvas()
    println("start ex-geo")
}