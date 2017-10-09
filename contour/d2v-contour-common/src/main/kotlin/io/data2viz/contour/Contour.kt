package io.data2viz.contour


data class GeoJson(val type: String, val value: Double, val coordinates: List<Array<List<Array<Double>>>>)


fun contour(init: Contour.() -> Unit) = Contour().apply(init)

val cases = arrayOf(
        arrayOf(),
        arrayOf(line(pt(1.0, 1.5), pt(0.5, 1.0))),
        arrayOf(line(pt(1.5, 1.0), pt(1.0, 1.5))),
        arrayOf(line(pt(1.5, 1.0), pt(0.5, 1.0))),
        arrayOf(line(pt(1.0, 0.5), pt(1.5, 1.0))),
        arrayOf(line(pt(1.0, 0.5), pt(0.5, 1.0)), line(pt(1.0, 1.5), pt(1.5, 1.0))),
        arrayOf(line(pt(1.0, 0.5), pt(1.0, 1.5))),
        arrayOf(line(pt(1.0, 0.5), pt(0.5, 1.0))),
        arrayOf(line(pt(0.5, 1.0), pt(1.0, 0.5))),
        arrayOf(line(pt(1.0, 1.5), pt(1.0, 0.5))),
        arrayOf(line(pt(0.5, 1.0), pt(1.0, 1.5)), line(pt(1.5, 1.0), pt(1.0, 0.5))),
        arrayOf(line(pt(1.5, 1.0), pt(1.0, 0.5))),
        arrayOf(line(pt(0.5, 1.0), pt(1.5, 1.0))),
        arrayOf(line(pt(1.0, 1.5), pt(1.5, 1.0))),
        arrayOf(line(pt(0.5, 1.0), pt(1.0, 1.5))),
        arrayOf()
)

fun line(start: Array<Double>, end: Array<Double>) = arrayOf(start, end)
fun pt(x: Double, y: Double) = arrayOf(x, y)

class Contour {

    var thresholds: (Array<Double>) -> Array<Double> = { arrayOf() }
    var dx: Int = 1
    var dy: Int = 1


    fun size(dx: Int, dy: Int) {
        if (dx <= 0 || dy <= 0)
            error("invalid size")
        this.dx = dx
        this.dy = dy
    }

    fun contours(values: Array<Double>): List<GeoJson> {

        if (values.size != dx * dy)
            error("Wrong value size")
        val tz = thresholds(values).sortedArray()

        val layers = tz.map { thresold ->
            val polygons: MutableList<Array<List<Array<Double>>>> = mutableListOf<Array<List<Array<Double>>>>()
            val holes = mutableListOf<List<Array<Double>>>()

            isorings(values, thresold) { ring: List<Array<Double>> ->
                //todo smooth ring
                if (area(ring) > 0)
                    polygons.add(arrayOf(ring))
                else
                    holes.add(ring)

            }

//            holes.forEach { hole ->
//                for (i in (0 until polygons.size)) {
//                    val polygon = polygons[i]
//                    if (contains(polygon[0], hole) != -1) {
//                        polygon.add(hole)
//                        return;
//                    }
//                }
//            }
            polygons as List<Array<List<Array<Double>>>>
        }

        return layers.mapIndexed { index, it ->
            GeoJson("MultiPolygon", tz[index], it)
        }
    }

//    private fun contains(ring: List<Array<Double>>, hole: List<Array<Double>>): Any {
//        var i = -1
//        val n = hole.size
//        var c = 'e'
//        while (++i < n)
//            if (c = ringContains(ring, hole[i]))
//                return c
//        return 0
//    }

    private fun ringContains(ring: List<Array<Double>>, point: Array<Double>): Any {
        var x = point[0]
        var y = point[1]
        var contains = -1
        var n = ring.size
        var j = n -1
        for ( i in (0 until n)) {
            j = i
            var pi = ring[i]
            var xi = pi[0]
            var yi = pi[1]
            var pj = ring[j]
            var xj = pj[0]
            var yj = pj[1];
            if (segmentContains(pi, pj, point))
                return 0;
            if (((yi > y) != (yj > y)) && ((x < (xj - xi) * (y - yi) / (yj - yi) + xi)))
                contains = - contains;
        }
        return contains;
    }

