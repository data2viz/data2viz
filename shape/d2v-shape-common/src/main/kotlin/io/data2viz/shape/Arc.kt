package io.data2viz.shape

//import io.data2viz.math.Angle
//import io.data2viz.math.rad
import io.data2viz.path.PathAdapter
import kotlin.math.*

private data class cornerTangentValues(
        val cx: Double,
        val cy: Double,
        val x01: Double,
        val y01: Double,
        val x11: Double,
        val y11: Double
)

/*data class ArcParameters(
        val innerRadius: Double = .0,
        val outerRadius: Double = 100.0,
        val cornerRadius: Double = 0.0,
        val padRadius: Double? = null,
        val startAngle: Double = 0.0,
        val endAngle: Double = 0.0,
        val padAngle: Double = 0.0
)*/

fun <T> arc(init: ArcGenerator<T>.() -> Unit) = ArcGenerator<T>().apply(init)
class ArcGenerator<T> {

    var innerRadius: (T) -> Double = const(.0)
    var outerRadius: (T) -> Double = const(100.0)
    var cornerRadius: (T) -> Double = const(.0)
    var padRadius: ((T) -> Double)? = null
    var startAngle: (T) -> Double = const(.0)
    //    var startAngle: (T) -> Angle = const(Angle(.0))           // TODO : Angle ?
    var endAngle: (T) -> Double = const(.0)
    //    var endAngle: (T) -> Angle = const(Angle(.0))             // TODO : Angle ?
    var padAngle: (T) -> Double = const(.0)
//    var padAngle: (T) -> Angle = const(Angle(.0))             // TODO : Angle ?

    fun centroid(args: T): Array<Double> {
        val r = innerRadius(args) + outerRadius(args) / 2.0
        val a = startAngle(args) + endAngle(args) / 2.0 - halfPi
//        val a = startAngle(args) + endAngle(args) / 2.0 - halfPi.rad
        return arrayOf(cos(a) * r, sin(a) * r)
//        return arrayOf(a.cos * r, a.sin * r)
    }

