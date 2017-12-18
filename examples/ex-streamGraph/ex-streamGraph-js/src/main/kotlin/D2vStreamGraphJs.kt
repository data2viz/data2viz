import io.data2viz.examples.streamGraph.height
import io.data2viz.examples.streamGraph.margins
import io.data2viz.examples.streamGraph.streamGraph
import io.data2viz.examples.streamGraph.width
import io.data2viz.viz.selectOrCreateSvg
import io.data2viz.viz.viz


@Suppress("unused")

fun main(args: Array<String>) {

    val root = selectOrCreateSvg().apply {
        setAttribute("width", "${width + margins.hMargins}")
        setAttribute("height", "${height + margins.vMargins}")
    }

    root.viz {
        streamGraph()
    }

}
