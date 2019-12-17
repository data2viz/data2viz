/*
 * Copyright (c) 2018-2019. data2viz sàrl.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 *
 */

package io.data2viz.hierarchy.pack

import io.data2viz.hierarchy.CircleValues
import io.data2viz.hierarchy.PackNode
import kotlin.math.sqrt

private data class Circle(
    override var x: Double = .0,
    override var y: Double = .0,
    override var r: Double = .0
) : CircleValues

/**
 * Computes the smallest circle that encloses the specified array of circles, each of which must have a circle.r
 * property specifying the circle’s radius, and circle.x and circle.y properties specifying the circle’s center.
 * The enclosing circle is computed using the Matoušek-Sharir-Welzl algorithm.
 */
fun enclose(circles: List<CircleValues>): CircleValues? {
    var i = 0
    val shuffledCircles = circles.shuffled()
    val n = shuffledCircles.size
    var B = listOf<CircleValues>()
    var e: CircleValues? = null

    while (i < n) {
        val p = shuffledCircles[i]
        if (e != null && enclosesWeak(e, p)) i++
        else {
            B = extendBasis(B, p)
            e = encloseBasis(B)
            i = 0
        }
    }
    return e
}

private fun enclosesWeak(a: CircleValues, b: CircleValues): Boolean {
    val dr = a.r - b.r + epsilon
    val dx = b.x - a.x
    val dy = b.y - a.y
    return (dr > 0) && ((dr * dr) > ((dx * dx) + (dy * dy)))
}

private fun enclosesWeakAll(a: CircleValues, B: List<CircleValues>): Boolean {
    for (i in 0 until B.size) {
        if (!enclosesWeak(a, B[i])) {
            return false
        }
    }
    return true
}

private fun enclosesNot(a: CircleValues, b: CircleValues): Boolean {
    val dr = a.r - b.r
    val dx = b.x - a.x
    val dy = b.y - a.y
    return (dr < 0) || ((dr * dr) < ((dx * dx) + (dy * dy)))
}

private fun encloseBasis(B:List<CircleValues>): CircleValues {
    when (B.size) {
        1 -> return encloseBasis1(B[0])
        2 -> return encloseBasis2(B[0], B[1])
        else -> return encloseBasis3(B[0], B[1], B[2])
    }
}

private fun encloseBasis1(a: CircleValues) = Circle(a.x, a.y, a.r)

private fun encloseBasis2(a: CircleValues, b: CircleValues): CircleValues {
    val x1 = a.x
    val y1 = a.y
    val r1 = a.r
    val x2 = b.x
    val y2 = b.y
    val r2 = b.r
    val x21 = x2 - x1
    val y21 = y2 - y1
    val r21 = r2 - r1
    val l = sqrt(x21 * x21 + y21 * y21)
    return Circle((x1 + x2 + x21 / l * r21) / 2, (y1 + y2 + y21 / l * r21) / 2, (l + r1 + r2) / 2)
}

private fun encloseBasis3(a: CircleValues, b: CircleValues, c: CircleValues): CircleValues {
    val x1 = a.x
    val y1 = a.y
    val r1 = a.r
    val x2 = b.x
    val y2 = b.y
    val r2 = b.r
    val x3 = c.x
    val y3 = c.y
    val r3 = c.r
    val a2 = x1 - x2
    val a3 = x1 - x3
    val b2 = y1 - y2
    val b3 = y1 - y3
    val c2 = r2 - r1
    val c3 = r3 - r1
    val d1 = x1 * x1 + y1 * y1 - r1 * r1
    val d2 = d1 - x2 * x2 - y2 * y2 + r2 * r2
    val d3 = d1 - x3 * x3 - y3 * y3 + r3 * r3
    val ab = a3 * b2 - a2 * b3
    val xa = (b2 * d3 - b3 * d2) / (ab * 2) - x1
    val xb = (b3 * c2 - b2 * c3) / ab
    val ya = (a3 * d2 - a2 * d3) / (ab * 2) - y1
    val yb = (a2 * c3 - a3 * c2) / ab
    val A = xb * xb + yb * yb - 1
    val B = 2 * (r1 + xa * xb + ya * yb)
    val C = xa * xa + ya * ya - r1 * r1
    val r = -(if (A != .0) (B + sqrt(B * B - 4 * A * C)) / (2 * A) else C / B)
    return Circle(x1 + xa + xb * r, y1 + ya + yb * r, r)
}

private fun extendBasis(B: List<CircleValues>, p: CircleValues): List<CircleValues> {
    if (enclosesWeakAll(p, B)) return listOf(p)

    // If we get here then B must have at least one element.
    for (i in 0 until B.size) {
        if (enclosesNot(p, B[i])
            && enclosesWeakAll(encloseBasis2(B[i], p), B)
        ) {
            return listOf(B[i], p)
        }
    }

    // If we get here then B must have at least two elements.
    for (i in 0 until B.size - 1) {
        for (j in (i + 1) until B.size) {
            if (enclosesNot(encloseBasis2(B[i], B[j]), p)
                && enclosesNot(encloseBasis2(B[i], p), B[j])
                && enclosesNot(encloseBasis2(B[j], p), B[i])
                && enclosesWeakAll(encloseBasis3(B[i], B[j], p), B)
            ) {
                return listOf(B[i], B[j], p)
            }
        }
    }

    // If we get here then something is very wrong.
    throw RuntimeException("Unable to compute enclosing circle for PackLayout.")
}
