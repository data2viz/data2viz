import io.data2viz.color.ColorTests
import io.data2viz.color.EncodedColorsTests
import io.data2viz.core.TicksTests
import io.data2viz.dsv.DsvTests
import io.data2viz.experiments.fantasymap.buildFantasyMap
import io.data2viz.interpolate.*
import io.data2viz.test.*


fun allTests() {
    htmlExecution(
            DsvTests(),
            NumberTests(),
            ColorTests(),
            TicksTests(),
            EncodedColorsTests(),
            EaseTests(),
            ExceptionMatchers(),
            StringMatchers(),
            IntMatchers(),
            LongMatchers(),
            DoubleMatchers(),
            TestCollectionMatchers(),
            ScaleTests(),
            RGBTests(),
            HSLTests(),
            InstanciationTests()
    )
}

fun bindingPerfs() = io.data2viz.experiments.bindingPerfs()
fun chart()    = io.data2viz.experiments.chart.chart()
fun fantasyMap()    = buildFantasyMap()
//fun fantasyVoroinoMap()    = buildVoronoiFantasyMap()
fun voronoi()    = io.data2viz.experiments.voronoi.voronoi()
fun voronoiSphere()    = io.data2viz.experiments.voronoi.voronoiSphere()
fun svgPerfs() = io.data2viz.experiments.perfs.svgPerfs()
fun jsPerfs() = io.data2viz.experiments.perfs.jsPerfs()
fun walking()    = io.data2viz.experiments.walking.walkingDead()
fun mouse()    = io.data2viz.experiments.mouse.mouse()
fun animate()  = io.data2viz.experiments.animate.animate()