    private fun segmentContains(a: Array<Double>, b: Array<Double>, c: Array<Double>): Boolean {
        var i = if (a[0]  == b[0]) 1 else 0
        return collinear(a, b, c) && within(a[i], c[i], b[i])
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    private fun within(p: Double, q: Double, r: Double)= p <= q && q <= r || r <= q && q <= p

    private fun collinear(a: Array<Double>, b: Array<Double>, c: Array<Double>) =
            (b[0] - a[0]) * (c[1] - a[1]) == (c[0] - a[0]) * (b[1] - a[1])

    private fun area(ring: List<Array<Double>>): Double {
        var i = 0
        val n = ring.size
        var area = ring[n - 1][1] * ring[0][0] - ring[n - 1][0] * ring[0][1]
        while (++i < n)
            area += ring[i - 1][1] * ring[i][0] - ring[i - 1][0] * ring[i][1];
        return area;
    }


    private class Fragment(var start: Double, var end: Double, val ring: MutableList<Array<Double>>)

    private fun isorings(values: Array<Double>, thresold: Double, callback: (List<Array<Double>>) -> Unit) {
        var t0: Boolean
        var t1: Boolean
        var t2: Boolean
        var t3: Boolean
        val fragmentByStart = mutableMapOf<Double, Fragment>()
        val fragmentByEnd = mutableMapOf<Double, Fragment>()

        var x = -1
        var y = -1

        fun thresold(index: Int) = values[index] >= thresold
        fun Boolean.shl(bitCount: Int = 0) = (if (this) 1 else 0) shl bitCount

        fun index(point: Array<Double>) = point.x * 2 + point.y * (dx + 1) * 4

        fun stitch(line: Array<Array<Double>>) {
            val start = pt(line.start.x + x, line.start.y + y)
            val end = pt(line.end.x + x, line.end.y + y)
            val startIndex = index(start)
            val endIndex = index(end)

            var f = fragmentByEnd.get(startIndex)
            var g = fragmentByStart.get(endIndex)
            if (f != null) {
                if (g != null) {
                    fragmentByEnd.remove(f.end)
                    fragmentByStart.remove(g.start)
                    if (f === g) {
                        f.ring.add(end)
                        callback(f.ring)
                    } else {
                        val startEnd = Fragment(f.start, g.end, (f.ring + g.ring).toMutableList())
                        fragmentByStart.put(f.start, startEnd)
                        fragmentByStart.put(g.end, startEnd)
                    }
                } else {
                    fragmentByEnd.remove(f.end)
                    f.ring.add(end)
                    f.end = endIndex
                    fragmentByEnd.put(endIndex, f)
                }
            } else if (fragmentByStart[endIndex]?.let { f = it;true } == true) {
                if (fragmentByEnd[startIndex]?.let { g = it; true } == true) {
                    fragmentByStart.remove(f!!.start)
                    fragmentByEnd.remove(g!!.start)
                    if (f === g) {
                        f!!.ring.add(end)
                        callback(f!!.ring)
                    } else {
                        val startEnd = Fragment(g!!.start, f!!.end, (g!!.ring + f!!.ring).toMutableList())
                        fragmentByStart.put(g!!.start, startEnd)
                        fragmentByEnd.put(f!!.end, startEnd)
                    }
                } else {
                    fragmentByStart.remove(f!!.start)
                    f!!.ring.add(0, start)
                    f!!.start = startIndex
                    fragmentByStart[startIndex] = f!!
                }
            } else {
                val startEnd = Fragment(startIndex, endIndex, mutableListOf(start, end))
                fragmentByStart[startIndex] = startEnd
                fragmentByEnd[endIndex] = startEnd
            }
        }

        // Special case for the first row (y = -1, t2 = t3 = 0).
        t1 = thresold(0)
        cases[if (t1) 1 else 0].forEach(::stitch)
        while (++x < dx - 1) {
            t0 = t1
            t1 = thresold(x + 1)
            cases[(t0.shl() or t1.shl(1))].forEach(::stitch)
        }
        cases[t1.shl()].forEach(::stitch)

        // General case for the intermediate rows.
        while (++y < dy - 1) {
            x = -1
            t1 = thresold(y * dx + dx)
            t2 = thresold(y * dx)
            cases[t1.shl(1) or t2.shl(2)].forEach(::stitch)
            while (++x < dx - 1) {
                t0 = t1
                t1 = thresold(y * dx + dx + x + 1)
                t3 = t2
                t2 = thresold(y * dx + x + 1)
                cases[t0.shl() or t1.shl(1) or t2.shl(2) or t3.shl(3)].forEach(::stitch)
            }
            cases[t1.shl() or t2.shl(3)].forEach(::stitch)
        }

        // Special case for the last row (y = dy - 1, t0 = t1 = 0).
        x = -1
        t2 = thresold(y * dx)
        cases[t2.shl(2)].forEach(::stitch)
        while (++x < dx - 1) {
            t3 = t2
            t2 = thresold(y * dx + x + 1)
            cases[t2.shl(2) or t3.shl(3)].forEach(::stitch)
        }
        cases[t2.shl(3)].forEach(::stitch)


    }

    fun smoothLinear(ring: MutableList<Array<Double>>, values: Array<Double>, value: Double) {
//        ring.forEach { pt ->
//            val x = pt.x
//            val y = pt.y
//            val xt = if (x != 0.0) x else 0.0 //todo WTF
//            val yt = if (y != 0.0) y else 0.0
//            val v1 = values[yt * dx - xt - 1]
//            if (x >0 && x < dx && xt === x){
//
//            }
//        }
    }


}


//Pseudo Point
private val Array<Double>.x: Double
    get() = this[0]
private val Array<Double>.y: Double
    get() = this[1]

//Pseudo line
private val Array<Array<Double>>.start: Array<Double>
    get() = this[0]
private val Array<Array<Double>>.end: Array<Double>
    get() = this[1]


