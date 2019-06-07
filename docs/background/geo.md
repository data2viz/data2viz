
# Date2Viz geo

The goal of Data2viz `Geo` module is to provide tools to facilitate map projections: 
the transformation of the latitudes and longitudes of locations from the surface of a
 sphere into locations on a plane. Data2viz started by from a port of D3JS code to 
 Kotlin multiplatform. The `geo` module makes no exception and is a direct translation
  of D3JS algorithms and mechanisms into Kotlin. So, it's a good idea to refer to all 
  D3JS resources on the subject.

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
     point (x,y,z)    │      │       │      │      │      │              │      │    
     ───────────────▶ │      │  ──▶  │      │  ──▶ │      │              │      │    
                      │      │       │      │      │      │              │      │    
     line end         │      │       │      │      │      │              │      │    
     ───────────────▶ │      │  ──▶  │      │  ──▶ │      │              │      │    
                      │      │       │      │      │      │              │      │    
     polygon end      │      │       │      │      │      │              │      │    
     ───────────────▶ │      │  ──▶  │      │  ──▶ │      │              │      │    
                      │      │       │      │      │      │              │      │        
                      +——————+       +——————+      +——————+              +——————+    
                      
```  

While each `Stream` perform a specific part of the transformation, preserving the 
Stream chain, the last `Stream` transform the incoming calls into a a `Path`.

So, what `Streams` composes a global projection (now from top to down) ?

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
 +-----------------+       +----------------------------+       +-----------------+       +---------------------+ 
 |     Resample    |  ──▶  | TranslateAndScaleProjector |  ──▶  | RotateProjector |  ──▶  | ProjectionProjector | 
 +-----------------+       +----------------------------+       +-----------------+       +---------------------+ 
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

