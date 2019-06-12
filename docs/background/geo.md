
# Date2Viz geo module

```
     .-'';'-.                 ┌──────────────────┐
  ,'    [_,-. `.              │                  │
 /)    ,--,_>\_ \             │   ) `"""";._/}   │
|     (      \_  |    ──>     │   |       ' /    │
|_     `-.    /  |            │   \        |     │
 \`-.    ;  _(’ /             │    '--. .-.\     │
  `.(     \/  ,'              │ USA    `   `     │
     `-....-´                 └──────────────────┘

```

The goal of Data2viz `Geo` module is to provide tools to facilitate map projections: 
the transformation of the latitude and longitude of locations from the surface of a
 sphere into x and y positions on a plane.

> Data2viz started from a port of D3JS code to  Kotlin multiplatform. The `geo`
module makes no exception and is a direct translation of D3JS algorithms and
mechanisms into Kotlin. So, it's a good idea to refer to all D3JS resources on
the subject.

These transformations could be relatively simple: a math function to transform 
Geographic coordinates (longitude and latitude) into cartesian coordinates (x and y) 
to define a position on the screen.

However, projections are not limited to discrete points. To draw maps, we need more 
complex geometries: lines (streets, highways, and boundaries), polygons (countries, 
provinces, tracts of land), and multi-part collections of these types.

Data2viz geo module projects GeoJson objects on the screen using projections. The 
[GeoJson specification](https://tools.ietf.org/html/rfc7946) defines a few object types: 
 - Point: a discrete location specified by its longitude, its latitude, and an optional altitude,
- Multipoint: a collection of points,
- LineString: a line defined by a list of points.
- MultiLineString: a collection of lines.
- Polygon: an external close line (a ring), with optional internal rings representing holes.
- MultiPolygon: a collection of polygons.
- GeometryCollection: a collection of geometries of the previous types.
- Feature: an association of a geometry with properties.
- FeatureCollection: a collection of Features.

The primary use case of the `Geo` module is to perform the transformation of a 
GeoJson object into a path rendered on the screen:

```
              ┌──────────────────┐
GeoJson  ──>  │    Projection    │  ──>   Path
              └──────────────────┘
```

As GeoJson objects are only composed of Polygons, lines, and points, Data2viz 
uses a simplified interface, `Stream` to deal with those base objects. 


## Streams 
 
The global transformation mechanism is split into a chain of `Streams`:


```

Polygon                Stream 1      Stream x      PathStream              Path     
                      ┌──────┐       ┌──────┐      ┌──────┐              ┌──────┐
                      │      │       │      │      │      │              │      │        
     polygon start    │      │       │      │      │      │    moveTo    │      │        
     ───────────────> │      │  ──>  │      │  ──> │      │   ───────>   │      │    
                      │      │       │      │      │      │              │      │    
     line start       │      │       │      │      │      │   lineTo     │      │    
     ───────────────> │      │  ──>  │      │  ──> │      │   ───────>   │      │    
                      │      │       │      │      │      │              │      │    
     point (x,y,z)    │      │       │      │      │      │   lineTo     │      │    
     ───────────────> │      │  ──>  │      │  ──> │      │   ───────>   │      │    
                      │      │       │      │      │      │              │      │    
     point (x,y,z)    │      │       │      │      │      │   lineTo     │      │    
     ───────────────> │      │  ──>  │      │  ──> │      │   ───────>   │      │    
                      │      │       │      │      │      │              │      │    
     point (x,y,z)    │      │       │      │      │      │   lineTo     │      │
     ───────────────> │      │  ──>  │      │  ──> │      │   ───────>   │      │
                      │      │       │      │      │      │              │      │    
     line end         │      │       │      │      │      │              │      │    
     ───────────────> │      │  ──>  │      │  ──> │      │              │      │    
                      │      │       │      │      │      │              │      │    
     polygon end      │      │       │      │      │      │              │      │    
     ───────────────> │      │  ──>  │      │  ──> │      │              │      │    
                      │      │       │      │      │      │              │      │        
                      └──────┘       └──────┘      └──────┘              └──────┘

```

A polygon of three points, for instance, calls on the first stream this
series of calls: `polygonStart`,`lineStart`,`point(x,y,z)`,`point(x,y,z)`
,`point(x,y,z)`, `lineEnd`,`polygonEnd`. Coordinates are only transmitted
during `point` calls. `polygon` and `line` call give the context of
the current transmitted GeoJson object.

Depending on the Stream's role, the calls to downstream Stream are synchronous
or buffered. `TransformRadian` Stream, for example, which is only changing
coordinates from degrees to radians during `point` calls, transmits immediately
(synchronously) the calls to its following Stream. The number of transmitted Point
from input to output of a Stream can differ. For instance, `ResampleStream` uses an
 adaptative algorithm to generate intermediary points to have smooth curves made of
 lines.


While each `Stream` perform a specific part of the transformation, preserving the
Stream chain, the last `Stream` transform the incoming calls into a a `Path`.

So, what `Streams` composes a global projection?

_Previous left to right flow is now presented from top to down due to the number of steps._

```
    GeoJson object

          │
          v
 ┌─────────────────┐
 │ TransformRadian │
 └─────────────────┘
          │
          v
 ┌─────────────────┐       ┌─────────────────┐
 │ TransformRotate │  ──>  │ RotateProjector │
 └─────────────────┘       └─────────────────┘
          │
          v
 ┌─────────────────┐
 │     PreClip     │
 └─────────────────┘
          │
          v
 ┌─────────────────┐       ┌────────────────────────────┐       ┌─────────────────────┐
 │     Resample    │  ──>  │ TranslateAndScaleProjector │  ──>  │ ProjectionProjector │
 └─────────────────┘       └────────────────────────────┘       └─────────────────────┘
          │
          v
 ┌─────────────────┐
 │    PostClip     │
 └─────────────────┘
          │
          v
 ┌─────────────────┐
 │    PathStream   │
 └─────────────────┘
          │
          v
         Path

```

`TransformRadian` stream has the simple role of transforming longitude and latitude
coordinates from degrees (GeoJson specification) to radians, which is the default
unit for trigonometry calculus.

`TransformRotate` stream allows applying a rotation transformation on all axes
(yaw, pitch, roll) while the coordinates are still geometric.

`PreClip` stream allows cutting objects while the coordinates are still
geometric. Few types of clip can be applied. The most usual per-clip are
 CircleClip and AntimeridianClip that clip geometries through the antimeridian.

`Resample` stream performs a lot of transformations. It converts the geometric
coordinates to cartesian coordinates. It transforms cartesian coordinates by
applying translation and scaling to fit the expected display.  Finally, it
resamples the lines, creating intermediary points to smooth curves.

`PostClip` stream allows cutting objects with cartesian coordinates. The most
usual post-clip is RectangleClip.

`PathStream` is the last element of the chain. It converts all the previous
chained transformations into calls on a Path using `moveTo`, `lineTo` and
`arc` function calls. `arc` calls displays single points as circles.


## Projection

A `Projection` is an assembly of all these streams to perform the global transformation wanted 
by the user. The Geo Module provides some factories to get and configure projections in a 
straightforward way, using a discoverable API.

```kotlin
//default Mercator projection
val mercatorProjection = Geo.Projections.Mercator()

//Mercator projection with scale, and center set to display France
val franceMercator = Geo.Projections.Mercator {
    fixExtend(screen, france)
}

```

A projection provides some setters to configure and modify it. It's possible to :
 - set the rotation of the map on all axes in geographic coordinates
 - set pre and post clip,
 - set scale,
 - ...

A projection allows to project any kind of GeoJson objects:

```kotlin
//projecting a country GeoJson object and retrieving it as a new path
val path = projection.project(country)

//projecting a country GeoJson object on a predefined path
projection.project(country, countryPath)
```

It's also possible to project simple 

## Projectors

This interface represents the elementary transformation from geographic to cartesian coordinate.  
It provides two functions: 
 - `project`: geographic to cartesian transformation
 - `invert`: cartesian to geographic transformation.
