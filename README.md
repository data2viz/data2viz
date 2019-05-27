<img src="http://data2viz.io/images/identity/d2v-logo-no-gradient-lg-no-marge@2.png" alt="Data2viz" width="604" style="max-width:100%;">


[![Download](https://api.bintray.com/packages/data2viz/data2viz/data2viz/images/download.svg) ](https://bintray.com/data2viz/data2viz/data2viz/_latestVersion)
[![Build Status](https://travis-ci.org/data2viz/data2viz.svg?branch=master)](https://travis-ci.org/data2viz/data2viz)
[![GitHub License](https://img.shields.io/badge/license-Apache%20License%202.0-blue.svg?style=flat)](http://www.apache.org/licenses/LICENSE-2.0) 
# Isomorphic dataviz

Data2viz is a multiplatform data visualization library based on Kotlin. The rendering produces the same result
on each platform: Android, JavaScript and JavaFX.


The algorithms and other portions are ported from [d3js](https://github.com/d3/d3) modules.


The code is separated in modules that can be independently used. Some are specifically designed for visualization 
([d2v-axis](https://github.com/data2viz/data2viz/tree/master/axis),
[d2v-path](https://github.com/data2viz/data2viz/tree/master/path),
[d2v-shape](https://github.com/data2viz/data2viz/tree/master/shape), 
[d2v-viz](https://github.com/data2viz/data2viz/tree/master/viz)) while others can be used outside of dataviz projects. 
For example, 
[d2v-format](https://github.com/data2viz/data2viz/tree/master/format) 
can be used as a multiplatform Kotlin format library outside of any dataviz project.

Data2viz allows you to develop data visualizations through a fully typed DSL. It simplifies the creation of complex
 visualizations via the IDE’s context-based suggestions.
 
You can try it on [data2viz playground](https://play.data2viz.io/tutorials/colors/).

## How can I use it?
Artifacts are published on [Maven Central](https://repo.maven.apache.org/maven2/io/data2viz/) and [Bintray](https://bintray.com/data2viz/data2viz/data2viz).

For example: to use data2viz in a Kotlin Javascript project, you need to add the following to the repositories and dependencies blocks:

```kotlin
repositories {
    mavenCentral()
}

dependencies {
    compile "io.data2viz:d2v-data2viz-js:$data2viz_version"
    compile "org.jetbrains.kotlin:kotlin-stdlib-js:$kotlin_version"
}
```

You can then create a visualization in your code and render it in the context of your application. For example:
```kotlin
fun main(args: Array<String>) {
    println("starting a first viz")
    viz {
        width = 400.0
        height = 400.0

        rect {
            width = 50.0
            height = 50.0
            x = 100.0
            y = 100.0
            style.fill = colors.red
        }

    }.bindRendererOn("viz")     // <- the canvas id of which the viz is rendered.

}
```

You can also [clone the examples repo](https://github.com/data2viz/data2viz-examples) or have a look
at the [example directory](examples) in this repo.


## Some samples

All examples are available in the [examples](examples) directory. Here is a small sample you what you can
do with data2viz. All examples are able to run in the browser and as JavaFX applications. You can open the JavaScript version
of each example using the links below.

You can also install the Android demo app [directly from the Play Store](https://play.google.com/store/apps/details?id=io.data2viz.data2canvas).


The easiest option is to build data2viz is gradle tasks (from terminal or IDEA gradle view)

* to run Android application you should run `:installDebug` task like `./gradlew :app:installDebug`
* to tun JFX application you should run :run task like `./gradlew :ex-geo:ex-geo-jfx:run`
* to build JS files you should run `:build` like `./gradlew :ex-geo:ex-geo-js:build` and open `index.html` from module directory


<a href="https://play.google.com/store/apps/details?id=io.data2viz.data2canvas" >
<img src="https://raw.githubusercontent.com/data2viz/data2viz/master/docs/img/android-world-animation.png" width="250">
</a>

#### [Geo projection](examples/ex-geo)

This code uses the geo module to show how to load a GeoJson file and render it using a
projection.

<a target="_blank" href="https://data2viz.github.io/data2viz/ex-geo-js/index.html" >
Open live JavaScript version
</a>

[See code](examples/ex-geo)

<a href="https://github.com/data2viz/data2viz/tree/master/examples/ex-geo" >
<img src="https://raw.githubusercontent.com/data2viz/data2viz/master/examples/images/geo.png" width="400">
</a>

#### [Forces](examples/ex-force)

This demo shows how to use different types of forces: radial, nbody, x, and y forces.

<a target="_blank" href="https://data2viz.github.io/data2viz/ex-force-js/index.html" >
Open live JavaScript version
</a>

[See code](examples/ex-force)

<a href="https://github.com/data2viz/data2viz/tree/master/examples/ex-force" >
<img src="https://raw.githubusercontent.com/data2viz/data2viz/master/examples/images/force.png" width="400">
</a>

#### [Chord Graph](examples/ex-chord)

This demonstrates a chord diagram, which shows relationships between entities using arcs.

<a target="_blank" href="https://data2viz.github.io/data2viz/ex-chord-js/index.html" >
Open live JavaScript version
</a>

[See code](examples/ex-chord)

<a href="https://github.com/data2viz/data2viz/tree/master/examples/ex-chord" >
<img src="https://raw.githubusercontent.com/data2viz/data2viz/master/examples/images/chord.png" width="400">
</a>


## Current status and roadmap

APIs are mostly stabilized now, but there may still be some breaking changes before v1.0.

### Version v0.7 (Current):

This major version brings support for Android. This addition of Android as a target
platform had a big impact on the design of data2viz. Before v0.7 basic visual elements 
(rectangles, circles, ...) were wrappers on specific platform elements. Now, these elements
are a memory version of the visualization. Elements are just rendered on each platform 
using canvas. 

This version includes a cleaner hierarchy with layers at its base.

### Version v0.6:

The current version contains the following modules:

  - axis: a module that display axis for scales.
  - chord: generator for chord charts.
  - colors: manage different color spaces (RGBA, HSLA, LAB), gradients, categories of colors, ...
  - core: some main elements like trigonometric functions, namespace constants.
  - dsv: parsing of CSV, TSV files.
  - ease: a collection of functions used by transitions.
  - format: a multiplatform formatting library with a DSL
  - geo: mechanism and algorithms to project GeoJson objects on a visualization.
  - hierarchy: generator for hierarchical charts.
  - path: abstraction over path generation.
  - random: various randomize functions.
  - sankey: generator for sankey charts.
  - scale: manage the transformation between domain objects and visualizations.
  - selection: a way of selecting visual elements of a datavisualization in order to apply some modifications.
  - shape: provides some generators for curves, areas, lines, stack, symbols,...
  - test: an internal module used to simplify multiplatform testing with a higher DSL.
  - tile: manage the loading, and display of tiles.
  - time: a multiplatform module to simplify time management inside datavisualizations.
  - time-format: formatting date and time.
  - timer: multiplatform way of managing animations through shared frames.
  - transitions: modifying visual elements through animated transitions.
  - viz: multiplatform abstraction over visual elements (circle, rectangle, ...)
  - voronoi: the voronoi algorithm


### Roadmap:

- We plan to release a chart module inspired by [vega-lite](https://vega.github.io/vega-lite/) to enable very fast charting development
- We'll create all necessary geographic projections
- Before the v1.0 release, we'll spend some time on polishing APIs and DSLs in order to improve the consistency
of the library


## Inspirations
 - [d3js](https://github.com/d3/d3): a lot of modules and algorithms come from d3js.
 - [paperjs](https://github.com/paperjs/paper.js): another source of inspiration for viz hierarchy and simple API.
 - [chromajs](https://github.com/gka/chroma.js): smart library for managing colors easily.
 - [delaunator](https://github.com/mapbox/delaunator): a really fast JavaScript library for Delaunay triangulation of 2D points.
 - [kotlinx.html](https://github.com/Kotlin/kotlinx.html): isomorphic html rendering.
 - [kotlintest](https://github.com/kotlintest/kotlintest): nice DSL for testing (partly ported in [test](tests)).
 
