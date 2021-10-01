<img src="http://data2viz.io/images/identity/d2v-logo-no-gradient-lg-no-marge@2.png" alt="Data2viz" width="604" style="max-width:100%;">


[![Build Status](https://travis-ci.org/data2viz/data2viz.svg?branch=master)](https://travis-ci.org/data2viz/data2viz)
[![GitHub License](https://img.shields.io/badge/license-Apache%20License%202.0-blue.svg?style=flat)](http://www.apache.org/licenses/LICENSE-2.0) 
# Isomorphic dataviz

Data2viz is a data visualization toolbox for Kotlin Multiplatform.

You can pick what you need in the different modules and use them independently in the following environments: Android, JavaScript(IR and Legacy), and JavaFX (iOs to come). 

Your code produces the same results and rendering on each platform.

A lot of algorithms come from [d3js](https://github.com/d3/d3) modules.

### [core](https://github.com/data2viz/data2viz/tree/master/core)
This module exposes some basics elements like Circle, Rect, Path, and also some parts of DSL (percent, angles, etc.).

### [color](https://github.com/data2viz/data2viz/tree/master/color)
This module provides algorithms to create, modify, and convert colors through differents spaces (RGB, HCL, HSL, Lab). It also manage linear and radial color gradients. You can easily use it outside of data2viz. 

### [shape](https://github.com/data2viz/data2viz/tree/master/shape)
Algorithms to generate paths: symbols, curves, ...

### [scale](https://github.com/data2viz/data2viz/tree/master/scale)
Different ways to convert data to sizes.

### [interpolate](https://github.com/data2viz/data2viz/tree/master/interpolate)
Functions to perform interpolation of various elements (numbers, colors, points, curves)

### [random](https://github.com/data2viz/data2viz/tree/master/random)
Algorithms to generate random data.

### [axis](https://github.com/data2viz/data2viz/tree/master/axis)
Helper module to create axis for charts, using scales. 

### [delaunay](https://github.com/data2viz/data2viz/tree/master/delaunay)
This module is a high performant algorithm to compute the Voronoi diagram. You should use it in many use cases to find the closest point of interest to the pointer position. 

### [ease](https://github.com/data2viz/data2viz/tree/master/ease)
This module contains a collection of easing functions to manage acceleration inside animations.

### [dsv](https://github.com/data2viz/data2viz/tree/master/dsv)
Minimal way of parsing CSV, TSV files using Kotlin.

### [force](https://github.com/data2viz/data2viz/tree/master/force)
Use physics to animate your visualizations.

### [format](https://github.com/data2viz/data2viz/tree/master/format)
Different ways of formating numbers and currencies.

### [time](https://github.com/data2viz/data2viz/tree/master/time)
Some multiplatform classes to manage time and dates in visualizations.

### [time-format](https://github.com/data2viz/data2viz/tree/master/time-format)
Extend formatting to time and dates.

### [quadtree](https://github.com/data2viz/data2viz/tree/master/quadtree)
Separation of space through the quadtree algorithm.

### [voronoi](https://github.com/data2viz/data2viz/tree/master/voronoi)
Fortune's algorithm implementation of the Voronoi diagram. You should probably use Delaunay instead.

### [geo](https://github.com/data2viz/data2viz/tree/master/geo)
A collection of mechanisms and functions to project GeoJson elements (points, lines, polygons) on a screen using different implementations of projections.

### [hexbin](https://github.com/data2viz/data2viz/tree/master/hexbin)
A useful way of aggregating data using hexagons. You can represent data through the fill color or the area.

### [sankey](https://github.com/data2viz/data2viz/tree/master/sankey)
A generator for sankey charts.

### [hierarchy](https://github.com/data2viz/data2viz/tree/master/hierarchy)
A generator for hierarchical charts.

### [tests](https://github.com/data2viz/data2viz/tree/master/tests)
An internal module used to simplify multiplatform testing with a higher DSL.

### [timer](https://github.com/data2viz/data2viz/tree/master/timer)
A multiplatform implementation to manage animations through shared frames.

### [viz](https://github.com/data2viz/data2viz/tree/master/viz)
Multiplatorm API and implementation of rendering and events management.


Data2viz allows you to develop data visualizations through a fully typed DSL. It simplifies the creation of complex
 visualizations via the IDE’s context-based suggestions.
 
## Where should I start?

### Documentation

All data2viz documentation is located in 
[a distinct documentation project](https://github.com/data2viz/data2viz-docs). 
You should start there and follow 
[the first JavaFX tutorial](https://github.com/data2viz/data2viz-docs/blob/master/tutorials/barchart-jfx/javafx-bar-chart.md).

### Data2viz Playground

You can also play with the API without installing anything. 
[Data2viz playground](https://play.data2viz.io/) is a website where 
you can browse existing sample, modify them online and immediately 
see the result. 


## Using in your projects

> Note that the library is experimental, and the API is subject to change.

The library is published to data2viz space repository.



### Gradle

- Add the data2viz maven repository:

```kotlin
repositories {
    maven { url = uri("https://maven.pkg.jetbrains.space/data2viz/p/maven/public") }
}
```

- In multiplatform projects, add a dependency to the commonMain source set dependencies
```kotlin
kotlin {
    sourceSets {
        commonMain {
             dependencies {
                 implementation("io.data2viz.d2v:axis:0.8.14")
                 implementation("io.data2viz.d2v:chord:0.8.14")
                 implementation("io.data2viz.d2v:color:0.8.14")
                 implementation("io.data2viz.d2v:contour:0.8.14")
                 implementation("io.data2viz.d2v:delaunay:0.8.14")
                 implementation("io.data2viz.d2v:dsv:0.8.14")
                 implementation("io.data2viz.d2v:ease:0.8.14")
                 implementation("io.data2viz.d2v:force:0.8.14")
                 implementation("io.data2viz.d2v:format:0.8.14")
                 implementation("io.data2viz.d2v:geo:0.8.14")
                 implementation("io.data2viz.d2v:hexbin:0.8.14")
                 implementation("io.data2viz.d2v:hierarchy:0.8.14")
                 implementation("io.data2viz.d2v:quadtree:0.8.14")
                 implementation("io.data2viz.d2v:random:0.8.14")
                 implementation("io.data2viz.d2v:scale:0.8.14")
                 implementation("io.data2viz.d2v:shape:0.8.14")
                 implementation("io.data2viz.d2v:tile:0.8.14")
                 implementation("io.data2viz.d2v:time:0.8.14")
                 implementation("io.data2viz.d2v:timer:0.8.14")
                 implementation("io.data2viz.d2v:viz:0.8.14")
             }
        }
    }
}
```

- To use the library in a single-platform project, add a dependency to the dependencies block.

```groovy
dependencies {
    implementation("io.data2viz.d2v:axis:0.8.14")
    implementation("io.data2viz.d2v:chord:0.8.14")
    implementation("io.data2viz.d2v:color:0.8.14")
    implementation("io.data2viz.d2v:contour:0.8.14")
    implementation("io.data2viz.d2v:delaunay:0.8.14")
    implementation("io.data2viz.d2v:dsv:0.8.14")
    implementation("io.data2viz.d2v:ease:0.8.14")
    implementation("io.data2viz.d2v:force:0.8.14")
    implementation("io.data2viz.d2v:format:0.8.14")
    implementation("io.data2viz.d2v:geo:0.8.14")
    implementation("io.data2viz.d2v:hexbin:0.8.14")
    implementation("io.data2viz.d2v:hierarchy:0.8.14")
    implementation("io.data2viz.d2v:quadtree:0.8.14")
    implementation("io.data2viz.d2v:random:0.8.14")
    implementation("io.data2viz.d2v:scale:0.8.14")
    implementation("io.data2viz.d2v:shape:0.8.14")
    implementation("io.data2viz.d2v:tile:0.8.14")
    implementation("io.data2viz.d2v:time:0.8.14")
    implementation("io.data2viz.d2v:timer:0.8.14")
    implementation("io.data2viz.d2v:viz:0.8.14")
}
```

## Current status and roadmap

APIs are mostly stabilized now, but there may still be some breaking changes before v1.0.


## Inspirations
 - [d3js](https://github.com/d3/d3): a lot of modules and algorithms come from d3js.
 - [paperjs](https://github.com/paperjs/paper.js): another source of inspiration for viz hierarchy and simple API.
 - [chromajs](https://github.com/gka/chroma.js): smart library for managing colors easily.
 - [delaunator](https://github.com/mapbox/delaunator): a really fast JavaScript library for Delaunay triangulation of 2D points.
 - [kotlinx.html](https://github.com/Kotlin/kotlinx.html): isomorphic html rendering.
 - [kotlintest](https://github.com/kotlintest/kotlintest): nice DSL for testing (partly ported in [test](tests)).
 
