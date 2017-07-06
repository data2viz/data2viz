import io.data2viz.color.ColorTests
import io.data2viz.color.EncodedColorsTests
import io.data2viz.core.TicksTests
import io.data2viz.dsv.DsvTests
import io.data2viz.experiments.fantasymap.buildFinalFantasyMap
import io.data2viz.interpolate.*
import io.data2viz.random.RandomTests
import io.data2viz.tile.TileTests
import io.data2viz.test.*
import io.data2viz.voronoi.VoronoiTests


fun allTests() {
    htmlExecution(
//            VoronoiTests(),
//            TileTests(),
            RandomTests(),
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
            HSLTests()
    )
}

fun bindingPerfs() = io.data2viz.experiments.bindingPerfs()
fun fantasyMap()    = buildFinalFantasyMap()
//fun fantasyVoroinoMap()    = buildVoronoiFantasyMap()
fun jsPerfs() = io.data2viz.experiments.perfs.jsPerfs()