    /**
     * Use the data to generate an arc on the context
     */
    fun <C : PathAdapter> arc(args: T, context: C): C {
        var r0 = innerRadius(args)
        var r1 = outerRadius(args)
        val a0 = startAngle(args) - halfPi
//        val a0 = startAngle(args) - halfPi.rad
        val a1 = endAngle(args) - halfPi
//        val a1 = endAngle(args) - halfPi.rad
        val da = abs(a1 - a0)
//        val da = abs(a1.rad - a0.rad)
        val cw = a1 > a0
//        val cw = a1.rad > a0.rad

        // Ensure that the outer radius is always larger than the inner radius.
        if (r1 < r0) {
            val r = r1
            r1 = r0
            r0 = r
        }

        // Is it a point?
        if (r1 <= epsilon) context.moveTo(0.0, 0.0)

        // Or is it a circle or annulus?
        else if (da > tau - epsilon) {
            context.moveTo(r1 * cos(a0), r1 * sin(a0));
//            context.moveTo(r1 * a0.cos, r1 * a0.sin);
            context.arc(.0, .0, r1, a0, a1, !cw);
//            context.arc(.0, .0, r1, a0.rad, a1.rad, !cw);
            if (r0 > epsilon) {
                context.moveTo(r0 * cos(a1), r0 * sin(a1));
//                context.moveTo(r0 * a1.cos, r0 * a1.sin);
                context.arc(.0, .0, r0, a1, a0, cw);
//                context.arc(.0, .0, r0, a1.rad, a0.rad, cw);
            }
        }

        // Or is it a circular or annular sector?
        else {
            var a01 = a0
            var a11 = a1
            var a00 = a0
            var a10 = a1
            var da0 = da
            var da1 = da
            val ap = padAngle(args) / 2.0
            val rp = if (ap <= epsilon) 0.0 else {
//            val rp = if (ap.rad <= epsilon) 0.0 else {
                val temp = if (padRadius != null) padRadius!!(args) else sqrt(r0 * r0 + r1 * r1)
                if (temp != 0.0) 1.0 else 0.0
            }
            val rc = min(abs(r1 - r0) / 2, cornerRadius(args))
            var rc0 = rc
            var rc1 = rc

            // Apply padding? Note that since r1 ≥ r0, da1 ≥ da0.
            if (rp > epsilon) {
                var p0 = asin(rp / r0 * ap)
//                var p0 = asin(rp / r0 * ap.sin).rad
                var p1 = asin(rp / r1 * ap)
//                var p1 = asin(rp / r1 * ap.sin).rad
                da0 -= p0 * 2
//                da0 -= p0.rad * 2
                if (da0 > epsilon) {
                    p0 *= if (cw) 1.0 else -1.0
                    a00 += p0
                    a10 -= p0
                } else {
                    da0 = .0
                    a10 = (a0 + a1) / 2
                    a00 = a10
                }
                da1 -= p1 * 2
//                da1 -= p1.rad * 2
                if (da1 > epsilon) {
                    p1 *= if (cw) 1.0 else -1.0
                    a01 += p1
                    a11 -= p1
                } else {
                    da1 = .0
                    a11 = (a0 + a1) / 2
                    a01 = a11
                }
            }

            val x01 = r1 * cos(a01)
//            val x01 = r1 * a01.cos
            val y01 = r1 * sin(a01)
//            val y01 = r1 * a01.sin
            val x10 = r0 * cos(a10)
//            val x10 = r0 * a10.cos
            val y10 = r0 * sin(a10)
//            val y10 = r0 * a10.sin

            val x11 = r1 * cos(a11)
//            val x11 = r1 * a11.cos
            val y11 = r1 * sin(a11)
//            val y11 = r1 * a11.sin
            val x00 = r0 * cos(a00)
//            val x00 = r0 * a00.cos
            val y00 = r0 * sin(a00)
//            val y00 = r0 * a00.sin

            // Apply rounded corners?
            if (rc > epsilon) {

                // Restrict the corner radius according to the sector angle.
                if (da < pi) {
                    val oc = if (da0 > epsilon) intersect(x01, y01, x00, y00, x11, y11, x10, y10) else arrayOf(x10, y10)
                    val ax = x01 - oc[0]
                    val ay = y01 - oc[1]
                    val bx = x11 - oc[0]
                    val by = y11 - oc[1]
                    val kc = 1 / sin(acos((ax * bx + ay * by) / (sqrt(ax * ax + ay * ay) * sqrt(bx * bx + by * by))) / 2)
                    val lc = sqrt(oc[0] * oc[0] + oc[1] * oc[1])
                    rc0 = min(rc, (r0 - lc) / (kc - 1))
                    rc1 = min(rc, (r1 - lc) / (kc + 1))
                }
            }

            // Is the sector collapsed to a line?
            if (!(da1 > epsilon)) context.moveTo(x01, y01)

            // Does the sector’s outer ring have rounded corners?
            else if (rc1 > epsilon) {
                val t0 = cornerTangents(x00, y00, x01, y01, r1, rc1, cw);
                val t1 = cornerTangents(x11, y11, x10, y10, r1, rc1, cw);

                context.moveTo(t0.cx + t0.x01, t0.cy + t0.y01);

                // Have the corners merged?
                if (rc1 < rc) context.arc(t0.cx, t0.cy, rc1, atan2(t0.y01, t0.x01), atan2(t1.y01, t1.x01), !cw);

                // Otherwise, draw the two corners and the ring.
                else {
                    context.arc(t0.cx, t0.cy, rc1, atan2(t0.y01, t0.x01), atan2(t0.y11, t0.x11), !cw);
                    context.arc(.0, .0, r1, atan2(t0.cy + t0.y11, t0.cx + t0.x11), atan2(t1.cy + t1.y11, t1.cx + t1.x11), !cw);
                    context.arc(t1.cx, t1.cy, rc1, atan2(t1.y11, t1.x11), atan2(t1.y01, t1.x01), !cw);
                }
            }

            // Or is the outer ring just a circular arc?
            else {
                context.moveTo(x01, y01)
                context.arc(.0, .0, r1, a01, a11, !cw)
//                context.arc(.0, .0, r1, a01.rad, a11.rad, !cw)
            }

            // Is there no inner ring, and it’s a circular sector?
            // Or perhaps it’s an annular sector collapsed due to padding?
            if (!(r0 > epsilon) || !(da0 > epsilon)) context.lineTo(x10, y10)

            // Does the sector’s inner ring (or point) have rounded corners?
            else if (rc0 > epsilon) {
                val t0 = cornerTangents(x10, y10, x11, y11, r0, -rc0, cw)
                val t1 = cornerTangents(x01, y01, x00, y00, r0, -rc0, cw)

                context.lineTo(t0.cx + t0.x01, t0.cy + t0.y01)

                // Have the corners merged?
                if (rc0 < rc) context.arc(t0.cx, t0.cy, rc0, atan2(t0.y01, t0.x01), atan2(t1.y01, t1.x01), !cw)

                // Otherwise, draw the two corners and the ring.
                else {
                    context.arc(t0.cx, t0.cy, rc0, atan2(t0.y01, t0.x01), atan2(t0.y11, t0.x11), !cw)
                    context.arc(.0, .0, r0, atan2(t0.cy + t0.y11, t0.cx + t0.x11), atan2(t1.cy + t1.y11, t1.cx + t1.x11), cw)
                    context.arc(t1.cx, t1.cy, rc0, atan2(t1.y11, t1.x11), atan2(t1.y01, t1.x01), !cw)
                }
            }

            // Or is the inner ring just a circular arc?
            else context.arc(.0, .0, r0, a10, a00, cw);
//            else context.arc(.0, .0, r0, a10.rad, a00.rad, cw);
        }

        context.closePath();
        return context
    }

