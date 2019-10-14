# Contributing

## Performances optimization

Performance is critical in datavisualization. It allows fluid animations, 
a significant volume of data, etc.

However, optimizing the performance in Data2viz project is challenging, 
due to multiplatform targets. One modification can improve performance 
on one platform, but degrade on another. It's very usual to observe very 
different bottlenecks from one platform to another.

Accordingly, it is mandatory to test all performance optimizations in 
isolated branches, with a complete performance report comparing the 
results before and after the modification. The tests must compare 
the results on all platforms. 

## Code style
Spaces, NOT tabs.