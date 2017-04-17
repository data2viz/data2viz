import io.data2viz.selection.SelectionTests
import test.htmlExecution


fun executeAll() {
    htmlExecution(
            ColorTests(),
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

fun svgPerfs() = io.data2viz.samples.perfs.svgPerfs()
fun chart()    = io.data2viz.samples.chart.chart()
fun animate()  = io.data2viz.samples.animate.animate()
