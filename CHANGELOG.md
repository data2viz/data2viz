# Version v0.8

This version brings multiplatform events to the library. It is now possible to add pointer events to
a visualization and retrieve position during different events: move, click, double-click, ...

This version also includes some major internal modifications on the Geo module.


## 0.8.0-RC10

bugs: #182

## 0.8.0-RC8
> Published 30 Dec 2019

**Breaking change**: publish common code as `-common` artifact and metadata
as artifact without suffix.

## 0.8.0-RC7
> Published 18 Dec 2019

**Breaking change**: new package and artifact names for NPM publications.

## 0.8.0-RC6
> Published 17 Dec 2019

**Breaking change**: Due to the use of the new MPP (and some constraints that come with it), 
data2viz artifacts are now deployed on jcenter (instead of maven central) using a new structure.
 
Ex: io.data2viz:d2v-core-js becomes io.data2viz.d2v:core-js  

## 0.8.0-RC5
* Breaking change: make distinction between mouse and touch events (#162)

## 0.8.0-RC4
> Published 26 Nov 2019
* Breaking change timeFormat module renamed in time-format (#164)

## 0.8.0-RC3
> Published 12 Nov 2019
* Fix KPointerEvent position inside browser when external CSS size applied (#160)


## 0.8.0-RC2
> Published 05 Nov 2019


* Add Copyright
* Remove minimum distance for drag (#159)

## 0.8.0-RC1
> Published 20 Sep 2019

* Adding multiplatform events API
* Implementation of all major geographic projections

# Version v0.7 :

This major version brings support for Android. This addition of Android as a target
platform had a big impact on the design of data2viz. Before v0.7 basic visual elements 
(rectangles, circles, ...) were wrappers on specific platform elements. Now, these elements
are a memory version of the visualization. Elements are just rendered on each platform 
using canvas. 