    // Compute perpendicular offset line of length rc.
    // http://mathworld.wolfram.com/Circle-LineIntersection.html
    private fun cornerTangents(x0: Double, y0: Double, x1: Double, y1: Double, r1: Double, rc: Double, cw: Boolean): cornerTangentValues {
        val x01 = x0 - x1
        val y01 = y0 - y1
        val lo = (if (cw) rc else -rc) / sqrt(x01 * x01 + y01 * y01)
        val ox = lo * y01
        val oy = -lo * x01
        val x11 = x0 + ox
        val y11 = y0 + oy
        val x10 = x1 + ox
        val y10 = y1 + oy
        val x00 = (x11 + x10) / 2
        val y00 = (y11 + y10) / 2
        val dx = x10 - x11
        val dy = y10 - y11
        val d2 = dx * dx + dy * dy
        val r = r1 - rc
        val D = x11 * y10 - x10 * y11
        val d = (if (dy < 0) -1.0 else 1.0) * sqrt(max(.0, r * r * d2 - D * D))
        var cx0 = (D * dy - dx * d) / d2
        var cy0 = (-D * dx - dy * d) / d2
        val cx1 = (D * dy + dx * d) / d2
        val cy1 = (-D * dx + dy * d) / d2
        val dx0 = cx0 - x00
        val dy0 = cy0 - y00
        val dx1 = cx1 - x00
        val dy1 = cy1 - y00

        // Pick the closer of the two intersection points.
        // TODO Is there a faster way to determine which intersection to use?
        if (dx0 * dx0 + dy0 * dy0 > dx1 * dx1 + dy1 * dy1) {
            cx0 = cx1
            cy0 = cy1
        }

        return cornerTangentValues(cx0, cy0, -ox, -oy, cx0 * (r1 / r - 1), cy0 * (r1 / r - 1))
    }

    fun intersect(x0: Double, y0: Double, x1: Double, y1: Double, x2: Double, y2: Double, x3: Double, y3: Double): Array<Double> {
        val x10 = x1 - x0
        val y10 = y1 - y0
        val x32 = x3 - x2
        val y32 = y3 - y2
        val t = (x32 * (y0 - y2) - y32 * (x0 - x2)) / (y32 * x10 - x32 * y10)
        return arrayOf(x0 + t * x10, y0 + t * y10)
    }
}