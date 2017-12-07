package io.data2viz.examples.progression


data class Progression(val version: String, val module: String, val estimatedLOC: Int, val commonLOC: Int, val jsLOC: Int, val JVMLOC: Int, val testsLOC: Int)


val modules = listOf(
        Progression("0.2",  "core", 150, 76, 3, 0, -150),
        Progression("0.2",  "voronoi", 1000, 930, 0, 0, -550),
        Progression("0.2",  "color", 400, 378, 2, 6, -307),
        Progression("0.2",  "tests", 296, 269, 19, 8, -0),
        Progression("0.2",  " ", 0, 0, 0, 0, -0),
        Progression("0.3",  "path", 156, 139, 14, 3, -450),
        Progression("0.3",  "shape", 1902, 1871, 0, 31, -1300),
        Progression("0.3",  "scale", 753, 715, 0, 0, -1653),
        Progression("0.3",  "format", 781, 732, 7, 42, -1025),
        Progression("0.3",  "viz", 400, 52, 149, 121, -85),
        Progression("0.3",  "interpolate", 462, 206, 13, 0, -300),
        Progression("0.3",  "random", 51, 46, 3, 2, -0),
        Progression("0.3",  "axis", 142, 0, 39, 0, -31),
        Progression("0.3",  "time-format", 574, 0, 0, 0, -0),
        Progression("0.3",  "time", 298, 0, 0, 0, -0),
        Progression("0.3",  "  ", 0, 0, 0, 0, -0),
        Progression("0.4",  "contour", 350, 230, 26, 0, -364),
        Progression("0.4",  "sankey", 265, 0, 0, 0, -0),
        Progression("0.4",  "chord", 179, 0, 0, 0, -0),
        Progression("0.4",  "   ", 0, 0, 0, 0, -0),
        Progression("0.5",  "hierarchy", 1100, 0, 0, 0, -0),
        Progression("0.5",  "viz-chart", 1200, 0, 0, 0, -0),
        Progression("0.5",  "scale-chromatic", 386, 0, 0, 0, -0)
        )


val Progression.remainingLOC:Int
        get() = estimatedLOC - commonLOC - jsLOC - JVMLOC

