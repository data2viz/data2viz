
# Data2Viz geo module

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
              +------------------+  
GeoJson  ──▶  │    Geo Module    │  ──▶   Path  
              +------------------+  
```

As GeoJson objects are only composed of Polygons, lines, and points, Data2viz 
uses a simplified interface, `Stream` to deal with those base objects. 
 
 
The global transformation mechanism is split into a chain of `Streams`:


```

Polygon                Stream 1      Stream x      PathStream              Path     
                      +——————+       +——————+      +——————+              +——————+    
                      │      │       │      │      │      │              │      │        
     polygon start    │      │       │      │      │      │    moveTo    │      │        
     ───────────────▶ │      │  ──▶  │      │  ──▶ │      │   ───────▶   │      │    
                      │      │       │      │      │      │              │      │    
     line start       │      │       │      │      │      │   lineTo     │      │    
     ───────────────▶ │      │  ──▶  │      │  ──▶ │      │   ───────▶   │      │    
                      │      │       │      │      │      │              │      │    
     point (x,y,z)    │      │       │      │      │      │   lineTo     │      │    
     ───────────────▶ │      │  ──▶  │      │  ──▶ │      │   ───────▶   │      │    
                      │      │       │      │      │      │              │      │    
     point (x,y,z)    │      │       │      │      │      │   lineTo     │      │    
     ───────────────▶ │      │  ──▶  │      │  ──▶ │      │   ───────▶   │      │    
                      │      │       │      │      │      │              │      │    
     point (x,y,z)    │      │       │      │      │      │   lineTo     │      │    
     ───────────────▶ │      │  ──▶  │      │  ──▶ │      │   ───────▶   │      │    
                      │      │       │      │      │      │              │      │    
     line end         │      │       │      │      │      │              │      │    
     ───────────────▶ │      │  ──▶  │      │  ──▶ │      │              │      │    
                      │      │       │      │      │      │              │      │    
     polygon end      │      │       │      │      │      │              │      │    
     ───────────────▶ │      │  ──▶  │      │  ──▶ │      │              │      │    
                      │      │       │      │      │      │              │      │        
                      +——————+       +——————+      +——————+              +——————+    
                      
```  

A polygon of three points, for example, calls on the first stream a series of calls on the first
stream: `polygonStart`,`lineStart`,`point(x,y,z)`,`point(x,y,z)`,`point(x,y,z)`, `lineEnd`,`polygonEnd`.
Coordinates are only transmitted during `point` calls. `polygon` and `line` call give the context of 
the current transmitted GeoJson object. 

Depending on the Stream's role, the calls to downstream Stream are synchronous or buffered. 
`TransformRadian` Stream, for example, which is only changing coordinates from degrees to radians
during `point` calls, transmits immediately (synchronously) the calls to its following Stream. The
number of transmitted Point from input to output of a Stream can differ. For instance, `ResampleStream`
uses an adaptative algorithm to generate intermediary points to have smooth curves made of lines.    
 

While each `Stream` perform a specific part of the transformation, preserving the 
Stream chain, the last `Stream` transform the incoming calls into a a `Path`.

So, what `Streams` composes a global projection?  
_Previous left to right flow is now presented from top to down due to the number of steps._

```
    GeoJson object
    
          │
          ▼
 +----------------+ 
 |  CachedStream  | 
 +----------------+     
          │
          ▼
 +-----------------+ 
 | TransformRadian | 
 +-----------------+ 
          │
          ▼
 +-----------------+       +-----------------+
 | TransformRotate |  ──▶  | RotateProjector |
 +-----------------+       +-----------------+
          │
          ▼
 +-----------------+ 
 |     PreClip     | 
 +-----------------+ 
          │
          ▼
 +-----------------+       +----------------------------+       +---------------------+ 
 |     Resample    |  ──▶  | TranslateAndScaleProjector |  ──▶  | ProjectionProjector | 
 +-----------------+       +----------------------------+       +---------------------+ 
          │
          ▼
 +-----------------+ 
 |    PostClip     | 
 +-----------------+ 
          │
          ▼
 +-----------------+ 
 |    PathStream   | 
 +-----------------+ 
          │
          ▼
         Path

```

