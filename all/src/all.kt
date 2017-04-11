import io.data2viz.selection.SelectionTests
import test.htmlExecution


fun executeAll() {
    htmlExecution(
            SelectionTests(),
            TicksTests(),
            ViridisTests(),
            EaseTests(),
            ExceptionMatchers(),
            StringMatchers(),
            IntMatchers(),
            LongMatchers(),
            DoubleMatchers(),
            TestCollectionMatchers()
    )
}

fun svgPerfs()  = io.data2viz.samples.perfs.svgPerfs()
fun chart()  = io.data2viz.samples.chart.chart()
