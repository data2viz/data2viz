<img src="http://data2viz.io/images/identity/d2v-logo-no-gradient-lg-no-marge@2.png" alt="Data2viz" width="604" style="max-width:100%;">


[![Download](https://api.bintray.com/packages/data2viz/data2viz/data2viz/images/download.svg) ](https://bintray.com/data2viz/data2viz/data2viz/_latestVersion)
[![Build Status](https://travis-ci.org/data2viz/data2viz.svg?branch=master)](https://travis-ci.org/data2viz/data2viz)
[![GitHub License](https://img.shields.io/badge/license-Apache%20License%202.0-blue.svg?style=flat)](http://www.apache.org/licenses/LICENSE-2.0) 
# Isomorphic dataviz

data2viz is a multiplatform data visualization library based on kotlin. The rendering produces the same result
on each platform: android, JS and JavaFx.


A very important part of current version is a port of [d3js](https://github.com/d3/d3) modules.

The code is separated in modules that can be independently used. Some are specifically designed for visualization 
([d2v-axis](https://github.com/data2viz/data2viz/tree/master/axis),
[d2v-path](https://github.com/data2viz/data2viz/tree/master/path),
[d2v-shape](https://github.com/data2viz/data2viz/tree/master/shape), 
[d2v-viz](https://github.com/data2viz/data2viz/tree/master/viz)) while others can be used outside of dataviz projects. 
For example, 
[d2v-format](https://github.com/data2viz/data2viz/tree/master/format) 
can be used as a multiplatform kotlin format library outside of any dataviz project.

data2viz proposes to develop data visualizations through a fully typed DSL. It simplifies the creation of complex
 visualizations by helping developer with IDE’s suggestions based on the current context.


## Some samples

All examples are available in [examples](examples) directory. Here is a first selection that shows you what you can
do with data2viz. All example are running in the browser and as JavaFx applications. You can open each js version
using the links below.

#### [Geo projection](examples/ex-geo)

This code uses the geo module to show how to load a GeoJson file and render it using a
projection.

<a target="_blank" href="https://data2viz.github.io/data2viz/ex-geo-js/index.html" >
Open online js version.
</a>

[See code](examples/ex-geo)

<a href="https://github.com/data2viz/data2viz/tree/master/examples/ex-geo" >
<img src="https://raw.githubusercontent.com/data2viz/data2viz/master/examples/images/geo.png" width="400">
</a>

#### [Forces](examples/ex-force)

This funny demo shows how to use different aspects of forces: radial, nbody, x, y forces.

<a target="_blank" href="https://data2viz.github.io/data2viz/ex-force-js/index.html" >
Open online js version.
</a>

[See code](examples/ex-force)

<a href="https://github.com/data2viz/data2viz/tree/master/examples/ex-force" >
<img src="https://raw.githubusercontent.com/data2viz/data2viz/master/examples/images/force.png" width="400">
</a>

#### [Chord Graph](examples/ex-chord)

- Chord diagram,
- Arcs, Path

<a target="_blank" href="https://data2viz.github.io/data2viz/ex-chord-js/index.html" >
Open online js version.
</a>

[See code](examples/ex-chord)


<a href="https://github.com/data2viz/data2viz/tree/master/examples/ex-chord" >
<img src="https://raw.githubusercontent.com/data2viz/data2viz/master/examples/images/chord.png" width="400">
</a>


#### [Stream Graph](examples/ex-streamGraph)

- Stack,
- Curves, stream grapth

<a target="_blank" href="https://data2viz.github.io/data2viz/ex-streamGraph-js/index.html" >
Open online js version.
</a>

[See code](examples/ex-streamGraph)

<a href="https://github.com/data2viz/data2viz/tree/master/examples/ex-streamGraph" >
<img src="https://raw.githubusercontent.com/data2viz/data2viz/master/examples/images/stream-graph.png" width="400">
</a>

#### [Selection performance sample](examples/ex-selection)

- Animated circle, deployed inside JavaFx and Js

<a target="_blank" href="https://data2viz.github.io/data2viz/ex-selection/index.html" >
Open online js version.
</a>

[See code](examples/ex-selection)

<a href="https://github.com/data2viz/data2viz/tree/master/examples/ex-selection" >
<img src="https://raw.githubusercontent.com/data2viz/data2viz/master/examples/images/selection.png" width="400">
</a>


#### [Natural Log Scale](examples/ex-natural-logscale)

- Common Plot, deployed inside JavaFx and Js
- LogScale, ContinuousScale.
- Path

<a target="_blank" href="https://data2viz.github.io/data2viz/ex-natural-logscale-js/index.html" >
Open online js version.
</a>

[See code](examples/ex-natural-logscale)

<a href="https://github.com/data2viz/data2viz/tree/master/examples/ex-natural-logscale" >
<img src="https://raw.githubusercontent.com/data2viz/data2viz/master/examples/images/natural-log-scale-jfx.png" width="400">
</a>


## Current status && Roadmap

The project is currently in a very active devlopment phase. APIs are expected to change and we don't
encourage you to use it already in production.

### current version (v0.7):

This major version brings the support of android. This addition of android as a target 
platform had a big impact on the design of data2viz. Before v0.7 basic visual elements 
(rectangles, circles, ...) were wrappers on specific platform elements. Now, these elements
are a memory version of the visualization. Elements are just rendered on each platform 
using canvas. 

This version includes a cleaner hierarchy with layers at its base.

###  Version 0.6: 

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


### and next:
We plan to release a chart module inspired by vega-lite to enable very fast charting development.

We'll create all the necessary geographic projections.

Before the v1.0 release, we'll spend some time on polishing APIs and DSLs in order to provide
a very consistent way of using our library.


## Tests
Tests are executed through karma and mocha and included in the build.

## How can I use it?


Artefacts are published on [Bintray](https://bintray.com/data2viz/data2viz/data2viz).

You can also clone this repo and have a look on the [example directory](examples).


## Inspirations
 - [d3js](https://github.com/d3/d3): a lot of modules and algorithms come from d3js
 - [paperjs](https://github.com/paperjs/paper.js): another source of inspiration for viz hierarchy and simple API.
 - [delaunator](https://github.com/mapbox/delaunator): A really fast JavaScript library for Delaunay triangulation of 2D points.
 - [kotlinx.html](https://github.com/Kotlin/kotlinx.html): isomorphic html rendering 
 - [kotlintest](https://github.com/kotlintest/kotlintest): nice DSL for testing (partly ported in [test](tests))
 
