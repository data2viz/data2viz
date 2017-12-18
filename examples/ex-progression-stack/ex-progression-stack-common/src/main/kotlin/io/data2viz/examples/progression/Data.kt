package io.data2viz.examples.progression


data class ModuleState(
        val version: String,
        val name: String,
        val estimatedLOC: Int,
        val commonLOC: Int,
        val jsLOC: Int,
        val JVMLOC: Int,
        val testLOC: Int)


// 18/12/2017
val modules = listOf (
        ModuleState("0.2",  "core", 150, 76, 3, 2, 149),
        ModuleState("0.2",  "voronoi", 930, 930, 0, 0, 550),
        ModuleState("0.2",  "color", 386, 378, 2, 6, 307),
        ModuleState("0.2",  "tests", 298, 271, 19, 8, 51),
        ModuleState("0.3",  "path", 156, 139, 14, 3, 450),
        ModuleState("0.3",  "shape", 1902, 1853, 0, 31, 1349),
        ModuleState("0.3",  "scale", 753, 689, 0, 0, 1637),
        ModuleState("0.3",  "format", 781, 425, 7, 37, 1144),
        ModuleState("0.3",  "viz", 550, 71, 234, 196, 113),
        ModuleState("0.3",  "interpolate", 462, 207, 13, 0, 300),
        ModuleState("0.3",  "random", 51, 46, 3, 2, 0),
        ModuleState("0.3",  "axis", 120, 82, 1, 1, 97),
        ModuleState("0.3",  "time", 600, 212, 160, 72, 302),
        ModuleState("0.3",  "timeformat", 700, 672, 0, 0, 432),
        ModuleState("0.4",  "contour", 350, 230, 26, 0, 364),
        ModuleState("0.4",  "sankey", 265, 0, 0, 0, 0),
        ModuleState("0.4",  "chord", 179, 0, 0, 0, 0),
        ModuleState("0.5",  "hierarchy", 1100, 0, 0, 0, 0),
        ModuleState("0.5",  "vizchart", 1200, 0, 0, 0, 0),
        ModuleState("0.5",  "scale\nchromatic", 386, 0, 0, 0, 0)
)


val ModuleState.remainingLOC:Int
        get() = estimatedLOC - commonLOC - jsLOC - JVMLOC

val ModuleState.testsLoc:Int
        get() = -testLOC

