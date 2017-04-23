import io.data2viz.interpolate.EaseTests
import test.htmlExecution


fun allTests() {
    htmlExecution(
            ColorTests(),
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

fun bindingPerfs() = io.data2viz.experiments.bindingPerfs()
fun svgPerfs() = io.data2viz.experiments.perfs.svgPerfs()
fun chart()    = io.data2viz.experiments.chart.chart()
fun animate()  = io.data2viz.experiments.animate.animate()
